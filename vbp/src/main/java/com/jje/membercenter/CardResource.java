package com.jje.membercenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberVerfyDto;
import com.jje.dto.membercenter.ResumeCardDto;
import com.jje.dto.membercenter.VipCardInfoDto;
import com.jje.membercenter.domain.CRMResumeCardRepository;
import com.jje.membercenter.domain.CRMUpdateRightCardRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.persistence.WebMemberRepository;

@Path("card")
@Component
public class CardResource {
    private static final Logger LOG = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    private CRMUpdateRightCardRepository crmUpdateRightCardRepository;

    @Autowired
    private CRMResumeCardRepository crmResumeCardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WebMemberRepository webMemberRepository;

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listCard")
    public Response listCard(String memberId) {
        LOG.warn("--------start CardResource.listCard({})---------" ,memberId);
        ResultMemberDto<ResumeCardDto> resultDto = new ResultMemberDto<ResumeCardDto>();
        try {
            List<VipCardInfoDto> list = crmUpdateRightCardRepository.queryVIPCardInfo(memberId);
            List<ResumeCardDto> liCardDtos = new ArrayList<ResumeCardDto>();
            for (VipCardInfoDto dto : list) {
                ResumeCardDto dCardDto = new ResumeCardDto();
                dCardDto.setMemcardno(dto.getMemcardno());
                dCardDto.setMembid(dto.getMembid());
                dCardDto.setMembcdtype(dto.getMembcdtype());
                dCardDto.setMembcdstat(dto.getMembcdstat());
                dCardDto.setMembcdsour(dto.getMembcdsour());
                if (dto.getStartdate() != null) {
                    dCardDto.setStartdate(new SimpleDateFormat("MM/dd/yyyy").parse(dto.getStartdate()));
                }
                if (dto.getEnddate() != null) {
                    dCardDto.setEnddate(new SimpleDateFormat("MM/dd/yyyy").parse(dto.getEnddate()));
                }
                dCardDto.setJjmemcardno(dto.getJjmemcardno());
                dCardDto.setVipflag(dto.getVipflag());
                dCardDto.setListofhistory(dto.getListofhistory());
                liCardDtos.add(dCardDto);
            }
            resultDto.setResults(liCardDtos);
            return Response.status(Status.OK).entity(resultDto).build();
        } catch (Exception e) {
            LOG.error("--------CardResource.listCard({})-----调用CRM权益卡查询接口出错:" + e,memberId);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/resumeCard")
    public Response resumeCard(ResumeCardDto resumeCardDto) {
        try {
            LOG.warn("--------start CardResource.resumeCard(memcardno {})---------" ,resumeCardDto.getMemcardno());
            CRMResponseDto crmResponseDto = crmResumeCardRepository.resumeCard(resumeCardDto);
            return Response.status(Status.OK).entity(crmResponseDto).build();
        } catch (Exception e) {
            LOG.error("--------CardResource.resumeCard({})-----调用CRM会员卡续会接口出错:" + e,resumeCardDto.getMembid());
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/modifyQuestion")
    public Response modifyQuestion(MemberDto memberDto) {
        CRMResponseDto dto = new CRMResponseDto();
        try {
            LOG.warn("--------start CardResource.modifyQuestion(mobile {})---------" ,memberDto.getCellPhone());
            Member member = new Member();
            member.setId(memberDto.getId());
            member.setRemindQuestion(memberDto.getRemindQuestion());
            member.setRemindAnswer(memberDto.getRemindAnswer());
            memberRepository.updateQuestion(member);
            dto.setRetcode("1");
            dto.setRetmsg("SUCCESS");
            LOG.warn("--------end CardResource.modifyQuestion(mobile {})---------" ,memberDto.getCellPhone());
        } catch (Exception e) {
            LOG.error("-------- CardResource.modifyQuestion(mobile {})--error!!--保存密保问题出错：" + e.getMessage(),memberDto.getCellPhone(), e);
            dto.setRetcode("2");
            dto.setRetmsg("FALSE");
        }
        return Response.status(Status.OK).entity(dto).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/findPhoneOrMail")
    public Response findPhoneOrMail(String telPhoneOrMail) {
        LOG.warn("-------CardResource.telPhoneOrMail({})----",telPhoneOrMail);
        ResultMemberDto<MemberVerfyDto> resultDto = new ResultMemberDto<MemberVerfyDto>();
        try {
            List<MemberVerfy> list = memberRepository.queryRegisterByCellphoneOrEmailOrCardNo(telPhoneOrMail);
            List<MemberVerfyDto> listDto = new ArrayList<MemberVerfyDto>();
            for (MemberVerfy memberVerfy : list) {
                MemberVerfyDto dto = new MemberVerfyDto();
                dto = memberVerfy.toDto();
                listDto.add(dto);
            }
            resultDto.setResults(listDto);
            return Response.status(Status.OK).entity(resultDto).build();
        } catch (Exception e) {
            LOG.error("***** 查询数据库MEMBER_VERIFY表出错: *****" + e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
}
