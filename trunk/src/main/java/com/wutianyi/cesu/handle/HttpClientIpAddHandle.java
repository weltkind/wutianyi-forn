package com.wutianyi.cesu.handle;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientIpAddHandle implements Handle
{

    MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
    HttpClient httpClient = new HttpClient(connectionManager);
    String uri = "http://hanjiewu.cs0309.3g.qq.com/webapp_scan/ip.jsp?";

    @Override
    public String handle(String str)
    {
        GetMethod method = new GetMethod(uri + "ip=" + str);
        String result = "";
        try
        {
            httpClient.executeMethod(method);
            result = method.getResponseBodyAsString();
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
        finally
        {
            method.releaseConnection();
        }
        return result;
    }

    public static void main(String[] args)
    {
        Handle handle = new HttpClientIpAddHandle();
        String result = handle.handle("121.30.233.130");
        result = result.replaceAll("\r\n", "");
        String[] rs = result.split("-");
        for(String s : rs)
        {
            System.out.println(s);
        }
        System.out.println();
    }
}
