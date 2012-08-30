package com.wutianyi.study.jmx.ch6;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import com.sun.jdmk.comm.RmiConnectorClient;
import com.wutianyi.study.jmx.RMIClientFactory;

public class PollingSetup implements NotificationListener
{

    public PollingSetup()
    {
        try
        {
            RmiConnectorClient client = RMIClientFactory.getClient();
            ObjectName pollingName = new ObjectName("JMXBookAgent:name=polling");
            client.createMBean("com.wutianyi.study.jmx.ch6.Polling", pollingName);
            client.addNotificationListener(pollingName, this, null, null);
            //添加接收server发出的notification
            ObjectName delegate = new ObjectName("JMImplementation:type=MBeanServerDelegate");
            client.addNotificationListener(delegate, this, null, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void handleNotification(Notification notification, Object handback)
    {
        String type = notification.getType();
        System.out.println(type);
    }
    public static void main(String[] args)
    {
        PollingSetup setup = new PollingSetup();
    }

}
