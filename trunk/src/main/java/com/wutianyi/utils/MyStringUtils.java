package com.wutianyi.utils;

import org.apache.commons.lang.StringUtils;

public class MyStringUtils extends StringUtils
{
    public static int convertInt(String str, int defaultValue)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch (Exception e)
        {

        }
        return defaultValue;
    }

    public static int convertInt(Object obj, int defaultValue)
    {
        if (null == obj || !(obj instanceof Integer))
        {
            return defaultValue;
        }
        return (Integer) obj;
    }
}
