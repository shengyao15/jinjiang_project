package com.jje.order.payment.domain;

import org.springframework.stereotype.Component;

@Component
public class OrderPaymentRequestProviderEmptyImpl extends OrderPaymentRequestProvider {

	public OrderPaymentRequestResult calculate(String orderNo) {
		return null;
	}
}
