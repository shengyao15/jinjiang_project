package actest.score;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.score.CancelTradeAnswerDto;
import com.jje.dto.membercenter.score.CancelTradeReceiverDto;
import com.jje.dto.membercenter.score.ScoreAnswerDto;
import com.jje.dto.membercenter.score.ScoreReceiverDto;
import com.jje.membercenter.score.ScoreTradeResource;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
@Ignore
public class ScoreTradeResourceTest {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;



    private ScoreReceiverDto mockSuccessScoreReceiverDto(String points) {
        ScoreReceiverDto receiverDto = new ScoreReceiverDto();
        receiverDto.setOrderNO("SAMSUN250");
        receiverDto.setOrderOriginPart("TRAVEL");
        receiverDto.setMcMemberCode("3");
        receiverDto.setPoints(points);
        receiverDto.setRedeemproduct("0000025");
        receiverDto.setTransdate(new Date());
        return receiverDto;
    }


    @Test
    public void should_be_trade_cancel_failed_when_crm_not_exist_txnid() {
        InvokeResult<CancelTradeAnswerDto> result = resourceInvokeHandler.doPost("scoreTradeResource", ScoreTradeResource.class, "scoreTrade/cancelTrade", mockCancelTradeReceiverDto("2001","1"), CancelTradeAnswerDto.class);
        CancelTradeAnswerDto answer = result.getOutput();
        Assert.assertEquals("00002", answer.getRecode());
    }

    private CancelTradeReceiverDto mockCancelTradeReceiverDto(String transid,String punishPoints) {
        CancelTradeReceiverDto cancelTradeReceiverDto = new CancelTradeReceiverDto();
        cancelTradeReceiverDto.setMcMemberCode("3");
        cancelTradeReceiverDto.setTxnid(transid);
        cancelTradeReceiverDto.setPunishpoints(punishPoints);
        return cancelTradeReceiverDto;
    }

    
    /**
     * data.sql  ---mc_member_code:3  mem_num :0000025
     */
    @Test
    public void should_be_trade_cancel_success_when_crm_exist_txnid() {
        String reducePoints="2";
        InvokeResult<ScoreAnswerDto> result = resourceInvokeHandler.doPost("scoreTradeResource", ScoreTradeResource.class, "scoreTrade/trade", mockSuccessScoreReceiverDto(reducePoints), ScoreAnswerDto.class);
        ScoreAnswerDto answer = result.getOutput();
        String remainPoints=answer.getRemainpoint();
        String punishPoints="1";
        InvokeResult<CancelTradeAnswerDto> cancelResult = resourceInvokeHandler.doPost("scoreTradeResource", ScoreTradeResource.class, "scoreTrade/cancelTrade", mockCancelTradeReceiverDto(answer.getTransid(),punishPoints), CancelTradeAnswerDto.class);
        CancelTradeAnswerDto cancelAnswer = cancelResult.getOutput();
        Assert.assertEquals(Integer.parseInt(remainPoints)+Integer.parseInt(reducePoints)-Integer.parseInt(punishPoints)+"", cancelAnswer.getLoypoint());
    }
}
