package com.jje.vbp.memberRecommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.memberRecommend.domain.MemberRecommendDomain;
import com.jje.vbp.memberRecommend.persistence.MemberRecommendMapper;
import com.jje.vbp.memberRecommend.persistence.MemberRecommendOrderMapper;

public class MemberRecommandMapperTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Autowired
	private MemberRecommendMapper memberRecommandMapper;

	@Test
	public void should_success_insert_memberRecommand() {
		MemberRecommendDomain memberRecommand;
		for (Integer i = 10000; i < 10100; i++) {
			memberRecommand = new MemberRecommendDomain();
			if (i % 10 < 5) {
				memberRecommand.setRecommenderId(((Integer) ((i / 10) * 5))
						.toString() + "0");
				memberRecommand.setRegisterId(i.toString());
				memberRecommand.setCampaign("TestCampaign");
				memberRecommandMapper.insert(memberRecommand);
			}
		}

		List<MemberRecommendDomain> memberRecommands = memberRecommandMapper
				.queryByRecommenderId("50050");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("recommenderId", "50050");
		map.put("recommendTimes", 5);
		memberRecommandMapper.queryTopNByRecommenderId(map);

		int count = memberRecommandMapper.countByRecommenderId("50050");

		Assert.assertTrue(true);
	}
	


}
