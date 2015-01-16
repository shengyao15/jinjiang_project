package com.jje.membercenter.forcrm.memberCenterInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.TransformType;
import com.jje.dto.membercenter.forcrm.MemberForCRMReqDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMRespDto;
import com.jje.dto.mms.mmsmanage.MessageRespDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.forcrm.MemberCenterInterface;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.remote.handler.MBPHandler;
import com.jje.membercenter.service.MemberService;
import com.jje.vbp.handler.CbpHandler;

/**
 * 门店注册接口(1，注册全新会员 2，迁移会员)
 */
public class AddMemberTest extends DataPrepareFramework {

    @Mock
    private CbpHandler spyCbpHandler;

    @Autowired
    private CbpHandler cbpHandler;
    
    @Mock
    private MBPHandler spyMbpHandler;

    @Autowired
    private MBPHandler mbpHandler;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private WebMemberRepository webMemberRepository;

    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(spyCbpHandler.registerIssue(Mockito.any(MemberDto.class)))
                .thenReturn(new CouponSysIssueResult());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService), "cbpHandler",
                spyCbpHandler);
   //     Mockito.when(spyMbpHandler.sendPasswordForStoreRegister(Mockito.any(MBPHandler.TemplateType.class),Mockito.any(MemberDto.class)))
       //         .thenReturn(new MessageRespDto());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService), "mbpHandler",
                spyMbpHandler);
    }

    @org.junit.After
    public void clearMocks() throws Exception {
    	ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService), "cbpHandler",
                cbpHandler);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService), "mbpHandler",
                mbpHandler);
    }

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Test
    public void should_be_success_when_addMember_with_single_phone_and_new_member() {
        ResourceInvokeHandler.InvokeResult<MemberForCRMRespDto> result = resourceInvokeHandler.doPost("memberCenterInterface", MemberCenterInterface.class,
                "/membercenterinterface/add", mockAddMemberWithNewMember(), MemberForCRMRespDto.class);
        MemberForCRMRespDto postResult = result.getOutput();
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals("数据插入成功", postResult.getStatus());
    }
    
    @Test
    public void should_be_success_when_addMember_with_single_email_and_web_member() {
        ResourceInvokeHandler.InvokeResult<MemberForCRMRespDto> result = resourceInvokeHandler.doPost("memberCenterInterface", MemberCenterInterface.class,
                "/membercenterinterface/add", mockAddMemberWithWebMember(), MemberForCRMRespDto.class);
        MemberForCRMRespDto postResult = result.getOutput();
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals("数据插入成功", postResult.getStatus());
        WebMember webMember = webMemberRepository.getWebMember(9990002l);
		Assert.assertEquals("NORMAL", webMember.getMemType());
		Assert.assertEquals(TransformType.NORMAL, webMember.getTransformType());
        
    }
    
    private MemberForCRMReqDto mockAddMemberWithWebMember() {
        MemberForCRMReqDto memberForCRMReqDto = this.initData();
        memberForCRMReqDto.getMember().setMemberID("11-13122221155");
        memberForCRMReqDto.getMember().setMemberCode("11-13122221155");
        memberForCRMReqDto.getMember().setCardNo("13122221155");
        memberForCRMReqDto.getMember().setEmail("cilin.xiao@d123.123");
        memberForCRMReqDto.getMember().setCellPhone("13122558844");
        int index = 0;
        for(MemberMemCardDto memberMemCardDto :memberForCRMReqDto.getMember().getCardList()){
            memberMemCardDto.setxCardNum("a-CardNum-5" + index++);
        }
        return memberForCRMReqDto;
    }

    private MemberForCRMReqDto mockAddMemberWithNewMember() {
        MemberForCRMReqDto memberForCRMReqDto = this.initData();
        memberForCRMReqDto.getMember().setMemberID("j_member_id_001");
        memberForCRMReqDto.getMember().setMemberCode("19900001");
        memberForCRMReqDto.getMember().setCardNo("19910001CardNo");
        memberForCRMReqDto.getMember().setCellPhone("19910001");
        int index = 0;
        for(MemberMemCardDto memberMemCardDto :memberForCRMReqDto.getMember().getCardList()){
            memberMemCardDto.setxCardNum("1-CardNum2" + index++);
        }
        return memberForCRMReqDto;
    }

    @Test
    public void should_be_transfer_success_when_addMember_with_email_exists() {
        ResourceInvokeHandler.InvokeResult<MemberForCRMRespDto> result = resourceInvokeHandler.doPost("memberCenterInterface", MemberCenterInterface.class,
                "/membercenterinterface/add", mockTransferMemberWithEmailExists(), MemberForCRMRespDto.class);
        MemberForCRMRespDto postResult = result.getOutput();
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals("数据插入成功", postResult.getStatus());
    }


    @Test
    public void should_be_fail_when_addMember_with_email_phone() {
        ResourceInvokeHandler.InvokeResult<MemberForCRMRespDto> result = resourceInvokeHandler.doPost("memberCenterInterface", MemberCenterInterface.class,
                "/membercenterinterface/add", mockAddMemberWithEmailExists(), MemberForCRMRespDto.class);
        MemberForCRMRespDto postResult = result.getOutput();
        Assert.assertEquals(Response.Status.NOT_ACCEPTABLE, result.getStatus());
        Assert.assertEquals("数据已存在,无法插入", postResult.getStatus());
    }

    private MemberForCRMReqDto mockTransferMemberWithEmailExists() {
        MemberForCRMReqDto memberForCRMReqDto = this.initData();
        memberForCRMReqDto.getMember().setMemberID("j_member_id_002");
        memberForCRMReqDto.getMember().setMemberCode("19900002");
        memberForCRMReqDto.getMember().setCardNo("19910002CardNo");
        memberForCRMReqDto.getMember().setEmail("evan.du@123.123");
        memberForCRMReqDto.getMember().setCellPhone("");
        int index = 0;

        for(MemberMemCardDto memberMemCardDto :memberForCRMReqDto.getMember().getCardList()){
               memberMemCardDto.setxCardNum("1-CardNum1" + index++);
        }
        return memberForCRMReqDto;
    }

    private MemberForCRMReqDto mockAddMemberWithEmailExists() {
        MemberForCRMReqDto memberForCRMReqDto = this.initData();
        memberForCRMReqDto.getMember().setMemberID("j_member_id_003");
        memberForCRMReqDto.getMember().setMemberCode("19900003");
        memberForCRMReqDto.getMember().setCardNo("19910003CardNo");
        memberForCRMReqDto.getMember().setEmail("");
        memberForCRMReqDto.getMember().setCellPhone("135241019881");
        return memberForCRMReqDto;
    }

    private MemberForCRMReqDto initData(){
        MemberForCRMReqDto memberForCRMReqDto = new MemberForCRMReqDto();
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberID("1-XZ2-2");
        memberDto.setCardNo("1234569991CardNo");//电商卡号
        memberDto.setCellPhone("13524101988");
        memberDto.setFullName("Jenny");
        memberDto.setCardLevel("cardLevel");
        memberDto.setIdentityNo("identityNo");
        memberDto.setIdentityType("identityType");
        memberDto.setMemberHierarchy("1");
        memberDto.setMemberCode("1212121212");
        memberDto.setActivateCode("2323232323");
        memberDto.setMemberType("memberType");
        memberDto.setPassword("password");
        memberDto.setRegisterDate(new Date());
        memberDto.setRegisterSource("regSrc");
        memberDto.setRemindAnswer("remindAnswer");
        memberDto.setRemindQuestion("remindQuestion");
        memberDto.setStatus("status");
        memberDto.setTitle("title");
        memberDto.setScoreType(1);
        memberDto.setLastUpd(new Date());

        List<MemberMemCardDto> cardList = new ArrayList<MemberMemCardDto>();
        MemberMemCardDto memberMemCardDto1 = new MemberMemCardDto();
        memberMemCardDto1.setCardTypeCd("J2 Benefit Card");
        memberMemCardDto1.setxCardNum("1234569991");
        //memberMemCardDto1.setSource("source");
        memberMemCardDto1.setValidDate(new Date());
        memberMemCardDto1.setDueDate(new Date());
        memberMemCardDto1.setStatus("status");
        cardList.add(memberMemCardDto1);

        MemberMemCardDto memberMemCardDto2 = new MemberMemCardDto();
        memberMemCardDto2.setCardTypeCd("cardType");
        memberMemCardDto2.setxCardNum("1234569992");
        memberMemCardDto2.setSource("source");
        memberMemCardDto2.setValidDate(new Date());
        memberMemCardDto2.setDueDate(new Date());
        memberMemCardDto2.setStatus("status");
        cardList.add(memberMemCardDto2);

        memberDto.setCardList(cardList);

        memberForCRMReqDto.setAuthorizationUserName("jinjiangvbp");
        memberForCRMReqDto.setAuthorizationPassword("123456");
        memberForCRMReqDto.setMember(memberDto);
        return memberForCRMReqDto;
    }
}
