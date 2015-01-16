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
import com.jje.dto.ResultMemberDto;
import com.jje.membercenter.CardResource;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.crm.CRMUpdateRightCardProxy;
import com.jje.membercenter.domain.CRMUpdateRightCardRepository;
import com.jje.membercenter.xsd.QueryRightCardInfoRequest;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse.Body.Rightcard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class ListCardTest extends DataPrepareFramework {

	@Mock
	CRMUpdateRightCardProxy spyCRMUpdateRightCardProxy;

	@Autowired
	CRMUpdateRightCardProxy crmUpdateRightCardProxy;

	@Autowired
	private CRMUpdateRightCardRepository crmUpdateRightCardRepository;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@SuppressWarnings("rawtypes")
	@Test
	public void should_be_success_when_listCard() throws Exception {
		Mockito.when(spyCRMUpdateRightCardProxy.queryVIPCardInfo(Mockito.any(QueryRightCardInfoRequest.class)))
				.thenReturn(getQueryRightCardInfoResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmUpdateRightCardRepository), "crmUpdateRightCardProxy",
				spyCRMUpdateRightCardProxy);

		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doPost("cardResource", CardResource.class,
				"/card/listCard", new String("1000"), ResultMemberDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		Mockito.verify(spyCRMUpdateRightCardProxy).queryVIPCardInfo(Mockito.any(QueryRightCardInfoRequest.class));

		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmUpdateRightCardRepository), "crmUpdateRightCardProxy", crmUpdateRightCardProxy);
	}

	private QueryRightCardInfoResponse getQueryRightCardInfoResponse() {
		QueryRightCardInfoResponse queryRightCardInfoResponse = new QueryRightCardInfoResponse();
		QueryRightCardInfoResponse.Body body = new QueryRightCardInfoResponse.Body();
		Rightcard rightcard = new Rightcard();
		rightcard.setEnddate("");
		rightcard.setJjmemcardno("");
		rightcard.setMembcdsour("");
		rightcard.setMembcdstat("");
		rightcard.setMembcdtype("");
		rightcard.setMembid("");
		rightcard.setMemcardno("");
		rightcard.setStartdate("");
		rightcard.setListofhistory(new QueryRightCardInfoResponse.Body.Rightcard.Listofhistory());
		body.getRightcard().add(rightcard);
		queryRightCardInfoResponse.setBody(body);
		return queryRightCardInfoResponse;
	}

}
