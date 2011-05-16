package com.wutianyi.threadpattern.TwoPhaseTermination;

/**
 * 增加shutdown的处理线程，在程序结束之后，运行相应的线程进行处理工作
 * @author hanjie.wuhj
 *
 */
public class AddShutDownHookMain {
	public static void main(String[] args) {
		System.out.println("main: BEGIN");

		// 在程序退出之后会运行线程进行相应的后续处理工作
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("*****");
				System.out.println(Thread.currentThread().getName()
						+ ": SHUNDOWN HOOK!");
				System.out.println("*****");
			}
		});
		System.out.println("main:SLEEP ...");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("main: EXIT");

		System.exit(0);
		System.out.println("main: END");
	}
}
