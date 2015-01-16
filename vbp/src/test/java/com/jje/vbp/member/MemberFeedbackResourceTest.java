package com.jje.vbp.member;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.vbp.member.MemberFeedbackChannel;
import com.jje.dto.vbp.member.MemberFeedbackDto;
import com.jje.dto.vbp.member.MemberFeedbackSources;
import com.jje.dto.vbp.member.MemberFeedbackStatus;
import com.jje.dto.vbp.member.MemberSubFeedbackDto;
import com.jje.membercenter.DataPrepareFramework;

public class MemberFeedbackResourceTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void should_query_feedback_success() throws Exception {

		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doPost("memberFeedbackResource", MemberFeedbackResource.class, "/memberFeedback/queryFeedback", buildMemberFeedbackDto(),
				ResultMemberDto.class);

		ResultMemberDto memDto = result.getOutput();
		Assert.assertNotNull(memDto);
		@SuppressWarnings("unchecked")
		List<MemberFeedbackDto> dtos = memDto.getResults();
		Assert.assertTrue(dtos != null && dtos.size() > 0);
		MemberFeedbackDto dto = dtos.get(0);
		Assert.assertEquals("10086", dto.getOperator());
	}
	
	@Test
	public void should_getFeedbackById_success() throws Exception {
		
		InvokeResult<MemberFeedbackDto> result = resourceInvokeHandler.doGet("memberFeedbackResource", MemberFeedbackResource.class, "/memberFeedback/getFeedbackById/"+1,
				MemberFeedbackDto.class);
		
		MemberFeedbackDto memDto = result.getOutput();
		Assert.assertNotNull(memDto);
		List<MemberSubFeedbackDto> dtos = memDto.getSubFeedbacks();
		Assert.assertTrue(dtos != null && dtos.size() > 0);
		MemberSubFeedbackDto dto = dtos.get(0);
		Assert.assertEquals("test", dto.getOperator());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void should_insert_feedback_success() throws Exception {

		InvokeResult result = resourceInvokeHandler.doPost("memberFeedbackResource", MemberFeedbackResource.class, "/memberFeedback/saveFeedback", buildInsertMemberFeedbackDto(), Response.class);

		Assert.assertEquals(Status.OK, result.getStatus());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void should_insert_sub_feedback_success() throws Exception {
		
		InvokeResult result = resourceInvokeHandler.doPost("memberFeedbackResource", MemberFeedbackResource.class, "/memberFeedback/saveSubFeedback", buildInsertMemberSubFeedbackDto(), Response.class);
		
		Assert.assertEquals(Status.OK, result.getStatus());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void should_update_feedback_success() throws Exception {

		InvokeResult result = resourceInvokeHandler.doPost("memberFeedbackResource", MemberFeedbackResource.class, "/memberFeedback/updateFeedback", buildUpdateMemberFeedbackDto(), Response.class);

		Assert.assertEquals(Status.OK, result.getStatus());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	@Ignore
	public void should_queryFeedbackStatistics_success() throws Exception {
		
		InvokeResult result = resourceInvokeHandler.doGet("memberFeedbackResource", MemberFeedbackResource.class, "/memberFeedback/queryFeedbackStatistics", Response.class);
		Assert.assertEquals(Status.OK, result.getStatus());
	}

	private QueryMemberDto<MemberFeedbackDto> buildMemberFeedbackDto() {
		QueryMemberDto<MemberFeedbackDto> queryDto = new QueryMemberDto<MemberFeedbackDto>();
		MemberFeedbackDto dto = new MemberFeedbackDto();
		dto.setMcMemberCode("10000089");
		dto.setChannel(MemberFeedbackChannel.TRAVEL);
		queryDto.setCondition(dto);
		queryDto.setPagination(null);
		return queryDto;
	}

	private MemberFeedbackDto buildInsertMemberFeedbackDto() {
		MemberFeedbackDto dto = new MemberFeedbackDto();
		dto.setMcMemberCode("10000089");
		dto.setChannel(MemberFeedbackChannel.TRAVEL);
		dto.setStatus(MemberFeedbackStatus.UNTREATED);
		dto.setSources(MemberFeedbackSources.WEB);
		dto.setContent("ts");
		dto.setOperator("10086");
		return dto;
	}
	
	private MemberSubFeedbackDto buildInsertMemberSubFeedbackDto() {
		MemberSubFeedbackDto dto = new MemberSubFeedbackDto();
		dto.setFeedbackId(1L);
		dto.setContent("ts");
		dto.setOperator("10086");
		return dto;
	}

	private MemberFeedbackDto buildUpdateMemberFeedbackDto() {
		MemberFeedbackDto dto = new MemberFeedbackDto();
		dto.setMcMemberCode("10000089");
		dto.setChannel(MemberFeedbackChannel.TRAVEL);
		dto.setStatus(MemberFeedbackStatus.UNTREATED);
		dto.setContent("ts");
		dto.setOperator("10087");
		return dto;
	}
}
