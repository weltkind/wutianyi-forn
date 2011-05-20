package com.wutianyi.study.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class A {
	
	private Ainter ainter;

	public Ainter getAinter() {
		return ainter;
	}
	@Autowired
	public void setAinter(Ainter ainter) {
		this.ainter = ainter;
	}
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/com/wutianyi/study/annotation/trademanger.xml");
	}
	
}
