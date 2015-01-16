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
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberXmlResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class GetMemberCardOrderDtoTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;
	
	@Test   
	public void should_be_success_when_get_member_cardOrder_dto() throws Exception {
		String orderNo ="V30e20ce8";
		InvokeResult<MemberCardOrderDto> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class,
				"/member/getmembercardorder", orderNo, MemberCardOrderDto.class);
		MemberCardOrderDto memberCardOrderDto = result.getOutput();
		Assert.assertNotNull(memberCardOrderDto);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	

}
