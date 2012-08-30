package com.wutianyi.study.jmx.example;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.LoggingMXBean;

public class MXBeanMain
{
    public static void main(String[] args)
    {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        List<MemoryPoolMXBean> memPoolBeans = ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean mpb : memPoolBeans)
        {
            System.out.println("Memory Pool: " + mpb.getName());
        }
        LoggingMXBean loggingBean = LogManager.getLoggingMXBean();
    }
}
