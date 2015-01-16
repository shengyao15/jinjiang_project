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
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class GetMemberIdByMcMemberCodeTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_get_memberId_by_mcMemberCode() throws Exception {
		InvokeResult<String> result = resourceInvokeHandler.doGet("memberResource", MemberResource.class,
				"/member/getMemberIdByMcMemberCode/3", String.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

}
