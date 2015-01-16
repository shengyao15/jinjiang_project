package com.jje.membercenter.resource.memberResource;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.BaseDataDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class ListRemindQuestionTypesTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test   
	public void should_be_success_when_list_invoice_types() throws Exception {
		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doGet("memberResource", MemberResource.class,
				"/member/listInvoiceTypes", ResultMemberDto.class);
		ResultMemberDto<BaseDataDto> baseDataDto = result.getOutput();
		 List<BaseDataDto> dtoResultList = baseDataDto.getResults();
		 Assert.assertNotNull(dtoResultList);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}


}
