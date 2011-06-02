package com.wutianyi.duowan.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.wutianyi.duowan.dao.PrivilegeDao;
import com.wutianyi.duowan.dataobject.FloatLayerDTO;

public class PrivilegeDaoImpl extends SqlMapClientDaoSupport implements
		PrivilegeDao {

	private static Logger logger = Logger.getLogger(PrivilegeDaoImpl.class);

	public FloatLayerDTO getPriGameNAIByGameId(int gameId) {
		return (FloatLayerDTO) this.getSqlMapClientTemplate().queryForObject(
				"getPriGameNAIByGameId", gameId);
	}

	public List<FloatLayerDTO> listPriGameNAIByGameIds(List<Integer> gameIds) {
		if (null == gameIds || gameIds.size() == 0) {
			logger.info("The gameIds is Null or Empty!");
			return Collections.EMPTY_LIST;
		}
		List<FloatLayerDTO> floatLayers = this.getSqlMapClientTemplate()
				.queryForList("listPriGameNAIByGameIds", gameIds);
		Map<Integer, Integer> usedCounts = this.getSqlMapClientTemplate()
				.queryForMap("listPrivilegeGameName", gameIds, "gameId", "usedCount");
		
		for(FloatLayerDTO floatLayer : floatLayers) {
			System.err.println(usedCounts.get(floatLayer.getGameId()).getClass());
			floatLayer.setCountByStatus(usedCounts.get(floatLayer.getGameId()));
		}
		return floatLayers;
	}

}
