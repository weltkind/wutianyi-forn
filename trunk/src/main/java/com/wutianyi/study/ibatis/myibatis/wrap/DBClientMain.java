package com.wutianyi.study.ibatis.myibatis.wrap;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.Configuration;

import com.wutianyi.study.ibatis.myibatis.LoggerMapper;

public class DBClientMain
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
        DBClientSqlSession sqlSession = new DBClientSqlSession(dataSource, configuration);
        LoggerMapper mapper = sqlSession.getMapper(LoggerMapper.class);
        List<LoggerDO> loggers = mapper.listAllLogger(2);
        LoggerDO logger = mapper.fetchLogger(new Object[]{9, "mC4jHuz9dQ"});
        System.out.println(loggers.size());
        System.out.println(logger.getNew_value());
    }
}
