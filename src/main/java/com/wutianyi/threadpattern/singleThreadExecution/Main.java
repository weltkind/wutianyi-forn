package com.wutianyi.threadpattern.singleThreadExecution;

/**
 * Single Thread Execution --能通过这座桥的，只有一个人
 * @author hanjie.wuhj
 *
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("Testing Gate, hit GRTL+C to exit.");
		Gate gate = new Gate();
		new UserThread(gate, "Alice", "Alaska").start();
		new UserThread(gate, "Bobby", "Brazil").start();
		new UserThread(gate, "Chris", "Canada").start();
	}
}
