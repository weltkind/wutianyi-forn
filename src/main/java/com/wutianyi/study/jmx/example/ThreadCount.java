package com.wutianyi.study.jmx.example;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class ThreadCount
{
    public static void main(String[] args)
    {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.println(threadBean.getPeakThreadCount());
    }
}
