package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.ScoresTradeRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class RegisterScoresTradeReq extends CrmRequest {
	public RegisterScoresTradeReq() {
		super(CrmTransCode.REGISTER_SCORES_TRADE);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public ScoresTradeRes send() {
		return this.sendToType(ScoresTradeRes.class);
	}

	public static class RequestBody extends Body {
		private String membid;//会员id
		private String transdate;//交易日期
        private String product;
		public String getMembid() {
			return membid;
		}
		public void setMembid(String membid) {
			this.membid = membid;
		}
		public String getTransdate() {
			return transdate;
		}
		public void setTransdate(String transdate) {
			this.transdate = transdate;
		}
		public String getProduct() {
			return product;
		}
		public void setProduct(String product) {
			this.product = product;
		}
		
    }
}
