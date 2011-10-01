package com.wutianyi.study.mysqlslap.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hanjiewu 需要生成的表数据的配置信息
 */
public class TableConfiguration implements Serializable
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -7558249055374366336L;
    /**
     * 数据库
     */
    String databaseName;
    /**
     * 表
     */
    String tableName;
    /**
     * 生成的sql数目
     */
    int sqlNums = 0;
    /**
     * 列的信息
     */
    List<ColumnConfiguration> columnsConfig;
    /**
     * null值的列
     */
    List<ColumnConfiguration> nullColumns;
    /**
     * 重复值的列
     */
    List<ColumnConfiguration> duplicateColumns;
    /**
     * 列的名称
     */
    List<String> columnsName;

    public void addColumnConfiguration(ColumnConfiguration columnConfiguration)
    {
        if (null == columnConfiguration)
        {
            return;
        }
        columnsConfig.add(columnConfiguration);
    }

    public void setDatabaseName(String databaseName)
    {
        this.databaseName = databaseName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public void setColumnsConfig(List<ColumnConfiguration> columnsConfig)
    {
        this.columnsConfig = columnsConfig;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public String getTableName()
    {
        return tableName;
    }

    public List<ColumnConfiguration> getColumnsConfig()
    {
        return columnsConfig;
    }

    public List<ColumnConfiguration> getNullColumns()
    {
        if (null == nullColumns)
        {
            nullColumns = new ArrayList<ColumnConfiguration>();
            for (ColumnConfiguration c : columnsConfig)
            {
                if (ColumnsType.EMPTY.equal(c.getType().getType()))
                {
                    nullColumns.add(c);
                }
            }
        }

        return nullColumns;
    }

    public List<ColumnConfiguration> getDuplicateColumns()
    {
        if (null == duplicateColumns)
        {
            duplicateColumns = new ArrayList<ColumnConfiguration>();

            for (ColumnConfiguration c : columnsConfig)
            {
                if (ColumnsType.DUPLICATE.equal(c.getType().getType()))
                {
                    duplicateColumns.add(c);
                }
            }
        }

        return duplicateColumns;
    }

    public int getSqlNums()
    {
        return sqlNums;
    }

    public void setSqlNums(int sqlNums)
    {
        this.sqlNums = sqlNums;
    }

    public List<String> getColumnsName()
    {
        if (null == columnsName)
        {
            columnsName = new ArrayList<String>();
            for (ColumnConfiguration columns : columnsConfig)
            {
                columnsName.add(columns.getColumnName());
            }
        }

        return columnsName;
    }

}
