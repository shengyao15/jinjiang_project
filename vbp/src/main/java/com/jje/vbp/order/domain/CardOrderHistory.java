package com.jje.vbp.order.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.membercenter.domain.MemberCardOrder;

public class CardOrderHistory {

	private Long logId;

	private Long orderId;

	private String orderNo;

	private Date orderTime;

	private Date createTime;

	private String cardNo;

	private String orderType;

	private String currentLevel;

	private String nextLevel;

	private BigDecimal amount;

	private Date payTime;

	private String paymentNo;

	private String payType;

	private String paymentVender;

	private String bankCode;

	private Integer status;

	private Integer payStatus;

	private Date startDate;

	private Date endDate;

	private BigDecimal refundAmount;

	private String mcMemberCode;

	private String saleChannel;

	private String operationType;

	private Date operationTime;

	private String remark;

	public CardOrderHistory() {
		super();
	}

	public CardOrderHistory(MemberCardOrder order, String operationType, String remark) {
		this();
		BeanUtils.copyProperties(order, this);
		this.orderId = order.getId();
		this.operationType = operationType;
		this.remark = remark;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}

	public String getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPaymentVender() {
		return paymentVender;
	}

	public void setPaymentVender(String paymentVender) {
		this.paymentVender = paymentVender;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getMcMemberCode() {
		return mcMemberCode;
	}

	public void setMcMemberCode(String mcMemberCode) {
		this.mcMemberCode = mcMemberCode;
	}

	public String getSaleChannel() {
		return saleChannel;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Override
	public String toString() {
		return "CardOrderHistory [logId=" + logId + ", orderId=" + orderId + ", orderNo=" + orderNo + ", orderTime=" + orderTime
				+ ", createTime=" + createTime + ", cardNo=" + cardNo + ", orderType=" + orderType + ", currentLevel=" + currentLevel
				+ ", nextLevel=" + nextLevel + ", amount=" + amount + ", payTime=" + payTime + ", paymentNo=" + paymentNo + ", payType="
				+ payType + ", paymentVender=" + paymentVender + ", bankCode=" + bankCode + ", status=" + status + ", payStatus="
				+ payStatus + ", startDate=" + startDate + ", endDate=" + endDate + ", refundAmount=" + refundAmount + ", mcMemberCode="
				+ mcMemberCode + ", saleChannel=" + saleChannel + ", operationType=" + operationType + ", operationTime=" + operationTime
				+ ", remark=" + remark + "]";
	}
	
}
