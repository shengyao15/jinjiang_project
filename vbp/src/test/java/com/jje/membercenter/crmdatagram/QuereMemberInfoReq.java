package com.jje.membercenter.crmdatagram;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

 

@XmlRootElement(name = "request")
@Component
public class QuereMemberInfoReq extends CrmRequest {
	public QuereMemberInfoReq() {
		super(CrmTransCode.GET_MEMBER_RELATED_INFO);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public QuereMemberInfoRes send()  {
		return this.sendToType(QuereMemberInfoRes.class);

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
