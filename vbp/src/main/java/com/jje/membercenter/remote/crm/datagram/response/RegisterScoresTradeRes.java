package com.jje.membercenter.remote.crm.datagram.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name = "response")
public class RegisterScoresTradeRes extends CrmResponse {
	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {

		private String membid;//会员id
		
		private String transid;//交易号
		


		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getTransid() {
			return transid;
		}

		public void setTransid(String transid) {
			this.transid = transid;
		}
	}

}
