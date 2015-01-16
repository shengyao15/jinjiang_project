package com.jje.membercenter.persistence;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.accountapply.AccountMergeApplyDto;
import com.jje.dto.membercenter.accountapply.HotelScoreRegisterApplyDto;
import com.jje.membercenter.crm.impl.CRMAccountApplyProxyImpl;
import com.jje.membercenter.domain.AccountApplyRepository;

@Repository
@Transactional
public class AccountApplyRepositoryImpl implements AccountApplyRepository
{
	@Autowired
	private CRMAccountApplyProxyImpl crmccountApplyProxyImpl;
	
//	public boolean applyRegisterScore(HotelScoreRegisterApplyDto hotelScoreRegisterApplyDto)
//	{
//		return crmccountApplyProxyImpl.applyRegisterScore(hotelScoreRegisterApplyDto);
//	}

//	public ResultMemberDto<HotelScoreRegisterApplyDto> listRegisterScoreHistory(
//			QueryMemberDto<HotelScoreRegisterApplyDto> queryDto)
//	{
//		return crmccountApplyProxyImpl.listRegisterScoreHistory(queryDto);
//	}

	public boolean addAccountMergeApply(AccountMergeApplyDto accountMergeApplyDto)
	{
		//return crmccountApplyProxyImpl.addAccountMergeApply(accountMergeApplyDto);
		return  false ;
	}

	public ResultMemberDto<AccountMergeApplyDto> listMergeApplyHistory(QueryMemberDto<AccountMergeApplyDto> queryDto)
	{
		//return crmccountApplyProxyImpl.listMergeApplyHistory(queryDto);
		return  null ;
	}

	public boolean applyRegisterScore(
			HotelScoreRegisterApplyDto scoreRegisterApplyDto) {
		// TODO Auto-generated method stub
		return false;
	}

	public ResultMemberDto<HotelScoreRegisterApplyDto> listRegisterScoreHistory(QueryMemberDto<HotelScoreRegisterApplyDto> queryDto)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
