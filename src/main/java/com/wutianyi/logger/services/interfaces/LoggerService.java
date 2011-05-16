package com.wutianyi.logger.services.interfaces;

import com.wutianyi.logger.utils.LoggerType;

/**
 * @author hanjie.wuhj
 *
 */
public interface LoggerService {

	/**
	 * 具体实现的时候会将object对象转化为json格式的数据结构，然后保存于数据库中
	 * @param obj 需要记录日志的对象
	 * @param type 表明记录日志的类型
	 * @param operator 表明实际的操作者
	 */
	public void logger(Object obj, LoggerType type, String operator); 
}
