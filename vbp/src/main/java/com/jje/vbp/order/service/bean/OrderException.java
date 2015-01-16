package com.jje.vbp.order.service.bean;

import com.jje.dto.vbp.order.CardOrderResponseCode;

public class OrderException extends RuntimeException {

	private static final long serialVersionUID = -554864941765020835L;

	private CardOrderResponseCode code;

	public OrderException(CardOrderResponseCode code, String message) {
		super(message);
		this.code = code;
	}

	public CardOrderResponseCode getCode() {
		return code;
	}

	public void setCode(CardOrderResponseCode code) {
		this.code = code;
	}

}
