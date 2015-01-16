package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.ScoresTradeRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class ScoresTradeReq extends CrmRequest {
	public ScoresTradeReq() {
		super(CrmTransCode.SCORES_TRADE);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public ScoresTradeRes send() {
		return this.sendToType(ScoresTradeRes.class);
	}

	public static class RequestBody extends Body {
		private String membid;//会员id
		private String transdate;//交易日期
		private String points;//消费积分
		private String redeemproduct;//兑换产品名称
        private String productname;
        private String transtype;
        private String ordernumber;
        private String source;

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

        public String getProductname() {
			return productname;
		}

		public void setProductname(String product) {
			this.productname = product;
		}
	

        public String getTranstype() {
            return transtype;
        }

        public void setTranstype(String transtype) {
            this.transtype = transtype;
        }

		public void setOrdernumber(String orderNo) {
			this.ordernumber=orderNo;			
		}

	
		public String getOrdernumber() {
			return ordernumber;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		
		
		
    }
}
