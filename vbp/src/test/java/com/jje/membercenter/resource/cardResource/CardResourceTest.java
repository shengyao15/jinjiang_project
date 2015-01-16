package com.jje.membercenter.resource.cardResource;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.CardResource;
import com.jje.membercenter.DataPrepareFramework;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class CardResourceTest extends DataPrepareFramework {
	
	


	@Autowired
	private VirtualDispatcherService virtualDispatcherService;

	@Test
	public void test_webmember_save_question_ok() throws Exception {
			String content = JaxbUtils.convertToXmlString(getTestWebMemberDto());
			MockHttpRequest request = MockHttpRequest.post("/card/modifyQuestion");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			virtualDispatcherService.getDispatcher("cardResource",  CardResource.class).invoke(request, response);
			CRMResponseDto rsponseDto =JaxbUtils.convertToObject(response.getContentAsString(), CRMResponseDto.class);			
			Assert.assertEquals("1", rsponseDto.getRetcode());
	}
 
	private MemberDto getTestWebMemberDto(){
		MemberDto dto=new MemberDto();
		dto.setMemType(MemType.QUICK_REGIST.name());
		dto.setIsWebMember(true);
		dto.setId(1L);
		dto.setRemindQuestion("您最喜欢的密码是什么？");
		dto.setRemindAnswer("123456");
		return dto;
	}

	
	@Test
	public void test_member_save_question_ok() throws Exception {
			String content = JaxbUtils.convertToXmlString(getTestMemberDto());
			MockHttpRequest request = MockHttpRequest.post("/card/modifyQuestion");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			virtualDispatcherService.getDispatcher("cardResource",  CardResource.class).invoke(request, response);
			CRMResponseDto rsponseDto =JaxbUtils.convertToObject(response.getContentAsString(), CRMResponseDto.class);
			Assert.assertEquals("1", rsponseDto.getRetcode());
	}
 
	private MemberDto getTestMemberDto(){
		MemberDto dto=new MemberDto();
		dto.setMemberID("1-EZ4C9");
		dto.setMemberCode("1-19056406");
		dto.setRemindQuestion("您最喜欢的密码是什么？");
		dto.setRemindAnswer("123456");
		return dto;
	}
	
}
