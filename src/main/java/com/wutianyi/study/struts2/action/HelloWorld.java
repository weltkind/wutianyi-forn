package com.wutianyi.study.struts2.action;

import com.opensymphony.xwork2.ActionSupport;

public class HelloWorld extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8584627856798236849L;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String execute() throws Exception{
		setMessage("您好！Struts 2! ");
		return SUCCESS;
	}
}
