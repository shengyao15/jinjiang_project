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
import com.jje.dto.membercenter.ContactQqAndMsnDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.UpdateContactRequest;
import com.jje.membercenter.xsd.UpdateContactResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class InsertContactInfoTest extends DataPrepareFramework {

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
	public void should_be_success_when_insert_contactInfo() throws Exception {
		Mockito.when(crmMembershipProxy.updateContact(Mockito.any(UpdateContactRequest.class)))
				.thenReturn(getUpdateContactResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy", crmMembershipProxy);

		InvokeResult<CRMResponseDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/profile/insertContactInfo", getContactQqAndMsnDto(), CRMResponseDto.class);
		CRMResponseDto crmResponseDto= result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNotNull(crmResponseDto);
		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy", CRMMembershipProxy.class);
	}

	private UpdateContactResponse getUpdateContactResponse() {
		UpdateContactResponse response = new UpdateContactResponse();
		UpdateContactResponse.Body body = new UpdateContactResponse.Body();
		response.setBody(body);
		return response;
	}

	private ContactQqAndMsnDto getContactQqAndMsnDto() {
		ContactQqAndMsnDto dto = new ContactQqAndMsnDto();
		return dto;
	}

}
