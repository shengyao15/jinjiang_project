package com.jje.membercenter.score.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.QueryScoreDto;
import com.jje.dto.membercenter.ConsumeType;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.dto.membercenter.score.ScoreFillUpDtos;
import com.jje.dto.membercenter.score.ScoreRedeemDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.ScoreResource;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.remote.crm.datagram.response.GetScoresHistoryRes;
import com.jje.membercenter.remote.crm.datagram.response.GetScoresHistoryRes.Listofloytransaction;
import com.jje.membercenter.remote.crm.datagram.response.ScoresRedeemResponse;
import com.jje.membercenter.remote.crm.datagram.response.ScoresRedeemResponse.Listofsr;
import com.jje.membercenter.remote.crm.datagram.response.ScoresRedeemResponse.ResponseBody;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.crm.support.CrmResponse.DefaultResponseHead;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.membercenter.remote.vo.ScoreRedeemVO;
import com.jje.membercenter.remote.vo.ScoreTransactionVO;
import com.jje.membercenter.service.MemberService;


public class ScoreResourceTest  extends DataPrepareFramework{
    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Autowired
    private ScoresHandler scoreHandler;

    @Mock
    private CrmPassage crmPassage;
    
    @Mock
    private MemberService memberService;
    


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_query_redeem_list_fail_when_required_params_not_exist() {
        ResourceInvokeHandler.InvokeResult<ScoreFillUpDtos> result = resourceInvokeHandler.doPost("scoreResource", ScoreResource.class, "score/redeemList", mockScoreRedeemDto(""), ScoreFillUpDtos.class);        
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR,result.getStatus());

    }


    private Member mockMember() {
        Member mem = new Member();
        mem.setMcMemberCode("10000");
        mem.setMemberID("rowid");
        return mem;
    }    
    
	private ScoresRedeemResponse getMockScoresRedeemResponses() {
		ScoresRedeemResponse scoresRedeemRes = new ScoresRedeemResponse();
		DefaultResponseHead head = new DefaultResponseHead();
		head.setRetcode(scoreHandler.SUCCESS_STATUS);
		scoresRedeemRes.setHead(head);
		ResponseBody  body=new ResponseBody();		
		Listofsr listofstr=new Listofsr();		
		ScoreRedeemVO vo=new ScoreRedeemVO();
		vo.setOwner("R-223");
		vo.setStatus(" OPEN ");
		vo.setSrdetail("消费类型:酒店\n入住城市:上海\n入住酒店:和平饭店\n入住房间:503\n入住时间:2012年02月24日\n退房时间:2012年02月24日\n消费金额(元):800.00\n订单号:JH00000052364\n发票号:02159875278");
		listofstr.getSr().add(vo);
		body.setListofsr(listofstr);
		scoresRedeemRes.setBody(body);		
		return scoresRedeemRes;
	}

	private Object mockScoreRedeemDto(String mcCode) {
		ScoreRedeemDto scoreRedeemDto = new ScoreRedeemDto();
		scoreRedeemDto.setMcMemberCode(mcCode);
		return scoreRedeemDto;
	}
	
	
	
	@Test
    public void should_query_score_history_fail_when_required_params_not_exist() {
        ResourceInvokeHandler.InvokeResult<MemberScoreDto> result = resourceInvokeHandler.doPost("scoreResource", ScoreResource.class, "score/getScoreHistorys", mockQueryScoreDto(""), MemberScoreDto.class);        
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR,result.getStatus());
    }
	
	@Test
    public void should_query_score_history_succsss_when_required_params_exist() {
		Mockito.when(crmPassage.sendToType(Matchers.anyObject(), Matchers.eq(GetScoresHistoryRes.class))).thenReturn(getMockGetScoresHistoryRes());
		Mockito.when(memberService.getMemberCodeByMcMemberCode(Matchers.anyString())).thenReturn("JF2323223");
		resourceInvokeHandler.setField(scoreHandler, "crmPassage", crmPassage);
		resourceInvokeHandler.setField(scoreHandler, "memberService", memberService);
        ResourceInvokeHandler.InvokeResult<MemberScoreDto> result = resourceInvokeHandler.doPost("scoreResource", ScoreResource.class, "score/getScoreHistorys", mockQueryScoreDto("7"), MemberScoreDto.class);
        Assert.assertEquals(Status.OK,result.getStatus());
        Assert.assertEquals(1, result.getOutput().getScoreHistory().size());
        resourceInvokeHandler.resetField(scoreHandler, "crmPassage", CrmPassage.class);
        resourceInvokeHandler.resetField(scoreHandler, "memberService", MemberService.class);
    }
	
	
	private GetScoresHistoryRes getMockGetScoresHistoryRes() {
		GetScoresHistoryRes getScoresHistoryRes = new GetScoresHistoryRes();
		DefaultResponseHead head = new DefaultResponseHead();
		head.setRetcode(CrmResponse.Status.SUCCESS.getCode());
		getScoresHistoryRes.setHead(head);
		GetScoresHistoryRes.ResponseBody body = new GetScoresHistoryRes.ResponseBody();
		List<ScoreTransactionVO> list = new ArrayList<ScoreTransactionVO>();
		ScoreTransactionVO scoreTransationVO = new ScoreTransactionVO();
		scoreTransationVO.setStatus("a");
		scoreTransationVO.setTransactiontype(ConsumeType.ACCRUAL.name());
		list.add(scoreTransationVO);
		Listofloytransaction listofloytransaction = new Listofloytransaction();
		listofloytransaction.setLoytransaction(list);
		body.setListofloytransaction(listofloytransaction);
		getScoresHistoryRes.setBody(body);
		return getScoresHistoryRes;
	}

	private QueryScoreDto mockQueryScoreDto(String mcCode){
		QueryScoreDto queryScoreDto=new QueryScoreDto();
		queryScoreDto.setMcMemberCode(mcCode);
		return  queryScoreDto;
		
	}
	
	
	
	@Test
	public void should_update_member_available_score_success_when_score_level_info_correct(){
		resourceInvokeHandler.doPost("scoreResource", ScoreResource.class, "score/updateMemberAvailableScore",getMemberScoreLevelInfoDto(),null);
		ResourceInvokeHandler.InvokeResult<MemberScoreLevelInfoDto> result = resourceInvokeHandler.doGet("scoreResource", ScoreResource.class, "score/getMemberScoreInfo/7",  MemberScoreLevelInfoDto.class);
		System.out.println(result.getOutput());
		System.out.println(result.getOutput().getAvailableScore());
		System.out.println(result.getOutput().getAvailableScore().toString());
		Assert.assertEquals("100100", result.getOutput().getAvailableScore().toString());
		
	}
	
	
	private MemberScoreLevelInfoDto  getMemberScoreLevelInfoDto(){
		MemberScoreLevelInfoDto  scoreInfo=new MemberScoreLevelInfoDto();
		scoreInfo.setAvailableScore(100100L);
		scoreInfo.setMcMemberCode("7");
		return scoreInfo;	 
	}


    

}
