package com.jje.membercenter.resource.memberMoveResource;

import java.util.ArrayList;
import java.util.List;

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
import com.jje.dto.membercenter.MemberAddressDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberMoveResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.UpdateMemberAddressRequest;
import com.jje.membercenter.xsd.UpdateMemberAddressResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class FirstMemberMoveAddressTest extends DataPrepareFramework {

	@Mock
	CRMMembershipProxy spyCRMMembershipProxy;
	
	@Autowired
	CRMMembershipProxy crmMembershipProxy;

    @Autowired
    private CRMMembershipRepository crmMembershipRepository;


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test
	public void should_be_success_when_set_first_member_address() throws Exception {
		Mockito.when(spyCRMMembershipProxy.updateMemberAddress(Mockito.any(UpdateMemberAddressRequest.class)))
				.thenReturn(getUpdateMemberAddressResponse());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				spyCRMMembershipProxy);

		InvokeResult<String> result = resourceInvokeHandler.doPost("memberMoveResource", MemberMoveResource.class,
				"/membermove/profile/firstMemberAddress", getAddressDtos(), String.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		Mockito.verify(spyCRMMembershipProxy).updateMemberAddress(Mockito.any(UpdateMemberAddressRequest.class));

		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);
	}

	
	
	private ResultMemberDto<MemberAddressDto> getAddressDtos(){
		ResultMemberDto<MemberAddressDto> addressDtos = new ResultMemberDto<MemberAddressDto>();
		List<MemberAddressDto> MemberAddressDtoList = new ArrayList<MemberAddressDto>();
		MemberAddressDto  memberAddressDto = new MemberAddressDto();
		memberAddressDto.setMemberId("11");
		MemberAddressDtoList.add(memberAddressDto);
		addressDtos.setResults(MemberAddressDtoList);
		return addressDtos;
	}
	private UpdateMemberAddressResponse getUpdateMemberAddressResponse() {
		UpdateMemberAddressResponse updateMemberAddressResponse = new UpdateMemberAddressResponse();
		UpdateMemberAddressResponse.Body body = new UpdateMemberAddressResponse.Body();
		body.setMembid("");
		body.setRecode("");
		body.setRemsg("");
		updateMemberAddressResponse.setBody(body);
		return updateMemberAddressResponse;
	}

}
