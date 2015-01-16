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
import com.jje.dto.ResultMemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMUpdateRightCardProxy;
import com.jje.membercenter.domain.CRMUpdateRightCardRepository;
import com.jje.membercenter.xsd.QueryRightCardInfoRequest;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class QueryVIPCardInfoTest extends DataPrepareFramework {

	@Mock
	private CRMUpdateRightCardProxy crmUpdateRightCardProxy;
	

    @Autowired
    private CRMUpdateRightCardRepository crmUpdateRightCardRepository;
    
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("rawtypes")
	@Test   
	public void should_be_success_when_queryVIPCardInfo() throws Exception {
		Mockito.when(crmUpdateRightCardProxy.queryVIPCardInfo(Mockito.any(QueryRightCardInfoRequest.class)))
				.thenReturn(mockQueryRightCardInfoResponse());
		resourceInvokeHandler.setField(crmUpdateRightCardRepository, "crmUpdateRightCardProxy",crmUpdateRightCardProxy);

		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/queryVIPCardInfo", "memberId", ResultMemberDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		resourceInvokeHandler.setField(crmUpdateRightCardRepository, "crmUpdateRightCardProxy",CRMUpdateRightCardProxy.class);
	}
	
	
	private QueryRightCardInfoResponse mockQueryRightCardInfoResponse() {
		QueryRightCardInfoResponse queryRightCardInfoResponse = new QueryRightCardInfoResponse();
		QueryRightCardInfoResponse.Body body = new QueryRightCardInfoResponse.Body();
		queryRightCardInfoResponse.setBody(body);
		return queryRightCardInfoResponse;
	}
}
