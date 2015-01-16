package com.jje.membercenter.persistence;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.score.domain.MemberScoreTrade;

@Transactional
@Repository
public class MemberScoreTradeLogRepositoryImpl implements MemberScoreTradeLogRepository{
	
	@Autowired
	private MemberScoreTradeLogMapper memberScoreTradeMapper;

	public MemberScoreTrade addMemberScoreTradeLog(MemberScoreTrade memberScoreTrade) {
		MemberScoreTrade log = new MemberScoreTrade();
		BeanUtils.copyProperties(memberScoreTrade, log);
		memberScoreTradeMapper.addMemberScoreTradeLog(log);
		return log;
	}

	public void updateMemberScoreTradeLog(MemberScoreTrade memberScoreTrade) {
		memberScoreTradeMapper.updateMemberScoreTradeLog(memberScoreTrade);
	}
	
}
