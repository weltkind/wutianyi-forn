package com.wutianyi.logger.schema;

import org.springframework.beans.factory.annotation.Autowired;

import com.wutianyi.logger.services.interfaces.LoggerService;

public class LoggerSchema {
	
	@Autowired
	private LoggerService loggerService;
	
	public void afterDeleteMethodAdvise(Object parameter) {
		
	}
	
	public void aroundInsertMethodAdvise(Object parameter) {
		
	}
	
}
