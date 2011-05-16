package com.wutianyi.threadpattern.balking.timeout;

/**
 * balking 的变形，可以在等待一定时间之间在检查是否满足警戒条件，如果不满足再做处理
 * @author hanjie.wuhj
 *
 */
public class Main {
	public static void main(String[] args) {
		Host host = new Host(10000);
		System.out.println("execute BEGIN");
		try {
			host.execute();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
