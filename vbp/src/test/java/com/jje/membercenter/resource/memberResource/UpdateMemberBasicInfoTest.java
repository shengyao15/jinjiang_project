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
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class UpdateMemberBasicInfoTest extends DataPrepareFramework {

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
	public void should_be_success_when_updateMemberBasicInfo() throws Exception {
		Mockito.when(crmMembershipProxy.updateMemberBasicInfo(Mockito.any(MemberBasicInfoUpdateRequest.class)))
				.thenReturn(mockMemberBasicInfoUpdateResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);

		InvokeResult<String> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/profile/updateBaseInfo", mockMemberBasicInfoDto(), String.class);
		String retCode= result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNull(retCode);
	}

	private MemberBasicInfoUpdateResponse mockMemberBasicInfoUpdateResponse() {
		MemberBasicInfoUpdateResponse response = new MemberBasicInfoUpdateResponse();
		return response;
	}

	private MemberBasicInfoDto mockMemberBasicInfoDto() {
		MemberBasicInfoDto dto = new MemberBasicInfoDto();
		dto.setCdno("");
		return dto;
	}
}
