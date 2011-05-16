package com.wutianyi.threadpattern.TwoPhaseTermination;

public class CountupThread extends Thread {

	private long counter = 0;

	private volatile boolean shutDownRequested = false;

	public void shutDownRequest() {
		shutDownRequested = true;
		interrupt();
	}

	public boolean isShutDownRequested() {
		return shutDownRequested;
	}

	public final void run() {
		try {
			while (!shutDownRequested) {
				dowWork();
			}
		} catch (InterruptedException e) {

		} finally {
			doShutDown();
		}
	}

	private void dowWork() throws InterruptedException {
		counter++;
		System.out.println("doWork: counter = " + counter);
		Thread.sleep(500);
	}

	private void doShutDown() {
		System.out.println("doShutdown: counter = " + counter);
	}
}
