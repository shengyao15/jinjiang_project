package com.jje.membercenter.domain;

import java.util.Date;

public class MemberCoupon {
  
	private long id;
	
	private String memberId;
	
	private String useModule;
	
	private Date createTime;
	
	private String couponNo;
	
	private int status;

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

	public String getUseModule() {
		return useModule;
	}

	public void setUseModule(String useModule) {
		this.useModule = useModule;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
