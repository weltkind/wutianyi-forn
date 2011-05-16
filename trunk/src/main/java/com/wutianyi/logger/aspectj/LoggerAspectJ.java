package com.wutianyi.logger.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.wutianyi.logger.dao.LoggerDAO;
import com.wutianyi.logger.services.interfaces.LoggerService;
import com.wutianyi.logger.utils.LoggerType;

@Aspect
public class LoggerAspectJ {
	
	@Autowired
	private LoggerService loggerService;
	
	@Pointcut("execution (public * insert*(..))")
	private void insertMethodPointcut(){}
	
	@Pointcut("execution (public * update*(..))")
	private void updateMethodPointcut(){}
	
	@Pointcut("execution (public * delete*(..))")
	private void deleteMethodPointcut() {}
	
	@Pointcut("execution (public * *(..))")
	private void otherMethodPointcut(){}
	
	
	@AfterReturning("deleteMethodPointcut() && args(parameter)")
	public void afterDeleteMethodAdvise(Object parameter) {
		loggerService.logger(parameter, LoggerType.DELETE, null);
	}
	
	@AfterReturning("deleteMethodPointcut() && args(parameter, operator)")
	public void afterDeleteMethodAdviseWithOperator(Object parameter, String operator) {
		loggerService.logger(parameter, LoggerType.DELETE, operator);
	}
	
	@Around("insertMethodPointcut() && args(parameter)")
	public Object aroundInsertMethodAdvise(ProceedingJoinPoint joinPoint, Object parameter) {
		loggerService.logger(parameter, LoggerType.INSERT, null);
		Object obj = null;
		try {
			obj = joinPoint.proceed(new Object[]{parameter});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	@Around("insertMethodPointcut() && args(parameter, operator)")
	public Object aroundInsertMethodAdviseWithOperator(ProceedingJoinPoint joinPoint, Object parameter, String operator) {
		loggerService.logger(parameter, LoggerType.INSERT, operator);
		Object obj = null;
		try {
			obj = joinPoint.proceed(new Object[]{parameter, operator});
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
}
