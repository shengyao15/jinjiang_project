package com.jje.membercenter.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.QueryScoreDto;
import com.jje.membercenter.crm.CRMOperationProxy;
import com.jje.membercenter.domain.ScoreRepository;

@Repository
@Transactional
public class ScoreRepositoryImpl implements ScoreRepository
{
	@Autowired
	CRMOperationProxy crmOperationProxy;

	public MemberScoreDto queryScore(QueryScoreDto queryScoreDto)
	{
		// TODO Auto-generated method stub
		return null;
	}

//	public MemberScoreDto queryScore(QueryScoreDto queryScoreDto)
//	{
//		return crmOperationProxy.findScoreLog(queryScoreDto);
//	}

}
