package com.jje.membercenter.resource.memberResource;

import java.util.ArrayList;
import java.util.List;

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
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberAddressDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.UpdateMemberAddressRequest;
import com.jje.membercenter.xsd.UpdateMemberAddressResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class SaveMemberAddressTest extends DataPrepareFramework {

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
	public void should_be_success_when_Update_Member_Address() throws Exception {
		Mockito.when(crmMembershipProxy.updateMemberAddress(Mockito.any(UpdateMemberAddressRequest.class)))
				.thenReturn(getSaveMemberAddressResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);

		InvokeResult<String> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/profile/saveMemberAddress", getMemberAddressDto(), String.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}
	
	public ResultMemberDto<MemberAddressDto>  getMemberAddressDto(){
		ResultMemberDto<MemberAddressDto> addressDtos = new ResultMemberDto<MemberAddressDto>();
		addressDtos.setPagination(new Pagination());
		addressDtos.setQueryId("");
		List<MemberAddressDto>  memberList = new ArrayList<MemberAddressDto>();
		MemberAddressDto  memberAddressDto = new MemberAddressDto();
		memberAddressDto.setMemberId("112");
		memberList.add(memberAddressDto);
		addressDtos.setResults(memberList);
		return addressDtos; 
	}
	
	
	
	private UpdateMemberAddressResponse getSaveMemberAddressResponse() {
		UpdateMemberAddressResponse updateMemberAddressResponse = new UpdateMemberAddressResponse();
		UpdateMemberAddressResponse.Body body = new UpdateMemberAddressResponse.Body();
		body.setMembid("1");
		body.setRecode("2");
		body.setRemsg("3");
		updateMemberAddressResponse.setBody(body);
		return updateMemberAddressResponse;
	}

}
