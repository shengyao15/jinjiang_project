package com.jje.membercenter.crm.impl;

import com.jje.membercenter.crm.CRMAccountApplyProxy;
import com.jje.membercenter.xsd.*;
import org.springframework.stereotype.Component;

@Component
public class CRMAccountApplyProxyImpl extends CRMBaseProxy implements CRMAccountApplyProxy
{

	public RegisterScoreResponse applyRegisterScore(RegisterScoreRequest request) throws Exception
	{
		return this.getResponse(request, RegisterScoreResponse.class);
	}

	public RegisterScoreHistoryResponse listRegisterScoreHistory(RegisterScoreHistoryRequest request) throws Exception
	{
		return this.getResponse(request, RegisterScoreHistoryResponse.class);
	}

	public MemberScoreResponse queryScore(MemberScoreRequest request) throws Exception
	{
		return this.getResponse(request, MemberScoreResponse.class);
	}

	public ApplyCardResponse applyCard(ApplyCardRequest request) throws Exception
	{
		return this.getResponse(request, ApplyCardResponse.class);
	}

	public ListApplyCardResponse listApplyCard(ListApplyCardRequest request) throws Exception
	{
		return this.getResponse(request, ListApplyCardResponse.class);
	}

	public AccountMergeResponse addAccountMergeApply(AccountMergeRequest request) throws Exception
	{
		return this.getResponse(request, AccountMergeResponse.class);
	}

	public ListAccountMergeResponse listMergeApplyHistory(ListAccountMergeRequest request) throws Exception
	{
		return this.getResponse(request, ListAccountMergeResponse.class);
	}
}
