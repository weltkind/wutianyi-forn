package com.wutianyi.study.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringInit {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void init() {
		System.out.println(name);
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("biz/spring/wutianyi.xml");
		TestSpringInit testSpringInit = (TestSpringInit) context.getBean("testSpringInit");
		System.out.println(testSpringInit.getName());
	}
}
