package com.wutianyi.study.jmx.example;

import static java.lang.management.ManagementFactory.*;

import java.io.IOException;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class ThreadMonitor
{
    private MBeanServerConnection server;
    private ThreadMXBean tmbean;
    private ObjectName objname;

    private static String INDENT = "    ";

    private String findDeadlocksMethodName = "findDeadlockedThreads";
    private boolean canDumpLocks = true;

    public ThreadMonitor(MBeanServerConnection server) throws IOException
    {
        this.server = server;
        //
        this.tmbean = newPlatformMXBeanProxy(server, THREAD_MXBEAN_NAME, ThreadMXBean.class);
        try
        {
            objname = new ObjectName(THREAD_MXBEAN_NAME);
        }
        catch (MalformedObjectNameException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        parseMBeanInfo();
    }

    public ThreadMonitor()
    {
        this.tmbean = getThreadMXBean();
    }

    public void threadDump()
    {
        if (canDumpLocks)
        {
            if (tmbean.isObjectMonitorUsageSupported() && tmbean.isSynchronizerUsageSupported())
            {
                dumpThreadInfoWithLocks();
            }

        }
        else
        {
            dumpThreadInfo();
        }
    }

    private void dumpThreadInfo()
    {
        System.out.println("Full Java thread dump");
        long[] tids = tmbean.getAllThreadIds();
        ThreadInfo[] tinfos = tmbean.getThreadInfo(tids, Integer.MAX_VALUE);
        for (ThreadInfo ti : tinfos)
        {
            printThreadInfo(ti);
        }
    }

    private void printThreadInfo(ThreadInfo ti)
    {
        printThread(ti);
        StackTraceElement[] stackTrace = ti.getStackTrace();
        MonitorInfo[] monitors = ti.getLockedMonitors();
        for (int i = 0; i < stackTrace.length; i++)
        {
            StackTraceElement ste = stackTrace[i];
            System.out.println(INDENT + "at " + ste.toString());
            for (MonitorInfo mi : monitors)
            {
                if (mi.getLockedStackDepth() == i)
                {
                    System.out.println(INDENT + " - locked " + mi);
                }
            }
        }
        System.out.println();
    }

    private void printThread(ThreadInfo ti)
    {
        StringBuilder sb = new StringBuilder("\"" + ti.getThreadName() + "\"" + " Id=" + ti.getThreadId() + " in "
                + ti.getThreadState());
        if (ti.getLockName() != null)
        {
            sb.append(" on lock=" + ti.getLockName());
        }
        if (ti.isSuspended())
        {
            sb.append(" (suspended)");
        }
        if (ti.isInNative())
        {
            sb.append(" (running in native)");
        }
        System.out.println(sb.toString());
        if (ti.getLockOwnerName() != null)
        {
            System.out.println(INDENT + " owned by " + ti.getLockOwnerName() + " Id=" + ti.getLockOwnerId());
        }
    }

    private void dumpThreadInfoWithLocks()
    {
        System.out.println("Full Java thread dump with locks info");
        ThreadInfo[] tinfos = tmbean.dumpAllThreads(true, true);
        for (ThreadInfo ti : tinfos)
        {
            printThreadInfo(ti);
            LockInfo[] syncs = ti.getLockedSynchronizers();
            printLockInfo(syncs);
        }
        System.out.println();
    }

    private void printMonitorInfo(ThreadInfo ti, MonitorInfo[] monitors)
    {
        System.out.println(INDENT + "Locked monitors: count = " + monitors.length);
        for (MonitorInfo mi : monitors)
        {
            System.out.println(INDENT + "  - " + mi + " locked at");
            System.out.println(INDENT + "       " + mi.getLockedStackDepth() + " " + mi.getLockedStackFrame());
        }
    }

    private void printLockInfo(LockInfo[] syncs)
    {
        System.out.println(INDENT + "Locked synchronizers: count = " + syncs.length);
        for (LockInfo li : syncs)
        {
            System.out.println(INDENT + "  - " + li);
        }
        System.out.println();
    }

    public boolean findDeadlock()
    {
        long[] tids;
        if (findDeadlocksMethodName.equals("findDeadlockedThreads") && tmbean.isSynchronizerUsageSupported())
        {
            tids = tmbean.findDeadlockedThreads();
            if (tids == null)
            {
                return false;
            }
            System.out.println("Deadlock found :-");
            ThreadInfo[] infos = tmbean.getThreadInfo(tids, true, true);
            for (ThreadInfo ti : infos)
            {
                printThreadInfo(ti);
                printLockInfo(ti.getLockedSynchronizers());
                System.out.println();
            }
        }
        else
        {
            tids = tmbean.findMonitorDeadlockedThreads();
            if (tids == null)
            {
                return false;
            }
            ThreadInfo[] infos = tmbean.getThreadInfo(tids);
            for (ThreadInfo ti : infos)
            {
                printThreadInfo(ti);
            }

        }
        return true;
    }

    private void parseMBeanInfo()
    {
        try
        {
            MBeanOperationInfo[] mopis = server.getMBeanInfo(objname).getOperations();
            boolean found = false;
            for (MBeanOperationInfo op : mopis)
            {
                if (op.getName().equals(findDeadlocksMethodName))
                {
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                findDeadlocksMethodName = "findMonitorDeadlockedThreads";
                canDumpLocks = false;
            }
        }
        catch (InstanceNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IntrospectionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ReflectionException e)
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
}
