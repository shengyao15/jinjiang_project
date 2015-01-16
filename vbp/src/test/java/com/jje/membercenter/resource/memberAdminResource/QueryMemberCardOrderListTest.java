package com.jje.membercenter.resource.memberAdminResource;

import java.util.Date;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberAdminResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class QueryMemberCardOrderListTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test   
	public void should_be_success_when_adminOrder() throws Exception {
		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doPost("memberAdminResource", MemberAdminResource.class,
				"/member/adminOrder", getMemberCardOrderDto(), ResultMemberDto.class);
		ResultMemberDto<MemberCardOrderDto> resultDtos = result.getOutput();
		Assert.assertEquals(1, resultDtos.getTotal());
		Assert.assertEquals(result.getStatus(), Status.OK);
	}
	
	private QueryMemberDto<MemberCardOrderDto> getMemberCardOrderDto() {
		QueryMemberDto<MemberCardOrderDto> queryDto = new QueryMemberDto<MemberCardOrderDto>();
		MemberCardOrderDto condition = new MemberCardOrderDto();
		condition.setOrderNo("V190a889b");
		condition.setCardNo("1012");
		condition.setStartDate(DateUtils.parseDate("2012-02-05"));
		condition.setEndDate(new Date());
		queryDto.setCondition(condition);
		Pagination pagination = new Pagination();
		queryDto.setPagination(pagination);
		return queryDto;
	}
}
