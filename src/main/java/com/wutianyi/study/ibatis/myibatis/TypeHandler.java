package com.wutianyi.study.ibatis.myibatis;

import java.util.Date;


public class TypeHandler
{
    public static <T> Object stringToType(String column, Class<T> clz, SimpleRowSet rs)
    {
        try
        {
            if (clz.equals(Integer.class) || clz.equals(int.class))
            {
                return rs.getInt(column);
            }
            if (clz.equals(String.class))
            {
                return rs.getString(column);
            }
            if (clz.equals(Float.class) || clz.equals(float.class))
            {
                return rs.getFloat(column);
            }
            if (clz.equals(Short.class) || clz.equals(short.class))
            {
                return rs.getShort(column);
            }
            if (clz.equals(Boolean.class) || clz.equals(boolean.class))
            {
                return rs.getBoolean(column);
            }
            if (clz.equals(Double.class) || clz.equals(double.class))
            {
                return rs.getDouble(column);
            }
            if (clz.equals(Long.class) || clz.equals(long.class))
            {
                return rs.getLong(column);
            }
            if (clz.equals(Date.class))
            {
                return rs.getDate(column);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
