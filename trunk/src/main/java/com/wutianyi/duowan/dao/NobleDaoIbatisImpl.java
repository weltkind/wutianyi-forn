package com.wutianyi.duowan.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class NobleDaoIbatisImpl extends SqlMapClientDaoSupport implements NobleDao{

	public void insertNoble(String name) {
	}

	public boolean checkUserExist(String username) {
		Integer isExist = (Integer) this.getSqlMapClientTemplate().queryForObject("checkUserExist");
		
		return null != isExist ? true : false;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/com/wutianyi/study/ibatis/duowan-spring.xml");
	}

}
