package com.wutianyi.study.mysqlslap.pool;

import java.sql.Connection;

public class PoolsFactory {

	private static ExecutorPools<Connection> connectionPool = new ExecutorPools<Connection>(
			5, Connection.class);

	@SuppressWarnings("unchecked")
	public static PoolObject getConnection() {
		return connectionPool.getPoolObject();
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 1; i++) {
			Thread thread = new Thread(new Runnable() {

				public void run() {
					PoolObject<Connection> poolObject = PoolsFactory
							.getConnection();
					
					try {
						Thread.sleep(5000);
						poolObject.getPoolObject();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			});
			thread.start();
		}
//		long startTime = System.currentTimeMillis();
//		Thread.sleep(1000);
//		System.out.println(System.currentTimeMillis() - startTime);
	}

}
