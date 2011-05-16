package com.wutianyi.threadpattern.workerThread;

public class WorkerThread extends Thread{

	private final Channel channel;
	
	public WorkerThread(String string, Channel channel) {
		super(string);
		this.channel = channel;
	}
	
	@Override
	public void run() {
		while(true) {
			Request request = channel.takeRequest();
			request.execute();
		}
	}

}
