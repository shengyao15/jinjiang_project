package com.jje.membercenter.xsd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "head", "body" })
@XmlRootElement(name = "request")
public class ThirdPartyLoginRequest {

	@XmlElement(required = true)
	protected ThirdPartyLoginRequest.Head head;
	@XmlElement(required = true)
	protected ThirdPartyLoginRequest.Body body;

	public ThirdPartyLoginRequest.Head getHead() {
		return head;
	}

	public void setHead(ThirdPartyLoginRequest.Head value) {
		this.head = value;
	}

	public ThirdPartyLoginRequest.Body getBody() {
		return body;
	}

	public void setBody(ThirdPartyLoginRequest.Body value) {
		this.body = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "crsid", "phone" })
	public static class Body {

		@XmlElement(required = true)
		protected String crsid;
		
		@XmlElement(required = true)
		protected String phone;
		
		public String getCrsid() {
			return crsid;
		}

		public void setCrsid(String crsid) {
			this.crsid = crsid;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
		
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "transcode", "reqtime", "systype" })
	public static class Head {

		@XmlElement(required = true)
		protected BigInteger transcode;
		@XmlElement(required = true)
		protected BigInteger reqtime;
		@XmlElement(required = true)
		protected String systype;

		public BigInteger getTranscode() {
			return transcode;
		}

		public void setTranscode(BigInteger value) {
			this.transcode = value;
		}

		public BigInteger getReqtime() {
			return reqtime;
		}

		public void setReqtime(BigInteger reqtime) {
			this.reqtime = reqtime;
		}

		public String getSystype() {
			return systype;
		}

		public void setSystype(String value) {
			this.systype = value;
		}

	}

}
