package com.wutianyi.study.ibatis.myibatis.wrap;

import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.wutianyi.study.ibatis.myibatis.wrap.dataobject.SoftInfoDTO;
import com.wutianyi.study.ibatis.myibatis.wrap.mapper.SoftInfoMapper;

public class WrapTest
{
    private DBClientSqlSession sqlSession;

    @Before
    public void setUp()
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/study");
        dataSource.setUser("root");
        dataSource.setPassword("860728");
        dataSource.setEncoding("utf-8");
        Configuration configuration = new Configuration();
        configuration.addMapper(SoftInfoMapper.class);
        sqlSession = new DBClientSqlSession(dataSource, configuration);
    }

    @Test
    public void testSoftInfoMapper()
    {
        SoftInfoMapper mapper = sqlSession.getMapper(SoftInfoMapper.class);
        mapper.addProductInfo(new Object[]
        { "手机管家", "手机管家" });
        mapper.addResourceInfo(new Object[]
        { "http://baidu.com", "android版本" });
        mapper.addSoftInfo(new Object[]
        { 1, 1, "Android2.3", "Android", "gf", "Android版本" });
    }

    @Test
    public void testSoftInfoMapperList()
    {
        SoftInfoMapper mapper = sqlSession.getMapper(SoftInfoMapper.class);
        List<SoftInfoDTO> softInfos = mapper.listAllSoftInfo();
        if (null != softInfos)
        {
            for(SoftInfoDTO softInfo : softInfos)
            {
                printlnSoftInfo(softInfo);
            }
        }
    }

    private void printlnSoftInfo(SoftInfoDTO softInfo)
    {
        System.out.println("id: " + softInfo.getId());
        System.out.println("g_f: " + softInfo.getG_f());
        System.out.println("name: " + softInfo.getName());
        System.out.println("product_id: " + softInfo.getProduct_id());
        System.out.println("resource_id: " + softInfo.getResource_id());
        System.out.println("s_description: " + softInfo.getS_description());
        System.out.println("gmt_create: " + softInfo.getGmt_create());
        System.out.println("gmt_modified: " + softInfo.getGmt_modified());

    }
}
