package com.wutianyi.study.mysqlslap.pool;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class ExecutorPools<T> {

	private static Logger logger = Logger.getLogger(ExecutorPools.class);
	private final static int MAX_SIZE = 10;
	/**
	 * 鍥炴敹鐨勬椂闂达紝濡傛灉瀵硅薄2s娌℃湁鏀惧洖鍒欎細鑷姩鍥炴敹璇ュ璞�
	 */
	private final static long RECOVER_TIMER = 2000;

	int poolSize;
	/**
	 * 寮哄埗鍥炴敹
	 */
	RecoverPoolObjectTimerTask recoverTimerTask = new RecoverPoolObjectTimerTask();
	boolean isOpen = false;

	ArrayBlockingQueue<T> blockingQueue = new ArrayBlockingQueue<T>(MAX_SIZE);

	ConcurrentHashMap<WeakReference<Integer>, T> map = new ConcurrentHashMap<WeakReference<Integer>, T>();
	ConcurrentHashMap<WeakReference<Integer>, PoolObject<T>> activeObjects = new ConcurrentHashMap<WeakReference<Integer>, PoolObject<T>>();

	ReferenceQueue<Integer> queue = new ReferenceQueue<Integer>();

	@SuppressWarnings("unchecked")
	public ExecutorPools(int _poolSize, Class clz) {
		isOpen = true;
		if (_poolSize > MAX_SIZE) {
			_poolSize = MAX_SIZE;
		}

		this.poolSize = _poolSize;
		initPools(clz);

		Thread thread = new Thread(new Runnable() {

			public void run() {

				while (true) {
					try {

						WeakReference<Integer> ref = (WeakReference<Integer>) queue
								.remove();
						logger.info("[Auto Recover!]");
						recoverPoolObject(ref);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});
		logger.info("[Start Auto Recover!]");
		thread.start();
		logger.info("[Start enhause Recover!]");
		RecoverTimer.startTimerTask(recoverTimerTask);
	}

	synchronized void recoverPoolObject(WeakReference<Integer> ref) {
		T t = map.get(ref);

		if (null != t) {
			
			blockingQueue.add(t);
			map.remove(ref);

			PoolObject<T> poolObject = activeObjects.remove(ref);
			if (null != poolObject) {
				poolObject.active = false;
			}

		}
		logger.info("[Recover poolObject!]");
		logger.info("[map: ]" + map);
		logger.info("[activeObjects: ]" + activeObjects);
		logger.info("-------------------------------------------");
	}

	@SuppressWarnings("unchecked")
	private void initPools(Class clz) {
		BeanFactory<T> beanFactory = PoolBeanFactory.getBeanFactory(clz);
		logger.info("[Init The Pool Bean!]");

		for (int i = 0; i < poolSize; i++) {
			T t = beanFactory.createBean();
			blockingQueue.add(t);
		}
		logger.info("[Finish Init The Pool Bean!]");
	}

	public void addT(T t) {
		blockingQueue.add(t);
	}

	@SuppressWarnings("unchecked")
	public PoolObject<T> getPoolObject() {
		T t = null;
		PoolObject<T> poolObject = null;
		WeakReference<Integer> reference = null;
		try {

			t = blockingQueue.take();
			reference = new WeakReference<Integer>(t.hashCode(), queue);

			map.put(reference, t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (null != t) {
			// 鍒濆鍖栬幏鍙栫殑瀵硅薄
			poolObject = new PoolObject(reference, t, this);
			poolObject.active = true;
			poolObject.lastUsedTime = System.currentTimeMillis();

			activeObjects.put(reference, poolObject);
		}
		logger.info("[Get PoolObject!]");
		logger.info("[map: ]" + map);
		logger.info("[activeObjects: ]" + activeObjects);

		return poolObject;

	}

	class RecoverPoolObjectTimerTask extends TimerTask {

		@Override
		public void run() {
			if (activeObjects.size() == 0) {
				return;
			}

			long currentTime = System.currentTimeMillis();

			for (Entry<WeakReference<Integer>, PoolObject<T>> entry : activeObjects
					.entrySet()) {
				PoolObject<T> poolObject = entry.getValue();

				synchronized (poolObject) {
					System.out.println(currentTime - poolObject.lastUsedTime);
					if (currentTime - poolObject.lastUsedTime > RECOVER_TIMER) {
						logger.info("[Enhause Recover!]");
						recoverPoolObject(entry.getKey());
					}
				}

			}
		}

	}

	public static void main(String[] args) throws InterruptedException {

	}

}
