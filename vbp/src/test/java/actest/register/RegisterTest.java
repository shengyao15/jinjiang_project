package actest.register;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.AddressDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.InvoiceDto;
import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.membercenter.MemberResource;
//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class RegisterTest {
	
	@Autowired
	private MemberResource memberResource;
	
	@Test
	public void test_online_noairlinecompany_register() {
		Response res = memberResource.addVIPMembership(mockNoairlinecompanyMemberRegisterDto());
		//Assert.assertTrue(res.getStatus() == Status.OK.getStatusCode());
		CRMResponseDto crmRes = (CRMResponseDto) res.getEntity();
		Assert.assertNull(crmRes);
	}
	
	@Test
	public void test_online_airlinecompany_register() {
		MemberRegisterDto register= mockAirlinecompanyMemberRegisterDto();
		System.out.println(JaxbUtils.convertToXmlString(register));
		Response res = memberResource.addVIPMembership(mockAirlinecompanyMemberRegisterDto());
		Assert.assertTrue(res.getStatus() == Status.OK.getStatusCode());
		CRMResponseDto crmRes = (CRMResponseDto) res.getEntity();
        Assert.assertNotNull(crmRes);
	}
	
	private MemberRegisterDto mockAirlinecompanyMemberRegisterDto() {
		MemberRegisterDto reg = new MemberRegisterDto();
		MemberInfoDto info = mockMemberInfoDto();
		info.setMemberScoreType(MemberScoreType.MILEAGE);
		info.setAirLineCardNo("H634564894");
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
		reg.setCouponCode("541E8W8RLPDO");
		return reg; 
	}
	
	private MemberInfoDto mockMemberInfoDto() {
		MemberInfoDto info = new MemberInfoDto();
		info.setSurname("testScore-tzyq23442pkpD3134343tyyy1s");
		info.setPasssword("E10ADC3949BA59ABBE56E057F20F883Erp34334tyyy1rs");	
		info.setMemberType("JJ Card");
		info.setTitle("Mr.");
		info.setCertificateNo("testScorrezXyymkltyuD3133434tyyy1rs");
		info.setCertificateType("ID");
		info.setEmail("testSc332@pD341334ytyy1sr.com");
		info.setMobile("137615661D3133443ytyy2sr");
		info.setPrimaryAddress(mockPrimaryAddress());
		info.setRegisterSource(RegistChannel.Website);
		return info;
	}
	
	private AddressDto mockPrimaryAddress() {
		AddressDto address = new AddressDto();
		address.setAddress("长乐路pop3oD341343tyyy2sr");
		address.setAllAddress("p233y34tyy上海3市卢湾4区长乐路400号5s03室D3r12");
		address.setAllAddressKey("5052");
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
