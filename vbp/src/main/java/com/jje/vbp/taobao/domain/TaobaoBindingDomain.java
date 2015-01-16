package com.jje.vbp.taobao.domain;

import java.util.Date;

public class TaobaoBindingDomain {

	private Long id;
	private String taobaoId;
	private String taobaoLevel;
	private String memberId;
	private String status = "UNBIND";// UNBIND,BINDED
	private String bindMode = "LOGIN";// LOGIN,REGISTER
	private Date createDate;
	private Date updateDate;

	public TaobaoBindingDomain() {
		super();
	}

	public TaobaoBindingDomain(String taobaoId, String memberId, String taobaoLevel) {
		this();
		this.taobaoId = taobaoId;
		this.memberId = memberId;
		this.taobaoLevel = taobaoLevel;
	}

	public TaobaoBindingDomain(String taobaoId, String memberId, String taobaoLevel, String status, String mode) {
		this.taobaoId = taobaoId;
		this.memberId = memberId;
		this.status = status;
		this.bindMode = mode;
        this.taobaoLevel = taobaoLevel;
	}

	public String getTaobaoId() {
		return taobaoId;
	}

	public void setTaobaoId(String taobaoId) {
		this.taobaoId = taobaoId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBindMode() {
		return bindMode;
	}

	public void setBindMode(String bindMode) {
		this.bindMode = bindMode;
	}

	public String getTaobaoLevel() {
		return taobaoLevel;
	}

	public void setTaobaoLevel(String taobaoLevel) {
		this.taobaoLevel = taobaoLevel;
	}

	@Override
	public String toString() {
		return "TaobaoBindingDomain [id=" + id + ", taobaoId=" + taobaoId + ", taobaoLevel=" + taobaoLevel + ", memberId=" + memberId + ", status=" + status
				+ ", bindMode=" + bindMode + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}

}
