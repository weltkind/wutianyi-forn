package com.wutianyi.qq;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client
{

    public static void main(String[] args) throws UnknownHostException, IOException
    {
        // ServerSocket server = new ServerSocket(8080);
        // System.out.println("start");
        // while(true)
        // {
        // Socket socket = server.accept();
        // System.out.println("connected");
        // socket.close();
        // }
        String str = "MA20120111150007232\0";
        char[] ruleid = new char[64];
        System.out.println(ruleid.length);
        System.out.println(ruleid[1]);
        System.arraycopy(str.toCharArray(), 0, ruleid, 0, str.toCharArray().length);
        ruleid[str.toCharArray().length] = '\0';
        System.out.println(ruleid);
        System.out.println(str.getBytes().length);
        // Socket socket = new Socket("172.27.35.19", 20001);
        // if(socket.isConnected())
        // {
        // System.out.println("yes");
        // }
        // socket.close();
    }
}
