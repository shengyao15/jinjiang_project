package com.jje.membercenter.persistence;

import java.util.List;

import com.jje.membercenter.domain.MemberScoreRedeem;

public interface MemberScoreRedeemMapper
{
	void addScoreRedeem(MemberScoreRedeem memberScoreRedeem);

	List<MemberScoreRedeem> queryRedeem(MemberScoreRedeem memberScoreRedeem);
}
