package com.jje.membercenter.crm.impl;

import org.springframework.stereotype.Component;

import com.jje.membercenter.crm.CRMUpdateRightCardProxy;
import com.jje.membercenter.xsd.QueryRightCardInfoRequest;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse;

@Component
public class CRMUpdateRightCardProxyImpl extends CRMBaseProxy implements CRMUpdateRightCardProxy
{

	public QueryRightCardInfoResponse queryVIPCardInfo(
			QueryRightCardInfoRequest request) throws Exception {
		return this.getResponse(request, QueryRightCardInfoResponse.class);
	}

	

}
