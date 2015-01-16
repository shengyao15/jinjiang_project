package com.jje.vbp.sns.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.dto.vbp.sns.ThirdpartyBindDto;

public class ThirdpartyBind {
	
	private Long id;
	
	private String mcMemberCode;
	
	private String memberId;
	
	private String thirdpartyType;
	
	private String thirdpartySign;
	
	private String thirdpartyLevel;
	
	private String vipFlag;
	
	private String originLevel;
	
	private String destinationLevel;
	
	private String registerFlag;
	
	private String scoreFlag;
	
	private String couponFlag;
	
	private Date createDate;
	
	public ThirdpartyBind(){}
	
	public ThirdpartyBind(ThirdpartyBindDto dto){
		this.id = dto.getId();
		this.mcMemberCode = dto.getMcMemberCode();
		this.memberId = dto.getMemberId();
		this.thirdpartyType = dto.getThirdpartyType();
		this.thirdpartySign = dto.getThirdpartySign();
		this.thirdpartyLevel = dto.getThirdpartyLevel();
		this.vipFlag = dto.getVipFlag();
	}
	
	public ThirdpartyBindDto toDto(){
		ThirdpartyBindDto dto = new ThirdpartyBindDto();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMcMemberCode() {
		return mcMemberCode;
	}

	public void setMcMemberCode(String mcMemberCode) {
		this.mcMemberCode = mcMemberCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getThirdpartyType() {
		return thirdpartyType;
	}

	public void setThirdpartyType(String thirdpartyType) {
		this.thirdpartyType = thirdpartyType;
	}

	public String getThirdpartySign() {
		return thirdpartySign;
	}

	public void setThirdpartySign(String thirdpartySign) {
		this.thirdpartySign = thirdpartySign;
	}

	public String getThirdpartyLevel() {
		return thirdpartyLevel;
	}

	public void setThirdpartyLevel(String thirdpartyLevel) {
		this.thirdpartyLevel = thirdpartyLevel;
	}

	public String getVipFlag() {
		return vipFlag;
	}

	public void setVipFlag(String vipFlag) {
		this.vipFlag = vipFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOriginLevel() {
		return originLevel;
	}

	public void setOriginLevel(String originLevel) {
		this.originLevel = originLevel;
	}

	public String getDestinationLevel() {
		return destinationLevel;
	}

	public void setDestinationLevel(String destinationLevel) {
		this.destinationLevel = destinationLevel;
	}

	public String getRegisterFlag() {
		return registerFlag;
	}

	public void setRegisterFlag(String registerFlag) {
		this.registerFlag = registerFlag;
	}

	public String getScoreFlag() {
		return scoreFlag;
	}

	public void setScoreFlag(String scoreFlag) {
		this.scoreFlag = scoreFlag;
	}

	public String getCouponFlag() {
		return couponFlag;
	}

	public void setCouponFlag(String couponFlag) {
		this.couponFlag = couponFlag;
	}
	
}
