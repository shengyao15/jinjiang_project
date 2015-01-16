package com.jje.membercenter.remote.vo;



public class Personaladdress {
	private String addpriflag;
	private String addrtype;
	private String address;
	private String province;
	private String city;
	private String area;
	private String streetaddr;
	private String postcode;
	private String nation;
	
	public Personaladdress() {
	}
	
	public Personaladdress(String addpriflag, String addrtype, String address,
			String province, String city, String area, String streetaddr,
			String postcode,String nation) {
		super();
		this.addpriflag = addpriflag;
		this.addrtype = addrtype;
		this.address = address;
		this.province = province;
		this.city = city;
		this.area = area;
		this.streetaddr = streetaddr;
		this.postcode = postcode;
		this.nation = nation;
	}




	public String getAddpriflag() {
		return addpriflag;
	}

	public void setAddpriflag(String addpriflag) {
		this.addpriflag = addpriflag;
	}

	public String getAddrtype() {
		return addrtype;
	}

	public void setAddrtype(String addrtype) {
		this.addrtype = addrtype;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreetaddr() {
		return streetaddr;
	}

	public void setStreetaddr(String streetaddr) {
		this.streetaddr = streetaddr;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public String getNation() {
		return nation;
	}


	public void setNation(String nation) {
		this.nation = nation;
	}
	
	

}
