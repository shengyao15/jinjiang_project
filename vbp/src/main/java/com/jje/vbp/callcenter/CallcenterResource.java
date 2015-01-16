package com.jje.vbp.callcenter;

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
import com.jje.dto.vbp.callcenter.MemberCallcenterConditionDto;
import com.jje.dto.vbp.callcenter.MemberCallcenterResult;
import com.jje.vbp.callcenter.service.CallcenterService;

@Path("callcenter")
@Component
public class CallcenterResource {

	private final Logger logger = LoggerFactory.getLogger(CallcenterResource.class); 
	
	@Autowired
	private CallcenterService callcenterService;
	
	@POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
	@Path("/queryMember")
	public Response queryMember(MemberCallcenterConditionDto callcenterCondition) {
		logger.info("queryMember(QueryMemberDto<MemberCallcenterConditionDto> {})", JaxbUtils.convertToXmlString(callcenterCondition));
		MemberCallcenterResult result = null;
		try {
			result = callcenterService.queryMember(callcenterCondition);
		} catch (Exception ex) {
			result = new MemberCallcenterResult();
			logger.info("queryMember(QueryMemberDto<MemberCallcenterConditionDto> {}) Exception", ex);
		}
		return Response.ok(result).build();
	}
}
