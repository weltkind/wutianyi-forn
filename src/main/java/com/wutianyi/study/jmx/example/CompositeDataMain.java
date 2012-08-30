package com.wutianyi.study.jmx.example;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.net.MalformedURLException;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class CompositeDataMain
{
    public static void main(String[] args) throws IOException, MalformedObjectNameException, Exception
    {
        JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(address);
        MBeanServerConnection mbs = connector.getMBeanServerConnection();

        ObjectName srvThreadName = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
        long[] threadIds = (long[]) mbs.getAttribute(srvThreadName, "AllThreadIds");
        CompositeData[] threadDataset = (CompositeData[]) mbs.invoke(srvThreadName, "getThreadInfo", new Object[]
        { threadIds }, new String[]
        { "[J" });
        for(CompositeData threadCD:threadDataset)
        {
            ThreadInfo threadInfo = ThreadInfo.from(threadCD);
            if(null != threadInfo)
            {
                System.out.println(threadInfo.getThreadName());
            }
        }
    }
}
