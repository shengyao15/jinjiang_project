package com.jje.membercenter.resource.memberXmlResource;

import java.math.BigDecimal;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberXmlDto;
import com.jje.dto.membercenter.ValidationDto;
import com.jje.dto.payment.PayResultForBizDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.MemberXmlResource;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.xsd.MemberRegisterRequest;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.vbp.handler.CbpHandler;
@Ignore
public class MemberXmlResourceTest extends DataPrepareFramework {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Autowired
	private CRMMembershipRepository crmMembershipRepository;

    @Mock
    private CRMMembershipProxy spyCrmMembershipProxy;
    
    @Mock
	private MemberRepository spyMemberRepository;
    
    @Autowired
    private MemberXmlResource memberXmlResource;

    @Mock
    private CbpHandler spyCbpHandler;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_be_get_xml_success_when_uuid_exist() {
        ResourceInvokeHandler.InvokeResult<MemberXmlDto> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class, "/member/getXml", "1000", MemberXmlDto.class);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals("xml@xml.com", result.getOutput().getEmail());
    }


    @Test
    public void should_update_member_xml_callback_flag_success_when_uuid_not_empty() {
        ResourceInvokeHandler.InvokeResult<String> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class, "/member/updateXmlStatus", "12", String.class);
        Assert.assertEquals("ok", result.getOutput());
    }

    @Test
    public void should_register_login_get_member_success_when_men_name_exists() {
        ResourceInvokeHandler.InvokeResult<MemberDto> result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class, "/member/registerLogin", getValidationDto("13585909081"), MemberDto.class);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals("3", result.getOutput().getMcMemberCode());
        Assert.assertEquals("chenyongne@126.com", result.getOutput().getEmail());
    }

    private ValidationDto getValidationDto(String memName) {
        ValidationDto validation = new ValidationDto();
        validation.setUsernameOrCellphoneOrEmail(memName);
        return validation;
    }

    @SuppressWarnings("rawtypes")
	@Test
    public void should_register_login_get_member_fail_when_men_name_not_exists() {
        ResourceInvokeHandler.InvokeResult result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class, "/member/registerLogin", getValidationDto("notExists@email.com"), null);
        Assert.assertEquals(Response.Status.UNAUTHORIZED, result.getStatus());
    }

    @SuppressWarnings("rawtypes")
	@Test
    public void should_pay_success_when_has_been_payed() {
        ResourceInvokeHandler.InvokeResult result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class, "/member/pay", mockPayResultForBizDto("112233"), String.class);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals(MemberXmlResource.UPDATE_ORDER_SUCCESS, result.getOutput());
    }

    @SuppressWarnings("rawtypes")
	@Test
    public void should_pay_success_when_all_params_exist() throws Exception {
    	//getMemberByMcMemberCode
    	Mockito.when(spyMemberRepository.getMemberByMcMemberCode(Mockito.any(String.class))).thenReturn(mockMember());
        Mockito.when(spyMemberRepository.getMemberByMemberNum(Mockito.any(String.class))).thenReturn(mockMember());

        Mockito.when(spyCrmMembershipProxy.addVIPMembership(Mockito.any(MemberRegisterRequest.class))).thenReturn(mockMemberRegisterResponse());
        Mockito.when(spyCbpHandler.registerIssue(Mockito.any(MemberDto.class))).thenReturn(mockCouponSysIssueResult());
        resourceInvokeHandler.setField(memberXmlResource, "memberRepository", spyMemberRepository);        
        resourceInvokeHandler.setField(crmMembershipRepository, "crmMembershipProxy", spyCrmMembershipProxy);
        resourceInvokeHandler.setField(memberXmlResource, "cbpHandler", spyCbpHandler);
        ResourceInvokeHandler.InvokeResult result = resourceInvokeHandler.doPost("memberXmlResource", MemberXmlResource.class, "/member/pay?uuid=1001", mockPayResultForBizDto("11223344"), String.class);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals(MemberXmlResource.UPDATE_ORDER_SUCCESS, result.getOutput());
        resourceInvokeHandler.resetField(memberXmlResource, "memberRepository", MemberRepository.class);        
        resourceInvokeHandler.resetField(crmMembershipRepository, "crmMembershipProxy", CRMMembershipProxy.class);
        resourceInvokeHandler.resetField(memberXmlResource, "cbpHandler", CbpHandler.class);
    }

    private CouponSysIssueResult mockCouponSysIssueResult() {
        return null;
    }

    private Member mockMember(){
    	Member mem=new Member();
    	mem.setCardNo("cardNo1");
    	mem.setMcMemberCode("mc1");
    	mem.setFullName("fullName1");
    	return mem;
    }

    private MemberRegisterResponse mockMemberRegisterResponse() {
        MemberRegisterResponse memberRegisterResponse = new MemberRegisterResponse();
        MemberRegisterResponse.Body body = new MemberRegisterResponse.Body();
        body.setMembid("D1-20121127");
        body.setRecode("1");
        body.setRemsg("suc");
        memberRegisterResponse.setBody(body);
        return memberRegisterResponse;
    }

    private PayResultForBizDto mockPayResultForBizDto(String orderNo) {
        PayResultForBizDto payResultForBizDto = new PayResultForBizDto();
        payResultForBizDto.setResult(PayResultForBizDto.Result.TRUE);
        payResultForBizDto.setOrderNo(orderNo);
        payResultForBizDto.setPaymentType(PayResultForBizDto.PaymentType.ALIPAY);
        payResultForBizDto.setPayType(PayResultForBizDto.PayType.ONLINE);
        return payResultForBizDto;
    }
}
