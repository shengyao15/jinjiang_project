package com.jje.membercenter.commonContact.commonlyUsedContactResource;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.SexDto;
import com.jje.dto.membercenter.contact.CommonlyUsedContactDto;
import com.jje.dto.membercenter.contact.CommonlyUsedContactsDto;
import com.jje.dto.travel.reservation.GuestCertificationTypeDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.commonContact.CommonlyUsedContactResource;

public class QueryCommonlyUsedContactsWithoutPaginationTest extends DataPrepareFramework {

	@Autowired
    private ResourceInvokeHandler handler;
	
	
	/**
	 * 根据mc查询无数据
	 */
	@Test public void should_be_query_empty_result_successful_when_given_mc_member_code() {
		String mcMemberCode = "11522";
		InvokeResult<CommonlyUsedContactsDto> getResult = handler.doGet("commonlyUsedContactResource", CommonlyUsedContactResource.class, "/commonlyUsedContact/queryList/" + mcMemberCode, CommonlyUsedContactsDto.class);
		Assert.assertEquals(Status.OK, getResult.getStatus());
		
		CommonlyUsedContactsDto outResult = getResult.getOutput();
		List<CommonlyUsedContactDto> list = outResult.getCommonlyUsedContactDtos();
		Assert.assertEquals(true, CollectionUtils.isEmpty(list));
	}
	
	/**
	 * 根据mc查询有数据
	 */
	@Test public void should_be_query_successful_when_given_mc_member_code() {
		String mcMemberCode = "220978";
		InvokeResult<CommonlyUsedContactsDto> getResult = handler.doGet("commonlyUsedContactResource", CommonlyUsedContactResource.class, "/commonlyUsedContact/queryList/" + mcMemberCode, CommonlyUsedContactsDto.class);
		Assert.assertEquals(Status.OK, getResult.getStatus());
		
		CommonlyUsedContactsDto outResult = getResult.getOutput();
		List<CommonlyUsedContactDto> list = outResult.getCommonlyUsedContactDtos();
		Assert.assertEquals(false, CollectionUtils.isEmpty(list));
		
		CommonlyUsedContactDto dto = list.get(0);
		Assert.assertEquals(mcMemberCode, dto.getMcMemberCode());
		Assert.assertEquals("aaaa", dto.getName());
		Assert.assertEquals(GuestCertificationTypeDto.PASSPORT, dto.getIdentityType());
		Assert.assertEquals("RGHJ", dto.getIdentityNo());
		Assert.assertEquals(DateUtils.parseDate("2012-03-12"), dto.getBirthday());
		Assert.assertEquals("", dto.getPhone());
		Assert.assertEquals(SexDto.FEMALE, dto.getGender());
		Assert.assertEquals("", dto.getCardNo());
		
	}
}
