package com.jje.vbp.mall.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: yapon
 * Date: 3/13/12
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "request")
public class DeductReceived implements MessageStruct<DeductReceived.Head, DeductReceived.Body> {

    @XmlElement
    private Head head;
    @XmlElement
    private Body body;

    public DeductReceived() {
        head = new Head();
        body = new Body();
    }
    
    public DeductReceived(Head head, Body body) {
			this();
			this.head = head;
			this.body = body;
		}

		public Head getHead() {
        return head;
    }

    public Body getBody() {
        return body;
    }

    public static class Head {

        private String transCode;
        private String reqTime;
        private String sysType;
        
        public Head() {
					super();
				}
        
				public Head(String transCode, String reqTime, String sysType) {
					this();
					this.transCode = transCode;
					this.reqTime = reqTime;
					this.sysType = sysType;
				}

				@XmlElement(name = "systype")
        public String getSysType() {
            return sysType;
        }

        public void setSysType(String sysType) {
            this.sysType = sysType;
        }

        @XmlElement(name = "reqtime")
        public String getReqTime() {
            return reqTime;
        }

        public void setReqTime(String reqTime) {
            this.reqTime = reqTime;
        }

        @XmlElement(name = "transcode")
        public String getTransCode() {
            return transCode;
        }

        public void setTransCode(String transCode) {
            this.transCode = transCode;
        }
    }

    public static class Body {

        private String memberId;
        private String productId;
        private String points;
        private String transDate;
        private String orderNum;
        private String callId;
        private String partnerCard;
        private String sid;
        private String uname;
        private String umobile;
        private String key;
        
        public Body() {
					super();
				}
        
				public Body(String memberId, String productId, String points, String transDate, String orderNum, String callId,
						String partnerCard, String sid, String uname, String umobile, String key) {
					this();
					this.memberId = memberId;
					this.productId = productId;
					this.points = points;
					this.transDate = transDate;
					this.orderNum = orderNum;
					this.callId = callId;
					this.partnerCard = partnerCard;
					this.sid = sid;
					this.uname = uname;
					this.umobile = umobile;
					this.key = key;
				}

				@XmlElement(name = "key")
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @XmlElement(name = "umobile")
        public String getUmobile() {
            return umobile;
        }

        public void setUmobile(String umobile) {
            this.umobile = umobile;
        }

        @XmlElement(name = "uname")
        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        @XmlElement(name = "sid")
        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        @XmlElement(name = "partnercard")
        public String getPartnerCard() {
            return partnerCard;
        }

        public void setPartnerCard(String partnerCard) {
            this.partnerCard = partnerCard;
        }

        @XmlElement(name = "callid")
        public String getCallId() {
            return callId;
        }

        public void setCallId(String callId) {
            this.callId = callId;
        }

        @XmlElement(name = "ordernum")
        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        @XmlElement(name = "transdate")
        public String getTransDate() {
            return transDate;
        }

        public void setTransDate(String transDate) {
            this.transDate = transDate;
        }

        @XmlElement(name = "points")
        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        @XmlElement(name = "productid")
        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        @XmlElement(name = "membid")
        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
    }
}
