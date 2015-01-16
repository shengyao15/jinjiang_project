package com.jje.vbp.thirdparty;

import junit.framework.Assert;

import org.junit.After;
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
import com.jje.dto.member.thirdParty.ThirdPartyLoginDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.SSOResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.ThirdPartyLoginRequest;
import com.jje.membercenter.xsd.ThirdPartyLoginResponse;

public class ThirdPartyLoginResourceTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	
	@Autowired
	private CRMMembershipRepository crmRepository;

	@Autowired
	CRMMembershipProxy crmMembershipProxy;

	@Mock
	private CRMMembershipProxy spyCrmMembershipProxy;
	

	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(spyCrmMembershipProxy.queryByCrsId(Mockito.any(ThirdPartyLoginRequest.class))).thenReturn(mockSuccessDto());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmRepository), "crmMembershipProxy", spyCrmMembershipProxy);
	}

	@After
	public void clearMocks() throws Exception {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmRepository), "crmMembershipProxy", crmMembershipProxy);
	}
                                                                
	@Test
	public void should_be_query_success() {
		InvokeResult<ThirdPartyLoginDto> result = resourceInvokeHandler.doGet("thirdPartyLoginResource", ThirdPartyLoginResource.class, "/member/thirdPartyLogin/query/123/12345678911",ThirdPartyLoginDto.class);
		Assert.assertEquals(ThirdPartyLoginDto.ThirdPartyLoginResult.SUCCESS, result.getOutput().getResponseMessage());
	}

	private ThirdPartyLoginResponse mockSuccessDto() {
		ThirdPartyLoginResponse response=	 new ThirdPartyLoginResponse();
		response.setBody(new ThirdPartyLoginResponse.Body("1-11DR-1",null,"00001","SUCCESS"));
	    return response;
	}
	
	
	@Test
	public void should_be_query_fail() throws Exception {
		Mockito.when(spyCrmMembershipProxy.queryByCrsId(Mockito.any(ThirdPartyLoginRequest.class))).thenReturn(mockFailDto());
		InvokeResult<ThirdPartyLoginDto> result = resourceInvokeHandler.doGet("thirdPartyLoginResource", ThirdPartyLoginResource.class, "/member/thirdPartyLogin/query/123/12345678911",ThirdPartyLoginDto.class);
		Assert.assertEquals(ThirdPartyLoginDto.ThirdPartyLoginResult.FAIL, result.getOutput().getResponseMessage());
	}
	
	private ThirdPartyLoginResponse mockFailDto() {
		ThirdPartyLoginResponse response=	 new ThirdPartyLoginResponse();
		response.setBody(new ThirdPartyLoginResponse.Body("1-11DR-1",null,"00002","FAIL"));
	    return response;
	}
	
	
	@Test
	public void should_be_success_remove_tickit() throws Exception {
		InvokeResult<String> result = resourceInvokeHandler.doGet("SSOResource", SSOResource.class, "/sso/removeTicket/asdad",String.class);
		Assert.assertEquals(result.getOutput(),"OK");
	}
	
}
