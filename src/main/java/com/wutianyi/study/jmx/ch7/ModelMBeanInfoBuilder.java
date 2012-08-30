package com.wutianyi.study.jmx.ch7;

import java.lang.reflect.Constructor;
import java.util.Hashtable;
import java.util.Vector;

import javax.management.Descriptor;
import javax.management.MBeanParameterInfo;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;

public class ModelMBeanInfoBuilder
{
    protected Hashtable attributes = new Hashtable();
    protected Hashtable notifications = new Hashtable();
    protected Hashtable constructors = new Hashtable();
    protected Hashtable operations = new Hashtable();

    public ModelMBeanInfoBuilder()
    {

    }

    public void addModelMBeanMethod(String name, String[] paramTypes, String[] paramNames, String[] paramDescs,
            String description, String rtype, int type, Descriptor desc)
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
        operations.put(name, new ModelMBeanOperationInfo(name, description, params, rtype, type, desc));
    }

    public void addModelMBeanNotification(String[] type, String className, String description, Descriptor desc)
    {
        notifications.put(className, new ModelMBeanNotificationInfo(type, className, description, desc));
    }

    public void addModelMBeanAttribute(String fname, String ftype, boolean read, boolean write, boolean is,
            String description, Descriptor desc)
    {
        attributes.put(fname, new ModelMBeanAttributeInfo(fname, ftype, description, read, write, is, desc));
    }

    public void addModelMBeanConstructor(Constructor c, String description, Descriptor desc)
    {
        constructors.put(c, new ModelMBeanConstructorInfo(description, c, desc));
    }

    public ModelMBeanInfo buildModelMBeanInfo(Descriptor desc)
    {
        ModelMBeanOperationInfo[] ops = new ModelMBeanOperationInfo[operations.size()];
        copyInto(ops, operations);

        ModelMBeanAttributeInfo[] atts = new ModelMBeanAttributeInfo[attributes.size()];
        copyInto(atts, attributes);

        ModelMBeanConstructorInfo[] cons = new ModelMBeanConstructorInfo[constructors.size()];
        copyInto(cons, constructors);

        ModelMBeanNotificationInfo[] notifs = new ModelMBeanNotificationInfo[notifications.size()];
        copyInto(notifs, notifications);

        return new ModelMBeanInfoSupport("javax.management.modelmbean,ModelMBeanInfo", "description", atts, cons, ops,
                notifs, desc);
    }

    public Descriptor buildAttributeDescriptor(String name, String displayName, String persistPolicy,
            String persistPeriod, Object defaultValue, String setter, String getter, String currency)
    {
        Descriptor desc = new DescriptorSupport();

        if (name != null)
        {
            desc.setField("name", name);
            desc.setField("descriptorType", "attribute");
        }

        if (displayName != null)
        {
            desc.setField("diplayName", displayName);
        }

        if (null != getter)
        {
            desc.setField("getMethod", getter);
        }

        if (null != setter)
        {
            desc.setField("setMethod", setter);
        }

        if (null != currency)
        {
            desc.setField("currentTimeLimit", currency);
        }

        if (null != persistPolicy)
        {
            desc.setField("persistPolicy", persistPolicy);
        }

        if (null != persistPeriod)
        {
            desc.setField("persistPeriod", persistPeriod);
        }
        if (null != defaultValue)
        {
            desc.setField("default", defaultValue);
        }
        return desc;
    }

    public Descriptor buildOperationDescriptor(String name, String displayName, String role, Object targetObject,
            Object targetType, String ownerClass, String currency)
    {
        Descriptor desc = new DescriptorSupport();
        if (name != null)
            desc.setField("name", name);

        desc.setField("descriptorType", "operation");
        if (displayName != null)
            desc.setField("displayName", displayName);
        if (role != null)
            desc.setField("role", role);
        if (targetObject != null)
            desc.setField("targetObject", targetObject);
        if (targetType != null)
            desc.setField("targetType", targetType);
        if (ownerClass != null)
            desc.setField("class", ownerClass);
        if (currency != null)
            desc.setField("currencyTimeLimit", currency);

        return desc;
    }

    public Descriptor buildMBeanDescriptor(String name, String displayName, String persistPolicy, String persistPeriod,
            String persistLocation, String persistName, String log, String logFile)
    {
        Descriptor desc = new DescriptorSupport();
        if (name != null)
            desc.setField("name", name);
        desc.setField("descriptorType", "mbean");
        if (displayName != null)
            desc.setField("displayName", displayName);
        if (persistLocation != null)
            desc.setField("persistLocation", persistLocation);
        if (persistName != null)
            desc.setField("persistName", persistName);
        if (log != null)
            desc.setField("log", log);
        if (persistPolicy != null)
            desc.setField("persistPolicy", persistPolicy);
        if (persistPeriod != null)
            desc.setField("persistPeriod", persistPeriod);
        if (logFile != null)
            desc.setField("logFile", logFile);

        return desc;
    }

    private void copyInto(Object[] array, Hashtable table)
    {
        Vector temp = new Vector(table.values());
        temp.copyInto(array);
    }
}
