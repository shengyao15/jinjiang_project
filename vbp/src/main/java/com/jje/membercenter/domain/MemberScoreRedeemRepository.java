package com.jje.membercenter.domain;

import java.util.List;

public interface MemberScoreRedeemRepository
{
	void addScoreRedeem(MemberScoreRedeem memberScoreRedeem);

	List<MemberScoreRedeem> queryRedeem(MemberScoreRedeem memberScoreRedeem);

}
