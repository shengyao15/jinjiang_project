package com.jje.membercenter.commonContact.domain;

import com.jje.dto.SexDto;
import com.jje.dto.membercenter.contact.CommonlyUsedContactDto;
import com.jje.dto.travel.reservation.GuestCategoryDto;
import com.jje.dto.travel.reservation.GuestCertificationTypeDto;

import java.util.Date;

public class CommonlyUsedContact {

	private CommonlyUsedContactDto dto;

	public CommonlyUsedContact() {
		dto = new CommonlyUsedContactDto();
	}

	public CommonlyUsedContact(CommonlyUsedContactDto dto) {
		this.dto = dto;
	}

	public Long getId() {
		return dto.getId();
	}

	public void setId(Long id) {
		dto.setId(id);
	}

	public String getMcMemberCode() {
		return dto.getMcMemberCode();
	}

	public void setMcMemberCode(String mcMemberCode) {
		dto.setMcMemberCode(mcMemberCode);
	}

	public String getName() {
		return dto.getName();
	}

	public void setName(String name) {
		dto.setName(name);
	}

	public GuestCertificationTypeDto getIdentityType() {
		return dto.getIdentityType();
	}

	public void setIdentityType(GuestCertificationTypeDto identityType) {
		dto.setIdentityType(identityType);
	}

	public GuestCategoryDto getCategory() {
		return dto.getCategory();
	}

	public void setCategory(GuestCategoryDto category) {
		dto.setCategory(category);
	}

	public Date getUpdateDate() {
		return dto.getUpdateDate();
	}

	public void setUpdateDate(Date updateDate) {
		dto.setUpdateDate(updateDate);
	}

	public String getIdentityNo() {
		return dto.getIdentityNo();
	}

	public void setIdentityNo(String identityNo) {
		dto.setIdentityNo(identityNo);
	}

	public Date getBirthday() {
		return dto.getBirthday();
	}

	public void setBirthday(Date birthday) {
		dto.setBirthday(birthday);
	}

	public String getPhone() {
		return dto.getPhone();
	}

	public void setPhone(String phone) {
		dto.setPhone(phone);
	}

	public String getCardNo() {
		return dto.getCardNo();
	}

	public void setCardNo(String cardNo) {
		dto.setCardNo(cardNo);
	}

	public SexDto getGender() {
		return dto.getGender();
	}

	public void setGender(SexDto gender) {
		dto.setGender(gender);
	}

	public Date getCreateDate() {
		return dto.getCreateDate();
	}

	public void setCreateDate(Date createDate) {
		dto.setCreateDate(createDate);
	}

	public String getEnFirstName() {
		return dto.getEnFirstName() != null ? dto.getEnFirstName()
				.toUpperCase() : null;
	}

	public void setEnFirstName(String enFirstName) {
		dto.setEnFirstName(enFirstName);
	}

	public String getEnLastName() {
		return dto.getEnLastName() != null ? dto.getEnLastName().toUpperCase()
				: null;
	}

	public void setEnLastName(String enLastName) {
		dto.setEnLastName(enLastName);
	}

	public String getCertificateAddress() {
		return dto.getCertificateAddress();
	}

	public void setCertificateAddress(String certificateAddress) {
		dto.setCertificateAddress(certificateAddress);
	}

	public Date getCertificateDate() {
		return dto.getCertificateDate();
	}

	public void setCertificateDate(Date certificateDate) {
		dto.setCertificateDate(certificateDate);
	}

	public String getLabel() {
		return dto.getLabel();
	}

	public void setLabel(String label) {
		dto.setLabel(label);
	}

	public String toString() {
		return dto.toString();
	}

	public CommonlyUsedContactDto toDto() {
		return dto;
	}

}
