package com.jje.membercenter.resource.memberResource;

import java.math.BigInteger;

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
import com.jje.dto.membercenter.MemberQuickRegisterDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.QuickRegisterRequest;
import com.jje.membercenter.xsd.QuickRegisterResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QuickRegisterTest extends DataPrepareFramework {

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
	public void should_be_success_when_quickRegister() throws Exception {
		Mockito.when(crmMembershipProxy.quickRegister(Mockito.any(QuickRegisterRequest.class)))
				.thenReturn(getQuickRegisterResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);

		InvokeResult<CRMResponseDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/quickRegister", getMemberQuickRegisterDto(), CRMResponseDto.class);
		CRMResponseDto crmResponseDto = result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNotNull(crmResponseDto);
	}

	private QuickRegisterResponse getQuickRegisterResponse() {
		QuickRegisterResponse quickRegisterResponse = new QuickRegisterResponse();
		QuickRegisterResponse.Body body = new QuickRegisterResponse.Body();
		body.setMembid("");
		body.setRecode(new BigInteger("1"));
		body.setRemsg("");
		quickRegisterResponse.setBody(body);
		return quickRegisterResponse;
	}

	private MemberQuickRegisterDto getMemberQuickRegisterDto() {
		MemberQuickRegisterDto dto = new MemberQuickRegisterDto();
		return dto;
	}
}
