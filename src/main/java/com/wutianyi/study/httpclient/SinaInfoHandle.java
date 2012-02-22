package com.wutianyi.study.httpclient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.tags.LinkTag;

import com.wutianyi.utils.Pair;

public class SinaInfoHandle implements InfoHandle<Pair<String, String>>
{

    Map<String, String> urlMapName;
    Set<String> nextPages;

    public SinaInfoHandle()
    {
        urlMapName = new HashMap<String, String>();
        nextPages = new HashSet<String>();
    }

    public int getSize() 
    {
        return urlMapName.size();
    }
    
    @Override
    public Pair<String, String> handle(Node node)
    {
        if (node instanceof LinkTag)
        {
            LinkTag link = (LinkTag) node;
            if (StringUtils.equals(link.getAttribute("class"), "a02"))
            {
                nextPages.add("http://blog.sina.com.cn/main/html/" + link.getAttribute("href"));
            }
            else
            {
                String href = "http://blog.sina.com.cn/rss/"
                        + link.getAttribute("href").substring(link.getAttribute("href").lastIndexOf('/') + 1) + ".xml";
                String value = link.toPlainTextString().replaceAll("/", "").replaceAll(":", "").replaceAll("\\s", "")
                        .replaceAll("\\*", "");
                if (urlMapName.containsKey(href))
                {
                    if (urlMapName.get(href).length() > value.length())
                    {
                        return new Pair<String, String>(href, value);
                    }
                    else
                    {
                        return new Pair<String, String>(href, urlMapName.get(href));
                    }
                }
                else
                {
                    urlMapName.put(href, value);
                }
            }
        }
        return null;
    }

    public Set<String> getNextPages()
    {
        return nextPages;
    }

    public static void main(String[] args)
    {
        String str = "88**afda";
        System.out.println(str.replaceAll("\\*", ""));
    }
}
