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
import com.jje.dto.membercenter.MemberXmlDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class SaveXmlTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_saveXml() throws Exception {
		InvokeResult<StatusDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/saveXml", mockMemberXmlDto(), StatusDto.class);
		StatusDto statusDto = result.getOutput();
		Assert.assertNotNull(statusDto);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	private MemberXmlDto mockMemberXmlDto() {
		MemberXmlDto dto = new MemberXmlDto();
		dto.setId("");
		dto.setEmail("");
		dto.setMobile("");
		dto.setOrderNo("");
		dto.setCertificateNo("");
		dto.setCertificateType("");
		dto.setXml("");
		dto.setCallBackFlag("");
		return dto;
	}

}
