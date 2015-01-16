package com.jje.membercenter.challengeScore;

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

import com.jje.common.utils.StringUtils;
import com.jje.membercenter.challengeScore.service.CrmChallengeScoreService;
import com.jje.membercenter.service.MemberService;

@Path("/crmChallengeScore")
@Component
public class CrmChallengeScoreResource {
	
	private final Logger logger = LoggerFactory
			.getLogger(CrmChallengeScoreResource.class);
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private CrmChallengeScoreService crmChallengeScoreService;
	
	@GET
	@Path("/crmSignup/{mcCode}/{channel}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response crmSignup(@PathParam("mcCode") String mcCode,
			@PathParam("channel") String channel) {
		if(StringUtils.isBlank(mcCode) || StringUtils.isBlank(channel)) {
			return Response.status(Status.NOT_ACCEPTABLE).build();
		}
		try {
			// get memberId
			String memberId = memberService.getMemberIDByMcMemberCode(mcCode);
			if(StringUtils.isBlank(memberId)) {
				logger.error("crmSignup: member not found(mcCode={})", mcCode);
				return Response.ok().entity("fail").build();
			}
			// sign up to crm
			return Response.ok().entity(crmChallengeScoreService
					.signup(memberId, channel, "JFDTZ")).build();
		} catch(Exception ex) {
			logger.error("crm sign up error: {}", mcCode + "&" + channel, ex);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
