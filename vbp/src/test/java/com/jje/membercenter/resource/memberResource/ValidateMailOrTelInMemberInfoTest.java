package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

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
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class ValidateMailOrTelInMemberInfoTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_validate_mail_or_tel_in_memberInfo() throws Exception {
		InvokeResult<StatusDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/validateMailOrTelInMemberInfo", "278250658@qq.com", StatusDto.class);
		StatusDto statusDto = result.getOutput();
		 Assert.assertNotNull(statusDto);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}


}
