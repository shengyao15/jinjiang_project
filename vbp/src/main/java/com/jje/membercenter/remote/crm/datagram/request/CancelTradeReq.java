package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.CancelTradeRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class CancelTradeReq extends CrmRequest {
	public CancelTradeReq() {
		super(CrmTransCode.CANCEL_TRADE);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public CancelTradeRes send() {
		return this.sendToType(CancelTradeRes.class);
	}

	public static class RequestBody extends Body {
		private String membid;//会员id
		private String txnid;//交易号
		private String punishpoints;//扣减积分
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
		public String getPunishpoints() {
			return punishpoints;
		}
		public void setPunishpoints(String punishpoints) {
			this.punishpoints = punishpoints;
		}
		
		
	}
}
