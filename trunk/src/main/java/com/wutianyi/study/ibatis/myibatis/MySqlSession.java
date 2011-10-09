package com.wutianyi.study.ibatis.myibatis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

public class MySqlSession implements SqlSession
{

    private DataSource dataSource;
    private Configuration configuration;
    private MyFastResultSetHandler resultSetHandler;
    private Map<String, MessageFormat> messageFormats = new ConcurrentHashMap<String, MessageFormat>();

    public MySqlSession(DataSource _dataSource, Configuration _configuration)
    {
        this.dataSource = _dataSource;
        this.configuration = _configuration;

    }

    @Override
    public Object selectOne(String statement)
    {

        return null;
    }

    @Override
    public Object selectOne(String statement, Object parameter)
    {
        return null;
    }

    @Override
    public List selectList(String statement)
    {

        return null;
    }

    @Override
    public List selectList(String statement, Object parameter)
    {
        try
        {

            // MappedStatement ms = configuration.getMappedStatement(statement);
            // dataSource.getConnection().createStatement().executeQuery(ms.getBoundSql(null).getSql());
            String[] columns = new String[]
            { "id", "gmtCreate", "gmtModified", "oldValue", "newValue", "operationType", "operator" };
            String[][] data = new String[][]
            {
            {
                    "1000000000",
                    "2011-10-01 : 17:37:09",
                    "2011-10-01 : 17:37:09",
                    "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",
                    "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",
                    "3OuZfCgpE2LxKqgMtLXsNnpR6f9pnml6nhzGAdEeFt9xDHHheD5RSTJhBKn2GnemWQt4JXro77E2GpjxQEPHOUsyIKuJFRLZUkLUi83s5QOS6BpLVSI93PrM0AS8fjXj",
                    "6KkgBmMwG6o2hwGsoQw85fZLXj1UarT7GufU18kBGSjeaMInyXnMjOJaRerNzYKX" } };
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);

            MessageFormat messageFormat = messageFormats.get(statement);
            if (null == messageFormat)
            {
                String sql = mappedStatement.getBoundSql(parameter).getSql();
                messageFormat = new MessageFormat(sql);
                messageFormats.put(statement, messageFormat);
            }
            Connection conn = dataSource.getConnection();
           
            SimpleRowSet rs = executeQuery(messageFormat.format(changeParameters(parameter)), conn);
            conn.close();
            resultSetHandler = new MyFastResultSetHandler(mappedStatement);
            SimpleRowSet results = new SimpleRowSet(columns, data);
            List result = resultSetHandler.handleResultSets(rs);
            return result;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private Object[] changeParameters(Object obj)
    {
        Object[] parameters = null;
        if (!obj.getClass().equals(Object[].class))
        {
            if (obj.getClass().equals(String.class))
            {

                parameters = new Object[]
                { "'" + obj + "'" };
            }
            else
            {
                parameters = new Object[]
                { obj.toString() };
            }
        }
        else
        {
            Object[] _obj = (Object[]) obj;
            parameters = new Object[_obj.length];
            for (int i = 0; i < _obj.length; i++)
            {
                if (_obj[i].getClass().equals(String.class))
                {
                    parameters[i] = "'" + _obj[i] + "'";
                }
                else
                {
                    parameters[i] = _obj[i];
                }
            }
        }
        return parameters;
    }

    private SimpleRowSet executeQuery(String sql, Connection conn) throws Exception
    {
        Statement stmt = null;
        ResultSet r = null;
        try
        {
            stmt = conn.createStatement();
            r = stmt.executeQuery(sql);

            // 行数和列数
            ResultSetMetaData rsmd = r.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String[] columnNames = new String[columnCount];

            for (int i = 1; i <= columnCount; i++)
            {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }

            List<String[]> data = new ArrayList<String[]>();
            // 获得数据
            while (r.next())
            {
                String[] rowData = new String[columnCount];
                data.add(rowData);
                for (int i = 1; i <= columnCount; i++)
                {
                    rowData[i - 1] = r.getString(i);
                    if (rowData[i - 1] == null)
                    {
                        rowData[i - 1] = "`~SQL_NULL~`";
                    }
                }
            }
            String[][] array = data.toArray(new String[0][0]);
            SimpleRowSet srs = new SimpleRowSet(columnNames, array);
            return srs;
        }
        finally
        {
            if (null != r)
            {
                r.close();
            }
            if (null != stmt)
            {
                stmt.close();
            }
        }
    }

    @Override
    public List selectList(String statement, Object parameter, RowBounds rowBounds)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void select(String statement, Object parameter, ResultHandler handler)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public int insert(String statement)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int insert(String statement, Object parameter)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(String statement)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(String statement, Object parameter)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(String statement)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(String statement, Object parameter)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void commit()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void commit(boolean force)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void rollback()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void rollback(boolean force)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void close()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearCache()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Configuration getConfiguration()
    {
        // TODO Auto-generated method stub
        return configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type)
    {
        return configuration.getMapper(type, this);
    }

    @Override
    public Connection getConnection()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
