package com.jje.membercenter.remote.crm.datagram.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.common.utils.JaxbUtils;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.vo.Contact;

@XmlRootElement(name = "response")
public class MemberBaseInfoRes extends CrmResponse {
	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {
		private String membid;

		private String loypoint;

		private String cardno;

		private String cardstat;

		private String rightflg;

		private String cardtype;

		private Listofcontact listofcontact;

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

		public String getCardno() {
			return cardno;
		}

		public void setCardno(String cardno) {
			this.cardno = cardno;
		}

		public String getCardstat() {
			return cardstat;
		}

		public void setCardstat(String cardstat) {
			this.cardstat = cardstat;
		}

		public String getRightflg() {
			return rightflg;
		}

		public void setRightflg(String rightflg) {
			this.rightflg = rightflg;
		}

		public String getCardtype() {
			return cardtype;
		}

		public void setCardtype(String cardtype) {
			this.cardtype = cardtype;
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

}
