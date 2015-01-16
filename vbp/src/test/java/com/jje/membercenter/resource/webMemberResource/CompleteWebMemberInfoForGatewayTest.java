package com.jje.membercenter.resource.webMemberResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.RegistResponse;
import com.jje.dto.membercenter.RegistResponseStatus;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.WebMemberResource;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.remote.handler.MemberHandler;

public class CompleteWebMemberInfoForGatewayTest extends DataPrepareFramework {

	@Mock
	private MemberHandler spyMemberHandler;
	
	@Mock
	MemberRepository spyMemberRepository;

	@Autowired
	private WebMemberResource  webMemberResource;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_be_register_success_when_given_member_not_exists()
			throws Exception {
		Mockito.when(spyMemberHandler.updateQuickMemberBaseInfo(Mockito.any(Member.class))).thenReturn(mockRegistResponse());
		resourceInvokeHandler.setField(webMemberResource, "memberHandler", spyMemberHandler);

		InvokeResult<RegistResponse> result = resourceInvokeHandler.doPost("webMemberResource", WebMemberResource.class, "/webMember/completeWebMemberInfoForGateway", mockMemberDto(), RegistResponse.class);
		Assert.assertEquals(Status.OK, result.getStatus());
		Assert.assertEquals("12345", result.getOutput().getMcMemberCode());
		Assert.assertEquals(RegistResponseStatus.OK, result.getOutput().getStatus());

		//resourceInvokeHandler.setField(webMemberResource, "memberHandler", MemberHandler.class);
	}
	
	@Test
	public void should_be_register_success_when_given_member_exists()
			throws Exception {
		InvokeResult<RegistResponse> result = resourceInvokeHandler.doPost("webMemberResource", WebMemberResource.class, "/webMember/completeWebMemberInfoForGateway", mockExistsMemberDto(), RegistResponse.class);
		Assert.assertEquals(Status.OK, result.getStatus());
		Assert.assertEquals(RegistResponseStatus.MEMBER_ALREDY_REGIST, result.getOutput().getStatus());

	}

	private RegistResponse mockRegistResponse() {
		RegistResponse result = new RegistResponse();
		result.setStatus(RegistResponseStatus.OK);
		result.setMcMemberCode("12345");
		return result;
	}

	private MemberDto mockMemberDto() {
		MemberDto memberDto = new MemberDto();
		memberDto.setMcMemberCode("3231663100");
		return memberDto;
	}
	
	private MemberDto mockExistsMemberDto() {
		MemberDto memberDto = new MemberDto();
		memberDto.setMcMemberCode("12345");
		memberDto.setIdentityNo("112312111");
		memberDto.setIdentityType("Others");
		return memberDto;
	}
}
