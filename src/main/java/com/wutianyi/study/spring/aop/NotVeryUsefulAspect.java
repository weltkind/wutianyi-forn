package com.wutianyi.study.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class NotVeryUsefulAspect {

	@Pointcut("execute (* transfer(..))")//表达式
	private void anyOldTransfer(){};//签名
	
	@Pointcut("execution (public * *(..))")
	private void anyPublicOperation() {}
	
	@Pointcut("within (com.xyz.someapp.trading..*)")
	private void inTrading(){}
	
	@Pointcut("anyPublicOperation() && inTrading()")
	private void tradingOperation(){}
	
	//实现了AccountService接口的代理对象
	@Pointcut("this(com.xyz.service.AccountService)")
	private void thisAnnotion(){}
	
	//实现了AccountService的目标对象
	@Pointcut("target(com.xyz.service.AccountService)")
	private void targetAnnotion(){}
	
	//任何只接受一个参数的方法
	@Pointcut("args(java.io.Serializable)")
	private void argsAnnotion(){}
	
	//目标对象中有一个@transactional 注解
	@Pointcut("@target(org.springframework.transaction.annotation.Transactionl)")
	private void targetAAnnotion() {}
	
	@Pointcut("@annotion(test)")
	private void annotion() {}
	
	@Pointcut("@args(Test)")
	private void argsAAnnotation(){}
	
	//任何一个在名称为tradeService的spring bean之上的链接点
	@Pointcut("bean(tradeService)")
	private void beanAnnotation(){}
}
