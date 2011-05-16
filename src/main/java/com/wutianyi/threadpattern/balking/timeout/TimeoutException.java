package com.wutianyi.threadpattern.balking.timeout;

public class TimeoutException extends InterruptedException{
	
	public TimeoutException(String msg) {
		super(msg);
	}
}
