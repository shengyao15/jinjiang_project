package com.jje.membercenter.crm;

import com.jje.membercenter.xsd.QueryRightCardInfoRequest;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse;

public interface CRMUpdateRightCardProxy
{
	QueryRightCardInfoResponse queryVIPCardInfo(QueryRightCardInfoRequest request) throws Exception;
}
