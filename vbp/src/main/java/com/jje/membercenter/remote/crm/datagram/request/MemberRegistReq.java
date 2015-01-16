package com.jje.membercenter.remote.crm.datagram.request;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


import com.jje.membercenter.remote.crm.datagram.response.MemberRegistRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;
import com.jje.membercenter.remote.vo.Contact.Listofpersonaladdress;

@XmlRootElement(name = "request")
public class MemberRegistReq extends CrmRequest {
	public MemberRegistReq() {
		super(CrmTransCode.REGISTER_MEMBER);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public MemberRegistRes send() {
		return this.sendToType(MemberRegistRes.class);
	}

	public static class RequestBody extends Body {

		private Record record;

		public Record getRecord() {
			return record;
		}

		public void setRecord(Record record) {
			this.record = record;
		}

	}

	public static class Record {
		private String name;
		private String passwd;
		private String pwdquestion;
		private String pwdanswer;
		private String tier;
		private String jjcardtype;
		private String invc;
		private String partnercard;
		private String partnerflag;
		private String partnername;
		private String regichnl;
		private String thirdpartyType;
        private String regichnltag;
        private String activeSubChannel;

		private Listofcontact listofcontact;


        public String getActiveSubChannel() {
            return activeSubChannel;
        }

        public void setActiveSubChannel(String activeSubChannel) {
            this.activeSubChannel = activeSubChannel;
        }

        public Listofcontact getListofcontact() {
			return listofcontact;
		}

		public void setListofcontact(Listofcontact listofcontact) {
			this.listofcontact = listofcontact;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}

		public String getPwdquestion() {
			return pwdquestion;
		}

		public void setPwdquestion(String pwdquestion) {
			this.pwdquestion = pwdquestion;
		}

		public String getPwdanswer() {
			return pwdanswer;
		}

		public void setPwdanswer(String pwdanswer) {
			this.pwdanswer = pwdanswer;
		}

		public String getTier() {
			return tier;
		}

		public void setTier(String tier) {
			this.tier = tier;
		}

		public String getJjcardtype() {
			return jjcardtype;
		}

		public void setJjcardtype(String jjcardtype) {
			this.jjcardtype = jjcardtype;
		}

		public String getInvc() {
			return invc;
		}

		public void setInvc(String invc) {
			this.invc = invc;
		}

		public String getPartnercard() {
			return partnercard;
		}

		public void setPartnercard(String partnercard) {
			this.partnercard = partnercard;
		}

		public String getPartnerflag() {
			return partnerflag;
		}

		public void setPartnerflag(String partnerflag) {
			this.partnerflag = partnerflag;
		}

		public String getPartnername() {
			return partnername;
		}

		public void setPartnername(String partnername) {
			this.partnername = partnername;
		}

        public String getRegichnl() {
            return regichnl;
        }

        public void setRegichnl(String regichnl) {
            this.regichnl = regichnl;
        }

		public String getThirdpartyType() {
			return thirdpartyType;
		}

		public void setThirdpartyType(String thirdpartyType) {
			this.thirdpartyType = thirdpartyType;
		}

        public String getRegichnltag() {
            return regichnltag;
        }

        public void setRegichnltag(String regichnltag) {
            this.regichnltag = regichnltag;
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
		private String conpriflag;
		private String title;
		private String email;
		private String mobile;
		private String postflg;
		private String invflg;
		private String emailbill;
		private String emailpro;
		private String emailinvestigate;
		private String emailpartner;
		private String emailepro;
		private String cardid;
		private String cardtype;
		private Listofpersonaladdress listofpersonaladdress;

		public Listofpersonaladdress getListofpersonaladdress() {
			return listofpersonaladdress;
		}

		public void setListofpersonaladdress(Listofpersonaladdress listofpersonaladdress) {
			this.listofpersonaladdress = listofpersonaladdress;
		}

		public String getConpriflag() {
			return conpriflag;
		}

		public void setConpriflag(String conpriflag) {
			this.conpriflag = conpriflag;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
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

		public String getInvflg() {
			return invflg;
		}

		public void setInvflg(String invflg) {
			this.invflg = invflg;
		}

		public String getEmailbill() {
			return emailbill;
		}

		public void setEmailbill(String emailbill) {
			this.emailbill = emailbill;
		}

		public String getEmailpro() {
			return emailpro;
		}

		public void setEmailpro(String emailpro) {
			this.emailpro = emailpro;
		}

		public String getEmailinvestigate() {
			return emailinvestigate;
		}

		public void setEmailinvestigate(String emailinvestigate) {
			this.emailinvestigate = emailinvestigate;
		}

		public String getEmailpartner() {
			return emailpartner;
		}

		public void setEmailpartner(String emailpartner) {
			this.emailpartner = emailpartner;
		}

		public String getEmailepro() {
			return emailepro;
		}

		public void setEmailepro(String emailepro) {
			this.emailepro = emailepro;
		}

		public String getCardid() {
			return cardid;
		}

		public void setCardid(String cardid) {
			this.cardid = cardid;
		}

		public String getCardtype() {
			return cardtype;
		}

		public void setCardtype(String cardtype) {
			this.cardtype = cardtype;
		}
	}

}
