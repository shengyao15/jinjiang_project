package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberRequest;
import com.jje.membercenter.xsd.MemberResponse;

public class BindTest extends DataPrepareFramework {

	@Mock
	private CRMMembershipProxy spyCrmMembershipProxy;
	
    @Autowired
    private CRMMembershipRepository crmMembershipRepository;
    
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test   
	public void should_be_success_when_bind() throws Exception {
		Mockito.when(spyCrmMembershipProxy.getMember(Mockito.any(MemberRequest.class)))
				.thenReturn(getMemberResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy", spyCrmMembershipProxy);

		InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/bind", "1221", MemberDto.class);
		Assert.assertEquals(result.getStatus(), Status.NO_CONTENT);
		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy", CRMMembershipProxy.class);
	}
	
	
	
	
	
	private MemberResponse getMemberResponse() {
		MemberResponse memberResponse = new MemberResponse();
		MemberResponse.Body body = new MemberResponse.Body();
		MemberResponse.Head head = new MemberResponse.Head();
		head.setRetcode("1");
		memberResponse.setBody(body);
		memberResponse.setHead(head);
		return memberResponse;
	}

}
