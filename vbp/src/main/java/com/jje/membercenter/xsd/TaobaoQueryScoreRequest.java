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
public class TaobaoQueryScoreRequest {

	@XmlElement(required = true)
	protected TaobaoQueryScoreRequest.Head head;
	@XmlElement(required = true)
	protected TaobaoQueryScoreRequest.Body body;

	public TaobaoQueryScoreRequest.Head getHead() {
		return head;
	}

	public void setHead(TaobaoQueryScoreRequest.Head head) {
		this.head = head;
	}

	public TaobaoQueryScoreRequest.Body getBody() {
		return body;
	}

	public void setBody(TaobaoQueryScoreRequest.Body body) {
		this.body = body;
	}

  @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "transcode",
        "reqtime",
        "systype"
    })
	public static class Head {

		@XmlElement(required = true)
		protected String transcode;
		@XmlElement(required = true)
		protected String reqtime;
		@XmlElement(required = true)
		protected String systype;
		
		
		public String getTranscode() {
			return transcode;
		}
		public void setTranscode(String transcode) {
			this.transcode = transcode;
		}
		public String getReqtime() {
			return reqtime;
		}
		public void setReqtime(String reqtime) {
			this.reqtime = reqtime;
		}
		public String getSystype() {
			return systype;
		}
		public void setSystype(String systype) {
			this.systype = systype;
		}

	}

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {
      "membid"
  })
	public static class Body {

		@XmlElement(required = true)
		protected String membid;

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

	}
}
