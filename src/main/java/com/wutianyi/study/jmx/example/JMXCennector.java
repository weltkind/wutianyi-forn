package com.wutianyi.study.jmx.example;

import java.io.IOException;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXCennector
{
    public static void main(String[] args) throws IOException
    {
        JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:1234/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(address);
        MBeanServerConnection mbs = connector.getMBeanServerConnection();
        
        System.out.println("ok");
    }
}
