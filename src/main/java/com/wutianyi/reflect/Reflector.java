package com.wutianyi.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Reflector
{
    private static boolean classCacheEnabled = true;
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static final Map<Class<?>, Reflector> REFLECTOR_MAP = new ConcurrentHashMap<Class<?>, Reflector>();

    private Class<?> type;
    private String[] readablePropertyNames = EMPTY_STRING_ARRAY;
    private String[] writeablePropetyNames = EMPTY_STRING_ARRAY;

    private Map<String, Invoker> setMethods = new HashMap<String, Invoker>();
    private Map<String, Invoker> getMethods = new HashMap<String, Invoker>();

    private Map<String, Class<?>> setTypes = new HashMap<String, Class<?>>();
    private Map<String, Class<?>> getTypes = new HashMap<String, Class<?>>();
    private Constructor<?> defaultConstructor;

    private Map<String, String> caseInsensitivePropertyMap = new HashMap<String, String>();

    private Reflector(Class<?> clazz)
    {
        type = clazz;
        addDefaultConstructor(clazz);
        addGetMethods(clazz);
    }

    private void addGetMethods(Class<?> clazz)
    {
        Method[] methods = getClassMethods(clazz);
        for(Method method : methods)
        {
            String name = method.getName();
            if(name.startsWith("get") && name.length() > 3)
            {
                if(method.getParameterTypes().length == 0)
                {
                    
                }
            }
        }
    }

    private Method[] getClassMethods(Class<?> cls)
    {
        HashMap<String, Method> uniqueMethods = new HashMap<String, Method>();
        Class<?> currentClass = cls;
        while (currentClass != null)
        {
            addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());

            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> inter : interfaces)
            {
                addUniqueMethods(uniqueMethods, inter.getDeclaredMethods());
            }
            currentClass = currentClass.getSuperclass();
        }
        Collection<Method> methods = uniqueMethods.values();
        return methods.toArray(new Method[methods.size()]);

    }

    private void addUniqueMethods(HashMap<String, Method> uniqueMethods, Method[] methods)
    {
        for (Method method : methods)
        {
            if (!method.isBridge())
            {
                String signature = getSignature(method);
                if (!uniqueMethods.containsKey(signature))
                {
                    if (canAccessPrivateMethods())
                    {
                        method.setAccessible(true);
                    }
                    uniqueMethods.put(signature, method);
                }
            }
        }
    }

    private String getSignature(Method method)
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
        return builder.toString();
    }

    /**
     * 增加默认的构造函数
     * 
     * @param clazz
     */
    private void addDefaultConstructor(Class<?> clazz)
    {
        Constructor<?>[] consts = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : consts)
        {
            if (constructor.getParameterTypes().length == 0)
            {
                if (canAccessPrivateMethods())
                {
                    constructor.setAccessible(true);
                }
                if (constructor.isAccessible())
                {
                    this.defaultConstructor = constructor;
                }
            }
        }
    }

    private static boolean canAccessPrivateMethods()
    {
        try
        {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager)
            {
                // 检查是否有权限禁止java的语言检查机制
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        }
        catch (SecurityException e)
        {
            return false;
        }
        return true;
    }
}
