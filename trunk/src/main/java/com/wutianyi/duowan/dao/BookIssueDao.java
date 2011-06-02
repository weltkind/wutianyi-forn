package com.wutianyi.duowan.dao;

import com.wutianyi.duowan.dataobject.CodeActivityDO;

public interface BookIssueDao {
	
	public CodeActivityDO fetchCodeActivity(int actId);
	
	public String getGameName(int gameId);
}
