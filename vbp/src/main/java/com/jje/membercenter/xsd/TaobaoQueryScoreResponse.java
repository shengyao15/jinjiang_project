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
public class TaobaoQueryScoreResponse {

    @XmlElement(required = true)
    protected TaobaoQueryScoreResponse.Head head;
    @XmlElement(required = true)
    protected TaobaoQueryScoreResponse.Body body;
    
	public TaobaoQueryScoreResponse.Head getHead() {
		return head;
	}

	public void setHead(TaobaoQueryScoreResponse.Head head) {
		this.head = head;
	}

	public TaobaoQueryScoreResponse.Body getBody() {
		return body;
	}

	public void setBody(TaobaoQueryScoreResponse.Body body) {
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
        "points",
        "balance"
    })
	public static class Body {

		@XmlElement(required = true)
		protected String membid;
		@XmlElement(required = true)
		protected String points;
		@XmlElement(required = true)
		protected String balance;
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
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
		}
		
		

	}
}
