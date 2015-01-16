package com.jje.membercenter.score.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
import com.jje.dto.coupon.issue.exchange.RuleValidateMessage;
import com.jje.dto.coupon.issue.exchange.ValidateIssueResult;
import com.jje.dto.coupon.rule.CouponRuleDto;
import com.jje.dto.coupon.rule.issue.IssueRuleDto;
import com.jje.dto.coupon.rule.issue.exchange.ExchangeEventDto;
import com.jje.dto.membercenter.score.ScoreExchangeDto;
import com.jje.dto.vbp.coupon.MallScoreDeductResultDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.score.ScoreTradeResource;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.mall.domain.DeductReturned;
import com.jje.vbp.mall.service.MemberMallService;

@Ignore
public class ScoreExchangeResourceTest  extends DataPrepareFramework{
	
	 @Autowired
     private ResourceInvokeHandler resourceInvokeHandler;
	
	 @Autowired
	 private  MemberMallService  mallService;
	 
	 @Mock
     private CrmPassage crm;
	 
	 @Mock
	 private CbpHandler cbpHandler; 
	 
	 @Mock
	 private CbpHandler cbpHandler2; 
	 
	 @Autowired
	 private ScoreTradeResource scoreTradeResource;

	 
	 @Before
	 public void initMocks() throws Exception {
	        MockitoAnnotations.initMocks(this);
	 }

	@Test
	public void should_be_trade_success() throws Exception{
		Mockito.when(crm.sendToType(Matchers.anyObject(), Matchers.eq(DeductReturned.class))).thenReturn(mockDeductReturnedDto());
        Mockito.when(cbpHandler.triggerCouponExchangeIssue(Mockito.any(ExchangeIssueDto.class))).thenReturn(mockCouponSysIssueResultDto());
        Mockito.when(cbpHandler2.validteExchangeIssueRule(Mockito.any(ExchangeIssueDto.class))).thenReturn(buildValidateIssueResultDto());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(mallService), "crm", crm);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(mallService), "cbpHandler", cbpHandler);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(scoreTradeResource), "cbpHandler", cbpHandler2);
		InvokeResult<MallScoreDeductResultDto>	result= resourceInvokeHandler.doPost("scoreTradeResource", ScoreTradeResource.class, "/scoreTrade/scoreExchange", buildScoreExchangeDto(), MallScoreDeductResultDto.class);
		Assert.assertEquals(result.getOutput().getCode(), "201");
	}
	
	private ValidateIssueResult buildValidateIssueResultDto() {
		ValidateIssueResult result=new ValidateIssueResult(true, RuleValidateMessage.SUCCESS);
		List<CouponRuleDto> couponRuleDtos=new ArrayList<CouponRuleDto>();
		CouponRuleDto dto=new CouponRuleDto();
		IssueRuleDto issueRuleDto= new IssueRuleDto();
		ExchangeEventDto eventDto=new ExchangeEventDto();
		eventDto.setCost("200");
		issueRuleDto.setExchangeEventDto(eventDto);
		dto.setIssueRuleDto(issueRuleDto);
		couponRuleDtos.add(dto);
		result.setCouponRuleDtos(couponRuleDtos);
		return result;
	}

	@After
	public void after(){
		resourceInvokeHandler.resetField(mallService, "crm", CrmPassage.class);
        resourceInvokeHandler.resetField(mallService, "cbpHandler", CbpHandler.class);
	}
	
	
	private Object buildScoreExchangeDto() {
		ScoreExchangeDto dto=new ScoreExchangeDto();
		dto.setMcMemberCode("882200");
		dto.setPoints("200");
		dto.setProductId("123");
		dto.setIssueCount(4);
		return dto;
	}

	
	private DeductReturned mockDeductReturnedDto() {
		DeductReturned deReturned=new DeductReturned();
		deReturned.getHead().setRetcode("00201");
		deReturned.getBody().setMemberId("Jj123456");
		deReturned.getBody().setTransId("1234567");
		return deReturned;
	}
	

	private CouponSysIssueResult mockCouponSysIssueResultDto() 
	{
		CouponSysIssueResult result = new CouponSysIssueResult(4, new BigDecimal(20));
		result.setResponseMessage(ResponseMessage.SUCCESS);
		return result;
	}

	
	
	

}
