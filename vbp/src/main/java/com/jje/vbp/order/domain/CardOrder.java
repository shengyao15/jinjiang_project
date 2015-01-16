package com.jje.vbp.order.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.CardType;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.vbp.order.CardOrderDto;
import com.jje.dto.vbp.order.CardOrderStatus;
import com.jje.dto.vbp.order.CardOrderType;
import com.jje.vbp.order.service.bean.EmptyValidation;

public class CardOrder {
	
	private Long id;

	private String orderNo;

	@NotNull(groups=EmptyValidation.class)
	private Date orderDate = new Date();

	@NotNull(groups=EmptyValidation.class)
	private CardOrderType orderType;

	@NotNull(groups=EmptyValidation.class)
	private NewCardType cardType;
	
	@NotBlank(groups=EmptyValidation.class)
	private String orderChannel;//RegistChannel

	@NotBlank(groups=EmptyValidation.class)
	private String cardNo;
	
	@NotBlank(groups=EmptyValidation.class)
	private String mcMemberCode;//下单客户

	private CardOrderStatus orderStatus;
	
	public CardOrder() {
		super();
	}
	
	public CardOrder(CardOrderDto orderDto) {
		super();
		BeanUtils.copyProperties(orderDto, this);
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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date bookingDate) {
		this.orderDate = bookingDate;
	}

	public CardOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(CardOrderType orderType) {
		this.orderType = orderType;
	}


	public NewCardType getCardType() {
		return cardType;
	}

	public void setCardType(NewCardType cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public CardOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(CardOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getMcMemberCode() {
		return mcMemberCode;
	}

	public void setMcMemberCode(String mcMemberCode) {
		this.mcMemberCode = mcMemberCode;
	}
	
	public CardOrderDto toDto() {
		CardOrderDto orderDto = new CardOrderDto();
		BeanUtils.copyProperties(this, orderDto);
		return orderDto;
	}
	
	public String getAction() {
		return this.getOrderType().getPriceQueryAlias() + this.getCardType().getCode();
	}

}
