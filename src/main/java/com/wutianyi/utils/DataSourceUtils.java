package com.wutianyi.utils;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.wutianyi.study.net.mapper.NetMapper;

public class DataSourceUtils
{
    public static SqlSessionFactory sqlSessionFactory;

    static
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/study");
        dataSource.setUser("root");
        dataSource.setPassword("860728");

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("develop", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(NetMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    /**
     * 测试环境为了方便使用，实际中需要对session进行处理
     * @param mapper
     * @return
     */
    public static <T> T getMapper(Class<T> mapper)
    {
        SqlSession session = sqlSessionFactory.openSession();
        // 直接指定mapper中的sql
        // session.selectOne("sql", null);
        T m = session.getMapper(mapper);
        return m;
    }
}
