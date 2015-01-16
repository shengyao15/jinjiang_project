package com.jje.vbp.member;

import org.junit.*;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.vbp.member.MemberActivateDto;
import com.jje.dto.vbp.member.MemberActivateResultDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.vbp.member.service.MemberManagerService;

public class CompleteWebMemberTest extends DataPrepareFramework{

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;
	
	@Autowired
	private MemberManagerService memberManagerService;
	
	@Mock
	private CRMMembershipRepository crmMembershipRepository;
	
	
	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Ignore
	public void should_be_success_when_exsit_not_activity() throws Exception{
		Mockito.when(crmMembershipRepository.activeMember(Matchers.any(MemberBasicInfoDto.class))).thenReturn("00001");
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberManagerService), "crmMembershipRepository", crmMembershipRepository);
		InvokeResult<MemberActivateResultDto> result=resourceInvokeHandler.doPost("memberManagerResource", MemberManagerResource.class, "/memberManager/completeWebMember",getMemberActivateDto(), MemberActivateResultDto.class);
		MemberActivateResultDto  resultDto= result.getOutput();
		Assert.assertEquals(resultDto.getStatusMessage(), MemberActivateResultDto.StatusMessage.OK);
	}

	@Test
	public void should_be_fail_when_activated() throws Exception{
		Mockito.when(crmMembershipRepository.activeMember(Matchers.any(MemberBasicInfoDto.class))).thenReturn("00001");
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberManagerService), "crmMembershipRepository", crmMembershipRepository);
		InvokeResult<MemberActivateResultDto> result=resourceInvokeHandler.doPost("memberManagerResource", MemberManagerResource.class, "/memberManager/completeWebMember",getMemberActivateDto2(), MemberActivateResultDto.class);
		MemberActivateResultDto  resultDto= result.getOutput();
		Assert.assertEquals(resultDto.getStatusMessage(), MemberActivateResultDto.StatusMessage.MEMBER_ACTIVATED);
	}
	

	
	private MemberActivateDto getMemberActivateDto() {
		MemberActivateDto memberActivateDto=new MemberActivateDto();
		memberActivateDto.setFullName("memtest");
		memberActivateDto.setEmail("whatfuck@jinjiang.com");
		memberActivateDto.setPhone("19112855454");
		memberActivateDto.setIdentityNo("55555");
		memberActivateDto.setIdentityType("Others");
		memberActivateDto.setMcMemberCode("112");
		memberActivateDto.setRegistChannel(RegistChannel.Website);
		memberActivateDto.setTitle("Mr");
		return memberActivateDto;
	}
	
	
	private MemberActivateDto getMemberActivateDto2() {
		MemberActivateDto memberActivateDto=new MemberActivateDto();
		memberActivateDto.setFullName("陈勇");
		memberActivateDto.setEmail("whatfuck@jinjiang.com");
		memberActivateDto.setPhone("19112855454");
		memberActivateDto.setIdentityNo("11111");
		memberActivateDto.setIdentityType("Others");
		memberActivateDto.setMcMemberCode("112");
		memberActivateDto.setRegistChannel(RegistChannel.Website);
		memberActivateDto.setTitle("Mr");
		return memberActivateDto;
	}
	
	
	
	
	@After
	public void after() {
		resourceInvokeHandler.resetField(memberManagerService, "crmMembershipRepository", CRMMembershipRepository.class);
	}
	
	
	
	

}
