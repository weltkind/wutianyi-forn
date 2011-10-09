package com.wutianyi.reflect;

import java.lang.reflect.InvocationTargetException;

import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.invoker.Invoker;

import com.wutianyi.study.ibatis.myibatis.MyTypeHandler;

public class ReflectMain
{
    public static void main(String[] args)
    {
        Reflector reflector = Reflector.forClass(A.class);
        ReflectMain m = new ReflectMain();
        A t = m.new A();
        String[] properties = reflector.getSetablePropertyNames();
        
        for(String property : properties)
        {
            Invoker invoker = reflector.getSetInvoker(property);
            try
            {
                Object[] parameter = new Object[]{MyTypeHandler.stringToType("1", reflector.getSetterType(property))};
                invoker.invoke(t, parameter);
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(t.get_a() + " : " + t.get_b());
    }

    class A
    {
        private String _a;
        private int _b;

        public String get_a()
        {
            return _a;
        }

        public void set_a(String _a)
        {
            this._a = _a;
        }

        public int get_b()
        {
            return _b;
        }

        public void set_b(int _b)
        {
            this._b = _b;
        }

    }
}
