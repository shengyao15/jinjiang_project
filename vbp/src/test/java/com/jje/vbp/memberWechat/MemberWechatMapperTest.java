package com.jje.vbp.memberWechat;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.memberWechat.domain.MemberWechatDomain;
import com.jje.vbp.memberWechat.service.MemberWechatService;

public class MemberWechatMapperTest extends DataPrepareFramework {

	@Autowired
	private MemberWechatService memberWechatService;

	@Test
	public void should_success_insert_memberWechat() {

		MemberWechatDomain domain;
		for (Integer i = 100001; i < 100011; i++) {
			domain = new MemberWechatDomain();
			domain.setId(i.longValue());
			domain.setMcMemberCode(i.toString());
			domain.setServiceId(i.toString());
			domain.setWechatId(i.toString());
			memberWechatService.insert(domain);
		}

		List<MemberWechatDomain> domains = memberWechatService
				.getMemberWechatByMcCode("100002");
		domains = memberWechatService.getMemberWechatByMcCode("100020");

		Assert.assertTrue(true);
	}

}
