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
public class QueryMemberDtoByEmailTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_queryMemberDtoByEmail() throws Exception {
		InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/queryMemberDtoByEmail", "chenyongne@126.com", MemberDto.class);
		MemberDto memberDto = result.getOutput();
		Assert.assertNotNull(memberDto);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}
}
