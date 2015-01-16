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
import com.jje.dto.ResultMemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberAddressRequest;
import com.jje.membercenter.xsd.MemberAddressResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryMemberAddressTest extends DataPrepareFramework {

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

	@SuppressWarnings("rawtypes")
	@Test   
	public void should_be_success_when_Query_Member_Address() throws Exception {
		Mockito.when(crmMembershipProxy.queryMemberAddress(Mockito.any(MemberAddressRequest.class)))
				.thenReturn(getMemberAddressResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy",crmMembershipProxy);

		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/profile/memberAddress", "11220", ResultMemberDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy",CRMMembershipProxy.class);
	}

	
	private MemberAddressResponse getMemberAddressResponse() {
		MemberAddressResponse memberAddressResponse = new MemberAddressResponse();
		MemberAddressResponse.Body body = new MemberAddressResponse.Body();
		MemberAddressResponse.Body.Listofcontact value = new MemberAddressResponse.Body.Listofcontact();
		MemberAddressResponse.Body.Listofcontact.Contact contact = new MemberAddressResponse.Body.Listofcontact.Contact();
		MemberAddressResponse.Body.Listofcontact.Contact.Listofpersonaladdress  Listofpersonaladdress= new MemberAddressResponse.Body.Listofcontact.Contact.Listofpersonaladdress();
		MemberAddressResponse.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress personaddress = new MemberAddressResponse.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress();
		Listofpersonaladdress.getPersonaladdress().add(personaddress);
		contact.setListofpersonaladdress(Listofpersonaladdress);
		value.setContact(contact);
		body.setMembid("");
		body.setListofcontact(value);
		memberAddressResponse.setBody(body);
		return memberAddressResponse;
	}

}
