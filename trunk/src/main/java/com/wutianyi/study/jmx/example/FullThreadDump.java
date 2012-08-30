package com.wutianyi.study.jmx.example;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class FullThreadDump
{
    private MBeanServerConnection server;
    private JMXConnector jmxc;

    public FullThreadDump(String hostname, int port)
    {
        System.out.println("Connecting to " + hostname + ":" + port);
        String urlPath = "/jndi/rmi://" + hostname + ":" + port + "/jmxrmi";
        connect(urlPath);
    }

    public void dump()
    {
        try
        {
            ThreadMonitor monitor = new ThreadMonitor(server);
            monitor.threadDump();
            if (!monitor.findDeadlock())
            {
                System.out.println("No deadlock found.");
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void connect(String urlPath)
    {
        try
        {
            JMXServiceURL url = new JMXServiceURL("rmi", "", 0, urlPath);
            this.jmxc = JMXConnectorFactory.connect(url);
            this.server = this.jmxc.getMBeanServerConnection();
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        String host = args[0];
        int port =Integer.parseInt(args[1]);
        FullThreadDump threadDump = new FullThreadDump(host, port);
        threadDump.dump();
    }
}
