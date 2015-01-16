package com.jje.membercenter.commonContact.commonlyUsedContactResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.SexDto;
import com.jje.dto.hotels.hotel.ResponseResult;
import com.jje.dto.membercenter.contact.CommonlyUsedContactDto;
import com.jje.dto.travel.reservation.GuestCertificationTypeDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.commonContact.CommonlyUsedContactResource;

public class AddCommonlyUsedContactTest extends DataPrepareFramework {
	
	private static final Logger logger = LoggerFactory.getLogger(AddCommonlyUsedContactTest.class);

	@Autowired
    private ResourceInvokeHandler handler;
	
	/**
	 * 请求参数不正确，不执行新增操作
	 */
	@Test public void should_be_add_fail_when_given_no_normal_dto() {
		InvokeResult<ResponseResult> getResult = handler.doPost("commonlyUsedContactResource", CommonlyUsedContactResource.class, "/commonlyUsedContact/add", mockNotNormalDto(), ResponseResult.class);
		Assert.assertEquals(Status.BAD_REQUEST, getResult.getStatus());
		ResponseResult responseResult = getResult.getOutput();
		Assert.assertNotNull(responseResult);
		Assert.assertEquals(ResponseResult.FAIL, responseResult.getStatus());
		Assert.assertEquals(Status.BAD_REQUEST.getReasonPhrase(), responseResult.getCode());
		Assert.assertEquals("请求参数不正确", responseResult.getMessage());
	}

	/**
	 * 一个会员最多能添加15条常用联系人记录，超过不再添加
	 */
	@Test public void should_be_add_fail_when_given_dto_mc_query_commonly_used_contact_more_than_15_records() {
		InvokeResult<ResponseResult> getResult = handler.doPost("commonlyUsedContactResource", CommonlyUsedContactResource.class, "/commonlyUsedContact/add", mockConflictDto(), ResponseResult.class);
		Assert.assertEquals(Status.CONFLICT, getResult.getStatus());
		
		ResponseResult responseResult = getResult.getOutput();
		Assert.assertNotNull(responseResult);
		Assert.assertEquals(ResponseResult.FAIL, responseResult.getStatus());
		Assert.assertEquals(Status.CONFLICT.getReasonPhrase(), responseResult.getCode());
		Assert.assertEquals("与业务规则冲突，一个会员最多能添加15条常用联系人记录，超过不再添加", responseResult.getMessage());
	}

	/**
	 * 入参不为空，执行新增操作，且成功
	 */
	@Test public void should_be_add_successful_when_given_dto() {
		logger.warn("-----【"+ JaxbUtils.convertToXmlString(mockSuccessDto()) + "】------");
		InvokeResult<ResponseResult> getResult = handler.doPost("commonlyUsedContactResource", CommonlyUsedContactResource.class, "/commonlyUsedContact/add", mockSuccessDto(), ResponseResult.class);
		Assert.assertEquals(Status.OK, getResult.getStatus());
		
		ResponseResult responseResult = getResult.getOutput();
		Assert.assertNotNull(responseResult);
		Assert.assertEquals(ResponseResult.SUCCESS, responseResult.getStatus());
		Assert.assertEquals(Status.OK.getReasonPhrase(), responseResult.getCode());
		Assert.assertEquals("成功添加一个常用联系人", responseResult.getMessage());
	}

	/**
	 * 入参不为空，sql异常，可能是字段的长度超长导致
	 */
	@Test public void should_be_add_sql_exception_when_given_too_length_fileds_dto () {
		InvokeResult<ResponseResult> getResult = handler.doPost("commonlyUsedContactResource", CommonlyUsedContactResource.class, "/commonlyUsedContact/add", mockExceptionDto(), ResponseResult.class);
		Assert.assertEquals(Status.INTERNAL_SERVER_ERROR, getResult.getStatus());
		
		ResponseResult responseResult = getResult.getOutput();
		Assert.assertNotNull(responseResult);
		Assert.assertEquals(ResponseResult.FAIL, responseResult.getStatus());
		Assert.assertEquals(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), responseResult.getCode());
		Assert.assertEquals("发生系统异常", responseResult.getMessage());
	}
	
	private CommonlyUsedContactDto mockNotNormalDto() {
		CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
		dto.setIdentityNo("421111254623152213");
		dto.setPhone("13122168169");
		dto.setCardNo("464611");
		dto.setGender(SexDto.MALE);
		return dto;
	}
	
	private CommonlyUsedContactDto mockConflictDto() {
		CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
		dto.setMcMemberCode("220978");
		dto.setName("肖慈林");
		dto.setIdentityType(GuestCertificationTypeDto.PASSPORT);
		dto.setIdentityNo("421111254623152213");
		dto.setBirthday(DateUtils.parseDate("1986-12-12"));
		dto.setPhone("13122168169");
		dto.setCardNo("464611");
		dto.setGender(SexDto.MALE);
		
		return dto;
	}
	
	private CommonlyUsedContactDto mockSuccessDto() {
		CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
		dto.setMcMemberCode("120978");
		dto.setName("肖慈林");
		dto.setIdentityType(GuestCertificationTypeDto.PASSPORT);
		dto.setIdentityNo("421111254623152213");
		dto.setBirthday(DateUtils.parseDate("1986-12-12"));
		dto.setPhone("13122168169");
		dto.setCardNo("464611");
		dto.setGender(SexDto.MALE);
		
		return dto;
	}
	
	private CommonlyUsedContactDto mockExceptionDto() {
		CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
		dto.setMcMemberCode("120978");
		dto.setName("肖慈林");
		dto.setIdentityType(GuestCertificationTypeDto.PASSPORT);
		dto.setIdentityNo("421111254623152213");
		dto.setBirthday(DateUtils.parseDate("1986-12-12"));
		dto.setPhone("1312216816913122168169131221681691312216816913122168169");
		dto.setCardNo("464611");
		dto.setGender(SexDto.MALE);
		
		return dto;
	}
}
