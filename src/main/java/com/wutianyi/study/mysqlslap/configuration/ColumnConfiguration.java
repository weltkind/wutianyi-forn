package com.wutianyi.study.mysqlslap.configuration;

import java.io.Serializable;

/**
 * @author hanjiewu 每列的配置信息 程序会自动适配到相应的列类型
 */
public class ColumnConfiguration implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -7549616761510762287L;
    /**
     * 列的名称
     */
    private String columnName;
    private int columnSize = -1;
    /**
     * 列的类型
     */
    private ColumnsType type;

    public ColumnConfiguration(String _columnName, int _columnSize, String _type)
    {
        this.columnName = _columnName;
        this.columnSize = _columnSize;
        this.type = ColumnsType.getColumnTypeByType(_type);
    }

    public String getColumnName()
    {
        return columnName;
    }

    public int getColumnSize()
    {
        return columnSize;
    }

    public ColumnsType getType()
    {
        return type;
    }

}
