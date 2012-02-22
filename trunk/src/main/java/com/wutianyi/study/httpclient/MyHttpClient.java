package com.wutianyi.study.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

public class MyHttpClient
{

    HttpClient httpClient;

    public MyHttpClient()
    {
        httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        httpClient.getHostConfiguration().setProxy("web-proxy.oa.com", 8080);
    }

    void getBodyToFile(String url, String dest) throws HttpException, IOException
    {
        HttpMethod method = new GetMethod(url);
        FileOutputStream outputStream = null;
        try
        {
            httpClient.executeMethod(method);
            outputStream = new FileOutputStream(new File(dest));
            InputStream inputStream = method.getResponseBodyAsStream();
            byte[] bufs = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bufs)) != -1)
            {
                outputStream.write(bufs, 0, len);
            }
        }
        finally
        {
            if(null != outputStream)
            {
                outputStream.flush();
                outputStream.close();
            }
            method.releaseConnection();
        }
    }

    String getBodyAsString(String url, String contentType, String encoding) throws HttpException, IOException
    {
        HttpMethod method = new GetMethod(url);
        method.addRequestHeader("Content-Type", contentType);
        try
        {
            httpClient.executeMethod(method);
            if (method.getStatusCode() == 200)
            {
                return new String(method.getResponseBody(), encoding);
            }
        }
        finally
        {
            method.releaseConnection();
        }

        return null;
    }

}
