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
public class TaobaoRedeemRequest {

	@XmlElement(required = true)
	protected TaobaoRedeemRequest.Head head;
	@XmlElement(required = true)
	protected TaobaoRedeemRequest.Body body;

	public TaobaoRedeemRequest.Head getHead() {
		return head;
	}

	public void setHead(TaobaoRedeemRequest.Head head) {
		this.head = head;
	}

	public TaobaoRedeemRequest.Body getBody() {
		return body;
	}

	public void setBody(TaobaoRedeemRequest.Body body) {
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
      "membid","transdate","ordernumber","productname","points","redeemproduct","source"
  })
	public static class Body {

		@XmlElement(required = true)
		protected String membid;
		@XmlElement(required = false)
		protected String transdate;
		@XmlElement(required = false)
		protected String ordernumber;
		@XmlElement(required = true)
		protected String productname;
		@XmlElement(required = true)
		protected String points;
		@XmlElement(required = true)
		protected String redeemproduct;
		@XmlElement(required = false)
		protected String source;
		
		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getTransdate() {
			return transdate;
		}

		public void setTransdate(String transdate) {
			this.transdate = transdate;
		}

		public String getOrdernumber() {
			return ordernumber;
		}

		public void setOrdernumber(String ordernumber) {
			this.ordernumber = ordernumber;
		}

		public String getProductname() {
			return productname;
		}

		public void setProductname(String productname) {
			this.productname = productname;
		}

		public String getPoints() {
			return points;
		}

		public void setPoints(String points) {
			this.points = points;
		}

		public String getRedeemproduct() {
			return redeemproduct;
		}

		public void setRedeemproduct(String redeemproduct) {
			this.redeemproduct = redeemproduct;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		
	}
}
