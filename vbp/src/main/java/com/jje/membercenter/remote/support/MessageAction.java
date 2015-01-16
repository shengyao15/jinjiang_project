package com.jje.membercenter.remote.support;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.NotAcceptableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;


@Path("messageAction")
@Component
public class MessageAction {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageManager messageManager;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/receive")
	public  Response  receive(BaseReceiver baseReceiver) {
		logger.info(" receive  request "+" transCode="+baseReceiver.getTransCode() +" xml= "+JaxbUtils.convertToXmlString(baseReceiver));
		BaseAnswer answer = new BaseAnswer();
		try {
			answer = messageManager.receive(baseReceiver);
			answer.setMessage(BaseAnswer.Status.SUCCESS.name());
			answer.setMessage(BaseAnswer.Status.SUCCESS.getAlias());
		} catch (NotAcceptableException e) {
			answer.setStatus(BaseAnswer.Status.FAIL.name());
			answer.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			answer.setStatus(BaseAnswer.Status.ERROR.name());
			answer.setMessage(BaseAnswer.Status.ERROR.getAlias());
		}
		logger.info(" return  response "+" transCode="+baseReceiver.getTransCode() +" xml= "+JaxbUtils.convertToXmlString(answer));
		return  Response.status(Response.Status.OK).entity(answer).build();

	}
}
