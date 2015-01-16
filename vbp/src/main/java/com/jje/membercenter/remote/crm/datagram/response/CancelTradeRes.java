package com.jje.membercenter.remote.crm.datagram.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name = "response")
public class CancelTradeRes extends CrmResponse {
	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {

		private String membid;//会员id
		private String txnid;//交易号
		private String loypoint;//会员可用积分

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getTxnid() {
			return txnid;
		}

		public void setTxnid(String txnid) {
			this.txnid = txnid;
		}

		public String getLoypoint() {
			return loypoint;
		}

		public void setLoypoint(String loypoint) {
			this.loypoint = loypoint;
		}

	}

}
