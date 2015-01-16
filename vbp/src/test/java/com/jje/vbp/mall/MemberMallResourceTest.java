package com.jje.vbp.mall;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jje.common.utils.DateUtils;
import com.jje.membercenter.persistence.MemberScoreTradeOrderRepository;
import com.jje.membercenter.score.domain.MemberScoreTrade;
import com.jje.membercenter.score.domain.TradeStatus;
import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.CouponSysIssueResult.ResponseMessage;
import com.jje.dto.coupon.issue.exchange.ExchangeIssueDto;
import com.jje.dto.vbp.coupon.MallScoreDeductDto;
import com.jje.dto.vbp.coupon.MallScoreDeductResultDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.mall.domain.DeductReturned;
import com.jje.vbp.mall.service.MemberMallService;

import javax.ws.rs.core.Response;

public class MemberMallResourceTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Autowired
	private MemberMallService mallService;

	@Mock
	private CrmPassage crm;

	@Mock
	private CbpHandler cbpHandler;
	
	@Mock
	private MemberHandler memberHandler;

    @Autowired
    private MemberScoreTradeOrderRepository memberScoreTradeOrderRepository;
	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_be_trade_success() throws Exception {
		Mockito.when(crm.sendToType(Matchers.anyObject(), Matchers.eq(DeductReturned.class))).thenReturn(mockDeductReturnedDto());
		Mockito.when(cbpHandler.triggerCouponExchangeIssue(Mockito.any(ExchangeIssueDto.class))).thenReturn(
				mockCouponSysIssueResultDto());
		Mockito.when(memberHandler.getMemberScore(Matchers.eq("1-18120148"))).thenReturn(1000);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(mallService), "crm", crm);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(mallService), "cbpHandler", cbpHandler);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(mallService), "memberHandler", memberHandler);
		InvokeResult<MallScoreDeductResultDto> result = resourceInvokeHandler.doPost("memberMallResource", MemberMallResource.class,
				"/mall/mallScoreDeduct", buildScoreExchangeDto(), MallScoreDeductResultDto.class);
		Assert.assertEquals(result.getOutput().getCode(), "201");
	}
	
	@Test
	public void should_retry_trade_success() throws Exception {
		Mockito.when(crm.sendToType(Matchers.anyObject(), Matchers.eq(DeductReturned.class))).thenReturn(mockDeductReturnedDto());
		Mockito.when(cbpHandler.triggerCouponExchangeIssue(Mockito.any(ExchangeIssueDto.class))).thenReturn(
				mockCouponSysIssueResultDto());
		Mockito.when(memberHandler.getMemberScore(Matchers.eq("1-18120148"))).thenReturn(1000);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(mallService), "crm", crm);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(mallService), "cbpHandler", cbpHandler);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(mallService), "memberHandler", memberHandler);
		MallScoreDeductDto scoreExchangeDto = buildScoreExchangeDto();
		scoreExchangeDto.setOrderNum("TRO_10022");
		InvokeResult<MallScoreDeductResultDto> result = resourceInvokeHandler.doPost("memberMallResource", MemberMallResource.class,
				"/mall/mallScoreDeduct", scoreExchangeDto, MallScoreDeductResultDto.class);
		Assert.assertEquals(result.getOutput().getCode(), "201");
		result = resourceInvokeHandler.doPost("memberMallResource", MemberMallResource.class,
				"/mall/mallScoreDeductRetry", scoreExchangeDto, MallScoreDeductResultDto.class);
		Assert.assertEquals(result.getOutput().getCode(), "201");
	}

	@After
	public void after() {
		resourceInvokeHandler.resetField(mallService, "crm", CrmPassage.class);
		resourceInvokeHandler.resetField(mallService, "cbpHandler", CbpHandler.class);
		resourceInvokeHandler.resetField(mallService, "memberHandler", MemberHandler.class);
	}

	private MallScoreDeductDto buildScoreExchangeDto() {
		MallScoreDeductDto dto = new MallScoreDeductDto();
		dto.setMcMemberCode("882200");
		dto.setPoints("200");
		dto.setProductId("123");
		dto.setIssueCount(4);
		dto.setOrderNum("TRO_10021");
		return dto;
	}

	private DeductReturned mockDeductReturnedDto() {
		DeductReturned deReturned = new DeductReturned();
		deReturned.getHead().setRetcode("00201");
		deReturned.getBody().setMemberId("Jj123456");
		deReturned.getBody().setTransId("1234567");
		return deReturned;
	}

	private CouponSysIssueResult mockCouponSysIssueResultDto() {
		CouponSysIssueResult result = new CouponSysIssueResult(4, new BigDecimal(20));
		result.setResponseMessage(ResponseMessage.SUCCESS);
		return result;
	}

    @Test
    public void should_be_send_success_when_have_block_Order() throws Exception {
        MemberScoreTrade memberScoreTrade = new MemberScoreTrade();
        memberScoreTrade.setStatus(TradeStatus.BLOCK);
        memberScoreTrade.setLastUpdateTime(DateUtils.addMinutes(new Date(),  -30));
        List<MemberScoreTrade> memberScoreTradesBefore =  memberScoreTradeOrderRepository.queryMemberScoreTradeOrderForTimeout(memberScoreTrade);
        Assert.assertTrue(CollectionUtils.isNotEmpty(memberScoreTradesBefore));
        InvokeResult<String> result =  resourceInvokeHandler.doGet("memberMallResource", MemberMallResource.class,
                  "/mall/scoreDeductTimeoutByMinute/30", String.class);
        Assert.assertEquals(result.getStatus(), Response.Status.OK);
        List<MemberScoreTrade> memberScoreTradesAfter =  memberScoreTradeOrderRepository.queryMemberScoreTradeOrderForTimeout(memberScoreTrade);
        Assert.assertTrue(CollectionUtils.isEmpty(memberScoreTradesAfter));
    }

}
