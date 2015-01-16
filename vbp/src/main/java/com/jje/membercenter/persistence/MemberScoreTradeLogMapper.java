package com.jje.membercenter.persistence;

import com.jje.membercenter.score.domain.MemberScoreTrade;

public interface MemberScoreTradeLogMapper {
	
	public void addMemberScoreTradeLog(MemberScoreTrade memberScoreTrade);
	
	public void updateMemberScoreTradeLog(MemberScoreTrade memberScoreTrade);
	
}
