package com.wutianyi.duowan.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.wutianyi.duowan.dao.BookIssueDao;
import com.wutianyi.duowan.dataobject.CodeActivityDO;

public class BookIssueDaoImpl extends SqlMapClientDaoSupport implements BookIssueDao{

	public CodeActivityDO fetchCodeActivity(int actId) {
		return (CodeActivityDO) this.getSqlMapClientTemplate().queryForObject("fetchCodeActivity", actId);
	}

	public String getGameName(int gameId) {
		return (String) this.getSqlMapClientTemplate().queryForObject(
				"getGameName", gameId);
	}

}
