package com.jje.vbp.bigcustomer.domain;

import com.jje.common.utils.MD5Utils;
import com.jje.dto.membercenter.bigcustomer.CustomerAccountDto;

public class CustomerAccount {
	private Long id;

	private String username;

	private String password;
	
	public CustomerAccount() {
	}

	public CustomerAccount(CustomerAccountDto dto) {
		this.username = dto.getUsername();
		this.password = MD5Utils.generatePassword(dto.getPassword());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
