package com.jje.vbp.validate.domain;

import java.util.Date;

public class ValidateCodeLog {

	private Long id;

	private String ip;

	private String phone;

	private String mail;

	private String code;

	private Date createDate;
	
	private String memberInfoId;
	
	

	public String getMemberInfoId() {
		return memberInfoId;
	}

	public void setMemberInfoId(String memberInfoId) {
		this.memberInfoId = memberInfoId;
	}

	public ValidateCodeLog() {
		super();
	}
	
	public ValidateCodeLog(String ip, String phone, String mail, String code, Date createDate) {
		this();
		this.ip = ip;
		this.phone = phone;
		this.mail = mail;
		this.code = code;
		this.createDate = createDate;
	}
	
	public static ValidateCodeLog getIPValidateCodeLogQuery(String ip, Date createDate) {
		ValidateCodeLog validateCodeLog = new ValidateCodeLog();
		validateCodeLog.ip = ip;
		validateCodeLog.createDate = createDate;
		return validateCodeLog;
	}
	
	public static ValidateCodeLog getPhoneValidateCodeLogQuery(String phone, Date createDate) {
		ValidateCodeLog validateCodeLog = new ValidateCodeLog();
		validateCodeLog.phone = phone;
		validateCodeLog.createDate = createDate;
		return validateCodeLog;
	}
	
	public static ValidateCodeLog getValidateCodeLogQuery(String phone, Date createDate,String memberInfoId) {
		ValidateCodeLog validateCodeLog = new ValidateCodeLog();
		validateCodeLog.phone = phone;
		validateCodeLog.createDate = createDate;
		validateCodeLog.memberInfoId = memberInfoId;
		return validateCodeLog;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail == null ? null : mail.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}