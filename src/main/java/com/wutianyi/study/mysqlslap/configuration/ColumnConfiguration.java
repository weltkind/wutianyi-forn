package com.wutianyi.study.mysqlslap.configuration;

import java.io.Serializable;

public class ColumnConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7549616761510762287L;
	private String columnName;
	private int columnSize = -1;
	private ColumnsType type;

	public ColumnConfiguration(String _columnName, int _columnSize, String _type) {
		this.columnName = _columnName;
		this.columnSize = _columnSize;
		this.type = ColumnsType.getColumnTypeByType(_type);
	}

	public String getColumnName() {
		return columnName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public ColumnsType getType() {
		return type;
	}

}
