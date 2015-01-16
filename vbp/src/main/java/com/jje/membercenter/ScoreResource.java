package com.jje.membercenter;

import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.QueryScoreDto;
import com.jje.dto.member.score.ScoreLogDto;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpDtos;
import com.jje.dto.membercenter.score.ScoreRedeemDto;
import com.jje.membercenter.domain.CRMOperationRespository;
import com.jje.membercenter.domain.ConfigRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.membercenter.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("score")
@Component
public class ScoreResource {

    @Autowired
    CRMOperationRespository crmOperationRespository;
    @Autowired
    private ScoresHandler scoresHandler;

    @Autowired
    MemberScoreLevelInfoRepository memberScoreLevelInfoRepository;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private MemberService memberService;

    private static final Logger logger = LoggerFactory.getLogger(ScoreResource.class);

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryChannelScore")
    public Response queryChannelScore(String orderNum) {
    	try{
    		String msg = crmOperationRespository.queryChannelScore(orderNum);
        	
        	return Response.ok(msg).build();
    	}catch(Exception e){
    		logger.error("获取渠道积分错误：  "+  orderNum + "  "+ e.getMessage(), e);
    		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	}
    	
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/list")
    public Response queryScore(QueryMemberDto<QueryScoreDto> queryDto) throws Exception {
        if (logger.isDebugEnabled()) logger.debug("-----start ScoreResource.queryScore()------");
        QueryScoreDto queryScoreDto = queryDto.getCondition();
        MemberScoreDto memberScoreDto = crmOperationRespository.queryScore(queryScoreDto);
        ResultMemberDto<MemberScoreDto> resultDto = new ResultMemberDto<MemberScoreDto>();
        Pagination pagination = queryDto.getPagination();
        int records = memberScoreDto.getScoreHistory().size();
        int page = pagination.getPage();
        int rows = pagination.getRows();
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        List<ScoreLogDto> listDtos = new ArrayList<ScoreLogDto>();
        if (memberScoreDto.getScoreHistory().size() > 0) {
            if (page < total) {
                for (int i = rows * (page - 1); i < rows * page; i++) {
                    listDtos.add(memberScoreDto.getScoreHistory().get(i));
                }
            } else {
                for (int i = rows * (page - 1); i < memberScoreDto.getScoreHistory().size(); i++) {
                    listDtos.add(memberScoreDto.getScoreHistory().get(i));
                }

            }
        }
        pagination.setRecords(records);
        pagination.countRecords(records);
        resultDto.setPagination(pagination);
        memberScoreDto.setScoreHistory(listDtos);
        List<MemberScoreDto> list = new ArrayList<MemberScoreDto>();
        list.add(memberScoreDto);
        resultDto.setResults(list);
        resultDto.getPagination().setPage(page);
        try {
            if (logger.isDebugEnabled()) logger.debug("-----end ScoreResource.queryScore()------");
            return Response.ok().entity(resultDto).build();
        } catch (Exception e) {
            logger.error("list exception " + e.getMessage(), e);
            return Response.status(Status.NO_CONTENT).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getScoreHistorys")
    public Response queryScore(QueryScoreDto queryDto) throws Exception {
        try {
            MemberScoreDto memberScoreDto = scoresHandler.getScoresHistory(queryDto);
            if (memberScoreDto == null) {
                logger.warn(" startDate=" + queryDto.getStartDate() + " endDate=" + queryDto.getEndDate() + " mcMemberCode=" + queryDto.getMcMemberCode() + " consumeSource=" + queryDto.getConsumeSource());
                logger.warn("getScoreHistorys memberScoreDto is null ");
                return Response.status(Status.NO_CONTENT).build();
            }
            return Response.ok().entity(memberScoreDto).build();
        } catch (Exception e) {
            logger.error("========= get ScoreHistorys error ==========", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getNotActiveUserScoreHistorys")
    public Response queryNotActiveUserScore(QueryScoreDto queryDto) throws Exception {
        try {
        	queryDto.setMcMemberCode(null);
            MemberScoreDto memberScoreDto = scoresHandler.getScoresHistory(queryDto);
            if (memberScoreDto == null) {
                logger.warn(" startDate=" + queryDto.getStartDate() + " endDate=" + queryDto.getEndDate() + " mcMemberCode=" + queryDto.getMcMemberCode() + " consumeSource=" + queryDto.getConsumeSource());
                logger.warn("getScoreHistorys memberScoreDto is null ");
                return Response.status(Status.NO_CONTENT).build();
            }
            return Response.ok().entity(memberScoreDto).build();
        } catch (Exception e) {
            logger.error("========= get ScoreHistorys error ==========", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/redeemList")
    public Response queryRedeemList(ScoreRedeemDto queryDto) {
        List<ScoreFillUpDto> list = null;
        ScoreFillUpDtos scoreFillUpDtos = new ScoreFillUpDtos();
        try {
            list = scoresHandler.queryRedeemList(queryDto);
            if (logger.isWarnEnabled())
                logger.warn("-----ScoreResource.queryRedeemList({})------list size is {} ", queryDto.getMcMemberCode(), scoreFillUpDtos.getList().size());
            scoreFillUpDtos.setList(list);
        } catch (Exception e) {
            logger.error("=========ScoreResource.queryRedeemList({}) error ==========", queryDto.getMcMemberCode(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(scoreFillUpDtos).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberScoreInfo/{mcMemberCode}")
    public Response getMemberScoreInfo(@PathParam("mcMemberCode") String mcMemberCode) {
        try {
            MemberScoreLevelInfoDto memberScoreLevelInfoDto = memberScoreLevelInfoRepository.getMemberScoreInfo(String.valueOf(memberService.getMemberInfoIdByMcMemberCode(mcMemberCode)));
            if (memberScoreLevelInfoDto != null) {
                MemberDegree level = MemberDegree.getMemberDegree(memberScoreLevelInfoDto.getScoreLevel() + "");
                if (level != null) {
                    logger.warn("--------ScoreResource.getMemberScoreInfo({}) level is null!!---------",mcMemberCode);
                    memberScoreLevelInfoDto.setUpdateScore(configRepository.getUpgradeScores(level));
                    memberScoreLevelInfoDto.setUpdateTime(configRepository.getUpgradeNumber(level));
                }
            }else {
                logger.warn("--------ScoreResource.getMemberScoreInfo({}) memberScoreLevelInfoDto is null!!---------",mcMemberCode);
            }
            return Response.ok().entity(memberScoreLevelInfoDto).build();
        } catch (Exception e) {
            logger.error("========= ScoreResource.MemberScoreInfo error ==========", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberScoreInfoById/{memberInfoId}")
    public Response getMemberScoreInfoById(@PathParam("memberInfoId") Long memberInfoId) {
        try {
            MemberScoreLevelInfoDto memberScoreLevelInfoDto = memberScoreLevelInfoRepository.getMemberScoreInfo(String.valueOf(memberInfoId));
            if (memberScoreLevelInfoDto != null) {
                MemberDegree level = MemberDegree.getMemberDegree(memberScoreLevelInfoDto.getScoreLevel() + "");
                if (level != null) {
                    logger.warn("--------ScoreResource.getMemberScoreInfoById({}) level is null!!---------",memberInfoId);
                    memberScoreLevelInfoDto.setUpdateScore(configRepository.getUpgradeScores(level));
                    memberScoreLevelInfoDto.setUpdateTime(configRepository.getUpgradeNumber(level));
                }
            }else {
                logger.warn("--------ScoreResource.getMemberScoreInfoById({}) memberScoreLevelInfoDto is null!!---------",memberInfoId);
            }
            return Response.ok().entity(memberScoreLevelInfoDto).build();
        } catch (Exception e) {
            logger.error("========= ScoreResource.getMemberScoreInfoById error ==========", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/updateMemberAvailableScore")
    public Response updateMemberAvailableScore(MemberScoreLevelInfoDto infoDto) {

        try {
            infoDto.setMemberInfoId(memberService.getMemberInfoIdByMcMemberCode(infoDto.getMcMemberCode()));
            logger.warn("------ScoreResource.updateMemberAvailableScore()----memberInfoId=" + infoDto.getMemberInfoId());
            memberScoreLevelInfoRepository.updateMemberAvailableScore(infoDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=========ScoreResource.updateMemberScoreInfo()----error--memberInfoId={}",infoDto.getMemberInfoId(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

}
