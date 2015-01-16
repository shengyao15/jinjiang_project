package itest.com.jje.membercenter;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.QueryScoreDto;
import com.jje.dto.member.score.ScoreLogDto;
import com.jje.dto.membercenter.ConsumeSource;
import com.jje.dto.membercenter.score.CancelTradeReceiverDto;
import com.jje.dto.membercenter.score.ScoreReceiverDto;
import com.jje.membercenter.ScoreResource;
import com.jje.membercenter.score.ScoreTradeResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class ScoreResourceIT {

	@Autowired
	private VirtualDispatcherService virtualDispatcherService;

	@Test
	public void getScoreHistorys() throws Exception {
		try {
			String content = JaxbUtils.convertToXmlString(getQueryScoreDto());
			System.out.println(content);
			MockHttpRequest request = MockHttpRequest.post("/score/getScoreHistorys");
			request.content(content.getBytes("UTF-8"));
			request.contentType(MediaType.APPLICATION_XML);
			MockHttpResponse response = new MockHttpResponse();
			virtualDispatcherService.getDispatcher("scoreResource", ScoreResource.class).invoke(request, response);
			MemberScoreDto mem = JaxbUtils.convertToObject(response.getContentAsString(), MemberScoreDto.class);
			List<ScoreLogDto> logs = mem.getScoreHistory();
			Assert.assertEquals("REDEMPTION", logs.get(0).getConsumeType());
			Assert.assertEquals("100", logs.get(0).getScore()+"");

			Assert.assertNotNull(response.getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private QueryScoreDto getQueryScoreDto() {
		QueryScoreDto query = new QueryScoreDto();
		// 1-18120148
		query.setMcMemberCode("3");
		query.setConsumeSource(ConsumeSource.JJI.getMessage());

		query.setEndDate(DateUtils.parseDate("11/22/2012", "MM/dd/yyyy"));
		query.setStartDate(DateUtils.parseDate("11/22/2011", "MM/dd/yyyy"));
		return query;
	}

	@Test
	public void trade() throws Exception {

		String content = JaxbUtils.convertToXmlString(getScoreReceiverDto());
		MockHttpRequest request = MockHttpRequest.post("/scoreTrade/trade");
		request.content(content.getBytes("UTF-8"));
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();
		virtualDispatcherService.getDispatcher("scoreTradeResource", ScoreTradeResource.class).invoke(request, response);
		System.out.println("res========= " + response.getContentAsString());
	}

	private ScoreReceiverDto getScoreReceiverDto() {
		ScoreReceiverDto receiverDto = new ScoreReceiverDto();
		receiverDto.setMcMemberCode("3");
		receiverDto.setPoints("12");
		receiverDto.setRedeemproduct("adc");
		receiverDto.setTransdate(new Date());
		return receiverDto;
	}

	@Test
	public void cancelTrade() throws Exception {
		String content = JaxbUtils.convertToXmlString(getCancelTradeReceiverDto());
		MockHttpRequest request = MockHttpRequest.post("/scoreTrade/cancelTrade");
		request.content(content.getBytes("UTF-8"));
		request.contentType(MediaType.APPLICATION_XML);
		MockHttpResponse response = new MockHttpResponse();
		virtualDispatcherService.getDispatcher("scoreTradeResource", ScoreTradeResource.class).invoke(request, response);
		System.out.println("res========= " + response.getContentAsString());
	}

	private CancelTradeReceiverDto getCancelTradeReceiverDto() {
		CancelTradeReceiverDto receiver = new CancelTradeReceiverDto();
		receiver.setMcMemberCode("3");
		receiver.setPunishpoints("1");
		receiver.setTxnid("");
		return receiver;
	}

}
