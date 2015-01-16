package com.jje.membercenter.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.score.domain.MemberScoreTrade;

import java.util.List;

@Repository
public class MemberScoreTradeOrderRepositoryImpl implements MemberScoreTradeOrderRepository {

	@Autowired
	private MemberScoreTradeOrderMapper memberScoreTradeMapper;
    @Transactional
	public void addMemberScoreTradeOrder(MemberScoreTrade memberScoreTrade) {
		memberScoreTradeMapper.addMemberScoreTradeOrder(memberScoreTrade);
	}
    @Transactional
	public void updateMemberScoreTradeOrder(MemberScoreTrade memberScoreTrade) {
		memberScoreTradeMapper.updateMemberScoreTradeOrderById(memberScoreTrade);
	}
    @Transactional
	public int updateMemberScoreTradeOrderForRetry(MemberScoreTrade record, MemberScoreTrade condition) {
		return memberScoreTradeMapper.updateMemberScoreTradeOrderForRetry(record, condition);
	}

	public MemberScoreTrade getMemberScoreTradeOrderForRetry(MemberScoreTrade condition) {
		return memberScoreTradeMapper.getMemberScoreTradeOrderForRetry(condition);
	}
	
	public MemberScoreTrade getMemberScoreTradeOrderForRetry(String trdOrderNo) {
		MemberScoreTrade memberScoreTrade = new MemberScoreTrade();
		memberScoreTrade.setTrdOrderNo(trdOrderNo);
		return memberScoreTradeMapper.getMemberScoreTradeOrderForRetry(memberScoreTrade);
	}
	
	public List<MemberScoreTrade> queryMemberScoreTradeOrderForTimeout(MemberScoreTrade condition) {
		return memberScoreTradeMapper.queryMemberScoreTradeOrderForTimeout(condition);
	}

}
