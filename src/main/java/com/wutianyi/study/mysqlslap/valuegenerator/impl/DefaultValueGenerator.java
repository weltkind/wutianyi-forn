package com.wutianyi.study.mysqlslap.valuegenerator.impl;

import com.wutianyi.study.mysqlslap.valuegenerator.ValueGenerator;

/**
 * @author hanjiewu
 *默认的值创建实例
 */
public class DefaultValueGenerator implements ValueGenerator
{

    public Object generateValue()
    {
        return 1;
    }

}
