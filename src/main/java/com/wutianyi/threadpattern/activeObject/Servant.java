package com.wutianyi.threadpattern.activeObject;

public class Servant {

	public Result makeString(int count, char fillchar) {
		char[] buffer = new char[count];
		
		for(int i = 0; i < count; i ++) {
			buffer[i] = fillchar;
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new RealResult(new String(buffer));
	}

	public void displayString(String string) {
		try {
			System.out.println("displayString: " + string);
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
