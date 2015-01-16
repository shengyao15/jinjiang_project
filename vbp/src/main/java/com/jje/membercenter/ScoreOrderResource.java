package com.jje.membercenter;

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

import com.jje.dto.membercenter.ScoreOrderDto;
import com.jje.membercenter.domain.ScoreOrder;
import com.jje.membercenter.domain.ScoreOrderRepository;

@Path("scoreOrder")
@Component
public class ScoreOrderResource {

	private static final Logger logger = LoggerFactory.getLogger(ScoreOrderResource.class);
	
	@Autowired
	ScoreOrderRepository scoreOrderRepository;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/save")
	public Response save(ScoreOrderDto scoreOrderDto) throws Exception {
		try {
            if(logger.isDebugEnabled())  logger.debug("-----start ScoreMemberResource.save()----");
			scoreOrderRepository.save(ScoreOrder.from(scoreOrderDto));
			if(logger.isDebugEnabled())  logger.debug("-----end ScoreMemberResource.save()----");
            return Response.ok().build();
		}
		catch (Exception e) {
			logger.error(" save error", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
