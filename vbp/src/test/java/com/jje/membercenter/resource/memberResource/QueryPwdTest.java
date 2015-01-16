package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

public class QueryPwdTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test
	public void should_be_success_when_member_phone() throws Exception {
		InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/queryPwd", "12585909081", MemberDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertEquals("E10ADC3949BA59ABBE56E057F20F883E", result.getOutput().getPassword());
	}

	@Test
	public void should_be_success_when_webMember_phone() throws Exception {
		InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/queryPwd", "13511109081", MemberDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertEquals("E10ADC3949BA59ABBE56E057F20F883E", result.getOutput().getPassword());
	}

}
