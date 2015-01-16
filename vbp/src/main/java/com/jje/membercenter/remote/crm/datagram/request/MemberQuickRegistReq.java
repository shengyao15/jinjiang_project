package com.jje.membercenter.remote.crm.datagram.request;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.MemberQuickRegistRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;
import com.jje.membercenter.remote.vo.Contact.Listofpersonaladdress;

@XmlRootElement(name = "request")
@Component
public class MemberQuickRegistReq extends CrmRequest {
	public MemberQuickRegistReq() {
		super(CrmTransCode.QUICK_REGIST);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public MemberQuickRegistRes send() {
		return this.sendToType(MemberQuickRegistRes.class);
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

        private String regichnl;

        private String regichnltag;

		private Listofcontact listofcontact;

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

        public String getRegichnl() {
            return regichnl;
        }

        public void setRegichnl(String regichnl) {
            this.regichnl = regichnl;
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
		private String cardid;
		private String cardtype;
		private String postflg;

		private Listofpersonaladdress listofpersonaladdress;

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

		public String getPostflg() {
			return postflg;
		}

		public void setPostflg(String postflg) {
			this.postflg = postflg;
		}

		public Listofpersonaladdress getListofpersonaladdress() {
			return listofpersonaladdress;
		}

		public void setListofpersonaladdress(Listofpersonaladdress listofpersonaladdress) {
			this.listofpersonaladdress = listofpersonaladdress;
		}

	}

}
