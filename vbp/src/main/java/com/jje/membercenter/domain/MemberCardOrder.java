package com.jje.membercenter.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.jje.dto.membercenter.MemberCardOrderDto;


/**
 * @author zhenhui_xiong
 * @version 1.0
 * @date Nov 3, 2011 4:23:24 PM
 */
public class MemberCardOrder {
	
	public static Integer PAY_STATUS_SUCC = 2;
	
	public static Integer PAY_STATUS_FAIL=3;
	
	private Long id;

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
	
	private String mcMemberCode; //会员中心统一会员编号
	
	private String saleChannel;
	
	private MemberXml memberXml;

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
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

	public MemberCardOrder () {
		
	}

	
	public MemberCardOrder(String orderNo, String cardNo,String mcMemberCode) {
		super();
		this.orderNo = orderNo;
		this.cardNo = cardNo;
		this.mcMemberCode = mcMemberCode;
	}

	public MemberCardOrder(String orderNo, Date payTime, String paymentNo,
			String payType, String paymentVender, String bankCode,
			Integer payStatus) {
		super();
		this.orderNo = orderNo;
		this.payTime = payTime;
		this.paymentNo = paymentNo;
		this.payType = payType;
		this.paymentVender = paymentVender;
		this.bankCode = bankCode;
		this.payStatus = payStatus;
	}

	public MemberCardOrder(MemberCardOrderDto memberCardOrderDto) {
		// TODO Auto-generated constructor stub
		this.id = memberCardOrderDto.getId();
		this.orderNo = memberCardOrderDto.getOrderNo();
		this.orderTime = memberCardOrderDto.getOrderTime();
		this.createTime = memberCardOrderDto.getCreateTime();
		this.cardNo = memberCardOrderDto.getCardNo();
		this.orderType = memberCardOrderDto.getOrderType();
		this.currentLevel = memberCardOrderDto.getCurrentLevel();
		this.nextLevel = memberCardOrderDto.getNextLevel();
		this.amount = memberCardOrderDto.getAmount();
		this.payTime = memberCardOrderDto.getPayTime();
		this.paymentNo = memberCardOrderDto.getPaymentNo();
		this.payType = memberCardOrderDto.getPayType();
		this.paymentVender = memberCardOrderDto.getPaymentVender();
		this.bankCode = memberCardOrderDto.getBankCode();
		this.status = memberCardOrderDto.getStatus();
		this.payStatus = memberCardOrderDto.getPayStatus();
		this.startDate = memberCardOrderDto.getStartDate();
		this.endDate = memberCardOrderDto.getEndDate();
		this.mcMemberCode = memberCardOrderDto.getMcMemberCode();
		this.saleChannel = memberCardOrderDto.getSaleChannel();
	}
	
	public MemberCardOrderDto toDto(){
		MemberCardOrderDto dto = new MemberCardOrderDto();
		dto.setId(id);
		dto.setOrderNo(orderNo);
		dto.setOrderTime(orderTime);
		dto.setCreateTime(createTime);
		dto.setCardNo(cardNo);
		dto.setOrderType(orderType);
		dto.setCurrentLevel(currentLevel);
		dto.setNextLevel(nextLevel);
		dto.setAmount(amount);
		dto.setPayTime(payTime);
		dto.setPaymentNo(paymentNo);
		dto.setPayType(payType);
		dto.setPaymentVender(paymentVender);
		dto.setBankCode(bankCode);
		dto.setStatus(status);
		dto.setPayStatus(payStatus);
		dto.setStartDate(startDate);
		dto.setEndDate(endDate);
		dto.setMcMemberCode(mcMemberCode);
		dto.setSaleChannel(saleChannel);
		dto.setPageUrl(this.getPageUrl());
		dto.setBgUrl(this.getBgUrl());
		return dto;
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
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public MemberXml getMemberXml() {
		return memberXml;
	}

	public void setMemberXml(MemberXml memberXml) {
		this.memberXml = memberXml;
	}
	
	public String getPageUrl() {
		String pageUrl = null;
		
		if(memberXml == null){
			return pageUrl;
		}

		if ("UPGRADE".equals(this.getOrderType())) {
			pageUrl = "/membercenter/update/updateSuccess";
		} else if ("RECHARGE".equals(this.getOrderType())) {
			pageUrl = "/membercenter/resume/success";
		}

		return getpageUrl(pageUrl, memberXml.getId());
	}

	public String getBgUrl() {
		String bgUrl = null;
		
		if(memberXml == null){
			return bgUrl;
		}

		if ("UPGRADE".equals(this.getOrderType())) {
			bgUrl = "/member/payForUpdate";
		} else if ("RECHARGE".equals(this.getOrderType())) {
			bgUrl = "/member/payForResumeCard";
		}
		return getBgUrl(bgUrl, memberXml.getId());
	}

	private String getpageUrl(String pageUrl, String uuid) {
		StringBuffer result = new StringBuffer();
		result.append(pageUrl);
		result.append("?needPay=Y");
		result.append("&uuid=");
		result.append(uuid);
		return result.toString();
	}

	private String getBgUrl(String pageUrl, String uuid) {
		return pageUrl + "?uuid=" + uuid;
	}
	
}
