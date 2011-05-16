package com.wutianyi.threadpattern.threadPerMessage;

/**
 * Thread-Per-Message 这个工作就交给你了
 * 
 * @author hanjie.wuhj
 * 
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		
		Host host = new Host();
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
