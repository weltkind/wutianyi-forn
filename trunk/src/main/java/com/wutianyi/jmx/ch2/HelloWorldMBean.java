package com.wutianyi.jmx.ch2;

public interface HelloWorldMBean
{
    public void setGreeting(String greet);
    
    public String getGreeting();
    
    public void pringGreeting();
}
