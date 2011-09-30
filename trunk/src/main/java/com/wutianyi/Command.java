package com.wutianyi;

public class Command
{
    public static void main(String[] args)
    {
        if(null != args) {
            for(String arg : args) {
                System.out.println(arg);
            }
        }
    }
}
