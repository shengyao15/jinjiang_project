package itest.com.jje.membercenter.mobilemember;


import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.AppLoginMemberDto;
import com.jje.dto.membercenter.ValidationDto;
import com.jje.membercenter.AppMemberResource;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class AppMemberResourceIT {

    @Autowired
    private VirtualDispatcherService virtualDispatcherService;

    @Test
    public void test_mobileTempMemberLogin() throws Exception {

        try {
            MockHttpResponse response = mockRemoteResource(getTempValidateDto());
            AppLoginMemberDto loginMemberDto = assertCondition(response);
            Assert.assertEquals("isTempMember value is true", loginMemberDto.getIsTempMember(), Boolean.TRUE.booleanValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_mobileMemberLogin() throws Exception {

        try {
            MockHttpResponse response = mockRemoteResource(getValidateDto());
            AppLoginMemberDto loginMemberDto = assertCondition(response);
            Assert.assertEquals("isTempMember value is false", loginMemberDto.getIsTempMember(), Boolean.FALSE.booleanValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_turnMain_mobileMemberLogin() throws Exception {
        try {
            MockHttpResponse response = mockRemoteResource(getTurnMainValidateDto());
            AppLoginMemberDto loginMemberDto = assertCondition(response);
            Assert.assertEquals("loginMemberDto.loginStatus is -1 ",loginMemberDto.getLoginStatus(),"-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_mobileMemberLogin_NOT_FOUND() throws Exception {

        try {
            MockHttpResponse response = mockRemoteResource(getNotFoundValidateDto());
            AppLoginMemberDto loginMemberDto = assertCondition(response);
            Assert.assertEquals("loginMemberDto.loginStatus is -1 ",loginMemberDto.getLoginStatus(),"-1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     private AppLoginMemberDto assertCondition(MockHttpResponse response) {
        Assert.assertEquals("response stauts is ok", response.getStatus(), Response.Status.OK.getStatusCode());
        Assert.assertNotNull("response xml is not null",response.getContentAsString());
        AppLoginMemberDto loginMemberDto = JaxbUtils.convertToObject(response.getContentAsString(), AppLoginMemberDto.class);
        Assert.assertNotNull("response entity is not null:", loginMemberDto);
        return loginMemberDto;
    }

    private ValidationDto getTurnMainValidateDto() {
        ValidationDto validationDto = new ValidationDto("xiaohei@163.com", "E10ADC3949BA59ABBE56E057F20F883E");
        return validationDto;
    }

    private ValidationDto getNotFoundValidateDto() {
        ValidationDto validationDto = new ValidationDto("13585909080", "123456");
        return validationDto;
    }

    private ValidationDto getTempValidateDto() {
        ValidationDto validationDto = new ValidationDto("13585909080", "E10ADC3949BA59ABBE56E057F20F883E");
        return validationDto;
    }

    private ValidationDto getValidateDto() {
        ValidationDto validationDto = new ValidationDto("chenyongne@126.com", "E10ADC3949BA59ABBE56E057F20F883E");
        return validationDto;
    }

    private MockHttpResponse mockRemoteResource(ValidationDto validationDto) throws URISyntaxException, UnsupportedEncodingException {
        String content = JaxbUtils.convertToXmlString(validationDto);
        System.out.println(content + "=====");
        MockHttpRequest request = MockHttpRequest.post("/appMember/mobileMemberLogin");
        request.content(content.getBytes("UTF-8"));
        request.contentType(MediaType.APPLICATION_XML);
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("appMemberResource", AppMemberResource.class).invoke(request, response);
        return response;
    }

}
