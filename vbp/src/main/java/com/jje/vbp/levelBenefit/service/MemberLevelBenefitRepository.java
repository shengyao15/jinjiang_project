package com.jje.vbp.levelBenefit.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.DateUtils;
import com.jje.vbp.levelBenefit.domain.MemberLevelBenefitDomain;
import com.jje.vbp.levelBenefit.persistence.MemberLevelBenefitMapper;

@Component
public class MemberLevelBenefitRepository {

	@Autowired
	private MemberLevelBenefitMapper memberLevelBenefitMapper;

/*	List<MemberLevelBenefitDomain> query(String memberId, String start,
			String end) {
		return memberLevelBenefitMapper.query(memberId, start, end);
	}*/
	
	// 获取上一个月的数据
	public List<MemberLevelBenefitDomain> query(String memberId) {
	    Date lastMonth  = DateUtils.add(new Date(), GregorianCalendar.MONTH, -1);
		String time = DateUtils.formatDate(lastMonth, "yyyyMM");
		List<MemberLevelBenefitDomain> list = memberLevelBenefitMapper.query(memberId, time);
		return list;//memberLevelBenefitMapper.query(memberId, time);
	}
}
