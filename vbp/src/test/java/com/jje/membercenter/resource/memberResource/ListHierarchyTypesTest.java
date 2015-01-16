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
import com.jje.dto.ResultMemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class ListHierarchyTypesTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@SuppressWarnings("rawtypes")
	@Test   
	public void should_be_success_when_listHierarchyTypes() throws Exception {
		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doGet("memberResource", MemberResource.class,
				"/member/listHierarchyTypes", ResultMemberDto.class);
		ResultMemberDto resultDto = result.getOutput();
		Assert.assertNotNull(resultDto);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

}
