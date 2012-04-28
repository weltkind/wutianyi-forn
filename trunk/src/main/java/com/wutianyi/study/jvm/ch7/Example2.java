package com.wutianyi.study.jvm.ch7;

public class Example2
{

    public static void main(String[] args)
    {
        int hours = NewbornBaby.hoursOfSleep;
        System.out.println(hours);
    }
    
    static {
        System.out.println("Example2 was initialized!");
    }
}
