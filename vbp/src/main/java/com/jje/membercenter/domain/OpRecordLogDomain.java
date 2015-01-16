package com.jje.membercenter.domain;

public class OpRecordLogDomain {

	public enum EnumOpType {
		MEMBER_ACTIVE, MEMBER_REGISTER,MEMBER_BIND,CRM_SIGNUP;
	}

	private long id;
	private EnumOpType opType;
	private String message;
	private String content;
	
	public OpRecordLogDomain() {
	}
	
	public OpRecordLogDomain(EnumOpType opType, String message, String content) {
		this.content = content;
		this.message = message;
		this.opType = opType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EnumOpType getOpType() {
		return opType;
	}

	public void setOpType(EnumOpType opType) {
		this.opType = opType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
