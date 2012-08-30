package com.wutianyi.study.jmx.ch5;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;

public class DynamicMBeanSupport implements DynamicMBean
{

    protected MBeanInfo mbeanInfo = null;
    protected Hashtable attributes = new Hashtable();
    protected Hashtable notifications = new Hashtable();
    protected Hashtable constructors = new Hashtable();
    protected Hashtable operations = new Hashtable();

    protected String description = "Description of the MBean";

    public DynamicMBeanSupport()
    {
        addMBeanAttribute("description", "java.lang.String", true, true, false, "Description of the MBean");
        addMBeanConstructor(this.getClass().getConstructors()[0], "Default Constructor");
    }

    private void addMBeanConstructor(Constructor<?> constructor, String desc)
    {
        this.constructors.put(constructor, new MBeanConstructorInfo(desc, constructor));
    }

    private void addMBeanAttribute(String string, String string2, boolean b, boolean c, boolean d, String string3)
    {
        // TODO Auto-generated method stub

    }

    protected void addMBeanOperation(String name, String[] paramTypes, String[] paramNames, String[] paramDescs,
            String desc, String rtype, int type)
    {
        MBeanParameterInfo[] params = null;
        if (null != paramTypes)
        {
            params = new MBeanParameterInfo[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++)
            {
                params[i] = new MBeanParameterInfo(paramNames[i], paramTypes[i], paramDescs[i]);
            }
        }
        operations.put(name, new MBeanOperationInfo(name, desc, params, rtype, type));

    }

    @Override
    public Object getAttribute(String name) throws AttributeNotFoundException, MBeanException, ReflectionException
    {
        try
        {
            Class c = this.getClass();
            Method m = c.getDeclaredMethod("get" + name, null);
            return m.invoke(this, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AttributeList getAttributes(String[] names)
    {
        AttributeList list = new AttributeList();
        for (int i = 0; i < names.length; i++)
        {
            try
            {
                list.add(new Attribute(names[i], this.getAttribute(names[i])));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo()
    {
        try
        {
            buildDynamicMBeanInfo();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mbeanInfo;
    }

    private void buildDynamicMBeanInfo()
    {
        MBeanOperationInfo[] ops = new MBeanOperationInfo[operations.size()];
        copyInto(ops, operations);
        MBeanAttributeInfo[] atts = new MBeanAttributeInfo[attributes.size()];
        copyInto(atts, attributes);
        MBeanConstructorInfo[] cons = new MBeanConstructorInfo[constructors.size()];
        copyInto(cons, constructors);
        mbeanInfo = new MBeanInfo(this.getClass().getName(), description, atts, cons, ops, null);
    }

    private void copyInto(Object[] ops, Hashtable table)
    {
        Vector temp = new Vector(table.values());
        temp.copyInto(ops);
    }

    @Override
    public Object invoke(String method, Object[] args, String[] types) throws MBeanException, ReflectionException
    {
        try
        {
            Class c = this.getClass();
            Class sig[] = null;
            if (null != types)
            {
                sig = new Class[types.length];
                for (int i = 0; i < types.length; i++)
                {
                    sig[i] = Class.forName(types[i]);
                }
            }
            Method m = c.getDeclaredMethod(method, sig);
            Object returnObject = m.invoke(this, args);
            return returnObject;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException,
            MBeanException, ReflectionException
    {
        String fname = attribute.getName();
        Object fvalue = attribute.getValue();
        try
        {
            Class c = this.getClass();
            String type = getTypes(fname, false, true);
            if (null == type)
            {
                throw new AttributeNotFoundException(fname);
            }
            Class[] types =
            { Class.forName(type) };
            Method m = c.getDeclaredMethod("set" + fname, types);
            Object[] args =
            { fvalue };
            m.invoke(this, args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String getTypes(String fname, boolean read, boolean write)
    {
        boolean allowed = true;
        if (attributes.containsKey(fname))
        {
            MBeanAttributeInfo temp = (MBeanAttributeInfo) attributes.get(fname);
            if (read)
            {
                if (!temp.isReadable())
                {
                    allowed = false;
                }
            }
            if (write)
            {
                if (!temp.isWritable())
                {
                    allowed = false;
                }
            }
            if (!allowed)
            {
                return null;
            }
            else
            {
                temp.getType();
            }
        }
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributeList)
    {
        Attribute[] atts = (Attribute[]) attributeList.toArray();
        AttributeList list = new AttributeList();
        for (int i = 0; i < atts.length; i++)
        {
            Attribute a = atts[i];
            try
            {
                this.setAttribute(a);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return attributeList;
    }

}
