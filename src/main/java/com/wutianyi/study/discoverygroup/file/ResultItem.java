package com.wutianyi.study.discoverygroup.file;

public class ResultItem {

	/**
	 * 说明是那个文件
	 */
	int fType;

	int line;

	/**
	 * 相对于另一个文件的类型 1:共有 2:独有 3:他有
	 */
	int type;

	public ResultItem() {

	}

	public ResultItem(int _fType, int _line, int _type) {
		this.fType = _fType;
		this.line = _line;
		this.type = _type;
	}

}
