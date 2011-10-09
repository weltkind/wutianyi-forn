package com.wutianyi.study.ibatis.mapper.deep;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author hanjiewu
 *类型处理器
 */
public class LoggerTypeHandler implements TypeHandler
{

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException
    {
        ps.setString(i, (String) parameter);
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException
    {
        return rs.getString(columnName);
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException
    {
        return cs.getString(columnIndex);
    }

}
