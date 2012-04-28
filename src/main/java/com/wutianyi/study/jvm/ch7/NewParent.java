package com.wutianyi.study.jvm.ch7;

public class NewParent
{
    static int hoursOfSleep = (int) (Math.random() * 3.0);
    
    static {
        System.out.println("NewParent was initialized");
    }
}
