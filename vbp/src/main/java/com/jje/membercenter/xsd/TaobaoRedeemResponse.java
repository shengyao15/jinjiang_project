package com.jje.membercenter.xsd;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "head",
    "body"
})
@XmlRootElement(name = "response")
public class TaobaoRedeemResponse {

    @XmlElement(required = true)
    protected TaobaoRedeemResponse.Head head;
    @XmlElement(required = true)
    protected TaobaoRedeemResponse.Body body;
    
	public TaobaoRedeemResponse.Head getHead() {
		return head;
	}

	public void setHead(TaobaoRedeemResponse.Head head) {
		this.head = head;
	}

	public TaobaoRedeemResponse.Body getBody() {
		return body;
	}

	public void setBody(TaobaoRedeemResponse.Body body) {
		this.body = body;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "transcode",
        "resptime",
        "systype",
        "retcode",
        "retmsg"
    })
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
		public void setTranscode(String transcode) {
			this.transcode = transcode;
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
		public void setSystype(String systype) {
			this.systype = systype;
		}
		public String getRetcode() {
			return retcode;
		}
		public void setRetcode(String retcode) {
			this.retcode = retcode;
		}
		public String getRetmsg() {
			return retmsg;
		}
		public void setRetmsg(String retmsg) {
			this.retmsg = retmsg;
		}

		
	}

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "membid",
        "transid",
        "remainpoint",
        "retcode",
        "retmsg"
    })
	public static class Body {

		@XmlElement(required = false)
		protected String membid;
		@XmlElement(required = false)
		protected String transid;
		@XmlElement(required = false)
		protected String remainpoint;
		@XmlElement(required = true)
		protected String retcode;
		@XmlElement(required = false)
		protected String retmsg;
		public String getMembid() {
			return membid;
		}
		public void setMembid(String membid) {
			this.membid = membid;
		}
		public String getTransid() {
			return transid;
		}
		public void setTransid(String transid) {
			this.transid = transid;
		}
		public String getRemainpoint() {
			return remainpoint;
		}
		public void setRemainpoint(String remainpoint) {
			this.remainpoint = remainpoint;
		}
		public String getRetcode() {
			return retcode;
		}
		public void setRetcode(String retcode) {
			this.retcode = retcode;
		}
		public String getRetmsg() {
			return retmsg;
		}
		public void setRetmsg(String retmsg) {
			this.retmsg = retmsg;
		}
		
		
		
		

	}
}
