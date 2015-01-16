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
import com.jje.dto.membercenter.MemberInfoAnswerDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryMemberByCodeTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_query_member_by_code() throws Exception {
		InvokeResult<MemberInfoAnswerDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/queryMemberByCode/3",null, MemberInfoAnswerDto.class);
		MemberInfoAnswerDto memberInfoAnswerDto= result.getOutput();
		Assert.assertEquals("1157863308", memberInfoAnswerDto.getMember().getCardNo());	
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

}
