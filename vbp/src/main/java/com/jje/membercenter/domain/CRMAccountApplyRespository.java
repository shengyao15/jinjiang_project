package com.jje.membercenter.domain;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.accountapply.AccountMergeApplyDto;
import com.jje.dto.membercenter.accountapply.BuyCardApplyDto;
import com.jje.dto.membercenter.accountapply.ScoreRegisterApplyDto;

public interface CRMAccountApplyRespository
{
	CRMResponseDto applyRegisterScore(ScoreRegisterApplyDto scoreRegisterApplyDto) throws Exception;

	ResultMemberDto<ScoreRegisterApplyDto> listRegisterScoreHistory(QueryMemberDto<ScoreRegisterApplyDto> queryDto) throws Exception;

	CRMResponseDto addAccountMergeApply(AccountMergeApplyDto accountMergeApplyDto) throws Exception;

	ResultMemberDto<AccountMergeApplyDto> listMergeApplyHistory(QueryMemberDto<AccountMergeApplyDto> queryDto) throws Exception;

	CRMResponseDto applyCard(BuyCardApplyDto buyCardApplyDto) throws Exception;

	ResultMemberDto<BuyCardApplyDto> listApplyCard(QueryMemberDto<BuyCardApplyDto> queryDto) throws Exception;
}
