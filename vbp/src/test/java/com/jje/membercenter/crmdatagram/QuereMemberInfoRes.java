package com.jje.membercenter.crmdatagram;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.account.xsd.AccountValidationResponse.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

 

@XmlRootElement(name = "response")
public class QuereMemberInfoRes extends CrmResponse {

	public static class ResponseBody extends Body {
		private String membid;

		private Listofcontact listofcontact;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public Listofcontact getListofcontact() {
			return listofcontact;
		}

		public void setListofcontact(Listofcontact listofcontact) {
			this.listofcontact = listofcontact;
		}
	}

	private ResponseBody body;

	public ResponseBody getBody() {
		return body;
	}

	public void setBody(ResponseBody body) {
		this.body = body;
	}


}
