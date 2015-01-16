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
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberRequest;
import com.jje.membercenter.xsd.MemberResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryMemberBasicInfoTest extends DataPrepareFramework {

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
		Mockito.when(crmMembershipProxy.getMember(Mockito.any(MemberRequest.class)))
				.thenReturn(mockMemberResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy",crmMembershipProxy);

		InvokeResult<MemberBasicInfoDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/profile/baseInfo", "memberId", MemberBasicInfoDto.class);
		MemberBasicInfoDto memberBasicInfoDto= result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNotNull(memberBasicInfoDto);
		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy",CRMMembershipProxy.class);
	}

	private MemberResponse mockMemberResponse() {
		MemberResponse response = new MemberResponse();
		MemberResponse.Body body = new MemberResponse.Body();
		MemberResponse.Body.Listofcontact listofcontact = new MemberResponse.Body.Listofcontact();
		body.setListofcontact(listofcontact);
		response.setBody(body);
		return response;
	}
}
