package com.jje.vbp.thirdparty;

import java.util.Date;
import java.util.List;

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

import com.jje.common.bam.BamDataCollector;
import com.jje.common.bam.BamMessage;
import com.jje.common.bam.StatusCode;
import com.jje.common.utils.DateUtils;
import com.jje.common.utils.ExceptionToString;
import com.jje.common.utils.JaxbUtils;
import com.jje.dto.member.thirdParty.GoldpalMemberQueryDto;
import com.jje.dto.member.thirdParty.GoldpalMemberQueryResponseDto;
import com.jje.dto.member.thirdParty.GoldpalMemberRegiestDto;
import com.jje.dto.member.thirdParty.GoldpalMemberRegiestResponseDto;
import com.jje.membercenter.remote.crm.datagram.request.GoldpalMemberQueryReq;
import com.jje.membercenter.remote.crm.datagram.request.GoldpalMemberRegiestReq;
import com.jje.membercenter.remote.crm.datagram.response.GoldpalMemberQueryRes;
import com.jje.membercenter.remote.crm.datagram.response.GoldpalMemberRegiestRes;
import com.jje.membercenter.remote.crm.datagram.response.GoldpalMemberQueryRes.Membercard;
import com.jje.membercenter.remote.crm.support.CrmSysType;


@Path("/goldPal")
@Component
public class GoldpalResource {
	
	private Logger logger = LoggerFactory.getLogger(GoldpalResource.class);
	
	@Autowired
  private BamDataCollector bamDataCollector;
	
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response goldpalRegiest(GoldpalMemberRegiestDto memberDto) throws Exception{
		GoldpalMemberRegiestRes res = new GoldpalMemberRegiestRes();
		try{ 	
			logger.info("goldpalRegiest start  paramet GoldpalMemberRegiestDto {}",memberDto);
			GoldpalMemberRegiestReq req = getGoldpalRegistReq(memberDto);
			res=req.send();
			logger.info("goldpalRegiest end paramet GoldpalMemberRegiestRes {}",res);
			if(!res.isStatus(com.jje.membercenter.remote.crm.support.CrmResponse.Status.SUCCESS)){
				logger.error("goldpalRegiest invoke CRM FAIL reason {}",res.getHead().getRetmsg());
				bamDataCollector.sendMessage(new BamMessage("vbp.goldPal.register", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberDto), JaxbUtils.convertToXmlString(res)));
				return Response.status(Status.OK).entity(new  GoldpalMemberRegiestResponseDto(res.getHead().getRetcode(),res.getHead().getRetmsg())).build();
			}
			bamDataCollector.sendMessage(new BamMessage("vbp.goldPal.register", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberDto), JaxbUtils.convertToXmlString(res)));
		}catch (Exception e) {
			logger.error("goldpal register error! GoldpalMemberRegiestDto: {}",memberDto, e);
			bamDataCollector.sendMessage(new BamMessage("vbp.goldPal.register", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberDto), ExceptionToString.toString(e)));
			return Response.status(Status.OK).entity(GoldpalMemberRegiestResponseDto.ERROR).build();
		}	
		return Response.status(Status.OK).entity(new  GoldpalMemberRegiestResponseDto(res.getHead().getRetcode(),"SUCCESS",res.getBody().getMembid(),res.getBody().getCardnum() )).build();
	}
	
	
	private GoldpalMemberRegiestReq getGoldpalRegistReq(GoldpalMemberRegiestDto memberDto) {
		
		GoldpalMemberRegiestReq req = new GoldpalMemberRegiestReq();
		
		GoldpalMemberRegiestReq.Listofcontact listofcontact = new GoldpalMemberRegiestReq.Listofcontact();
		
		GoldpalMemberRegiestReq.Listofpersonaladdress listofpersonaladdress = new GoldpalMemberRegiestReq.Listofpersonaladdress();
		GoldpalMemberRegiestReq.Personaladdress personaladdress = new GoldpalMemberRegiestReq.Personaladdress("Y",memberDto.getAddressType(),memberDto.getProvince(),
															memberDto.getCity(),memberDto.getArea(),memberDto.getStreetaddr(),memberDto.getPostcode(),memberDto.getNation());
		listofpersonaladdress.getPersonaladdress().add(personaladdress);
		
		listofcontact.getContact().add(new GoldpalMemberRegiestReq.Contact("Y",memberDto.getSex(),memberDto.getTitle(),memberDto.getNation(),memberDto.getIdentityType(),memberDto.getIdentityNo(),
									   memberDto.getEmail(),memberDto.getMobile(),"",CrmSysType.JJE_TBUSINESS.getCode(),listofpersonaladdress));
		
		GoldpalMemberRegiestReq.Listofmembercard listofmembercard = new GoldpalMemberRegiestReq.Listofmembercard();
		
		listofmembercard.getMembercard().add(new GoldpalMemberRegiestReq.Membercard(memberDto.getMemberCardNo(),memberDto.getMembCarddType(),"","",memberDto.getStartdate(),memberDto.getEndDate(),memberDto.getMembCardSource(),memberDto.getMembCardStatus(),memberDto.getAccpatpflg()));
		
		GoldpalMemberRegiestReq.Record record = new GoldpalMemberRegiestReq.Record(memberDto.getName(),memberDto.getMemberType(),DateUtils.formatDate(new Date(), "MM/dd/yyyy hh:mm:ss"),"Store"
																				   ,"Active"," ",memberDto.getSalesmanId(),memberDto.getCddesp(), memberDto.getRetailerId(),listofcontact,listofmembercard);
		record.setListofmembercard(listofmembercard);
		record.setListofcontact(listofcontact);		
		req.getBody().setRecord(record);
		
		return req;
	}
	
	@POST
	@Path("/query")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response goldpalQuery(GoldpalMemberQueryDto memberDto){
		GoldpalMemberQueryRes res = null;
		try {
			logger.info("goldpalQuery start  paramet GoldpalMemberQueryDto {}",memberDto);
			GoldpalMemberQueryReq req = getGoldpalQueryReq(memberDto);
			res=req.send();
			logger.info("goldpalQuery start  paramet GoldpalMemberQueryDto {}",res);
			if(res == null||!res.isStatus(com.jje.membercenter.remote.crm.support.CrmResponse.Status.SUCCESS)){
			    return Response.status(Status.OK).entity(new GoldpalMemberQueryResponseDto(res.getHead().getRetcode(),res.getHead().getRetmsg())).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.OK).entity(GoldpalMemberQueryResponseDto.ERROR).build();
		}
		return Response.status(Status.OK).entity(getGoldpalQueryData(res)).build();
	}
	
	
	private GoldpalMemberQueryReq getGoldpalQueryReq(GoldpalMemberQueryDto memberDto){
		GoldpalMemberQueryReq req = new GoldpalMemberQueryReq();
		GoldpalMemberQueryReq.RequestBody  requestBody = req.getBody();
		requestBody.setName(memberDto.getName());
		requestBody.setCardid(memberDto.getIdentityNo());
		requestBody.setCardtype(memberDto.getIdentityType());
		requestBody.setMembcdtype(memberDto.getMemberCardType());
		requestBody.setMemcd(memberDto.getMemberCardNo());
		requestBody.setEmail(memberDto.getEmail());
		requestBody.setMobile(memberDto.getMobile());
		req.setBody(requestBody);
		return req;
	}
	
	
	private GoldpalMemberQueryResponseDto getGoldpalQueryData(GoldpalMemberQueryRes res){
		GoldpalMemberQueryResponseDto responseDto = new GoldpalMemberQueryResponseDto();
		Membercard  membercard = res.getBody().getRecord().getListofmembercard().getMembercard().get(0);
		GoldpalMemberQueryRes.Record record = res.getBody().getRecord();
		GoldpalMemberQueryRes.Contact contact = res.getBody().getRecord().getListofcontact().getContact().get(0);
		responseDto.setAccpatpflg(membercard.getAccpatpflg());
		responseDto.setCddesp(record.getCddesp());
		responseDto.setEmail(contact.getEmail());
		responseDto.setEndDate(membercard.getEnddate());
		responseDto.setIdentityNo(contact.getCardid());
		responseDto.setIdentityType(contact.getCardtype());
		responseDto.setLoypoint(record.getLoypoint());
		responseDto.setMembCarddType(membercard.getMembcdtype());
		responseDto.setMembCardSource(membercard.getMembcdsour());
		responseDto.setMembCardStatus(membercard.getMembcdstat());
		responseDto.setMemberType(record.getMembtype());
		responseDto.setMobile(contact.getMobile());
		responseDto.setName(record.getName());
		responseDto.setNation(contact.getNation());
		responseDto.setPartnercard(membercard.getPartnercard());
		responseDto.setRegiestChannl(record.getRegichnl());
		responseDto.setRegiestDate(record.getRegidate());
		responseDto.setReturnCode(res.getHead().getRetcode());
		responseDto.setReturnMessage(res.getHead().getRetmsg());
		responseDto.setSex(contact.getSex());
		responseDto.setStartdate(membercard.getStartdate());
		responseDto.setPoint2Value(record.getPoint2Value());
		responseDto.setPoint3Value(record.getPoint3Value());
		return responseDto;
	}
	

}
