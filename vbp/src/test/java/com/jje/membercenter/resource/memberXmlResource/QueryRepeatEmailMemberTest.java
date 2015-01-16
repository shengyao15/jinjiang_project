package com.jje.membercenter.resource.memberXmlResource;

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
import com.jje.membercenter.MemberXmlResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryRepeatEmailMemberTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_query_repeat_email_member() throws Exception {
		InvokeResult<String> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class,
				"/member/queryRepeatEmailMember", getMemberDto(), String.class);
		String repeat = result.getOutput();
		Assert.assertEquals("Y", repeat);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	private MemberDto getMemberDto() {
		MemberDto  memberDto = new MemberDto();
		memberDto.setMemberCode("chenyongne@126.com");
		memberDto.setUserName("1157863308");
		return memberDto;
	}

}
