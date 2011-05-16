package com.wutianyi.threadpattern.threadPerMessage;

public class Helper {
	
	public void handler(int count, char c) {
		System.out.println("      handle(" + count + ", " + c + ") BEGIN");
		for(int i = 0; i < count; i ++) {
			slowly();
			System.out.println(c);
		}
		System.out.println("");
		System.out.println("      handle(" + count + ", " + c + ") END");
	}
	
	private void slowly() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
