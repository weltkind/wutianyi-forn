package com.wutianyi.study.ibatis.myibatis;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.Configuration;

import com.wutianyi.study.ibatis.mapper.deep.LoggerDO;

public class MybatisMain
{
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/study");
        dataSource.setUsername("root");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPassword("860728");
        configuration.addMapper(LoggerMapper.class);
        MySqlSession sqlSession = new MySqlSession(dataSource, configuration);
        LoggerMapper mapper = sqlSession.getMapper(LoggerMapper.class);
        List<LoggerDO> loggers = mapper.listAllLogger(1000000000);
        System.out.println(loggers.size());
    }
}
