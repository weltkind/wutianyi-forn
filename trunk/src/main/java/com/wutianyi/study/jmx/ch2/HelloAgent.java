package com.wutianyi.study.jmx.ch2;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloAgent implements NotificationListener
{
    private MBeanServer mbs = null;

    /**
     * 
     */
    public HelloAgent()
    {
        //域名
        mbs = MBeanServerFactory.createMBeanServer("HelloAgent");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        HelloWorld hw = new HelloWorld();
        ObjectName adapterName = null;
        ObjectName helloWorldName = null;
        
        try
        {
            //关联objectName
            helloWorldName = new ObjectName("HelloAgent:name=helloworld1");
            mbs.registerMBean(hw, helloWorldName);
            adapterName = new ObjectName("HelloAgent: name=htmladapter,port=9092");
            adapter.setPort(9092);
            mbs.registerMBean(adapter, adapterName);
            adapter.start();
            hw.addNotificationListener(this, null, null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        System.out.println("HelloAgent is running");
        HelloAgent agent = new HelloAgent();
    }
    @Override
    public void handleNotification(Notification notification, Object handback)
    {
        System.out.println("Receving notification...");
        System.out.println(notification.getType());
        System.out.println(notification.getMessage());
    }
}
