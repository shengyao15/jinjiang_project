package com.jje.membercenter.score.domain;

import java.util.Date;

import com.jje.dto.membercenter.score.ExchangeType;

public class MemberScoreTrade {
	
	private Long id;
	
	private String orderNo;
	
	private Date createTime;
	
	private String mcMemberCode;
	
	private String productId;
	
	//交易积分
	private String tradeScore;
	
	//状态
	private TradeStatus status;
	
	private String productName;
	
	private ExchangeType exchangeType;
	
	//交易流水号
	private String transId;
	
	//剩余积分
	private String remainPoint;
	
	private Date  lastUpdateTime;
	
	private String remark;
	
	//第3方订单号 
    private String  trdOrderNo;	
	

	public MemberScoreTrade() {
		
	}
	

	public MemberScoreTrade(String orderNo,String  trdOrderNo, Date createTime,
			String mcMemberCode, String productId, String tradeScore,
			TradeStatus status, String productName, ExchangeType exchangeType) {
		super();
		this.orderNo = orderNo;
		this.trdOrderNo=trdOrderNo;
		this.createTime = createTime;
		this.mcMemberCode = mcMemberCode;
		this.productId = productId;
		this.tradeScore = tradeScore;
		this.status = status;
		this.productName = productName;
		this.exchangeType = exchangeType;
        this.lastUpdateTime = new Date();
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getMcMemberCode() {
		return mcMemberCode;
	}

	public void setMcMemberCode(String mcMemberCode) {
		this.mcMemberCode = mcMemberCode;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTradeScore() {
		return tradeScore;
	}

	public void setTradeScore(String tradeScore) {
		this.tradeScore = tradeScore;
	}

	public TradeStatus getStatus() {
		return this.status;
	}

	public void setStatus(TradeStatus status) {
		this.status = status;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public ExchangeType getExchangeType() {
		return exchangeType;
	}


	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}


	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getRemainPoint() {
		return remainPoint;
	}

	public void setRemainPoint(String  remainPoint) {
		this.remainPoint = remainPoint;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getTrdOrderNo() {
		return trdOrderNo;
	}


	public void setTrdOrderNo(String trdOrderNo) {
		this.trdOrderNo = trdOrderNo;
	}


	@Override
	public String toString() {
		return "MemberScoreTrade [id=" + id + ", orderNo=" + orderNo + ", createTime=" + createTime + ", mcMemberCode="
				+ mcMemberCode + ", productId=" + productId + ", tradeScore=" + tradeScore + ", status=" + status + ", productName="
				+ productName + ", exchangeType=" + exchangeType + ", transId=" + transId + ", remainPoint=" + remainPoint
				+ ", lastUpdateTime=" + lastUpdateTime + ", remark=" + remark + ", trdOrderNo=" + trdOrderNo + "]";
	}
	
}
