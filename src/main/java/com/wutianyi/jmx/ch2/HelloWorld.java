package com.wutianyi.jmx.ch2;

public class HelloWorld implements HelloWorldMBean
{

    private String greeting = null;

    public HelloWorld(String greeting)
    {
        this.greeting = greeting;
    }

    public HelloWorld()
    {
        this.greeting = "Hello World! I am a Standard MBean";
    }

    public void setGreeting(String greet)
    {
        this.greeting = greet;

    }

    public String getGreeting()
    {
        return greeting;
    }

    public void pringGreeting()
    {
        System.out.println(greeting);
    }

}
