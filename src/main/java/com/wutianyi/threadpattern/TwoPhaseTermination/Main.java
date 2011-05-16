package com.wutianyi.threadpattern.TwoPhaseTermination;

/**
 * Two-Phase Termination---快把玩具收拾好，去睡觉吧
 * @author hanjie.wuhj
 *
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("main: BEGIN");
		
		try{
			CountupThread t = new CountupThread();
			t.start();
			
			Thread.sleep(10000);
			
			System.out.println("main: shutDownRequested");
			t.shutDownRequest();
			System.out.println("main: join");
			t.join();
		} catch(InterruptedException e) {
			
		}
		System.out.println("main: END");
	}
}
