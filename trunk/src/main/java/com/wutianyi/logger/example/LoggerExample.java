package com.wutianyi.logger.example;

public class LoggerExample implements LoggerExampleService {
	
	public void insertExample(ExampleDO example) {
		System.err.println("insertExample");
	}
	
	public void insertExample(ExampleDO example, String operator) {
		System.err.println("insertExampleWithOperato");
	}
	
	public void updateExample(ExampleDO example) {
		
	}
	
	public void updateExample(ExampleDO example, String operator){
		
	}
	
	public void deleteExample(ExampleDO example) {
		
	}
	
	public void deleteExample(ExampleDO example, String operator) {
		
	}
	
	public void otherExample(ExampleDO example) {
		
	}
}
