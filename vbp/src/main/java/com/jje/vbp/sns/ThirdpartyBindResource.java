package com.jje.vbp.sns;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.vbp.sns.ThirdpartyBindDto;
import com.jje.dto.vbp.sns.ThirdpartyBindResult;
import com.jje.vbp.sns.domain.ThirdpartyBind;
import com.jje.vbp.sns.service.ThirdpartyBindService;

@Path("/member/thirdpartyBind")
@Component("thirdpartyBindResource")
public class ThirdpartyBindResource {
	
	@Autowired
	private ThirdpartyBindService thirdpartyBindService;
	
	private final Logger logger = LoggerFactory.getLogger(ThirdpartyBindResource.class);
	
	@Path("/bind")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response bind(ThirdpartyBindDto thirdpartyBindDto){
		logger.info("ThirdpartyBindResource.bind request:" + JaxbUtils.convertToXmlString(thirdpartyBindDto));
		ThirdpartyBindResult result = null;
		try{
			result = thirdpartyBindService.bind(new ThirdpartyBind(thirdpartyBindDto));
		}catch (Exception e) {
			logger.error("ThirdpartyBindResource.bind error",e);
			result = new ThirdpartyBindResult("00002","绑定失败",thirdpartyBindDto.getMemberId());
		}
		return Response.ok(result).build();
	}

	@Path("/query")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response queryThirdpartyBind(ThirdpartyBindDto thirdpartyBindDto) {
		logger.info("ThirdpartyBindResource.query request:" + JaxbUtils.convertToXmlString(thirdpartyBindDto));
		ThirdpartyBind thirdpartyBind = thirdpartyBindService.queryThirdpartyBind(new ThirdpartyBind(thirdpartyBindDto));
		return Response.ok(thirdpartyBind.toDto()).build();
	}
}
