package com.jje.membercenter.resource.memberAdminResource;

import java.util.Date;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberOrderNoteDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberAdminResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class SaveMemberOrderNoteTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_saveMemberOrderNote() throws Exception {
		InvokeResult<String> result = resourceInvokeHandler.doPost("memberAdminResource", MemberAdminResource.class,
				"/member/saveOrderNote", getMemberOrderNoteDto(), String.class);
		String output = result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertEquals("ok", output);
	}

	private Object getMemberOrderNoteDto() {
		MemberOrderNoteDto dto = new MemberOrderNoteDto();
		dto.setContent("测试啊");
		dto.setCreateTime(new Date());
		dto.setOperation("admin");
		dto.setOrderNo("12154");
		dto.setReason("reason");
		dto.setUserId("1000");
		dto.setUserName("admin");
		return dto;
	}

}
