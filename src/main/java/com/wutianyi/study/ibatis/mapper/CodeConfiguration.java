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
import com.wutianyi.study.net.dataobject.HiddenNode;
import com.wutianyi.study.net.mapper.NetMapper;

/**
 * @author hanjiewu 代码中直接初始化SqlSessionFactory
 */
public class CodeConfiguration
{
    public static void main(String[] args)
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/study");
        dataSource.setUser("root");
        dataSource.setPassword("860728");
        
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("develop", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(NetMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        SqlSession session = sqlSessionFactory.openSession();
        //直接指定mapper中的sql
//        session.selectOne("sql", null);
        NetMapper mapper = session.getMapper(NetMapper.class);
        HiddenNode node = new HiddenNode("test_1");
        mapper.insertHiddenNode(node);
        System.out.println(node.getId());
        session.close();
        
    }
}