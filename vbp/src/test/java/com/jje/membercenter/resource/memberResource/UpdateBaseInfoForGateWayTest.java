package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.UpdateBasicInfoResult;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateResponse;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UpdateBaseInfoForGateWayTest extends DataPrepareFramework {
	
	@Mock
	CRMMembershipProxy spyCrmMembershipProxy;
	
	@Autowired
	CRMMembershipProxy crmMembershipProxy;
	
	@Autowired
	private CRMMembershipRepository crmMembershipRepository;

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void should_be_update_fail_when_given_only_have_email_basicInfo() throws Exception {
		InvokeResult<String> postResult = this.resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/profile/updateBaseInfoForGateWay", mockMemberBasicInfoDto(Type.EMAIL), String.class);
		Assert.assertEquals(Status.BAD_REQUEST, postResult.getStatus());
		Assert.assertEquals(UpdateBasicInfoResult.PARAM_INVALID.getCode(), postResult.getOutput());
	}
	
	@Test
	public void should_be_update_fail_when_given_only_have_cell_basicInfo() {
		InvokeResult<String> postResult = this.resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/profile/updateBaseInfoForGateWay", mockMemberBasicInfoDto(Type.CELL), String.class);
		Assert.assertEquals(Status.BAD_REQUEST, postResult.getStatus());
		Assert.assertEquals(UpdateBasicInfoResult.PARAM_INVALID.getCode(), postResult.getOutput());
	}
	
	@Test
	public void should_be_update_fail_when_given_only_have_mcMemberCode_basicInfo() {
		InvokeResult<String> postResult = this.resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/profile/updateBaseInfoForGateWay", mockMemberBasicInfoDto(Type.MC), String.class);
		Assert.assertEquals(Status.BAD_REQUEST, postResult.getStatus());
		Assert.assertEquals(UpdateBasicInfoResult.PARAM_INVALID.getCode(), postResult.getOutput());
	}
	
	@Test
	public void should_be_update_fail_when_given_only_have_email_and_cell_basicInfo() {
		InvokeResult<String> postResult = this.resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/profile/updateBaseInfoForGateWay", mockMemberBasicInfoDto(Type.EMAIL_CELL), String.class);
		Assert.assertEquals(Status.BAD_REQUEST, postResult.getStatus());
		Assert.assertEquals(UpdateBasicInfoResult.PARAM_INVALID.getCode(), postResult.getOutput());
	}
	
	@Test
	public void should_be_update_success_when_given_only_have_mcMemberCode_and_email_basicInfo() throws Exception {
		Mockito.when(spyCrmMembershipProxy.updateMemberBasicInfo(Mockito.any(MemberBasicInfoUpdateRequest.class))).thenReturn(mockMemberBasicInfoUpdateResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", spyCrmMembershipProxy);
		InvokeResult<String> postResult = this.resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/profile/updateBaseInfoForGateWay", mockMemberBasicInfoDto(Type.MC_EMAIL), String.class);
		Assert.assertEquals(Status.OK, postResult.getStatus());
		Assert.assertEquals(UpdateBasicInfoResult.SUCCESS.getCode(), postResult.getOutput());
	}
	
	@Test
	public void should_be_update_success_when_given_only_have_mcMemberCode_and_cell_basicInfo() throws Exception {
		Mockito.when(spyCrmMembershipProxy.updateMemberBasicInfo(Mockito.any(MemberBasicInfoUpdateRequest.class))).thenReturn(mockMemberBasicInfoUpdateResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", spyCrmMembershipProxy);
		InvokeResult<String> postResult = this.resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/profile/updateBaseInfoForGateWay", mockMemberBasicInfoDto(Type.MC_CELL), String.class);
		Assert.assertEquals(Status.OK, postResult.getStatus());
		Assert.assertEquals(UpdateBasicInfoResult.SUCCESS.getCode(), postResult.getOutput());
	}
	
	@Test
	public void should_be_update_success_when_given_have_all_basicInfo() throws Exception {
		Mockito.when(spyCrmMembershipProxy.updateMemberBasicInfo(Mockito.any(MemberBasicInfoUpdateRequest.class))).thenReturn(mockMemberBasicInfoUpdateResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", spyCrmMembershipProxy);
		InvokeResult<String> postResult = this.resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/profile/updateBaseInfoForGateWay", mockMemberBasicInfoDto(Type.ALL), String.class);
		Assert.assertEquals(Status.OK, postResult.getStatus());
		Assert.assertEquals(UpdateBasicInfoResult.SUCCESS.getCode(), postResult.getOutput());
	}
	
	@Test(expected=Exception.class)
	public void should_be_update_null_exception_when_given_invalid_mcMemberCode() {
		this.resourceInvokeHandler.doPost("memberResource", MemberResource.class, "/member/profile/updateBaseInfoForGateWay", mockMemberBasicInfoDto(Type.INVALID_MC), String.class);
	}
	
	private MemberBasicInfoUpdateResponse mockMemberBasicInfoUpdateResponse() {
		MemberBasicInfoUpdateResponse response = new MemberBasicInfoUpdateResponse();
		MemberBasicInfoUpdateResponse.Body body = new MemberBasicInfoUpdateResponse.Body();
		body.setRecode(UpdateBasicInfoResult.SUCCESS.getCode());
		response.setBody(body);
		return response;
	}
	
	private MemberBasicInfoDto mockMemberBasicInfoDto(Type type) {
		MemberBasicInfoDto dto = new MemberBasicInfoDto();
		switch (type) {
			case ALL:
				dto.setMcMemberCode("10082");
				dto.setEmail("xiaolinjava@163.com");
				dto.setCell("13122222222");
				break;
			case EMAIL:
				dto.setEmail("xiaolinjava@163.com");
				break;
			case CELL:
				dto.setCell("13122222222");
				break;
			case MC:
				dto.setMcMemberCode("111111");
				break;
			case MC_EMAIL:
				dto.setMcMemberCode("10082");
				dto.setEmail("xiaolinjava@163.com");
				break;
			case MC_CELL:
				dto.setMcMemberCode("10083");
				dto.setCell("13122222222");
				break;
			case EMAIL_CELL:
				dto.setEmail("xiaolinjava@163.com");
				dto.setCell("13122222222");
				break;
			case INVALID_MC:
				dto.setMcMemberCode("invalid mc");
				dto.setEmail("xiaolinjava@163.com");
				dto.setCell("13122222222");
				break;
		}
		return dto;
	}
	
	private enum Type {
		ALL, EMAIL, CELL, MC, MC_EMAIL, MC_CELL, EMAIL_CELL,INVALID_MC;
	}
	
	@After
	public void after() throws Exception {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", crmMembershipProxy);
	}

}