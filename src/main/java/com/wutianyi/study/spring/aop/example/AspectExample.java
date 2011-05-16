package com.wutianyi.study.spring.aop.example;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * Ordered 可以定义切面中通知的级别
 * 
 * @author hanjie.wuhj
 * 
 */
@Aspect
public class AspectExample implements Ordered {
	//记住+ 号的作用，如果没有加号，不能直接转化引入的接口类型
	@DeclareParents(value = "com.wutianyi.study.spring.aop.example.ServiceExample+", defaultImpl = ImportInterfaceImpl.class)
	private static ImportInterface importInterface;

	private static final int DEFAULT_MAX_RETRIES = 2;

	private int maxRetries = DEFAULT_MAX_RETRIES;
	private int order = 1;

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Pointcut("execution( * com.wutianyi.study.spring.aop.example.ServiceExample.*(..) )")
	private void interceptorPublicMethod() {
	}

	@Before("interceptorPublicMethod()")
	public void beforeAopTest() {
		System.out.println("Before the target method process!");
	}

	public int getOrder() {
		return order;
	}

}
