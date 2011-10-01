package com.wutianyi.study.mysqlslap.pool;

import java.lang.ref.Reference;

import org.apache.log4j.Logger;

public class PoolObject<T> {
	
	private static Logger logger = Logger.getLogger(PoolObject.class);
	
	Object reference;
	T poolObject;
	long lastUsedTime;
	boolean active = false;
	
	ExecutorPools<T> pools;

	@SuppressWarnings("unchecked")
	Reference weakReference;

	@SuppressWarnings("unchecked")
	PoolObject(Reference ref, T _poolObject, ExecutorPools<T> _pools) {
		this.weakReference = ref;
		this.reference = ref.get();
		this.poolObject = _poolObject;
		this.pools = _pools;
	}

	/**
	 * 不要在外面保留PoolObject对象的引用
	 * 
	 * @return
	 */
	public T getPoolObject() {
		synchronized (this) {
			if(!active) {
				logger.info("[Active The Object Pool!]");
				
				PoolObject<T> _poolObject = pools.getPoolObject();
				activePoolObject(_poolObject);
			}
			
			logger.info("[Update The Last Used Time!]");
			this.lastUsedTime = System.currentTimeMillis(); 
		}
		
		return poolObject;
	}

	void activePoolObject(PoolObject<T> _poolObject) {
		this.reference = _poolObject.reference;
		this.lastUsedTime = _poolObject.lastUsedTime;
		this.poolObject = _poolObject.poolObject;
		this.active = _poolObject.active;
		this.weakReference = _poolObject.weakReference;
	}
}
