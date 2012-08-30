package com.wutianyi.study.jmx.ch6;

public interface PollingMBean
{
    public void start();

    public void stop();

    public void setInterval(long time);
}
