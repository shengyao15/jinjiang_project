package com.jje.vbp.memberRecommend.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.recommend.MemberRecommendDto;

public class MemberRecommendDomain {

	private long id;
	private String recommenderId;
	private String registerId;
	private String campaign;
	private Date createDate;
	private Date updateDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRecommenderId() {
		return recommenderId;
	}

	public void setRecommenderId(String recommenderId) {
		this.recommenderId = recommenderId;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
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

	public MemberRecommendDto toDto() {
		MemberRecommendDto dto = new MemberRecommendDto();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}
}
