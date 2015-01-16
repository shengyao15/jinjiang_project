package itest.com.jje.membercenter.mobilemember;

import javax.ws.rs.core.MediaType;

import com.jje.dto.membercenter.*;
import junit.framework.Assert;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.membercenter.MemberResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class RegisterIT {
	
	@Autowired
	private VirtualDispatcherService virtualDispatcherService;
	
	@Test
	public void register() {
		try {
			String content = JaxbUtils.convertToXmlString(mockAirlinecompanyMemberRegisterDto());
			System.out.println(content);
			MockHttpRequest request = MockHttpRequest.post("/member/addVIPMembership");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			virtualDispatcherService.getDispatcher("memberResource", MemberResource.class).invoke(request, response);
			CRMResponseDto crm = JaxbUtils.convertToObject(response.getContentAsString(), CRMResponseDto.class);
			Assert.assertTrue(crm.getRetcode().equals("1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    @Test
	public void registerAddXiangMember() {
		try {
			String content = JaxbUtils.convertToXmlString(mockXiangMemberRegisterDto());
			System.out.println(content);
			MockHttpRequest request = MockHttpRequest.post("/member/addVIPMembership");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			virtualDispatcherService.getDispatcher("memberResource", MemberResource.class).invoke(request, response);
			CRMResponseDto crm = JaxbUtils.convertToObject(response.getContentAsString(), CRMResponseDto.class);
			Assert.assertTrue(crm.getRetcode().equals("1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private MemberRegisterDto mockXiangMemberRegisterDto() {
        MemberRegisterDto reg = new MemberRegisterDto();
		MemberInfoDto info = mockMemberInfoDto();
		info.setMemberScoreType(MemberScoreType.MILEAGE);
		info.setAirLineCardNo("H12304564899");
		info.setAirLineCompany(MemberAirLineCompany.ACDH);
		reg.setMemberInfoDto(info);
		reg.setInvoice(mockInvoiceDto());
		reg.setNeedMailCard(true);
		reg.setNeedMailInvoice(false);
        info.setRegisterSource(RegistChannel.Ikamobile);
		return reg;
    }

    private MemberRegisterDto mockAirlinecompanyMemberRegisterDto() {
		MemberRegisterDto reg = new MemberRegisterDto();
		MemberInfoDto info = mockMemberInfoDto();
		info.setMemberScoreType(MemberScoreType.MILEAGE);
		info.setAirLineCardNo("H12304564899");
		info.setAirLineCompany(MemberAirLineCompany.ACDH);
		reg.setMemberInfoDto(info);
		reg.setInvoice(mockInvoiceDto());
		reg.setNeedMailCard(true);
		reg.setNeedMailInvoice(false);
		return reg; 
	}

	private MemberRegisterDto mockNoairlinecompanyMemberRegisterDto() {
		MemberRegisterDto reg = new MemberRegisterDto();
		reg.setMemberInfoDto(mockMemberInfoDto());
		reg.setInvoice(mockInvoiceDto());
		reg.setNeedMailCard(true);
		reg.setNeedMailInvoice(false);
		return reg; 
	}
	
	private MemberInfoDto mockMemberInfoDto() {
		MemberInfoDto info = new MemberInfoDto();
		info.setSurname("memberinfosurname12");
		info.setPasssword("memberinfopassword12");
		info.setRemindQuestion("Safe001");
		info.setRemindAnswer("321");
		info.setMemberType("JJ Card");
		info.setTitle("Mr.");
		info.setCertificateNo("330002312412350");
		info.setCertificateType("ID");
		info.setEmail("aaa12345@163.com");
		info.setMobile("15026988880");
		info.setPrimaryAddress(mockPrimaryAddress());
		return info;
	}
	
	private AddressDto mockPrimaryAddress() {
		AddressDto address = new AddressDto();
		address.setAddress("长乐路123");
		address.setAllAddress("上海市卢湾区长乐路400号503室");
		address.setAllAddressKey("505");
		address.setCityId("1000");
		address.setDistrictId("2000");
		address.setProvinceId("3000");
		address.setEditable(true);
		address.setName("无");
		address.setPostcode("200000");
		address.setPriFlag("N");
		address.setType("1");
		return address;
	}

	private InvoiceDto mockInvoiceDto() {
		InvoiceDto invoice = new InvoiceDto();
		invoice.setAmount(200.00F);
		invoice.setItem("会员注册费用");
		invoice.setTitle("江锦");
		return invoice;
	}

}
