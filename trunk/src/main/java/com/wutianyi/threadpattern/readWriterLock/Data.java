package com.wutianyi.threadpattern.readWriterLock;

public class Data {
	
	private final char[] buffer;
	private final ReadWriterLock lock = new ReadWriterLock();
	
	public Data(int size) {
		this.buffer = new char[size];
		for(int i = 0; i < buffer.length; i ++) {
			buffer[i] = '*';
		}
	}
	
	public char[] read() throws InterruptedException {
		lock.readLock();
		try {
			return doRead();
		} finally {
			lock.readUnlock();
		}
		
	}
	
	public void writer(char c) {
		lock.writeUnlock();
		
		try{
			doWriter(c);
		} finally {
			lock.writeUnlock();
		}
	}
	
	private void doWriter(char c) {
		for(int i = 0; i < buffer.length; i ++) {
			buffer[i] = c;
			slowly();
		}
	}
	
	private char[] doRead() {
		char[] newbuf = new char[buffer.length];
		
		for(int i = 0; i < buffer.length; i ++) {
			newbuf[i] = buffer[i];
		}
		slowly();
		return newbuf;
	}
	
	private void slowly() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
