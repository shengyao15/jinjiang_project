package com.jje.membercenter.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.ValidateCodeDto;

public class ValidateCode {
	private Long id;
	private String receiver;
	private String code;
	private Date createTime;
	
	public ValidateCode(){
		super();
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public ValidateCode(ValidateCodeDto dto){
		BeanUtils.copyProperties(dto, this);
	}
}
