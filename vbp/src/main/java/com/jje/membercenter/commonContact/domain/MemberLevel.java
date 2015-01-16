package com.jje.membercenter.commonContact.domain;

import org.apache.commons.lang.StringUtils;

import com.jje.dto.IdentityType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.membercenter.contact.MemberLevelDto;

public class MemberLevel {

	private MemberLevelDto dto;

	public MemberLevel() {
		dto = new MemberLevelDto();
	}

	public MemberLevel(MemberLevelDto dto) {
		this.dto = dto;
	}

	public MemberLevel(MemberDto memberDto) {
		this();
		dto.setCardNo(memberDto.getCardNo());
		dto.setCardType(NewCardType.valueOfCode(memberDto.getCardType()));
		dto.setMcMemberCode(memberDto.getMcMemberCode());
	}

	public String getName() {
		return dto.getName();
	}
	
	public String getIdentityType() {
		if (StringUtils.isBlank(dto.getIdentityType())) {
			return null;
		}
		return IdentityType.valueOfByGuestIdentityTypeCode(dto.getIdentityType()).getCode();
	}

	public String getIdentityNo() {
		return dto.getIdentityNo();
	}

	public String getMcMemberCode() {
		return dto.getMcMemberCode();
	}

	public void setMcMemberCode(String mcMemberCode) {
		dto.setMcMemberCode(mcMemberCode);
	}

	public String getCardNo() {
		return dto.getCardNo();
	}

	public void setCardNo(String cardNo) {
		dto.setCardNo(cardNo);
	}

	public String getScoreLevel() {
		return dto.getScoreLevel();
	}

	public void setScoreLevel(String scoreLevel) {
		dto.setScoreLevel(scoreLevel);
	}

	public MemberLevelDto toDto() {
		return dto;
	}
	
	

}
