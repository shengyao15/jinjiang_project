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
import com.jje.dto.membercenter.cardbind.CardBindStatusDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.remote.handler.MemberHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class PartnerCardBindTest extends DataPrepareFramework {

	@Autowired
	private MemberHandler memberHandler;
	
	@Mock
	private MemberRepository memberRepository;
    
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test   
	public void should_be_success_when_partnerCardBind() throws Exception {
		Mockito.when(memberRepository.partnerCardBind(Mockito.any(PartnerCardBindDto.class)))
				.thenReturn(false);
		resourceInvokeHandler.setField(memberHandler, "memberRepository",memberRepository);

		InvokeResult<CardBindStatusDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/partnerCardBind", mockPartnerCardBindDto(), CardBindStatusDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
		resourceInvokeHandler.resetField(memberHandler, "memberRepository",MemberRepository.class);
	}
	
	private PartnerCardBindDto mockPartnerCardBindDto() {
		PartnerCardBindDto dto = new PartnerCardBindDto();
		return dto;
	}
}
