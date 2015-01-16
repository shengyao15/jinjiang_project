package com.jje.membercenter.crm;

import com.jje.membercenter.remote.crm.datagram.request.CrmPromotionRequest;
import com.jje.membercenter.remote.crm.datagram.response.CrmPromotionResponse;


public interface CRMPromotionProxy {
	
	CrmPromotionResponse challengeScoreSignup(CrmPromotionRequest request) throws Exception;
}
