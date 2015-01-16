package com.jje.membercenter.persistence;

import org.apache.ibatis.annotations.Param;

import com.jje.membercenter.score.domain.MemberScoreTrade;

import java.util.List;

public interface MemberScoreTradeOrderMapper {
	
	public void addMemberScoreTradeOrder(MemberScoreTrade memberScoreTrade);
	
	public void updateMemberScoreTradeOrderById(MemberScoreTrade memberScoreTrade);
	
	public int updateMemberScoreTradeOrderForRetry(@Param("record") MemberScoreTrade record, @Param("condition") MemberScoreTrade condition);
	
	public MemberScoreTrade getMemberScoreTradeOrderForRetry(@Param("condition") MemberScoreTrade condition);

    public List<MemberScoreTrade> queryMemberScoreTradeOrderForTimeout(@Param("condition") MemberScoreTrade condition);

}
