package actest.member;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.CardDto;
import com.jje.dto.membercenter.MemberCardType;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberQuickRegisterDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.crm.CRMConstant;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.xsd.MemberRegisterRequest;
import com.jje.membercenter.xsd.MemberRegisterRequest.Body.Record;
import com.jje.membercenter.xsd.MemberRegisterRequest.Body.Record.Listofcontact;
import com.jje.membercenter.xsd.MemberRegisterRequest.Body.Record.Listofcontact.Contact;
import com.jje.membercenter.xsd.MemberRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress;
import com.jje.membercenter.xsd.MemberRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress.Personaladdress;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.membercenter.xsd.MemberRegisterResponse.Record.Member.Listofcard;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;


public class MemberRegisterTest extends DataPrepareFramework{
	@Mock
	CRMMembershipProxy crmMembershipProxy ;
	
	@Autowired
	CRMMembershipRepository crmMembershipRepository ;
	
	@Autowired
	MemberResource memberResource;
	
    @Autowired
    ResourceInvokeHandler handler;

    @Ignore
	@Test
	public void addVIPMembership() throws Exception
	{
		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyyMMdd-HHmmss");
		String startStr = formatDate.format(new Date());
		System.out.println("***注册接口开始***" + startStr + "******");
		MemberRegisterRequest request = new MemberRegisterRequest();
		MemberRegisterResponse response = new MemberRegisterResponse();
		MemberRegisterRequest.Head head = new MemberRegisterRequest.Head();
		MemberRegisterRequest.Body body = new MemberRegisterRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode(BigInteger.valueOf(Long.parseLong("10009")));
		head.setSystype("JJ000");
		Record record = new Record();
		record.setName("leE301");
		record.setPasswd("E10ADC3949BA59ABBE56E057F20F883E");
		record.setPwdanswer("3434334");
		record.setPwdquestion("Safe001");
		record.setJjcardtype("J2 Benefit Card");
		record.setTier("1");
		record.setInvc("dfdfdf");

        record.setRegichnl(RegistChannel.Mobile.name());
        record.setPartnerflag(CRMConstant.FLAG_FALSE);

		Listofcontact listofcontact = new Listofcontact();
		Contact contact = new Contact();
		contact.setTitle("Mr.");
		contact.setCardtype("Soldier");
		contact.setCardid("555999887");
		contact.setConpriflag("Y");
		contact.setEmail("cbee23ew2@654321.com");
		contact.setEmailbill("Y");
		contact.setEmailepro("Y");
		contact.setEmailinvestigate("Y");
		contact.setEmailpartner("Y");
		contact.setEmailpro("Y");
		contact.setInvflg("Y");
		contact.setMobile("14088492518");
		contact.setPostflg("Y");
		Listofpersonaladdress listofpersonaladdress = new Listofpersonaladdress();
		Personaladdress address = new Personaladdress();
		address.setAddpriflag("Y");
		address.setAddress("1");
		address.setAddrtype("Home Address");
		address.setArea("ABB100");
		address.setCity("1");
		address.setPostcode(BigInteger.valueOf(Long.parseLong("10000")));
//		address.setNation("Chinese");
		address.setProvince("1");
		address.setStreetaddr("中关村1200");
		
		Personaladdress address2 = new Personaladdress();
		address2.setAddpriflag("N");
		address2.setAddress("2");
		address2.setAddrtype("Office Address");
		address2.setArea("ABB100");
		address2.setCity("1");
		address2.setPostcode(BigInteger.valueOf(Long.parseLong("10001")));
//		address2.setNation("Chinese");
		address2.setProvince("1");
		address2.setStreetaddr("三里屯1200");
		
		Personaladdress address3 = new Personaladdress();
		address3.setAddpriflag("N");
		address3.setAddress("2");
		address3.setAddrtype("Receipt Address");
		address3.setArea("ABB100");
		address3.setCity("1");
		address3.setPostcode(BigInteger.valueOf(Long.parseLong("10001")));
//		address3.setNation("Chinese");
		address3.setProvince("1");
		address3.setStreetaddr("三里屯1200");

		listofpersonaladdress.getPersonaladdress().add(address);
		listofpersonaladdress.getPersonaladdress().add(address2);
		listofpersonaladdress.getPersonaladdress().add(address3);
		contact.setListofpersonaladdress(listofpersonaladdress);
		listofcontact.setContact(contact);
		record.setListofcontact(listofcontact);
		body.setRecord(record);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.addVIPMembership(request);
		
		String endStr = formatDate.format(new Date());
		System.out.println("***注册接口结束***" + endStr + "******");
		Assert.assertNotNull(response);
	}
	
	@Ignore
	@Test
	public void updateVIPCardInfo() throws Exception
	{
		SynRightCardRequest request = new SynRightCardRequest();
		SynRightCardResponse response = new SynRightCardResponse();
		SynRightCardRequest.Head head = new SynRightCardRequest.Head();
		SynRightCardRequest.Body body = new SynRightCardRequest.Body();
		head.setReqtime(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		head.setTranscode("30010");
		head.setSystype("JJ000");
		body.setMembid("1-18636602");
		body.setMembcdno("6600002332");
		body.setOptype("升级");
		body.setBuydate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		body.setChannel("yy");
		body.setVipstore("2000");
		body.setSalesperson("lifneng");
		body.setSalesmount("50000");
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.updateVIPCardInfo(request);
		Assert.assertNotNull(response);
	}
	
	@Ignore
	@Test
	public void quickRegisterTest() throws Exception
	{
		MemberQuickRegisterDto dto = new MemberQuickRegisterDto();
		dto.setName("abcD");
		dto.setPassword("1243");
		dto.setMobile("1234323424234");
		dto.setEmail("dfaB@163.com");
		dto.setPostflg(true);
		dto.setCertificateNo("420912198809220945");
		dto.setCertificateType("身份证");
		dto.setProvince("北京");
		dto.setCity("海淀区");
		dto.setStreetaddr("djajfd");
		dto.setTitle("先生");
		dto.setPostcode("10000");
		dto.setArea("changGlu400");
		CRMResponseDto crmDto = crmMembershipRepository.quickRegister(dto);
		Assert.assertNotNull(crmDto);
	}
	
	@Ignore
	@Test
	public void quickRegisterMemberPassTest() {
		MemberQuickRegisterDto memberQuickRegisterDto = new MemberQuickRegisterDto(
				"Jerry", "先生", "13901010101", "Jerry@dell.com", "123456",
				"身份证", "123456789009876543", true, "", "", "", "", "10000");
		Response response = memberResource
				.quickRegister(memberQuickRegisterDto);
		CRMResponseDto crmRespDto = (CRMResponseDto) response.getEntity();
		Assert.assertEquals("1", crmRespDto.getRetcode());
	}
	
	@Test
	@Ignore
	public void queryCardStautsFailTest() throws Exception {
		String memberId = "0024491";
		CardDto cardDto = crmMembershipRepository.queryCardStauts(memberId);
		Assert.assertNotNull(cardDto);
	}
	
	
	@Ignore
	@Test
	public void queryCardStautsPassTest() throws Exception {
		String memberId = "0024491";
		CardDto cardDto = crmMembershipRepository.queryCardStauts(memberId);
		Assert.assertNotNull(cardDto);
	}
	
	@Test
	public void newMemberRegist() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(crmMembershipProxy.addVIPMembership(Mockito.any(MemberRegisterRequest.class))).thenReturn(mockMemberRegisterResponse());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(crmMembershipRepository), "crmMembershipProxy", crmMembershipProxy);
      //  ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberResource), "crmMembershipRepository", crmMembershipRepository);
        ResourceInvokeHandler.InvokeResult<CRMResponseDto> postResult = handler.doPost("memberResource", MemberResource.class, "/member/addVIPMembership", mockdto(), CRMResponseDto.class);
        Assert.assertEquals(Response.Status.UNAUTHORIZED, postResult.getStatus());
	}
	
	MemberRegisterDto mockdto(){
		MemberRegisterDto dto = new MemberRegisterDto();
		MemberInfoDto info = new MemberInfoDto();
		info.setEmail("aaa1a@qq.com");
		info.setPasssword("28CB38D5F2608536");
		info.setCertificateNo("310106209910150098");
    	info.setCertificateType("ID");
    	info.setSurname("ccc");
        info.setPartnerName("MASTER");
        info.setPartnercardNo("40137135633800642");
    	info.setMemberType(MemberCardType.JBENEFITCARD.getCode());
    	dto.setMemberInfoDto(info);
		return dto;
	}

	MemberRegisterResponse mockMemberRegisterResponse(){
		MemberRegisterResponse response = new MemberRegisterResponse();
		MemberRegisterResponse.Body body = new MemberRegisterResponse.Body();
        MemberRegisterResponse.Record record = new MemberRegisterResponse.Record();
        MemberRegisterResponse.Record.Member member = new MemberRegisterResponse.Record.Member();
        member.setFullName("CCC");
		body.setRecode("00001");
        member.setRegisterSource("WEB");
        body.setMembid("541811");
        member.setMemberCode("11111");
        member.setMemberHierarchy("3");
        member.setListofcard(new Listofcard());
        member.setStatus("Actvited");
        member.setCardLevel("1");

        record.setMember(member);
        body.setRecord(record);
		response.setBody(body);
		return response;
	}
	
	
}
