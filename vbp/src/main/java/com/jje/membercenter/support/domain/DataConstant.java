package com.jje.membercenter.support.domain;

public enum DataConstant {

	GOLD_UPGRADE_NUMBER("金卡升级次数 ","UPGRADE_NUMBER"),PLATINUM_UPGRADE_NUMBER("铂金卡升级次数 ","UPGRADE_NUMBER"),GOLD_UPGRADE_SCORES("金卡升级积分","UPGRADE_SCORES"),PLATINUM_UPGRADE_SCORES("铂金卡升级积分","UPGRADE_SCORES");
	private String name;
	private String type;
	private DataConstant(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

 

	public String getType() {
		return type;
	}

}
