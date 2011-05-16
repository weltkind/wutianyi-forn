package com.wutianyi.logger.query;

import java.util.Date;

/**
 * 把所有的请求类型都放入里面
 * @author hanjie.wuhj
 *
 */
public class LoggerQuery {
	
	private Integer id;
	private Date gmtCreateBeginDate;
	private Date gmtCreateEndDate;
	private String operationType;
	private String operator;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getGmtCreateBeginDate() {
		return gmtCreateBeginDate;
	}
	/**
	 * 设置gmtCreateBeginDate 的值，程序会确保begin date始终小于end date
	 * @param gmtCreateBeginDate 如果为空，则不判断
	 */
	public void setGmtCreateBeginDate(Date gmtCreateBeginDate) {
		if(null != gmtCreateEndDate && null != gmtCreateBeginDate) {
			if(gmtCreateBeginDate.after(this.gmtCreateEndDate)) {
				this.gmtCreateBeginDate = this.gmtCreateEndDate;
				this.gmtCreateEndDate = gmtCreateBeginDate;
				gmtCreateBeginDate = this.gmtCreateBeginDate;
			}
		}
		
		this.gmtCreateBeginDate = gmtCreateBeginDate;
	}
	public Date getGmtCreateEndDate() {
		return gmtCreateEndDate;
	}
	/**
	 * 设置gmtCreateEndDate的值，程序会确保end date 大于begin date
	 * @param gmtCreateEndDate 如果为空，就不做判断
	 */
	public void setGmtCreateEndDate(Date gmtCreateEndDate) {
		if(null != gmtCreateEndDate && null != gmtCreateEndDate) {
			if(gmtCreateEndDate.before(this.gmtCreateBeginDate)) {
				this.gmtCreateEndDate = this.gmtCreateBeginDate;
				this.gmtCreateBeginDate = gmtCreateEndDate;
				gmtCreateEndDate = this.gmtCreateEndDate;
			}
		}
		
		this.gmtCreateEndDate = gmtCreateEndDate;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public static void main(String[] args) {
	}
}
