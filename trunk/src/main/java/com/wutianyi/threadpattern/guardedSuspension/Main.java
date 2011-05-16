package com.wutianyi.threadpattern.guardedSuspension;

/**
 * Guarded Suspension -- 要等我准备好喔
 * 等待到条件符合的时候继续执行, 这个条件就是警戒条件
 * @author hanjie.wuhj
 *
 */
public class Main {
	
	public static void main(String[] args) {
		RequestQueue requestQueue = new RequestQueue();
		new ClientThread(requestQueue, "Alice", 3141592L).start();
		new ServerThread(requestQueue, "Bobby", 6535897L).start();
	}
}
