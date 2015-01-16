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
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;
import com.jje.membercenter.xsd.UpdateMemberAddressRequest;
import com.jje.membercenter.xsd.UpdateMemberAddressResponse;

public class PayForUpdateTest extends DataPrepareFramework {

	@Mock
	private CRMMembershipProxy crmMembershipProxy;
	
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Autowired
	private CRMMembershipRepository crmMembershipRepository;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test   
	public void should_be_success_when_payForUpdate() throws Exception {
		Mockito.when(crmMembershipProxy.updateVIPCardInfo(Mockito.any(SynRightCardRequest.class))).thenReturn(getSynRightCardResponse());
		Mockito.when(crmMembershipProxy.updateMemberAddress(Mockito.any(UpdateMemberAddressRequest.class))).thenReturn(getUpdateMemberAddressResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",crmMembershipProxy);
		
		InvokeResult<String> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class,
				"/member/payForUpdate?uuid=2ac51d6ac4cc41e9925bce2f81c3355d", getPayResultForBizDto(), String.class);
		
		String repeat = result.getOutput();
		Assert.assertEquals("success", repeat);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	private UpdateMemberAddressResponse getUpdateMemberAddressResponse() {
		UpdateMemberAddressResponse response = new UpdateMemberAddressResponse();
		UpdateMemberAddressResponse.Body body = new UpdateMemberAddressResponse.Body();
		response.setBody(body);
		body.setRecode("jack");
		return response;
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
		payResultForBizDto.setOrderNo("V131214a79a");
		return payResultForBizDto;
	}

}
