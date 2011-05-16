package com.wutianyi.logger.utils;

public enum LoggerType {
	INSERT("insert"), DELETE("delete"), UPDATE("update"), OTHER("other");
	
	private String type;
	
	private LoggerType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
