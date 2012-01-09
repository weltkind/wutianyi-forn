package com.wutianyi.qq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class KeyWordAnalyzer
{

    public static void main(String[] args) throws IOException
    {

        File[] files = new File[]
        { new File("E:\\shuju\\txl\\58"), new File("E:\\shuju\\txl\\59") };
        Map<String, Map<String, Node>> results = new HashMap<String, Map<String, Node>>();
        for (File f : files)
        {
            File[] fs = f.listFiles();
            for (File file : fs)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
                System.out.println(file.getAbsolutePath());
                String line = reader.readLine();
                while (StringUtils.isNotBlank(line))
                {
                    String ss[] = line.split("INFO");
                    line = reader.readLine();
                    if (null != ss && ss.length == 2)
                    {
                        ss = ss[1].trim().split("\t");
                        String platform = ss[0];

                        if (ss.length == 2)
                        {
                            continue;
                        }
                        String count = ss[ss.length - 1];
                        String keyword = "";
                        for (int i = 1; i < ss.length - 1; i++)
                        {
                            keyword += ss[i];
                            if (i != ss.length - 2)
                            {
                                keyword += "\t";
                            }
                        }

                        Map<String, Node> ks = results.get(platform);
                        if (null == ks)
                        {
                            ks = new HashMap<String, Node>();
                            results.put(platform, ks);
                        }
                        Node n = ks.get(keyword);
                        if (null == n)
                        {
                            n = new Node();
                            n.setName(keyword);
                            n.setResult(Integer.parseInt(count));
                            n.setPlatform(platform);
                            ks.put(keyword, n);
                        }
                        if(n.getResult() == 0)
                        {
                            n.setResult(Integer.parseInt(count));
                        }
                        n.setCount(n.getCount() + 1);

                    }
                }
                reader.close();
            }
        }
        Map<String, List<Node>> mapR = new HashMap<String, List<Node>>();

        for (Entry<String, Map<String, Node>> entry : results.entrySet())
        {
            List<Node> nodes = new ArrayList<Node>();
            for (Entry<String, Node> e : entry.getValue().entrySet())
            {
                nodes.add(e.getValue());
            }
            mapR.put(entry.getKey(), nodes);
        }
        for (Entry<String, List<Node>> entry : mapR.entrySet())
        {
            PrintWriter pw = new PrintWriter(new File(entry.getKey() + ".csv"), "utf-8");
            pw.println("搜索词,搜索次数,搜索结果");
            List<Node> list = entry.getValue();
            Collections.sort(list, new Comparator<Node>()
            {

                @Override
                public int compare(Node o1, Node o2)
                {
                    if (o1.getCount() < o2.getCount())
                    {
                        return 1;
                    }
                    if (o1.getCount() > o2.getCount())
                    {
                        return -1;
                    }
                    return 0;
                }
            });
            System.out.println("平台：" + entry.getKey());
            for (Node n : list)
            {
                pw.println(n.getName() + "," + n.getCount() + "," + n.getResult());
            }
            pw.flush();
            pw.close();
        }
    }

}
