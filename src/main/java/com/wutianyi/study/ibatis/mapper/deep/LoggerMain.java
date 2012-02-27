package com.wutianyi.study.ibatis.mapper.deep;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class LoggerMain
{
    public static void main(String[] args) throws IOException
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/work");
        dataSource.setUsername("root");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPassword("860728");
//        String resource = "mybatis/mybatis-config.xml";
//        Reader reader = Resources.getResourceAsReader(resource);
//        SqlSessionFactory _sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(LoggerMapper.class);
        
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        LoggerMapper mapper = sqlSession.getMapper(LoggerMapper.class);
        List<LoggerDO> loggers = mapper.listAllLogger(1000000000);
        printlnLogger(loggers);
        sqlSession.close();
    }

    private static void printlnLogger(List<LoggerDO> loggers)
    {
        for (LoggerDO logger : loggers)
        {
            System.out.println("------------------------------");
            System.out.println(logger.getId());
            System.out.println(logger.getNewValue());
            System.out.println(logger.getOldValue());
            System.out.println(logger.getGmtCreate());
            System.out.println(logger.getGmtModified());
            System.out.println(logger.getOperationType());
            System.out.println(logger.getOperator());
            System.out.println("-------------------------------");
        }
    }
}
