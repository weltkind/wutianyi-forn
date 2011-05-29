package com.wutianyi.study.duowan;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wutianyi.duowan.dao.NobleDao;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/com/wutianyi/study/ibatis/duowan-spring.xml"})
public class NobleDaoTest extends AbstractJUnit4SpringContextTests{
	
	private NobleDao nobleDao;
	@Autowired
	public void setNobleDao(NobleDao nobleDao) {
		this.nobleDao = nobleDao;
	}
	
	@Before
	public void insertUsername() {
//		this.simpleJdbcTemplate.update("insert into noble(username) values('wutianyi')", new HashMap<String, String>());
	}
	
	@Test
	public void test() {
		
	}
}
