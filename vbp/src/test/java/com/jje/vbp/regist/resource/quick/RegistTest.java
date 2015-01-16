package com.jje.vbp.regist.resource.quick;

import junit.framework.Assert;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.register.RegisterIssueDto;
import com.jje.dto.membercenter.MemType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.WebMemberDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto.QuickRegistResult;
import com.jje.dto.mms.mmsmanage.EmailMessageDto;
import com.jje.dto.mms.mmsmanage.MessageRespDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.handler.MbpHandler;
import com.jje.vbp.handler.proxy.CbpProxy;
import com.jje.vbp.handler.proxy.MbpProxy;
import com.jje.vbp.regist.QuickRegistResource;


public class RegistTest extends DataPrepareFramework {
	
	private String email = "0000011110@163.com";
	private String phone = "0000011110";
	private String identify_char = "R_";
	
	@Mock
	private CbpProxy mockCbpProxy;
	
	@Mock
	private MbpProxy mockMbpProxy;
	
	@Autowired
	private CbpHandler cbpHandler;
	
	@Autowired
	private MbpHandler mbpHandler;
	
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;
	
	@Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
	
	//FIXME
	@Test
	public void should_quick_regist_whith_only_phone(){
		this.mockBehavior();
	    
	    WebMemberDto registDto = this.mockWebMemberDtoWithPhone(phone);
		InvokeResult<WebMemberRegisterReturnDto> outPut = resourceInvokeHandler.doPost("quickRegistResource", QuickRegistResource.class, "/quickRegist/regist", registDto, WebMemberRegisterReturnDto.class);
		WebMemberRegisterReturnDto result = outPut.getOutput();
		Assert.assertEquals(QuickRegistResult.SUCCESS.getCode(), result.getStatus());
		
		this.reverseMock();
	}
	
	//FIXME
	@Test
	public void should_quick_regist_whith_only_email(){
		this.mockBehavior();
		
		WebMemberDto registDto = this.mockWebMemberDtoWithEmail(email);
		InvokeResult<WebMemberRegisterReturnDto> outPut = resourceInvokeHandler.doPost("quickRegistResource", QuickRegistResource.class, "/quickRegist/regist", registDto, WebMemberRegisterReturnDto.class);
		WebMemberRegisterReturnDto result = outPut.getOutput();
		Assert.assertEquals(QuickRegistResult.SUCCESS.getCode(), result.getStatus());
		
		this.reverseMock();
	}
	
	@Test
	public void should_quick_regist_whith_email_and_phone(){
		this.mockBehavior();
		
		WebMemberDto registDto = this.mockWebMemberDtoWithEmailAndPhone(email,phone);
		InvokeResult<WebMemberRegisterReturnDto> outPut = resourceInvokeHandler.doPost("quickRegistResource", QuickRegistResource.class, "/quickRegist/regist", registDto, WebMemberRegisterReturnDto.class);
		WebMemberRegisterReturnDto result = outPut.getOutput();
		Assert.assertEquals(QuickRegistResult.SUCCESS.getCode(), result.getStatus());
		
		this.reverseMock();
	}
	
	@Test
	public void should_quick_regist_whith_exist_email(){
		this.mockBehavior();
		
		WebMemberDto registDto = this.mockWebMemberDtoWithEmail(email);
		InvokeResult<WebMemberRegisterReturnDto> outPut = resourceInvokeHandler.doPost("quickRegistResource", QuickRegistResource.class, "/quickRegist/regist", registDto, WebMemberRegisterReturnDto.class);
		outPut = resourceInvokeHandler.doPost("quickRegistResource", QuickRegistResource.class, "/quickRegist/regist", registDto, WebMemberRegisterReturnDto.class);
		WebMemberRegisterReturnDto result = outPut.getOutput();
		Assert.assertEquals(QuickRegistResult.EMAIL_USED.getCode(), result.getStatus());
		
		this.reverseMock();
	}
	
	@Test
	public void should_quick_regist_whith_exist_phone(){
		this.mockBehavior();
		
		WebMemberDto registDto = this.mockWebMemberDtoWithPhone(phone);
		InvokeResult<WebMemberRegisterReturnDto> outPut = resourceInvokeHandler.doPost("quickRegistResource", QuickRegistResource.class, "/quickRegist/regist", registDto, WebMemberRegisterReturnDto.class);
		outPut = resourceInvokeHandler.doPost("quickRegistResource", QuickRegistResource.class, "/quickRegist/regist", registDto, WebMemberRegisterReturnDto.class);
		WebMemberRegisterReturnDto result = outPut.getOutput();
		Assert.assertEquals(QuickRegistResult.PHONE_USED.getCode(), result.getStatus());
		
		this.reverseMock();
	}
	
	private void mockBehavior(){
		Mockito.when(mockCbpProxy.issueCoupon(Matchers.any(RegisterIssueDto.class))).thenReturn(new CouponSysIssueResult());
		Mockito.when(mockMbpProxy.sendEmailMessage(Matchers.any(EmailMessageDto.class))).thenReturn(new MessageRespDto());
	    ReflectionTestUtils.setField(cbpHandler, "cbpProxy", mockCbpProxy);
	    ReflectionTestUtils.setField(mbpHandler, "mbpProxy", mockMbpProxy);
	}
	
	private void reverseMock(){
		resourceInvokeHandler.resetField(cbpHandler, "cbpProxy", CbpProxy.class);
		resourceInvokeHandler.resetField(mbpHandler, "mbpProxy", MbpProxy.class);
	}
	
	
	private WebMemberDto mockWebMemberDtoWithEmail(String email){
		WebMemberDto dto =  this.mockWebMemberDto();
		dto.setEmail(email);
		return dto;
	}
	
	private WebMemberDto mockWebMemberDtoWithPhone(String phone){
		WebMemberDto dto =  this.mockWebMemberDto();
		dto.setPhone(phone);
		return dto;
	}
	
	private WebMemberDto mockWebMemberDtoWithEmailAndPhone(String email,String phone){
		WebMemberDto dto =  this.mockWebMemberDto();
		dto.setEmail(identify_char+email);
		dto.setPhone(identify_char+phone);
		return dto;
	}
	
	private WebMemberDto mockWebMemberDto(){
		WebMemberDto dto =  new WebMemberDto();
		dto.setRegistChannel(RegistChannel.Website);
		dto.setMemType(MemType.QUICK_REGIST.name());
		dto.setPwd(DigestUtils.md5Hex("123456"));
		return dto;
	}
}
