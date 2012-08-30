package com.wutianyi.qq;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.wutianyi.study.encoding.CharsetServices;

public class ExternalResourceUtils
{
    private static Map<String, String[]> externalResources;

    static
    {
        externalResources = new HashMap<String, String[]>();
        File file = new File(ExternalResourceUtils.class.getResource("/external_resource.txt").getFile());
        if (file.exists() && file.isFile())
        {
            try
            {
                @SuppressWarnings("unchecked")
                List<String> contents = FileUtils.readLines(file);
                if (null != contents && !contents.isEmpty())
                {
                    for (String c : contents)
                    {
                        if (StringUtils.isNotBlank(c))
                        {
                            String[] cs = c.split("\\^=");
                            if (cs.length != 2)
                            {
                                continue;
                            }
                            String[] urls = cs[1].split(";");
                            externalResources.put(cs[0], urls);
                        }
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static byte[] getExternalResources(String key) throws IOException
    {
        String[] urls = externalResources.get(key);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (null != urls)
        {
            for (String url : urls)
            {
                System.out.println(url);
                URL u = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.connect();
                byte[] buffers = new byte[urlConnection.getContentLength()];
                InputStream inputStream = urlConnection.getInputStream();
                inputStream.read(buffers);
                
                buffers = CharsetServices.tranEncoding(buffers, "utf-8");
                
                baos.write(buffers);
                // System.out.println(urlConnection.getContentLength());
                // System.out.println(urlConnection.getHeaderField("Content-Encoding"));
                // System.out.println(urlConnection.getContentEncoding());

//                Map<String, List<String>> headers = urlConnection.getHeaderFields();
//                for (Entry<String, List<String>> header : headers.entrySet())
//                {
//                    System.out.print(header.getKey());
//                    System.out.print("=");
//                    for (String h : header.getValue())
//                    {
//                        System.out.print(h + ",");
//                    }
//                    System.out.println();
//                }
            }
        }

        return baos.toByteArray();
    }

    public static String getQueryStringByExt(String key, boolean merge)
    {
        
        return null;
    }
    
    public static void main(String[] args) throws IOException
    {
        byte[] bufs = ExternalResourceUtils.getExternalResources("external_js");
//        bufs = CharsetServices.tranEncoding(bufs, "utf-8");
        System.out.println(new String(bufs));
    }
}
