package com.wutianyi.study.vcard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        byte[] b = FileUtils.readFileToByteArray(new File("test.csv"));
        String[] lines = split(new String(b, "GBK"));
        System.out.println(lines.length);
        for (String line : lines)
        {
            System.out.println(line);
        }
    }

    private static String[] split(String str)
    {
        if (StringUtils.isBlank(str))
        {
            return null;
        }
        int len = str.length();
        boolean inline = false;

        List<String> strs = new ArrayList<String>();

        int prev = 0;
        boolean enter = false;

        for (int i = 0; i < len; i++)
        {
            switch (str.charAt(i))
            {
            case '\r':
            {
                enter = true;
                break;
            }
            case '\n':
            {
                if (!inline)
                {
                    int l = (enter) ? i - 1 : i;
                    strs.add(str.substring(prev, l));
                    prev = i + 1;

                }
                enter = false;
                break;
            }
            case '"':
            {
                inline = !inline;
                enter = false;
                break;
            }
            default:
            {
                enter = false;
            }
            }
        }

        return strs.toArray(new String[0]);
    }

}
