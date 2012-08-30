package com.wutianyi.study.jmx.example;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

public class GetProcessID
{
    public static void main(String[] args)
    {
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        System.out.println(bean.getName());
        String[] classPaths = bean.getClassPath().split(";");
        for(String classPath : classPaths)
        {
            System.out.println(classPath);
        }
        System.out.println();
        
        String[] libPaths = bean.getLibraryPath().split(";");
        for(String libPath : libPaths)
        {
            System.out.println(libPath);
        }
        
        System.out.println(bean.getUptime());
        System.out.println(new Date(bean.getStartTime()));
    }
}
