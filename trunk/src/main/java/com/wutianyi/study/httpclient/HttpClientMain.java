package com.wutianyi.study.httpclient;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.ParserException;

import com.wutianyi.utils.Pair;

public class HttpClientMain
{
    private static String url = "http://blog.sina.com.cn/main/html/alltop_more_new_1.html";

    public static void main(String[] args) throws ParserException, HttpException, IOException, InterruptedException
    {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final MyHttpClient myHttpClient = new MyHttpClient();
        HtmlParser parser = new HtmlParser(myHttpClient.getBodyAsString(url, "text/html;charset=gbk", "gbk"));
        SinaInfoHandle handle = new SinaInfoHandle();
        List<Pair<String, String>> results = parser.parser(handle, new NodeFilter[]
        { new TagNameFilter("a"), new HasParentFilter(new TagNameFilter("td")) });

        Set<String> nextPages = handle.getNextPages();
        System.out.println(handle.getSize());
        for (String next : nextPages)
        {
            HtmlParser nextParser = new HtmlParser(myHttpClient.getBodyAsString(next, "text/html;charset=gbk", "gbk"));
            SinaInfoHandle nextHandle = new SinaInfoHandle();
            results.addAll(nextParser.parser(nextHandle, new NodeFilter[]
            { new TagNameFilter("a"), new HasParentFilter(new TagNameFilter("td")) }));
            System.out.println(next + " : " + nextHandle.getSize());
        }
        System.out.println(results.size());

        for (final Pair<String, String> pair : results)
        {
            executorService.submit(new Runnable()
            {

                @Override
                public void run()
                {
                    try
                    {
                        myHttpClient.getBodyToFile(pair.getFirst(), "file/rss/" + pair.getSecond() + ".xml");
                    }
                    catch (HttpException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
    }
}
