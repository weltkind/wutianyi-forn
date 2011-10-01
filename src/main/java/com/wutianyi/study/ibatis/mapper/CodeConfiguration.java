package com.wutianyi.study.ibatis.mapper;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @author hanjiewu 代码中直接初始化SqlSessionFactory
 */
public class CodeConfiguration
{
    public static void main(String[] args)
    {
        DataSource dataSource = new MysqlDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("develop", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession session = sqlSessionFactory.openSession();
        //直接指定mapper中的sql
        session.selectOne("sql", null);
        session.close();
        
    }
}
