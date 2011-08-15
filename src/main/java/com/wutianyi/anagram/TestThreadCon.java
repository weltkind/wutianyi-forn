package com.wutianyi.anagram;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class TestThreadCon {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	public class TestThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {

			return new TestThread(r);
		}

	}

	private class TestThread extends Thread {

		public TestThread(Runnable r) {
			super(r);
		}

		@Override
		public void run() {
			System.out.println("run");
			threadLocal.set(Thread.currentThread().getName());
			super.run();
		}

	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		TestThreadCon con = new TestThreadCon();
		ThreadFactory factory = con.new TestThreadFactory();
		ExecutorService service = Executors.newFixedThreadPool(2, factory);
		Future<String> future = service.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {

				Thread.sleep(1000);
				System.out.println(threadLocal.get());
				return "hello World";
			}
		});
		Future<String> future_1 = service.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {

				Thread.sleep(1000);
				System.out.println(threadLocal.get());
				return "hello World";
			}
		});
		System.out.println(future.get());
		
		service.shutdown();
	}
}
