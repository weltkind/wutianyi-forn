package com.wutianyi.study.ibatis.mapper.deep;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * @author hanjiewu
 *插件，可以用于修改底层的核心行为
 */
@Intercepts(
{ @Signature(type = Executor.class, method = "update", args =
{ MappedStatement.class, Object.class }) })
public class LoggerPlugin implements Interceptor
{

    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties)
    {
        // TODO Auto-generated method stub

    }

}
