package com.wutianyi.study.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientMain
{
    
    public static void main(String[] args)
    {
        HttpClient httpClient = new HttpClient();
        httpClient.getState().setCredentials(null, null);
        HttpMethod method = new GetMethod();
        method.setDoAuthentication(true);
    }
}
