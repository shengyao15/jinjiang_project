package com.jje.vbp.taobao.taobaoResource;

import java.util.Random;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
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
import com.jje.dto.member.taobao.TaobaoErrorMsg;
import com.jje.dto.member.taobao.TaobaoNotifyDto;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.Member;
import com.jje.vbp.taobao.TaobaoResource;
import com.jje.vbp.taobao.domain.TaobaoRepository;
import com.jje.vbp.taobao.proxy.TaobaoProxy;

public class TaobaoRegistTest extends DataPrepareFramework {
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;
	@Autowired
	private TaobaoProxy taobaoProxy;
	@Mock
	private TaobaoProxy spyTaobaoProxy;
	@Autowired
	private TaobaoRepository taobaoRepository;
	
	
	@Test
	public void testUpgrade() {
		Member member =  new Member();
		member.setCardNo("7700158064");
		member.setMemberID("1-89YAR7");
		member.setMcMemberCode("30439");
		member.setEmail("f2k3f3veev4@234323q.com");
		member.setCellPhone("25343000");
		MemberRegisterDto dto =  new MemberRegisterDto();
		MemberInfoDto infoDto = new MemberInfoDto();
		infoDto.setTaobaoLevel("F2");
		dto.setMemberInfoDto(infoDto);
		taobaoRepository.autoUpgrade(member, dto);
	}

	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(
				AopTargetUtils.getTarget(taobaoRepository), "taobaoProxy",
				spyTaobaoProxy);
		Mockito.when(spyTaobaoProxy.notify(Mockito.any(TaobaoNotifyDto.class)))
				.thenReturn(Boolean.TRUE);
	}

	@After
	public void clearMocks() throws Exception {
		ReflectionTestUtils.setField(
				AopTargetUtils.getTarget(taobaoRepository), "taobaoProxy",
				taobaoProxy);
	}
	
	@Test
	public void should_success_when_regist() {
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler
				.doPost("taobaoResource", TaobaoResource.class,
						"/taobao/regist", buildMemberRegisterDto("success"),
						String.class);
		Assert.assertEquals(result.getStatus(), Response.Status.OK);
		Assert.assertEquals(TaobaoErrorMsg.SUCCESS.toString(),
				result.getOutput());
	}

	@Test
	public void should_tbid_used_when_regist() {
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler
				.doPost("taobaoResource", TaobaoResource.class,
						"/taobao/regist", buildMemberRegisterDto("tbid_used"),
						String.class);
		Assert.assertEquals(result.getStatus(), Response.Status.OK);
		Assert.assertEquals(TaobaoErrorMsg.TBID_USED.toString(),
				result.getOutput());
	}
	
	@Test
	public void should_not_acceptable_when_regist() {
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler
				.doPost("taobaoResource", TaobaoResource.class,
						"/taobao/regist", buildMemberRegisterDto("not_acceptable"),
						String.class);
		Assert.assertEquals(result.getStatus(), Response.Status.OK);
		Assert.assertEquals(TaobaoErrorMsg.NOT_ACCEPTABLE.toString(),
				result.getOutput());
	}
	
	@Test
	public void should_crm_fail_when_regist() {
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler
				.doPost("taobaoResource", TaobaoResource.class,
						"/taobao/regist", buildMemberRegisterDto("crm_fail"),
						String.class);
		Assert.assertEquals(result.getStatus(), Response.Status.OK);
		Assert.assertEquals(TaobaoErrorMsg.CRM_FAIL.toString(),
				result.getOutput());
	}
	
	public MemberRegisterDto buildMemberRegisterDto(String type) {
		MemberRegisterDto memberRegistDto = new MemberRegisterDto();
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		
		memberInfoDto.setTaobaoLevel("F2");
		if(!StringUtils.equals(type, "not_acceptable")) {
			memberInfoDto.setTaobaoId(randomString(7, "Number"));
		}
		if(StringUtils.equals(type, "tbid_used")) {
			memberInfoDto.setTaobaoId("9001011");
		}
		memberInfoDto.setMemberType("Silver Card");
		memberInfoDto.setCertificateNo(randomString(10, "String"));
		memberInfoDto.setCertificateType("Work");
		memberInfoDto.setEmail(randomString(13, "String") + "@110.com");
		if(StringUtils.equals(type, "crm_fail")) {
			memberInfoDto.setMobile("13795398694");
		} else {
			memberInfoDto.setMobile(randomString(11, "Number"));
		}
		memberInfoDto.setRegisterSource(RegistChannel.Website);
		memberInfoDto.setSurname(randomString(6, "String"));
		memberInfoDto.setTitle("Mr.");
		memberInfoDto.setTier("11");
		memberInfoDto.setTaobaoLevel("F2");
		memberRegistDto.setMemberInfoDto(memberInfoDto);
		return memberRegistDto;
	}

	public static final String randomString(int length, String type) {
		Random randGen = null;
		char[] numbersAndLetters = null;
		char[] numbers = null;
		
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
					+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
			numbers = ("0123456789").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			if(StringUtils.equals("String", type)) {
				randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			} else {
				randBuffer[i] = numbers[randGen.nextInt(9)];
			}
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}
}
