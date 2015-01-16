package com.jje.membercenter.resource.memberAdminResource;

import java.math.BigDecimal;
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
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberAdminResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class UpdateCardOrderTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_updateCardOrder() throws Exception {
		InvokeResult<String> result = resourceInvokeHandler.doPost("memberAdminResource", MemberAdminResource.class,
				"/member/updateCardOrder", getMemberCardOrderDto(), String.class);
		String output = result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertEquals("ok", output);
	}

	private Object getMemberCardOrderDto() {
		MemberCardOrderDto dto = new MemberCardOrderDto();
		dto.setPayStatus(1);
		dto.setAmount(new BigDecimal(20));
		dto.setOrderNo("V190a8cdb");
		dto.setOrderTime(new Date());
		dto.setCreateTime(new Date());
		dto.setCardNo("1013");
		dto.setOrderType("MEMCARD");
		dto.setRefundAmount(new BigDecimal(10));
		dto.setMcMemberCode("10001");
		return dto;
	}
	

}
