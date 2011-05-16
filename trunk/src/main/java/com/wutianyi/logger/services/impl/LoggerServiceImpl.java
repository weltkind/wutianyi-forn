package com.wutianyi.logger.services.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.wutianyi.logger.dao.LoggerDAO;
import com.wutianyi.logger.dataobject.LoggerDO;
import com.wutianyi.logger.services.interfaces.LoggerService;
import com.wutianyi.logger.utils.LoggerType;
import com.wutianyi.logger.utils.MapperJsonUtils;

public class LoggerServiceImpl implements LoggerService {
	private static Logger logger = Logger.getLogger(LoggerServiceImpl.class);
	
	private final String OPERATOR = "operator";

	@Autowired
	private LoggerDAO loggerDao;

	public void logger(Object obj, LoggerType type, String operator) {
		LoggerDO logger = createLoggerDO(obj, type, operator);
		if (null != logger) {
			loggerDao.recordLogger(logger);
		}
	}

	private LoggerDO createLoggerDO(Object obj, LoggerType type, String operator) {
		if (null == obj) {
			logger.error("[The Logger Object is null!]");
			return null;
		}
		if (null == type) {
			type = LoggerType.OTHER;
		}
		
		if(StringUtils.isBlank(operator)) {
			operator = OPERATOR;
		}
		LoggerDO logger = new LoggerDO();
		logger.setGmtCreate(new Date());
		logger.setGmtModified(new Date());
		logger.setOldValue(MapperJsonUtils.changeObjectToJsonString(obj));
		logger.setOperationType(type.getType());
		logger.setOperator(operator);
		return logger;
	}

}
