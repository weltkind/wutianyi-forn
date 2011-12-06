package com.wutianyi.study.search;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

/**
 * @author hanjiewu
 * 
 * @param <T>T需要构建自己的equals方法
 */
public class IndexSearch<T>
{

    /**
     * 保存索引
     */
    private Map<String, List<Integer>> indexs;

    /**
     * 实际保存对象
     */
    private Map<Integer, T> objs;

    /**
     * 用于分词
     */
    private IKSegmentation ikSegementation;

    /**
     * 保存当前的一个索引
     */
    private int index;

    /**
     * 默认搜索的时候只处理三个单词，当没有结果的时候才进行下面的处理
     */
    private final int KEY_SEARCH_LIMIT = 5;

    public IndexSearch()
    {
        indexs = new HashMap<String, List<Integer>>();
        ikSegementation = new IKSegmentation(new StringReader(StringUtils.EMPTY), true);
        String ss = new String("扣费,手机管家");
        List<String> extendWords = Arrays.asList(ss.split(","));
                
        Dictionary.getInstance().loadExtendWords(extendWords);
        objs = new HashMap<Integer, T>();
    }

    /**
     * 都构建完成之后才使用与搜索 添加的使用只使用一个分词器
     * 
     * @param content
     *            需要分析的内容
     * @param obj
     */
    public void addIndexs(String content, T obj)
    {
        if (StringUtils.isBlank(content) || null == obj)
        {
            return;
        }
        // 对分词器进行同步
        synchronized (ikSegementation)
        {
            ikSegementation.reset(new StringReader(content));

            StringBuilder builder = new StringBuilder(content + " :");
            try
            {
                objs.put(index, obj);
                Lexeme lexeme = ikSegementation.next();
                while (null != lexeme)
                {
                    List<Integer> is = indexs.get(lexeme.getLexemeText());
                    builder.append(" | " + lexeme.getLexemeText());
                    if (null == is)
                    {
                        is = new ArrayList<Integer>();
                        indexs.put(lexeme.getLexemeText(), is);
                    }
                    is.add(index);
                    lexeme = ikSegementation.next();
                }
                index ++;
                System.out.println(builder.toString());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据key进行搜索
     * 
     * @param key
     * @return
     */
    public List<T> search(String key, int keyWrodLimit)
    {
        if (StringUtils.isBlank(key) || keyWrodLimit < 1)
        {
            return Collections.EMPTY_LIST;
        }
        IKSegmentation ikSegementation = new IKSegmentation(new StringReader(key));
        try
        {
            Lexeme lexeme = ikSegementation.next();
            List<T> results = new ArrayList<T>();
            Map<Integer, Set<Integer>> sorts = new HashMap<Integer, Set<Integer>>(keyWrodLimit, 2);
            int i = 0;
            int counts = 1;
            StringBuilder builder = new StringBuilder(key + " : ");
            while (null != lexeme && (i < keyWrodLimit || sorts.size() == 0))
            {
                List<Integer> is = indexs.get(lexeme.getLexemeText());
                builder.append(" | " + lexeme.getLexemeText());
                System.out.print("关键词：" + lexeme.getLexemeText() + ": 匹配结果： ");
                if (null != is && is.size() > 0)
                {
                    setResults(sorts, is, counts);
                    counts++;
                }
                System.out.println();
                ++i;
                lexeme = ikSegementation.next();
            }
            System.out.println(builder.toString());
            for (int j = counts; j > 0; j--)
            {
                Set<Integer> set = sorts.get(j);
                if (null != set && set.size() > 0)
                {
                    for (Integer s : set)
                    {
                        results.add(objs.get(s));
                    }
                }
            }
            return results;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public List<T> search(String keyword)
    {
        return search(keyword, KEY_SEARCH_LIMIT);
    }

    /**
     * @param sorts
     * @param indexs
     * @param counts
     *            表示第几次结果的添加
     */
    private void setResults(Map<Integer, Set<Integer>> sorts, List<Integer> indexs, int counts)
    {
        if (null == indexs || indexs.size() == 0)
        {
            return;
        }
        if (1 == counts)
        {
            Set<Integer> set = new HashSet<Integer>();
            sorts.put(1, set);
            for (Integer index : indexs)
            {
                System.out.print(objs.get(index) + " | ");
                set.add(index);
            }
            return;
        }
        List<Integer> t = new ArrayList<Integer>(indexs);
        for (int i = counts - 1; i > 0; i--)
        {
            Set<Integer> set = sorts.get(i);
            for (Iterator<Integer> itr = t.iterator(); itr.hasNext();)
            {
                int index = itr.next();
                System.out.print(objs.get(index) + "");
                if (set.contains(index))
                {
                    Set<Integer> nextL = sorts.get(i + 1);
                    System.out.print("(从" + i + " 生到 " + (i + 1) + ") | ");
                    if (null == nextL)
                    {
                        nextL = new HashSet<Integer>();
                        sorts.put(i + 1, nextL);
                    }
                    set.remove(index);
                    nextL.add(index);
                    itr.remove();
                }
                else if (i == 1)
                {
                    set.add(index);
                }
            }

        }
    }

    public static void main(String[] args)
    {
        String[] contents = new String[]
        { "QQ手机管家是什么？", "QQ手机管家包含了哪些功能？", "QQ手机管家需要收费吗？", "使用手机QQ管家的IP拨号功能会产生流量吗？", "使用手机QQ管家的归属地查询会产生流量吗？",
                "请问手机管家中设置呼叫转移后，是否要扣费？", "如何下载QQ手机管家？", "手机机型不适配软件会产生什么问题？", "下载安装软件时，提示证书过期？", "QQ手机管家现在支持的手机平台有哪些？" };
        IndexSearch<String> indexSearch = new IndexSearch<String>();
        for(String content : contents)
        {
            indexSearch.addIndexs(content, content);
        }
        List<String> results = indexSearch.search("手机防盗");
        for(String r : results)
        {
            System.out.println(r);
        }
    }
}
