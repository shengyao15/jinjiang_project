package com.jje.membercenter.resource.memberXmlResource;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberXmlDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberXmlResource;

public class GetmemberXmlByOrderNoTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_get_memberXml_by_orderNo() throws Exception {
		String orderNo ="V1314a79a";
		InvokeResult<MemberXmlDto> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class,
				"/member/getmemberXmlByOrderNo", orderNo, MemberXmlDto.class);
		MemberXmlDto memberXmlDto = result.getOutput();
		Assert.assertNotNull(memberXmlDto);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	

}
