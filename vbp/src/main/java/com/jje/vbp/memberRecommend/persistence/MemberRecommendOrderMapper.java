package com.jje.vbp.memberRecommend.persistence;

import java.util.List;

import com.jje.vbp.memberRecommend.domain.MemberRecommendOrderDomain;

public interface MemberRecommendOrderMapper {
	List<MemberRecommendOrderDomain> queryByMemberId(String memberId);
}
