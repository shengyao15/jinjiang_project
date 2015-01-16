package com.jje.membercenter.score;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.coupon.issue.exchange.ValidateIssueResult;
import com.jje.dto.coupon.rule.CouponRuleDto;
import com.jje.dto.membercenter.score.CancelTradeAnswerDto;
import com.jje.dto.membercenter.score.CancelTradeReceiverDto;
import com.jje.dto.membercenter.score.ScoreAnswerDto;
import com.jje.dto.membercenter.score.ScoreExchangeDto;
import com.jje.dto.membercenter.score.ScoreReceiverDto;
import com.jje.dto.vbp.coupon.MallScoreDeductResultDto;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.mall.service.MemberMallService;

@Path("scoreTrade")
@Component
public class ScoreTradeResource {

    private static final Logger logger = LoggerFactory
            .getLogger(ScoreTradeResource.class);

    @Autowired
    private ScoresHandler scoreHandler;

    @Autowired
    private MemberMallService memberMallService;

    @Autowired
    private CbpHandler cbpHandler;

    @POST
    @Path("/trade")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response trade(ScoreReceiverDto receiverDto) {
        try {
            logger.debug("========= start score trade ========="
                    + JaxbUtils.convertToXmlString(receiverDto));
            ScoreAnswerDto result = scoreHandler.trade(receiverDto);
            logger.debug("========= end score trade =========="
                    + JaxbUtils.convertToXmlString(result));
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            logger.error("score trade error: " + e.getMessage(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @POST
    @Path("/scoreExchange")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response scoreExchange(ScoreExchangeDto exchangeDto) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("========= start score exchange =========" + JaxbUtils.convertToXmlString(exchangeDto));
            }
            if (!exchangeDto.valid()) {
                return Response.ok().entity(new MallScoreDeductResultDto("207", "参数不合法")).build();
            }
            ValidateIssueResult result = cbpHandler.validteExchangeIssueRule(exchangeDto.toExchangeIssueDto(exchangeDto));
            if (!result.isCanIssue()) {
                return Response.ok().entity(new MallScoreDeductResultDto("207",result.getMessage().getMessage())).build();
            }
            List<CouponRuleDto> couponRuleDtos = result.getCouponRuleDtos();

            BigDecimal points = new BigDecimal(exchangeDto.getIssueCount()).multiply(new BigDecimal(couponRuleDtos.get(0).getIssueRuleDto().getExchangeEventDto().getCost()));
            exchangeDto.setPoints(points.toString());
            MallScoreDeductResultDto resultDto = memberMallService.mallScoreDeduct(exchangeDto.toMallScoreDeductDto()).toDto();
            if (logger.isDebugEnabled()) {
                logger.debug("========= end score exchange ==========" + JaxbUtils.convertToXmlString(resultDto));
            }
            return Response.ok().entity(resultDto).build();
        } catch (Exception e) {
            logger.error("score trade exchange: " + e.getMessage(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @POST
    @Path("/cancelTrade")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response cancelTrade(CancelTradeReceiverDto receiver) {
        try {
            logger.debug("========= start score cancelTrade ========="
                    + JaxbUtils.convertToXmlString(receiver));
            CancelTradeAnswerDto result = scoreHandler.cancelTrade(receiver);
            logger.debug("========= end score cancelTrade =========="
                    + JaxbUtils.convertToXmlString(result));
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            logger.error("score cancelTrade error: " + e.getMessage(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
