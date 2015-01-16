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
import com.jje.membercenter.xsd.UpdateContactRequest;
import com.jje.membercenter.xsd.UpdateContactResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class UpdateContactInfoTest extends DataPrepareFramework {

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
	public void should_be_success_when_update_contract_info() throws Exception {
		Mockito.when(crmMembershipProxy.updateContact(Mockito.any(UpdateContactRequest.class)))
				.thenReturn(getUpdateContactResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);

		InvokeResult<String> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/profile/updateContactInfo", getMemberCommunicationDto(), String.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}
	
	private MemberCommunicationDto getMemberCommunicationDto(){
		MemberCommunicationDto  memberCommunicationDto = new MemberCommunicationDto();
		memberCommunicationDto.setEmail("sfds@sina.com");
		return memberCommunicationDto;
	}
	
	
	
	private UpdateContactResponse getUpdateContactResponse() {
		UpdateContactResponse updateContactResponse = new UpdateContactResponse();
		UpdateContactResponse.Body body = new UpdateContactResponse.Body();
		UpdateContactResponse.Head head = new UpdateContactResponse.Head();
		updateContactResponse.setBody(body);
		updateContactResponse.setHead(head);
		return updateContactResponse;
	}

}
