package com.jje.membercenter.resource.memberMoveResource;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberMoveResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.service.MemberService;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateResponse;
import com.jje.vbp.handler.CbpHandler;

public class ActiveMemberTest extends DataPrepareFramework{
	
	@Mock
	private CRMMembershipProxy spyCrmMembershipProxy;
	
	@Mock
	private CbpHandler spyCbpHandler;
	
	@Autowired
	private CRMMembershipProxy crmMembershipProxy;
	
    @Autowired
    private CRMMembershipRepository crmMembershipRepository;
    
	@Autowired
	private CbpHandler cbpHandler;
	
	@Autowired
    private MemberService memberService;
	
	@Autowired
	private MemberMoveResource memberMoveResource;
	
	private static final String ACTIVE_SUCCESS = "00001";
	private static final String ACTIVE_FAILURE = "00002";
		
	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(spyCrmMembershipProxy.updateMemberBasicInfo(
				Mockito.any(MemberBasicInfoUpdateRequest.class))).
				thenReturn(mockMemberBasicInfoUpdateResponse());
	    Mockito.when(spyCbpHandler.activingIssueCoupon(Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
	    		.thenReturn(new CouponSysIssueResult());
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository),
				"crmMembershipProxy", spyCrmMembershipProxy);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService),
				"cbpHandler", spyCbpHandler);
	}
	
	@After
	public void clearMocks() throws Exception{
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository),
				"crmMembershipProxy", crmMembershipProxy);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService),
				"cbpHandler", cbpHandler);
	}

	@Test
	public void should_be_success_when_phone_and_email_not_exist() throws Exception {
		MemberBasicInfoDto basicInfoDto = new MemberBasicInfoDto();
		basicInfoDto.setRegistChannel(RegistChannel.Website);
		basicInfoDto.setBingFlg("Mobile Activiated");
		basicInfoDto.setEmail("3@test.com");
		basicInfoDto.setMemberId("10000");
		basicInfoDto.setCdtype("ID");
		basicInfoDto.setCdno("12345");
		Response repsponse = memberMoveResource.active(basicInfoDto);
		Assert.assertEquals(Status.OK.getStatusCode(), repsponse.getStatus());
		Assert.assertEquals(ACTIVE_SUCCESS, repsponse.getEntity().toString());
	}
	
	@Test
	public void should_be_failure_when_phone_exist_in_member_mem_info() throws Exception {
		MemberBasicInfoDto basicInfoDto = new MemberBasicInfoDto();
		basicInfoDto.setRegistChannel(RegistChannel.Website);
		basicInfoDto.setBingFlg("Mobile Activiated");
		basicInfoDto.setCell("999");
		basicInfoDto.setEmail("233@test.com");
		basicInfoDto.setMemberId("10000");
		basicInfoDto.setCdtype("ID");
		basicInfoDto.setCdno("12345");
		Response repsponse = memberMoveResource.active(basicInfoDto);
		Assert.assertEquals(Status.OK.getStatusCode(), repsponse.getStatus());
		Assert.assertEquals(ACTIVE_FAILURE, repsponse.getEntity().toString());
	}
	
	@Test
	public void should_be_failure_when_email_exist_in_member_mem_info() throws Exception {
		MemberBasicInfoDto basicInfoDto = new MemberBasicInfoDto();
		basicInfoDto.setRegistChannel(RegistChannel.Website);
		basicInfoDto.setBingFlg("Mobile Activiated");
		basicInfoDto.setCell("2");
		basicInfoDto.setEmail("2@test.com");
		basicInfoDto.setMemberId("10000");
		basicInfoDto.setCdtype("ID");
		basicInfoDto.setCdno("12345");
		Response repsponse = memberMoveResource.active(basicInfoDto);
		Assert.assertEquals(Status.OK.getStatusCode(), repsponse.getStatus());
		Assert.assertEquals(ACTIVE_FAILURE, repsponse.getEntity().toString());
	}
	
	private MemberBasicInfoUpdateResponse mockMemberBasicInfoUpdateResponse() {
		MemberBasicInfoUpdateResponse response = new MemberBasicInfoUpdateResponse();
		MemberBasicInfoUpdateResponse.Body body = new MemberBasicInfoUpdateResponse.Body();
		response.setBody(body);
		response.getBody().setRecode(ACTIVE_SUCCESS);
		return response;
	}
	
}
