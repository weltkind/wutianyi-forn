package com.wutianyi.study.vcard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class SplitMain
{
    public static void main(String[] args) throws IOException
    {
        List<String> contents = FileUtils.readLines(new File(Main.class.getResource(
                "/com/wutianyi/study/vcard/split.txt").getFile()));
        String[] l1 = splitStr(contents.get(0));
        String[] l2 = splitStr(contents.get(1));
        for(int i = 0; i < l1.length; i ++)
        {
            System.out.println(l1[i] + " : " + l2[i]);
        }
        String[] groups = "12-06-06-导入1 ::: 12-06-05-导入2 ::: 测试 ::: 测试_2 ::: 测试_3 ::: 测试_4 ::: 测试_5 ::: 我的家人 ::: 我的同事朋友".split(" ::: ");
        for(String g : groups)
        {
            System.out.println(g);
        }
    }

    private static String[] splitStr(String str)
    {
        boolean inline = false;
        List<String> strs = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            switch (c)
            {
            case '"':
            {
                inline = !inline;
                break;
            }
            case ',':
            {
                if (!inline)
                {
                    strs.add(builder.toString());
                    builder.delete(0, builder.length());
                }
                else
                {
                    builder.append(c);
                }
                break;
            }
            default:
                builder.append(c);
            }
        }
        if (builder.length() > 0)
        {
            strs.add(builder.toString());
        }
        return strs.toArray(new String[0]);
    }
}
