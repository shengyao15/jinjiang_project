package com.jje.membercenter.crm.impl;

import org.springframework.stereotype.Component;

import com.jje.membercenter.crm.CRMOperationProxy;
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

@Component
public class CRMOperationProxyImpl extends CRMBaseProxy implements CRMOperationProxy
{
	
	public MemberScoreResponse findScoreLog(MemberScoreRequest request) throws Exception
	{
		return this.getResponse(request, MemberScoreResponse.class);
	}
	
	public TemplateResponse SyncTemplate(TemplateRequest request) throws Exception
	{
		return this.getResponse(request, TemplateResponse.class);
	}
	
	public ChannelScoreResponse getChannelScore(ChannelScoreRequest request) throws Exception {
		return this.getResponse(request, ChannelScoreResponse.class);
	}
	
	public TaobaoQueryScoreResponse getTaobaoScore(TaobaoQueryScoreRequest request) throws Exception {
		return this.getResponse(request, TaobaoQueryScoreResponse.class);
	}

	@Override
	public TaobaoRedeemResponse redeemTaobaoScore(TaobaoRedeemRequest request) throws Exception {
		return this.getResponse(request, TaobaoRedeemResponse.class);
	}

}
