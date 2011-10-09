package com.wutianyi.study.ibatis.mapper.deep;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

/**
 * @author hanjiewu
 *结果对象创建器
 */
public class LoggerFactory extends DefaultObjectFactory
{

    @Override
    public Object create(Class type)
    {
        return super.create(type);
    }

    @Override
    public Object create(Class type, List<Class> constructorArgTypes, List<Object> constructorArgs)
    {
        return super.create(type, constructorArgTypes, constructorArgs);
    }

    @Override
    public void setProperties(Properties properties)
    {
        super.setProperties(properties);
    }

}
