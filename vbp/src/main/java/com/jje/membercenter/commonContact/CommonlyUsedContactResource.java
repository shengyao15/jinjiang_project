package com.jje.membercenter.commonContact;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.jje.dto.hotels.hotel.ResponseResult;
import com.jje.dto.membercenter.contact.CommonlyUsedContactDto;
import com.jje.dto.membercenter.contact.CommonlyUsedContactsDto;
import com.jje.dto.membercenter.contact.MemberLevelDto;
import com.jje.membercenter.commonContact.domain.CommonlyUsedContact;
import com.jje.membercenter.commonContact.domain.CommonlyUsedContactRepository;
import com.jje.membercenter.commonContact.domain.MemberLevel;

@Path("/commonlyUsedContact")
@Component("commonlyUsedContactResource")
public class CommonlyUsedContactResource {

	private static final int MAX_COUNT_COMMONLY_USED_CONTACT = 15;

	private static final Logger logger = LoggerFactory
			.getLogger(CommonlyUsedContactResource.class);

	@Autowired
	private CommonlyUsedContactRepository contactRepository;

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryList/{mcMemberCode}")
	public Response queryCommonlyUsedContactsWithoutPagination(
			@PathParam("mcMemberCode") String mcMemberCode) {

		CommonlyUsedContactsDto dtos = queryCommonlyUsedContacts(mcMemberCode,
				null);
		return Response.ok().entity(dtos).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryList/{mcMemberCode}/{label}")
	public Response queryCommonlyUsedContactsWithoutPagination(
			@PathParam("mcMemberCode") String mcMemberCode,
			@PathParam("label") String label) {

		CommonlyUsedContactsDto dtos = queryCommonlyUsedContacts(mcMemberCode,
				label);
		return Response.ok().entity(dtos).build();
	}

	private CommonlyUsedContactsDto queryCommonlyUsedContacts(
			String mcMemberCode, String label) {
		List<CommonlyUsedContact> commonlyUsedContactList = contactRepository
				.queryCommonlyUsedContactsWithoutPagination(mcMemberCode, label);
		if (CollectionUtils.isEmpty(commonlyUsedContactList)) {
			logger.warn(
					"CommonlyUsedContactResource.queryCommonlyUsedContactsWithoutPagination({})--return results is empty",
					mcMemberCode);
		}
		CommonlyUsedContactsDto dtos = new CommonlyUsedContactsDto();
		for (CommonlyUsedContact commonlyUsedContact : commonlyUsedContactList) {
			dtos.add(commonlyUsedContact.toDto());
		}

		return dtos;
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/add")
	public Response addCommonlyUsedContact(CommonlyUsedContactDto dto) {
		ResponseResult responseResult = new ResponseResult();
		responseResult.setStatus(ResponseResult.FAIL);
		if (dto != null && !dto.isNormal()) {
			responseResult.setCode(Status.BAD_REQUEST.getReasonPhrase());
			responseResult.setMessage("请求参数不正确");
			logger.warn(
					"CommonlyUsedContactResource.addCommonlyUsedContact({})----请求参数不正确",
					dto);
			return Response.status(Status.BAD_REQUEST).entity(responseResult)
					.build();
		}
		List<CommonlyUsedContact> contacts = contactRepository
				.queryCommonlyUsedContactsWithoutPagination(
						dto.getMcMemberCode(), null);
		if (!CollectionUtils.isEmpty(contacts)
				&& contacts.size() >= MAX_COUNT_COMMONLY_USED_CONTACT) {
			responseResult.setCode(Status.CONFLICT.getReasonPhrase());
			responseResult.setMessage("与业务规则冲突，一个会员最多能添加15条常用联系人记录，超过不再添加");
			logger.warn(String
					.format("CommonlyUsedContactResource.addCommonlyUsedContact(%s)----与业务规则冲突，一个会员最多能添加15条常用联系人记录，但该会员(%s)的常用联系人记录为%s",
							dto, dto.getMcMemberCode(), contacts.size()));
			return Response.status(Status.CONFLICT).entity(responseResult)
					.build();
		}
		try {
			contactRepository.addCommonlyUsedContact(new CommonlyUsedContact(
					dto));
		} catch (Exception e) {
			responseResult.setCode(Status.INTERNAL_SERVER_ERROR
					.getReasonPhrase());
			responseResult.setMessage("发生系统异常");
			logger.error(
					"CommonlyUsedContactResource.addCommonlyUsedContact({})--发生sql异常，可能是字段超长,也可能是其他原因导致系统异常",
					dto, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(responseResult).build();
		}
		responseResult.setStatus(ResponseResult.SUCCESS);
		responseResult.setCode(Status.OK.getReasonPhrase());
		responseResult.setMessage("成功添加一个常用联系人");
		return Response.status(Status.OK).entity(responseResult).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/batchAdd")
	public Response batchAddCommonlyUsedContact(CommonlyUsedContactsDto dtos) {
		if (dtos.isEmpty()) {
			logger.warn(
					"CommonlyUsedContactResource.batchAddCommonlyUsedContact({})--contact list is empty",
					dtos);
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!dtos.isNormal()) {
			logger.warn(
					"CommonlyUsedContactResource.batchAddCommonlyUsedContact({})----请求参数不正确",
					dtos);
			return Response.status(Status.BAD_REQUEST).build();
		}
		List<CommonlyUsedContact> contacts = contactRepository
				.queryCommonlyUsedContactsWithoutPagination(
						dtos.getMcMemberCode(), null);
		if (!CollectionUtils.isEmpty(contacts)
				&& (contacts.size() >= MAX_COUNT_COMMONLY_USED_CONTACT || (contacts
						.size() + dtos.size()) >= MAX_COUNT_COMMONLY_USED_CONTACT)) {
			logger.warn(
					"CommonlyUsedContactResource.batchAddCommonlyUsedContact({})--与业务规则冲突，一个会员最多能添加15条常用联系人记录，超过不再添加",
					dtos);
			return Response.status(Status.CONFLICT).build();
		}
		try {
			contactRepository.batchCommonlyUsedContact(dtos
					.getCommonlyUsedContactDtos());
		} catch (Exception e) {
			logger.error(
					"CommonlyUsedContactResource.batchAddCommonlyUsedContact({})--batch add commonlyUsedContact exception",
					dtos, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryMemberLevel")
	public Response queryMemberLevel(MemberLevelDto dto) {
		try {
			MemberLevel level = contactRepository.queryMember(new MemberLevel(
					dto));
			if (level == null) {
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			MemberLevelDto levelDto = level.toDto();
			levelDto.setName(dto.getName());
			levelDto.setIdentityType(dto.getIdentityType());
			levelDto.setIdentityNo(dto.getIdentityNo());

			return Response.status(Status.OK).entity(levelDto).build();
		} catch (Exception e) {
			logger.error("CommonlyUsedContactResource.queryMemberLevel({})",
					dto, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
