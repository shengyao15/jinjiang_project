package com.jje.membercenter.resource.memberXmlResource;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.payment.PayResultForBizDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberXmlResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMResumeCardRepository;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;


public class PayForResumeCardTest extends DataPrepareFramework {

    @Autowired
	private CRMMembershipProxy crmMembershipProxy;
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Autowired
	private CRMResumeCardRepository crmResumeCardRepository;
    @Mock
    private CRMMembershipProxy spyCrmMembershipProxy;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test   
	public void should_be_success_when_pay_for_resume_card() throws Exception {
		Mockito.when(spyCrmMembershipProxy.updateVIPCardInfo(Mockito.any(SynRightCardRequest.class)))
		.thenReturn(getSynRightCardResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmResumeCardRepository), "crmMembershipProxy",spyCrmMembershipProxy);
		
		InvokeResult<String> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class,
				"/member/payForResumeCard?uuid=2ac51d6ac4cc41e9925bce2f81c2244d", getPayResultForBizDto(), String.class);
		
		
		String repeat = result.getOutput();
		Assert.assertEquals("success", repeat);
		Assert.assertEquals(result.getStatus(), Status.OK);
        resourceInvokeHandler.setField(crmResumeCardRepository, "crmMembershipProxy",crmMembershipProxy);
	}

	private SynRightCardResponse getSynRightCardResponse() {
		SynRightCardResponse  synRightCardResponse = new SynRightCardResponse();
		SynRightCardResponse.Head  head = new SynRightCardResponse.Head();
		head.setRetcode("11");
		head.setRetmsg("msg");
		head.setTranscode("");
		synRightCardResponse.setHead(head);
		return synRightCardResponse;
	}

	private PayResultForBizDto getPayResultForBizDto() {
		PayResultForBizDto  payResultForBizDto = new PayResultForBizDto();
		payResultForBizDto.setOrderNo("V30e20ce8");
		return payResultForBizDto;
	}

}
