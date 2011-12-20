package com.wutianyi.study.discoverygroup;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

public class MyHttpClient
{
    private HttpClient httpClient;

    public MyHttpClient()
    {
        MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
        httpClient = new HttpClient(manager);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
    }

    public String getBodyAsString(String url)
    {
        HttpMethod method = new GetMethod(url);

        method.setFollowRedirects(true);
        try
        {
            method.addRequestHeader(new Header("Content-Type", "text/html; charset=ISO-8859-1"));
            method.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:8.0.1) Gecko/20100101 Firefox/8.0.1");
            method.addRequestHeader("Proxy-Connection", "keep-alive");
            httpClient.executeMethod(method);
            Header[] headers = method.getRequestHeaders();
            for (Header header : headers)
            {
                System.out.println(header.getName() + " : " + header.getValue());
            }
            System.out.println(method.getStatusLine());
            System.out.println(method.getStatusCode());
            return method.getResponseBodyAsString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            method.releaseConnection();
        }
        return StringUtils.EMPTY;
    }

    public static void main(String[] args)
    {
        MyHttpClient myHttpClient = new MyHttpClient();
        System.out.println(myHttpClient.getBodyAsString("http://alibaba.com"));
    }
}
