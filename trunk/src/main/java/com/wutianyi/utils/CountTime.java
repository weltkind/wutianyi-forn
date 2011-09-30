package com.wutianyi.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CountTime {

	private int threadNum;
	private CyclicBarrier barrier;

	public CountTime(Runnable runnable, int threadNum) {

		barrier = new CyclicBarrier(threadNum + 1);

		for (int i = 0; i < threadNum; i++) {
			MyThread t = new MyThread(runnable);
			t.start();
		}
		try {
			long start = System.nanoTime();
			barrier.await();
			barrier.await();
			long end = System.nanoTime();

			System.out.println("Run " + threadNum
					+ " to finish runnable cost: " + (end - start) + " ms");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	class MyThread extends Thread {
		public MyThread(Runnable runnable) {
			super(runnable);
		}

		public void run() {
			try {
				barrier.await();
				super.run();
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
		CountTime countTime = new CountTime(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			}
		}, 10);
	}
}
