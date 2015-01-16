package com.jje.membercenter.remote.crm.datagram.response;

import com.jje.dto.vbp.mall.DeductResDto;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.crm.support.Head;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "response")
public class MemberQueryScoreRes  extends CrmResponse{

    //private ResponseHead head;

    private ResponseBody body;

    public MemberQueryScoreRes() {
        super();
    }

    public ResponseBody getBody() {
        return body;
    }

    public void setBody(ResponseBody body) {
        this.body = body;
    }

    public static class ResponseBody extends Body {

        private String orderId;

        private String memberId;

        private String reCode;

        private String reMsg;

        private String productId;

        private String points;

        private String status;

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

        @XmlElement(name = "status")
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @XmlElement(name = "recode")
        public String getReCode() {
            return reCode;
        }

        public void setReCode(String reCode) {
            this.reCode = reCode;
        }

        @XmlElement(name = "remsg")
        public String getReMsg() {
            return reMsg;
        }

        public void setReMsg(String reMsg) {
            this.reMsg = reMsg;
        }

        @XmlElement(name = "orderid")
        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }



        @XmlElement(name = "membid")
        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
    }

    public DeductResDto toDto(){
        DeductResDto dto = new DeductResDto();
        //head
        dto.setTransCode(getHead().getTranscode());
        dto.setRespTime(getHead().getReqtime());
        dto.setSysType(getHead().getSystype());
        //body
        dto.setOrderId(getBody().getOrderId());
        dto.setMemberId(getBody().getMemberId());
        dto.setReCode(getBody().getReCode());
        dto.setReMsg(getBody().getReMsg());
        dto.setProductId(getBody().getProductId());
        dto.setPoints(getBody().getPoints());
        dto.setStatus(getBody().getStatus());
        return dto;
    }


}
