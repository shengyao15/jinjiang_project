package com.jje.membercenter.resource.scoreResource;

import java.util.List;

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

import com.jje.common.utils.AopTargetUtils;
import com.jje.common.utils.DateUtils;
import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.QueryScoreDto;
import com.jje.dto.member.score.ScoreLogDto;
import com.jje.dto.membercenter.ConsumeSource;
import com.jje.membercenter.ScoreResource;
import com.jje.membercenter.remote.crm.datagram.response.GetScoresHistoryRes;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.membercenter.remote.vo.ScoreTransactionVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class GetScoreHistorysTest {
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;
	
	@Mock
	private CrmPassage mockCrmPassage;
	
	@Autowired
	private CrmPassage crmPassage;
	
	@Autowired
	private ScoresHandler scoresHandler;
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getScoreHistorys() throws Exception {
		
		Mockito.when(mockCrmPassage.sendToType(Matchers.anyObject(), Matchers.eq(GetScoresHistoryRes.class))).thenReturn(this.getScoresHistoryRes());
		ReflectionTestUtils.setField(scoresHandler, "crmPassage", mockCrmPassage);
		
		InvokeResult<MemberScoreDto> result = resourceInvokeHandler.doPost("scoreResource", ScoreResource.class, "/score/getScoreHistorys", getQueryScoreDto(), MemberScoreDto.class);
		MemberScoreDto memberScoreDto = result.getOutput();
		List<ScoreLogDto> logs = memberScoreDto.getScoreHistory();
		Assert.assertEquals("REDEMPTION", logs.get(0).getConsumeType());
		Assert.assertEquals("100", logs.get(0).getScore()+"");
		
		
		Mockito.verify(mockCrmPassage).sendToType(Matchers.anyObject(), Matchers.eq(GetScoresHistoryRes.class));
		
		ReflectionTestUtils.setField(scoresHandler, "crmPassage", crmPassage);
	}
	
   private GetScoresHistoryRes getScoresHistoryRes() {
        GetScoresHistoryRes testRes = new GetScoresHistoryRes();
        GetScoresHistoryRes.ResponseBody body = new GetScoresHistoryRes.ResponseBody();
        ScoreTransactionVO scoreTransactionVO = new ScoreTransactionVO();
        scoreTransactionVO.setComments("comments");
        scoreTransactionVO.setStartdate("12/23/2011");
        scoreTransactionVO.setEnddate("01/23/2012");
        scoreTransactionVO.setTransactiondate("12/23/2011 14:00:00");
        scoreTransactionVO.setTransactiontype("REDEMPTION");
        scoreTransactionVO.setPoints("100");
        scoreTransactionVO.setProductname("productName");
        GetScoresHistoryRes.Listofloytransaction list = new GetScoresHistoryRes.Listofloytransaction();
        list.getLoytransaction().add(scoreTransactionVO);
        body.setListofloytransaction(list);
        testRes.setBody(body);
        return testRes;
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
}
