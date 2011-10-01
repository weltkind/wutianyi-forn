package com.wutianyi.study.mysqlslap.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanjie.wuhj 表的元信息
 */
public class TableMetaData implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -4682105321204255470L;

    /**
     * 主键
     */
    private List<String> primaryKeys;

    /**
     * 每列对应的元信息
     */
    private Map<String, ColumnMetaData> columnMetaDatas;

    private Collection<ColumnMetaData> columnsName;

    private List<String> names;

    public TableMetaData()
    {
        columnMetaDatas = new HashMap<String, ColumnMetaData>();
        primaryKeys = new ArrayList<String>();
        columnsName = columnMetaDatas.values();
        names = new ArrayList<String>();
    }

    /**
     * 
     * @param _columnsName
     * @return
     */
    public boolean isRepeatable(List<String> _columnsName)
    {

        return !_columnsName.containsAll(primaryKeys);
    }

    /**
     * @param column
     * @param size
     * @return 长度验证
     */
    public boolean lengthValidator(String column, int size)
    {
        ColumnMetaData columneMetaData = columnMetaDatas.get(column);

        if (null == columneMetaData || size <= 0)
        {
            return false;
        }
        return columneMetaData.getColumnSize() >= size ? true : false;

    }

    /**
     * 
     * @param columnMetaData
     */
    public void addColumnMetaData(ColumnMetaData columnMetaData)
    {
        if (null == columnMetaData)
        {
            return;
        }
        if (!containColumnName(columnMetaData.getName()))
        {
            names.add(columnMetaData.getName());
            columnMetaDatas.put(columnMetaData.getName(), columnMetaData);
        }
    }

    /**
     * ���primaryKey
     * 
     * @param primaryKeyName
     * @param columnName
     */
    public void addPrimaryKey(String columnName)
    {

        if (!primaryKeys.contains(columnName))
        {
            primaryKeys.add(columnName);
        }

    }

    /**
     * 
     * @param columnNames
     * @return
     */
    public boolean containAllColumnNames(List<String> _columnsName)
    {
        return names.containsAll(_columnsName);
    }

    public boolean containColumnName(String columnName)
    {
        return names.contains(columnName);
    }

    /**
     * 
     * @param columnName
     * @return
     */
    public boolean isNullable(String columnName)
    {

        ColumnMetaData column = columnMetaDatas.get(columnName);

        if (null != column)
        {
            return column.isNullable();
        }

        return false;
    }

    public List<String> getPrimaryKeys()
    {
        return Collections.unmodifiableList(primaryKeys);
    }

    public Collection<ColumnMetaData> getColumnsName()
    {
        return Collections.unmodifiableCollection(columnsName);
    }

    /**
     * 
     * @param columnsName
     * @return
     */
    public List<ColumnMetaData> intersectColumnsMetaData(List<String> columnsName)
    {
        List<ColumnMetaData> columnsMetaData = new ArrayList<ColumnMetaData>();

        for (ColumnMetaData columnMetaData : this.columnsName)
        {
            if (!columnsName.contains(columnMetaData.getName()))
            {
                columnsMetaData.add(columnMetaData);
            }
        }

        return columnsMetaData;
    }

    public ColumnMetaData getColumnMetaData(String columnName)
    {
        return columnMetaDatas.get(columnName);
    }

    public static void main(String[] args)
    {
        List<String> l = new ArrayList<String>();
        l.add("test_1");
        List<String> l_2 = Collections.unmodifiableList(l);
        System.out.println(l);
        System.out.println(l_2);
        l.add("test_2");
        System.out.println(l);
        System.out.println(l_2);
        l_2.add("test_3");
    }
}
