package com.jje.vbp.bigcustomer.domain;

import java.util.Date;

import com.jje.dto.membercenter.bigcustomer.BigCustomerDto;

public class BigCustomer {

	private Long id;

	private String name;

    @Deprecated
	private Long accountId;

	private String channel;

	private Date createDate;

	private Date updateDate;

	private String contactName;

	private String email;

	private String phone;

    private String mcMemberCode;
    
    private String crmId;

    public BigCustomer(BigCustomerDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.accountId = dto.getAccountId();
        this.channel = dto.getChannel();
        this.contactName = dto.getContactName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.mcMemberCode =dto.getMcMemberCode();
        this.crmId = dto.getCrmId();
    }
    public BigCustomer(){}

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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigCustomerDto toDto() {
		BigCustomerDto dto = new BigCustomerDto(id, name, accountId, channel,contactName,email,phone,mcMemberCode);
		dto.setCrmId(crmId);
		return dto;
	}

    public String getMcMemberCode() {
        return mcMemberCode;
    }

    public void setMcMemberCode(String mcMemberCode) {
        this.mcMemberCode = mcMemberCode;
    }
	public String getCrmId() {
		return crmId;
	}
	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}
}
