package com.wutianyi.study.discoverygroup;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public class CharacterFilterUtils
{
    public static String filter(Filter filter, String content)
    {
        if (null == filter || StringUtils.isBlank(content))
        {
            return StringUtils.EMPTY;
        }

        StringBuilder builder = new StringBuilder(content.length());
        int len = content.length();
        for (int i = 0; i < len; i++)
        {
            if (!filter.isFilter(content.charAt(i)))
            {
                builder.append(content.charAt(i));
            }
        }

        return builder.toString();
    }

    public static void main(String[] args)
    {
        char[] cs = new char[]
        { 'c', 'd', 'a' };
        System.out.println(Arrays.toString(cs));
    }
}
