package com.wutianyi.study.ibatis.dao;

import com.wutianyi.study.ibatis.dataobject.Test1;
import com.wutianyi.study.ibatis.dataobject.Test1Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class Test1DAOImpl extends SqlMapClientDaoSupport implements Test1DAO {

	public int deleteByExample(Test1Example example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void insert(String record) {
		this.getSqlMapClientTemplate().insert("insert", "test");
	}

	public List selectByExample(Test1Example example, String orderByClause) {
		// TODO Auto-generated method stub
		return null;
	}

	public List selectByExample(Test1Example example) {
		// TODO Auto-generated method stub
		return null;
	}

	public Test1 selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int updateByPrimaryKey(Test1 record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByPrimaryKeySelective(Test1 record) {
		// TODO Auto-generated method stub
		return 0;
	}

}