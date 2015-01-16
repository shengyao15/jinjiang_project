package com.jje.membercenter.support.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.common.utils.MD5Utils;
import com.jje.dto.membercenter.WebMemberDto;
import com.jje.dto.membercenter.bigcustomer.CustomerAccountDto;
import com.jje.dto.membercenter.score.DataCodeDto;

public class DataCode {
	private Long id;
	private String name;
	private String code;
	private String val;
	private String type;
	private String describe;
	private String sort;
	private Date createDate;
	private String createPerson;
	private Date updateDate;
	private String updatePerson;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public DataCodeDto toDto() {
		DataCodeDto dto = new DataCodeDto();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}
}
