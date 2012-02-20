package com.wutianyi.study.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;


public class Main
{
    public static void main(String[] args)
    {
        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setProxy("web-proxy.oa.com", 8080);
        HttpMethod httpMethod = new GetMethod("http://www.baidu.com");
       
        try
        {
            httpClient.executeMethod(httpMethod);
            System.out.println(httpMethod.getStatusLine());
            System.out.println(httpMethod.getResponseBodyAsString());
        }
        catch (HttpException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
