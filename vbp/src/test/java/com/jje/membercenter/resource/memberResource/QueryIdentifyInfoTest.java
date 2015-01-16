package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryIdentifyInfoTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_queryIdentifyInfo() throws Exception {
		InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/validateIdentifyInfo", mockMemberDto(), MemberDto.class);
		MemberDto memberDto = result.getOutput();
		Assert.assertNotNull(memberDto);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	private MemberDto mockMemberDto() {
		MemberDto dto = new MemberDto();
		dto.setIdentityNo("55555");
		dto.setIdentityType("Others");
		return dto;
	}
}
