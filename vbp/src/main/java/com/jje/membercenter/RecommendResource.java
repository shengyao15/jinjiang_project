package com.jje.membercenter;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.data.util.RecommendStatic;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.coupon.issue.CouponSysIssueResult.ResponseMessage;
import com.jje.dto.membercenter.recommend.MemberRecommendDto;
import com.jje.dto.membercenter.recommend.MemberRecommendOrderDto;
import com.jje.vbp.memberRecommend.service.MemberRecommendService;

@Path("recommend")
@Component
public class RecommendResource {
    private static final Logger logger = LoggerFactory.getLogger(RecommendResource.class);

    @Autowired
    private MemberRecommendService memberRecommendService;


    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getRecommend/{recommendId}")
    public Response getRecommend(@PathParam("recommendId") String recommendId) {
    	ResultMemberDto<MemberRecommendDto> resultDto = new ResultMemberDto<MemberRecommendDto>();
    	List<MemberRecommendDto> resList = memberRecommendService.getRecommend(recommendId,RecommendStatic.RECOMMEND_TIMES,RecommendStatic.RECOMMEND_CAMPAIGN );
    	resultDto.setResults(resList);
    	return Response.status(Status.OK).entity(resultDto).build();
    }
    
    
    @GET
    @Path("/recommendRegisterCoupon/{num}")
    public Response recommendRegisterCoupon(@PathParam("num") Integer num) {
    	ResponseMessage response = memberRecommendService.recommendRegisterCoupon(num);
    	logger.info("recommendRegisterCoupon " + num +" started~!");
    	return Response.status(Status.OK).entity(response.getCode()).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getRecommendOrder/{memberId}")
    public Response getRecommendOrder(@PathParam("memberId") String memberId) {
    	ResultMemberDto<MemberRecommendOrderDto> resultDto = new ResultMemberDto<MemberRecommendOrderDto>();
    	List<MemberRecommendOrderDto> resList = memberRecommendService.getRecommendOrder(memberId);
    	resultDto.setResults(resList);
    	return Response.status(Status.OK).entity(resultDto).build();
    }
}
