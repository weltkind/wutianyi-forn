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

}
