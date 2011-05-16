package com.wutianyi.study.ibatis.oracle.dao;

import java.text.MessageFormat;

import org.junit.Test;

public class MyTest {
	
	@Test
	public void test() {
		
	}
	
	public static void main(String[] args) {
		MessageFormat messageFormat = new MessageFormat("{0},{1}");
		messageFormat.format(new Object[]{new String("test"), new Integer(1)});
	}
}
