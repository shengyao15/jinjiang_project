package com.jje.vbp.member;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import com.jje.dto.vbp.member.MemberActivateDto;
import com.jje.dto.vbp.member.MemberActivateResultDto;
import com.jje.vbp.member.service.MemberManagerService;


@Path("memberManager")
@Component
public class MemberManagerResource{
	
	 private static final Logger logger = LoggerFactory.getLogger(MemberManagerResource.class);
	 
	 @Autowired
	 private MemberManagerService memberManagerService;
 
	 @POST
	 @Consumes(MediaType.APPLICATION_XML)
	 @Produces(MediaType.APPLICATION_XML)
	 @Path("/memberActivate")	
	 public Response memberActivate(MemberActivateDto memberActivateDto) {
		  MemberActivateResultDto  resultDto=null;
		  try {
			logger.info("会员激活 start----------  {}",memberActivateDto);
			//开始激活
			resultDto=memberManagerService.activate(memberActivateDto);
			logger.info("会员激活 end----------  {}",memberActivateDto);
		  }catch (Exception e) {
			  logger.error("激活失败  {}",e);
			  return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		  }
		  return Response.ok(resultDto).build();
	}	
	 
	 
	 
	 @POST
	 @Consumes(MediaType.APPLICATION_XML)
	 @Produces(MediaType.APPLICATION_XML)
	 @Path("/completeWebMember")	
	 public Response completeWebMember(MemberActivateDto memberActivateDto) {
		  MemberActivateResultDto  resultDto=null;
		  try {
			logger.info("快速会员转正 start----------  {}",memberActivateDto);
			resultDto=memberManagerService.completeWebMember(memberActivateDto);
			logger.info("快速会员转正 end----------  {}",memberActivateDto);
		  }catch (Exception e) {
			  logger.error("快速会员转正失败  {}",e);
			  return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		  }
		  return Response.ok(resultDto).build();
	} 
	 
	 @POST
	 @Consumes(MediaType.APPLICATION_XML)
	 @Produces(MediaType.APPLICATION_XML)
	 @Path("/validateMemberInfo")	
	 public Response validateIdentifyInfo(MemberActivateDto memberActivateDto) {
		  MemberActivateResultDto  resultDto=null;
		  try {
			logger.info("验证会员信息 start----------  {}",memberActivateDto);
			resultDto=memberManagerService.validateIdentifyInfo(memberActivateDto);
			logger.info("验证会员信息 end----------  {}",memberActivateDto);
		  }catch (Exception e) {
			  logger.error("验证会员信息失败  {}",e);
			  return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		  }
		  return Response.ok(resultDto).build();
	}  
	 
	
	
}
