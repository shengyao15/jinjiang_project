package com.jje.membercenter.resource.memberResource;

import java.math.BigInteger;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
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
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.AddressDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.InvoiceDto;
import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberRegisterRequest;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.vbp.handler.CbpHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class AddVIPMembershipTest extends DataPrepareFramework {

	@Mock
	private CRMMembershipProxy spyCrmMembershipProxy;
	
	@Autowired
	private CRMMembershipProxy crmMembershipProxy;
	
    @Autowired
    private CRMMembershipRepository crmMembershipRepository;
    
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Mock
	private CbpHandler spyCbpHandler;

	@Autowired
	private CbpHandler cbpHandler;
	
	
	@Autowired
	private MemberResource memberResource;
	
	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);		
		//Mockito.doNothing().when(cbpHandler).lockCoupon(Mockito.anyString());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberResource), "cbpHandler",
				spyCbpHandler);
	}

	@After
	public void clearMocks() throws Exception {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberResource), "cbpHandler",
				cbpHandler);
	}
	
	@Test   
	public void should_be_success_when_add_vipmember_ship() throws Exception {
		Mockito.when(spyCrmMembershipProxy.addVIPMembership(Mockito.any(MemberRegisterRequest.class)))
				.thenReturn(getMemberRegisterResponse());		
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				spyCrmMembershipProxy);		
		MemberRegisterDto memberRegisterDto=getMemberRegisterDto();
		System.out.println(JaxbUtils.convertToXmlString(memberRegisterDto));
		InvokeResult<CRMResponseDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/addVIPMembership",memberRegisterDto , CRMResponseDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy",
				crmMembershipProxy);		
	}

	private MemberRegisterDto getMemberRegisterDto(){
		MemberRegisterDto  memberRegisterDto = new MemberRegisterDto();
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		memberInfoDto.setEmail("t54627@333.com");
		memberInfoDto.setMobile("1123453323455");
		memberInfoDto.setSurname("test33");
		memberInfoDto.setMemberType("JJ Card");
		AddressDto primaryAddress = new AddressDto();
		memberInfoDto.setPrimaryAddress(primaryAddress);
		memberInfoDto.setRegisterSource(RegistChannel.Website_partner);
		memberInfoDto.setAirLineCompany(MemberAirLineCompany.ACDH);
		memberInfoDto.setAirLineCardNo("15353728289202");
		memberInfoDto.setMemberScoreType(MemberScoreType.MILEAGE);
		InvoiceDto invoice = new InvoiceDto();
		memberRegisterDto.setInvoice(invoice);
		memberRegisterDto.setMemberInfoDto(memberInfoDto);
		memberInfoDto.setPasssword("E10ADC3949BA59ABBE56E057F20F883E");
		return memberRegisterDto;
	}
	private MemberRegisterResponse getMemberRegisterResponse() {
		MemberRegisterResponse memberRegisterResponse = new MemberRegisterResponse();
		MemberRegisterResponse.Body body = new MemberRegisterResponse.Body();
		body.setRecode("00001");
		memberRegisterResponse.setBody(body);
		return memberRegisterResponse;
	}

}
