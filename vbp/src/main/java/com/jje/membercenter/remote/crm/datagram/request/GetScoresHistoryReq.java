package com.jje.membercenter.remote.crm.datagram.request;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.membercenter.remote.crm.datagram.response.GetScoresHistoryRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class GetScoresHistoryReq extends CrmRequest {
	public GetScoresHistoryReq() {
		super(CrmTransCode.GET_SCORES_HISTORY);
		this.body = new RequestBody();
	}

	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public GetScoresHistoryRes send() {
		return this.sendToType(GetScoresHistoryRes.class);
	}

	public static class RequestBody extends Body {
		private String membid;//会员号
		private String startdate;//开始日期
		private String enddate;//结束日期
		private String pointtype;//积分类型
		private String partnername;//来源版块
		private String productname;//消费产品

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getStartdate() {
			return startdate;
		}

		public void setStartdate(String startdate) {
			this.startdate = startdate;
		}

		public String getEnddate() {
			return enddate;
		}

		public void setEnddate(String enddate) {
			this.enddate = enddate;
		}

		public String getPointtype() {
			return pointtype;
		}

		public void setPointtype(String pointtype) {
			this.pointtype = pointtype;
		}

		public String getPartnername() {
			return partnername;
		}

		public void setPartnername(String partnername) {
			this.partnername = partnername;
		}

		public String getProductname() {
			return productname;
		}

		public void setProductname(String productname) {
			this.productname = productname;
		}
		
		

	}
}
