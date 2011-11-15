package com.wutianyi.cesu.analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.wutianyi.utils.MyStringUtils;

/**
 * 构建解析树
 * @author hanjiewu
 *
 */
public class Node
{
    /**
     * 表示flag的值
     */
    int flag;

    /**
     * 名称
     */
    String name;

    /**
     * 总的秒数
     */
    int sum = 0;

    /**
     * 总共的请求
     */
    int count = 0;

    /**
     * 下个节点
     */
    Map<Integer, Node> next;

    /**
     * 下个name的节点
     */
    Map<String, Node> sNext;

    public Node()
    {
        next = new HashMap<Integer, Node>();
    }

    public Node(int _flag)
    {
        this.flag = _flag;
        next = new HashMap<Integer, Node>();
        sNext = new HashMap<String, Node>();
    }

    public Node(String _name)
    {
        this.name = _name;
        sNext = new HashMap<String, Node>();
    }

    public Node addNode(int flag)
    {
        Node node = next.get(flag);
        if (null == node)
        {
            node = new Node(flag);
        }
        next.put(flag, node);
        return node;
    }

    public Node addNode(String name)
    {
        Node node = sNext.get(name);
        if (null == node)
        {
            node = new Node(name);
        }
        sNext.put(name, node);
        return node;
    }

    public void addNode(String name, int time)
    {
        Node node = sNext.get(name);
        if (null == node)
        {
            node = new Node(name);
        }
        node.addTime(time);
        sNext.put(name, node);
    }

    public void addTime(int time)
    {
        sum += time;
        count++;
    }

    public int getFlag()
    {
        return flag;
    }

    public String getName()
    {
        return name;
    }

    public int getSum()
    {
        return sum;
    }

    public int getCount()
    {
        return count;
    }

    public Map<Integer, Node> getNext()
    {
        return next;
    }

    public Map<String, Node> getsNext()
    {
        return sNext;
    }

    public static void main(String[] args) throws IOException
    {
        File file = new File("file");
        File[] files = file.listFiles();
        int sum = 0;
        int count = 0;
        for (File f : files)
        {
            if (f.getName().startsWith("record"))
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String line = reader.readLine();
                while (StringUtils.isNotBlank(line))
                {
                    ++count;
                    int v = MyStringUtils.convertInt(line.substring(line.lastIndexOf('=') + 1), 0);
                    sum += v;
                    line = reader.readLine();
                }
                reader.close();
            }
        }
        System.out.println(sum);
        System.out.println(count);
        System.out.println(sum / count);
    }
}
