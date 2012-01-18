package com.wutianyi.qq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class TestClient
{
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("172.27.35.19",20001));
        
        System.out.println("start");
        
        OutputStream out = socket.getOutputStream();
        out.write(10);
        out.flush();
        
        int len = 10;
        int seq = 1;
        short prot_ver = 100;
        short api_ver = 100;
        int reqtime = (int) System.currentTimeMillis();
        int uin = 619561504;
        String str = "MA20120111150007232\0";
        char[] ruleid = new char[32];
        System.arraycopy(str.toCharArray(), 0, ruleid, 0, str.toCharArray().length);
        ruleid[str.toCharArray().length] = '\0';
        char[] reserved = new char[32];
        reserved[0] = '\n';
        int resultcode = 1;
        char[]resultinfo = new char[64];
        resultinfo[0] = '\0';
        
           
//        InputStream input = socket.getInputStream();
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        System.out.println("getInput");
//        while((len = input.read(buffer)) != -1)
//        {
//            System.out.println(buffer);
//        }
    }
    
}
