package com.jje.membercenter.remote.support;

public enum JJETransCode {

	J000000("test"),J000002("test2");
	
	private String alias;

	public String getAlias() {
		return alias;
	}

	private JJETransCode(String alias) {

		this.alias = alias;
	}

}
