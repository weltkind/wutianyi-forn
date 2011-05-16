package com.wutianyi.study.spring.aop.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wutianyi.study.spring.aop.example.schema.SchemaInterface;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/com/wutianyi/study/spring/aop/example/schema/aop-schema-context.xml"})
public class AopSchemaTest {
	
	@Autowired
	private SchemaInterface schemaInterface;
	
	@Test
	public void testBefore() {
		schemaInterface.schemaMethod();
	}
}
