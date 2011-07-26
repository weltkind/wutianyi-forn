package com.wutianyi.study.spring.aop.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/com/wutianyi/study/spring/aop/example/aop-example-context.xml" })
public class AopExampleTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private ServiceExample serviceExample;
	
	private ImportInterface importInterface;
	@Test
	public void testBefore() {
		serviceExample.testAop("test");
	}
	
	@Test
	public void testImportInterface() {
//		importInterface = (ImportInterface) applicationContext.getBean("serviceExample");
//		importInterface.importMethod();
	}
}
