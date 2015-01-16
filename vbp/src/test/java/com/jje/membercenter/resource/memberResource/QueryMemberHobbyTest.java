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
import com.jje.dto.membercenter.MemberHobbyDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberHobbyRequest;
import com.jje.membercenter.xsd.MemberHobbyResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryMemberHobbyTest extends DataPrepareFramework {

	@Mock
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
	public void should_be_success_when_query_member_hobby() throws Exception {
		Mockito.when(crmMembershipProxy.queryMemberHobby(Mockito.any(MemberHobbyRequest.class)))
				.thenReturn(getMemberHobbyResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy",crmMembershipProxy);

		InvokeResult<MemberHobbyDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/queryMemberHobby", "memberId", MemberHobbyDto.class);
		MemberHobbyDto memberHobbyDto= result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNotNull(memberHobbyDto);
		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy",CRMMembershipProxy.class);
	}

	private MemberHobbyResponse getMemberHobbyResponse() {
		MemberHobbyResponse response = new MemberHobbyResponse();
		return response;
	}
}
