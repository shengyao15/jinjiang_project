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
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class UpdateVIPCardInfoTest extends DataPrepareFramework {

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
	public void should_be_success_when_update_vip_card_info() throws Exception {
		Mockito.when(crmMembershipProxy.updateVIPCardInfo(Mockito.any(SynRightCardRequest.class)))
				.thenReturn(getSynRightCardResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);

		InvokeResult<CRMResponseDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/updateVIPCardInfo", getMemberUpdateDto(), CRMResponseDto.class);
		CRMResponseDto crmResponseDto = result.getOutput();
		Assert.assertNotNull(crmResponseDto);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	private Object getMemberUpdateDto() {
		MemberUpdateDto dto = new MemberUpdateDto();
		return dto;
	}

	private SynRightCardResponse getSynRightCardResponse() {
		SynRightCardResponse synRightCardResponse = new SynRightCardResponse();
		SynRightCardResponse.Head head = new SynRightCardResponse.Head();
		synRightCardResponse.setHead(head);
		return synRightCardResponse;
	}
}
