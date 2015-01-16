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
import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberPrivilegeDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryRequest;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryResponse;
import com.jje.membercenter.xsd.MemberPrivilegeRequest;
import com.jje.membercenter.xsd.MemberPrivilegeResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class FindPrivByMemberIdTest extends DataPrepareFramework {

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test   
	public void should_be_success_when_find_Priv_By_MemberId() throws Exception {
		Mockito.when(crmMembershipProxy.getPrivCardInfo(Mockito.any(MemberPrivilegeRequest.class)))
				.thenReturn(getMemberPrivilegeResponse());
		Mockito.when(crmMembershipProxy.getPrivCardHistory(Mockito.any(MemberPrivilegeHistoryRequest.class)))
		.thenReturn(getMemberPrivilegeHistoryResponse());
		
		
		resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy",crmMembershipProxy);

		InvokeResult<ResultMemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/findPriv", getQueryDto(), ResultMemberDto.class);
		ResultMemberDto<MemberPrivilegeDto> memberPrivilegeDto= result.getOutput();
		Assert.assertEquals(result.getStatus(), Status.OK);
		Assert.assertNotNull(memberPrivilegeDto);
		resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy",CRMMembershipProxy.class);
	}

	private MemberPrivilegeResponse getMemberPrivilegeResponse() {
		MemberPrivilegeResponse memberPrivilegeResponse = new MemberPrivilegeResponse();
		MemberPrivilegeResponse.Body body = new MemberPrivilegeResponse.Body();
		memberPrivilegeResponse.setBody(body);
		return memberPrivilegeResponse;
	}
	
	private MemberPrivilegeHistoryResponse getMemberPrivilegeHistoryResponse() {
		MemberPrivilegeHistoryResponse memberPrivilegeHistoryResponse = new MemberPrivilegeHistoryResponse();
		MemberPrivilegeHistoryResponse.Body body = new MemberPrivilegeHistoryResponse.Body();
		memberPrivilegeHistoryResponse.setBody(body);
		return memberPrivilegeHistoryResponse;
	}
	
	private QueryMemberDto<String> getQueryDto() {
		QueryMemberDto<String> queryDto  = new QueryMemberDto<String>();
		Pagination pagination = new Pagination();
		queryDto.setPagination(pagination);
		return queryDto;
	}
	
}
