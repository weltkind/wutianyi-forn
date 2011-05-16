package com.wutianyi.threadpattern.balking;

import java.io.IOException;

public class SaverThread extends Thread{
	
	private Data data;
	
	public SaverThread(String name, Data data) {
		super(name);
		this.data = data;
	}
	
	@Override
	public void run() {
		try{
			while(true) {
				data.save();
				Thread.sleep(1000);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
