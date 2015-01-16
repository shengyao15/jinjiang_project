package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.MemberBaseInfoRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class MemberBaseInfoReq extends CrmRequest {
	public MemberBaseInfoReq() {
		super(CrmTransCode.GET_MEMBER_INFO);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public MemberBaseInfoRes send() {
		return this.sendToType(MemberBaseInfoRes.class);
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
