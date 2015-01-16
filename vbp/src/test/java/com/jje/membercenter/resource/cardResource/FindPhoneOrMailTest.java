package com.jje.membercenter.resource.cardResource;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberVerfyDto;
import com.jje.membercenter.CardResource;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class FindPhoneOrMailTest {


	@Autowired
	CardResource cardResource;
	
	@Test
	public void addAccountMergeApplyPassTest() throws Exception
	{
		String telPhoneOrMail = "18801045188";
		Response response  = cardResource.findPhoneOrMail(telPhoneOrMail);
		ResultMemberDto<MemberVerfyDto> resultDto = (ResultMemberDto<MemberVerfyDto>) response.getEntity();
		System.out.println(resultDto.getResults().size());
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

}
