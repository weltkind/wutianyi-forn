package com.wutianyi.study.net.bo;

import org.apache.ibatis.session.SqlSession;

import com.wutianyi.study.net.dataobject.HiddenNode;
import com.wutianyi.study.net.dataobject.HiddenUrl;
import com.wutianyi.study.net.dataobject.WordHidden;
import com.wutianyi.study.net.mapper.NetMapper;
import com.wutianyi.utils.DataSourceUtils;

public class BusinessBO
{
    public static void generatehiddennode(int[] wordids, int[] urls)
    {
        SqlSession session = DataSourceUtils.sqlSessionFactory.openSession();
        NetMapper mapper = session.getMapper(NetMapper.class);
        if (null == wordids || null == urls || wordids.length > 3)
        {
            return;
        }
        String createKey = joinInt(wordids);
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

    private static String joinInt(int[] wordids)
    {
        String r = "";
        for (int i : wordids)
        {
            r += i + "_";
        }
        return r.substring(0, r.length() - 1);
    }

    public static void main(String[] args)
    {
        generatehiddennode(new int[]
        { 2, 3, 4 }, new int[]
        { 5, 6, 7 });
    }
}
