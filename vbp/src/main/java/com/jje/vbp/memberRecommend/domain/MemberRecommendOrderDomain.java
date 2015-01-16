package com.jje.vbp.memberRecommend.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.recommend.MemberRecommendOrderDto;

public class MemberRecommendOrderDomain {

	private long id;
	private String recommenderId;
	private String memberId;
	private String memberName;
	private int scoreLevel;
	private Date bookDate;

	

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



	public String getMemberId() {
		return memberId;
	}



	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}



	public String getMemberName() {
		return memberName;
	}



	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}



	public int getScoreLevel() {
		return scoreLevel;
	}



	public void setScoreLevel(int scoreLevel) {
		this.scoreLevel = scoreLevel;
	}



	public Date getBookDate() {
		return bookDate;
	}



	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}



	public MemberRecommendOrderDto toDto() {
		MemberRecommendOrderDto dto = new MemberRecommendOrderDto();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}
}
