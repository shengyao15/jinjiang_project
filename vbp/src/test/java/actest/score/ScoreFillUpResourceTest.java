package actest.score;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpResultDto;
import com.jje.dto.membercenter.score.ScoreFillUpType;
import com.jje.membercenter.score.ScoreFillUpResource;
import com.jje.membercenter.score.domain.ScoreFillUp;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
@Ignore
public class ScoreFillUpResourceTest {
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Test
    public void should_be_fillUp_success_when_params_required_and_correct() {
        ResourceInvokeHandler.InvokeResult<ScoreFillUpResultDto> result = resourceInvokeHandler.doPost("scoreFillUpResource", ScoreFillUpResource.class, "scorefillup", mockHotelScoreFillUp(), ScoreFillUpResultDto.class);
        Assert.assertEquals(Response.Status.OK,result.getStatus());
    }

    private ScoreFillUpDto mockHotelScoreFillUp() {
        ScoreFillUpDto score = new ScoreFillUpDto();
        score.setBusinessType(ScoreFillUpType.HOTEL);
        score.setCheckInCity("上海11");
        score.setHotelName("和平饭店11");
        score.setRoomNo("50311");
        score.setCheckInTime(new Date());
        score.setCheckOutTime(new Date());
        score.setAmount(new BigDecimal("800.0011"));
        score.setOrderNo("JH000000523641");
        score.setInvoiceNo("021598752781");
        score.setMcMemberCode("7");
        return score;
    }

    @Test
    public void should_get_correct_hotel_scorefillup_when_the_correct_pattern_hotel_detail() {
        String detail = "消费类型:酒店\n入住城市:上海\n入住酒店:和平饭店\n入住房间:503\n入住时间:2012年02月24日\n退房时间:2012年02月24日\n消费金额(元):800.00\n订单号:JH00000052364\n发票号:02159875278";
        ScoreFillUp score = new ScoreFillUp();
        score.setDetail(detail);
        Assert.assertTrue(score.getCheckInCity().equals("上海"));
    }

    @Test
    public void should_be_correct_pattern_detail_when_correct_travel_score_fill_up() {
        ScoreFillUp score = mockTravelScoreFillUp();
        Assert.assertTrue(score.getDetail().contains("I000001"));
    }

     private ScoreFillUp mockTravelScoreFillUp() {
        ScoreFillUp score = new ScoreFillUp();
        score.setBusinessType(ScoreFillUpType.TRAVEL);
        score.setPayTime(new Date());
        score.setStoreName("锦江店");
        score.setLineName("马尔代夫Robinson罗宾逊岛6日4晚(直飞*含三餐*含税12年5/1-6/25)");
        score.setGroupCode("G0012");
        score.setDepartDate(new Date());
        score.setReturnDate(new Date());
        score.setAmount(new BigDecimal(800.00));
        score.setInvoiceNo("I000001");
        score.setOrderNo("TSHC00012354");
        score.setMcMemberCode("3");
        return score;
    }

    @Test
    public void should_get_correct_travel_scorefillup_when_the_correct_pattern_travel_detail() {
        String detail = "消费类型:旅游\n购买时间:2012年02月28日\n购买门店:锦江店\n线路名称:马尔代夫Robinson罗宾逊岛6日4晚(直飞*含三餐*含税12年5/1-6/25)\n团号:G0012\n出发日期:2012年02月28日\n结束日期:2012年02月28日\n消费金额(元):800\n订单号:TSHC00012354\n发票号:I000001";
        ScoreFillUp score = new ScoreFillUp();
        score.setDetail(detail);
        Assert.assertTrue(score.getGroupCode().equals("G0012"));
    }

    @Test
    public void should_be_correct_pattern_detail_when_correct_auto_score_fill_up() {
        ScoreFillUp score = mockAutoScoreFillUp();
        Assert.assertTrue(score.getDetail().contains("1001"));
    }

    private ScoreFillUp mockAutoScoreFillUp() {
        ScoreFillUp score = new ScoreFillUp();
        score.setBusinessType(ScoreFillUpType.AUTO);
        score.setPayTime(new Date());
        score.setStoreName("锦江店");
        score.setCarNo("1001");
        score.setCarStartTime(new Date());
        score.setCarEndTime(new Date());
        score.setAmount(new BigDecimal(800.00));
        score.setInvoiceNo("I000001");
        score.setOrderNo("A0000123456");
        score.setMcMemberCode("3");
        return score;
    }

    @Test
    public void should_get_correct_auto_scorefillup_when_the_correct_pattern_auto_detail() {
        String detail = "消费类型:租车\n购买时间:2012年02月28日\n购买门店:锦江店\n车牌号:1001\n租车开始时间:2012年02月28日\n租车结束时间:2012年02月28日\n消费金额(元):800\n订单号:A0000123456\n发票号:I000001";
        ScoreFillUp score = new ScoreFillUp();
        score.setDetail(detail);
        Assert.assertTrue(score.getCarNo().equals("1001"));
    }
}
