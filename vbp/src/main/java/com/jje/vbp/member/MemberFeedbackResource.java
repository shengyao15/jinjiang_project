package com.jje.vbp.member;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.vbp.member.FeedbackStatisticsDto;
import com.jje.dto.vbp.member.MemberFeedbackDto;
import com.jje.dto.vbp.member.MemberSubFeedbackDto;
import com.jje.vbp.member.service.MemberFeedbackService;

@Path("memberFeedback")
@Component
public class MemberFeedbackResource {

	private static final Logger logger = LoggerFactory.getLogger(MemberFeedbackResource.class);

	@Autowired
	private MemberFeedbackService memberFeedbackService;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryFeedback")
	public Response queryFeedback(QueryMemberDto<MemberFeedbackDto> queryDto) {
		ResultMemberDto<MemberFeedbackDto> feedbackResult = null;
		try {
			logger.info("MemberFeedbackResource queryFeedback :" + JaxbUtils.convertToXmlString(queryDto));
			feedbackResult = memberFeedbackService.queryFeedback(queryDto);
		} catch (Exception e) {
			feedbackResult = new ResultMemberDto<MemberFeedbackDto>();
			logger.error("MemberFeedbackResource queryFeedback Exception :{}", e.getMessage());
		}
		return Response.ok(feedbackResult).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryFeedbackWithSub")
	public Response queryFeedbackWithSub(QueryMemberDto<MemberFeedbackDto> queryDto) {
		ResultMemberDto<MemberFeedbackDto> feedbackResult = null;
		try {
			logger.info("MemberFeedbackResource queryFeedbackWithSub :" + JaxbUtils.convertToXmlString(queryDto));
			feedbackResult = memberFeedbackService.queryFeedbackWithSub(queryDto);
		} catch (Exception e) {
			feedbackResult = new ResultMemberDto<MemberFeedbackDto>();
			logger.error("MemberFeedbackResource queryFeedbackWithSub Exception :{}", e.getMessage());
		}
		return Response.ok(feedbackResult).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/getFeedbackById/{feedbackId}")
	public Response getFeedbackById(@PathParam("feedbackId") Long feedbackId) {
		MemberFeedbackDto memberFeedbackDto = null;
		try {
			memberFeedbackDto = memberFeedbackService.getFeedbackById(feedbackId);
		} catch (Exception e) {
			memberFeedbackDto = new MemberFeedbackDto();
			logger.error("MemberFeedbackResource getFeedbackById Exception :{}", e.getMessage());
		}
		return Response.ok(memberFeedbackDto).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryFeedbackStatistics")
	public Response queryFeedbackStatistics() {
		FeedbackStatisticsDto statistics = null;
		try {
			statistics = memberFeedbackService.queryFeedbackStatistics();
		} catch (Exception e) {
			statistics = new FeedbackStatisticsDto();
			logger.error("MemberFeedbackResource queryFeedbackStatistics Exception :{}", e.getMessage());
		}
		return Response.ok(statistics).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/saveFeedback")
	public Response saveFeedback(MemberFeedbackDto memberFeedbackDto) {
		try {
			logger.info("MemberFeedbackResource saveFeedback :" + JaxbUtils.convertToXmlString(memberFeedbackDto));
			memberFeedbackService.saveFeedback(memberFeedbackDto);
		} catch (Exception e) {
			logger.error("MemberFeedbackResource saveFeedback Exception :{}", e.getMessage());
			return Response.noContent().build();
		}
		return Response.ok().build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/saveSubFeedback")
	public Response saveSubFeedback(MemberSubFeedbackDto memberSubFeedbackDto) {
		try {
			logger.info("MemberFeedbackResource saveSubFeedback :" + JaxbUtils.convertToXmlString(memberSubFeedbackDto));
			memberFeedbackService.saveSubFeedback(memberSubFeedbackDto);
		} catch (Exception e) {
			logger.error("MemberFeedbackResource saveSubFeedback Exception :{}", e.getMessage());
			return Response.noContent().build();
		}
		return Response.ok().build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/updateFeedback")
	public Response updateFeedback(MemberFeedbackDto memberFeedbackDto) {
		try {
			logger.info("MemberFeedbackResource updateFeedback :" + JaxbUtils.convertToXmlString(memberFeedbackDto));
			memberFeedbackService.updateFeedback(memberFeedbackDto);
		} catch (Exception e) {
			logger.error("MemberFeedbackResource updateFeedback Exception :{}", e.getMessage());
			return Response.noContent().build();
		}
		return Response.ok().build();
	}

}
