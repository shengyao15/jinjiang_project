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
import com.jje.dto.membercenter.CardDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberRequest;
import com.jje.membercenter.xsd.MemberResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryCardStautsTest extends DataPrepareFramework {

	@Mock
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
	public void should_be_success_when_query_cardStauts() throws Exception {
		Mockito.when(crmMembershipProxy.queryCardStauts(Mockito.any(MemberRequest.class)))
				.thenReturn(getMemberResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy",crmMembershipProxy);

		InvokeResult<CardDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/queryCardStauts", getMemberDto(), CardDto.class);
		CardDto cardDto = result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNotNull(cardDto);
		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy",CRMMembershipProxy.class);
	}

	private MemberResponse getMemberResponse() {
		MemberResponse memberResponse = new MemberResponse();
		MemberResponse.Body body = new MemberResponse.Body();
		memberResponse.setBody(body);
		return memberResponse;
	}

	@SuppressWarnings("deprecation")
	private MemberDto getMemberDto() {
		MemberDto dto = new MemberDto();
		dto.setMemberID("");
		return dto;
	}

}
