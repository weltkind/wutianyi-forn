package com.wutianyi.study.spring.aop.example;

public class ServiceExampleImpl implements ServiceExample{

	public void testAop(String message) {
		System.out.println(message);
		System.out.println("I am the real target!");
	}
	
	public static void main(String[] args) {
		
	}
}
