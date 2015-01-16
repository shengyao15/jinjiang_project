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
public class ChannelScoreResponse {

    @XmlElement(required = true)
    protected ChannelScoreResponse.Head head;
    @XmlElement(required = true)
    protected ChannelScoreResponse.Body body;
    
	public ChannelScoreResponse.Head getHead() {
		return head;
	}

	public void setHead(ChannelScoreResponse.Head head) {
		this.head = head;
	}

	public ChannelScoreResponse.Body getBody() {
		return body;
	}

	public void setBody(ChannelScoreResponse.Body body) {
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
        "retcode",
        "remsg",
        "ordernum"
    })
	public static class Body {

		@XmlElement(required = true)
		protected String retcode;
		@XmlElement(required = true)
		protected String remsg;
		@XmlElement(required = true)
		protected String ordernum;
		public String getRetcode() {
			return retcode;
		}
		public void setRetcode(String retcode) {
			this.retcode = retcode;
		}
		public String getRemsg() {
			return remsg;
		}
		public void setRemsg(String remsg) {
			this.remsg = remsg;
		}
		public String getOrdernum() {
			return ordernum;
		}
		public void setOrdernum(String ordernum) {
			this.ordernum = ordernum;
		}
		
		

	}
}
