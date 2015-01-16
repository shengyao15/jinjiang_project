package com.jje.membercenter.domain;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.accountapply.AccountMergeApplyDto;
import com.jje.dto.membercenter.accountapply.HotelScoreRegisterApplyDto;

public interface AccountApplyRepository
{

	boolean applyRegisterScore(HotelScoreRegisterApplyDto scoreRegisterApplyDto);

	ResultMemberDto<HotelScoreRegisterApplyDto> listRegisterScoreHistory(QueryMemberDto<HotelScoreRegisterApplyDto> queryDto);

	boolean addAccountMergeApply(AccountMergeApplyDto accountMergeApplyDto);

	ResultMemberDto<AccountMergeApplyDto> listMergeApplyHistory(QueryMemberDto<AccountMergeApplyDto> queryDto);
}
