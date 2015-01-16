package com.jje.vbp.memberRecommend.persistence;

import java.util.List;
import java.util.Map;

import com.jje.vbp.memberRecommend.domain.MemberRecommendCoupon;
import com.jje.vbp.memberRecommend.domain.MemberRecommendDomain;

public interface MemberRecommendMapper {

	int insert(MemberRecommendDomain memberRecommand);

	List<MemberRecommendDomain> queryByRecommenderId(String recommenderId);
	
	List<MemberRecommendDomain> queryTopNByRecommenderId(Map<String,Object> map);

	int countByRecommenderId(String recommenderId);
	
	List<MemberRecommendDomain> queryRecommendRegisterCoupon(Map<String,Object> params);
	
	int queryRecommendRegisterCouponCount(Map<String,Object> params);

	void insertMemberRecommendCoupon(MemberRecommendCoupon recommCoupon);
}
