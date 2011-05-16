package com.wutianyi.logger.query;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class LoggerQueryTest {
	
	private LoggerQuery query = null;
	private Calendar calendar = null;
	
	@Before
	public void setUp() {
		query = new LoggerQuery();
		query.setGmtCreateBeginDate(new Date());
		query.setGmtCreateEndDate(new Date());
		calendar = new GregorianCalendar(2010, 10, 10);
	}
	
	@Test
	public void testSetBeginDateBeforeEndDate() {
		query.setGmtCreateBeginDate(calendar.getTime());
		
		Assert.isTrue(query.getGmtCreateBeginDate().before(query.getGmtCreateEndDate()));
	}
	
	@Test
	public void testSetBeginDateAfterEndDate() {
		query.setGmtCreateBeginDate(calendar.getTime());
		query.setGmtCreateEndDate(calendar.getTime());
		
		query.setGmtCreateBeginDate(new Date());
		Assert.isTrue(query.getGmtCreateBeginDate().before(query.getGmtCreateEndDate()));
	}
	
	@Test
	public void testSetEndDateAfterBeginDate() {
		query.setGmtCreateBeginDate(calendar.getTime());
		query.setGmtCreateEndDate(calendar.getTime());
		
		query.setGmtCreateEndDate(new Date());
		
		Assert.isTrue(query.getGmtCreateEndDate().after(query.getGmtCreateBeginDate()));
	}
	
	@Test
	public void testSetEndDateBeforeBeginDate() {
		query.setGmtCreateEndDate(calendar.getTime());
		
		Assert.isTrue(query.getGmtCreateEndDate().after(query.getGmtCreateBeginDate()));
	}
}
