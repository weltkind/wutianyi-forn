package com.wutianyi.study.jmx.ch3;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import com.sun.jdmk.comm.RmiConnectorServer;

public class JMXBookAgent
{
    private MBeanServer server;

    public JMXBookAgent()
    {
        System.out.println("\n\tCREAT the MBeanServer.");
        server = MBeanServerFactory.createMBeanServer("JMXBookAgent");
        startHTMLAdapter();
        startRMIConnector();
    }

    private void startRMIConnector()
    {
        RmiConnectorServer connector = new RmiConnectorServer();
        ObjectName connectorName = null;
        try
        {
            connector.setPort(2099);
            connectorName = new ObjectName("JMXBookAgent:name=RMIConnector");
            server.registerMBean(connector, connectorName);
            connector.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void startHTMLAdapter()
    {
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        ObjectName adapterName = null;
        try
        {
            adapter.setPort(9082);
            adapterName = new ObjectName("JMXBookAgent:name=html,port=9082");
            server.registerMBean(adapter, adapterName);
            adapter.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void startMletService()
    {
        ObjectName mletName = null;
        try
        {
            mletName = new ObjectName("JMXBookAgent:name=mlet");
            server.createMBean("javax.management.loading.Mlet", mletName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n>>> START of JMXBook Agent");
        System.out.println("\n>>> CREATE the agent...");
        JMXBookAgent agent = new JMXBookAgent();
        System.out.println("\nAgent is Ready of Service....\n");
    }
}
