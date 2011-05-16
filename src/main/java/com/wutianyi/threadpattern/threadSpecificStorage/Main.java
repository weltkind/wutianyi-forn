package com.wutianyi.threadpattern.threadSpecificStorage;

/**
 * Thread-Specific storage --每个线程的保管箱 
 * @author hanjie.wuhj
 *
 */
public class Main {
	
	public static void main(String[] args) {
		new ClientThread("Alice").start();
		new ClientThread("Bobby").start();
		new ClientThread("Chris").start();
	}
}
