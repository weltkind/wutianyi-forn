package com.wutianyi.duowan.dataobject;

public class FloatLayerDTO {

	private int gameId;
	private String gameName;
	private String gameStatusName;
	private int countByStatus;
	private int statusType;
	private String introduction;

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameStatusName() {
		return gameStatusName;
	}

	public void setGameStatusName(String gameStatusName) {
		this.gameStatusName = gameStatusName;
	}

	public int getCountByStatus() {
		return countByStatus;
	}

	public void setCountByStatus(int countByStatus) {
		this.countByStatus = countByStatus;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getStatusType() {
		return statusType;
	}

	public void setStatusType(int statusType) {
		this.statusType = statusType;
	}

}
