package com.wutianyi.study.mysqlslap.pool;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.wutianyi.study.mysqlslap.pool.beanfactory.impl.ConnectionBeanFactory;

@SuppressWarnings("unchecked")
public class PoolBeanFactory {

	private static Map<Class, BeanFactory> map;

	static {
		map = new HashMap<Class, BeanFactory>();
		map.put(Connection.class, new ConnectionBeanFactory());
	}

	public static BeanFactory getBeanFactory(Class clz) {
		return map.get(clz);
	}
	
	public static void main(String[] args) {
		
	}
	
}
