package com.wutianyi.study.jmx.ch4;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertyManager implements PropertyManagerMBean
{
    private Properties props = null;

    public PropertyManager(String path)
    {
        try
        {
            props = new Properties();
            FileInputStream f = new FileInputStream(path);
            props.load(f);
            f.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getProperty(String key)
    {
        return props.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value)
    {
        props.setProperty(key, value);
    }

    @Override
    public Enumeration keys()
    {
        return props.keys();
    }

    @Override
    public void setSource(String path)
    {
        try
        {
            Properties p = new Properties();
            FileInputStream f = new FileInputStream(path);
            p.load(f);
            props = p;
            f.close();
        }
        catch (Exception e)
        {

        }

    }

}
