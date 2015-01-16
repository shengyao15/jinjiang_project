package com.jje.vbp.coupon.memberCouponResource;

import java.util.UUID;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.coupon.coupon.BatchUpdateDto;
import com.jje.dto.coupon.coupon.CouponValidateResultForExchangeDto;
import com.jje.dto.coupon.rule.use.ExchangeSceneDto.ExchangeType;
import com.jje.dto.membercenter.score.ScoreReceiverDto;
import com.jje.dto.vbp.coupon.ExchangeScoreDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.vbp.coupon.MemberCouponResource;
import com.jje.vbp.coupon.service.MemberCouponService;
import com.jje.vbp.handler.proxy.CbpProxy;

public class ExchangeScoreTest extends DataPrepareFramework {
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Autowired
	private CbpProxy cbpProxy;
	@Autowired
	private ScoresHandler scoresHandler;	
	@Autowired
	private MemberCouponService memberCouponService;
	
	@Mock
	private CbpProxy spyCbpProxy;
	@Mock
	private ScoresHandler spyScoresHandler;	
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		Mockito.doNothing().when(spyCbpProxy).lockCoupon(Matchers.any(BatchUpdateDto.class));
		Mockito.doNothing().when(spyCbpProxy).useCoupon(Matchers.any(BatchUpdateDto.class));
		Mockito.doNothing().when(spyCbpProxy).unlockCoupon(Matchers.any(BatchUpdateDto.class));
		Mockito.when(spyCbpProxy.validateForExchange(Matchers.anyString(), Matchers.anyString())).thenReturn(successExchangeResult());
		Mockito.when(spyScoresHandler.scoreTrade(Matchers.any(ScoreReceiverDto.class))).thenReturn(null);
		ReflectionTestUtils.setField(memberCouponService, "cbpProxy", spyCbpProxy);
		ReflectionTestUtils.setField(memberCouponService, "scoresHandler", spyScoresHandler);
	}

	@After
	public void clearMocks() {
		ReflectionTestUtils.setField(memberCouponService, "cbpProxy", cbpProxy);
		ReflectionTestUtils.setField(memberCouponService, "scoresHandler", scoresHandler);
	}
	
	
	@Test
	public void should_success_when_exchangeScore() {
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost(
				"memberCouponResource", MemberCouponResource.class, "/coupon/exchangeScore", mockSuccessDto(), String.class);
		System.out.println(JaxbUtils.convertToXmlString( mockSuccessDto()));
		Assert.assertEquals(Response.Status.OK, result.getStatus());
		Assert.assertEquals("success", result.getOutput());
	}

	private CouponValidateResultForExchangeDto successExchangeResult() {
		CouponValidateResultForExchangeDto dto = new CouponValidateResultForExchangeDto();
		dto.setCanUse(true);
		dto.setExchangeType(ExchangeType.SCORE);
		return dto;
	}

	private ExchangeScoreDto mockSuccessDto() {
		return new ExchangeScoreDto("10086", "exchangeCode");
	}
	
	@Test
	public void should_success_when_coupon_can_not_use() {
		Mockito.when(spyCbpProxy.validateForExchange(Matchers.anyString(), Matchers.anyString())).thenReturn(canNotUseExchangeResult());
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost(
				"memberCouponResource", MemberCouponResource.class, "/coupon/exchangeScore", mockSuccessDto(), String.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());
		Assert.assertTrue(result.getOutput().contains("优惠卷不可以使用"));
	}

	private CouponValidateResultForExchangeDto canNotUseExchangeResult() {
		CouponValidateResultForExchangeDto dto = new CouponValidateResultForExchangeDto();
		dto.setCanUse(false);
		dto.setExchangeType(ExchangeType.SCORE);
		return dto;
	}
	
	@Test
	public void should_success_when_coupon_type_error() {
		Mockito.when(spyCbpProxy.validateForExchange(Matchers.anyString(), Matchers.anyString())).thenReturn(typeErrorExchangeResult());
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost(
				"memberCouponResource", MemberCouponResource.class, "/coupon/exchangeScore", mockSuccessDto(), String.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());
		Assert.assertTrue(result.getOutput().contains("优惠卷不可以兑换积分"));
	}

	private CouponValidateResultForExchangeDto typeErrorExchangeResult() {
		CouponValidateResultForExchangeDto dto = new CouponValidateResultForExchangeDto();
		dto.setCanUse(true);
		dto.setExchangeType(null);
		return dto;
	}
	
	@Test
	public void should_success_when_coupon_use_error() {
		Mockito.doThrow(new RuntimeException()).when(spyCbpProxy).useCoupon(Matchers.any(BatchUpdateDto.class));
		ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost(
				"memberCouponResource", MemberCouponResource.class, "/coupon/exchangeScore", mockSuccessDto(), String.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());
	}
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replace("-","").toUpperCase());
	}

}
