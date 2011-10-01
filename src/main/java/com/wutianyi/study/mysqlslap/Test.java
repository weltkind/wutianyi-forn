package com.wutianyi.study.mysqlslap;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Test {
	public static void main(String[] args) throws InterruptedException {

		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		long[] ids = threadMXBean.getAllThreadIds();

		for (long id : ids) {
			System.out.println(id);
		}
		System.out.println("-----------------");

		Thread thread = new Thread(new Runnable() {

			public void run() {
				System.out.println("start thread");
			}
		});
		thread.setName("yes");
		System.out.println(thread.getId());
		thread.start();
		

		System.out.println("--------------------");

		ids = threadMXBean.getAllThreadIds();
		for (long id : ids) {
			System.out.println(id);
		}

		System.out.println("---------------");
		Thread.sleep(1000);
		ids = threadMXBean.getAllThreadIds();
		for (long id : ids) {
			System.out.println(id);
		}
	}
}
