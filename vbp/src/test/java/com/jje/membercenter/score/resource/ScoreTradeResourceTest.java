package com.jje.membercenter.score.resource;

import java.util.Date;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.score.CancelTradeAnswerDto;
import com.jje.dto.membercenter.score.CancelTradeReceiverDto;
import com.jje.dto.membercenter.score.ExchangeType;
import com.jje.dto.membercenter.score.ScoreAnswerDto;
import com.jje.dto.membercenter.score.ScoreExchangeDto;
import com.jje.dto.membercenter.score.ScoreReceiverDto;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.remote.crm.datagram.response.CancelTradeRes;
import com.jje.membercenter.remote.crm.datagram.response.ScoresTradeRes;
import com.jje.membercenter.remote.crm.datagram.response.ScoresTradeRes.ResponseBody;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.membercenter.score.ScoreTradeResource;
import com.jje.membercenter.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class ScoreTradeResourceTest {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Mock
    private CrmPassage crmPassage;

    @Autowired
    private ScoresHandler scoreHandler;

    @Mock
    private MemberService memberService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_trade_success_when_member_exists_and_have_score() {
        Mockito.when(crmPassage.sendToType(Matchers.anyObject(), Matchers.eq(ScoresTradeRes.class))).thenReturn(getSuccessScoreTradeRes());
        Mockito.when(memberService.getMemberCodeByMcMemberCode(Matchers.anyString())).thenReturn("JF2323223");
        Mockito.when(memberService.getMemberByMemberNum(Matchers.anyString())).thenReturn(mockMember());
        ReflectionTestUtils.setField(scoreHandler, "crmPassage", crmPassage);
        ReflectionTestUtils.setField(scoreHandler, "memberService", memberService);

        InvokeResult<ScoreAnswerDto> result = resourceInvokeHandler.doPost("scoreTradeResource", ScoreTradeResource.class, "scoreTrade/trade", mockSuccessScoreReceiverDto(), ScoreAnswerDto.class);
        ScoreAnswerDto answer = result.getOutput();
        Assert.assertEquals("1000", answer.getRemainpoint());
    }


    private Member mockMember() {
        Member mem = new Member();
        mem.setMcMemberCode("10000");
        mem.setMemberID("rowid");
        return mem;
    }

    private ScoresTradeRes getSuccessScoreTradeRes() {
        ScoresTradeRes res = new ScoresTradeRes();
        ResponseBody body = new ResponseBody();
        body.setMembid("JF2323223");
        body.setRemainpoint("1000");
        body.setTransid("20120731");
        res.setBody(body);
        return res;
    }

    private ScoreReceiverDto mockSuccessScoreReceiverDto() {
        ScoreReceiverDto receiverDto = new ScoreReceiverDto();
        receiverDto.setOrderNO("J10000");
        receiverDto.setOrderOriginPart("TRAVEL");
        receiverDto.setMcMemberCode("1000");
        receiverDto.setPoints("222");
        receiverDto.setRedeemproduct("redeem");
        receiverDto.setTransdate(new Date());
        return receiverDto;
    }


    @Test
    public void should_trade_fail_when_params_not_include_memid() {
        InvokeResult<ScoreAnswerDto> result = resourceInvokeHandler.doPost("scoreTradeResource", ScoreTradeResource.class, "scoreTrade/trade", mockNotIncludeRequiedParamsScoreReceiverDto(), ScoreAnswerDto.class);
        Assert.assertEquals(Status.INTERNAL_SERVER_ERROR, result.getStatus());
    }


    private ScoreReceiverDto mockNotIncludeRequiedParamsScoreReceiverDto() {
        ScoreReceiverDto receiverDto = new ScoreReceiverDto();
        receiverDto.setOrderNO("J23223232");
        receiverDto.setOrderOriginPart("TRAVEL");
        receiverDto.setMcMemberCode("");
        receiverDto.setPoints("2222");
        return receiverDto;
    }

    @Test
    public void should_cancel_Trade_fail_when_required_params_not_exists() {
        InvokeResult<CancelTradeAnswerDto> cancelResult = resourceInvokeHandler.doPost("scoreTradeResource", ScoreTradeResource.class, "scoreTrade/cancelTrade", mockCancelTradeReceiverDto(""), CancelTradeAnswerDto.class);
        Assert.assertEquals(Status.INTERNAL_SERVER_ERROR, cancelResult.getStatus());
    }

    private CancelTradeReceiverDto mockCancelTradeReceiverDto(String mcCode) {
        CancelTradeReceiverDto cancelTradeReceiverDto = new CancelTradeReceiverDto();
        cancelTradeReceiverDto.setTxnid("1111");
        cancelTradeReceiverDto.setPunishpoints("2");
        cancelTradeReceiverDto.setMcMemberCode(mcCode);
        return cancelTradeReceiverDto;
    }

    @Test
    public void should_cancel_Trade_success_when_required_params_exists() {
        Mockito.when(crmPassage.sendToType(Matchers.anyObject(), Matchers.eq(CancelTradeRes.class))).thenReturn(getSuccessCancelTradeRes());
        Mockito.when(memberService.getMemberCodeByMcMemberCode(Matchers.anyString())).thenReturn("JF2323223");
        Mockito.when(memberService.getMemberByMemberNum(Matchers.anyString())).thenReturn(mockMember());
        ReflectionTestUtils.setField(scoreHandler, "crmPassage", crmPassage);
        ReflectionTestUtils.setField(scoreHandler, "memberService", memberService);
        InvokeResult<CancelTradeAnswerDto> cancelResult = resourceInvokeHandler.doPost("scoreTradeResource", ScoreTradeResource.class, "scoreTrade/cancelTrade", mockCancelTradeReceiverDto("R2121"), CancelTradeAnswerDto.class);
        Assert.assertEquals("2000",cancelResult.getOutput().getLoypoint());
        resourceInvokeHandler.resetField(scoreHandler, "crmPassage", CrmPassage.class);
        resourceInvokeHandler.resetField(scoreHandler, "memberService", MemberService.class);
    }

    private CancelTradeRes getSuccessCancelTradeRes() {
        CancelTradeRes res = new CancelTradeRes();
        CancelTradeRes.ResponseBody body=new CancelTradeRes.ResponseBody();
        body.setLoypoint("2000");
        body.setMembid("R2121");
        res.setBody(body );
        return res;
    }
    
    


}
