package com.jje.membercenter.remote.crm.support;

public enum CrmSrType {
	
	SCORE_FILL_UP("Credit Readd", "积分补登");
	
	private String code;

	private String alias;

	public String getAlias() {
		return alias;
	}

	
	public String getCode() {
		return code;
	}
	
	private CrmSrType(String code,String alias ) {
		this.code = code;
		this.alias = alias;
	}

}
