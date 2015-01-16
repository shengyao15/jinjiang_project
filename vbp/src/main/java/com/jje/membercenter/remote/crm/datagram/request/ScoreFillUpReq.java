package com.jje.membercenter.remote.crm.datagram.request;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.score.ScoreFillUpType;
import com.jje.membercenter.remote.crm.datagram.response.ScoreFillUpRes;
import com.jje.membercenter.remote.crm.support.Body;
import com.jje.membercenter.remote.crm.support.CrmRequest;
import com.jje.membercenter.remote.crm.support.CrmTransCode;

@XmlRootElement(name = "request")
@Component
public class ScoreFillUpReq extends CrmRequest{

	public ScoreFillUpReq() {
		super(CrmTransCode.SCORE_FILL_UP);
		body=new RequestBody();
		
	}
	
	private RequestBody body;

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}

	public ScoreFillUpRes send() throws Exception {
		return this.sendToType(ScoreFillUpRes.class);
	}
	
	public static class RequestBody extends Body {
		
		private String membid;
		
		private String srtype;
		
		private String srdetail; // 拼字符串的详细信息  弃用
		
		private ScoreFillUpType type; // 积分补登类型
		
		private String city; // 城市
		
		private String retailername; // 酒店名称
		
		private String room; // 入住房间
		
		private String startdate; // 入住时间
		
		private String enddate; // 退房时间
		
		private BigDecimal amount; // 消费金额(元)
		
		private String ordernumber; // 订单号
		
		private String invoicenumber; // 发票号

		private String buydate; // 购买日期
		
		private String lineName; // 线路名称
		
		private String groupnum; // 团号
		
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

		public String getBuydate() {
			return buydate;
		}

		public void setBuydate(String buydate) {
			this.buydate = buydate;
		}

		public String getLineName() {
			return lineName;
		}

		public void setLineName(String lineName) {
			this.lineName = lineName;
		}

		public String getGroupnum() {
			return groupnum;
		}

		public void setGroupnum(String groupnum) {
			this.groupnum = groupnum;
		}
		
		public ScoreFillUpType getType() {
			return type;
		}

		public void setType(ScoreFillUpType type) {
			this.type = type;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getRetailername() {
			return retailername;
		}

		public void setRetailername(String retailername) {
			this.retailername = retailername;
		}

		public String getRoom() {
			return room;
		}

		public void setRoom(String room) {
			this.room = room;
		}


		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getOrdernumber() {
			return ordernumber;
		}

		public void setOrdernumber(String ordernumber) {
			this.ordernumber = ordernumber;
		}

		public String getInvoicenumber() {
			return invoicenumber;
		}

		public void setInvoicenumber(String invoicenumber) {
			this.invoicenumber = invoicenumber;
		}

		public String getMembid() {
			return membid;
		}

		public void setMembid(String membid) {
			this.membid = membid;
		}

		public String getSrtype() {
			return srtype;
		}

		public void setSrtype(String srtype) {
			this.srtype = srtype;
		}

		public String getSrdetail() {
			return srdetail;
		}

		public void setSrdetail(String srdetail) {
			this.srdetail = srdetail;
		}
		
	}

}
