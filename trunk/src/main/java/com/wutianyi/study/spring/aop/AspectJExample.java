package com.wutianyi.study.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;

@Aspect
public class AspectJExample {
	
	@Before("execution (* com.xyz.myapp.dao.*.*(..))")
	public void doAccessCheck(){
		
	}
	//使用后置通知时不允许返回一个完全不同的引用
	@AfterReturning(pointcut="com.xyz.Test", returning="retVal")
	public void doAccessCheckAfter(Object retVal) {
		
	}
	
	@AfterThrowing("com.xyz.Test")
	public void doRecoveryActions() {
		
	}
	@AfterThrowing(pointcut="com.xyz.Test", throwing="ex")
	public void doRecoveryActions_1(DataAccessException ex) {
		
	}
	
	@After("")
	public void doReleaseLock() {
		
	}
	@Around("com.xyz.Test")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		Object retValu = pjp.proceed();
		
		return retValu;
	}
	//任何通知都可以将第一个参数指定为joinPoint 类型
	@Before("com.xyz.Test")
	public void joinPoint(JoinPoint joinPoint) {
		
	}
	
	@Pointcut("execution (* *.*(..) && args(account, ..))")
	private void AccountDataAccessOperation(Object account){}
	
	@Before("AccountDataAccessOperation(account)")
	public void validateAccount(Object account) {
		
	}
	
	@Before("com.xyz.lib.Poincuts.anyPublishMethod() && @annotation(auditable)")
	public void audit(Auditable auditable) {
		
	}
	//任何匹配的bean都会实现aopInterface接口
	@DeclareParents(value="com.xyz.Test", defaultImpl=AopInterfacesImpl.class)
	public static AopInterfaces aopInterface;
}
