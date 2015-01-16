package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.TierUpdateRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class TierUpdateReq extends CrmRequest {
	public TierUpdateReq() {
		super(CrmTransCode.TIER_UPDATE);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public TierUpdateRes send() {
		return this.sendToType(TierUpdateRes.class);
	}

	public static class RequestBody extends Body {

		private String membid;
		private String tier;
		private String thirdpartyType;
		private String openId;
		private String thirdpartyLevel;
		public String getMembid() {
			return membid;
		}
		public void setMembid(String membid) {
			this.membid = membid;
		}
		public String getTier() {
			return tier;
		}
		public void setTier(String tier) {
			this.tier = tier;
		}
		public String getThirdpartyType() {
			return thirdpartyType;
		}
		public void setThirdpartyType(String thirdpartyType) {
			this.thirdpartyType = thirdpartyType;
		}
		public String getOpenId() {
			return openId;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		public String getThirdpartyLevel() {
			return thirdpartyLevel;
		}
		public void setThirdpartyLevel(String thirdpartyLevel) {
			this.thirdpartyLevel = thirdpartyLevel;
		}

	}
}
