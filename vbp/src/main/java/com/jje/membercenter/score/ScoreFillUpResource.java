package com.jje.membercenter.score;

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
import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpResultDto;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.membercenter.score.domain.ScoreFillUp;

@Path("scorefillup")
@Component
public class ScoreFillUpResource {
	
	@Autowired
	private ScoresHandler scoreHandler;

	private static final Logger logger = LoggerFactory
			.getLogger(ScoreFillUpResource.class);

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response fillUp(ScoreFillUpDto score) {
		try {
			logger.debug("========= init score fill up ========="+JaxbUtils.convertToXmlString(score));
			ScoreFillUpResultDto result =scoreHandler.scoreFillUp(new ScoreFillUp(score)).toDto();
			logger.debug("========= end score fill up =========="+JaxbUtils.convertToXmlString(result));
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			logger.error("score fill up error:", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
