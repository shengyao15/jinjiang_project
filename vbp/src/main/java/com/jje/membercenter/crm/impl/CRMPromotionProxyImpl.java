package com.jje.membercenter.crm.impl;

import org.springframework.stereotype.Component;

import com.jje.membercenter.crm.CRMPromotionProxy;
import com.jje.membercenter.remote.crm.datagram.request.CrmPromotionRequest;
import com.jje.membercenter.remote.crm.datagram.response.CrmPromotionResponse;

@Component
public class CRMPromotionProxyImpl extends CRMBaseProxy implements CRMPromotionProxy{

	@Override
	public CrmPromotionResponse challengeScoreSignup(CrmPromotionRequest request) throws Exception {
		return this.getResponse(request, CrmPromotionResponse.class);
	}

}
