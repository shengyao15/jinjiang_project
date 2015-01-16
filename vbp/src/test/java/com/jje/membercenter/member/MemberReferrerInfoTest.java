package com.jje.membercenter.member;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.ReferrerInfoDto;
import com.jje.dto.membercenter.ReferrerRegistResult;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class MemberReferrerInfoTest {
	
	@Autowired
	MemberResource memberResource;
	
	@Test
	public void should_save_referrer_info_test () {
		ReferrerInfoDto info = mockRefInfoDto();
		System.out.println(JaxbUtils.convertToXmlString(info));
		Response res = memberResource.referrerHandler(info);
		System.out.println(res);
		Assert.assertTrue(res.getStatus()==Status.OK.getStatusCode());
		ReferrerRegistResult result = (ReferrerRegistResult) res.getEntity();
		Assert.assertTrue(result.getStatus().equals(ReferrerRegistResult.ReferrerRegistStatus.SUCCESS));
	}

	private ReferrerInfoDto mockRefInfoDto() {
		ReferrerInfoDto info = new ReferrerInfoDto();
		info.setMemberInfoId(2L);
		//1157863308
		info.setReferrerCardNo("1157866001");
		return info;
	}

}
