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
import com.jje.dto.membercenter.MemberCommunicationDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberCommunicationRequest;
import com.jje.membercenter.xsd.MemberCommunicationResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryContactInfoTest extends DataPrepareFramework {

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
	public void should_be_success_when_query_member_Communication() throws Exception {
		Mockito.when(crmMembershipProxy.queryMemberCommuniction(Mockito.any(MemberCommunicationRequest.class)))
				.thenReturn(getMemberCommunicationResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy", crmMembershipProxy);

		InvokeResult<MemberCommunicationDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/profile/contactInfo", "12344", MemberCommunicationDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy", CRMMembershipProxy.class);
	}
	
	
	
	
	private MemberCommunicationResponse getMemberCommunicationResponse() {
		MemberCommunicationResponse memberCommunicationResponse = new MemberCommunicationResponse();
		MemberCommunicationResponse.Body body = new MemberCommunicationResponse.Body();
		body.setMembid("1");
		MemberCommunicationResponse.Body.Listofcontact value = new MemberCommunicationResponse.Body.Listofcontact();
		MemberCommunicationResponse.Body.Listofcontact.Contact contact = new MemberCommunicationResponse.Body.Listofcontact.Contact();
		value.setContact(contact);
		body.setMembid("");
		body.setListofcontact(value);
		memberCommunicationResponse.setBody(body);
		return memberCommunicationResponse;
	}

}
