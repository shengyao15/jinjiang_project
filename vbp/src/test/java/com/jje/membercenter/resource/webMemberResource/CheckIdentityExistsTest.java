package com.jje.membercenter.resource.webMemberResource;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.CheckStatusResponse;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.WebMemberResource;

public class CheckIdentityExistsTest extends DataPrepareFramework {
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	@Test
	public void should_exists_when_checkIdentityExists() {
		ResourceInvokeHandler.InvokeResult<CheckStatusResponse> result = resourceInvokeHandler.doPost(
				"webMemberResource", WebMemberResource.class, "/webMember/checkIdentityExists", mockExistsDto(),
				CheckStatusResponse.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());
		Assert.assertEquals(CheckStatusResponse.ID_NUMBER_EXIST, result.getOutput());
	}

	private MemberDto mockExistsDto() {
		MemberDto dto = new MemberDto();
		dto.setIdentityNo("jack.shi");
		dto.setIdentityType("ID");
		return dto;
	}

	@Test
	public void should_not_exists_when_checkIdentityExists() {
		ResourceInvokeHandler.InvokeResult<CheckStatusResponse> result = resourceInvokeHandler.doPost(
				"webMemberResource", WebMemberResource.class, "/webMember/checkIdentityExists", mockNotExistsDto(),
				CheckStatusResponse.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());
		Assert.assertEquals(CheckStatusResponse.ID_NUMBER_NOT_EXIST, result.getOutput());
	}

	private MemberDto mockNotExistsDto() {
		MemberDto dto = new MemberDto();
		dto.setIdentityNo("evan");
		dto.setIdentityType("ID");
		return dto;
	}

	@Test
	public void should_not_exists_when_checkIdentityExists_with_diffTypeDto() {
		ResourceInvokeHandler.InvokeResult<CheckStatusResponse> result = resourceInvokeHandler.doPost(
				"webMemberResource", WebMemberResource.class, "/webMember/checkIdentityExists",
				mockNotExistsWithDiffTypeDto(), CheckStatusResponse.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());
		Assert.assertEquals(CheckStatusResponse.ID_NUMBER_NOT_EXIST, result.getOutput());
	}

	private MemberDto mockNotExistsWithDiffTypeDto() {
		MemberDto dto = new MemberDto();
		dto.setIdentityNo("jack.shi");
		dto.setIdentityType("OTHER");
		return dto;
	}

	@Test
	public void should_not_exists_when_checkIdentityExists_with_NullTypeDto() {
		ResourceInvokeHandler.InvokeResult<CheckStatusResponse> result = resourceInvokeHandler.doPost(
				"webMemberResource", WebMemberResource.class, "/webMember/checkIdentityExists",
				mockNotExistsWithNullTypeDto(), CheckStatusResponse.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());
		Assert.assertEquals(CheckStatusResponse.ID_NUMBER_NOT_EXIST, result.getOutput());
	}

	private MemberDto mockNotExistsWithNullTypeDto() {
		MemberDto dto = new MemberDto();
		dto.setIdentityNo("jack.shi");
		return dto;
	}

	@Test
	public void should_not_exists_when_checkIdentityExists_with_Status_Off() {
		ResourceInvokeHandler.InvokeResult<CheckStatusResponse> result = resourceInvokeHandler.doPost(
				"webMemberResource", WebMemberResource.class, "/webMember/checkIdentityExists",
				mockNotExistsWithStatusOffDto(), CheckStatusResponse.class);
		Assert.assertEquals(Response.Status.OK, result.getStatus());
		Assert.assertEquals(CheckStatusResponse.ID_NUMBER_NOT_EXIST, result.getOutput());
	}
	
	private MemberDto mockNotExistsWithStatusOffDto() {
		MemberDto dto = new MemberDto();
		dto.setIdentityNo("jack.shi1212");
		dto.setIdentityType("ID");
		return dto;
	}
	
}
