/**
 * 
 */

package com.wutianyi.study.ibatis.mapper;

/**
 * @author wutianyi
 * 
 */
public class NodeMgrDO {

	private int		id;
	private String	type;
	private String	host;
	private int		port;
	private String	domain;
	private int		weight;
	
	public int getId() {
	
		return id;
	}
	
	public void setId(int id) {
	
		this.id = id;
	}
	
	public String getType() {
	
		return type;
	}
	
	public void setType(String type) {
	
		this.type = type;
	}
	
	public String getHost() {
	
		return host;
	}
	
	public void setHost(String host) {
	
		this.host = host;
	}
	
	public int getPort() {
	
		return port;
	}
	
	public void setPort(int port) {
	
		this.port = port;
	}
	
	public String getDomain() {
	
		return domain;
	}
	
	public void setDomain(String domain) {
	
		this.domain = domain;
	}
	
	public int getWeight() {
	
		return weight;
	}
	
	public void setWeight(int weight) {
	
		this.weight = weight;
	}
	
}
