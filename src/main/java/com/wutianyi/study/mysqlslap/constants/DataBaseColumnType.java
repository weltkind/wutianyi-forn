package com.wutianyi.study.mysqlslap.constants;

public enum DataBaseColumnType {

	INT("INT"), VARCHAR("VARCHAR"), DATETIME("DATETIME");
	
	private String type;
	
	private DataBaseColumnType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean equal(String _type) {
		return type.equals(_type);
	}
}
