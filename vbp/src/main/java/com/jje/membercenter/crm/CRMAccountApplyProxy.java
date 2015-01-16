package com.jje.membercenter.crm;

import com.jje.membercenter.xsd.*;

public interface CRMAccountApplyProxy
{

	RegisterScoreResponse applyRegisterScore(RegisterScoreRequest request) throws Exception;

	RegisterScoreHistoryResponse listRegisterScoreHistory(RegisterScoreHistoryRequest request) throws Exception;

	MemberScoreResponse queryScore(MemberScoreRequest request) throws Exception;

	ApplyCardResponse applyCard(ApplyCardRequest request) throws Exception;

	ListApplyCardResponse listApplyCard(ListApplyCardRequest request) throws Exception;

	AccountMergeResponse addAccountMergeApply(AccountMergeRequest request) throws Exception;

	ListAccountMergeResponse listMergeApplyHistory(ListAccountMergeRequest request) throws Exception;

}
