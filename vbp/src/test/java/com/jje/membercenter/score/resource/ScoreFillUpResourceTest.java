package com.jje.membercenter.score.resource;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpResultDto;
import com.jje.dto.membercenter.score.ScoreFillUpType;
import com.jje.membercenter.remote.crm.datagram.response.ScoreFillUpRes;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.membercenter.score.ScoreFillUpResource;
import com.jje.membercenter.service.MemberService;
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

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class ScoreFillUpResourceTest {
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Mock
    private MemberService memberService;

    @Autowired
    private ScoresHandler scoreHandler;

    @Mock
    private CrmPassage crmPassage;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_be_fillUp_fail_when_required_params_not_exist() {
        ResourceInvokeHandler.InvokeResult<ScoreFillUpResultDto> result = resourceInvokeHandler.doPost("scoreFillUpResource", ScoreFillUpResource.class, "scorefillup", mockHotelInfoEmptyScoreFillUp(), ScoreFillUpResultDto.class);
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR,result.getStatus());

    }

    @Test
    public void should_fillUp_success_when_params_required(){
        Mockito.when(crmPassage.sendToType(Matchers.anyObject(), Matchers.eq(ScoreFillUpRes.class))).thenReturn(getMockScoreFillUpRes());
        Mockito.when(memberService.getMemberCodeByMcMemberCode(Matchers.anyString())).thenReturn("JF2323223");
        ReflectionTestUtils.setField(scoreHandler, "crmPassage", crmPassage);
        ReflectionTestUtils.setField(scoreHandler, "memberService", memberService);
        ResourceInvokeHandler.InvokeResult<ScoreFillUpResultDto> result = resourceInvokeHandler.doPost("scoreFillUpResource", ScoreFillUpResource.class, "scorefillup", mockHotelScoreFillUp(), ScoreFillUpResultDto.class);
        Assert.assertEquals("1",result.getOutput().getReturnCode());
        resourceInvokeHandler.resetField(scoreHandler, "crmPassage", CrmPassage.class);
        resourceInvokeHandler.resetField(scoreHandler, "memberService", MemberService.class);
    }


     private ScoreFillUpDto mockHotelInfoEmptyScoreFillUp() {
        ScoreFillUpDto score = new ScoreFillUpDto();
        score.setBusinessType(ScoreFillUpType.HOTEL);
        return score;
    }

    private ScoreFillUpDto mockHotelScoreFillUp() {
        ScoreFillUpDto score = new ScoreFillUpDto();
        score.setBusinessType(ScoreFillUpType.HOTEL);
        score.setCheckInCity("上海");
        score.setHotelName("和平饭店");
        score.setRoomNo("503");
        score.setCheckInTime(new Date());
        score.setCheckOutTime(new Date());
        score.setAmount(new BigDecimal("800.00"));
        score.setOrderNo("JH00000052364");
        score.setInvoiceNo("02159875278");
        score.setMcMemberCode("1000");
        return score;
    }

    private ScoreFillUpRes getMockScoreFillUpRes() {
        ScoreFillUpRes scoreFillUpRes = new ScoreFillUpRes();
        ScoreFillUpRes.ResponseBody body = new ScoreFillUpRes.ResponseBody();
        body.setMembid("1000");
        body.setRecode(new BigDecimal(1).toBigInteger());
        body.setRemsg("SB");
        scoreFillUpRes.setBody(body);
        return scoreFillUpRes;
    }


}
