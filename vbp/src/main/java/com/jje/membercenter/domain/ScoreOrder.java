/**
 * 
 */
package com.jje.membercenter.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.ScoreOrderDto;

public class ScoreOrder {
	private Long id;
	private String orderNo;
	private Date createTime;
	private String memberId;
	private Integer buyScore;
	private String orderStatus;
	private Double payAmout;
	private Date payTime;
	private String paymentNo;
	private String payType;
	private String paymentVender;
	private String bankCode;
	private String payStatus;

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getBuyScore() {
		return buyScore;
	}

	public void setBuyScore(Integer buyScore) {
		this.buyScore = buyScore;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getPayAmout() {
		return payAmout;
	}

	public void setPayAmout(Double payAmout) {
		this.payAmout = payAmout;
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

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public static ScoreOrder from(ScoreOrderDto dto) {
		if (dto == null) {
			return null;
		}
		ScoreOrder scoreOrder = new ScoreOrder();

		BeanUtils.copyProperties(dto, scoreOrder);

		return scoreOrder;
	}

}
