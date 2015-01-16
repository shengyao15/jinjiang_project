package com.jje.membercenter.forcrm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.spi.NotAcceptableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.jje.common.bam.BamDataCollector;
import com.jje.common.bam.BamMessage;
import com.jje.common.bam.StatusCode;
import com.jje.common.utils.ExceptionToString;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.RestClient;
import com.jje.data.util.JsonUtils;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MergeMembersDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.forcrm.MemberForCRMReqDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMRespDto;
import com.jje.dto.mms.mmsmanage.MessageRespDto;
import com.jje.dto.nbp.SendType;
import com.jje.dto.nbp.TemplateModule;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.remote.handler.NBPHandler;
import com.jje.membercenter.service.MemberService;
@Path("membercenterinterface")
@Component
public class MemberCenterInterface {

	@Value(value = "${member.authorization.username}")
	private String authorizationUserName;

	@Value(value = "${member.authorization.password}")
	private String authorizationPassword;

	@Value(value = "${mbp.mms.url}")
	private String mmsService;

	@Value(value = "${sender.mail.message}")
	private String senderName;

	@Value(value = "${senderEmail.mail.message}")
	private String senderEmail;

	@Value(value = "${replyName.mail.message}")
	private String replyName;

	@Value(value = "${replyEmail.mail.message}")
	private String replyEmail;

	@Value(value = "${messageContent}")
	private String messageContent;

	@Value(value = "${emailSubject}")
	private String emailSubject;

	@Value(value = "${commonMemberTemplateNo}")
	private String commonMemberTemplateNo;

	@Value(value = "${xiangMember}")
	private String xiangMember;
	
	@Value(value = "${nbp.url}")
	private String nbp;

	@Autowired
	private MemberRepository memberRepository;

    @Autowired
    private NBPHandler nbpHandler;



    @Autowired
	private RestClient restClient;

	@Autowired
	MemberService memberService;
	
	@Autowired
  private BamDataCollector bamDataCollector;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/query")
	public Response queryMember(MemberForCRMReqDto memberForCRMReqDto) {
		MemberDto memberDto = memberForCRMReqDto.getMember();
		Member member = new Member(memberDto);
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		crmResp.setMemberId(member.getMemberID());

		try {

			if (!this.isAuthorization(memberForCRMReqDto.getAuthorizationUserName(),
					memberForCRMReqDto.getAuthorizationPassword())) {
				crmResp.setStatus("权限不足");
				return Response.status(Status.UNAUTHORIZED).entity(crmResp).build();
			}

			if (StringUtils.isEmpty(member.getMemberID())) {
				crmResp.setStatus("查询失败，传入的MemberID为空");
				return Response.status(Status.BAD_REQUEST).entity(crmResp).build();
			}

			List<Member> memberList = memberRepository.queryByMemberID(member.getMemberID());
			if (memberList == null) {
				crmResp.setStatus("0");
				return Response.status(Status.OK).entity(crmResp).build();
			}

			crmResp.setStatus(memberList.size() + "");
			return Response.status(Status.OK).entity(crmResp).build();
		} catch (Exception e) {
			logger.error("update member error:", e);
			crmResp.setStatus("数据查询失败");
			return Response.status(Status.BAD_REQUEST).entity(crmResp).build();
		}
	}

	//前台注册
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/store")
    public Response storeMember(MemberForCRMReqDto memberForCRMReqDto) {
        logger.warn("================callBack store=============email is {}====",memberForCRMReqDto.getMember().getEmail());
		MemberDto memberDto = memberForCRMReqDto.getMember();
		memberDto.setActiveDate(new Date());
		memberDto.setActiveChannel(memberDto.getRegisterSource());
		crmParameterLog(memberDto, "store--Member register");
		Member member = new Member(memberDto);
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		crmResp.setMemberId(member.getMemberID());// memberID唯一
		try {

			if (!this.isAuthorization(memberForCRMReqDto.getAuthorizationUserName(),
					memberForCRMReqDto.getAuthorizationPassword())) {
				crmResp.setStatus("权限不足");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.store", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.UNAUTHORIZED).entity(crmResp).build();
			}

			List<Member> memberList = memberRepository.queryByMemberID(member.getMemberID());
			if (memberList != null && memberList.size() != 0) {
				logger.warn("the record for the memberId has existed");
				crmResp.setStatus("数据已存在,无法插入");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.store", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.BAD_REQUEST).entity(crmResp).build();
			}

			if (memberDto.getLastUpd() == null) {
				member.setLastUpd(new Date());
			}
			// 事务处理
			member.setMcMemberCode(memberRepository.generateMcMemberCode());
			memberService.storeMember(member, memberDto);
			crmResp.setStatus("数据插入成功");
			
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.store", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
			return Response.status(Status.OK).entity(crmResp).build();
		} catch (Exception e) {
			logger.error("store member error:{}", JaxbUtils.convertToXmlString(memberForCRMReqDto), e);
			crmResp.setStatus("数据插入失败");
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.store", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), ExceptionToString.toString(e)));
			return Response.status(Status.CONFLICT).entity(crmResp).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/sendPassword")
	public Response sendPasswordByPhoneAndEmail(MemberDto memberDto) {
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		crmResp.setMemberId(memberDto.getMemberID());// memberID唯一
		try {
			crmResp.setStatus("发送密码到手机邮箱成功");
			MessageRespDto messageRespDto = new MessageRespDto();
			try {
				messageRespDto = sendPasswordByShortMessage(memberDto);
				if ("F".equals(messageRespDto.getStatus())) {
					logger.warn("发送密码到手机失败");
					crmResp.setStatus("发送密码到手机失败");
				}
			} catch (Exception e) {
				logger.error("发送密码到手机异常:", e);
				crmResp.setStatus("发送密码到手机异常");
			}
			try {
				messageRespDto = sendPasswordByEmail(memberDto);
				if ("F".equals(messageRespDto.getStatus())) {
					logger.warn("发送密码到邮箱失败");
					crmResp.setStatus("发送密码到邮箱失败");
				}
			} catch (Exception e) {
				logger.error("发送密码到邮箱异常:", e);
				crmResp.setStatus("发送密码到邮箱异常");
			}
			return Response.status(Status.OK).entity(crmResp).build();
		} catch (Exception e) {
			logger.error("sendPassword exception:", e);
			crmResp.setStatus("发送密码异常");
			return Response.status(Status.CONFLICT).entity(crmResp).build();
		}
	}



    private MemberForCRMRespDto updateMemberInfoAndVerifyForMove(MemberForCRMReqDto memberForCRMReqDto) {
		MemberDto dto = memberForCRMReqDto.getMember();
		dto.setActiveDate(new Date());	//添加激活时间
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		crmResp.setMemberId(dto.getMemberID());
		try {
			memberService.updateMemberInfoAndVerifyForMove(dto);
			crmResp.setStatus("");
			return crmResp;
		} catch (Exception ex) {
			logger.error("update member error:", ex);
			crmResp.setStatus("数据更新失败(move)");
			return crmResp;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/delete")
	public Response deleteMember(MemberForCRMReqDto memberForCRMReqDto) {
		Member member = new Member(memberForCRMReqDto.getMember());
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		crmResp.setMemberId(member.getMemberID());
		try {
			if (!this.isAuthorization(memberForCRMReqDto.getAuthorizationUserName(),
					memberForCRMReqDto.getAuthorizationPassword())) {
				crmResp.setStatus("权限不足");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.delete", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.UNAUTHORIZED).entity(crmResp).build();
			}
			if (member.getMemberID() == null && "".equals(member.getMemberID())) {
				crmResp.setStatus("数据格式错误");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.delete", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.BAD_REQUEST).entity(crmResp).build();
			}
			List<Member> memberList = memberRepository.queryByMemberID(member.getMemberID());
			crmResp.setStatus("数据删除成功");
			if (memberList.size() == 0 || memberList == null) {
				crmResp.setStatus("数据不存在,无法删除");
				// return
				// Response.status(Status.BAD_REQUEST).entity(crmResp).build();
			}
			memberRepository.deleteMemberByMemberId(member.getMemberID());
			
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.delete", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
			return Response.status(Status.OK).entity(crmResp).build();
		} catch (Exception e) {
			logger.error("delete member error:{}", JaxbUtils.convertToXmlString(memberForCRMReqDto), e);
			crmResp.setStatus("数据删除失败");
			
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.delete", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), ExceptionToString.toString(e)));
			return Response.status(Status.NOT_ACCEPTABLE).entity(crmResp).build();
		}
	}

	//更新
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/update")
	public Response updateMember(MemberForCRMReqDto memberForCRMReqDto) {
		crmParameterLog(memberForCRMReqDto.getMember(), "update--Member modify");
		MemberDto memberDto = memberForCRMReqDto.getMember();

		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		crmResp.setMemberId(memberDto.getMemberID());
		try {
			if (!this.isAuthorization(memberForCRMReqDto.getAuthorizationUserName(),
					memberForCRMReqDto.getAuthorizationPassword())) {
				crmResp.setStatus("权限不足");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.update", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.UNAUTHORIZED).entity(crmResp).build();
			}
			if (StringUtils.isBlank(memberDto.getMemberID())) {
				logger.warn("memberId is null");
				crmResp.setStatus("数据格式错误，MemberId不能为空");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.update", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.BAD_REQUEST).entity(crmResp).build();
			}

			List<Member> memberList = memberRepository.queryByMemberID(memberDto.getMemberID());
			crmResp.setStatus("数据更新成功");
			if (memberList == null || memberList.size() == 0) {
				logger.warn("the record for the memberId is not existed");
				crmResp.setStatus("NoMember");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.update", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.OK).entity(crmResp).build();
			}
			fillMemberDto(memberDto, memberList);//填充memberDto信息
			if (CollectionUtils.isEmpty(memberList.get(0).getMemberVerfyList())) {
				//网站/手机完善信息 / 立即加入迁移流程 时，激活锦江之星会员
				MemberForCRMRespDto crmDto = updateMemberInfoAndVerifyForMove(memberForCRMReqDto);
				if (crmDto.getStatus() == null || "".equals(crmDto.getStatus())) {
					crmDto.setStatus("迁移成功");
					bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.update", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmDto)));
					return Response.status(Status.OK).entity(crmDto).build();
				}
				return Response.status(Status.NOT_ACCEPTABLE).entity(crmDto).build();
			} else {
				memberRepository.updateMemberInfoAndVerify(new Member(memberDto));
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.update", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.OK).entity(crmResp).build();
			}
		} catch (Exception e) {
			logger.error("update member error:{}", JaxbUtils.convertToXmlString(memberForCRMReqDto), e);
			crmResp.setStatus("数据更新失败");
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.update", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), ExceptionToString.toString(e)));
			return Response.status(Status.NOT_ACCEPTABLE).entity(crmResp).build();
		}
	}
	
	private void fillMemberDto(MemberDto memberDto, List<Member> memberList) {
		if (StringUtils.isBlank(memberDto.getMemberCode())) { 
			memberDto.setMemberCode(memberList.get(0).getMemberCode());
		}
		if (memberDto.getId() == 0) {
			memberDto.setId(memberList.get(0).getId());
		}
		if(StringUtils.isBlank(memberDto.getPassword())) {
			memberDto.setPassword(memberList.get(0).getPassword());
		}
	}

	//升级续费
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/upgradeOrResume")
	public Response upgradeOrResume(MemberForCRMReqDto memberForCRMReqDto) {
		MemberDto memberDto = memberForCRMReqDto.getMember();
		crmParameterLog(memberDto, "upgradeOrResume--Member upgradeOrResume");
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		crmResp.setMemberId(memberDto.getMemberID());// memberID唯一

		try {

			if (!this.isAuthorization(memberForCRMReqDto.getAuthorizationUserName(),
					memberForCRMReqDto.getAuthorizationPassword())) {
				crmResp.setStatus("权限不足");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.upgradeOrResume", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.UNAUTHORIZED).entity(crmResp).build();
			}

			if (memberDto.getMemberID() == null || "".equals(memberDto.getMemberID())) {
				logger.warn("memberId is null");
				crmResp.setStatus("数据格式错误，MemberId不能为空");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.upgradeOrResume", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.BAD_REQUEST).entity(crmResp).build();
			}

			List<MemberMemCard> memberMemCardList = memberRepository.queryMemberMemCardByMemberId(memberDto.getMemberID());

			crmResp.setStatus("数据更新成功");
			if (memberMemCardList == null || memberMemCardList.size() == 0) {
				logger.info("the record for the memberId is not existed");
				crmResp.setStatus("NoMember");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.upgradeOrResume", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
				return Response.status(Status.OK).entity(crmResp).build();
			}
			memberRepository.updateMemberInfoAndCard(memberDto);
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.upgradeOrResume", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
			return Response.status(Status.OK).entity(crmResp).build();
		} catch (Exception e) {
			logger.error("upgradeOrResume member error:", e);
			crmResp.setStatus("数据插入失败");
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.upgradeOrResume", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), ExceptionToString.toString(e)));
			return Response.status(Status.CONFLICT).entity(crmResp).build();
		}
	}

	public boolean isAuthorization(String userName, String password) {
		if (authorizationUserName.equals(userName.trim()) && authorizationPassword.equals(password.trim())) {
			return true;
		}
		return false;
	}

	private void crmParameterLog(MemberDto memberDto, String method) {
		StringBuffer strs = new StringBuffer();
		strs.append("****** crmParameterLog" + this.getClass().toString()).append(",");
		strs.append(method).append(":");
		strs.append("id=" + memberDto.getId()).append(",");
		strs.append("memberId=" + memberDto.getMemberID()).append(",");
		strs.append("cellPhone=" + memberDto.getCellPhone()).append(",");
		strs.append("email=" + memberDto.getEmail()).append(",");
		strs.append("activateCode=" + memberDto.getActivateCode()).append(",");
		strs.append("activeChannel=" + memberDto.getActiveChannel()).append(",");
		strs.append("memberCode=" + memberDto.getMemberCode()).append(",");
		strs.append("cardNo=" + memberDto.getCardNo()).append(",");
		strs.append("cardLevel=" + memberDto.getCardLevel()).append(",");
		strs.append("fullName=" + memberDto.getFullName()).append(",");
		strs.append("userName=" + memberDto.getUserName()).append(",");
		strs.append("identityNo=" + memberDto.getIdentityNo()).append(",");
		strs.append("identityType=" + memberDto.getIdentityType()).append(",");
		strs.append("memberHierarchy=" + memberDto.getMemberHierarchy()).append(",");
		strs.append("memberHierarchyName=" + memberDto.getMemberHierarchyName()).append(",");
		strs.append("cardType=" + memberDto.getCardType()).append(",");
		strs.append("memberType=" + memberDto.getMemberType()).append(",");
		strs.append("password=" + memberDto.getPassword()).append(",");
		strs.append("registerSource=" + memberDto.getRegisterSource()).append(",");
		strs.append("remindQuestion=" + memberDto.getRemindQuestion()).append(",");
		strs.append("remindAnswer=" + memberDto.getRemindAnswer()).append(",");
		strs.append("status=" + memberDto.getStatus()).append(",");
		strs.append("title=" + memberDto.getTitle()).append(",");
		strs.append("ValidStartDate=" + memberDto.getValidStartDate()).append(",");
		strs.append("scoreType="+memberDto.getScoreType()).append(",");
		strs.append("ValidEndDate=" + memberDto.getValidEndDate()).append("-----").append("cardList:");
		List<MemberMemCardDto> memberMemCardDtoList = memberDto.getCardList();
		if (memberMemCardDtoList != null && memberMemCardDtoList.size() > 0) {
			for (MemberMemCardDto memberMemCardDto : memberMemCardDtoList) {
				strs.append("memberId=" + memberMemCardDto.getMemId()).append(",");
            strs.append("cardTypeCd=" + memberMemCardDto.getCardTypeCd()).append(",");
            strs.append("xCardNum=" + memberMemCardDto.getxCardNum()).append(",");
            strs.append("source=" + memberMemCardDto.getSource()).append(",");
            strs.append("status=" + memberMemCardDto.getStatus());
            strs.append("dueDate=" + memberMemCardDto.getDueDate());
            strs.append("validDate=" + memberMemCardDto.getValidDate());
			}
		}
		
		logger.warn(strs.toString());
	}

	private MessageRespDto sendPasswordByShortMessage(MemberDto memberDto) throws Exception {
        com.jje.dto.nbp.ShortMessageDto shortMessageDto = new com.jje.dto.nbp.ShortMessageDto();
        shortMessageDto.setSendSource("会员模块发密码到门店注册会员手机");
        shortMessageDto.setMobile(memberDto.getCellPhone());
        shortMessageDto.setSendType(com.jje.dto.nbp.SendType.PRODUCT.name());
        shortMessageDto.setModule(TemplateModule.MEMBER.name());
        shortMessageDto.setTemplateNo(commonMemberTemplateNo);
        Map context = new HashMap();
        if ("JJ Card".equals(memberDto.getCardType())) {
            context.put("fullName", memberDto.getFullName());
            context.put("cardNo", memberDto.getCardNo());
            context.put("password", memberDto.getPassword());
            shortMessageDto.setContext(JsonUtils.objectToJson(context));
        } else {
            if ("J Benefit Card".equals(memberDto.getCardType())) {
                context.put("cardType", "享卡");
            } else if ("J2 Benefit Card".equals(memberDto.getCardType())) {
                context.put("cardType", "悦享卡");
            } else if ("J8 Benefit Card".equals(memberDto.getCardType())) {
                context.put("cardType", "无限享卡");
            }
            context.put("fullName", memberDto.getFullName());
            context.put("cardNo", memberDto.getCardNo());
            context.put("password", memberDto.getPassword());
            shortMessageDto.setContext(JsonUtils.objectToJson(context));
        }
        shortMessageDto.setPriority(10);
        MessageRespDto messageRespDto = new MessageRespDto();
        messageRespDto = nbpHandler.sendShortMessage(shortMessageDto);
        return messageRespDto;
	}

	public MessageRespDto sendPasswordByEmail(MemberDto memberDto) throws Exception {
		JSONObject contextJson = new JSONObject();
		contextJson.put("password", memberDto.getPassword());
		
		 com.jje.dto.nbp.EmailMessageDto emailDto = new com.jje.dto.nbp.EmailMessageDto();
		 emailDto.setContext(contextJson.toString());
		 emailDto.setEmailReceiver(memberDto.getEmail());
		 emailDto.setModule(TemplateModule.MEMBER);
		 emailDto.setPriority(1);
		 emailDto.setSendSource("会员中心模块向门店注册会员发送密码到邮箱");
		 emailDto.setSendType(SendType.PRODUCT);
		 emailDto.setSubject(emailSubject);
		 emailDto.setTemplateNo("M_Stores_Member_Activate_Email");
		 emailDto.setSenderName(senderName);
		 emailDto.setSenderEmail(senderEmail);
		 emailDto.setReplyName(replyName);
		 emailDto.setReplyEmail(replyEmail);
		
		MessageRespDto messageRespDto = new MessageRespDto();
		try {
			restClient.post(nbp + "/mail/sendSingleMail", emailDto);
			messageRespDto.setStatus(messageRespDto.STATUS_SUCCESS);
		} catch (Exception e) {
			messageRespDto.setStatus(messageRespDto.STATUS_FAIL);
			messageRespDto.setRemark(e.getMessage());
		}
		
		return messageRespDto;
	}

	//门店注册
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/add")
	public Response addMember(MemberForCRMReqDto memberForCRMReqDto) {

		MemberDto memberDto = memberForCRMReqDto.getMember();
        memberDto.setPassword(null);
        memberDto.setActiveDate(new Date());
        memberDto.setActiveChannel(RegistChannel.Store.name());
		if (logger.isInfoEnabled()) {
			logger.debug("addOrTransferMemberInfo:" + JaxbUtils.convertToXmlString(memberForCRMReqDto));
			crmParameterLog(memberDto, "add--Member register");
		}

		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		crmResp.setMemberId(memberDto.getMemberID());// memberID唯一

		if (!this.isAuthorization(memberForCRMReqDto.getAuthorizationUserName(), memberForCRMReqDto.getAuthorizationPassword())) {
			crmResp.setStatus("权限不足");
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.add", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
			return Response.status(Status.UNAUTHORIZED).entity(crmResp).build();
		}

		try {
			memberService.addOrTransferMemberInfo(memberDto);
			crmResp.setStatus("数据插入成功");
			logger.debug("会员" + memberDto.getMemberCode() + "数据插入成功");
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.add", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberForCRMReqDto), JaxbUtils.convertToXmlString(crmResp)));
			return Response.status(Status.OK).entity(crmResp).build();
		} catch (NotAcceptableException e) {
			crmResp.setStatus(e.getMessage());
			logger.error("store member notAcceptable:", e);
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.add", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), ExceptionToString.toString(e)));
			return Response.status(Status.NOT_ACCEPTABLE).entity(crmResp).build();
		} catch (Exception e) {
			crmResp.setStatus("数据插入失败");
			logger.error("store member error:", e);
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.add", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberForCRMReqDto), ExceptionToString.toString(e)));
			return Response.status(Status.CONFLICT).entity(crmResp).build();
		}
	}
	
	

	//合并会员
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/mergeMember")
	public Response mergeMember(MergeMembersDto mergeMembersDto) {
		logger.warn("-----mergeMember  info:----"+JaxbUtils.convertToXmlString(mergeMembersDto));
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		try {
			
			List<MemberDto>  mems = mergeMembersDto.getMembers();
			if(!mems.isEmpty()){
				memberService.updateMergeMember(mems);
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.mergeMember", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(mergeMembersDto), JaxbUtils.convertToXmlString(crmResp)));
			}else{
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.mergeMember", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(mergeMembersDto), JaxbUtils.convertToXmlString(crmResp)));
			}
			crmResp.setStatus("用户合并成功");
		} catch (Exception e) {		
			crmResp.setStatus("用户合并失败 ："+e.getMessage());
			logger.error("-------mergeMember error:", e);
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.mergeMember", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(mergeMembersDto), ExceptionToString.toString(e)));
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(crmResp).build();			
		}
		return Response.status(Status.OK).entity(crmResp).build();
		
	}
	
	//激活会员
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/activeMember")
	public Response activeMember(MemberDto dto) {
		logger.warn("-----activeMember  info:----"+JaxbUtils.convertToXmlString(dto));
		dto.setActiveDate(new Date());
		dto.setActiveChannel(RegistChannel.CallCenter.name());
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		try {	
			if(StringUtils.isEmpty(dto.getCellPhone())&& StringUtils.isEmpty(dto.getEmail())){
				crmResp.setStatus("用户手机号,邮箱不能都为空");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.activeMember", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(crmResp)));
			}else{
				//CRM 激活按钮时 激活用户
				memberService.storeActiveMember(dto);
				crmResp.setStatus("用户激活成功");
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.activeMember", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(crmResp)));
			}
		} catch (Exception e) {		
			crmResp.setStatus("用户激活失败 ："+e.getMessage());
			logger.error("-------activeMember error:", e);
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.activeMember", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(dto), ExceptionToString.toString(e)));
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(crmResp).build();			
		}
		logger.warn("-----activeMember  info: response dto ----"+JaxbUtils.convertToXmlString(crmResp));
		return Response.status(Status.OK).entity(crmResp).build();
		
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/upgradeIssuedCouponForStore")
    public Response upgradeIssuedCouponForStore(MemberDto memberDto) {
        logger.warn("-----upgradeIssuedCouponForStore  info:----"+JaxbUtils.convertToXmlString(memberDto));
        MemberForCRMRespDto memberForCRMRespDto = new MemberForCRMRespDto();
        memberForCRMRespDto.setMemberId(memberDto.getMemberID());
        try {
            CouponSysIssueResult couponSysIssueResult = memberService.upgradeIssuedCouponForStore(memberDto);
            memberForCRMRespDto.setStatus(couponSysIssueResult.getResponseMessage().getMessage());
            bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.upgradeIssuedCouponForStore", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberDto), JaxbUtils.convertToXmlString(memberForCRMRespDto)));
            return Response.status(Status.OK).entity(memberForCRMRespDto).build();
        } catch (Exception e) {
             logger.error("会员升级,发放优惠券出错", e);
             memberForCRMRespDto.setStatus("会员升级,发放优惠券出错" + e.getMessage());
             bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.upgradeIssuedCouponForStore", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberDto), ExceptionToString.toString(e)));
             return Response.status(Status.INTERNAL_SERVER_ERROR).entity(memberForCRMRespDto).build();
        }
    }

}
