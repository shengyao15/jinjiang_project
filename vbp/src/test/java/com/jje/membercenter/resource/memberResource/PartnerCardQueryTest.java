package com.jje.membercenter.resource.memberResource;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.cardbind.CardExistStatusDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class PartnerCardQueryTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test   
	public void should_be_success_when_partner_card_query() throws Exception {
		InvokeResult<CardExistStatusDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/partnerCardQuery", getPartnerCardBindDto(), CardExistStatusDto.class);
		Assert.assertEquals(result.getStatus(), Status.OK);
	}

	private PartnerCardBindDto getPartnerCardBindDto() {
		PartnerCardBindDto  partnerCardBindDto = new PartnerCardBindDto();
		partnerCardBindDto.setPartnerCardNo("111");
		partnerCardBindDto.setPartnerCode(MemberAirLineCompany.ACDH);
		return partnerCardBindDto;
	}

}
