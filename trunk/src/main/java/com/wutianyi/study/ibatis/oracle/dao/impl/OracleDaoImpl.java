package com.wutianyi.study.ibatis.oracle.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.wutianyi.study.ibatis.oracle.dao.OracleDao;

public class OracleDaoImpl extends SqlMapClientDaoSupport implements OracleDao{

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listAllCommodity() {
		return this.getSqlMapClientTemplate().queryForList("listAllCommodity");
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listAllAttrDict() {
		return this.getSqlMapClientTemplate().queryForList("listAllAttrDict");
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listAllCommodityAttrValueRel() {
		return this.getSqlMapClientTemplate().queryForList("listAllCommodityAttrValueRel");
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listAllValueDict() {
		return this.getSqlMapClientTemplate().queryForList("listAllValueDict");
	}

}
