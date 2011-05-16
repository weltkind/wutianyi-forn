package com.wutianyi.logger.utils;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.wutianyi.logger.example.ExampleDO;

public class MapperJsonUtils {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Logger logger = Logger.getLogger(MapperJsonUtils.class);
	
	/**
	 * @param obj
	 * @return 返回的是json格式的数据结构，但是会增加相应的标识，说明是具体的那个类
	 */
	public static String changeObjectToJsonString(Object obj) {
		if(null == obj) { 
			return StringUtils.EMPTY;
		}
		
		try {
			String jsonString = obj.getClass().getName() + ":" + objectMapper.writeValueAsString(obj);
			return jsonString;
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return StringUtils.EMPTY;
	}
	
	public static void main(String[] args) {
		ExampleDO exampleDO = new ExampleDO();
		exampleDO.setValue("wutianyi");
		System.out.println(exampleDO.getClass().getName());
		System.out.println(changeObjectToJsonString(exampleDO));
	}
}
