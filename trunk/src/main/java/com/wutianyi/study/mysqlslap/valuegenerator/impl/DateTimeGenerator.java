package com.wutianyi.study.mysqlslap.valuegenerator.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wutianyi.study.mysqlslap.valuegenerator.ValueGenerator;

public class DateTimeGenerator implements ValueGenerator {
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
	
	public Object generateValue() {
		return "'" + dateFormat.format(new Date()) + "'";
	}

}
