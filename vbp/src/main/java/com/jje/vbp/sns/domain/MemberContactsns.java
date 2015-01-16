package com.jje.vbp.sns.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.dto.vbp.sns.MemberContactsnsDto;

public class MemberContactsns {
	
	private Long id;
	
	private String memberId;
	
	private String useType;
	
	private String sns;
	
	private String status;
	
	private Date bindDate;
	
	private Date endDate;
	
	private String bindChannel;
	
	private String comments;
	
	public MemberContactsns(){}
	
	public MemberContactsns(MemberContactsnsDto dto){
		BeanUtils.copyProperties(dto, this);
	}
	
	public MemberContactsnsDto toDto(){
		MemberContactsnsDto dto = new MemberContactsnsDto();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getUseType() {
		return useType;
	}


	public void setUseType(String useType) {
		this.useType = useType;
	}


	public String getSns() {
		return sns;
	}


	public void setSns(String sns) {
		this.sns = sns;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getBindDate() {
		return bindDate;
	}


	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getBindChannel() {
		return bindChannel;
	}


	public void setBindChannel(String bindChannel) {
		this.bindChannel = bindChannel;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
}
