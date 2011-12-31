package com.wutianyi.study.net.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.wutianyi.study.net.dataobject.HiddenNode;
import com.wutianyi.study.net.dataobject.HiddenUrl;
import com.wutianyi.study.net.dataobject.WordHidden;
import com.wutianyi.study.net.mapper.NetMapper;
import com.wutianyi.utils.DataSourceUtils;

public class BusinessBO
{

    public static float getStrenghtByUrlId(int urlId, int hiddenId)
    {
        SqlSession session = DataSourceUtils.sqlSessionFactory.openSession();
        NetMapper mapper = session.getMapper(NetMapper.class);
        Float f = mapper.getStrenghtByUrlId(urlId, hiddenId);
        session.close();
        if (null == f)
        {
            return 0.0f;
        }
        else
        {
            return f;
        }
    }
    public static float getStrenghtByWordId(int wordId, int hiddenId)
    {
        SqlSession session = DataSourceUtils.sqlSessionFactory.openSession();
        NetMapper mapper = session.getMapper(NetMapper.class);
        Float f = mapper.getStrenghtByWordId(wordId, hiddenId);
        session.close();
        if (null == f)
        {
            return 0.0f;
        }
        else
        {
            return f;
        }
    }

    public static int[] getHiddenIds(int[] wordIds, int[] urlIds)
    {
        SqlSession session = DataSourceUtils.sqlSessionFactory.openSession();
        NetMapper mapper = session.getMapper(NetMapper.class);
        String key = joinInt(wordIds, ',');
        List<Integer> hiddenIds = mapper.getHiddenIdsByWord(key);
        if (null == hiddenIds)
        {
            hiddenIds = new ArrayList<Integer>();
        }
        key = joinInt(urlIds, ',');
        List<Integer> hus = mapper.getHiddenIdsByUrl(key);
        if (null != hus)
        {
            hiddenIds.addAll(hus);
        }
        int[] r = new int[hiddenIds.size()];
        int i = 0;
        for (int h : hiddenIds)
        {
            r[i] = h;
            i++;
        }
        session.commit();
        session.close();
        return r;
    }

    public static void generatehiddennode(int[] wordids, int[] urls)
    {
        SqlSession session = DataSourceUtils.sqlSessionFactory.openSession();
        NetMapper mapper = session.getMapper(NetMapper.class);
        if (null == wordids || null == urls || wordids.length > 3)
        {
            return;
        }
        String createKey = joinInt(wordids, '_');
        Integer res = mapper.getHiddenNodeId(createKey);
        if (null == res)
        {
            HiddenNode node = new HiddenNode(createKey);
            mapper.insertHiddenNode(node);
            for (int id : wordids)
            {
                mapper.insertWordHidden(new WordHidden(id, node.getId(), 1.0f / wordids.length));
            }
            for (int url : urls)
            {
                mapper.insertHiddenUrl(new HiddenUrl(node.getId(), url, 0.1f));
            }
        }
        session.commit();
        session.close();
    }

    private static String joinInt(int[] wordids, char joinChar)
    {
        String r = "";
        for (int i : wordids)
        {
            r += i + joinChar;
        }
        return r.substring(0, r.length() - 1);
    }

    public static void main(String[] args)
    {
        // generatehiddennode(new int[]
        // { 2, 3, 4 }, new int[]
        // { 5, 6, 7 });
    }
}
