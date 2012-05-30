package com.wutianyi.study.vcard;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import scala.actors.threadpool.Arrays;

public class MainCompare
{
    public static void main(String[] args) throws Exception
    {
        List<String> content1 = FileUtils.readLines(new File(Main.class.getResource(
                "/com/wutianyi/study/vcard/resource1.txt").getFile()));
        List<String> content2 = FileUtils.readLines(new File(Main.class.getResource(
                "/com/wutianyi/study/vcard/resource2.txt").getFile()));

        int len = content1.size();
        int len2 = content2.size();
        for (int i = 0; i < len; i++)
        {
            String c = content1.get(i);
            String[] cs = c.split(",");

            Set<String> s = toSet(cs);

            if (cs.length != s.size())
            {
                throw new Exception("1相同的头部");
            }

            if (len2 > i)
            {
                String c2 = content2.get(i);
                String[] cs2 = c2.split(",");
                Set<String> s2 = toSet(cs2);
                if (cs.length != s.size())
                {
                    throw new Exception("2相同的头部");
                }
                System.out.println("2不包含1的头部：");
                for (String t : s)
                {

                    if (!s2.contains(t))
                    {
                        System.out.println(t);
                    }
                }
                System.out.println("1不包含2的头部");
                for (String t : s2)
                {

                    if (!s.contains(t))
                    {
                        System.out.println(t);
                    }
                }
            }

        }
    }

    private static Set<String> toSet(String[] ss)
    {
        Set<String> set = new HashSet<String>();
        for (String s : ss)
        {
            set.add(s);
        }
        return set;
    }
}
