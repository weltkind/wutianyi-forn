package com.wutianyi.study.mysqlslap.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;

public class SqlTemplateUtils
{

    private final static String insertSqlTemplate = "insertsql.vm";
    private final static VelocityEngine velocityEngine = new VelocityEngine();

    /**
     * @param table
     * @param columns
     * @return 创建sql的模板
     */
    public static String getInsertSqlTemplate(String table, List<String> columns)
    {
        Template template = velocityEngine.getTemplate(insertSqlTemplate);
        Context context = new VelocityContext();
        context.put("table", table);
        context.put("columns", columns);
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        sw.flush();
        return sw.toString();
    }

    public static void main(String[] args)
    {
        List<String> columns = new ArrayList<String>();
        columns.add("test_1");
        columns.add("test_2");

        List<String> values = new ArrayList<String>();
        values.add("value_1");
        values.add("value_2");

        System.out.println(getInsertSqlTemplate("product", columns));
    }
}
