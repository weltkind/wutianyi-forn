package com.wutianyi.study.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

public class BasicAuthenticationExample
{

    public static void main(String[] args) throws HttpException, IOException
    {
        HttpClient client = new HttpClient();
        client.getState().setCredentials(new AuthScope("qq.com", 80, AuthScope.ANY_HOST),
                new UsernamePasswordCredentials("tianyi86728@qq.com", "wuhanjie860728"));
        GetMethod get = new GetMethod("https://mail.qq.com/cgi-bin/loginpage?s=session_timeout&from=&r=3ce371394d93188927bca726ae2485c7&");
        get.setDoAuthentication(true);
        get.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
        try
        {
            int status = client.executeMethod(get);
            get.getResponseCharSet();
            System.out.println(status + "\n" + get.getResponseBodyAsString());
        }
        finally
        {
            get.releaseConnection();
        }

    }
}
