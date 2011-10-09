package com.wutianyi.study.ibatis.myibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPropertyTokenizer
{
    public static void main(String[] args)
    {
        Class<temp> clz = temp.class;
        Field[] fields = clz.getDeclaredFields();
        Method[] methods = clz.getMethods();
        for (Field field : fields)
        {
            System.out.println(field.getName());
        }
        for(Method method : methods)
        {
            StringBuilder builder = new StringBuilder();
            builder.append(method.getName());
            Class<?>[] parameters = method.getParameterTypes();
            for (int i = 0; i < parameters.length; i++)
            {
                if (i == 0)
                {
                    builder.append(":");
                }
                else
                {
                    builder.append(',');
                }
                builder.append(parameters[i].getName());
            }
            System.out.println(builder.toString());
        }
        
    }

    class temp
    {
        List<String> list = new ArrayList<String>();
        Map<String, String> map = new HashMap<String, String>();
        String[] strs = new String[5];
        int id;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public List<String> getList()
        {
            return list;
        }

        public void setList(List<String> list)
        {
            this.list = list;
        }

        public Map<String, String> getMap()
        {
            return map;
        }

        public void setMap(Map<String, String> map)
        {
            this.map = map;
        }

        public String[] getStrs()
        {
            return strs;
        }

        public void setStrs(String[] strs)
        {
            this.strs = strs;
        }

    }
}
