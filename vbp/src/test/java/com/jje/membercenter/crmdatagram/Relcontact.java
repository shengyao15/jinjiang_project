package com.jje.membercenter.crmdatagram;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jje.dto.travel.reservation.GuestCertificationTypeDto;
import com.jje.dto.travel.reservation.GuestSexDto;

public class Relcontact {
	private String relconfstname;
	private String relconlastname;
	private String relcontitle;
	private String relconcell;
	private String relconcdno;
	private String relconcdtype;
	private String relconbirthday;
	private String relconcomp;
	private String relcontype;
	private String relconfax;
	private String relconphone;
	private String sex;
	
	public String getRelconfstname() {
		return relconfstname;
	}

	public void setRelconfstname(String relconfstname) {
		this.relconfstname = relconfstname;
	}

	public String getRelconlastname() {
		return relconlastname;
	}

	public void setRelconlastname(String relconlastname) {
		this.relconlastname = relconlastname;
	}

	public String getRelcontitle() {
		return relcontitle;
	}

	public void setRelcontitle(String relcontitle) {
		this.relcontitle = relcontitle;
	}

	public String getRelconcell() {
		return relconcell;
	}

	public void setRelconcell(String relconcell) {
		this.relconcell = relconcell;
	}

	public String getRelconcdno() {
		return relconcdno;
	}

	public void setRelconcdno(String relconcdno) {
		this.relconcdno = relconcdno;
	}

	public String getRelconcdtype() {
		return relconcdtype;
	}

	public void setRelconcdtype(String relconcdtype) {
		this.relconcdtype = relconcdtype;
	}

	public String getRelconbirthday() {
		return relconbirthday;
	}

	public void setRelconbirthday(String relconbirthday) {
		this.relconbirthday = relconbirthday;
	}

	public String getRelconcomp() {
		return relconcomp;
	}

	public void setRelconcomp(String relconcomp) {
		this.relconcomp = relconcomp;
	}

	public String getRelcontype() {
		return relcontype;
	}

	public void setRelcontype(String relcontype) {
		this.relcontype = relcontype;
	}

	public String getRelconfax() {
		return relconfax;
	}

	public void setRelconfax(String relconfax) {
		this.relconfax = relconfax;
	}

	public String getRelconphone() {
		return relconphone;
	}

	public void setRelconphone(String relconphone) {
		this.relconphone = relconphone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public GuestCertificationTypeDto convertCertificationType(){
		Map<String,GuestCertificationTypeDto> mapping=new HashMap<String,GuestCertificationTypeDto>();
		mapping.put("ID", GuestCertificationTypeDto.NATIONALID);
		mapping.put("Soldier", GuestCertificationTypeDto.MILITARYID);
		mapping.put("Passport", GuestCertificationTypeDto.PASSPORT);
		mapping.put("Taiwan", GuestCertificationTypeDto.TAIWAN);
		if(StringUtils.isEmpty(relconcdtype)){
			return GuestCertificationTypeDto.NOTHING;
 		}
		if(mapping.get(relconcdtype)==null){
			return GuestCertificationTypeDto.OTHER;
		}
		return mapping.get(relconcdtype);
	}
	
	public GuestSexDto  convertSex(){
		Map<String,GuestSexDto> mapping=new HashMap<String,GuestSexDto>();
		mapping.put("Mr.", GuestSexDto.MALE);
		mapping.put("Ms.", GuestSexDto.FEMALE);
		mapping.put("Unknown", GuestSexDto.UNKNOWN);
        if(StringUtils.isEmpty(relcontitle)){
            return GuestSexDto.UNKNOWN;
        }
		return mapping.get(relcontitle);
	}
	
	public Date convertBirthday() throws ParseException{
		if(StringUtils.isEmpty(relconbirthday)){
			return null;
		}
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		return format.parse(relconbirthday);
	}
}
