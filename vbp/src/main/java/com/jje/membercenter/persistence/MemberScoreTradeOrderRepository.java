package com.jje.membercenter.persistence;

import com.jje.membercenter.score.domain.MemberScoreTrade;

import java.util.List;

public interface MemberScoreTradeOrderRepository {

	public void addMemberScoreTradeOrder(MemberScoreTrade memberScoreTrade);

	public void updateMemberScoreTradeOrder(MemberScoreTrade memberScoreTrade);
	
	public int updateMemberScoreTradeOrderForRetry(MemberScoreTrade record, MemberScoreTrade condition);
	
	public MemberScoreTrade getMemberScoreTradeOrderForRetry(MemberScoreTrade condition);
	
	public MemberScoreTrade getMemberScoreTradeOrderForRetry(String trdOrderNo);

    List<MemberScoreTrade> queryMemberScoreTradeOrderForTimeout(MemberScoreTrade condition);
}
