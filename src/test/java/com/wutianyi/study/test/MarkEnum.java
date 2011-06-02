package com.wutianyi.study.test;

public enum MarkEnum {
	Rising(0),fall(1);
	
	private int mark;
	
	private MarkEnum(int mark) {
		this.mark = mark;
	}
	
	public int getMark() {
		return mark;
	}
}
