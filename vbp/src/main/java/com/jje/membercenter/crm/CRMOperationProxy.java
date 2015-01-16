package com.jje.membercenter.crm;

import com.jje.membercenter.xsd.ChannelScoreRequest;
import com.jje.membercenter.xsd.ChannelScoreResponse;
import com.jje.membercenter.xsd.MemberScoreRequest;
import com.jje.membercenter.xsd.MemberScoreResponse;
import com.jje.membercenter.xsd.TaobaoQueryScoreRequest;
import com.jje.membercenter.xsd.TaobaoQueryScoreResponse;
import com.jje.membercenter.xsd.TaobaoRedeemRequest;
import com.jje.membercenter.xsd.TaobaoRedeemResponse;
import com.jje.membercenter.xsd.TemplateRequest;
import com.jje.membercenter.xsd.TemplateResponse;



public interface CRMOperationProxy
{
	MemberScoreResponse findScoreLog(MemberScoreRequest request) throws Exception;
	
	TemplateResponse SyncTemplate(TemplateRequest request) throws Exception;
	
	ChannelScoreResponse getChannelScore(ChannelScoreRequest request) throws Exception;
	
	TaobaoQueryScoreResponse getTaobaoScore(TaobaoQueryScoreRequest request) throws Exception;

	TaobaoRedeemResponse redeemTaobaoScore(TaobaoRedeemRequest request) throws Exception;

}
