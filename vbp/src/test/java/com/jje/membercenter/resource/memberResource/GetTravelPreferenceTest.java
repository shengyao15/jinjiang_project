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
import com.jje.dto.membercenter.TravellPreferenceDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.QueryTravellPreferenceRequest;
import com.jje.membercenter.xsd.QueryTravellPreferenceResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class GetTravelPreferenceTest extends DataPrepareFramework {

	@Mock
	private CRMMembershipProxy spyCrmMembershipProxy;
	
    @Autowired
    private CRMMembershipRepository crmMembershipRepository;
    
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test   
	public void should_be_success_when_get_travel_preference() throws Exception {
		Mockito.when(spyCrmMembershipProxy.getTravellPreference(Mockito.any(QueryTravellPreferenceRequest.class)))
				.thenReturn(getQueryTravellPreferenceResponse());
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy",spyCrmMembershipProxy);

		InvokeResult<TravellPreferenceDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/getTravellPreference", "memberId", TravellPreferenceDto.class);
		TravellPreferenceDto travellPreferenceDto = result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNotNull(travellPreferenceDto);
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy",CRMMembershipProxy.class);
	}

	private QueryTravellPreferenceResponse getQueryTravellPreferenceResponse() {
		QueryTravellPreferenceResponse queryTravellPreferenceResponse = new QueryTravellPreferenceResponse();
		QueryTravellPreferenceResponse.Body body = new QueryTravellPreferenceResponse.Body();
		queryTravellPreferenceResponse.setBody(body);
		return queryTravellPreferenceResponse;
	}

}