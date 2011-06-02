package com.wutianyi.duowan.dao;

import java.util.List;

import com.wutianyi.duowan.dataobject.FloatLayerDTO;

public interface PrivilegeDao {

	/**
	 * 获取特权游戏的名称，已领取的特权号，介绍
	 * 
	 * @param gameId
	 * @return
	 */
	public FloatLayerDTO getPriGameNAIByGameId(int gameId);

	/**
	 * 获取一批特权游戏信息
	 * @param gameIds
	 * @return
	 */
	public List<FloatLayerDTO> listPriGameNAIByGameIds(List<Integer> gameIds);
}
