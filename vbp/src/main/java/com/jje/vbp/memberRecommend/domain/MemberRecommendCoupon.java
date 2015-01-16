package com.jje.vbp.memberRecommend.domain;

import java.util.Date;

public class MemberRecommendCoupon {

	private Long id;

	private String recommenderId;

	private String campaign;

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	private Date createDate;

	public MemberRecommendCoupon() {
		super();
	}

	public MemberRecommendCoupon(String recommenderId) {
		super();
		this.recommenderId = recommenderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecommenderId() {
		return recommenderId;
	}

	public void setRecommenderId(String recommenderId) {
		this.recommenderId = recommenderId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
