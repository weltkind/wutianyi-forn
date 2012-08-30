package com.wutianyi.study.jmx.example;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.MalformedURLException;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import sun.management.HotSpotDiagnostic;

public class ProxyConnector
{

    public static void main(String[] args) throws IOException
    {
        JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(address);
        MBeanServerConnection mbs = connector.getMBeanServerConnection();
        ThreadMXBean threadBean = ManagementFactory.newPlatformMXBeanProxy(mbs, ManagementFactory.THREAD_MXBEAN_NAME,
                ThreadMXBean.class);
        System.out.println(threadBean.getThreadCount());
        threadBean.setThreadCpuTimeEnabled(true);
        long[]threadIds = threadBean.getAllThreadIds();
        ThreadInfo[] threadInfos = threadBean.getThreadInfo(threadIds);
        for(ThreadInfo threadInfo : threadInfos)
        {
            if(null != threadInfo)
            {
                System.out.println(threadInfo.getThreadName());
            }
        }
        System.out.println(threadBean.getPeakThreadCount());
        
    }
}
