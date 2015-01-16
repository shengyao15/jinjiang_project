package com.jje.vbp.sns;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.dto.vbp.sns.ThirdpartyBindDto;
import com.jje.dto.vbp.sns.ThirdpartyBindResult;
import com.jje.dto.vbp.sns.ThirdpartyBindType;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.remote.crm.datagram.response.TierUpdateRes;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.vbp.sns.service.ThirdpartyBindService;

public class ThirdpartyBindResourceTest extends DataPrepareFramework{
	
	@Mock
	private MemberHandler spyMemberHandler;
	
	@Autowired
	private MemberHandler memberHandler;
	
	@Autowired
	private ThirdpartyBindResource thirdpartyBindResource;
	
	@Autowired
	private ThirdpartyBindService thirdpartyBindService;
	
	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(thirdpartyBindService), "memberHandler", spyMemberHandler);
		Mockito.when(spyMemberHandler.updateTier(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(mockReponse());
	}

	@After
	public void clearMocks() throws Exception {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(thirdpartyBindService), "memberHandler", memberHandler);
	}
	
	private TierUpdateRes mockReponse() {
		TierUpdateRes res = new TierUpdateRes();
		TierUpdateRes.ResponseBody body = new TierUpdateRes.ResponseBody();
		body.setRecode("00001");
		res.setBody(body);
		return res;
	}

	@Test
	public void bindTest(){
		ThirdpartyBindDto thirdpartyBindDto = new ThirdpartyBindDto();
		thirdpartyBindDto.setMcMemberCode("10086");
		thirdpartyBindDto.setMemberId("J10000");
		thirdpartyBindDto.setThirdpartyLevel("4");
		thirdpartyBindDto.setThirdpartySign("1124@QQ.COM");
		thirdpartyBindDto.setThirdpartyType(ThirdpartyBindType.TENPAY.name());
		thirdpartyBindDto.setVipFlag("1");
		Response response = thirdpartyBindResource.bind(thirdpartyBindDto);
		ThirdpartyBindResult result = (ThirdpartyBindResult) response.getEntity();
		Assert.assertEquals(true, result.isSuccess());
		response = thirdpartyBindResource.queryThirdpartyBind(thirdpartyBindDto);
		ThirdpartyBindDto dto = (ThirdpartyBindDto)response.getEntity();
		Assert.assertEquals(thirdpartyBindDto.getMcMemberCode(), dto.getMcMemberCode());
	}
	
	@Test
	public void existsMcMemberCode(){
		ThirdpartyBindDto thirdpartyBindDto = new ThirdpartyBindDto();
		thirdpartyBindDto.setMcMemberCode("10087");
		thirdpartyBindDto.setMemberId("J20000");
		thirdpartyBindDto.setThirdpartyLevel("4");
		thirdpartyBindDto.setThirdpartySign("1124@QQ.COM");
		thirdpartyBindDto.setThirdpartyType(ThirdpartyBindType.TENPAY.name());
		thirdpartyBindDto.setVipFlag("1");
		Response response = thirdpartyBindResource.bind(thirdpartyBindDto);
		ThirdpartyBindResult result = (ThirdpartyBindResult) response.getEntity();
		Assert.assertEquals(false, result.isSuccess());
	}
	
	@Test
	public void existsSignTest(){
		ThirdpartyBindDto thirdpartyBindDto = new ThirdpartyBindDto();
		thirdpartyBindDto.setMcMemberCode("10088");
		thirdpartyBindDto.setMemberId("J30000");
		thirdpartyBindDto.setThirdpartyLevel("4");
		thirdpartyBindDto.setThirdpartySign("1125@QQ.COM");
		thirdpartyBindDto.setThirdpartyType(ThirdpartyBindType.TENPAY.name());
		thirdpartyBindDto.setVipFlag("1");
		Response response = thirdpartyBindResource.bind(thirdpartyBindDto);
		ThirdpartyBindResult result = (ThirdpartyBindResult) response.getEntity();
		Assert.assertEquals(false, result.isSuccess());
	}
}
