package com.jje.membercenter.resource.webMemberResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.CouponSysIssueResult.ResponseMessage;
import com.jje.dto.membercenter.MemType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.WebMemberDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.WebMemberResource;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.vbp.handler.CbpHandler;

public class QuickRegisterTest extends DataPrepareFramework{
	
	@Mock
	private CbpHandler spyCBPHandler;
	
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;
    
    @Autowired
    private WebMemberResource webMemberResource;
    
    @Autowired
	private WebMemberRepository webMemberRepository;
    
    @Before
	public void initMocks() throws Exception{
		MockitoAnnotations.initMocks(this);
		CouponSysIssueResult sysIssueResult = new CouponSysIssueResult(ResponseMessage.SUCCESS);
    	Mockito.when(spyCBPHandler.quickRegisterIssue(Mockito.any(RegistChannel.class),Mockito.anyString(), Mockito.anyString())).thenReturn(sysIssueResult);
    	resourceInvokeHandler.setField(webMemberResource, "cbpHandler",spyCBPHandler);
	}
    
    @After
    public void clearMocks() {
    	resourceInvokeHandler.resetField(webMemberResource, "cbpHandler",CbpHandler.class);
    }
    
    @Test
    public void should_be_register_success_when_given_email(){
    	WebMemberDto webMemberDto = mockWebMemberDto("test1@jinjiang.com",null);
    	InvokeResult<WebMemberRegisterReturnDto> result = resourceInvokeHandler.doPost("webMemberResource", WebMemberResource.class,
				"/webMember/regist", webMemberDto, WebMemberRegisterReturnDto.class);
    	Assert.assertEquals(Status.OK, result.getStatus());
		Assert.assertNotNull(result.getOutput().getMcMemberCode());
		assertLastUpdateTime(result.getOutput().getMcMemberCode());
    }

	private void assertLastUpdateTime(String mcCode) {
		WebMember webMember = webMemberRepository.getMemberByMcMemberCode(mcCode);
		Assert.assertNotNull(webMember.getLastUpdateTime());
	}
    
    @Test
    public void should_be_register_success_when_given_phone() {
    	WebMemberDto webMemberDto = mockWebMemberDto(null,"123456789");
    	InvokeResult<WebMemberRegisterReturnDto> result = resourceInvokeHandler.doPost("webMemberResource", WebMemberResource.class,
				"/webMember/regist", webMemberDto, WebMemberRegisterReturnDto.class);
    	Assert.assertEquals(Status.OK, result.getStatus());
		Assert.assertNotNull(result.getOutput().getMcMemberCode());
		assertLastUpdateTime(result.getOutput().getMcMemberCode());
    }

    @Test
	public void should_be_register_success_when_given_email_and_phone() throws Exception{
		WebMemberDto webMemberDto = mockWebMemberDto("345ww@jinjiang.com","123456");
		InvokeResult<WebMemberRegisterReturnDto> result = resourceInvokeHandler.doPost("webMemberResource", WebMemberResource.class,
				"/webMember/regist", webMemberDto, WebMemberRegisterReturnDto.class);
		Assert.assertEquals(Status.OK, result.getStatus());
		Assert.assertNotNull(result.getOutput().getMcMemberCode());
		assertLastUpdateTime(result.getOutput().getMcMemberCode());
	}
    
    @Test
    public void should_be_register_failure_when_given_email_exist() throws Exception{
    	WebMemberDto webMemberDto = mockWebMemberDto("same@jinjiang.com",null);
    	InvokeResult<WebMemberRegisterReturnDto> result = resourceInvokeHandler.doPost("webMemberResource", WebMemberResource.class,
    			"/webMember/regist", webMemberDto, WebMemberRegisterReturnDto.class);
    	Assert.assertEquals(Status.OK, result.getStatus());
    	Assert.assertEquals("-1",result.getOutput().getStatus());
    	Assert.assertEquals("该邮箱已被使用",result.getOutput().getMessage());
    }
    
    @Test
    public void should_be_register_failure_when_given_phone_exist() throws Exception{
    	WebMemberDto webMemberDto = mockWebMemberDto(null,"111111");
    	InvokeResult<WebMemberRegisterReturnDto> result = resourceInvokeHandler.doPost("webMemberResource", WebMemberResource.class,
    			"/webMember/regist", webMemberDto, WebMemberRegisterReturnDto.class);
    	Assert.assertEquals(Status.OK, result.getStatus());
    	Assert.assertEquals("-2",result.getOutput().getStatus());
    	Assert.assertEquals("该手机号码已被使用",result.getOutput().getMessage());
    }
	
	private WebMemberDto  mockWebMemberDto(String email,String phone){
		WebMemberDto  dto=new WebMemberDto();
		dto.setActivityCode("code");
		dto.setEmail(email);
		dto.setPhone(phone);
		dto.setMemType(MemType.QUICK_REGIST.name());
		dto.setRegistChannel(RegistChannel.Website);
		dto.setPwd("123456");
		dto.setIpAddress("192.168.1.3");
		return dto;
	}
}
