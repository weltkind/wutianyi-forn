package com.wutianyi.study.jmx.ch4;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBSource implements DBSourceMBean
{

    private DataSource ds = null;
    private boolean commit = false;

    public DBSource(String JNDIName)
    {
        try
        {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(JNDIName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void resetDataSource(String name)
    {
        try
        {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(name);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setAutoCommit(boolean commit)
    {
        this.commit = commit;
    }

    @Override
    public boolean getAutoCommit()
    {
        return commit;
    }

    @Override
    public Connection getConnection()
    {
        Connection conn = null;
        try
        {
            conn = ds.getConnection();
            conn.setAutoCommit(commit);
            return conn;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
