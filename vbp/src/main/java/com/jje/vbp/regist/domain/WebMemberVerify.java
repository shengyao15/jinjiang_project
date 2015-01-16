package com.jje.vbp.regist.domain;

public class WebMemberVerify {

	private long id;        
	private long memInfoId; 
	private String menName; 
	private String password;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMemInfoId() {
		return memInfoId;
	}
	public void setMemInfoId(long memInfoId) {
		this.memInfoId = memInfoId;
	}
	public String getMenName() {
		return menName;
	}
	public void setMenName(String menName) {
		this.menName = menName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
