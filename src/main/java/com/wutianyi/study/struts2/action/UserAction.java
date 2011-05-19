package com.wutianyi.study.struts2.action;

public class UserAction {

	private String name;
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return
	 */
	public String addUser() {
		System.out.println(name + " : " + message);
		return "success";
	}
}
