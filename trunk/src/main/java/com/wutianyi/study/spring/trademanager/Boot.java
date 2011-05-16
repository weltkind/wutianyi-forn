package com.wutianyi.study.spring.trademanager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Boot {
	
	public static void main(String[] args) {
		ApplicationContext atx = new ClassPathXmlApplicationContext("classpath:com/wutianyi/study/spring/trademanger/trademanger.xml");
		FooService fooService = (FooService) atx.getBean("fooService");
		fooService.insertFoo(new Foo());
	}
}
