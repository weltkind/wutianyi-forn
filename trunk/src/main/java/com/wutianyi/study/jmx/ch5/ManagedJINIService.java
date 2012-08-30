package com.wutianyi.study.jmx.ch5;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ManagedJINIService extends Remote
{
    public void addEntries(Vector entry) throws RemoteException;

    public void modifyEntries(Vector oldEntries, Vector newEntries) throws RemoteException;
}
