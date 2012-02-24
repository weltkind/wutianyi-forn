package com.wutianyi.study.discoverygroup.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ibatis.common.resources.Resources;
import com.wutianyi.study.discoverygroup.parser.dataobject.Author;
import com.wutianyi.study.discoverygroup.parser.dataobject.Blogger;

public class BloggerMapperService
{
    private SqlSessionFactory sessionFactory;
    private String resource = "com/wutianyi/study/discoverygroup/blogger-config.xml";

    public BloggerMapperService() throws IOException
    {
        sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(resource));
    }

    public void insertAuthor(Author author)
    {
        SqlSession session = sessionFactory.openSession();
//        Map<String, Integer> dicts = new HashMap<String, Integer>();
        try
        {
            session.insert("com.wutianyi.study.discoverygroup.mapper.BloggerMapper.insertAuthor", author);
            List<Blogger> bloggers = author.getBloggers();

            for (Blogger blogger : bloggers)
            {
                blogger.setAuthorId(author.getId());
                session.insert("com.wutianyi.study.discoverygroup.mapper.BloggerMapper.insertBlogger", blogger);
            }
        }
        finally
        {
            session.commit();
            session.close();
        }
    }
}
