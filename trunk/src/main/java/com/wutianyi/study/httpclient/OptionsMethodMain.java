package com.wutianyi.study.httpclient;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.OptionsMethod;

public class OptionsMethodMain
{
    
    public static void main(String[] args) throws HttpException, IOException
    {
        HttpClient client = new HttpClient();
        OptionsMethod method = new OptionsMethod("http://www.baidu.com");
        client.executeMethod(method);
        Enumeration e = method.getAllowedMethods();
        while(e.hasMoreElements())
        {
            System.out.println(e.nextElement());
        }
        method.releaseConnection();
    }
}
