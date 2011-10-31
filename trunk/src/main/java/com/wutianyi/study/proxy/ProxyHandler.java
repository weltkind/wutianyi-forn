package com.wutianyi.study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author hanjiewu 实现代理类调用到委托类的调用，所以一般会包含实际的委托类
 */
public class ProxyHandler implements InvocationHandler
{

    /**
     * 实际执行的对象
     */
    private MyProxyInterface target;

    public ProxyHandler(MyProxyInterface target)
    {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {

        String result = "Hi " + method.invoke(target, args) + " welcome to proxy!";
        return result;
    }

}
