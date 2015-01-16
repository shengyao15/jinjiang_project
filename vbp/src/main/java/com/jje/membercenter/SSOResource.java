package com.jje.membercenter;

import com.jje.common.utils.MD5Utils;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.*;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.MemberScoreRedeem;
import com.jje.membercenter.domain.MemberScoreRedeemRepository;
import com.jje.membercenter.persistence.cache.PwdValidateNumCache;
import com.jje.membercenter.persistence.cache.SSOTicketCache;
import com.jje.membercenter.persistence.cache.ValidatePwdCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/sso")
@Component
public class SSOResource {
    private static final Logger LOG = LoggerFactory.getLogger(SSOResource.class);
    @Autowired
    CRMMembershipRepository crmMembershipRepository;

    @Autowired
    MemberScoreRedeemRepository memberScoreRedeemRepository;

    @Autowired
    SSOTicketCache ticketCache;

    @Autowired
    PwdValidateNumCache pValidateNumCache;

    @Autowired
    ValidatePwdCache validateNumCache;

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMember")
    public Response getMember(String memberId) throws Exception {
    	LOG.warn("------SSOResource.getMember memberId="+memberId);
        SSOMemberDto dto = crmMembershipRepository.getMember(memberId);
        if (dto == null) {
        	LOG.warn("SSOMemberDto is null");
            return Response.status(Status.NO_CONTENT).build();
        }
        return Response.ok().entity(dto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/reduceScore")
    public Response reduceScore(SSORedeemDto ssoRedeemDto) throws Exception {
        String code = crmMembershipRepository.reduceScore(ssoRedeemDto);
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Response><Code>201</Code><Desc>Success.</Desc></Response>";
        LOG.warn("reduceScore code="+code);
        if (str.equals(code)) {
            storeScoreRedeem(ssoRedeemDto);
        }
        return Response.ok().entity(code).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/addScoreRedeem")
    public Response addScoreRedeem(SSORedeemDto ssoRedeemDto) throws Exception {
        storeScoreRedeem(ssoRedeemDto);
        return Response.ok().build();
    }

    private void storeScoreRedeem(SSORedeemDto ssoRedeemDto) throws Exception {
        MemberScoreRedeem memberScoreRedeem = new MemberScoreRedeem(ssoRedeemDto);
        memberScoreRedeemRepository.addScoreRedeem(memberScoreRedeem);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/reconciled")
    public Response reconciled(SSOReconcileDto ssoReconcileDto) throws Exception {
    	LOG.warn("sTime="+ssoReconcileDto.getsTime()+" eTime"+ssoReconcileDto.geteTime());
        MemberScoreRedeem memberScoreRedeem = new MemberScoreRedeem();
        memberScoreRedeem.setsTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(ssoReconcileDto.getsTime()));
        memberScoreRedeem.seteTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(ssoReconcileDto.geteTime()));
        List<MemberScoreRedeem> list = memberScoreRedeemRepository.queryRedeem(memberScoreRedeem);
        ResultMemberDto<SSOReconciliationDto> resultDto = new ResultMemberDto<SSOReconciliationDto>();
        List<SSOReconciliationDto> listDtos = new ArrayList<SSOReconciliationDto>();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                SSOReconciliationDto dto = new SSOReconciliationDto();
                dto.setMemberId(list.get(i).getMemId());
                dto.setOrderId(list.get(i).getOrderId());
                dto.setProductcode(list.get(i).getPdCode());
                dto.setScore(list.get(i).getScore());
                dto.setTime(new SimpleDateFormat("yyyyMMddHHmmss").format(list.get(i).getTime()));
                dto.setMd5(MD5Utils.generatePassword(dto.getMemberId() +dto.getScore()+    dto.getOrderId() + dto.getProductcode() + dto.getTime() + "iskfdklaladfklladsnewrfi349sahjo"));
                listDtos.add(dto);
            }
        }
        resultDto.setResults(listDtos);
        return Response.ok().entity(resultDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/putTicketToCache")
    public Response putTicketToCache(SSOTicketDto ticketDto) throws Exception {
        LOG.warn("-----SSOResource.putTicketToCache() put one ticket to cache" + ticketDto.getTicket());
        ticketCache.putTicket(ticketDto);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getTicketFromCache")
    public Response getTicketFromCache(String ticket) throws Exception {
        LOG.warn("-----SSOResource.getTicketFromCache({}) get SSO ticket from cache" ,ticket);
        SSOTicketDto ticketDto = ticketCache.getTicket(ticket);
        if (ticketDto == null) {
        	LOG.warn("-----SSOResource.getTicketFromCache() ticketDto is null");
            return Response.noContent().entity(ticketDto).build();
        } else {
            return Response.ok().entity(ticketDto).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/putValueToCache")
    public Response putValueToCache(ValidateNumCacheDto cacheDto) throws Exception {
        LOG.warn("-----SSOResource.putValueToCache()-----put one value to cache" + cacheDto.getValidateNum()+" memberCode"+cacheDto.getMemberCode());
        pValidateNumCache.putValueToCache(cacheDto.getValidateNum(), cacheDto.getMemberCode());
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getValueFromCache")
    public Response getValueFromCache(String memberCode) throws Exception {
        LOG.warn("----SSOResource.getValueFromCache({}) get RecoverCode ticket from cache" ,memberCode);
        String validateNum = pValidateNumCache.getValueFromCache(memberCode);
        if (validateNum == null) {
        	LOG.warn("----SSOResource.getValueFromCache() validateNum is null");
            return Response.noContent().entity(validateNum).build();
        } else {
            return Response.ok().entity(validateNum).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/putPwdToCache")
    public Response putPwdToCache(ValidatePwdDto cacheDto) throws Exception {
        LOG.warn("-----SSOResource.putPwdToCache() put one value to cache" + cacheDto.getValidateNum() +" telPhone="+cacheDto.getTelPhone());
        validateNumCache.putValueToCache(cacheDto.getTelPhone(), cacheDto.getValidateNum());
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getPwdache")
    public Response getPwdache(String telPhone) throws Exception {
        LOG.debug("-----SSOResource.getPwdache({}) get RecoverCode ticket from cache" , telPhone);
        String validateNum = validateNumCache.getValueFromCache(telPhone);
        if (validateNum == null) {
        	LOG.warn("-----SSOResource.getPwdache({}) validateNum is null",telPhone);
            return Response.noContent().entity(validateNum).build();
        } else {
            return Response.ok().entity(validateNum).build();
        }
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/generaterTicket")
    public Response genTicket(MemberDto dto) {
    	SSOTicketDto ticketDto = new SSOTicketDto();
    	ticketDto.setMember(dto);
		ticketDto.setTicket(UUID.randomUUID().toString());
		try {
			putTicketToCache(ticketDto);
		} catch (Exception e) {
			return  Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok().entity(ticketDto.getTicket()).build();
    }
   
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/removeTicket/{key}")
    public Response removeTicket(@PathParam("key") String key) {
    	try{
    		ticketCache.removeTicket(key);
    	} catch (Exception e) {
    		Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	}
    	return Response.ok().entity("OK").build();
    }
   
}
