package com.jje.membercenter.resource.memberResource;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.MD5Utils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.common.utils.RestClient;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.StatusDto;
import com.jje.dto.membercenter.AddressDto;
import com.jje.dto.membercenter.BaseDataDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.ContactDto;
import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.MemberCardType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberInfoAnswerDto;
import com.jje.dto.membercenter.PersonaladdressDto;
import com.jje.dto.membercenter.QuickRegisterDto;
import com.jje.dto.membercenter.QuickRegisterRecordDto;
import com.jje.dto.membercenter.Request;
import com.jje.dto.membercenter.RequestHeadDto;
import com.jje.dto.membercenter.SMSValidationDto;
import com.jje.dto.membercenter.ValidateCodeDto;
import com.jje.dto.membercenter.ValidationDto;
import com.jje.dto.vbp.sns.MemberContactsnsDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.persistence.MemberMapper;
import com.jje.membercenter.service.MemberService;

@Transactional
public class MemberResourceTest extends DataPrepareFramework {

    @Autowired
    private VirtualDispatcherService virtualDispatcherService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Test
    public void keyInfoCheckTest() {
    	String s = "123456";
        InvokeResult<String> result1 = resourceInvokeHandler.doGet("memberResource", MemberResource.class,
				"/member/keyInfoCheck/card_no/"+s, String.class);
        InvokeResult<String> result2 = resourceInvokeHandler.doGet("memberResource", MemberResource.class,
				"/member/keyInfoCheck/identity_no/"+s, String.class);
        InvokeResult<String> result3 = resourceInvokeHandler.doGet("memberResource", MemberResource.class,
				"/member/keyInfoCheck/phone/"+s, String.class);
        InvokeResult<String> result4 = resourceInvokeHandler.doGet("memberResource", MemberResource.class,
				"/member/keyInfoCheck/email/"+s+"@qq.com", String.class);
        
        Assert.assertEquals("OK",result1.getOutput());
        Assert.assertEquals("OK",result2.getOutput());
        Assert.assertEquals("OK",result3.getOutput());
        Assert.assertEquals("OK",result4.getOutput());
    }
    
    @Test
    public void sendValidateCodeOperationTest() {
        InvokeResult<String> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/sendValidateCode", getSMSValidationDto(), String.class);
        InvokeResult<String> result1 = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/SMSRegisterValidation", getSMSValidationDto(), String.class);
        System.out.println(result.getOutput());
        System.out.println(result1.getOutput());
    }
    
    public SMSValidationDto getSMSValidationDto() {
    	SMSValidationDto sMSValidationDto = new SMSValidationDto();
    	sMSValidationDto.setIpAddress("192.168.1.1");
    	sMSValidationDto.setPhone("112");
    	sMSValidationDto.setValidationCode("1001");
    	
    	return sMSValidationDto;
    }
    
    @Test
    public void sendValidateCodeWhenBindPhoneTest() {
        InvokeResult<String> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/sendValidateCodeWhenBindPhone", getValidationDto(), String.class);
        System.out.println(result.getOutput());
    }
    
    public SMSValidationDto getValidationDto() {
    	SMSValidationDto sMSValidationDto = new SMSValidationDto();
    	sMSValidationDto.setPhone("151179");
    	sMSValidationDto.setValidationCode("1001");
    	sMSValidationDto.setMemberInfoId("1111");
    	
    	return sMSValidationDto;
    }

    @Test
    public void queryWebMemberTest() throws Exception {
        InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/validate", getWebMemberValidationDto(), MemberDto.class);
        MemberDto member = result.getOutput();
		Assert.assertEquals(true,member.getIsWebMember());
        Assert.assertEquals("张三",member.getFullName());
        Assert.assertEquals("1383838238",member.getCellPhone());
        Assert.assertEquals("xiaoJJ@126.com",member.getEmail());
    }

    private ValidationDto getWebMemberValidationDto() {
        ValidationDto dto = new ValidationDto();
        dto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        dto.setUsernameOrCellphoneOrEmail("16578656666");
        return dto;
    }
    
    @Test
    public void queryWebMemberByVIPMemberTest() throws Exception {
        InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/validate", getVipMemberValidationDto(), MemberDto.class);
        Assert.assertEquals(false,result.getOutput().getIsWebMember());
        Assert.assertEquals("张三",result.getOutput().getFullName());
        Assert.assertEquals("zhangsan@126.com",result.getOutput().getEmail());
    }

    private Object getVipMemberValidationDto() {
    	ValidationDto dto = new ValidationDto();
        dto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        dto.setCollaborateCard(MemberAirLineCompany.ACDH);
        dto.setUsernameOrCellphoneOrEmail("100000");
        return dto;
	}

	@Test
    public void queryMemberTest() throws Exception {
    	InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberResource", MemberResource.class,
				"/member/validate", getMemberValidationDto(), MemberDto.class);
        Assert.assertEquals(false,result.getOutput().getIsWebMember());
    }

    private ValidationDto getMemberValidationDto() {
        ValidationDto dto = new ValidationDto();
        dto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        dto.setUsernameOrCellphoneOrEmail("chenyongne@126.com");
        return dto;
    }


    /*
      * @Value(value = "${crm.url}") private String CRMUrl;
      */

    @Autowired
    MemberResource memberResource;

    @Autowired
    CRMMembershipRepository crmMembershipRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private RestClient client;

    @Autowired
    MemberCardOrderRepository memberCardOrderRepository;


    @Test
    public void queryMemberBasicInfoTest() {
    	MemberBasicInfoDto basicInfoDto = crmMembershipRepository.queryMemberBaseInfo("1-72FH4R");
    	Assert.assertNotNull(basicInfoDto);
    }
    
    @Test
    public void updateQuickMemberBasicInfoTest() {
    	MemberBasicInfoDto baseInfo =  new MemberBasicInfoDto();
    	baseInfo.setMemberId("1-6LT0JE");
    	baseInfo.setLastName("test");
    	baseInfo.setCdno("21011971061922");
    	baseInfo.setCdtype("ID");
    	
    	MemberBasicInfoDto basicInfoDto = crmMembershipRepository.queryMemberBaseInfo("1-72FH4R");
    	Assert.assertNotNull(basicInfoDto);
    }
    
    @Test
    public void selectTest() {
        Member a = memberRepository.queryByUsernameOrCellphoneOrEmail("13585909080");
        //Assert.assertNotNull(a);
    }

    @Test
    public void valildateIdentifyTest() {
        Member b = new Member();
        b.setIdentityNo("55555");
        b.setIdentityType("Others");
        List<Member> c = memberRepository.queryIdentifyInfo(b);
        Member a = c.get(0);
        String u = a.getUserName();
        String p = a.getPassword();
        Assert.assertNotNull(a);
    }

    @Test
    public void valildateMailOrTelTest() {
        List<MemberVerfy> a = memberRepository.queryRegisterByCellphoneOrEmailOrCardNo("13585909080");
        Assert.assertNotNull(a);
    }


    @Test
    public void validateMemberPassTest() {
        ValidationDto validationDto = new ValidationDto();
        validationDto.setUsernameOrCellphoneOrEmail("tangxin2000@hotmail.com");
        // validationDto.setPassword(MD5Utils.generatePassword("123456"));
        validationDto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        Response response = memberResource.validate(validationDto);
        MemberDto memberDto = (MemberDto) response.getEntity();
        Assert.assertNotNull(memberDto);
    }


    @Test
    public void getMemberCardNo() {
        ValidationDto validationDto = new ValidationDto();
        validationDto.setUsernameOrCellphoneOrEmail("333333");
        // validationDto.setPassword(MD5Utils.generatePassword("123456"));
        validationDto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        Response response = memberResource.getMemberCardNo("333333");
        ResultMemberDto<MemberDto> memberDto = (ResultMemberDto) response.getEntity();
        Assert.assertNotNull(memberDto);
    }


    @Test
    public void validateMemberFailTest() {
        ValidationDto loginDto = new ValidationDto();
        loginDto.setUsernameOrCellphoneOrEmail("1-3283011");
        loginDto.setPassword("1234567");
        Response response = memberResource.validate(loginDto);
        MemberDto memberDto = (MemberDto) response.getEntity();
        Assert.assertNull(memberDto);
    }


    /*
      * @Test public void registerMemberPassTest() { Request<QuickRegisterDto>
      * request = this.createMemberRequestData();
      * //com.jje.dto.membercenter.Response response = client.put("CRMUrl",
      * request, com.jje.dto.membercenter.Response.class); Response response =
      * memberResource.addMember(request); }
      */

    public Request<QuickRegisterDto> createMemberRequestData() {
        PersonaladdressDto personaladdress = new PersonaladdressDto("Y",
                "湖南省长沙市高新区", "410205");
        List<PersonaladdressDto> listofpersonaladdress = new ArrayList<PersonaladdressDto>();
        listofpersonaladdress.add(personaladdress);
        ContactDto contact = new ContactDto("Y", "先生", "Jerry_Z@dell.com",
                "12345678901", "身份证", "", "Y", listofpersonaladdress);
        List<ContactDto> listofcontact = new ArrayList<ContactDto>();
        listofcontact.add(contact);
        QuickRegisterRecordDto record = new QuickRegisterRecordDto("Jerry",
                "123456", listofcontact);
        List<QuickRegisterRecordDto> listofrecord = new ArrayList<QuickRegisterRecordDto>();
        listofrecord.add(record);
        QuickRegisterDto body = new QuickRegisterDto();
        body.setRecord(listofrecord);
        RequestHeadDto head = new RequestHeadDto("10004",
                new Date().toString(), "JJ000");
        Request<QuickRegisterDto> request = new Request<QuickRegisterDto>();
        request.setHead(head);
        request.setBody(body);
        return request;
    }


    // zz start
    @Test
    public void insertOrderTest() {
        MemberCardOrderDto testOrder = new MemberCardOrderDto();
        testOrder.setId(12l);
        testOrder.setCardNo("111");
        testOrder.setCreateTime(new Date());
        testOrder.setMcMemberCode("mcMemberCode");
        testOrder.setCurrentLevel("J Benefit Card");
        testOrder.setOrderTime(new Date());
        testOrder.setPayStatus(new Integer(2));
        testOrder.setStatus(new Integer(2));
        testOrder.setOrderType("MEMCARD");
        testOrder.setAmount(new BigDecimal(0.01));
        /*
           * 1,
           * 'A0FDP1025','2011-11-3','2011-11-3','M0FDP1025','C0FDP1025',1,'普通会员','高级会员',200.00,'2011-11-3','P0FDP1025','ONLINE','ALIPAY','B001',1,1
           */
        Response response = memberResource.insertOrder(testOrder);
        StatusDto result = (StatusDto) response.getEntity();

        Assert.assertNotNull(result);
    }

    @Test
    public void listMemberTypeTest() {

        Response response = memberResource.listMemberType();
        ResultMemberDto<BaseDataDto> result = (ResultMemberDto<BaseDataDto>) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    @Test
    public void listCertificateTypesTest() {

        Response response = memberResource.listCertificateTypes();
        ResultMemberDto<BaseDataDto> result = (ResultMemberDto<BaseDataDto>) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    @Test
    public void listAddressTypesTest() {

        Response response = memberResource.listAddressTypes();
        ResultMemberDto<BaseDataDto> result = (ResultMemberDto<BaseDataDto>) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    @Test
    public void listTitleTypesTest() {

        Response response = memberResource.listTitleTypes();
        ResultMemberDto<BaseDataDto> result = (ResultMemberDto<BaseDataDto>) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    @Test
    public void listProvinceTypesTest() {

        Response response = memberResource.listProvinceTypes();
        ResultMemberDto<BaseDataDto> result = (ResultMemberDto<BaseDataDto>) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    @Test
    public void listCityTypesTest() {
        BaseDataDto a = new BaseDataDto();
        a.setName("北京");
        Response response = memberResource.listCityTypes(a);
        ResultMemberDto<BaseDataDto> result = (ResultMemberDto<BaseDataDto>) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    @Test
    public void listTownTypesTest() {
        BaseDataDto a = new BaseDataDto();
        a.setParRowId("1-167-3");
        Response response = memberResource.listTownTypes(a);
        ResultMemberDto<BaseDataDto> result = (ResultMemberDto<BaseDataDto>) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    @Test
    public void validateEmailOrTelTest() {

        Response response = memberResource.validateEamilOrTel("150000384384");
        StatusDto statusDto = (StatusDto) response.getEntity();
        Assert.assertNotNull(statusDto);
    }

    @Test
    public void getAddressValuesTest() {
        AddressDto nameDto = new AddressDto();
        nameDto.setProvinceId("北京市");
        nameDto.setCityId("北京");
        nameDto.setDistrictId("东城区");
        Response response = memberResource.getAddressValues(nameDto);
        AddressDto result = (AddressDto) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    @Test
    public void getPriceTest() {
        Response response = memberResource.getPrice("J Benefit Card");
        ResultMemberDto<BaseDataDto> result = (ResultMemberDto<BaseDataDto>) response
                .getEntity();
        Assert.assertNotNull(result);
    }

    // zz end


    @Test
    public void query_member_by_memId_succeed() throws Exception {
        MockHttpRequest request = MockHttpRequest.post("/member/queryMemberByCode/4");
        request.contentType(MediaType.APPLICATION_XML);
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("memberResource", MemberResource.class).invoke(request, response);
        MemberInfoAnswerDto result = JaxbUtils.convertToObject(response.getContentAsString(), MemberInfoAnswerDto.class);
        Assert.assertNotNull(result);
    }

    @Test
    public void should_query_member_by_email() {
        String email = "chenyongne@126.com";
        Response response = memberResource.queryByEmail(email);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        MemberDto memberDto = (MemberDto) response.getEntity();
        Assert.assertThat("MemberDto is not null", memberDto, IsNull.notNullValue());
        Assert.assertThat("check memberDto isWebMember", memberDto.getIsWebMember(), Is.is(false));
        Assert.assertThat("check queryByEmail right", memberDto.getEmail(), Is.is(email));
    }

    @Test
    public void should_query_web_member_by_email() {
        String email = "test_web@jinjiang.com";
        Response response = memberResource.queryByEmail(email);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        MemberDto memberDto = (MemberDto) response.getEntity();
        Assert.assertThat("MemberDto is not null", memberDto, IsNull.notNullValue());
        Assert.assertThat("check memberDto isWebMember", memberDto.getIsWebMember(), Is.is(true));
        Assert.assertThat("check queryByEmail", memberDto.getEmail(), Is.is(email));
    }

    @Test
    public void should_validate_phone_success() {
        String phone = "18221094798";
        Response response = memberResource.validatePhone(phone);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        StatusDto statusDto = (StatusDto) response.getEntity();
        Assert.assertThat("check validatePhone isExcSuccess", statusDto.isExcSuccess(), Is.is(true));
        Assert.assertThat("check validatePhone isExist", statusDto.isExistFlag(), Is.is(true));
    }

    @Test
    public void should_validate_web_member_phone_success() {
        String phone = "18221095790";
        Response response = memberResource.validatePhone(phone);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        StatusDto statusDto = (StatusDto) response.getEntity();
        Assert.assertThat("check validatePhone isExcSuccess", statusDto.isExcSuccess(), Is.is(true));
        Assert.assertThat("check validatePhone isExist", statusDto.isExistFlag(), Is.is(true));
    }

    @Test
    public void should_update_member_psw_by_phone() {
        MemberDto memberDto = new MemberDto();
        memberDto.setCellPhone("19002857572");
        memberDto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        Response response = memberResource.updatePwdByEamilOrTel(memberDto);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        StatusDto statusDto = (StatusDto) response.getEntity();
        Assert.assertThat("check validatePhone isExcSuccess", statusDto.isExcSuccess(), Is.is(true));
        Assert.assertThat("check validatePhone isExist", statusDto.isExistFlag(), Is.is(true));
    }

    @Test
    public void should_update_web_member_psw_by_phone() {
        MemberDto memberDto = new MemberDto();
        memberDto.setCellPhone("1383838238");
        memberDto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        Response response = memberResource.updatePwdByEamilOrTel(memberDto);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        StatusDto statusDto = (StatusDto) response.getEntity();
        Assert.assertThat("check validatePhone isExcSuccess", statusDto.isExcSuccess(), Is.is(true));
        Assert.assertThat("check validatePhone isExist", statusDto.isExistFlag(), Is.is(true));
    }

    @Test
    public void should_modify_web_member_psw() {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(4);
        memberDto.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
        memberDto.setIsWebMember(true);
        Response response = memberResource.modifyPwd(memberDto);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        CRMResponseDto responseDto = (CRMResponseDto) response.getEntity();
        Assert.assertThat("check responseDto is success", responseDto.getRetcode(), Is.is("1"));
    }

    @Test
    public void testGenerateMcMemberCode() {
        Assert.assertNotNull(memberMapper.generateMcMemberCode());
    }

    @Test
    public void should_get_memberCode_by_McMemberCode_success() {
        String mcMemberCode = "3";
        String memberCode = memberService.getMemberCodeByMcMemberCode(mcMemberCode);
        Assert.assertThat("memberId is 1-19056406", memberCode, Is.is("1-19056406"));

    }

    @Test
    public void should_get_member_by_MemberID_success() {
        String memberID = "1-EZ4C9";
        Response response = memberResource.getMcMemberCodeByMemberID(memberID);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        String mcMemberCode = (String) response.getEntity();
        Assert.assertThat("check mcMemberCode", mcMemberCode, Is.is("3"));
    }

    @Test
    public void should_get_member_by_MemberNum_success() {
        String memberNum = "1-19056406";
        Response response = memberResource.getMemberByMemberNum(memberNum);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        MemberDto memberDto = (MemberDto) response.getEntity();
        Assert.assertThat("check mcMemberCode", memberDto.getMemberCode(), Is.is(memberNum));
        Assert.assertThat("check isWebMember", memberDto.getIsWebMember(), Is.is(false));
    }


    @Test
    public void should_get_member_by_McMemberCode_success() {
        String mcMemberCode = "3";
        Response response = memberResource.getMemberByMcMemberCode(mcMemberCode);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        MemberDto memberDto = (MemberDto) response.getEntity();
        Assert.assertThat("check mcMemberCode", memberDto.getMcMemberCode(), Is.is(mcMemberCode));
        Assert.assertThat("check isWebMember", memberDto.getIsWebMember(), Is.is(false));
    }

    @Test
    public void should_get_webMember_by_McMemberCode_success() {
        String mcMemberCode = "3231663100";
        Response response = memberResource.getMemberByMcMemberCode(mcMemberCode);
        Assert.assertThat("Response status is 200", response.getStatus(), Is.is(200));
        MemberDto memberDto = (MemberDto) response.getEntity();
        Assert.assertThat("check mcMemberCode", memberDto.getMcMemberCode(), Is.is(mcMemberCode));
        Assert.assertThat("check isWebMember", memberDto.getIsWebMember(), Is.is(true));
    }

    @Test
    public void should_get_web_Member_by_McMemberCode_success() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/member/getMemberByMcMemberCode/3231663100");
        request.contentType(MediaType.APPLICATION_XML);
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("memberResource", MemberResource.class).invoke(request, response);
        MemberDto result = JaxbUtils.convertToObject(response.getContentAsString(), MemberDto.class);
        Assert.assertNotNull(result);   
    }
    
    
    @Test
    public void should_member_by_cardNo_exist() throws Exception {
        MemberDto result = sendResourceForGetMemberByCardNo("9157863308");
        assertMemberExist(result);
    }

    @Test
    public void should_webMember_by_cardNo_exist() throws Exception {
        MemberDto result = sendResourceForGetMemberByCardNo("10086");
        assertWebMemberExist(result);
    }

    private void assertWebMemberExist(MemberDto result) {
        Assert.assertThat(result, IsNull.<Object>notNullValue());
        Assert.assertThat(result, Is.is(MemberDto.class));
        Assert.assertThat(result.getMcMemberCode(), Is.is("10086"));
        Assert.assertThat(result.getCardNo(), Is.is("10086"));
        Assert.assertThat(result.getEmail(), Is.is("10086@126.com"));
        Assert.assertThat(result.getCellPhone(), Is.is("10086"));
        org.junit.Assert.assertThat(result.getMemType(), Is.is("QUICK_REGIST"));
    }

    private MemberDto sendResourceForGetMemberByCardNo(String cardNo) throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/member/getMemberByCardNo/"+cardNo);
        request.contentType(MediaType.APPLICATION_XML);
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("memberResource", MemberResource.class).invoke(request, response);
        return JaxbUtils.convertToObject(response.getContentAsString(), MemberDto.class);
    }

    private void assertMemberExist(MemberDto result) {
    	 Assert.assertThat(result, IsNull.<Object>notNullValue());
         Assert.assertThat(result, Is.is(MemberDto.class));
         Assert.assertThat(result.getMcMemberCode(), Is.is("3231663100"));
         Assert.assertThat(result.getMemberID(), Is.is("9157863308"));
         Assert.assertThat(result.getCardNo(), Is.is("9157863308"));
         Assert.assertThat(result.getEmail(), Is.is("xiaoJJ@126.com"));
         Assert.assertThat(result.getCellPhone(), Is.is("1383838238"));
         Assert.assertThat(result.getCardType(), Is.is(MemberCardType.JJCARD.getCode()));
    }
    
    @Test
    public void insertValidateCodeTest() {
    	ValidateCodeDto dto = new ValidateCodeDto();
        dto.setCode("abcdefg");
        dto.setReceiver("mail@mail.com");
        Response response = memberResource.addValidateCode(dto);

        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void checkValidateCodeFalseTest() {
    	ValidateCodeDto dto = new ValidateCodeDto();
        dto.setCode("ABCD");
        dto.setReceiver("110");
        Response response = memberResource.checkValidateCode(dto);

        Assert.assertEquals(401, response.getStatus());
    }
    
    @Test
    public void checkValidateCodeTrueTest() {
    	ValidateCodeDto dto = new ValidateCodeDto();
        dto.setCode("1234");
        dto.setReceiver("110");
        Response response = memberResource.checkValidateCode(dto);

        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testBindPhone(){
    	MemberDto dto = new MemberDto();
    	dto.setMemberID("1-6IRFQY");
    	dto.setActivateCode("Email Activiated");
    	
    	Response response = memberResource.bind(dto);
        Assert.assertEquals(200, response.getStatus());
    	
    }
    
    @Test
    public void testBindEmail(){
    	MemberDto dto = new MemberDto();
    	dto.setMemberID("1-6IRFQY");
    	dto.setActivateCode("Mobile Activiated");
    	
    	Response response = memberResource.bindEmail(dto);
        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testActive(){
    	MemberBasicInfoDto baseInfo = new MemberBasicInfoDto();
		baseInfo.setCell("1376430975511");
		baseInfo.setPassword(MD5Utils.generatePassword("123456"));
		baseInfo.setMemberId("0000032");
		
		Response response = memberResource.active(baseInfo);
		Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testBindSns(){
    	MemberContactsnsDto dto = new MemberContactsnsDto();
    	dto.setBindChannel("Website");
    	dto.setComments("test");
    	dto.setMemberId("1-6IRFQY");
    	dto.setSns("546524812");
    	dto.setUseType("QQ");
    	System.out.println(JaxbUtils.convertToXmlString(dto));
    	Response response = memberResource.bindSns(dto);
		Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testUnBindSns(){
    	MemberContactsnsDto dto = new MemberContactsnsDto();
    	dto.setBindChannel("Website");
    	dto.setComments("test");
    	dto.setMemberId("1-6IRFQY");
    	dto.setSns("546524812");
    	dto.setUseType("QQ");
    	
    	Response response = memberResource.unBindSns(dto);
		Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testQueryMemberPartnerCards(){
    	memberResource.getPartnerCard("1-18UNJI");
    }
}
