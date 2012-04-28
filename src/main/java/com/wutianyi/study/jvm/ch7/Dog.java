package com.wutianyi.study.jvm.ch7;

public class Dog
{
    static final String greeting = "woof, woof, world!";
    
    static {
        System.out.println("Dog was initialized.");
    }
    
    static int getAngerLevel() {
        System.out.println("Angry was initialized!");
        return 1;
    }
}
