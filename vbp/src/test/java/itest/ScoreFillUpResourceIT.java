package itest;

import java.math.BigDecimal;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpType;
import com.jje.membercenter.score.ScoreFillUpResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class ScoreFillUpResourceIT {
	
	@Autowired
	private VirtualDispatcherService virtualDispatcherService;
	
	@Test
	public void hotelScoreFillUp() {

		try {
			String content = JaxbUtils.convertToXmlString(mockHotelScoreFillUp());
			System.out.println(content);
			MockHttpRequest request = MockHttpRequest.post("/scorefillup");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();

			virtualDispatcherService.getDispatcher("scoreFillUpResource", ScoreFillUpResource.class).invoke(request, response);
			Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void travelScoreFillUp() {

		try {
			String content = JaxbUtils.convertToXmlString(mockTravelScoreFillUp());
			System.out.println(content);
			MockHttpRequest request = MockHttpRequest.post("/scorefillup");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			
			virtualDispatcherService.getDispatcher("scoreFillUpResource", ScoreFillUpResource.class).invoke(request, response);
			Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void autoScoreFillUp() {

		try {
			String content = JaxbUtils.convertToXmlString(mockAutoScoreFillUp());
			System.out.println(content);
			MockHttpRequest request = MockHttpRequest.post("/scorefillup");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();

			virtualDispatcherService.getDispatcher("scoreFillUpResource", ScoreFillUpResource.class).invoke(request, response);
			Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ScoreFillUpDto mockHotelScoreFillUp() {
		ScoreFillUpDto score = new ScoreFillUpDto();
		score.setBusinessType(ScoreFillUpType.HOTEL);
		score.setCheckInCity("上海");
		score.setHotelName("华亭宾馆");
		score.setCheckInTime(new Date());
		score.setCheckOutTime(new Date());
		score.setRoomNo("1001");
		score.setAmount(new BigDecimal(800.00));
		score.setInvoiceUrl("http://www.invoice.com/251");
		score.setInvoiceNo("I000001");
//		score.setMemberId("1-18314562");
        score.setMcMemberCode("7");
		return score;
	}


    private ScoreFillUpDto mockTravelScoreFillUp() {
		ScoreFillUpDto score = new ScoreFillUpDto();
		score.setBusinessType(ScoreFillUpType.TRAVEL);
		score.setPayTime(new Date());
		score.setStoreName("锦江店");
		score.setLineName("马尔代夫Robinson罗宾逊岛6日4晚(直飞*含三餐*含税12年5/1-6/25)");
		score.setDepartDate(new Date());
		score.setReturnDate(new Date());
		score.setAmount(new BigDecimal(800.00));
		score.setInvoiceNo("I000001");
		score.setOrderNo("TSHC00012354");
//		score.setMemberId("1-18314562");
        score.setMcMemberCode("7");
		return score;
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

}
