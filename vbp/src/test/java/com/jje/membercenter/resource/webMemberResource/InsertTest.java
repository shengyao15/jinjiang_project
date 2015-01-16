package com.jje.membercenter.resource.webMemberResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.MemType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.WebMemberDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.membercenter.WebMemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class InsertTest {


	@Autowired
	private VirtualDispatcherService virtualDispatcherService;

//	@Test
//	public void queryComplexMemberInfo() throws Exception{
// 		MockHttpRequest request = MockHttpRequest.post("/webMember/queryComplexMemberInfo/D9068648" );
// 		request.contentType(MediaType.APPLICATION_XML);
//		MockHttpResponse response = new MockHttpResponse();
//		virtualDispatcherService.getDispatcher("webMemberResource", WebMemberResource.class).invoke(request, response);
//		ComplexMemberInfoDto result=JaxbUtils.convertToObject(response.getContentAsString(), ComplexMemberInfoDto.class);
//		Assert.assertNotNull(result);
//	}


	@Test
	public void insertTest() throws Exception{
		String content=JaxbUtils.convertToXmlString(mockWebMemberDto());
		System.out.println(content);
		MockHttpRequest request = MockHttpRequest.post("/webMember/regist" );
		request.content(content.getBytes("UTF-8"));
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();
		virtualDispatcherService.getDispatcher("webMemberResource", WebMemberResource.class).invoke(request, response);
		Assert.assertNotNull(response.getContentAsString());
	}



	@Test
	public void queryTest() throws Exception{
		MockHttpRequest request = MockHttpRequest.get("/webMember/queryRegisterByCellphoneOrEmail/email1" );
		MockHttpResponse response = new MockHttpResponse();
		virtualDispatcherService.getDispatcher("webMemberResource", WebMemberResource.class).invoke(request, response);
		System.out.println(response.getContentAsString());
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	
	private WebMemberDto  mockWebMemberDto(){
		WebMemberDto  dto=new WebMemberDto();
		dto.setActivityCode("code");
		dto.setEmail("email1");
		dto.setMemType(MemType.QUICK_REGIST.name());
		dto.setRegistChannel(RegistChannel.Mobile);
		dto.setPwd("pwd");
		dto.setIpAddress("192.168.1.3");
		return dto;
	}

    @Test
    public void  should_passed_registThirdParty() throws Exception {
		String content=JaxbUtils.convertToXmlString(mockWebMemberDtoForThirdParty());
		System.out.println(content);
		MockHttpRequest request = MockHttpRequest.post("/webMember/registThirdParty" );
		request.content(content.getBytes("UTF-8"));
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();
		virtualDispatcherService.getDispatcher("webMemberResource", WebMemberResource.class).invoke(request, response);
		Assert.assertNotNull(response.getContentAsString());
        WebMemberRegisterReturnDto returnDto = JaxbUtils.convertToObject(response.getContentAsString(), WebMemberRegisterReturnDto.class);
        org.junit.Assert.assertThat(returnDto, Is.is(WebMemberRegisterReturnDto.class));
        org.junit.Assert.assertThat(returnDto.getMcMemberCode(), IsNull.<String>notNullValue());
    }


    private WebMemberDto mockWebMemberDtoForThirdParty() {
        WebMemberDto  dto=new WebMemberDto();
		dto.setActivityCode("ThirdPartyCode");
		dto.setEmail("ThirdPartyEmail@xx.com");
        dto.setPhone("15987456321");
		dto.setMemType(MemType.THIRD_PARTY.name());
		dto.setRegistChannel(RegistChannel.Website);
		dto.setPwd("pwd");
		return dto;
    }


}
