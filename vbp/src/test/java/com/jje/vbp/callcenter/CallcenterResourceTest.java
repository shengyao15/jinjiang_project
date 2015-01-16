package com.jje.vbp.callcenter;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.vbp.callcenter.MemberCallcenterConditionDto;
import com.jje.dto.vbp.callcenter.MemberCallcenterResult;
import com.jje.membercenter.DataPrepareFramework;

//@Ignore
public class CallcenterResourceTest  extends DataPrepareFramework{

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test
	public void should_success_when_queryMember() {
		InvokeResult<MemberCallcenterResult> result = resourceInvokeHandler.doPost("callcenterResource", CallcenterResource.class,
				"/callcenter/queryMember", buildCallcenterCondition(), MemberCallcenterResult.class);
		Assert.assertEquals(Status.OK, result.getStatus());
		Assert.assertNotNull(result.getOutput());
		MemberCallcenterResult results = result.getOutput();
		Assert.assertNotNull(results);
		Assert.assertEquals(2, results.getMembers().size());
		Assert.assertEquals(0, results.getWebMembers().size());
	}

	private MemberCallcenterConditionDto buildCallcenterCondition() {
		MemberCallcenterConditionDto dto = new MemberCallcenterConditionDto();
		dto.setPhone("15900916061");
		return dto;
	}
	
}
