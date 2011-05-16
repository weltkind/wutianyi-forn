package com.wutianyi.threadpattern.guardedSuspension;

import java.util.LinkedList;

public class RequestQueue {
	
	private final LinkedList<Request> queue = new LinkedList<Request>();
	
	public synchronized Request getRequest() {
		while(queue.size() <= 0) {
			try {
				//只有获取了资源的锁定，才能调用wait
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return queue.removeFirst();
	}
	
	public synchronized void putRequest(Request request) {
		queue.addLast(request);
		notifyAll();
	}
}
