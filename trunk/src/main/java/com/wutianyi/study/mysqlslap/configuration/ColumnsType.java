package com.wutianyi.study.mysqlslap.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum ColumnsType implements Serializable{
	UNIQUE("unique"), DUPLICATE("duplicate"), EMPTY("empty"), AUTOINCREMENT(
			"autoincrement");

	private static Map<String, ColumnsType> columnsTypeMap;

	static {
		columnsTypeMap = new HashMap<String, ColumnsType>();
		for (ColumnsType columnType : ColumnsType.values()) {
			columnsTypeMap.put(columnType.getType(), columnType);
		}
	}

	private String type;

	private ColumnsType(String _type) {
		this.type = _type;
	}

	public String getType() {
		return type;
	}

	public boolean equal(String type) {
		return getType().equals(type);
	}

	public static ColumnsType getColumnTypeByType(String _type) {
		return columnsTypeMap.get(_type);
	}
}
