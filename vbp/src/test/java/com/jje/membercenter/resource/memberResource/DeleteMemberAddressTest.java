package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

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
import com.jje.dto.membercenter.MemberAddressDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberAddressDeleteRequest;
import com.jje.membercenter.xsd.MemberAddressDeleteResponse;

public class DeleteMemberAddressTest extends DataPrepareFramework {

	@Mock
	CRMMembershipProxy spyCRMMembershipProxy;

    @Autowired
    private CRMMembershipRepository crmMembershipRepository;
    													  

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test
	public void should_be_success_when_set_delete_Member_Address() throws Exception {
		Mockito.when(spyCRMMembershipProxy.deleteMemberAddress(Mockito.any(MemberAddressDeleteRequest.class)))
				.thenReturn(getMemberAddressDeleteResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy",spyCRMMembershipProxy);

		InvokeResult<String> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/profile/deleteMemberAddress", getAddressDtos(), String.class);
		Assert.assertEquals(result.getStatus(), Status.OK);

		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy",CRMMembershipProxy.class);
	}

	
	private MemberAddressDto getAddressDtos(){
		MemberAddressDto addressDtos = new MemberAddressDto();
		addressDtos.setAddr("shanghai");
		return addressDtos;
	}
	private MemberAddressDeleteResponse getMemberAddressDeleteResponse() {
		MemberAddressDeleteResponse memberAddressDeleteResponse = new MemberAddressDeleteResponse();
		MemberAddressDeleteResponse.Body body = new MemberAddressDeleteResponse.Body();
		body.setMembid("");
		body.setRecode("");
		body.setRemsg("");
		memberAddressDeleteResponse.setBody(body);
		return memberAddressDeleteResponse;
	}

}
