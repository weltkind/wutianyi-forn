package com.wutianyi.study.mysqlslap.valuegenerator.impl;

import org.apache.commons.lang.RandomStringUtils;

import com.wutianyi.study.mysqlslap.configuration.ColumnsType;
import com.wutianyi.study.mysqlslap.valuegenerator.ValueGenerator;

/**
 * @author hanjiewu 字符串类型数值创建
 */
public class VarcharGenerator implements ValueGenerator
{

    private ColumnsType type;
    private int length;

    /**
     * 重复的默认值
     */
    private String duplicateValue;

    public VarcharGenerator(ColumnsType _type, int _length)
    {
        this.type = _type;
        this.length = _length;
        duplicateValue = RandomStringUtils.randomAscii(length);
    }

    public Object generateValue()
    {
        if (ColumnsType.UNIQUE.equals(type))
        {
            return "'" + RandomStringUtils.random(length, true, true) + "'";

        }
        else if (ColumnsType.EMPTY.equals(type))
        {
            return null;
        }

        return "'" + duplicateValue + "'";
    }

    public static void main(String[] args)
    {
        VarcharGenerator generator = new VarcharGenerator(ColumnsType.DUPLICATE, 20);
        for (int i = 0; i < 10; i++)
        {
            System.out.println(generator.generateValue());
        }
    }
}
