package com.jje.membercenter.resource.memberXmlResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberXmlResource;
@Ignore
public class GetMemberRepeatTest extends DataPrepareFramework{

	@Autowired
    private ResourceInvokeHandler handler;

	@Test
	public void should_getRepeatMenName_NOT_Exist() throws Exception{
		InvokeResult<String> result = handler.doPost("memberXmlResource", MemberXmlResource.class, "/member/getMemberRepeat", 
				mockNotExistRepeatMember(), String.class);
		Assert.assertEquals("N", result.getOutput());
	}

	

	private MemberDto mockNotExistRepeatMember() {
		MemberDto mem = new MemberDto();
		mem.setMemberCode("1157866002");
		mem.setUserName("memtest1@jinjiang.com");
		return mem;

	}
	
	@Test
	public void should_getRepeatMenName_Exist()throws Exception {
		InvokeResult<String> result = handler.doPost("memberXmlResource", MemberXmlResource.class, "/member/getMemberRepeat", 
				mockExistRepeatMember(), String.class);
		Assert.assertEquals(Status.OK, result.getStatus());
		Assert.assertEquals("Y", result.getOutput());

	}

	private MemberDto mockExistRepeatMember() {
		MemberDto mem = new MemberDto();
		mem.setMemberCode("e3jdu3-3");
		mem.setUserName("member_name@jack.com");
		return mem;

	}

}
