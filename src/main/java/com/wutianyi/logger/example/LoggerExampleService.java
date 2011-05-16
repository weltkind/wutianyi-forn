package com.wutianyi.logger.example;

public interface LoggerExampleService {
	
	public void insertExample(ExampleDO example);
	
	public void insertExample(ExampleDO example, String operator);
	
	public void updateExample(ExampleDO example);
	
	public void updateExample(ExampleDO example, String Operator);

	public void deleteExample(ExampleDO example);
	
	public void deleteExample(ExampleDO example, String operator);

	public void otherExample(ExampleDO example);
}
