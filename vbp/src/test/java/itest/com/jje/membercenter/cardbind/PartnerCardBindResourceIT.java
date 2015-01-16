package itest.com.jje.membercenter.cardbind;


import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.cardbind.CardBindStatus;
import com.jje.dto.membercenter.cardbind.CardBindStatusDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import junit.framework.Assert;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;



public class PartnerCardBindResourceIT extends DataPrepareFramework{

	@Autowired
	private VirtualDispatcherService virtualDispatcherService;


     PartnerCardBindDto partnerCardBindDto_OK;

    PartnerCardBindDto partnerCardBindDto_FAIL;

     @Before
    public void initMockPartnerCardBindDto_OK(){
        partnerCardBindDto_OK = new PartnerCardBindDto();
        partnerCardBindDto_OK.setMcMemberCode("3");
        partnerCardBindDto_OK.setPartnerCardNo("7000005582");
        partnerCardBindDto_OK.setPartnerCode(MemberAirLineCompany.ACDH);
        partnerCardBindDto_OK.setPartnerFlag(MemberScoreType.MILEAGE);
    }

    @Before
     public void initMockPartnerCardBindDto_ERROR(){
        partnerCardBindDto_FAIL = new PartnerCardBindDto();
        partnerCardBindDto_FAIL.setMcMemberCode("14222");
        partnerCardBindDto_FAIL.setPartnerCardNo("6000005222");
        partnerCardBindDto_FAIL.setPartnerCode(MemberAirLineCompany.ACDH);
        partnerCardBindDto_FAIL.setPartnerFlag(MemberScoreType.SCORE);
    }

	@Test
	public void test_partnerCardBind_SUCCESS() throws Exception {
		 try {
             MockHttpResponse response = mockRemoteResource(partnerCardBindDto_OK);
             Assert.assertEquals("response stauts is ok", response.getStatus(), Response.Status.OK.getStatusCode());
             System.out.println("======response==xml=="+ response.getContentAsString()+"====");
             Assert.assertNotNull("response xml is not null", response.getContentAsString());
             CardBindStatusDto statusDto = JaxbUtils.convertToObject(response.getContentAsString(), CardBindStatusDto.class);
             Assert.assertNotNull("response entity is not null:", statusDto);
             org.junit.Assert.assertEquals("status alias is 0(SUCCESS)", statusDto.getStatus(), CardBindStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
	public void test_partnerCardBind_FAIL() throws Exception {

		 try {
             MockHttpResponse response = mockRemoteResource(partnerCardBindDto_FAIL);
             Assert.assertEquals("response stauts is ok", response.getStatus(), Response.Status.OK.getStatusCode());
             System.out.println("======response==xml=="+ response.getContentAsString()+"====");
             Assert.assertNotNull("response xml is not null", response.getContentAsString());
             CardBindStatusDto statusDto = JaxbUtils.convertToObject(response.getContentAsString(), CardBindStatusDto.class);
             Assert.assertNotNull("response entity is not null:", statusDto);
             org.junit.Assert.assertEquals("status alias is -1(FAIL)", CardBindStatus.FAIL, statusDto.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private MockHttpResponse mockRemoteResource(PartnerCardBindDto cardBindDto) throws URISyntaxException, UnsupportedEncodingException {
        String content = JaxbUtils.convertToXmlString(cardBindDto);
        System.out.println("xml :"+content + "=====");
        MockHttpRequest request = MockHttpRequest.post("/member/partnerCardBind");
        request.content(content.getBytes("UTF-8"));
        request.contentType(MediaType.APPLICATION_XML);
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("memberResource", MemberResource.class).invoke(request, response);
        return response;
    }

}
