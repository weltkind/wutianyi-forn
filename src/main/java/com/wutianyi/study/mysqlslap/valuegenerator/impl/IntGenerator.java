package com.wutianyi.study.mysqlslap.valuegenerator.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.wutianyi.study.mysqlslap.configuration.ColumnsType;
import com.wutianyi.study.mysqlslap.valuegenerator.ValueGenerator;

public class IntGenerator implements ValueGenerator {

	private ColumnsType type;
	private AtomicInteger valueGenerator;

	public IntGenerator(ColumnsType _type, int length) {
		this.type = _type;
		int initValue = 1;

		for (int i = 1; i < length; i++) {
			initValue = initValue * 10;
		}
		System.out.println(initValue);
		valueGenerator = new AtomicInteger(initValue);
	}

	public Object generateValue() {
		if (ColumnsType.AUTOINCREMENT.equals(type)
				|| ColumnsType.UNIQUE.equals(type)) {
			return valueGenerator.getAndIncrement();
		} else if (ColumnsType.DUPLICATE.equals(type)) {
			return valueGenerator.get();
		}

		return null;
	}
	
	public static void main(String[] args) {
		IntGenerator generator = new IntGenerator(ColumnsType.UNIQUE, 10);
		
		for(int i = 0; i < 10; i ++) {
			System.out.println(generator.generateValue());
		}
	}
}
