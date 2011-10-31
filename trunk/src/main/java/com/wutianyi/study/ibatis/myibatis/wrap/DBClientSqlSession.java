package com.wutianyi.study.ibatis.myibatis.wrap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.wutianyi.study.ibatis.myibatis.SimpleRowSet;

public class DBClientSqlSession implements SqlSession
{
    private Configuration configuration;
    private Map<String, MessageFormat> messageFormats = new ConcurrentHashMap<String, MessageFormat>();
    private DataSource dataSource;

    private final static Object[] NULL_OBJECT_ARRAY = new Object[0];

    public DBClientSqlSession(DataSource dataSource, Configuration configuration)
    {
        this.dataSource = dataSource;
        this.configuration = configuration;
    }

    @Override
    public Object selectOne(String statement)
    {
        return selectOne(statement, null);
    }

    @Override
    public Object selectOne(String statement, Object parameter)
    {
        List<?> list = selectList(statement, parameter);

        if (null != list && list.size() == 1)
        {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List selectList(String statement)
    {
        return selectList(statement, null);
    }

    @Override
    public List selectList(String statement, Object parameter)
    {
        return selectList(statement, parameter, RowBounds.DEFAULT);
    }

    @Override
    public List selectList(String statement, Object parameter, RowBounds rowBounds)
    {
        MessageFormat messageFormat = getMessageFormat(statement, parameter);
        Connection conn = null;
        try
        {
            conn = dataSource.getConnection();
            SimpleRowSet rs = executeQuery(messageFormat.format(changeParameters(parameter)), conn);
            List<ResultMap> resultMaps = configuration.getMappedStatement(statement).getResultMaps();
            if (null == resultMaps || resultMaps.size() == 0)
            {
                return null;
            }
            return DefaultObjectFactory.handleResultSets(rs, resultMaps.get(0).getType());
        }
        catch (SQLException e)
        {
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
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
    public void select(String statement, Object parameter, ResultHandler handler)
    {

    }

    @Override
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler)
    {

    }

    @Override
    public int insert(String statement)
    {
        return insert(statement, null);
    }

    @Override
    public int insert(String statement, Object parameter)
    {
        MessageFormat messageFormat = getMessageFormat(statement, parameter);
        Connection conn = null;
        Statement sm = null;
        try
        {
            conn = dataSource.getConnection();
            sm = conn.createStatement();
            return sm.executeUpdate(messageFormat.format(changeParameters(parameter)));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                sm.close();
                conn.close();
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return 0;
    }

    @Override
    public int update(String statement)
    {
        return update(statement, null);
    }

    @Override
    public int update(String statement, Object parameter)
    {
        return 0;
    }

    @Override
    public int delete(String statement)
    {
        return delete(statement, null);
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

    }

    @Override
    public void commit(boolean force)
    {

    }

    @Override
    public void rollback()
    {

    }

    @Override
    public void rollback(boolean force)
    {

    }

    @Override
    public void close()
    {

    }

    @Override
    public void clearCache()
    {

    }

    @Override
    public Configuration getConfiguration()
    {
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
        return null;
    }

    private MessageFormat getMessageFormat(String statement, Object parameter)
    {
        MessageFormat messageFormat = messageFormats.get(statement);

        if (null == messageFormat)
        {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            String sql = mappedStatement.getBoundSql(parameter).getSql();
            messageFormat = new MessageFormat(sql);
            messageFormats.put(statement, messageFormat);
        }
        return messageFormat;
    }

    private Object[] changeParameters(Object obj)
    {
        Object[] parameters = null;
        if (null == obj)
        {
            return NULL_OBJECT_ARRAY;
        }
        if (!obj.getClass().equals(Object[].class))
        {
            if (obj.getClass().equals(String.class))
            {

                parameters = new Object[]
                { "'" + obj.toString().replace("'", "\'") + "'" };
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
                    parameters[i] = "'" + _obj[i].toString().replace("'", "\'") + "'";
                }
                else
                {
                    parameters[i] = _obj[i].toString();
                }
            }
        }
        return parameters;
    }
}
