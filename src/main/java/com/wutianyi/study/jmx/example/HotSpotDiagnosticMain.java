package com.wutianyi.study.jmx.example;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.management.HotSpotDiagnosticMXBean;

import sun.management.HotSpotDiagnostic;

public class HotSpotDiagnosticMain
{
    public static void main(String[] args) throws IOException
    {
        JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(address);

        MBeanServerConnection msc = connector.getMBeanServerConnection();

        HotSpotDiagnosticMXBean hotspot = ManagementFactory.newPlatformMXBeanProxy(msc,
                "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
        hotspot.dumpHeap("out.txt", true);
    }
}
