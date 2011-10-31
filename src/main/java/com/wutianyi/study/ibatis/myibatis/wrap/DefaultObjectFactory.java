package com.wutianyi.study.ibatis.myibatis.wrap;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.invoker.Invoker;

import com.wutianyi.study.ibatis.myibatis.SimpleRowSet;
import com.wutianyi.study.ibatis.myibatis.TypeHandler;

public class DefaultObjectFactory
{

    public static <T> List<T> handleResultSets(SimpleRowSet rs, Class<T> type)
    {
        List<T> results = new ArrayList<T>();
        try
        {
            while (rs.next())
            {
                T rowValue = create(type);
                Reflector reflector = Reflector.forClass(type);

                String[] columns = reflector.getGetablePropertyNames();
                for (String column : columns)
                {
                    Invoker invoker = reflector.getSetInvoker(column);
                    Object args = TypeHandler.stringToType(column, reflector.getSetterType(column), rs);
                    invoker.invoke(rowValue, new Object[]
                    { args });

                }
                results.add(rowValue);
            }
            return results;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static <T> T create(Class<T> type)
    {
        try
        {
            Constructor<T> constructor = type.getDeclaredConstructor();
            // true，指示反射对象在使用的时候应该取消java语言的访问检查
            if (!constructor.isAccessible())
            {
                constructor.setAccessible(true);
            }
            return constructor.newInstance();
        }
        catch (Exception e)
        {
        }

        return null;
    }
}
