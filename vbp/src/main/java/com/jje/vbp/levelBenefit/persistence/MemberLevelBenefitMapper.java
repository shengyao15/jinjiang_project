package com.jje.vbp.levelBenefit.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jje.vbp.levelBenefit.domain.MemberLevelBenefitDomain;

public interface MemberLevelBenefitMapper {

/*	List<MemberLevelBenefitDomain> query(String memberId, String start,
			String end);*/
	
	List<MemberLevelBenefitDomain> query(@Param("memberId") String memberId, @Param("time") String time);
}
