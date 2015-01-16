package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import com.jje.membercenter.crmdatagram.Listofcontact;
import com.jje.membercenter.xsd.MemberRequest;
import com.jje.membercenter.xsd.MemberResponse;
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
import com.jje.membercenter.xsd.MemberHobbyUpdateRequest;
import com.jje.membercenter.xsd.MemberHobbyUpdateResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class UpdateHobbiesTest extends DataPrepareFramework {

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
	public void should_be_success_when_updateHobbies() throws Exception {
		Mockito.when(crmMembershipProxy.updateHobbies(Mockito.any(MemberHobbyUpdateRequest.class)))
				.thenReturn(mockMemberHobbyUpdateResponse());
        Mockito.when(crmMembershipProxy.getMember(Mockito.any(MemberRequest.class)))
                .thenReturn(anyMemberResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);

		InvokeResult<String> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/updateMemberHobby", mockMemberHobbyDto(), String.class);
		String retCode= result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNotNull(retCode);
		Assert.assertEquals("00001", retCode);
	}

	private MemberHobbyUpdateResponse mockMemberHobbyUpdateResponse() {
		MemberHobbyUpdateResponse response = new MemberHobbyUpdateResponse();
		MemberHobbyUpdateResponse.Body body = new MemberHobbyUpdateResponse.Body();
		body.setRecode("00001");
		response.setBody(body);
		return response;
	}

    private MemberResponse anyMemberResponse() {
        MemberResponse response = new MemberResponse();
        MemberResponse.Head head = new MemberResponse.Head();
        MemberResponse.Body.Listofcontact.Contact contact = new MemberResponse.Body.Listofcontact.Contact();
        MemberResponse.Body body = new MemberResponse.Body();
        MemberResponse.Body.Listofcontact ls = new MemberResponse.Body.Listofcontact();
        ls.setContact(contact);
        body.setListofcontact(ls);
        head.setRetcode("00001");
        response.setHead(head);
        response.setBody(body);
        return response;
    }

	private MemberHobbyDto mockMemberHobbyDto() {
		MemberHobbyDto dto = new MemberHobbyDto();
        dto.setMemberId("423424");
        dto.setEmail("qq@qq.com");
		return dto;
	}
}
