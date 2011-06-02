package com.wutianyi.duowan.dao;

public enum ActType {
	PRIVILEGE(2),BOOK_ISSUE(1);
	
	private int value;
	private ActType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
