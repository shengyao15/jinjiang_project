package com.jje.membercenter.remote.crm.datagram.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.account.xsd.AccountValidationResponse.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name="response")
public class GoldpalMemberQueryRes extends CrmResponse {
	
	private RequestBody body;
	
	public RequestBody membercard() {
		
		return body;
	}


	public void setBody(RequestBody body) {
		this.body = body;
	}
	
	
	public static class RequestBody extends Body{
		
		private Record record;

		public Record getRecord() {
			return record;
		}

		public void setRecord(Record record) {
			this.record = record;
		}
		
	}
	public static class Record{
		private String pwdanswer;
		private String pwdquestion;
		private String passwd;
		private String cddesp;
		private String regichnl;
		private String rightflag;
		private String membid;
		private String membtype;
		private String name;
		private String loypoint;
		private String Point2Value;
		private String Point3Value;
		private String tier;
		private String regidate;
		private String membstat;
		
		private Listofcontact listofcontact;
		private Listofmembercard listofmembercard;
		
		
		public Record() {
			
		}
		
		public Record(String pwdanswer, String pwdquestion, String passwd,
				String cddesp, String regichnl, String rightflag,
				String membid, String membtype, String name, String loypoint,
				String point2Value, String point3Value, String tier,
				String regidate, String membstat, Listofcontact listofcontact,
				Listofmembercard listofmembercard) {
			this.pwdanswer = pwdanswer;
			this.pwdquestion = pwdquestion;
			this.passwd = passwd;
			this.cddesp = cddesp;
			this.regichnl = regichnl;
			this.rightflag = rightflag;
			this.membid = membid;
			this.membtype = membtype;
			this.name = name;
			this.loypoint = loypoint;
			Point2Value = point2Value;
			Point3Value = point3Value;
			this.tier = tier;
			this.regidate = regidate;
			this.membstat = membstat;
			this.listofcontact = listofcontact;
			this.listofmembercard = listofmembercard;
		}


		public String getPwdanswer() {
			return pwdanswer;
		}

		public void setPwdanswer(String pwdanswer) {
			this.pwdanswer = pwdanswer;
		}

		public String getPwdquestion() {
			return pwdquestion;
		}

		public void setPwdquestion(String pwdquestion) {
			this.pwdquestion = pwdquestion;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getLoypoint() {
			return loypoint;
		}

		public void setLoypoint(String loypoint) {
			this.loypoint = loypoint;
		}

		public String getPoint2Value() {
			return Point2Value;
		}

		public void setPoint2Value(String point2Value) {
			Point2Value = point2Value;
		}

		public String getPoint3Value() {
			return Point3Value;
		}

		public void setPoint3Value(String point3Value) {
			Point3Value = point3Value;
		}

		public String getTier() {
			return tier;
		}

		public void setTier(String tier) {
			this.tier = tier;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMembtype() {
			return membtype;
		}
		public void setMembtype(String membtype) {
			this.membtype = membtype;
		}
		public String getMembstat() {
			return membstat;
		}
		public void setMembstat(String membstat) {
			this.membstat = membstat;
		}
		
		public String getCddesp() {
			return cddesp;
		}
		public void setCddesp(String cddesp) {
			this.cddesp = cddesp;
		}
		
		public Listofcontact getListofcontact() {
			return listofcontact;
		}
		public void setListofcontact(Listofcontact listofcontact) {
			this.listofcontact = listofcontact;
		}
		public Listofmembercard getListofmembercard() {
			return listofmembercard;
		}
		public void setListofmembercard(Listofmembercard listofmembercard) {
			this.listofmembercard = listofmembercard;
		}
		public String getRegidate() {
			return regidate;
		}
		public void setRegidate(String regidate) {
			this.regidate = regidate;
		}
		public String getRegichnl() {
			return regichnl;
		}
		public void setRegichnl(String regichnl) {
			this.regichnl = regichnl;
		}

		@XmlElement(name="rightflag",defaultValue="")
		public String getRightflag() {
			return rightflag;
		}

		public void setRightflag(String rightflag) {
			this.rightflag = rightflag;
		}
		
		
	}
	
	
	public static class Listofmembercard{
		
		private List<Membercard> membercard=new ArrayList<Membercard>();

		public List<Membercard> getMembercard() {
			return membercard;
		}

		public void setMembercard(List<Membercard> membercard) {
			this.membercard = membercard;
		}
		
	}
	
	
	public static class Membercard{	
		private String membcdid;
		private String membcdstat;
		private String membcdtype;
		private String enddate;
		private String partnercard;
		private String membcdsour;
		private String startdate;
		private String accpatpflg;
		
		public Membercard() {
			super();
		}
		
		public Membercard(String membcdid, String membcdstat,
				String membcdtype, String enddate, String partnercard,
				String membcdsour, String startdate, String accpatpflg) {
			this.membcdid = membcdid;
			this.membcdstat = membcdstat;
			this.membcdtype = membcdtype;
			this.enddate = enddate;
			this.partnercard = partnercard;
			this.membcdsour = membcdsour;
			this.startdate = startdate;
			this.accpatpflg = accpatpflg;
		}





		public String getMembcdsour() {
			return membcdsour;
		}

		public void setMembcdsour(String membcdsour) {
			this.membcdsour = membcdsour;
		}

		public String getMembcdstat() {
			return membcdstat;
		}

		public void setMembcdstat(String membcdstat) {
			this.membcdstat = membcdstat;
		}

		public String getMembcdid() {
			return membcdid;
		}
		public void setMembcdid(String membcdid) {
			this.membcdid = membcdid;
		}
		public String getMembcdtype() {
			return membcdtype;
		}
		public void setMembcdtype(String membcdtype) {
			this.membcdtype = membcdtype;
		}
		public String getPartnercard() {
			return partnercard;
		}
		public void setPartnercard(String partnercard) {
			this.partnercard = partnercard;
		}
		public String getStartdate() {
			return startdate;
		}
		public void setStartdate(String startdate) {
			this.startdate = startdate;
		}
		public String getEnddate() {
			return enddate;
		}
		public void setEnddate(String enddate) {
			this.enddate = enddate;
		}
		public String getAccpatpflg() {
			return accpatpflg;
		}
		public void setAccpatpflg(String accpatpflg) {
			this.accpatpflg = accpatpflg;
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

	public static class Contact {
		private String language;
		private String mobile;
		private String nation;
		private String email;
		private String postflg;
		private String cardid;
		private String cardtype;
		private String invflg;
		private String unitname;
		private String race;
		private String sex;
		private String income;
		private String conpriflag;
		private String birthday;
		private String ContactFirstName;
		private String ContactLastName;
		private String position;
		private String title;
		private String courcmp;
		

		public Contact() {
		}
		
		public Contact(String language, String mobile, String nation,
				String email, String postflg, String cardid, String cardtype,
				String invflg, String unitname, String race, String sex,
				String income, String conpriflag, String birthday,
				String contactFirstName, String contactLastName,
				String position, String title, String courcmp) {
			this.language = language;
			this.mobile = mobile;
			this.nation = nation;
			this.email = email;
			this.postflg = postflg;
			this.cardid = cardid;
			this.cardtype = cardtype;
			this.invflg = invflg;
			this.unitname = unitname;
			this.race = race;
			this.sex = sex;
			this.income = income;
			this.conpriflag = conpriflag;
			this.birthday = birthday;
			ContactFirstName = contactFirstName;
			ContactLastName = contactLastName;
			this.position = position;
			this.title = title;
			this.courcmp = courcmp;
		}


		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getInvflg() {
			return invflg;
		}

		public void setInvflg(String invflg) {
			this.invflg = invflg;
		}

		public String getUnitname() {
			return unitname;
		}

		public void setUnitname(String unitname) {
			this.unitname = unitname;
		}

		public String getRace() {
			return race;
		}

		public void setRace(String race) {
			this.race = race;
		}

		public String getIncome() {
			return income;
		}

		public void setIncome(String income) {
			this.income = income;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public String getContactFirstName() {
			return ContactFirstName;
		}

		public void setContactFirstName(String contactFirstName) {
			ContactFirstName = contactFirstName;
		}

		public String getContactLastName() {
			return ContactLastName;
		}

		public void setContactLastName(String contactLastName) {
			ContactLastName = contactLastName;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
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

		public String getCardtype() {
			return cardtype;
		}

		public void setCardtype(String cardtype) {
			this.cardtype = cardtype;
		}

		public String getCardid() {
			return cardid;
		}

		public void setCardid(String cardid) {
			this.cardid = cardid;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getPostflg() {
			return postflg;
		}

		public void setPostflg(String postflg) {
			this.postflg = postflg;
		}

		public String getCourcmp() {
			return courcmp;
		}

		public void setCourcmp(String courcmp) {
			this.courcmp = courcmp;
		}

	}

	public RequestBody getBody() {
		return body;
	}
	
	
	

}
