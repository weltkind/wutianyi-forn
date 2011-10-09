package com.wutianyi.study.ibatis.myibatis;

public class MyTypeHandler
{
    public static <T> Object stringToType(String parameter, Class<T> clz)
    {
        try
        {
            if (clz.equals(Integer.class) || clz.equals(int.class))
            {
                return Integer.parseInt(parameter);
            }
            if (clz.equals(String.class))
            {
                return parameter;
            }
            if (clz.equals(Float.class) || clz.equals(float.class))
            {
                return Float.parseFloat(parameter);
            }
            if (clz.equals(Short.class) || clz.equals(short.class))
            {
                return Short.parseShort(parameter);
            }
            if (clz.equals(Boolean.class) || clz.equals(boolean.class))
            {
                return Boolean.parseBoolean(parameter);
            }
            if (clz.equals(Double.class) || clz.equals(double.class))
            {
                return Double.parseDouble(parameter);
            }
            if (clz.equals(Long.class) || clz.equals(long.class))
            {
                return Long.parseLong(parameter);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
