package com.jje.membercenter.remote.crm.datagram.request;

import com.jje.membercenter.remote.crm.datagram.response.MemberQueryScoreRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@Component
public class MemberQueryScoreReq extends CrmRequest {

        public MemberQueryScoreReq(String memberId, String orderId) {
            super(CrmTransCode.QUERY_SCORE_DEDUCT);
            this.body = new RequestBody();
            body.setOrderId(orderId);
            body.setMembId(memberId);
        }

        public MemberQueryScoreReq() {
            super(CrmTransCode.QUERY_SCORE_DEDUCT);
            this.body = new RequestBody();
        }


    private RequestBody body;


        public RequestBody getBody() {
            return body;
        }

        public void setBody(RequestBody body) {
            this.body = body;
        }

        public MemberQueryScoreRes send() {
            return this.sendToType(MemberQueryScoreRes.class);
        }

        public static class RequestBody extends Body {

            private String membId;

            private String orderId;

            @XmlElement(name = "orderid")
            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            @XmlElement(name = "membid")
            public String getMembId() {
                return membId;
            }

            public void setMembId(String membId) {
                this.membId = membId;
            }
        }

}
