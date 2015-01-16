package com.jje.membercenter.mobilemember;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.dto.membercenter.AppLoginMemberDto;
import com.jje.dto.membercenter.ValidationDto;
import com.jje.membercenter.AppMemberResource;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.persistence.WebMemberRepository;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class AppMemberJunitTest extends DataPrepareFramework {

    @Autowired
    private AppMemberResource appMemberResource;

    @Autowired
    private WebMemberRepository webMemberRepository;

    @Test
    public void test_AppMemberResourceMobile_tempMemberLogin() {
        try {
            AppLoginMemberDto loginMemberDto = assertCondition(getMockTempMemberValidationDto());
            Assert.assertEquals("isTempMember value is true", Boolean.TRUE.booleanValue(), loginMemberDto.getIsTempMember());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AppLoginMemberDto assertCondition(ValidationDto validationDto){

        Response response = appMemberResource.mobileMemberLogin(validationDto);
            Assert.assertEquals("response status is ok:", response.getStatus(), Response.Status.OK.getStatusCode());
            AppLoginMemberDto loginMemberDto = (AppLoginMemberDto) response.getEntity();
            Assert.assertNotNull("response entity not null:", loginMemberDto);
        return loginMemberDto;
    }

    @Test
    public void test_AppMemberResourceMobile_MemberLogin() {
        try {
           AppLoginMemberDto loginMemberDto = assertCondition(getMockRegMemberValidationDto());
            Assert.assertEquals("loginStatus is 0",loginMemberDto.getLoginStatus(),"0");
            Assert.assertEquals("isTempMember value is false", loginMemberDto.getIsTempMember(), Boolean.FALSE.booleanValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_AppMemberResourceMobile_MemberLogin_TempMemberTurnMain_NotFoundForMainMember() {
        try {
            AppLoginMemberDto loginMemberDto = assertCondition(getMockTurnMainMemberNotFoundForMainMemberValidationDto());
            Assert.assertEquals("loginStatus is -1",loginMemberDto.getLoginStatus(),"-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_AppMemberResourceMobile_MemberLogin_NOT_FOUND() {
        try {
           AppLoginMemberDto loginMemberDto = assertCondition(getMockNotExitsMemberValidationDto());
           Assert.assertEquals("loginStatus is -1",loginMemberDto.getLoginStatus(),"-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_WebMemberRepository_queryMemberInfo() {
        try {
            WebMember webMember = webMemberRepository.queryMemberInfo("13585909080", "E10ADC3949BA59ABBE56E057F20F883E");
            Assert.assertNotNull("webMember not null: ", webMember);
            Assert.assertEquals("cardNo not null", "H2068643", webMember.getTempCardNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_WebMemberRepository_queryMemberInfo_NOT_FOUND() {
        try {
            WebMember webMember = webMemberRepository.queryMemberInfo("13585909080", "123456");
            Assert.assertNull("webMember not null: ", webMember);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ValidationDto getMockTurnMainMemberNotFoundForMainMemberValidationDto() {
        ValidationDto validationDto = new ValidationDto("xiaohei@163.com", "E10ADC3949BA59ABBE56E057F20wF883E");
        return validationDto;
    }

    private ValidationDto getMockTempMemberValidationDto() {
        ValidationDto validationDto = new ValidationDto("13585909080", "E10ADC3949BA59ABBE56E057F20F883E");
        return validationDto;
    }

    private ValidationDto getMockRegMemberValidationDto() {
        ValidationDto validationDto = new ValidationDto("test222@jinjiang.com", "E10ADC3949BA59ABBE56E057F20F883E");
        return validationDto;
    }

    private ValidationDto getMockNotExitsMemberValidationDto() {
        ValidationDto validationDto = new ValidationDto("chenyongne@126.com", "123456");
        return validationDto;
    }
}
