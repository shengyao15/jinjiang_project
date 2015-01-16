package com.jje.membercenter.commonContact.commonlyUsedContactResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.CardType;
import com.jje.dto.membercenter.contact.MemberLevelDto;
import com.jje.dto.travel.reservation.GuestCertificationTypeDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.commonContact.CommonlyUsedContactResource;


public class QueryMemberLevelTest extends DataPrepareFramework {
	
	private static final Logger logger = LoggerFactory.getLogger(QueryMemberLevelTest.class);

	@Autowired
    private ResourceInvokeHandler handler;
	
	@Test
	public void should_be_query_successful_when_given_condition() {
		logger.debug("-----send request xml 【{}】", JaxbUtils.convertToXmlString(mockNormalDto()));
		InvokeResult<MemberLevelDto> postResult = handler.doPost("commonlyUsedContactResource", CommonlyUsedContactResource.class, "/commonlyUsedContact/queryMemberLevel", mockNormalDto(), MemberLevelDto.class);
		Assert.assertEquals(Status.OK, postResult.getStatus());
		
		MemberLevelDto output = postResult.getOutput();
		Assert.assertTrue(output != null);
		Assert.assertEquals("11578633081", output.getCardNo());
		Assert.assertEquals("2", output.getScoreLevel());
		Assert.assertEquals("10082", output.getMcMemberCode());
		Assert.assertEquals(CardType.ENJOY, output.getCardType());
		
		logger.debug("-----response xml 【{}】", JaxbUtils.convertToXmlString(output));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void should_be_query_fail_when_given_condition() {
		InvokeResult postResult = handler.doPost("commonlyUsedContactResource", CommonlyUsedContactResource.class, "/commonlyUsedContact/queryMemberLevel", mockFailDto(), null);
		Assert.assertEquals(Status.NOT_ACCEPTABLE, postResult.getStatus());
	}
	
	private Object mockFailDto() {
		return new MemberLevelDto("xxx", GuestCertificationTypeDto.PASSPORT.name(), "dddddd");
	}

	private MemberLevelDto mockNormalDto() {
		return new MemberLevelDto("xixhaosdfdei", GuestCertificationTypeDto.PASSPORT.name(), "1235dsdfdfdf");
	}
}
