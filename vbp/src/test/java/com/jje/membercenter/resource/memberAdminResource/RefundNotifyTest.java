package com.jje.membercenter.resource.memberAdminResource;

import java.math.BigDecimal;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberAdminResource;
import com.jje.payment.dto.RefundNoticeDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class RefundNotifyTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_refundNotify() throws Exception {
		InvokeResult<String> result = resourceInvokeHandler.doPost("memberAdminResource", MemberAdminResource.class,
				"/member/refund/notify", getRefundNoticeDto(), String.class);
		String output = result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertEquals("success", output);
	}

	private Object getRefundNoticeDto() {
		RefundNoticeDto dto = new RefundNoticeDto();
		dto.setOrderNo("V190a8cde");
		dto.setRefundAmount(new BigDecimal(200.0));
		dto.setResult(true);
		return dto;
	}
	

}
