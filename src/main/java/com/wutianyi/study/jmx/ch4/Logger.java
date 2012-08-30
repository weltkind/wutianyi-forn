package com.wutianyi.study.jmx.ch4;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author hanjiewu
 *可以获取MBeanserver的实例
 */
public class Logger implements LoggerMBean,MBeanRegistration
{

    public static final int ALL = 3;
    public static final int ERRORS = 2;
    public static final int NONE = 1;

    private PrintWriter out = null;
    private int logLevel = Logger.ALL;
    
    private MBeanServer server = null;

    public Logger()
    {
        try
        {
            out = new PrintWriter(new FileOutputStream("record.log"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setLogLevel(int level)
    {
        this.logLevel = level;
    }

    @Override
    public int getLogLevel()
    {
        return logLevel;
    }

    @Override
    public String retrieveLog(int linesback)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void writeLog(String message, int type)
    {
        try
        {
            if (type <= logLevel)
            {
                out.println(message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void postDeregister()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void postRegister(Boolean arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preDeregister() throws Exception
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ObjectName preRegister(MBeanServer server, ObjectName arg1) throws Exception
    {
        this.server = server;
        
        try
        {
            ObjectName name1 = new ObjectName("HelloAgent:name=props");
            Object[] params = {"loglevel"};
            String[] sig = {"java.lang.String"};
            String value = (String) server.invoke(name1, "getProperty", params, sig);
            logLevel = Integer.parseInt(value);
        }
        catch(Exception e)
        {
            
        }
        return null;
    }

}
