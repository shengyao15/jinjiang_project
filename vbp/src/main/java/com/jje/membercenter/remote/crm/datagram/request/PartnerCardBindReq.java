package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.PartnerCardBindRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class PartnerCardBindReq extends CrmRequest {
	public PartnerCardBindReq() {
		super(CrmTransCode.PARTNER_CARD_BIND);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public PartnerCardBindRes send() {
		return this.sendToType(PartnerCardBindRes.class);
	}

	public static class RequestBody extends Body {
		private String membid;
		
		private String memcdno;
		
		private String accpointflg;
		
		private String membcdtyp;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getMemcdno() {
			return memcdno;
		}

		public void setMemcdno(String memcdno) {
			this.memcdno = memcdno;
		}

		public String getAccpointflg() {
			return accpointflg;
		}

		public void setAccpointflg(String accpointflg) {
			this.accpointflg = accpointflg;
		}

		public String getMembcdtyp() {
			return membcdtyp;
		}

		public void setMembcdtyp(String membcdtyp) {
			this.membcdtyp = membcdtyp;
		}
	}
}