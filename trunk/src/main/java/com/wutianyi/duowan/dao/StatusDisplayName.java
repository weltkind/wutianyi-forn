package com.wutianyi.duowan.dao;

import java.util.HashMap;
import java.util.Map;

public enum StatusDisplayName {
	GOT(1, "领取"), BOOK(2, "预订"), TAO(3, "淘号");
	
	private int id;
	private String displayName;
	private static Map<Integer, String> map;
	
	static {
		map = new HashMap<Integer, String>();
		for(StatusDisplayName s : StatusDisplayName.values()) {
			map.put(s.getId(), s.getDisplayName());
		}
	}
	
	private StatusDisplayName(int id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public static String getDisplayNameById(int id) {
		return map.get(id);
	}
}
