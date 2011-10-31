package com.wutianyi.study.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MysqlTest
{
    private static MysqlDataSource dataSource;

    static
    {
        dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/study");
        dataSource.setUser("root");
        dataSource.setPassword("860728");
    }
    /**
     * @throws SQLException
     */
    @Test
    public void testResultSetMetaData() throws SQLException
    {
        Connection conn = dataSource.getConnection();
        Statement sm = conn.createStatement();
        ResultSet rs = sm.executeQuery("select id,old_value as oldValue,new_value as newValue from logger");
        ResultSetMetaData rsmd = rs.getMetaData();
        
        int count = rsmd.getColumnCount();
        for(int i = 1; i <= count; i ++)
        {
            System.out.println(rsmd.getColumnName(i));
        }
    }
    public static void main(String[] args)
    {
        
    }

}
