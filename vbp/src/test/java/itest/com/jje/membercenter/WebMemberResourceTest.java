package itest.com.jje.membercenter;

import java.util.Random;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.WebMemberDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.WebMemberResource;

public class WebMemberResourceTest extends DataPrepareFramework {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;
    
    @Autowired
	private WebMemberResource webMemberResource;

    @Test
    public void test_regist_success_return_webmemberregisterreturndto() throws Exception {
        ResourceInvokeHandler.InvokeResult<WebMemberRegisterReturnDto> result = resourceInvokeHandler.doPost("webMemberResource", WebMemberResource.class, "/webMember/regist", getWebMemberDto(), WebMemberRegisterReturnDto.class);
        Assert.assertNotNull(result);
    }
    
    @Test
    public void should_regist_success(){
    	WebMemberDto webMemberDto = new WebMemberDto();
    	String mail = System.currentTimeMillis()+"@mail.com.cn";
//    	webMemberDto.setEmail(mail);
    	webMemberDto.setPhone("13523456789");
    	webMemberDto.setPwd("abc123");
    	webMemberDto.setRegistChannel(RegistChannel.CallCenter);
    	
    	Response response = webMemberResource.regist(webMemberDto);
    	
    	org.junit.Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void should_regist_hotel_channel_success(){
    	Random r = new Random();
    	int i = r.nextInt(1000);
    	String s = "xman"+i;
    	
    	WebMemberDto webMemberDto = new WebMemberDto();
    	String mail = s+"@mail.com.cn";
//    	webMemberDto.setEmail(mail);
    	webMemberDto.setPhone(s);
    	webMemberDto.setPwd(s);
    	webMemberDto.setRegistChannel(RegistChannel.CallCenter);
    	webMemberDto.setHotelChannel(s);
    	
    	Response response = webMemberResource.regist(webMemberDto);
    	
    	
    	org.junit.Assert.assertEquals(200, response.getStatus());
    }
    
    private WebMemberDto getWebMemberDto(){
        WebMemberDto dto =new WebMemberDto();
        dto.setIdentityType("Others");
        dto.setIdentityNo("456711111");
        dto.setEmail("urey.jiang@sohu.com");
        dto.setPwd("45678901");
        dto.setPhone("13122666202");
        dto.setUserName("test11");
        dto.setRegistTag("1|2|3|");
        dto.setRegistChannel(RegistChannel.CFT);
        dto.setMemType("vip");

        return dto;
    }
}
