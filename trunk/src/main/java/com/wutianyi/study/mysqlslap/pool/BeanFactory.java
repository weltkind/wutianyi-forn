package com.wutianyi.study.mysqlslap.pool;

/**
 * @author hanjiewu
 *工厂类
 * @param <T>
 */
public interface BeanFactory<T>
{
    T createBean();
}
