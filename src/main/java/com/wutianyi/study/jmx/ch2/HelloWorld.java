package com.wutianyi.study.jmx.ch2;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class HelloWorld extends NotificationBroadcasterSupport implements HelloWorldMBean
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
        Notification notification = new Notification("com.wutianyi.study.jmx.test", this, -1,
                System.currentTimeMillis(), greeting);
        sendNotification(notification);
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
