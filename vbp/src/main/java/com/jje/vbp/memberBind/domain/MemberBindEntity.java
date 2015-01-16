package com.jje.vbp.memberBind.domain;

import java.util.Date;

public class MemberBindEntity {

	public static enum BindType {

		LOGIN_BIND("login_bind"), REGISTER_BIND("register_bind");

		private String type;

		private BindType(String value) {
			type = value;
		}

		@Override
		public String toString() {
			return type;
		}
	}

	public static enum Status {

		STATUS_ON("status_on"), STATUS_OFF("status_off");

		private String status;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		private Status(String value) {
			status = value;
		}

		@Override
		public String toString() {
			return status;
		}
	}

	// id
	private long id;
	// memberID
	private String memberId;
	// mc_code
	private String mcMemberCode;
	// 绑定类型
	private BindType bindType;
	// 绑定key
	private String bindKey;
	// 绑定来源
	private String channel;
	// 状态
	private Status status;
	// 最后更新时间
	private Date createTime;
	// 最后更新时间
	private Date updateTime;

	public MemberBindEntity() {
		super();
	}

	public MemberBindEntity(String memberId, String mcMemberCode, BindType bindType, String bindKey, String channel, Status status) {
		super();
		this.memberId = memberId;
		this.mcMemberCode = mcMemberCode;
		this.bindType = bindType;
		this.bindKey = bindKey;
		this.channel = channel;
		this.status = status;
	}

	public MemberBindEntity(long id, String memberId, String mcMemberCode, BindType bindType,String bindKey, String channel, Status status, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.mcMemberCode = mcMemberCode;
		this.bindType = bindType;
		this.bindKey = bindKey;
		this.channel = channel;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMcMemberCode() {
		return mcMemberCode;
	}

	public void setMcMemberCode(String mcMemberCode) {
		this.mcMemberCode = mcMemberCode;
	}

	public BindType getBindType() {
		return bindType;
	}

	public void setBindType(BindType bindType) {
		this.bindType = bindType;
	}

	public String getBindKey() {
		return bindKey;
	}

	public void setBindKey(String bindKey) {
		this.bindKey = bindKey;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
