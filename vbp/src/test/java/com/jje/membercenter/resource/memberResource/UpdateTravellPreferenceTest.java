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
import com.jje.membercenter.xsd.UpdateTravellPreferenceRequest;
import com.jje.membercenter.xsd.UpdateTravellPreferenceResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class UpdateTravellPreferenceTest extends DataPrepareFramework {

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
	public void should_be_success_when_update_travel_preference() throws Exception {
		Mockito.when(crmMembershipProxy.updateTravellPreference(Mockito.any(UpdateTravellPreferenceRequest.class)))
				.thenReturn(getUpdateTravellPreferenceResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);

		InvokeResult<String> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/updateTravellPreference", new TravellPreferenceDto(), String.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	private UpdateTravellPreferenceResponse getUpdateTravellPreferenceResponse() {
		UpdateTravellPreferenceResponse updateTravellPreferenceResponse = new UpdateTravellPreferenceResponse();
		UpdateTravellPreferenceResponse.Body body = new UpdateTravellPreferenceResponse.Body();
		updateTravellPreferenceResponse.setBody(body);
		return updateTravellPreferenceResponse;
	}

}
