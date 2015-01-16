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

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberAddressDto;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.service.MemberService;


@Path("membermove")
@Component
public class MemberMoveResource {

    @Autowired
    private CRMMembershipRepository crmMembershipRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    MemberService memberService;
    
    //设定首选地址
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/firstMemberAddress")
    public Response firstMemberMoveAddress(ResultMemberDto<MemberAddressDto> addressDtos) {
        if (logger.isDebugEnabled())
            logger.debug("------start MemberMoveResource.firstMemberMoveAddress({})-------", addressDtos.getResults());
        String rcode = crmMembershipRepository.firstMemberAddress(addressDtos.getResults());
        return Response.status(Status.OK).entity(rcode).build();
    }

    //首选地址添加
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/saveFirstMemberAddress")
    public Response saveFirstMemberMoveAddress(ResultMemberDto<MemberAddressDto> addressDtos) {
        if (logger.isDebugEnabled())
            logger.debug("------start MemberMoveResource.saveFirstMemberMoveAddress({})-------", addressDtos.getResults());
        String rcode = crmMembershipRepository.saveMemberAddress(addressDtos.getResults());
        return Response.status(Status.OK).entity(rcode).build();
    }

    //基本信息更新
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/updateBaseInfo")
    public Response updateMemberMoveBasicInfo(MemberBasicInfoDto basicInfoDto) {
    	logger.info("------start MemberMoveResource.updateMemberMoveBasicInfo ({})", JaxbUtils.convertToXmlString(basicInfoDto));
        String rcode = crmMembershipRepository.updateMemberMoveBaseInfo(basicInfoDto);
        logger.info("------MemberMoveResource.updateMemberMoveBasicInfo() retCode = " + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }
    
    //手机锦江之星会员激活
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/active")
    public Response active(MemberBasicInfoDto basicInfoDto) {
    	basicInfoDto.setRegistChannel(RegistChannel.Mobile);
    	logger.info("------start MemberMoveResource.active ({})", JaxbUtils.convertToXmlString(basicInfoDto));
        String rcode = memberService.activeMember(basicInfoDto);
        logger.info("------MemberMoveResource.active() retCode = " + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }
}