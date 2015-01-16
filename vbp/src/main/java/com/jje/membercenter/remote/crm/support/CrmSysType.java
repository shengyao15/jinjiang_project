package com.jje.membercenter.remote.crm.support;

public enum CrmSysType {

	JJE_EBUSINESS("JJ000", "锦江电商"),
	JJE_HBUSINESS("JJ001", "星级酒店"),
	JJE_SBUSINESS("JJ002", "锦江之星"),
	JJE_BBUSINESS("JJ005", "百时快捷"),
	JJE_TBUSINESS("JJ003", "锦江旅游");

	private String code;

	private String alias;

	public String getAlias() {
		return alias;
	}


	public String getCode() {
		return code;
	}

	private CrmSysType(String code, String alias) {
		this.code = code;
		this.alias = alias;
	}

}
