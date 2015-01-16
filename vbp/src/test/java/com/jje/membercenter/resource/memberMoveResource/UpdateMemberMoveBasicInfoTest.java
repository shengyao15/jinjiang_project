package com.jje.membercenter.resource.memberMoveResource;

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
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberMoveResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class UpdateMemberMoveBasicInfoTest extends DataPrepareFramework {

	@Mock
	private CRMMembershipProxy spyCRMMembershipProxy;
	
	@Autowired
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
	public void should_be_success_when_updateMemberMoveBasicInfo() throws Exception {
		Mockito.when(spyCRMMembershipProxy.updateMemberBasicInfo(Mockito.any(MemberBasicInfoUpdateRequest.class)))
				.thenReturn(getMemberBasicInfoUpdateResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				spyCRMMembershipProxy);

		InvokeResult<String> result = resourceInvokeHandler.doPost("memberMoveResource", MemberMoveResource.class,
				"/membermove/profile/updateBaseInfo", getMemberBasicInfoDto(), String.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);
	}

	
	
	private MemberBasicInfoDto getMemberBasicInfoDto() {
		MemberBasicInfoDto dto = new MemberBasicInfoDto();
		dto.setCell("");
		dto.setEmail("");
		dto.setTitle("");
		dto.setMemberId("");
		dto.setBingFlg("");
		dto.setPassword("");
		return dto;
	}

	private MemberBasicInfoUpdateResponse getMemberBasicInfoUpdateResponse() {
		MemberBasicInfoUpdateResponse response = new MemberBasicInfoUpdateResponse();
		return response;
	}
}
