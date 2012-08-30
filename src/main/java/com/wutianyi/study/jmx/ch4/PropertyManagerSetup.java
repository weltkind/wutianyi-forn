package com.wutianyi.study.jmx.ch4;

import javax.management.ObjectName;

import com.sun.jdmk.comm.RmiConnectorClient;
import com.wutianyi.study.jmx.RMIClientFactory;

public class PropertyManagerSetup
{
    public PropertyManagerSetup()
    {
        try
        {
            RmiConnectorClient client = RMIClientFactory.getClient();
            ObjectName propertyName = new ObjectName("JMXBookAgent:name=property");
            client.createMBean("com.wutianyi.study.jmx.ch4.PropertyManager", propertyName);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
}
