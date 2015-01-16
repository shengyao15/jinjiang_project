package com.jje.membercenter.remote.crm.datagram.request;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.MemberBaseInfoUpdateRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;
import com.jje.membercenter.remote.vo.Contact;

@XmlRootElement(name = "request")
@Component
public class MemberBaseInfoUpdateReq extends CrmRequest {
	public MemberBaseInfoUpdateReq() {
		super(CrmTransCode.UPDATE_MEMBER_INFO);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public MemberBaseInfoUpdateRes send() {
		return this.sendToType(MemberBaseInfoUpdateRes.class);
	}

	public static class RequestBody extends Body {
		private String membid;
		
		private String bindflg;
		
		private String passwd;
		
		private String pwdquestion;
		
		private String pwdanswer;
		
		private String partnercard;
		
		private String partnerflag;
		
		private String partnername;
		
		private Listofcontact  listofcontact;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getBindflg() {
			return bindflg;
		}

		public void setBindflg(String bindflg) {
			this.bindflg = bindflg;
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

		public Listofcontact getListofcontact() {
			return listofcontact;
		}

		public void setListofcontact(Listofcontact listofcontact) {
			this.listofcontact = listofcontact;
		}

		public String getPwdanswer() {
			return pwdanswer;
		}

		public void setPwdanswer(String pwdanswer) {
			this.pwdanswer = pwdanswer;
		}
		
		

	}
	
	public static class Listofcontact{
		
		private List<Contact>  contact=new ArrayList<Contact>();

		public List<Contact> getContact() {
			return contact;
		}

		public void setContact(List<Contact> contact) {
			this.contact = contact;
		}
		
		
		
	} 
	
	
}
