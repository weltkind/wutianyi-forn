package com.wutianyi.cesu.analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.wutianyi.cesu.dataobject.StatisticDO;
import com.wutianyi.cesu.handle.Handle;
import com.wutianyi.cesu.handle.HttpClientIpAddHandle;
import com.wutianyi.cesu.handle.NullHandle;
import com.wutianyi.utils.MyStringUtils;

/**
 * 解析文件中的每行数据
 * 
 * @author hanjiewu
 * 
 */
public class TextAnalyse
{
    private static Handle NULL_HANDLE = new NullHandle();

    final String FILE_PREFIX = "record";

    File base = null;

    /**
     * 数据进行实际处理的类
     */
    Handle handle;

    /**
     * 传入的参数不能为空
     * 
     * @param _base
     * @param _handle
     */
    public TextAnalyse(File _base, Handle _handle)
    {
        this.base = _base;
        this.handle = _handle;
        if (null == _base || !_base.exists() || !_base.isDirectory())
        {
            throw new IllegalArgumentException("");
        }
        if (null == this.handle)
        {
            this.handle = NULL_HANDLE;
        }
    }

    Node analyse()
    {
        Node node = new Node();
        File[] files = base.listFiles();
        if (null != files)
        {
            for (File file : files)
            {
                if (file.getName().startsWith(FILE_PREFIX))
                {
                    analyseFile(file, node);
                }
            }
        }
        return node;
    }

    /**
     * 遍历文件中的每行，然后解析出相应的数据
     * 
     * @param file
     * @param node
     */
    private void analyseFile(File file, Node node)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = reader.readLine();
            while (StringUtils.isNotBlank(line))
            {
                StatisticDO statistic = analyseLine(line);
                addNode(node, statistic);
                line = reader.readLine();
            }
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void addNode(Node rootNode, StatisticDO statistic)
    {
        if (statistic.getFlag1() != 0)
        {
            Node curNode = rootNode.addNode(statistic.getFlag1());
            if (statistic.getFlag2() != 0)
            {
                curNode = curNode.addNode(statistic.getFlag2());
                if (statistic.getFlag3() != 0)
                {
                    curNode = curNode.addNode(statistic.getFlag3());
                }
            }
            Set<Entry<Integer, Integer>> entrys = statistic.getTimes().entrySet();
            String pC = handle.handle(statistic.getIp());
            pC = pC.replaceAll("\r\n", "");
            String province = null;
            String city = null;
            province = pC.substring(0, pC.indexOf("-") + 1);
            city = pC.substring(pC.indexOf("-") + 1);
            if(StringUtils.isBlank(city))
            {
                city = "other";
            }
            if(StringUtils.isBlank(province))
            {
                province = "other";
            }
            for (Entry<Integer, Integer> entry : entrys)
            {
                Node node = curNode.addNode(entry.getKey());
                node = node.addNode(province);
                node.addNode(city, entry.getValue());
            }
        }
    }

    /**
     * 解析没行数据
     * 
     * @param line
     */
    private StatisticDO analyseLine(String line)
    {
        StatisticDO statistic = new StatisticDO();
        String[] ss = line.split("INFO");
        if (null != ss && ss.length == 2)
        {
            String[] r = ss[1].trim().split(" ");
            if (null != r && r.length == 2)
            {
                statistic.setIp(r[0]);
                String[] times = r[1].split("&");
                for (String t : times)
                {
                    String[] bs = t.split("=");
                    if (null != r && r.length == 2)
                    {
                        if ("flag1".equals(bs[0]))
                        {
                            statistic.setFlag1(MyStringUtils.convertInt(bs[1], -1));
                        }
                        else if ("flag2".equals(bs[0]))
                        {
                            statistic.setFlag2(MyStringUtils.convertInt(bs[1], -1));
                        }
                        else if ("flag3".equals(bs[0]))
                        {
                            statistic.setFlag3(MyStringUtils.convertInt(bs[1], -1));
                        }
                        else
                        {
                            int pageId = MyStringUtils.convertInt(bs[0], -1);
                            if (pageId > 0 && pageId < 16)
                            {
                                statistic.addTime(pageId, MyStringUtils.convertInt(bs[1], 0));
                            }
                        }
                    }
                }
            }
        }
        return statistic;
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(new File("cesu.txt"));
        TextAnalyse analyse = new TextAnalyse(new File("file"), new HttpClientIpAddHandle());
        Node node = analyse.analyse();
        for (Entry<Integer, Node> entry : node.getNext().entrySet())
        {
            System.out.print(entry.getKey());
            Node n = entry.getValue();
            if (null != n.getNext())
            {
                for (Entry<Integer, Node> entry_1 : n.getNext().entrySet())
                {
                    System.out.print("----" + entry_1.getKey());
                    Node n_1 = entry_1.getValue();
                    if (null != n_1.getNext())
                    {
                        for (Entry<Integer, Node> entry_2 : n_1.getNext().entrySet())
                        {
                            System.out.print("----" + entry_2.getKey());
                            Node n_2 = entry_2.getValue();
                            if (null != n_2.getNext())
                            {
                                for (Entry<Integer, Node> entry_3 : n_2.getNext().entrySet())
                                {
                                    System.out.print("----" + entry_3.getKey());
                                    Node n_3 = entry_3.getValue();
                                    if (null != n_3.getsNext())
                                    {
                                        for (Entry<String, Node> entry_4 : n_3.getsNext().entrySet())
                                        {
                                            System.out.print(entry_4.getKey());
                                            Node n_4 = entry_4.getValue();
                                            pw.println(entry_4.getKey());
                                            if (null != n_4.getsNext())
                                            {
                                                for (Entry<String, Node> entry_5 : n_4.getsNext().entrySet())
                                                {
                                                    System.out.println(entry_5.getKey()
                                                            + " : "
                                                            + entry_5.getValue().getSum()
                                                            + " : "
                                                            + entry_5.getValue().getCount()
                                                            + " : "
                                                            + (entry_5.getValue().getSum() / entry_5.getValue()
                                                                    .getCount()));
                                                    pw.println("\t\t"
                                                            + entry_5.getKey()
                                                            + ":\tsum: "
                                                            + entry_5.getValue().getSum()
                                                            + "\tcount: "
                                                            + entry_5.getValue().getCount()
                                                            + "\tavg:"
                                                            + (entry_5.getValue().getSum() / entry_5.getValue()
                                                                    .getCount()));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        pw.flush();
        pw.close();
        node.getClass();
    }
}
