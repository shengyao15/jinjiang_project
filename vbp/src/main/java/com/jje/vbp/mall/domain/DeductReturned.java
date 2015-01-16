package com.jje.vbp.mall.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jje.membercenter.remote.crm.support.CrmResponse;


@XmlRootElement(name = "response")
public class DeductReturned extends CrmResponse {
	
    @XmlElement
    private Body body;

    public DeductReturned() {
        body = new Body();
    }

    public Body getBody() {
        return body;
    }

    public static class Body {

        private String memberId;
        private String transId;
        private String recode;

        @XmlElement(name = "remsg")
        public String getReMsg() {
            return reMsg;
        }

        public void setReMsg(String reMsg) {
            this.reMsg = reMsg;
        }

        private String reMsg;

        @XmlElement(name = "recode")
        public String getRecode() {
            return recode;
        }

        public void setRecode(String recode) {
            this.recode = recode;
        }

        @XmlElement(name = "transid")
        public String getTransId() {
            return transId;
        }

        public void setTransId(String transId) {
            this.transId = transId;
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
