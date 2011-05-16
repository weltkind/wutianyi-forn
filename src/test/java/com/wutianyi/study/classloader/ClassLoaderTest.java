package com.wutianyi.study.classloader;

public class ClassLoaderTest {
	
	public ClassLoaderTest() {
		
	}
	
	public static void main(String[] args) {
		ClassLoaderTest classLoad = new ClassLoaderTest();
		System.out.println(classLoad.getClass().getClassLoader().getResource(""));
	}
}
