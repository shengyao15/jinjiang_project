package com.jje.membercenter.commonContact.commonlyUsedContactResource;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.SexDto;
import com.jje.dto.membercenter.contact.CommonlyUsedContactDto;
import com.jje.dto.membercenter.contact.CommonlyUsedContactsDto;
import com.jje.dto.travel.reservation.GuestCertificationTypeDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.commonContact.CommonlyUsedContactResource;
import com.jje.membercenter.commonContact.domain.CommonlyUsedContact;
import com.jje.membercenter.commonContact.domain.CommonlyUsedContactRepository;

public class BatchAddCommonlyUsedContactTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler handler;

	@Autowired
	private CommonlyUsedContactRepository contactRepository;

	/**
	 * 请求参数为空，不执行新增操作
	 */
	@Test
	public void should_be_batch_add_commonly_used_contact_fail_when_given_empty_dtos() {
		CommonlyUsedContactsDto dtos = new CommonlyUsedContactsDto();
		InvokeResult<Object> result = handler.doPost(
				"commonlyUsedContactResource",
				CommonlyUsedContactResource.class,
				"/commonlyUsedContact/batchAdd", dtos, null);
		Assert.assertEquals(Status.BAD_REQUEST, result.getStatus());
	}

	/**
	 * 会员已有13条常用联系人，传入list为3时，超过最大限度15条，不执行新增操作
	 */
	@Test
	public void should_be_batch_add_failed_when_database_exists_commonly_used_contact_size_add_input_contactsDto_size_gt_max_size() {
		CommonlyUsedContactsDto dtos = new CommonlyUsedContactsDto();

		for (int index = 100; index < 103; index++) {
			CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
			dto.setMcMemberCode("220978");
			dto.setName("肖慈林");
			dto.setIdentityType(GuestCertificationTypeDto.PASSPORT);
			dto.setIdentityNo("421111254623152213");
			dto.setBirthday(DateUtils.parseDate("1986-12-12"));
			dto.setPhone("13122168169");
			dto.setCardNo("464611");
			dto.setGender(SexDto.MALE);
			dtos.add(dto);
		}
		InvokeResult<Object> result = handler.doPost(
				"commonlyUsedContactResource",
				CommonlyUsedContactResource.class,
				"/commonlyUsedContact/batchAdd", dtos, null);
		Assert.assertEquals(Status.CONFLICT, result.getStatus());
	}

	/**
	 * 会员已有15条常用联系人，超过最大限度15条，不执行新增操作
	 */
	@Test
	public void should_be_batch_add_failed_when_database_exists_commonly_used_contact_size_gt_max_size() {
		CommonlyUsedContactsDto dtos = new CommonlyUsedContactsDto();

		for (int index = 100; index < 101; index++) {
			CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
			dto.setMcMemberCode("220979");
			dto.setName("肖慈林");
			dto.setIdentityType(GuestCertificationTypeDto.PASSPORT);
			dto.setIdentityNo("421111254623152213");
			dto.setBirthday(DateUtils.parseDate("1986-12-12"));
			dto.setPhone("13122168169");
			dto.setCardNo("464611");
			dto.setGender(SexDto.MALE);
			dtos.add(dto);
		}

		InvokeResult<Object> result = handler.doPost(
				"commonlyUsedContactResource",
				CommonlyUsedContactResource.class,
				"/commonlyUsedContact/batchAdd", dtos, null);
		Assert.assertEquals(Status.CONFLICT, result.getStatus());
	}

	/**
	 * 入参不为空，,部分记录合法，部分记录不合法，不执行新增操作
	 */

	@Test
	public void should_be_batch_add_failed_when_given_commonly_used_contact_params_invaild() {
		CommonlyUsedContactsDto dtos = new CommonlyUsedContactsDto();

		for (int index = 100; index < 106; index++) {
			CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
			if (index != 100) {
				dto.setMcMemberCode("120970");
			}
			if (index != 101) {
				dto.setName("肖慈林");
			}
			if (index != 102) {
				dto.setIdentityType(GuestCertificationTypeDto.PASSPORT);
			}
			if (index != 103) {
				dto.setBirthday(DateUtils.parseDate("1986-12-12"));
			}
			dto.setIdentityNo("421111254623152213");
			dto.setPhone("13122168169");
			dto.setCardNo("464611");
			dto.setGender(SexDto.MALE);
			dtos.add(dto);
		}

		InvokeResult<Object> result = handler.doPost(
				"commonlyUsedContactResource",
				CommonlyUsedContactResource.class,
				"/commonlyUsedContact/batchAdd", dtos, null);
		Assert.assertEquals(Status.BAD_REQUEST, result.getStatus());

		List<CommonlyUsedContact> contacts = contactRepository
				.queryCommonlyUsedContactsWithoutPagination(
						dtos.getMcMemberCode(), null);
		Assert.assertEquals(0, contacts.size());
	}

	/**
	 * 入参不为空，,参数（字段超长太长）异常，事务回滚，不执行新增操作
	 */
	@Test
	public void should_be_batch_add_failed_and_rollback_when_exception() {
		CommonlyUsedContactsDto dtos = new CommonlyUsedContactsDto();
		for (int index = 100; index < 106; index++) {

			CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
			if (index == 105) {
				dto.setMcMemberCode(UUID.randomUUID().toString()
						+ UUID.randomUUID().toString());
			} else {
				dto.setMcMemberCode("120970");
			}
			dto.setName("肖慈林");
			dto.setIdentityType(GuestCertificationTypeDto.PASSPORT);
			dto.setIdentityNo("421111254623152213");
			dto.setBirthday(DateUtils.parseDate("1986-12-12"));
			dto.setPhone("13122168169");
			dto.setCardNo("464611");
			dto.setGender(SexDto.MALE);
			dtos.add(dto);
		}

		InvokeResult<Object> result = handler.doPost(
				"commonlyUsedContactResource",
				CommonlyUsedContactResource.class,
				"/commonlyUsedContact/batchAdd", dtos, null);
		Assert.assertEquals(Status.INTERNAL_SERVER_ERROR, result.getStatus());

		List<CommonlyUsedContact> contacts = contactRepository
				.queryCommonlyUsedContactsWithoutPagination(
						dtos.getMcMemberCode(), null);
		Assert.assertEquals(0, contacts.size());
	}

	/**
	 * 入参不为空，执行新增操作，且成功
	 */
	@Test
	public void should_be_batch_add_succesful_when_database_exists_commonly_used_contact_size_add_input_contactsDto_size_lt_max_size() {
		CommonlyUsedContactsDto dtos = new CommonlyUsedContactsDto();

		for (int index = 100; index < 108; index++) {
			CommonlyUsedContactDto dto = new CommonlyUsedContactDto();
			dto.setMcMemberCode("120970");
			dto.setName("肖慈林");
			dto.setIdentityType(GuestCertificationTypeDto.PASSPORT);
			dto.setIdentityNo("421111254623152213");
			dto.setBirthday(DateUtils.parseDate("1986-12-12"));
			dto.setPhone("13122168169");
			dto.setCardNo("464611");
			dto.setGender(SexDto.MALE);
			dtos.add(dto);
		}

		InvokeResult<Object> result = handler.doPost(
				"commonlyUsedContactResource",
				CommonlyUsedContactResource.class,
				"/commonlyUsedContact/batchAdd", dtos, null);
		Assert.assertEquals(Status.OK, result.getStatus());

		List<CommonlyUsedContact> contacts = contactRepository
				.queryCommonlyUsedContactsWithoutPagination(
						dtos.getMcMemberCode(), null);
		Assert.assertEquals(8, contacts.size());
	}
}
