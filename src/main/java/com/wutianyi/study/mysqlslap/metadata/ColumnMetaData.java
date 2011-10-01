package com.wutianyi.study.mysqlslap.metadata;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @author hanjiewu
 *列的元数据
 */
public class ColumnMetaData implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -4851923309873611889L;
    /**
     * 列名称
     */
    private String name;
    /**
     * 列的长度
     */
    private int columnSize;
    /**
     * 是否是空的
     */
    private boolean isNullable;
    /**
     * 列的数据类型
     */
    private String typeName;

    /**
     * @param _columnSize
     * @param _isNullable
     */
    public ColumnMetaData(String _name, int _columnSize, boolean _isNullable, String typeName)
    {
        if (StringUtils.isBlank(_name))
        {
            throw new RuntimeException("[The column name can be blank!]");
        }

        this.name = _name;
        this.columnSize = _columnSize;
        this.isNullable = _isNullable;
        this.typeName = typeName;
    }

    public String getName()
    {
        return name;
    }

    public int getColumnSize()
    {
        return columnSize;
    }

    public boolean isNullable()
    {
        return isNullable;
    }

    public String getTypeName()
    {
        return typeName;
    }

    @Override
    public boolean equals(Object obj)
    {

        if (null != obj && obj instanceof ColumnMetaData)
        {
            ColumnMetaData columnMetaData = (ColumnMetaData) obj;
            return name.equals(columnMetaData.name);

        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

}
