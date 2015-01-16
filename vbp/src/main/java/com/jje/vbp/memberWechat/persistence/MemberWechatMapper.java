package com.jje.vbp.memberWechat.persistence;

import java.util.List;
import java.util.Map;

import com.jje.vbp.memberRecommend.domain.MemberRecommendCoupon;
import com.jje.vbp.memberRecommend.domain.MemberRecommendDomain;
import com.jje.vbp.memberWechat.domain.MemberWechatDomain;

public interface MemberWechatMapper {

	int insert(MemberWechatDomain memberWechatDomain);
	
	List<MemberWechatDomain> getMemberWechatByMcCode(String mcCode);
}
