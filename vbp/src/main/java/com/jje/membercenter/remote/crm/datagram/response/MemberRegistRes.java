package com.jje.membercenter.remote.crm.datagram.response;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name = "response")
public class MemberRegistRes extends CrmResponse {
	

	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {
		private String membid;
		private BigInteger recode;
		private String remsg;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public BigInteger getRecode() {
			return recode;
		}

		public void setRecode(BigInteger recode) {
			this.recode = recode;
		}

		public String getRemsg() {
			return remsg;
		}

		public void setRemsg(String remsg) {
			this.remsg = remsg;
		}

	}

}
