package com.jje.membercenter.remote.crm.datagram.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name = "response")
public class LoypointRes extends CrmResponse {
	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}

	public static class ResponseBody extends Body {
		private String membid;

		private String points;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getPoints() {
			return points;
		}

		public void setPoints(String points) {
			this.points = points;
		}

		

	}

}
