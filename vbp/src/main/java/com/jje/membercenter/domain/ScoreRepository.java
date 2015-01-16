package com.jje.membercenter.domain;

import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.QueryScoreDto;


public interface ScoreRepository
{
	MemberScoreDto queryScore(QueryScoreDto queryScoreDto);
}
