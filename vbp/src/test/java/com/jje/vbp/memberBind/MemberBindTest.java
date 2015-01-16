package com.jje.vbp.memberBind;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.bam.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.memberbind.MemberBindDto;
import com.jje.dto.membercenter.memberbind.RegisterMemberBindDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.memberBind.domain.MemberBindEntity;
import com.jje.vbp.memberBind.service.MemberBindRepository;

public class MemberBindTest extends DataPrepareFramework{


    @Autowired
    private MemberBindRepository memberBindRepository;
    
    
    
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Test
    public void memberBindTest() throws Exception {
    	memberBindRepository.bindMember(this.getMemberBindEntity());
    }
    
    @Test
    public void queryBindMemberCountTest() throws Exception {
    	int count = memberBindRepository.queryBindMemberCount( this.getMemberBindEntity() );
    	
    	System.out.println(count);
    }
    
    public MemberBindEntity getMemberBindEntity() {
    	MemberBindEntity memberBindEntity = new MemberBindEntity();
    	memberBindEntity.setMcMemberCode("30690");
		memberBindEntity.setChannel("test");
    	return memberBindEntity;
    }
    
    @Test
    public void testRegisterBind(){
    	JaxbUtils.convertToXmlString(this.getData());
    	
        ResourceInvokeHandler.InvokeResult<Object> result = 
                resourceInvokeHandler.doPost("memberBindResource",MemberBindResource.class,"/memberBind/registerBind",getData(), null);
        
        Assert.assertEquals(Response.Status.OK, result.getStatus());
    }
    

    public RegisterMemberBindDto getData(){
    	RegisterMemberBindDto bean = new RegisterMemberBindDto();
    	MemberBindDto bindDto = new MemberBindDto();
    	MemberRegisterDto registerDto = new MemberRegisterDto();
    	MemberInfoDto info = new MemberInfoDto();
    	
		info.setEmail("aaa1a0008@qq.com");
		info.setPasssword("28CB38D5F26085338");
		info.setCertificateNo("31010620991015000008");
    	info.setCertificateType("ID");
    	info.setSurname("cccdee");
    	info.setRegisterSource(RegistChannel.Website);
//      info.setPartnerName("MASTER");
//      info.setPartnercardNo("56556137135633800642");
//    	info.setMemberType(MemberCardType.JBENEFITCARD.getCode());
    	info.setMobile("9191919191948");
    	
    	bindDto.setChannel("test");
    	bindDto.setBindKey("aaaa");
    	
    	registerDto.setMemberInfoDto(info);
    	
    	bean.setMemberBindDto(bindDto);
    	
    	bean.setMemberRegisterDto(registerDto);
    	
    	return bean;
    }
  
}
