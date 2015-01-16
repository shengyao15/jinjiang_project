package com.jje.membercenter.forcrm.memberCenterInterface;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.After;
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
import com.jje.dto.coupon.issue.upgrade.UpgradeIssueDto;
import com.jje.dto.membercenter.CardType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMRespDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.forcrm.MemberCenterInterface;
import com.jje.membercenter.service.MemberService;
import com.jje.vbp.handler.CbpHandler;


public class UpgradeIssuedCouponForStoreTest extends DataPrepareFramework {
    @Autowired
    private ResourceInvokeHandler handler;
    @Autowired
    private CbpHandler cbpHandler;
    @Mock
    private CbpHandler spyCbpHandler;
    @Autowired
    private MemberService memberService;

    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(spyCbpHandler.upgradeIssuedCoupon(Mockito.any(UpgradeIssueDto.class))).thenReturn(mockSuccessCouponSysIssueResult());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService), "cbpHandler", spyCbpHandler);
    }

    private CouponSysIssueResult mockSuccessCouponSysIssueResult() {
        CouponSysIssueResult couponSysIssueResult = new CouponSysIssueResult();
        couponSysIssueResult.setResponseMessage(CouponSysIssueResult.ResponseMessage.SUCCESS);
        return couponSysIssueResult;
    }

    @After
    public void clearMocks() throws Exception {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(memberService), "cbpHandler", cbpHandler);
    }


    @Test
    public void should_be_fail_when_given_member_memberId_not_exist() {
        ResourceInvokeHandler.InvokeResult<MemberForCRMRespDto> postResult = handler.doPost("memberCenterInterface", MemberCenterInterface.class,
                "/membercenterinterface/upgradeIssuedCouponForStore", mockNotExistMemberDto(), MemberForCRMRespDto.class);
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR,postResult.getStatus());

    }

    private MemberDto mockNotExistMemberDto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberID("3-20121122");
        memberDto.setCardType(CardType.ENJOY2.getCode());
        return memberDto;
    }

    @Test
    public void should_be_success_when_given_member_memberId_exist() {
        ResourceInvokeHandler.InvokeResult<MemberForCRMRespDto> postResult = handler.doPost("memberCenterInterface", MemberCenterInterface.class,
                "/membercenterinterface/upgradeIssuedCouponForStore", mockMemberDto(), MemberForCRMRespDto.class);
        Assert.assertEquals(Response.Status.OK,postResult.getStatus());
        Assert.assertEquals(CouponSysIssueResult.ResponseMessage.SUCCESS.getMessage(), postResult.getOutput().getStatus());
    }

    private MemberDto mockMemberDto() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberID("3-201201121");
        memberDto.setCardType(CardType.ENJOY2.getCode());
        return memberDto;
    }
}
