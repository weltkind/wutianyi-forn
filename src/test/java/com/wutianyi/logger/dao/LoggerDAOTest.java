package com.wutianyi.logger.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.wutianyi.logger.dataobject.LoggerDO;
import com.wutianyi.logger.query.LoggerQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/com/wutianyi/logger/spring/logger-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class LoggerDAOTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private LoggerDAO loggerDAO;
	
	private int id;
	
	@Before
	public void setUp() {
		
	}
	
	@BeforeTransaction
	public void preparedData() {
		id = simpleJdbcTemplate.queryForInt("select max(id) from logger");
		List<LoggerDO> loggers = createLoggerDOs(20, new GregorianCalendar());

		for (LoggerDO logger : loggers) {
			loggerDAO.recordLogger(logger);
		}
	}
	
	@AfterTransaction
	public void deleteData() {
		simpleJdbcTemplate.update("delete from logger where id >=" + id);
	}
	
	@Test
	public void testInsertLogger() {
		int num = simpleJdbcTemplate.queryForInt("select count(*) from logger");
		Assert.assertEquals(num, 20);
	}

	@Test
	public void testFindLoggerById() {
		LoggerDO logger = loggerDAO.findLoggerById(1);
		Assert.assertNotNull(logger);
		Assert.assertEquals("test_0", logger.getNewValue());
	}

	@Test
	public void testListLoggerByLoggerQuery() {
		LoggerQuery query = new LoggerQuery();
		query.setId(1);
		List<LoggerDO> loggers = loggerDAO.listLoggerByLoggerQuery(query);
		Assert.assertEquals(1, loggers.size());

		query.setOperator("wutianyi");
		loggers = loggerDAO.listLoggerByLoggerQuery(query);
		Assert.assertEquals(1, loggers.size());

		query.setOperator("wutianyi_1");
		loggers = loggerDAO.listLoggerByLoggerQuery(query);
		Assert.assertEquals(0, loggers.size());

		query = new LoggerQuery();
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, -21);
		System.out.println(calendar.getTime());
		query.setGmtCreateBeginDate(calendar.getTime());
		loggers = loggerDAO.listLoggerByLoggerQuery(query);
		Assert.assertEquals(20, loggers.size());
		calendar.add(Calendar.DATE, 10);
		query.setGmtCreateEndDate(calendar.getTime());
		loggers = loggerDAO.listLoggerByLoggerQuery(query);
		Assert.assertEquals(8, loggers.size());
	}

	private List<LoggerDO> createLoggerDOs(int num, Calendar calendar) {
		List<LoggerDO> loggers = new ArrayList<LoggerDO>();
		int id = this.id;
		for (int i = 0; i < num; i++) {
			LoggerDO logger = new LoggerDO();
			logger.setId(new Long(++ id));
			calendar.roll(Calendar.DATE, false);
			logger.setGmtCreate(calendar.getTime());
			logger.setGmtModified(new Date());
			logger.setNewValue("test_" + i);
			logger.setOldValue("test_" + i);
			logger.setOperationType("wutianyi");
			logger.setOperator("wutianyi");
			loggers.add(logger);
		}
		return loggers;
	}
}
