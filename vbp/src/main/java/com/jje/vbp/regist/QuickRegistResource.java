package com.jje.vbp.regist;

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

import com.jje.dto.membercenter.WebMemberDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.membercenter.WebMemberResource;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.handler.EbpHandler;
import com.jje.vbp.handler.MbpHandler;
import com.jje.vbp.regist.domain.WebMemberInfo;
import com.jje.vbp.regist.service.QuickRegistService;
import com.jje.vbp.validate.service.ValidateService;
@Component
@Path("quickRegist")
public class QuickRegistResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CbpHandler cbpHandler;
	@Autowired
	private MbpHandler mbpHandler;
	@Autowired
	private EbpHandler ebpHandler;
	@Autowired
	private ValidateService validateService; 
	@Autowired
	private QuickRegistService quickRegistService;

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/regist")
	public Response regist(WebMemberDto webMemberDto) {
		logger.info("request url:/quickRegist/regist, paramater: WebMemberDto {}", webMemberDto);
		WebMemberInfo webMemberInfo = WebMemberInfo.fromDto(webMemberDto);
		
		WebMemberRegisterReturnDto returnDto = new WebMemberRegisterReturnDto();
		if(validateService.isUsedByEmailOrPhone(webMemberInfo.getEmail(),webMemberInfo.getPhone(), returnDto)){
			return Response.ok().entity(returnDto).build();
		}
		
		returnDto = quickRegistService.regist(webMemberInfo);
		if(returnDto.isSuccess()){
			cbpHandler.quickRegisterIssue(webMemberInfo.getRegistChannel(),returnDto.getMcMemberCode(),null);
			ebpHandler.sendRegisterEvent(returnDto.getMcMemberCode(), webMemberInfo.getRegistChannel().name(), null, null);
			mbpHandler.sendQuickRegisterSuccessMessage(webMemberInfo);
		}
		return Response.ok().entity(returnDto).build();
	}
}
