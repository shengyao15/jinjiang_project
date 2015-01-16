package com.jje.membercenter.remote.crm.datagram.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.dto.membercenter.AddressDto;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name = "response")
public class MemberQueryAddressRes extends CrmResponse {
	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {

		private String membid;

		private Listofcontact listofcontact;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public Listofcontact getListofcontact() {
			return listofcontact;
		}

		public void setListofcontact(Listofcontact listofcontact) {
			this.listofcontact = listofcontact;
		}
		
	}

	public static class Listofcontact {
		private List<Contact> contact = new ArrayList<Contact>();

		public List<Contact> getContact() {
			return contact;
		}

		public void setContact(List<Contact> contact) {
			this.contact = contact;
		}

	}
	
	public static class Listofpersonaladdress {
		
		private List<Personaladdress> personaladdress=new ArrayList<Personaladdress>();

		public List<Personaladdress> getPersonaladdress() {
			return personaladdress;
		}

		public void setPersonaladdress(List<Personaladdress> personaladdress) {
			this.personaladdress = personaladdress;
		}
	}
	
	public static class Personaladdress {
		
		private String addrpriflag;
		private String addrnumber;
		private String addrtype;
		private String addr;
		private String continent;
		private String state;
		private String province;
		private String city;
		private String area;
		private String street;
		private String postcode;
		private String updt;

		public String getAddrpriflag() {
			return addrpriflag;
		}

		public void setAddrpriflag(String addrpriflag) {
			this.addrpriflag = addrpriflag;
		}

		public String getAddrnumber() {
			return addrnumber;
		}

		public void setAddrnumber(String addrnumber) {
			this.addrnumber = addrnumber;
		}

		public String getAddrtype() {
			return addrtype;
		}

		public void setAddrtype(String addrtype) {
			this.addrtype = addrtype;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getContinent() {
			return continent;
		}

		public void setContinent(String continent) {
			this.continent = continent;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
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

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getPostcode() {
			return postcode;
		}

		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}

		public String getUpdt() {
			return updt;
		}

		public void setUpdt(String updt) {
			this.updt = updt;
		}
		
		public AddressDto toAddressDto() {
			AddressDto condition = new AddressDto();
			condition.setCityId(city);
			condition.setProvinceId(province);
			condition.setDistrictId(area);
			return condition;
		}
	}

	public static class Contact {
		
		private String lastname;
		
		private Listofpersonaladdress listofpersonaladdress;

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public Listofpersonaladdress getListofpersonaladdress() {
			return listofpersonaladdress;
		}

		public void setListofpersonaladdress(Listofpersonaladdress listofpersonaladdress) {
			this.listofpersonaladdress = listofpersonaladdress;
		}
		
		

	}
}
