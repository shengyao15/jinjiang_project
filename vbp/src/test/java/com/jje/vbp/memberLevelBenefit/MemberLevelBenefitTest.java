package com.jje.vbp.memberLevelBenefit;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.levelBenefit.domain.MemberLevelBenefitDomain;
import com.jje.vbp.levelBenefit.persistence.MemberLevelBenefitMapper;

public class MemberLevelBenefitTest extends DataPrepareFramework{
	
	@Autowired
	private MemberLevelBenefitMapper mapper;
	
	@Test
	public void test() {
		List<MemberLevelBenefitDomain> list = mapper.query("1-7DWH1G","201407");
		Assert.assertEquals(1, list.size());
	}
}
