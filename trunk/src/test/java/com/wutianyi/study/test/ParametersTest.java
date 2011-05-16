package com.wutianyi.study.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 参数化测试
 * 1.需要制定runwith的类型
 * 2.定义public static Collection 方法
 * 3.给参数赋值
 * @author hanjie.wuhj
 * 
 */
@RunWith(Parameterized.class)
public class ParametersTest {

	private int param;
	private int result;

	@Parameters
	public static Collection data() {
		Object[][] data = new Object[][] { { 1, 2 }, { 3, 4 } };
		return Arrays.asList(data);
	}
	
	public ParametersTest(int param, int result) {
		this.param = param;
		this.result = result;
	}
	
	@Test
	public void printlnPR() {
		System.out.println(param + " : " + result);
	}

	public static void main(String[] args) {
		
	}

}
