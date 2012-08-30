package com.wutianyi.reflect;

import java.lang.reflect.Field;


public class A
{
    
    private String aeca;
    private String accd;
    private String bc;
    
    public static void main(String[] args)
    {
        Field[] fields = A.class.getFields();
        for(Field f : fields)
        {
            System.out.println(f.getName());
        }
    }
}
