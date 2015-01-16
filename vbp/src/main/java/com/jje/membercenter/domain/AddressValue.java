package com.jje.membercenter.domain;

import com.jje.dto.membercenter.AddressDto;

public class AddressValue {
	private String cityValue;
	private String districtValue;
	private String provinceValue;

	public String getCityValue() {
		return cityValue;
	}

	public void setCityValue(String cityValue) {
		this.cityValue = cityValue;
	}

	public String getDistrictValue() {
		return districtValue;
	}

	public void setDistrictValue(String districtValue) {
		this.districtValue = districtValue;
	}

	public String getProvinceValue() {
		return provinceValue;
	}

	public void setProvinceValue(String provinceValue) {
		this.provinceValue = provinceValue;
	}

	public AddressValue(String cityValue, String districtValue,
			String provinceValue) {
		super();
		this.cityValue = cityValue;
		this.districtValue = districtValue;
		this.provinceValue = provinceValue;
	}
	
	public AddressValue() {
		// TODO Auto-generated constructor stub
	}

	public AddressDto toDto() {
		AddressDto addressDto = new AddressDto();
		addressDto.setProvinceId(this.provinceValue);
		addressDto.setCityId(this.getCityValue());
		addressDto.setDistrictId(this.getDistrictValue());
		return addressDto;
	}

}
