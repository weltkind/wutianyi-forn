package com.wutianyi.threadpattern.immutable;

/**
 * Person 类中没有提供任何可以修改其状态的接口，
 * 所以可以不用对其进行共享互斥保护
 * @author hanjie.wuhj
 *
 */
public final class Person {
	
	private final String name;
	private final String address;
	
	public Person(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String toString() {
		return "[Person: name = " + name + ", address = " + address + " ]";
	}
	
}
