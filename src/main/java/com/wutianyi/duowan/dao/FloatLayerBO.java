package com.wutianyi.duowan.dao;

import com.wutianyi.duowan.dataobject.CodeActivityDO;
import com.wutianyi.duowan.dataobject.FloatLayerDTO;

public class FloatLayerBO {

	private PrivilegeDao privilegeDao;
	private BookIssueDao bookIssueDao;

	public FloatLayerDTO fetchFloatLayerInformation(int gameId, int actType) {
		FloatLayerDTO floatLayer = null;

		if (ActType.BOOK_ISSUE.getValue() == actType) {
			CodeActivityDO codeActivity = bookIssueDao
					.fetchCodeActivity(gameId);
			String gameName = bookIssueDao
					.getGameName(codeActivity.getGameId());
			floatLayer = new FloatLayerDTO();
			floatLayer.setGameName(gameName);
			floatLayer.setGameId(codeActivity.getGameId());
			floatLayer.setIntroduction(codeActivity.getOneSentence());
			if (codeActivity.getActState() == 1) {
				floatLayer.setCountByStatus(codeActivity.getGotCount());
			} else if (codeActivity.getActState() == 2) {
				floatLayer.setCountByStatus(codeActivity.getHadBook());
			} else {
				floatLayer.setCountByStatus(codeActivity.getTaoCount());
			}
			floatLayer.setGameStatusName(StatusDisplayName
					.getDisplayNameById(codeActivity.getActState()));
		} else {
			floatLayer = privilegeDao.getPriGameNAIByGameId(gameId);
			floatLayer.setGameStatusName(StatusDisplayName
					.getDisplayNameById(1));
		}
		return floatLayer;
	}

	public PrivilegeDao getPrivilegeDao() {
		return privilegeDao;
	}

	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	public BookIssueDao getBookIssueDao() {
		return bookIssueDao;
	}

	public void setBookIssueDao(BookIssueDao bookIssueDao) {
		this.bookIssueDao = bookIssueDao;
	}
}
