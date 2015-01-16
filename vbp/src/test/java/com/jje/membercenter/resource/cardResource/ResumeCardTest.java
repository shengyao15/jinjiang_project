package com.jje.membercenter.resource.cardResource;

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
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.ResumeCardDto;
import com.jje.membercenter.CardResource;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMResumeCardRepository;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class ResumeCardTest extends DataPrepareFramework {

	@Mock
	CRMMembershipProxy spyCrmMembershipProxy;
	
	@Autowired
	CRMMembershipProxy crmMembershipProxy;

	@Autowired
	private CRMResumeCardRepository crmResumeCardRepository;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_resumeCard() throws Exception {
		Mockito.when(spyCrmMembershipProxy.updateVIPCardInfo(Mockito.any(SynRightCardRequest.class)))
				.thenReturn(getSynRightCardResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmResumeCardRepository), "crmMembershipProxy",
				spyCrmMembershipProxy);
		InvokeResult<CRMResponseDto> result = resourceInvokeHandler.doPost("cardResource", CardResource.class,
				"/card/resumeCard", getResumeCardDto(), CRMResponseDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		Mockito.verify(spyCrmMembershipProxy).updateVIPCardInfo(Mockito.any(SynRightCardRequest.class));

		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmResumeCardRepository), "crmMembershipProxy", crmMembershipProxy);
	}
	private ResumeCardDto getResumeCardDto(){
		ResumeCardDto resumeCardDto = new ResumeCardDto();
		resumeCardDto.setMembid("100");
		resumeCardDto.setMemcardno("200");
		resumeCardDto.setAction("update");
		return resumeCardDto;
	}
	
	
	private SynRightCardResponse getSynRightCardResponse() {
		SynRightCardResponse synRightCardResponse = new SynRightCardResponse();
		SynRightCardResponse.Body body = new SynRightCardResponse.Body();
		SynRightCardResponse.Head head = new SynRightCardResponse.Head();
		head.setRetcode("100");
		head.setRetmsg("200");
		body.setMembid("100");
		body.setRecode("200");
		body.setRemsg("300");
		synRightCardResponse.setBody(body);
		synRightCardResponse.setHead(head);
		return synRightCardResponse;
	}

}
