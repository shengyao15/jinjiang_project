package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberBindRequest;
import com.jje.membercenter.xsd.MemberBindResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class BindEmailTest extends DataPrepareFramework {

	@Mock
	private CRMMembershipProxy spyCrmMembershipProxy;

	@Autowired
	private CRMMembershipProxy crmMembershipProxy;
	
    @Autowired
    private CRMMembershipRepository crmMembershipRepository;
    
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test   
	public void should_be_success_when_bindEmail() throws Exception {
		Mockito.when(spyCrmMembershipProxy.bindPhone(Mockito.any(MemberBindRequest.class)))
				.thenReturn(getMemberBindResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				spyCrmMembershipProxy);

		InvokeResult<CRMResponseDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/bindEmail", getMemberDto(), CRMResponseDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);
	}
	
	
	private MemberDto getMemberDto(){
		MemberDto  memberDto = new MemberDto();
		memberDto.setActivateCode("");
		return memberDto;
	}
	
	
	private MemberBindResponse getMemberBindResponse() {
		MemberBindResponse memberBindResponse = new MemberBindResponse();
		MemberBindResponse.Body body = new MemberBindResponse.Body();
		MemberBindResponse.Head head = new MemberBindResponse.Head();
		head.setRetcode("1");
		memberBindResponse.setBody(body);
		memberBindResponse.setHead(head);
		return memberBindResponse;
	}

}
