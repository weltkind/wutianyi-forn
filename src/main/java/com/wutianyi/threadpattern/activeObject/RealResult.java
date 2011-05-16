package com.wutianyi.threadpattern.activeObject;

public class RealResult extends Result {
	
	private final String resultValue;
	
	public RealResult(String resultValue) {
		this.resultValue = resultValue;
	}
	
	@Override
	public String getResultValue() {
		return resultValue;
	}

}
