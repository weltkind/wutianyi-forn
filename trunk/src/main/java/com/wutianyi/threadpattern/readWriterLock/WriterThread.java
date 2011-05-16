package com.wutianyi.threadpattern.readWriterLock;

import java.util.Random;

public class WriterThread extends Thread{

	private static final Random random = new Random();
	private final Data data;
	private final String filter;
	private int index = 0;
	
	public WriterThread(Data data, String filter) {
		this.data = data;
		this.filter = filter;
	}
	
	public void run() {
		try{
			while(true) {
				char c = nextChar();
				data.writer(c);
				Thread.sleep(random.nextInt(3000));
			}
		} catch(InterruptedException e) {
			
		}
	}

	private char nextChar() {
		char c = filter.charAt(index);
		index ++;
		if(index >= filter.length()) {
			index = 0;
		}
		return c;
	}
}
