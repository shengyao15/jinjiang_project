package com.jje.membercenter;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.RestClient;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.WebMemberDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.service.WebMemberService;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import javax.ws.rs.core.Response;
import java.util.Date;

public class MemberBatchCompleteResourceTest extends DataPrepareFramework {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Autowired
    private RestClient client;

    @Autowired
    private WebMemberService service;



    private static final String webMemberProxy="http://memberqa.jje.com:5555/vbp/webMember/regist";


    @Test
    public void test_regist_success_return_webmemberregisterreturndto() throws Exception {
        ResourceInvokeHandler.InvokeResult result =
                resourceInvokeHandler.doGet("memberBatchCompleteResource",
                        MemberBatchCompleteResource.class, "/batchComplete/batchCompleteMemberInfo/1000/3",
                        null);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
    }

    @Test
    public void test_single_regist_success_return_webmemberregisterreturndto() throws Exception {
        ResourceInvokeHandler.InvokeResult result =
                resourceInvokeHandler.doGet("memberBatchCompleteResource",
                        MemberBatchCompleteResource.class, "/batchComplete/singleCompleteMemberInfo/11670",
                        null);
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR, result.getStatus());
        System.out.println(result.getOutput());
    }


    @Ignore
    public void webmember_reg(){
        int num = 1;
        for(int i=0;i<=1000;i++){
            regist(num);
            num++;

        }
    }


    @Async
    public void  regist(int num){
        WebMemberDto webMemberDto = new WebMemberDto();
        webMemberDto.setEmail("test_t_e_s_t_"+num);
        System.out.println("注册"+num+"个会员");
        webMemberDto.setEmailBind(false);
        webMemberDto.setId(0);
        webMemberDto.setIpAddress("127.0.0.1");
        webMemberDto.setMemType("QUICK_REGIST");
        webMemberDto.setMobileBind(false);
        webMemberDto.setPhone("");
        webMemberDto.setPwd("E10ADC3949BA59ABBE56E057F20F883E");
        webMemberDto.setRegistChannel(RegistChannel.Website);
        webMemberDto.setRegistTime(new Date());
        WebMemberRegisterReturnDto dto = client.post(webMemberProxy , webMemberDto, WebMemberRegisterReturnDto.class);
        System.out.println(dto.getMessage());
    }





}
