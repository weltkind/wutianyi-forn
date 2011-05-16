package com.wutianyi.logger.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/com/wutianyi/logger/spring/aspectj-config.xml","classpath:/com/wutianyi/logger/spring/aspectj-context.xml"})
public class ExampleTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private LoggerExampleService loggerExample;
	
	@Test
	public void testdeleteExample() {
		ExampleDO example = new ExampleDO();
		example.setValue("deleteExample");
		loggerExample.deleteExample(example);
	}
	
	@Test
	public void testDeleteExampleWithOperator() {
		ExampleDO example = new ExampleDO();
		example.setValue("deleteExampleWithOperator");
		loggerExample.deleteExample(example, "wutianyi");
	}
	
	@Test
	public void testInsertExample() {
		ExampleDO example = new ExampleDO();
		example.setValue("insertExample");
		loggerExample.insertExample(example);
	}
	
	@Test
	public void testInsertExampleWithOperator() {
		ExampleDO example = new ExampleDO();
		example.setValue("insertExampleWithOperator");
		loggerExample.insertExample(example, "wutianyi");
	}
}
