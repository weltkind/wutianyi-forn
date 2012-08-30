package com.wutianyi.study.jmx.ch4;

import java.sql.Connection;

public interface DBSourceMBean
{
    public void resetDataSource(String name);

    public void setAutoCommit(boolean commit);
    
    public boolean getAutoCommit();
    
    public Connection getConnection();
}
