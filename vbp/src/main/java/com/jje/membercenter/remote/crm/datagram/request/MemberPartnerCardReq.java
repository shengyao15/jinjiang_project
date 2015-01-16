package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.LoypointRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class MemberPartnerCardReq extends CrmRequest {
	public MemberPartnerCardReq() {
		super(CrmTransCode.QUERY_MEMBER_PARTNER_CARD);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public MemberPartnerCardRes send() {
		return this.sendToType(MemberPartnerCardRes.class);
	}

	public static class RequestBody extends Body {
		private String membid;
		
		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

	}
}
