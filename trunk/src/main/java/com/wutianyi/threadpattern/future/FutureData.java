package com.wutianyi.threadpattern.future;

public class FutureData implements Data {

	private RealData realData = null;
	private boolean ready = false;

	public synchronized void setRealData(RealData realdata) {
		if (ready) {
			return;
		}

		this.realData = realdata;
		this.ready = true;
		notifyAll();
	}

	public synchronized String getContent() {
		while(!ready) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return realData.getContent();
	}

}
