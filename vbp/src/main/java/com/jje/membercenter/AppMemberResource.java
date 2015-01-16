package com.jje.membercenter;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.AppLoginMemberDto;
import com.jje.dto.membercenter.AppMemberDto;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.ValidationDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.domain.AppMember;
import com.jje.membercenter.domain.ConfigRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.service.MemberService;
import com.jje.vbp.handler.EbpHandler;

@Path("appMember")
@Component
public class AppMemberResource {

    @Autowired
    private MemberRepository memberRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebMemberRepository webMemberRepository;

    @Autowired
    MemberScoreLevelInfoRepository memberScoreLevelInfoRepository;

    @Autowired
    private ConfigRepository configRepository;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private EbpHandler ebpHandler;
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/mobileMemberLogin")
    public Response mobileMemberLogin(ValidationDto validationDto) {
    	AppLoginMemberDto loginMemberDto =  new AppLoginMemberDto();
    	try {
            logger.warn("--------start AppMemberResource.mobileMemberLogin(usernameOrCellphoneOrEmail {},pwd {})------", validationDto.getUsernameOrCellphoneOrEmail(), validationDto.getPassword());
            Member crmMember = memberRepository.queryByUsernameOrCellphoneOrEmail(validationDto.getUsernameOrCellphoneOrEmail());
            if (crmMember != null) {
                if (crmMember.validate(validationDto.getPassword())) {
                    //loginMemberDto.setIsTempMember(false);
                	loginMemberDto.setIsTempMember(crmMember.getIsWebMember());
                    loginMemberDto.setMcMemberCode(crmMember.getMcMemberCode());
                    ebpHandler.sendAppLoginEvent(crmMember.getMcMemberCode(), false);
                    return Response.status(Status.OK).entity(loginMemberDto).build();
                }
            }
        } catch (Exception e) {
            logger.error("-------AppMemberResource.mobileMemberLogin------ ERROR: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        loginMemberDto.setLoginStatus("-1");
        return Response.status(Status.OK).entity(loginMemberDto).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getAppMember/{mcMemberCode}/{flag}")
    public Response getAppMemberByCode(@PathParam("mcMemberCode") String mcMemberCode, @PathParam("flag") Boolean flag) {
    	AppMemberDto appMemberDto=new AppMemberDto();
        try {
            AppMember member = memberRepository.getAppMemberByMcMemberCode(mcMemberCode, flag);
            if (member == null) {
                logger.warn("--------AppMemberResource.getAppMemberByCode(mcCode {},flag {}) result is null!!------", mcMemberCode, flag);
                return Response.status(Status.NOT_FOUND).build();
            }
            appMemberDto=member.toDto();
            //调用积分接口如果是临时会员直接返回
//            if(!flag){
                MemberScoreLevelInfoDto memberScoreLevelInfoDto= this.getMemberScoreLevelInfoDto(mcMemberCode);
                appMemberDto.setMemberScoreLevelInfoDto(memberScoreLevelInfoDto);
//            }
                
            String cardNo = appMemberDto.getCardNo();
            MemberMemCard memberMemCard = memberRepository.getCardInfoByCardno(cardNo);
            MemberMemCardDto memberMemCardDto = new MemberMemCardDto();
            BeanUtils.copyProperties(memberMemCard, memberMemCardDto);
            appMemberDto.setMemberMemCardDto(memberMemCardDto);
            
                
            return Response.ok().entity(appMemberDto).build();
        } catch (Exception e) {
            logger.error("-----get member info by mcMemberCode error :mcMemberCode={},flag=" + flag, mcMemberCode, e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

	private MemberScoreLevelInfoDto getMemberScoreLevelInfoDto(
			String mcMemberCode) {
		 try {
	            MemberScoreLevelInfoDto memberScoreLevelInfoDto = memberScoreLevelInfoRepository.getMemberScoreInfo(String.valueOf(memberService.getMemberInfoIdByMcMemberCode(mcMemberCode)));
	            if (memberScoreLevelInfoDto != null) {
	                MemberDegree level = MemberDegree.getMemberDegree(memberScoreLevelInfoDto.getScoreLevel() + "");
	                if (level != null) {
	                    memberScoreLevelInfoDto.setUpdateScore(configRepository.getUpgradeScores(level));
	                    memberScoreLevelInfoDto.setUpdateTime(configRepository.getUpgradeNumber(level));
	                }
	            }else {
	                logger.warn("--------ScoreResource.getMemberScoreInfo({}) memberScoreLevelInfoDto is null!!---------",mcMemberCode);
	            }
	            return memberScoreLevelInfoDto;
	        } catch (Exception e) {
	            logger.error("========= ScoreResource.MemberScoreInfo error ==========", e);
	            return null;
	        }
	}


}