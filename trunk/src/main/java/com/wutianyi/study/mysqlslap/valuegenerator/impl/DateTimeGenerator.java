package com.wutianyi.study.mysqlslap.valuegenerator.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wutianyi.study.mysqlslap.valuegenerator.ValueGenerator;

/**
 * @author hanjiewu 时间类型创建器
 */
public class DateTimeGenerator implements ValueGenerator
{

    public Object generateValue()
    {
        // DateFormat 是非线程安全的
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
        // 为了符合mysql字符串的格式
        return "'" + dateFormat.format(new Date()) + "'";
    }

}
