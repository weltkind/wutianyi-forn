package com.wutianyi.study.jvm.ch7;

public class NewbornBaby extends NewParent
{
    static int hoursOfCrying = 6 + (int)(Math.random() * 2.0);
    
    static {
        System.out.println("NewbornBaby war initialize!");
    }
}
