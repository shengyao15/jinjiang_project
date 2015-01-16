package com.jje.vbp.memberBind;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.spi.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.memberbind.MemberBindCardDto;
import com.jje.dto.membercenter.memberbind.MemberBindDto;
import com.jje.dto.membercenter.memberbind.MemberCouponInfosDto;
import com.jje.dto.membercenter.memberbind.MemberInfoResultDto;
import com.jje.dto.membercenter.memberbind.RegisterMemberBindDto;
import com.jje.vbp.memberBind.service.MemberBindService;

/**
 *  This resource is for third party bind, not crm bind
 */
@Path("memberBind")
@Component
public class MemberBindResource {

	private static final Logger logger = LoggerFactory.getLogger(MemberBindResource.class);
    
	@Autowired
	MemberBindService memberBindService;
	
	
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/loginBind")
    public Response loginBind(MemberBindDto dto) {
    	try {
    		String msg = memberBindService.checkLoginAndBind(dto);
    		memberBindService.memberBindOpLog(msg, dto);
            return Response.status(Status.OK).entity(msg).build();
    	} catch(IllegalArgumentException e) {
    		logger.error("loginBind error: {}", dto, e);
    		return Response.status(Status.NOT_ACCEPTABLE).build();
    	} catch(Exception ex) {
    		logger.error("loginBind error: {}", dto, ex);
    		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	}
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/registerBind")
    public Response registBind(RegisterMemberBindDto registerMemberBindDto) {
    	try {
    		String msg = memberBindService.registBind(registerMemberBindDto);
    		memberBindService.memberBindOpLog(msg, registerMemberBindDto);
            return Response.status(Status.OK).entity(msg).build();
    	} catch(IllegalArgumentException e) {
    		logger.error("loginBind error: {}", registerMemberBindDto, e);
    		return Response.status(Status.NOT_ACCEPTABLE).build();
    	} catch(Exception ex) {
    		logger.error("checkBindForCardstay error:{}", registerMemberBindDto, ex);
   		 	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemeberInfo/{channel}/{bindKey}")
    public Response getMemberInfo(@PathParam("channel")String channel, @PathParam("bindKey")String bindKey) {
    	try {
    		MemberInfoResultDto memberInfoResultDto = memberBindService.getMemberInfo(channel, bindKey);
    		return Response.status(Status.OK).entity(memberInfoResultDto).build();
    	} catch(IllegalArgumentException iae) {
    		logger.error("loginBind error: {}", channel + "&" + bindKey, iae);
    		return Response.status(Status.NOT_ACCEPTABLE).build();
    	}catch(IllegalStateException ise) {
    		logger.error("checkBindForCardstay error:{}", channel + "&" + bindKey, ise);
   		 	return Response.status(Status.NOT_ACCEPTABLE).build();
    	}catch(NotFoundException nfe) {
    		logger.error("checkBindForCardstay error:{}", channel + "&" + bindKey, nfe);
   		 	return Response.status(Status.NOT_ACCEPTABLE).build();
    	}catch(Exception e) {
    		logger.error("checkBindForCardstay error:{}", channel + "&" + bindKey, e);
   		 	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	}
    }    
    
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getCouponInfo/{channel}/{bindKey}")
    public Response getMyCoupon(@PathParam("channel")String channel, @PathParam("bindKey")String bindKey) {
    	try {
    		MemberCouponInfosDto memberCouponInfosDto = memberBindService.getCouponInfo(channel, bindKey);
    		return Response.status(Status.OK).entity(memberCouponInfosDto).build();
    	}catch(Exception e) {
    		logger.error("checkBindForCardstay error:{}", channel + "&" + bindKey, e);
   		 	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getCardInfo/{channel}/{bindKey}") 
    public Response getCardInfo(@PathParam("channel")String channel, @PathParam("bindKey")String bindKey){
    	try {
    		MemberBindCardDto dto = memberBindService.getMemCardInfoByKey(bindKey, channel);
    		return Response.status(Status.OK).entity(dto).build();
    	} catch(Exception ex) {
    		logger.error("getCardInfo error: {}", channel + "&" + bindKey, ex);
   		 	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	}
    }
    
}
