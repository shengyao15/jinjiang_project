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
import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberXmlResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class GetMemeberCardOrderTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test   
	public void should_be_success_when_get_member_card_order() throws Exception {
		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class,
				"/member/order", getQueryDto(), ResultMemberDto.class);
		ResultMemberDto<MemberCardOrderDto> MemberCardOrderDtoResult = result.getOutput();
		Assert.assertNotNull(MemberCardOrderDtoResult);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	private QueryMemberDto<MemberCardOrderDto> getQueryDto() {
		QueryMemberDto<MemberCardOrderDto> memberCardOrderDto = new QueryMemberDto<MemberCardOrderDto>();
		memberCardOrderDto.setCondition(new MemberCardOrderDto());
		memberCardOrderDto.setPagination(new Pagination());
		return memberCardOrderDto;
	}

}
