package com.jje.membercenter.domain;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.ReferrerInfoDto;

public class ReferrerInfo {

	private Long id;

	private Long memberInfoId;

	private String referrerCardNo;

	public ReferrerInfo() {
	}

	public ReferrerInfo(ReferrerInfoDto dto) {
		BeanUtils.copyProperties(dto, this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberInfoId() {
		return memberInfoId;
	}

	public void setMemberInfoId(Long memberInfoId) {
		this.memberInfoId = memberInfoId;
	}

	public String getReferrerCardNo() {
		return referrerCardNo;
	}

	public void setReferrerCardNo(String referrerCardNo) {
		this.referrerCardNo = referrerCardNo;
	}

}
