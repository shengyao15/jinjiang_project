package com.jje.vbp.order;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.vbp.order.CardOrderDto;
import com.jje.dto.vbp.order.CardOrderResponseCode;
import com.jje.dto.vbp.order.CardOrderResponseDto;
import com.jje.dto.vbp.order.CardOrderType;
import com.jje.membercenter.DataPrepareFramework;

public class CardOrderResourceTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Before
	public void initMocks() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_create_cardOrder_success() throws Exception {
		InvokeResult<CardOrderResponseDto> result = resourceInvokeHandler.doPost("cardOrderResource", CardOrderResource.class,
				"/cardOrder/create", buildCardOrderDto("JJ001", NewCardType.SILVER_CARD, "789456"), CardOrderResponseDto.class);
		
		Assert.assertEquals(result.getOutput().getCode(), CardOrderResponseCode.SUCCESS);
	}
	
	@Test
	public void should_getOrderForPay_success() throws Exception {
		InvokeResult<CardOrderResponseDto> orderResult = resourceInvokeHandler.doPost("cardOrderResource", CardOrderResource.class,
				"/cardOrder/create", buildCardOrderDto("JJ1100001", NewCardType.PLATINUM_CARD, "789456"), CardOrderResponseDto.class);
		
		InvokeResult<MemberCardOrderDto> result = resourceInvokeHandler.doGet("cardOrderResource", CardOrderResource.class,
				"/cardOrder/getOrderForPay/"+orderResult.getOutput().getOrderNo(),MemberCardOrderDto.class);
		
		MemberCardOrderDto order = result.getOutput();
		Assert.assertTrue("bgUrl", order.getBgUrl().indexOf("/member/payForResumeCard")==0);
		Assert.assertTrue("pageUrl", order.getPageUrl().indexOf("/membercenter/resume/success")==0);
	}

	private CardOrderDto buildCardOrderDto(String cardNo, NewCardType cardType, String mcMemberCode) {
		CardOrderDto cardOrderDto = new CardOrderDto();
		cardOrderDto.setCardNo(cardNo);
		cardOrderDto.setOrderType(CardOrderType.RECHARGE);
		cardOrderDto.setCardType(cardType);
		cardOrderDto.setOrderChannel("Mobile");
		cardOrderDto.setMcMemberCode(mcMemberCode);
		return cardOrderDto;
	}

	@After
	public void after() {
		// resourceInvokeHandler.resetField();
	}

}
