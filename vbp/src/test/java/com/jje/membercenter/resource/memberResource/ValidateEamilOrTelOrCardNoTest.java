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
import com.jje.dto.StatusDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class ValidateEamilOrTelOrCardNoTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_ValidateEamilOrTelOrCardNo() throws Exception {
		String emailOrTelorCardNo ="whatfuck@jinjiang.com";
		InvokeResult<StatusDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/validateActivationMember", emailOrTelorCardNo, StatusDto.class);
		StatusDto statusDto = result.getOutput();
		Assert.assertEquals(true, statusDto.isExistFlag());
		Assert.assertEquals(result.getStatus(), Status.OK);
	}
}
