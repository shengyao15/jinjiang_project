package itest.com.jje.membercenter.score;


import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.score.RedeemStatus;
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpDtos;
import com.jje.dto.membercenter.score.ScoreRedeemDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.ScoreResource;
import com.jje.membercenter.remote.crm.datagram.request.ScoreRedeemRequest;
import com.jje.membercenter.remote.crm.datagram.response.ScoresRedeemResponse;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.remote.crm.support.CrmSrType;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.membercenter.service.MemberService;
import junit.framework.Assert;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ScoreRedeemListResourceIT  extends DataPrepareFramework {


    @Autowired
    private ScoresHandler scoresHandler;

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Mock
    private CrmPassage crmPassage;

    @Mock
    private MemberService memberService;

    @Before
    public void initMocks() throws Exception {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    public void queryRedeemListTest() throws Exception {
        Mockito.when(crmPassage.sendToType(Matchers.anyObject(),Matchers.eq(ScoresRedeemResponse.class))).thenReturn(
                mockScoreFillUpDtos());
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(scoresHandler), "crmPassage", crmPassage);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(scoresHandler), "memberService", memberService);
        ResourceInvokeHandler.InvokeResult<ScoreFillUpDtos> result = resourceInvokeHandler.doPost("scoreResource", ScoreResource.class,
                "/score/redeemList", getQueryDto(), ScoreFillUpDtos.class);
        Assert.assertEquals(Status.OK, result.getStatus());
    }

    private ScoresRedeemResponse mockScoreFillUpDtos() {
        ScoresRedeemResponse rs = new ScoresRedeemResponse();
        ScoresRedeemResponse.ResponseBody body =new ScoresRedeemResponse.ResponseBody();
        body.setMembid("1111");
        rs.setBody(body);
        return rs;
    }

    private ScoreRedeemDto getQueryDto() {
        ScoreRedeemDto scoreRedeemDto = new ScoreRedeemDto();
        scoreRedeemDto.setMcMemberCode("7");
        return scoreRedeemDto;
    }


}
