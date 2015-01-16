package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.jje.membercenter.account.xsd.AccountActivationRequest.Body;
import com.jje.membercenter.remote.crm.datagram.response.GoldpalMemberQueryRes;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name="request")
public class GoldpalMemberQueryReq extends CrmRequest {
	
	public GoldpalMemberQueryReq(){
		super(CrmTransCode.GOLDPAL_MEMBER_QUERY);
		this.body = new RequestBody();
	}
	
	private RequestBody body;

	public RequestBody getBody() {
		
		return body;
	}

	public void setBody(RequestBody body) {
		
		this.body = body;
	}
	
	public GoldpalMemberQueryRes send() throws Exception {
		
		return this.sendToType(GoldpalMemberQueryRes.class);
	}
	
	@XmlType(propOrder={"name","cardid","cardtype","email","mobile","memcd","membcdtype"})
	public static class RequestBody extends Body{
		private String name ;
		private String cardid ;
		private String cardtype ;
		private String email ;
		private String mobile ;
		private String memcd ;
	  	private String membcdtype ;
	  	
	  	
		public RequestBody() {
		}
		public RequestBody(String name, String membcdtype, String memcd,
				String email, String mobile, String cardid, String cardtype) {
			super();
			this.name = name;
			this.membcdtype = membcdtype;
			this.memcd = memcd;
			this.email = email;
			this.mobile = mobile;
			this.cardid = cardid;
			this.cardtype = cardtype;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMembcdtype() {
			return membcdtype;
		}
		public void setMembcdtype(String membcdtype) {
			this.membcdtype = membcdtype;
		}
		public String getMemcd() {
			return memcd;
		}
		public void setMemcd(String memcd) {
			this.memcd = memcd;
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
	  	
	}
	
}
	

