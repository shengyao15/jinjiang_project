package actest.score;

import java.math.BigDecimal;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.jje.membercenter.service.MemberService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpResultDto;
import com.jje.dto.membercenter.score.ScoreFillUpType;
import com.jje.membercenter.score.ScoreFillUpResource;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class ScoreFillUpResJUnitTest {
	
	@Autowired
	private ScoreFillUpResource res;


//	@Ignore
	@Test
	public void hotelScoreFillUpSuccess() {
		ScoreFillUpDto hotelScore = mockHotelScoreFillUp();
		String content = JaxbUtils.convertToXmlString(hotelScore);
		System.out.println(content);
		Response resp = res.fillUp(hotelScore);
		Assert.assertTrue(resp.getStatus()==Status.OK.getStatusCode());
		Assert.assertNotNull(resp.getEntity());
		ScoreFillUpResultDto result = (ScoreFillUpResultDto) resp.getEntity();
		Assert.assertNotNull(result.getMemberId());
//        Assert.assertEquals("1-19056406", result.getMemberId());
        Assert.assertEquals("1-18120148", result.getMemberId());
		Assert.assertNotNull(result.getReturnCode());
		Assert.assertTrue(result.getReturnCode().equals("1"));
	}
	
	@Test
	public void hotelScoreFillUpFail() {
		ScoreFillUpDto hotelScore = mockHotelScoreFillUp();
		hotelScore.setMcMemberCode("5");
		Response resp = res.fillUp(hotelScore);
		Assert.assertTrue(resp.getStatus()==Status.OK.getStatusCode());
		Assert.assertNotNull(resp.getEntity());
		ScoreFillUpResultDto result = (ScoreFillUpResultDto) resp.getEntity();
		System.out.println(result.getMemberId());
//         Assert.assertEquals("1-19056406", result.getMemberId());
		Assert.assertTrue(result.getMemberId().equals("1-18315555"));
		Assert.assertNotNull(result.getReturnCode());
		Assert.assertTrue(result.getReturnCode().equals("2"));
	}
	
	private ScoreFillUpDto mockHotelScoreFillUp() {
		ScoreFillUpDto score = new ScoreFillUpDto();
		score.setBusinessType(ScoreFillUpType.HOTEL);
		score.setCheckInCity("长沙");
		score.setHotelName("锦江之星");
		score.setCheckInTime(new Date());
		score.setCheckOutTime(new Date());
		score.setRoomNo("1001");
		score.setAmount(new BigDecimal(800.00));
		score.setInvoiceNo("I000001");
		score.setOrderNo("H000000000123");
//		score.setMemberId("1-19056406");
        score.setMcMemberCode("7");
		return score;
	}
	
//	@Ignore
	@Test
	public void autoScoreFillUpSuccess() {
		ScoreFillUpDto autoScore = mockAutoScoreFillUp();
		String content = JaxbUtils.convertToXmlString(autoScore);
		System.out.println(content);
		Response resp = res.fillUp(autoScore);
		Assert.assertTrue(resp.getStatus()==Status.OK.getStatusCode());
		Assert.assertNotNull(resp.getEntity());
		ScoreFillUpResultDto result = (ScoreFillUpResultDto) resp.getEntity();
		Assert.assertNotNull(result.getMemberId());
		Assert.assertNotNull(result.getReturnCode());
		Assert.assertTrue(result.getReturnCode().equals("1"));
	}
	
	@Test
	public void autoScoreFillUpFail() {
		ScoreFillUpDto autoScore = mockAutoScoreFillUp();
        autoScore.setMcMemberCode("5");
		Response resp = res.fillUp(autoScore);
		Assert.assertTrue(resp.getStatus()==Status.OK.getStatusCode());
		Assert.assertNotNull(resp.getEntity());
		ScoreFillUpResultDto result = (ScoreFillUpResultDto) resp.getEntity();
		Assert.assertTrue(result.getMemberId().equals("1-18315555"));
		Assert.assertNotNull(result.getReturnCode());
		Assert.assertTrue(result.getReturnCode().equals("2"));
	}
	
	private ScoreFillUpDto mockAutoScoreFillUp() {
		ScoreFillUpDto score = new ScoreFillUpDto();
		score.setBusinessType(ScoreFillUpType.AUTO);
		score.setPayTime(new Date());
		score.setStoreName("锦江店");
		score.setCarNo("1001");
		score.setCarStartTime(new Date());
		score.setCarEndTime(new Date());
		score.setAmount(new BigDecimal(800.00));
		score.setInvoiceNo("I000001");
//		score.setMemberId("1-19056406");
        score.setMcMemberCode("7");
        score.setOrderNo("A0000123456");
		return score;
	}
	
//	@Ignore
	@Test
	public void travelScoreFillUpSuccess() {
		ScoreFillUpDto travelScore = mockTravelScoreFillUp();
		String content = JaxbUtils.convertToXmlString(travelScore);
		System.out.println(content);
		Response resp = res.fillUp(travelScore);
		Assert.assertTrue(resp.getStatus()==Status.OK.getStatusCode());
		Assert.assertNotNull(resp.getEntity());
		ScoreFillUpResultDto result = (ScoreFillUpResultDto) resp.getEntity();
		Assert.assertNotNull(result.getMemberId());
		Assert.assertNotNull(result.getReturnCode());
		Assert.assertTrue(result.getReturnCode().equals("1"));
	}
	
	@Test
	public void travelScoreFillUpFail() {
		ScoreFillUpDto travelScore = mockTravelScoreFillUp();
		travelScore.setMcMemberCode("5");
		Response resp = res.fillUp(travelScore);
		Assert.assertTrue(resp.getStatus()==Status.OK.getStatusCode());
		Assert.assertNotNull(resp.getEntity());
		ScoreFillUpResultDto result = (ScoreFillUpResultDto) resp.getEntity();
		Assert.assertTrue(result.getMemberId().equals("1-18315555"));
		Assert.assertNotNull(result.getReturnCode());
		Assert.assertTrue(result.getReturnCode().equals("2"));
	}
	
	private ScoreFillUpDto mockTravelScoreFillUp() {
		ScoreFillUpDto score = new ScoreFillUpDto();
		score.setBusinessType(ScoreFillUpType.TRAVEL);
		score.setPayTime(new Date());
		score.setStoreName("锦江店");
		score.setLineName("马尔代夫Robinson罗宾逊岛6日4晚(直飞*含三餐*含税12年5/1-6/25)");
		score.setGroupCode("G0012");
		score.setDepartDate(new Date());
		score.setReturnDate(new Date());
		score.setAmount(new BigDecimal(800.00));
		score.setOrderNo("TSHC00012354");
		score.setInvoiceNo("I000001");
//		score.setMemberId("1-19056406");
        score.setMcMemberCode("7");
		return score;
	}

}
