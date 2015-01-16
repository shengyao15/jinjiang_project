package com.jje.membercenter.remote.crm.datagram.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.account.xsd.AccountValidationResponse.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;

@XmlRootElement(name="response")
public class GoldpalMemberRegiestRes extends CrmResponse {
	
	public static class DefaultResponseBody extends Body {
	private String membid;
	private String cardnum;
	public String getMembid() {
		return membid;
	}
	public void setMembid(String membid) {
		this.membid = membid;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
}

	private DefaultResponseBody body;

	public DefaultResponseBody getBody() {
		return body;
	}

	public void setBody(DefaultResponseBody body) {
		this.body = body;
	}


}
