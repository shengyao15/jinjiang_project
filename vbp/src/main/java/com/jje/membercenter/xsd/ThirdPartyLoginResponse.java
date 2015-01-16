package com.jje.membercenter.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "head", "body" })
@XmlRootElement(name = "response")
public class ThirdPartyLoginResponse {

	@XmlElement(required = true)
	protected ThirdPartyLoginResponse.Head head;
	@XmlElement(required = true)
	protected ThirdPartyLoginResponse.Body body;

	public ThirdPartyLoginResponse.Head getHead() {
		return head;
	}

	public void setHead(ThirdPartyLoginResponse.Head value) {
		this.head = value;
	}

	public ThirdPartyLoginResponse.Body getBody() {
		return body;
	}

	public void setBody(ThirdPartyLoginResponse.Body value) {
		this.body = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"crsid", "phone", "membid", "status", "recode", "remsg" })
	public static class Body {
		
		@XmlElement(required = true)
		protected String crsid;
		
		@XmlElement(required = true)
		protected String phone;
		
		@XmlElement(required = true)
		protected String membid;

		@XmlElement(required = true)
		protected String status;
		
		@XmlElement(required = true)
		protected String recode;
		@XmlElement(required = true)
		protected String remsg;

		public String getCrsid() {
			return crsid;
		}

		public String getPhone() {
			return phone;
		}

		public String getMembid() {
			return membid;
		}

		public String getRecode() {
			return recode;
		}

		public String getRemsg() {
			return remsg;
		}
		
		public String getStatus() {
			return status;
		}

		public Body() {
		}

		public Body(String membid, String status, String recode, String remsg) {
			this.membid = membid;
			this.status = status;
			this.recode = recode;
			this.remsg = remsg;
		}

	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "transcode", "resptime", "systype",
			"retcode", "retmsg" })
	public static class Head {

		@XmlElement(required = true)
		protected String transcode;
		
		@XmlElement(required = true)
		protected String resptime;
		
		@XmlElement(required = true)
		protected String systype;
		
		@XmlElement(required = true)
		protected String retcode;
		
		@XmlElement(required = true)
		protected String retmsg;

		public String getTranscode() {
			return transcode;
		}

		public void setTranscode(String value) {
			this.transcode = value;
		}

		public String getResptime() {
			return resptime;
		}

		public void setResptime(String resptime) {
			this.resptime = resptime;
		}

		public String getSystype() {
			return systype;
		}

		public void setSystype(String value) {
			this.systype = value;
		}

		public String getRetcode() {
			return retcode;
		}

		public void setRetcode(String value) {
			this.retcode = value;
		}

		public String getRetmsg() {
			return retmsg;
		}

		public void setRetmsg(String value) {
			this.retmsg = value;
		}

	}

}
