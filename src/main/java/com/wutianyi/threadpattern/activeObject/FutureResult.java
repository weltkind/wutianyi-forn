package com.wutianyi.threadpattern.activeObject;

public class FutureResult extends Result {
	private Result result = null;
	private boolean ready = false;
	private Object lock = new Object();

	public String getResultValue() {
		synchronized(lock) {
			while (!ready) {
				try {
					lock.wait();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return result.getResultValue();

	}

	public void setResult(Result result) {
		this.result = result;
		this.ready = true;
		synchronized(lock) {
			lock.notifyAll();
		}
		
	}

}
