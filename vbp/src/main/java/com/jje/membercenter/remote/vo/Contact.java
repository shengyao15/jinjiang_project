package com.jje.membercenter.remote.vo;

import java.util.ArrayList;
import java.util.List;


public class Contact {
	

	private String conpriflag;
	//private String lastname;
	private String sex;
	private String race;
	private String custtype;
	private String lifecycle;
	private Listofpersonaladdress listofpersonaladdress;
	private String title;
	private String nation;
	private String hometown;
	private String birthday;
	private String cdtype;
	private String cdno;
	private String email;
	private String cell;
	private String edulevl;
	//private String idustyle;
	private String unit;
	private String position;
	private String cmpname;
	private String married;
/*	private String preference;
	private String hobby;
	private String hobdesp;
	*/
	public static class Listofpersonaladdress{
		private List<Personaladdress> personaladdress=new ArrayList<Personaladdress>();

		public List<Personaladdress> getPersonaladdress() {
			return personaladdress;
		}

		public void setPersonaladdress(List<Personaladdress> personaladdress) {
			this.personaladdress = personaladdress;
		}
		
	}
	public String getConpriflag() {
		return conpriflag;
	}
	public void setConpriflag(String conpriflag) {
		this.conpriflag = conpriflag;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public String getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}
	public Listofpersonaladdress getListofpersonaladdress() {
		return listofpersonaladdress;
	}
	public void setListofpersonaladdress(Listofpersonaladdress listofpersonaladdress) {
		this.listofpersonaladdress = listofpersonaladdress;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCdtype() {
		return cdtype;
	}
	public void setCdtype(String cdtype) {
		this.cdtype = cdtype;
	}
	public String getCdno() {
		return cdno;
	}
	public void setCdno(String cdno) {
		this.cdno = cdno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getEdulevl() {
		return edulevl;
	}
	public void setEdulevl(String edulevl) {
		this.edulevl = edulevl;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCmpname() {
		return cmpname;
	}
	public void setCmpname(String cmpname) {
		this.cmpname = cmpname;
	}
	public String getMarried() {
		return married;
	}
	public void setMarried(String married) {
		this.married = married;
	}

	
	
	

}
