package com.jje.membercenter.remote.crm.datagram.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name = "response")
public class CrmPromotionResponse extends CrmResponse{
	
	private ResponseBody body;
	
	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}
	
	public static class ResponseBody extends Body {
		private String attstatus;

		public String getAttstatus() {
			return attstatus;
		}

		public void setAttstatus(String attstatus) {
			this.attstatus = attstatus;
		}
	}
}
