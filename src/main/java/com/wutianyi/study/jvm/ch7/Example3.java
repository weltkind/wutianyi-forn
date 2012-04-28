package com.wutianyi.study.jvm.ch7;

public class Example3
{
    
    public static void main(String[] args)
    {
        System.out.println(Angry.greeting);
        System.out.println(Dog.greeting);
    }

    static {
        System.out.println("Example3 was initialized!");
    }
}
