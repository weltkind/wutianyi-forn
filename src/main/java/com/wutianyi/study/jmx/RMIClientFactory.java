package com.wutianyi.study.jmx;

import com.sun.jdmk.comm.RmiConnectorAddress;
import com.sun.jdmk.comm.RmiConnectorClient;

public class RMIClientFactory
{

    public static RmiConnectorClient getClient()
    {
        RmiConnectorClient client = new RmiConnectorClient();
        RmiConnectorAddress address = new RmiConnectorAddress();
        address.setPort(2099);
        client.connect(address);
        return client;
    }
}
