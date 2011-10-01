package com.wutianyi.study.mysqlslap.pool.beanfactory.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.wutianyi.study.mysqlslap.pool.BeanFactory;

/**
 * @author hanjiewu
 *数据库连接工厂类
 */
public class ConnectionBeanFactory implements BeanFactory<Connection>
{

    private String url = "jdbc:mysql://localhost:3306/";
    private String username = "root";
    private String password = "860728";

    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Connection createBean()
    {
        Connection conn = null;

        try
        {
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException
    {
        ConnectionBeanFactory factory = new ConnectionBeanFactory();
        Connection conn = factory.createBean();

        if (!conn.isClosed())
        {
            System.out.println("no");
        }
        conn.close();
        if (conn.isClosed())
        {
            System.out.println("yes");
        }
        conn.setAutoCommit(true);

        if (conn.isClosed())
        {
            System.out.println("yes");
        }

    }

}
