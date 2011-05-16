package com.wutianyi.logger.dao;

import java.util.List;

import com.wutianyi.logger.dataobject.LoggerDO;
import com.wutianyi.logger.query.LoggerQuery;

public interface LoggerDAO {

	public void recordLogger(LoggerDO logger);

	/**
	 * 根据请求的query去获取loggerDO对象
	 * @param loggerQuery 如果参数为空则返回null
	 */
	public List<LoggerDO> listLoggerByLoggerQuery(LoggerQuery loggerQuery);
	
	/**
	 * 获取一个logger对象
	 * @param id
	 * @return
	 */
	public LoggerDO findLoggerById(int id);
	
}