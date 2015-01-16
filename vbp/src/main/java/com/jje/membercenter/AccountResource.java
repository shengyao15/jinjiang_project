package com.jje.membercenter;

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

import com.esotericsoftware.minlog.Log;
import com.jje.dto.membercenter.AccountDto;
import com.jje.dto.membercenter.CRMActivationRespDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.membercenter.domain.CRMAccountActivationRespository;
import com.jje.membercenter.domain.CRMMembershipRepository;

@Path("account")
@Component
public class AccountResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccountResource.class);
	
	@Autowired
	private CRMMembershipRepository crmMembershipRepository;
	
	@Autowired
	private CRMAccountActivationRespository crmAccountActivationRespository;
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/validate")
	public Response validate(AccountDto accountDto){
		try {
            CRMActivationRespDto crmActivationRespDto = crmMembershipRepository.validateActivationMember(accountDto);
            if (LOG.isWarnEnabled())
                LOG.warn("-----AccountResource.validate()----membrowid: "+ crmActivationRespDto.getMembrowid() + ", retcode: " + crmActivationRespDto.getRetcode() + ", retmsg: "+ crmActivationRespDto.getRetmsg());
            return Response.status(Status.OK).entity(crmActivationRespDto).build();
		} catch (Exception ex) {
			LOG.error("-----AccountResource.validate(mobile {}) error!!----",accountDto.getMobile(), ex);
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/activate")
	public Response activate(AccountDto accountDto){
		try {
			CRMResponseDto crmResponseDto= crmMembershipRepository.activateMember(accountDto);
			if (LOG.isWarnEnabled())
                LOG.warn("-----return:{} AccountResource.activate()----membid: " + crmResponseDto.getMembid() + ", retcode: " + crmResponseDto.getRetcode() + ", retmsg: " + crmResponseDto.getRetmsg(),crmResponseDto);
			return Response.status(Status.OK).entity(crmResponseDto).build();
		} catch (Exception ex) {
			LOG.error("-----AccountResource.activate(mobile {})---error!!------",accountDto.getMobile(), ex);
			return Response.status(Status.PRECONDITION_FAILED).build();
		}
	}
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/validatestar")
	public Response validateStar(AccountDto accountDto){
		try {
			CRMActivationRespDto crmActivationRespDto = crmAccountActivationRespository.validateActivationStar(accountDto);
			if (LOG.isWarnEnabled())
            LOG.warn("-----AccountResource.validateStar()---membrowid: "+crmActivationRespDto.getMembrowid()+", retcode: "+crmActivationRespDto.getRetcode()+", retmsg: "+crmActivationRespDto.getRetmsg());
			return Response.status(Status.OK).entity(crmActivationRespDto).build();
		} catch (Exception ex) {
			LOG.error("-----AccountResource.validateStar(mobile {})---error!!----",accountDto.getMobile(), ex);
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/activatestar")
	public Response activateStar(AccountDto accountDto){
		try {
			CRMResponseDto crmResponseDto= crmAccountActivationRespository.activateStar(accountDto);
			if (LOG.isWarnEnabled())
            LOG.warn("-----AccountResource.activateStar()---membid: "+crmResponseDto.getMembid()+", retcode: "+crmResponseDto.getRetcode()+", retmsg: "+crmResponseDto.getRetmsg());
			return Response.status(Status.OK).entity(crmResponseDto).build();
		} catch (Exception ex) {
			LOG.error("-----AccountResource.activateStar(mobile {})---error!!--",accountDto.getMobile(), ex);
			return Response.status(Status.PRECONDITION_FAILED).build();
		}
	}

}
