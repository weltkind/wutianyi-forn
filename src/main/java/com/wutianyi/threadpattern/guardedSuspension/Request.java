package com.wutianyi.threadpattern.guardedSuspension;

public class Request {
	
	private final String name;
	
	public Request(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "[ Request " + name + " ]";
	}
}
