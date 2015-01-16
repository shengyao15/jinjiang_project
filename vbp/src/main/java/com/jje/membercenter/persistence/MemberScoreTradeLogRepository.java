package com.jje.membercenter.persistence;

import com.jje.membercenter.score.domain.MemberScoreTrade;

public interface MemberScoreTradeLogRepository {

	public MemberScoreTrade addMemberScoreTradeLog(MemberScoreTrade memberScoreTrade);

	public void updateMemberScoreTradeLog(MemberScoreTrade memberScoreTrade);

}
