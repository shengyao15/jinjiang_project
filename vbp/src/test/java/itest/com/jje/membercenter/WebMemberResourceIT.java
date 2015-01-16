package itest.com.jje.membercenter;

import javax.ws.rs.core.MediaType;

import com.jje.dto.membercenter.*;
import com.jje.membercenter.DataPrepareFramework;
import junit.framework.Assert;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.MD5Utils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.WebMemberResource;

public class WebMemberResourceIT extends DataPrepareFramework {

	@Autowired
	private VirtualDispatcherService virtualDispatcherService;

	@Test
	public void migrateWebMemberInfo() throws Exception {
		try {
			String content = JaxbUtils.convertToXmlString(getMigrateMemberDto());
			System.out.println(content);
			MockHttpRequest request = MockHttpRequest.post("/webMember/completeWebMemberInfo");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			virtualDispatcherService.getDispatcher("webMemberResource", WebMemberResource.class).invoke(request, response);
			RegistResponse r=JaxbUtils.convertToObject(response.getContentAsString(), RegistResponse.class);
			Assert.assertEquals("OK", r.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MemberDto getMigrateMemberDto() {
		MemberDto dto = new MemberDto();
		dto.setIdentityType("Others");
		dto.setIdentityNo("11111");
		dto.setEmail("test111");
		dto.setPassword("pwd111");
		dto.setCellPhone("123456781011");
		dto.setFullName("陈勇");
		return dto;
	}

	
	@Test
	public void quickRegistWebMember() throws Exception {
		try {
			String content = JaxbUtils.convertToXmlString(getRegistMemberDto());
			MockHttpRequest request = MockHttpRequest.post("/webMember/completeWebMemberInfo");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			virtualDispatcherService.getDispatcher("webMemberResource", WebMemberResource.class).invoke(request, response);
			RegistResponse r=JaxbUtils.convertToObject(response.getContentAsString(), RegistResponse.class);
			Assert.assertEquals("OK", r.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void quickWebMember() throws Exception {
		try {
			String content = JaxbUtils.convertToXmlString(buildRegisterDto());
			MockHttpRequest request = MockHttpRequest.post("/member/quickRegister");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			virtualDispatcherService.getDispatcher("memberResource", MemberResource.class).invoke(request, response);
			CRMResponseDto dto = JaxbUtils.convertToObject(response.getContentAsString(), CRMResponseDto.class);
		    Assert.assertNotNull(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    @Test
    public void should_passed_quickRegistAllInfo() throws Exception {
        String content=JaxbUtils.convertToXmlString(mockQuickRegistAllInfo());
        MockHttpRequest request = MockHttpRequest.post("/webMember/quickRegistAllInfo" );
		request.content(content.getBytes("UTF-8"));
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();
		virtualDispatcherService.getDispatcher("webMemberResource", WebMemberResource.class).invoke(request, response);
        Assert.assertNotNull(response.getContentAsString());
    }

    private MemberDto mockQuickRegistAllInfo() {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("wiwifdf@123.com");
        memberDto.setCellPhone("16542312654");
        memberDto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        memberDto.setIdentityType("Work");
        memberDto.setIdentityNo("1235dfdf");
        memberDto.setFullName("xixhaoei");
        memberDto.setRegisterTag("aa|bb|cc");
        memberDto.setRegisterSource(RegistChannel.Ikamobile.name());
        System.out.println(JaxbUtils.convertToXmlString(memberDto));
        return memberDto;
    }
	
	
	private MemberQuickRegisterDto buildRegisterDto() {
		MemberQuickRegisterDto registerDto = new MemberQuickRegisterDto();
		registerDto.setEmail("121@sohu.com");
		registerDto.setMobile("12564e1189455");
		registerDto.setCertificateNo("12345678933456");
		registerDto.setCertificateType("Others");
		registerDto.setPassword(MD5Utils.generatePassword("123456"));
		registerDto.setName("testtest");
		return registerDto;
	}

	
	private MemberDto getRegistMemberDto() {
		MemberDto dto = new MemberDto();
		dto.setIdentityType("Others");
		dto.setIdentityNo("456711111");
		dto.setEmail("insert11sss1ss11@sohu.com");
		dto.setPassword("45678901");
		dto.setCellPhone("456789011s11sss11");
		dto.setFullName("test11");
		return dto;
	}
	

}
