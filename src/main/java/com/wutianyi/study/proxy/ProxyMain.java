package com.wutianyi.study.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class ProxyMain
{
    public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        //使用动态代理的原始流程
        InvocationHandler handler = new ProxyHandler(new ProxyImpl());
        Class clazz = Proxy.getProxyClass(handler.getClass().getClassLoader(), new Class[]{MyProxyInterface.class});
        Constructor constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});
        MyProxyInterface i = (MyProxyInterface) constructor.newInstance(new Object[]{handler});
        System.out.println(i.echo());
    }
}
