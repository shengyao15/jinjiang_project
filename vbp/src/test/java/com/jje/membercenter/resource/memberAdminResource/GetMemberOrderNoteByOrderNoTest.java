package com.jje.membercenter.resource.memberAdminResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberOrderNoteDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberAdminResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class GetMemberOrderNoteByOrderNoTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test   
	public void should_be_success_when_orderNote() throws Exception {
		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doPost("memberAdminResource", MemberAdminResource.class,
				"/member/orderNote", "14123", ResultMemberDto.class);
		ResultMemberDto<MemberOrderNoteDto> resultDtos = result.getOutput();
		Assert.assertEquals(1, resultDtos.getTotal());
		Assert.assertEquals(result.getStatus(), Status.OK);
	}
	

}
