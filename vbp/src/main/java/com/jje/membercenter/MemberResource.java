package com.jje.membercenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.spi.NotAcceptableException;
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
import com.jje.common.utils.MD5Utils;
import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.StatusDto;
import com.jje.dto.hotels.MasterCardRegInfoDto;
import com.jje.dto.membercenter.AddressDto;
import com.jje.dto.membercenter.BaseDataDto;
import com.jje.dto.membercenter.BaseResponse;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.CardDto;
import com.jje.dto.membercenter.ContactQqAndMsnDto;
import com.jje.dto.membercenter.MemberAddressDto;
import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.MemberCommunicationDto;
import com.jje.dto.membercenter.MemberCouponDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberHobbyDto;
import com.jje.dto.membercenter.MemberInfoAnswerDto;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberJREZQueryDto;
import com.jje.dto.membercenter.MemberJREZResultsDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MemberPrivilegeDto;
import com.jje.dto.membercenter.MemberQuickRegisterDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.dto.membercenter.MemberXmlDto;
import com.jje.dto.membercenter.PartnerName;
import com.jje.dto.membercenter.PrivilegeLogDto;
import com.jje.dto.membercenter.ReferrerInfoDto;
import com.jje.dto.membercenter.ReferrerRegistResult;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.SMSValidationDto;
import com.jje.dto.membercenter.TravellPreferenceDto;
import com.jje.dto.membercenter.UpdateBasicInfoResult;
import com.jje.dto.membercenter.ValidateCodeDto;
import com.jje.dto.membercenter.ValidateCodeMsgType;
import com.jje.dto.membercenter.ValidationDto;
import com.jje.dto.membercenter.VipCardInfoDto;
import com.jje.dto.membercenter.cardbind.CardBindStatusDto;
import com.jje.dto.membercenter.cardbind.CardExistStatus;
import com.jje.dto.membercenter.cardbind.CardExistStatusDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.dto.membercenter.cardbind.PartnerCardsListDto;
import com.jje.dto.vbp.sns.MemberContactsnsDto;
import com.jje.membercenter.domain.BaseData;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.CRMUpdateRightCardRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.membercenter.domain.MemberCoupon;
import com.jje.membercenter.domain.MemberCouponRepository;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;
import com.jje.membercenter.domain.OpRecordLogDomain;
import com.jje.membercenter.domain.OpRecordLogDomain.EnumOpType;
import com.jje.membercenter.domain.ValidateCode;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.persistence.OpRecordLogRepository;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.remote.crm.datagram.request.MemberPartnerCardRes;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.membercenter.remote.handler.NBPHandler;
import com.jje.membercenter.service.MemberService;
import com.jje.membercenter.service.MemberUpdateService;
import com.jje.membercenter.service.RegMemberService;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.membercenter.xsd.MemberRegisterResponse.Body;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.handler.EbpHandler;
import com.jje.vbp.validate.domain.ValidateCodeLog;
import com.jje.vbp.validate.domain.ValidateCodeLogRepository;

@Path("member")
@Component
public class MemberResource {
    private static final Logger LOG = LoggerFactory.getLogger(MemberResource.class);

    @Autowired
    private MemberXmlRepository memberXmlRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CRMUpdateRightCardRepository crmUpdateRightCardRepository;
    
    @Autowired
    private CRMMembershipRepository crmMembershipRepository;

    @Autowired
    private ValidateCodeLogRepository validateCodeLogRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    private WebMemberRepository webMemberRepository;

    @Autowired
    MemberHandler memberHandler;

    @Autowired
    MemberCardOrderRepository memberCardOrderRepositoryImpl;

    @Autowired
    MemberCouponRepository memberCouponRepositoryImpl;
    
    @Autowired
    MemberUpdateService memberUpdateService;
    
    @Autowired
	private CbpHandler cbpHandler;
    
    @Autowired
    private EbpHandler ebpHandler; 
    
    @Autowired
    private NBPHandler nbpHandler;
    
    @Autowired
    private BamDataCollector bamDataCollector;

    @Autowired
    private RegMemberService regMemberService;
    
    @Autowired
    private OpRecordLogRepository opRecordLogService;


    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/validate")
    public Response validate(ValidationDto validationDto) {
        try {
            String userName = validationDto.getUsernameOrCellphoneOrEmail();
			if (LOG.isInfoEnabled())
                LOG.info("------start MemberResource.validate()-------- 验证注册会员 usernameOrCellphoneOrEmail=" + userName);
            MemberDto memberDto = null;
            //生成合作卡登陆用户名
            userName = MemberAirLineCompany.getVerifyMemName(validationDto.getCollaborateCard(), userName);
            
            Member member = memberRepository.queryByUsernameOrCellphoneOrEmail(userName);
            if (member != null) {
                // 查询正式会员
                boolean result = member.validate(validationDto.getPassword());
                if (result) {
                    memberDto = member.toDto();
                    ebpHandler.sendWWWLoginEvent(memberDto.getMcMemberCode(), false);
                    LOG.info("正式会员验证通过 ");
                    return Response.status(Status.OK).entity(memberDto).build();
                }
            }
            if (LOG.isWarnEnabled())
                LOG.warn("-------MemberResource.validate({})-------没有找到匹配的用户信息", userName);
            return Response.status(Status.UNAUTHORIZED).build();
        } catch (Exception e) {
            LOG.error("登录发送错误：" + e.getMessage(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/validate4mgw")
    public Response validate4mgw(ValidationDto validationDto) {
        try {
            String userName = validationDto.getUsernameOrCellphoneOrEmail();
			if (LOG.isInfoEnabled())
                LOG.info("------start MemberResource.validate()-------- 验证注册会员 usernameOrCellphoneOrEmail=" + userName);
            //生成合作卡登陆用户名
            userName = MemberAirLineCompany.getVerifyMemName(validationDto.getCollaborateCard(), userName);
            
            Member member = memberRepository.queryByUsernameOrCellphoneOrEmail(userName);
            if (member != null) {
                // 查询正式会员
                boolean result = member.validate(validationDto.getPassword());
                if (result) {
                    return Response.status(Status.OK).entity("yes").build();
                }
            }
            if (LOG.isWarnEnabled())
                LOG.warn("-------MemberResource.validate({})-------没有找到匹配的用户信息", userName);
            return Response.status(Status.OK).entity("no").build();
        } catch (Exception e) {
            LOG.error("登录发送错误：" + e.getMessage(), e);
            return Response.status(Status.OK).entity("no").build();
        }
    }
    @GET
    @Path("/emailCheck/{email}")
    public Response emailCheck(@PathParam("email") String email) {
    	Member member = null;
    	try {
    		member = memberRepository.queryByUsernameOrCellphoneOrEmail(email);
    		if(member == null) {
    			return Response.status(Status.OK).entity(Boolean.TRUE).build();
    		} else {
    			return Response.status(Status.OK).entity(Boolean.FALSE).build();
    		}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
    }
    

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryMember/{emailOrPhone}")
    public Response queryMember(@PathParam("emailOrPhone") String emailOrPhone) {
    	Member member = null;
    	try {
    		member = memberRepository.queryByUsernameOrCellphoneOrEmail(emailOrPhone);
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		if (member == null) {
    		return Response.status(Status.NO_CONTENT).build();
    	}
    	return Response.status(Status.OK).entity(member.toDto()).build();
    	
    }
    

    private MemberDto convertMemberDto(WebMember webMember) {
        MemberDto memberDto = new MemberDto();
        memberDto.setCellPhone(webMember.getPhone());
        memberDto.setEmail(webMember.getEmail());
        memberDto.setCardNo(webMember.getTempCardNo());
        memberDto.setId(webMember.getId());
        memberDto.setPassword(webMember.getPwd());
        memberDto.setIdentityType(webMember.getIdentityType());
        memberDto.setIdentityNo(webMember.getIdentityNo());
        memberDto.setMemberCode(webMember.getMemberCode());
        if(StringUtils.isNotBlank(webMember.getEmail())){
        	memberDto.setFullName(webMember.getEmail());
        }else{
        	memberDto.setFullName(webMember.getPhone());
        }
        memberDto.setRegisterDate(webMember.getRegistTime());
        memberDto.setMemType(webMember.getMemType());
        memberDto.setMcMemberCode(webMember.getMcMemberCode());
        memberDto.setIsWebMember(true);
        return memberDto;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/validateActivationMember")
    public Response validateEamilOrTelOrCardNo(String emailOrTelorCardNo) {
        if (LOG.isInfoEnabled())
            LOG.info("----start MemberResource.validateEamilOrTelOrCardNo emailOrTelorCardNo-->" + emailOrTelorCardNo);
        StatusDto statusDto = new StatusDto();
        try {
            List<Member> result = memberRepository.queryByCellphoneOrEmail(emailOrTelorCardNo);
            if (result.size() == 0) {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(false);
                LOG.warn("-----MemberResource.validateEamilOrTelOrCardNo()---没有找到该会员 emailOrTelorCardNo-->" + emailOrTelorCardNo);
                return Response.status(Status.OK).entity(statusDto).build();

            } else {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(true);
                return Response.status(Status.OK).entity(statusDto).build();
            }

        } catch (Exception e) {
            statusDto.setExcSuccess(false);
            statusDto.setExistFlag(false);
            statusDto.setErrorMsg(e.getMessage());
            LOG.error("----MemberResource.validateEamilOrTelOrCard({})  error!!----" + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/keyInfoCheck/{keyInfo}/{keyValue}")
    public Response keyInfoCheck(@PathParam("keyInfo") String keyInfo, @PathParam("keyValue") String keyValue) {
        if (LOG.isInfoEnabled()){
        	LOG.info("----start MemberResource.keyInfoCheck keyInfo-->" + keyInfo +"  keyValue-->" + keyValue );
        }
        
        List<Member> result = memberRepository.keyInfoCheck(keyInfo, keyValue);

        String msg = "";
        if(result!=null && result.size()>0){
        	msg = "OK";
        }else{
        	msg = "Fail";
        }
    	
    	return Response.ok(msg).build();

    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberCardNo")
    public Response getMemberCardNo(String memberId) {
        if (LOG.isDebugEnabled()) LOG.debug("-----start-----MemberResource.getMemberCardNo({})---", memberId);
        List<Member> result = memberRepository.getMemberCardNo(memberId);
        if (result == null) {
            LOG.warn("-----MemberResource.getMemberCardNo({})---没有找到会员信息 memberId-->" + memberId, memberId);
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<MemberDto> resultDto = new ResultMemberDto<MemberDto>();
        List<MemberDto> dtoResult = new ArrayList<MemberDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }
        resultDto.setResults(dtoResult);
        if (LOG.isDebugEnabled()) LOG.debug("-----end-----MemberResource.getMemberCardNo({})---", memberId);
        return Response.status(Status.OK).entity(resultDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/quickRegister")
    public Response quickRegister(MemberQuickRegisterDto memberQuickRegisterDto) {
        CRMResponseDto crmResponseDto = new CRMResponseDto();
        if (LOG.isDebugEnabled())
            LOG.debug("-----start-----MemberResource.quickRegister(mobile {})---", memberQuickRegisterDto.getMobile());
        try {
        	MemberRegisterDto dto = toMemberRegisterResponse(memberQuickRegisterDto);
            OpRecordLogDomain opRecord = new OpRecordLogDomain();
    		opRecord.setOpType(EnumOpType.MEMBER_REGISTER);
			opRecord.setContent(JaxbUtils.convertToXmlString(dto));
        	String msg = validateRegister(
        			dto.getMemberInfoDto().getMobile(), 
        			dto.getMemberInfoDto().getEmail(), 
        			dto.getMemberInfoDto().getCertificateType(), 
        			dto.getMemberInfoDto().getCertificateNo(), 
        			crmResponseDto, 
        			opRecord, 
        			dto);

    		if(!"OK".equals(msg)) {
    	      opRecordLogService.insert(opRecord);
        	  crmResponseDto.setRetcode(CrmResponse.Status.FAIL.toString());
        	  crmResponseDto.setRetmsg(msg);
        	  crmResponseDto.setExecSuccess(false);
        	  return Response.status(Status.OK).entity(crmResponseDto).build();
    		}
    		
        	MemberRegisterResponse response  =  regMemberService.registerMember(dto);
        	crmResponseDto.setMembid(response.getBody().getMembid());
        	crmResponseDto.setRetcode(response.getBody().getRecode().toString());
        	crmResponseDto.setRetmsg(response.getBody().getRemsg());
        	if("00001".equals(crmResponseDto.getRetcode())) {
        		crmResponseDto.setRetmsg("注册成功！  该会员卡号：" + response.getBody().getRecord().getMember().getJJBECardNumber());
        	}
//        	crmResponseDto.setRetmsg(response.getBody().getRemsg());
//            crmResponseDto = crmMembershipRepository.quickRegister(memberQuickRegisterDto);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("CRM Error:" + ex.getMessage(), ex);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        if (LOG.isDebugEnabled())
            LOG.debug("-----end-----MemberResource.quickRegister(mobile {})---", memberQuickRegisterDto.getMobile());
        return Response.status(Status.OK).entity(crmResponseDto).build();
    }
	
	public  MemberRegisterDto toMemberRegisterResponse(MemberQuickRegisterDto memberQuickRegisterDto) {
    	MemberRegisterDto dto = new MemberRegisterDto();
        MemberInfoDto infoDto = new MemberInfoDto();
    	infoDto.setTitle(memberQuickRegisterDto.getTitle());
        infoDto.setSurname(memberQuickRegisterDto.getName());
        infoDto.setMobile(memberQuickRegisterDto.getMobile());
        infoDto.setEmail(memberQuickRegisterDto.getEmail());
        infoDto.setPasssword(memberQuickRegisterDto.getPassword());
        infoDto.setCertificateType(memberQuickRegisterDto.getCertificateType());
        infoDto.setCertificateNo(memberQuickRegisterDto.getCertificateNo());
        dto.setMemberInfoDto(infoDto);
    	return dto;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/findPriv")
    public Response findPrivByMemberId(QueryMemberDto<String> queryDto) throws Exception {
        if (LOG.isDebugEnabled())
            LOG.debug("-----start-----MemberResource.findPrivByMemberId(memberId {})---", queryDto.getCondition());
        MemberPrivilegeDto memberPrivilegeDto = crmMembershipRepository.getPrivCardInfo(queryDto);
        ResultMemberDto<MemberPrivilegeDto> resultDto = new ResultMemberDto<MemberPrivilegeDto>();
        Pagination pagination = queryDto.getPagination();
        int page = pagination.getPage();
        int rows = pagination.getRows();
        int records = memberPrivilegeDto.getPrivLogList().size();
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        List<PrivilegeLogDto> listDtos = new ArrayList<PrivilegeLogDto>();
        if (memberPrivilegeDto.getPrivLogList().size() > 0) {
            if (page < total) {
                for (int i = rows * (page - 1); i < rows * page; i++) {
                    listDtos.add(memberPrivilegeDto.getPrivLogList().get(i));
                }
            } else {
                for (int i = rows * (page - 1); i < memberPrivilegeDto.getPrivLogList().size(); i++) {
                    listDtos.add(memberPrivilegeDto.getPrivLogList().get(i));
                }
            }
        }
        pagination.setTotal(total);
        pagination.countRecords(records);
        resultDto.setPagination(pagination);
        memberPrivilegeDto.setPrivLogList(listDtos);
        List<MemberPrivilegeDto> list = new ArrayList<MemberPrivilegeDto>();
        list.add(memberPrivilegeDto);
        resultDto.setResults(list);
        try {
            if (LOG.isDebugEnabled())
                LOG.debug("-----end-----MemberResource.findPrivByMemberId(memberId {})---", queryDto.getCondition());
            return Response.ok().entity(resultDto).build();
        } catch (Exception e) {
            LOG.error("----MemberResource.findPrivByMemberId(memberId {}) error!!  exception:" + e.getMessage(), queryDto.getCondition(), e);
            return Response.status(Status.NO_CONTENT).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getTravellPreference")
    public Response getTravellPreference(String memberId) throws Exception {
        LOG.warn("MemberResource.getTravellPreference() memberId=" + memberId);
        if (LOG.isDebugEnabled())
            LOG.debug("-----start-----MemberResource.getTravellPreference(memberId {})---", memberId);
        TravellPreferenceDto travellPreferenceDto = crmMembershipRepository.getTravellPreference(memberId);
        if (travellPreferenceDto == null || "".equals(travellPreferenceDto)) {
            LOG.warn("-----MemberResource.getTravellPreference() is null!!----");
            return Response.status(Status.NO_CONTENT).build();
        }
        try {
            if (LOG.isDebugEnabled())
                LOG.debug("-----end-----MemberResource.getTravellPreference(memberId {})---", memberId);
            return Response.status(Status.OK).entity(travellPreferenceDto).build();
        } catch (Exception e) {
            LOG.error("-------MemberResource.getTravellPreference(memberId {}) exception:" + e.getMessage(), memberId, e);
            return Response.status(Status.NO_CONTENT).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/updateTravellPreference")
    public Response updateTravellPreference(TravellPreferenceDto travellPreferenceDto) {
        try {
            if (LOG.isDebugEnabled())
                LOG.debug("-----start-----MemberResource.updateTravellPreference(memberId {})---", travellPreferenceDto.getMemberId());
            crmMembershipRepository.updateTravellPreference(travellPreferenceDto);
            if (LOG.isDebugEnabled())
                LOG.debug("-----end-----MemberResource.updateTravellPreference(memberId {})---", travellPreferenceDto.getMemberId());
            return Response.status(Status.OK).build();
        } catch (Exception e) {
            LOG.error("----MemberResource.updateTravellPreference(memberId {}) error!!--- exception:" + e.getMessage(), travellPreferenceDto.getMemberId(), e);
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryCardStauts")
    public Response queryCardStauts(MemberDto memberDto) throws Exception {
        if (LOG.isDebugEnabled()) LOG.debug("-----start-----MemberResource.queryCardStauts()-------");
        String memberId = memberDto.getMemberCode();
        CardDto dto = crmMembershipRepository.queryCardStauts(memberId);
        if (dto == null) {
            LOG.warn("----MemberResource.queryCardStauts(memberId {}) cardDto is null memberId-->" + memberId, memberId);
            return Response.status(Status.NO_CONTENT).build();
        }
        try {
            if (LOG.isDebugEnabled()) LOG.debug("-----end-----MemberResource.queryCardStauts()-------");
            return Response.status(Status.OK).entity(dto).build();
        } catch (Exception e) {
            LOG.error("queryCardStauts exception:" + e.getMessage(), e);
            return Response.status(Status.NO_CONTENT).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberCouponCount")
    public Response getMemberCouponCount(MemberCouponDto memberCouponDto) {
        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.setMemberId(memberCouponDto.getMemberId());
        memberCoupon.setStatus(memberCouponDto.getStatus());
        String couponCount = String.valueOf(memberCouponRepositoryImpl.getCouponCount(memberCoupon));
        if (LOG.isInfoEnabled()) LOG.info("------MemberResource.getMemberCouponCount count=" + couponCount);
        LOG.warn("-------MemberResource.getMemberCouponCount count=" + couponCount);
        return Response.status(Status.OK).entity(couponCount).build();
    }

    // start by zz
    // å�–å¾—ä¼šå‘˜ç±»åˆ«
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listMemberType")
    public Response listMemberType() {
        List<BaseData> result = memberRepository.listMemberType();
        if (result == null) {
            LOG.warn("---MemberResource.listMemberType()-----result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }

        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        // b.setResults(resultDto);

        return Response.status(Status.OK).entity(resultDto).build();

        // return Response.status(Status.UNAUTHORIZED).build();
    }

    // å�–å¾—è¯�ä»¶ç±»åˆ«
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listCertificateTypes")
    public Response listCertificateTypes() {
        List<BaseData> result = memberRepository.listCertificateTypes();
        if (result == null) {
            LOG.warn("----MemberResource.listCertificateTypes()----result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    // å�–å¾—å®‰å…¨é—®é¢˜
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listRemindQuestionTypes")
    public Response listRemindQuestionTypes() {
        List<BaseData> result = memberRepository.listRemindQuestionTypes();
        if (result == null) {
            LOG.warn("-----MemberResource.listRemindQuestionTypes()----result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    // å�–å¾—åœ°å�€ç±»åž‹
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listAddressTypes")
    public Response listAddressTypes() {
        List<BaseData> result = memberRepository.listAddressTypes();
        if (result == null) {
            LOG.warn("------MemberResource.listAddressTypes()-----result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    // å�–å¾—å�‘ç¥¨å†…å®¹
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listInvoiceTypes")
    public Response listInvoiceTypes() {
        List<BaseData> result = memberRepository.listInvoiceTypes();
        if (result == null) {
            LOG.warn("------MemberResource.listInvoiceTypes()-----result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    // å�–å¾—ç§°è°“
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listTitleTypes")
    public Response listTitleTypes() {
        List<BaseData> result = memberRepository.listTitleTypes();
        if (result == null) {
            LOG.warn("-----MemberResource.listTitleTypes()------result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listEduTypes")
    public Response listEduTypes() {
        List<BaseData> result = memberRepository.listEduTypes();
        if (result == null) {
            LOG.warn("------MemberResource.listEduTypes()-----result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    // å�–å¾—çœ�
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listProvinceTypes")
    public Response listProvinceTypes() {
        List<BaseData> result = memberRepository.listProvinceTypes();
        if (result == null) {
            LOG.warn("-----MemberResource.listProvinceTypes()------result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    // å�–å¾—å¸‚
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listCityTypes")
    public Response listCityTypes(BaseDataDto baseData) {
        List<BaseData> result = memberRepository.listCityTypes(baseData.getName());
        if (result == null) {
            LOG.warn("------MemberResource.listCityTypes()-----result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    // å�–å¾—åŽ¿
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listTownTypes")
    public Response listTownTypes(BaseDataDto baseData) {
        List<BaseData> result = memberRepository.listTownTypes(baseData.getName());
        if (result == null) {
            LOG.warn("------MemberResource.listTownTypes()-----result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }
        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/insertOrder")
    public Response insertOrder(MemberCardOrderDto memberCardOrderDto) {
        StatusDto statusDto = new StatusDto();

        MemberCardOrder memberCardOrder = new MemberCardOrder(memberCardOrderDto);
        try {
            memberCardOrderRepositoryImpl.insertOrder(memberCardOrder);

            statusDto.setExcSuccess(true);

            return Response.status(Status.OK).entity(statusDto).build();
        } catch (Exception e) {
            statusDto.setExcSuccess(false);
            statusDto.setErrorMsg(e.getMessage());
            LOG.error("------MemberResource.insertOrder() error!!-----exception " + e.getMessage(), e);
            return Response.status(Status.OK).entity(statusDto).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/validateEamilOrTel")
    public Response validateEamilOrTel(String emailOrTel) {
        StatusDto statusDto = new StatusDto();
        try {
            List<MemberVerfy> result = memberRepository.queryRegisterByCellphoneOrEmailOrCardNo(emailOrTel);
            if (result.size() == 0) {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(false);
                LOG.warn("------MemberResource.validateEamilOrTel()-----没有找到结果 emailOrTel-->" + emailOrTel);
                return Response.status(Status.OK).entity(statusDto).build();

            } else {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(true);
                return Response.status(Status.OK).entity(statusDto).build();
            }

        } catch (Exception e) {
            statusDto.setExcSuccess(false);
            statusDto.setExistFlag(false);
            statusDto.setErrorMsg(e.getMessage());
            LOG.error("validateEamilOrTel exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/validateMailOrTelInMemberInfo")
    public Response validateMailOrTelInMemberInfo(String emailOrTel) {
        StatusDto statusDto = new StatusDto();
        try {
            List<Member> result = memberRepository.queryByCellphoneOrEmail(emailOrTel);
            if (result.size() == 0) {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(false);
                LOG.warn("----MemberResource.validateMailOrTelInMemberInfo()--没有找到结果 emailOrTel-->" + emailOrTel);
                return Response.status(Status.OK).entity(statusDto).build();

            } else {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(true);
                return Response.status(Status.OK).entity(statusDto).build();
            }

        } catch (Exception e) {
            statusDto.setExcSuccess(false);
            statusDto.setExistFlag(false);
            statusDto.setErrorMsg(e.getMessage());
            LOG.error("---MemberResource.validateMailOrTelInMemberInfo() error!! exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
        }

    }
    
//    @POST
//    @Consumes(MediaType.APPLICATION_XML)
//    @Produces(MediaType.APPLICATION_XML)
//    @Path("/addVIPMembership")
   /* public Response addVIPMembership(MemberRegisterDto dto) {
        LOG.debug("member resource addVIPMembership interface init...");
        //只有直接注册享卡才需要传入tier给CRM，以后传入的tier值以VBP为基准(20121220 cilin.xiao) 
        dto.calcTier(cbpHandler.verifyRegisterCouponNo(dto.getCouponCode()));
        try {
        	cbpHandler.lockCoupon(dto.getCouponCode());
        	if(StringUtils.isNotBlank(dto.getCouponCode())){
            	//判断couponcode是否存在，存在则查询ruleid
            	CouponMessageDto couponMessageDto = cbpHandler.queryCouponRule(dto.getCouponCode());
            	dto.setCouponRuleID(couponMessageDto.getRuleID());
            }
        	
            CRMResponseDto crmResponseDto = null;
            try{
            	//立即加入 非锦江之星会员
            	crmResponseDto = crmMembershipRepository.addVIPMembership(dto);
            }catch (Exception e){
                LOG.error("----MemberResource.addVIPMembership()----register error : ", e);
                cbpHandler.unlockCoupon(dto.getCouponCode());
                return Response.status(Status.UNAUTHORIZED).build();
            }
            if(crmResponseDto.isExecSuccess()){
                String mcMemberCode = memberService.getMcMemberCodeByMemberNum(crmResponseDto.getMembid());
                crmResponseDto.setMcMemberCode(mcMemberCode);                
                registSuccessHandler(dto.getMemberInfoDto(),dto.getCouponCode(), mcMemberCode,dto.getRegisterTag());
                ebpHandler.sendJJCardRegisterEvent(mcMemberCode, RegistChannel.Website.name(), dto.getMemberInfoDto().getMemberType());
            }
            else{
            	cbpHandler.unlockCoupon(dto.getCouponCode());
            }
            return Response.status(Status.OK).entity(crmResponseDto).build();
        } catch (Exception e) {
            LOG.error("----MemberResource.addVIPMembership()----register error : ", e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
    */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/masterCardMembership")
    public Response masterCardMembership(MasterCardRegInfoDto dto) {
    	 try {
    	MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
		MemberInfoDto info = new MemberInfoDto();
		info.setEmail(dto.getEmailAddress());
		info.setPasssword(MD5Utils.generatePassword(dto.getPassword()));
		info.setCertificateNo(dto.getCertificationNumber());
    	info.setCertificateType(dto.getCertificationType().getCode());
    	info.setSurname(dto.getName());
    	info.setPartnerName(PartnerName.MASTER.toString()); // 合作伙伴类型
    	info.setPartnercardNo(dto.getCardNumber()); // 万事达卡号
    	info.setRegisterSource(RegistChannel.Master); // 注册渠道
    	info.setIpAddress(dto.getIpAddress());
    	memberRegisterDto.setMemberInfoDto(info);
    	
    	return addVIPMembership(memberRegisterDto);
    	
    	 }catch (Exception e) {
             LOG.error("----MemberResource.masterCardMembership()----register error : ", e);
             return Response.status(Status.UNAUTHORIZED).build();
         }

    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/addValidateCode")
    public Response addValidateCode(ValidateCodeDto dto) {
    	StatusDto statusDto = new StatusDto();
    	 try {
    		 String res = memberRepository.addValidateCode(new ValidateCode(dto));
    		 if("OK".equals(res)){
    			 statusDto.setExcSuccess(true);
    		 }else{
    			 statusDto.setExcSuccess(false);
    			 statusDto.setErrorMsg(res);
    		 }
    		 return Response.status(Status.OK).entity(statusDto).build();
    	 }catch (Exception e) {
    		 LOG.error("addValidateCode", e);
    		 statusDto.setExcSuccess(false);
             return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
         }

    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/checkValidateCode")
    public Response checkValidateCode(ValidateCodeDto dto) {
    	StatusDto statusDto = new StatusDto();
    	 try {
    		 boolean flag = memberRepository.checkValidateCode(new ValidateCode(dto));
    		 statusDto.setExistFlag(flag);
    		 return Response.status(Status.OK).entity(statusDto).build();
    	 }catch (Exception e) {
    		 LOG.error("checkValidateCode", e);
    		 statusDto.setExistFlag(false);
    		 return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
         }

    }
    
    /*
     * 验证提交信息唯一性
     * 手机/邮箱：不能重复
     * 证件类型/证件号码：不能为空且不能重复
     * 证件类型/证件号码：验证“其他”类型
     */
    private String validateRegister(
    		String phone,
    		String email,
    		String identityType,
    		String identityNo,
    		CRMResponseDto crmResponseDto,
    		OpRecordLogDomain opRecord,
    		Object dto) {

    	// 证件类型/证件号不能为空
		if(StringUtils.isBlank(identityNo) 
				|| StringUtils.isBlank(identityType)) {
			opRecord.setMessage("identityNo or identityType is blank");
    		return "请输入证件号和证件类型";
		}
			
	    // 手机号校验
		if(!StringUtils.isBlank(phone) && memberRepository.queryByCellphone(phone) != null) {
			opRecord.setMessage("phone_exits");
    		return "此手机号码已注册,请进行锦江礼享⁺会员激活或请致电1010-1666,由客服为您服务！";
		}
		// 邮箱校验
		if(!StringUtils.isBlank(email) && memberRepository.queryByEmail(email) != null) {
			opRecord.setMessage("email_exits");
    		return "此邮箱地址已注册,请进行锦江礼享⁺会员激活或请致电1010-1666,由客服为您服务！";
		}
		// 证件号和证件类型校验
		if(memberRepository.getMemberByIdentify(identityType,identityNo) != null) {
			opRecord.setMessage("identityNo_exists");
    		return "您输入的证件类型和号码已注册,请输入其它证件号码或使用已注册的信息进行登录！";
		}
		return "OK";
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/addVIPMembership")
    public Response addVIPMembership(MemberRegisterDto dto) {
        LOG.debug("member resource addVIPMembership interface init...");

        try {
            OpRecordLogDomain opRecord = new OpRecordLogDomain();
    		opRecord.setOpType(EnumOpType.MEMBER_REGISTER);
			opRecord.setContent(JaxbUtils.convertToXmlString(dto));
    		CRMResponseDto crmResponseDto = new CRMResponseDto();
    		MemberInfoDto info = dto.getMemberInfoDto();
    		String msg = validateRegister(
    				info.getMobile(),
    				info.getEmail(),
    				info.getCertificateType(),
    				info.getCertificateNo(),
    				crmResponseDto, 
    				opRecord,
    				dto);

    		if(!"OK".equals(msg)) {
    	      opRecordLogService.insert(opRecord);
        	  crmResponseDto.setRetcode(CrmResponse.Status.FAIL.toString());
        	  crmResponseDto.setRetmsg(msg);
        	  crmResponseDto.setExecSuccess(false);
        	  return Response.status(Status.OK).entity(crmResponseDto).build();
    		}
    		
        	MemberRegisterResponse memberRegisterResponse = null;
        	// 注册
            memberRegisterResponse  =  regMemberService.registerMember(dto);
            if(memberRegisterResponse.isExecSuccess()){
            	 String mcCode =  regMemberService.getMcMemberCodeByMemberNum(memberRegisterResponse.getBody().getRecord().getMember().getMemberCode());
            	 if(StringUtils.isNotEmpty(dto.getMemberInfoDto().getIpAddress()))
                     memberRepository.updateMemIpAddressByMC(
                             dto.getMemberInfoDto().getIpAddress(), mcCode);
                 crmResponseDto.setMembid(memberRegisterResponse.getBody().getMembid());
				 
                 if(dto.getMemberInfoDto().getRegisterSource().toString().equals(RegistChannel.Website_partner.toString())) {
                	 cbpHandler.registerJJCardIssue(dto.getMemberInfoDto().getRegisterSource().toString(),mcCode,dto.getRegisterTag(), dto.getMemberInfoDto().getAirLineCompany());
                 } else {
                	 cbpHandler.registerJJCardIssue(dto.getMemberInfoDto().getRegisterSource().toString(),mcCode,dto.getRegisterTag());
                 }
				  
                 crmResponseDto.setMemNum(memberRegisterResponse.getBody().getRecord().getMember().getMemberCode());
          		crmResponseDto.setMcMemberCode(mcCode);
            }
           
            crmResponseDto.setRetcode(memberRegisterResponse.getBody().getRecode().toString());
     		crmResponseDto.setRetmsg(memberRegisterResponse.getBody().getRemsg());
     		crmResponseDto.setExecSuccess(memberRegisterResponse.isExecSuccess());
     		crmResponseDto.setHeadRetmsg(memberRegisterResponse.getHead().getRetmsg());
     		
     		if(crmResponseDto.isExecSuccess()){
     			bamDataCollector.sendMessage(new BamMessage("vbp.member.addVIPMembership", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(crmResponseDto)));
     		}else{
     			bamDataCollector.sendMessage(new BamMessage("vbp.member.addVIPMembership", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(crmResponseDto)));
     		}
     		
             return Response.status(Status.OK).entity(crmResponseDto).build();
           
           
        } catch (Exception e) {
            LOG.error("----MemberResource.addVIPMembership({})----register error : ", JaxbUtils.convertToXmlString(dto),  e);
            bamDataCollector.sendMessage(new BamMessage("vbp.member.addVIPMembership", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(dto), ExceptionToString.toString(e)));
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }



	

	private void registSuccessHandler(MemberInfoDto memberInfo, String couponCode,String mcMemberCode,String registerTag) {
        LOG.warn("----- registSuccessHandler memberInfo is {} " +
                "couponCode=" + couponCode+"mcMemberCode = " + mcMemberCode, memberInfo);
		if(StringUtils.isNotEmpty(memberInfo.getIpAddress()))
		memberRepository.updateMemIpAddressByMC(memberInfo.getIpAddress(), mcMemberCode);
 		cbpHandler.registerIssue(buildMemberDto(memberInfo,mcMemberCode, couponCode,registerTag));
 		cbpHandler.registerUseConpon(mcMemberCode,couponCode,"");
	}



	private void registSuccessHandler(MemberRegisterResponse.Body body,String mcMemberCode,String registerTag, MemberInfoDto memberInfo) {
        LOG.warn("----- registSuccessHandler memberInfo is {} ");
		if(StringUtils.isNotEmpty(memberInfo.getIpAddress()))
		memberRepository.updateMemIpAddressByMC(memberInfo.getIpAddress(), mcMemberCode);
 		cbpHandler.registerIssue(buildMemberDto(body,mcMemberCode,registerTag, memberInfo));
	}

    private MemberDto buildMemberDto(Body body, String mcMemberCode,
			String registerTag, MemberInfoDto memberInfo) {
    	MemberDto memberDto = new MemberDto();
    	memberDto.setCardList(new ArrayList<MemberMemCardDto>());
    	memberDto.setMcMemberCode(mcMemberCode);
    	memberDto.setRegisterSource(body.getRecord().getMember().getRegisterSource());
    	memberDto.setCardType(body.getRecord().getMember().getMemberType());
        memberDto.setRegisterTag(registerTag);
    	if(memberInfo.getAirLineCompany() != null && StringUtils.isNotEmpty(memberInfo.getAirLineCardNo())){
    		MemberMemCardDto card=new MemberMemCardDto();
    		card.setCardTypeCd(memberInfo.getAirLineCompany().toString()); 
    		memberDto.getCardList().add(card);
    	}
		return memberDto;
	}

	private MemberDto buildMemberDto(MemberInfoDto infoDto, String mcMemberCode, String couponCode, String registerTag) {
    	MemberDto memberDto = new MemberDto();
    	memberDto.setCouponCode(couponCode);
    	memberDto.setCardList(new ArrayList<MemberMemCardDto>());
    	memberDto.setMcMemberCode(mcMemberCode);
    	memberDto.setRegisterSource(infoDto.getRegisterSource().name());
    	memberDto.setCardType(infoDto.getMemberType());
        memberDto.setRegisterTag(registerTag);
    	if(infoDto.getAirLineCompany() != null && StringUtils.isNotEmpty(infoDto.getAirLineCardNo())){
    		MemberMemCardDto card=new MemberMemCardDto();
    		card.setCardTypeCd(infoDto.getAirLineCompany().name()); 
    		memberDto.getCardList().add(card);
    	}
		return memberDto;
	}

	@POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/updateVIPCardInfo")
    public Response updateVIPCardInfo(MemberUpdateDto dto) {
        try {
            CRMResponseDto crmResponseDto = memberUpdateService.updateVIPCardInfo(dto);
            if (crmResponseDto == null) {
                LOG.warn("-----MemberResource.updateVIPCardInfo() crmResponseDto is null");
                return Response.status(Status.UNAUTHORIZED).build();
            } else {
                return Response.status(Status.OK).entity(crmResponseDto).build();
            }
        } catch (Exception e) {
            LOG.error("updateVIPCardInfo exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/upgradeVIPCardInfoForGateway")
    public Response upgradeVIPCardInfoForGateway(MemberUpdateDto dto) {
        BaseResponse responseStatus = new BaseResponse();
        responseStatus.setStatus(BaseResponse.Status.FAIL);
        try {
            if (validateMemberNotExist(dto, responseStatus))
                return Response.ok(responseStatus).build();
            upgradeVIPForGateway(dto, responseStatus);
        } catch (Exception e) {
            LOG.error("----MemberResource.upgradeVIPCardInfoForGateway({}) error!!", dto, e);
            responseStatus.setStatus(BaseResponse.Status.ERROR);
            responseStatus.setMessage("接口异常!!");
        }
        return Response.ok(responseStatus).build();
    }

    // 取得价钱
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getPrice")
    public Response getPrice(String memberType) {
        List<BaseData> result = memberRepository.getPrice(memberType);
        if (result == null) {
            LOG.warn("MemberResource.getPrice() result is null memberType-->" + memberType);
            return Response.status(Status.UNAUTHORIZED).build();
        }

        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }

        resultDto.setResults(dtoResult);
        // b.setResults(resultDto);

        return Response.status(Status.OK).entity(resultDto).build();

        // return Response.status(Status.UNAUTHORIZED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getAddressValues")
    public Response getAddressValues(AddressDto nameDto) {
        AddressDto valueDto = new AddressDto();
        try {
            valueDto = memberRepository.getAddressValues(nameDto);
            return Response.status(Status.OK).entity(valueDto).build();
        } catch (Exception e) {
            LOG.error("getAddressValues exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    // 注册插入联系信息
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/insertContactInfo")
    public Response insertContactInfo(ContactQqAndMsnDto qqMsnDto) {
        CRMResponseDto dto = crmMembershipRepository.insertContactInfo(qqMsnDto);
        return Response.status(Status.OK).entity(dto).build();
    }

    // end by zz

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryMemberHobby")
    public Response queryMemberHobby(String memberId) {
        LOG.warn("----MemberResource.queryMemberHobby() memberId=" + memberId);
        MemberHobbyDto memberHobbyDto = crmMembershipRepository.queryMemberHobby(memberId);
        return Response.status(Status.OK).entity(memberHobbyDto).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/updateMemberHobby")
    public Response updateHobbies(MemberHobbyDto memberHobbyDto) throws Exception {
        String retCode = null;
				try {
					retCode = crmMembershipRepository.updateHobbies(memberHobbyDto);
				} catch (Exception e) {
					LOG.error("updateMemberHobby({}) error!", JaxbUtils.convertToXmlString(memberHobbyDto), e);
					bamDataCollector.sendMessage(new BamMessage("vbp.member.updateMemberHobby", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(memberHobbyDto), ExceptionToString.toString(e)));
				}
        if (LOG.isInfoEnabled())
            LOG.info("----MemberResource.updateMemberHobby() retCode-->" + retCode);
        if("00001".equals(retCode)){
        	bamDataCollector.sendMessage(new BamMessage("vbp.member.updateMemberHobby", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(memberHobbyDto), retCode));
        	return Response.status(Status.OK).entity(retCode).build();
        }else{
        	bamDataCollector.sendMessage(new BamMessage("vbp.member.updateMemberHobby", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(memberHobbyDto), StringUtils.defaultString(retCode)));
        	return Response.status(Status.UNAUTHORIZED).entity(StringUtils.defaultString(retCode)).build();
        }
    }

    // 基本信息查询
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/baseInfo")
    public Response queryMemberBasicInfo(String memberId) {
        LOG.warn("----MemberResource.queryMemberBasicInfo() memberId=" + memberId);
        MemberBasicInfoDto basicInfoDto = crmMembershipRepository.queryMemberBaseInfo(memberId);
        return Response.status(Status.OK).entity(basicInfoDto).build();
    }

    // 基本信息更新
	@POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/updateBaseInfo")
    public Response updateMemberBasicInfo(MemberBasicInfoDto basicInfoDto) {
    	LOG.info("---MemberResource.updateBaseInfo() basicInfoDto--> " + JaxbUtils.convertToXmlString(basicInfoDto));
        String rcode = crmMembershipRepository.updateMemberBaseInfo(basicInfoDto);
        if(CrmResponse.Status.SUCCESS.getCode().equals(rcode)){
        	Member member = new Member();
        	member.setMemberID(basicInfoDto.getMemberId());
        	member.setTitle(basicInfoDto.getTitle());
			if (StringUtils.isBlank(basicInfoDto.getEmail())) {
				member.setEmail(basicInfoDto.getEmail());
	      	}
	      	if (StringUtils.isBlank(basicInfoDto.getCell())) {
	      		member.setCellPhone(basicInfoDto.getCell());
	      	}
	      	LOG.error("---MemberResource.updateBaseInfo() member--> email : " + basicInfoDto.getEmail() + "  cell: "+basicInfoDto.getCell()+"  memberId:" + basicInfoDto.getMemberId());
	      	memberRepository.updateMemberInfo(member);
		}
        LOG.info("---MemberResource.updateBaseInfo() retCode-->" + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }
    
    // 锦江之星用户激活
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/active")
    public Response active(MemberBasicInfoDto basicInfoDto) {
    	LOG.info("---MemberResource.active() basicInfoDto--> " + JaxbUtils.convertToXmlString(basicInfoDto));
    	String rcode = memberService.activeMember(basicInfoDto);
        LOG.info("---MemberResource.active() retCode-->" + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }
    
    //提供给手机端基本信息更新
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
    @Path("/profile/updateBaseInfoForGateWay")
    public Response updateBaseInfoForGateWay(MemberBasicInfoDto basicInfoDto) {
    	if(!basicInfoDto.isNormalForGateWay()) {
    		LOG.warn("参数不合法，会员ID && (邮箱 or 手机号)必输");
    		return Response.status(Status.BAD_REQUEST).entity(UpdateBasicInfoResult.PARAM_INVALID.getCode()).build();
    	}
    	Member member = memberRepository.getMemberByMcMemberCode(basicInfoDto.getMcMemberCode());
    	UpdateBasicInfoResult repeat = memberUpdateService.validMemberRepeat(basicInfoDto, member.getMemberCode());
		if (repeat != null) {
			LOG.warn(repeat.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(repeat.getCode()).build();
    	}
		prepareBasicInfoDataForUpdate(basicInfoDto, member);
		String rcode = crmMembershipRepository.updateMemberBaseInfo(basicInfoDto);
        LOG.info("---MemberResource.updateBaseInfoForGetWay() retCode-->" + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }

	// 基本信息查询
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/memberAddress")
    public Response queryMemberAddress(String memberId) {
        LOG.warn("---MemberResource.queryMemberAddress()---profile memberAddress memberId-->" + memberId);
        List<MemberAddressDto> addressDtos = crmMembershipRepository.queryMemberAddress(memberId);
        ResultMemberDto<MemberAddressDto> resultDto = new ResultMemberDto<MemberAddressDto>();
        resultDto.setResults(addressDtos);
        return Response.status(Status.OK).entity(resultDto).build();
    }

    // 基本信息更新
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/updateMemberAddress")
    public Response updateMemberAddress(ResultMemberDto<MemberAddressDto> addressDtos) {
        String rcode = crmMembershipRepository.updateMemberAddress(addressDtos.getResults());
        LOG.warn("---MemberResource.updateMemberAddress() retCode-->" + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }

    // 基本信息新增
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/saveMemberAddress")
    public Response saveMemberAddress(ResultMemberDto<MemberAddressDto> addressDtos) {
        String rcode = crmMembershipRepository.saveMemberAddress(addressDtos.getResults());
        LOG.warn("---MemberResource.saveMemberAddress() profile saveMemberAddress retCode-->" + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }

    // 设定首选地址
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/firstMemberAddress")
    public Response firstMemberAddress(ResultMemberDto<MemberAddressDto> addressDtos) {
        String rcode = crmMembershipRepository.firstMemberAddress(addressDtos.getResults());
        LOG.warn("---MemberResource.firstMemberAddress()----profile firstMemberAddress retCode-->" + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }

    // 基本信息删除
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/deleteMemberAddress")
    public Response deleteMemberAddress(MemberAddressDto addressDto) {
        String rcode = crmMembershipRepository.deleteMemberAddress(addressDto);
        LOG.warn("MemberResource.deleteMemberAddress() profile deleteMemberAddress retCode-->" + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }

    // 基本信息查询
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/contactInfo")
    public Response queryContactInfo(String memberId) {
        LOG.warn("---MemberResource.queryContactInfo() profile contactInfo memberId-->" + memberId);
        MemberCommunicationDto commDto = crmMembershipRepository.queryMemberCommunication(memberId);
        return Response.status(Status.OK).entity(commDto).build();
    }

    // 基本信息更新
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/profile/updateContactInfo")
    public Response updateContactInfo(MemberCommunicationDto comDto) {
        String rcode = crmMembershipRepository.updateMemberCommunication(comDto);
        LOG.warn("---MemberResource.updateContactInfo() profile updateContactInfo retCode-->" + rcode);
        return Response.status(Status.OK).entity(rcode).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/bind")
    public Response bind(String memberId) {
        try {
            MemberDto memberDto = crmMembershipRepository.getMemberDto(memberId);
            if (memberDto == null) {
                LOG.warn("-----MemberResource.bind() memberDto is null memberId-->" + memberId);
                return Response.status(Status.NO_CONTENT).build();
            } else {
                return Response.status(Status.OK).entity(memberDto).build();
            }
        } catch (Exception e) {
            LOG.error("----MemberResource.bind error!!--- exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/bindPhone")
    public Response bind(MemberDto memberDto) {
        try {
            CRMResponseDto crmResponseDto = crmMembershipRepository.bindPhone(memberDto);
            return Response.status(Status.OK).entity(crmResponseDto).build();
        } catch (Exception e) {
            LOG.error("----MemberResource.bindPhone error!!---exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/updateEamilOrTel")
    public Response updatePwdByEamilOrTel(MemberDto memberDto) {
        LOG.warn("----MemberResource.updatePwdByEamilOrTel()---Member[email:{},cellPhone:{}] updatePwd to:{}", new Object[]{memberDto.getEmail(),
                memberDto.getCellPhone(), memberDto.getPassword()});
        StatusDto statusDto = new StatusDto();
        try {
            Member member = queryRegularMemberForUpdatePwdByCellphoneOrEmail(memberDto);
            if (member != null) {
                try {
                    memberRepository.updatePwd(member);
                    statusDto.setExcSuccess(true);
                    statusDto.setExistFlag(true);
                    return Response.status(Status.OK).entity(statusDto).build();
                } catch (Exception e) {
                    statusDto.setExcSuccess(false);
                    statusDto.setExistFlag(true);
                    LOG.error("update exception " + e.getMessage(), e);
                    return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
                }
            }
            statusDto.setExcSuccess(false);
            statusDto.setExistFlag(false);
            LOG.warn("MemberResource.updatePwdByEamilOrTel({})---没有找到该会员", memberDto.getCellPhone());
            return Response.status(Status.OK).entity(statusDto).build();
        } catch (Exception e) {
            statusDto.setExcSuccess(false);
            statusDto.setExistFlag(false);
            statusDto.setErrorMsg(e.getMessage());
            LOG.error("MemberResource.updatePwdByEamilOrTel({}) error!!--exception " + e.getMessage(), memberDto.getCellPhone(), e);
            return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
        }
    }

    private Member queryRegularMemberForUpdatePwdByCellphoneOrEmail(MemberDto memberDto) {
        LOG.warn("queryRegularMemberForUpdatePwdByCellphoneOrEmail email=" + memberDto.getEmail() + " callPhone="
                + memberDto.getCellPhone());
        List<Member> result = new ArrayList<Member>();
        if (memberDto.getEmail() != null) {
            result = memberRepository.queryByCellphoneOrEmail(memberDto.getEmail());
        } else if (memberDto.getCellPhone() != null) {
            result = memberRepository.queryByCellphoneOrEmail(memberDto.getCellPhone());
        }
        Member member = null;
        if (result.isEmpty()) {
            LOG.warn("MemberResource.queryRegularMemberForUpdatePwdByCellphoneOrEmail() result is empty");
            return null;
        }
        for (Member memberWithVerfyList : result) {
            if (memberWithVerfyList.getMemberVerfyList() == null || memberWithVerfyList.getMemberVerfyList().isEmpty()) {
                continue;
            }
            member = new Member();
            member.setPassword(memberDto.getPassword());
            member.setMemberID(memberWithVerfyList.getMemberID());
            member.setIsWebMember(false);
            return member;
        }
        return null;
    }

    private Member queryWebMemberForUpdatePwdByCellphoneOrEmail(MemberDto memberDto) {
        LOG.warn("queryWebMemberForUpdatePwdByCellphoneOrEmail email=" + memberDto.getEmail() + " callPhone="
                + memberDto.getCellPhone());
        WebMember webMember = null;
        if (memberDto.getEmail() != null) {
            webMember = webMemberRepository.queryMemberInfo(memberDto.getEmail(), null);
        } else if (memberDto.getCellPhone() != null) {
            webMember = webMemberRepository.queryMemberInfo(memberDto.getCellPhone(), null);
        }
        if (webMember == null) {
            LOG.warn("webMember is empty");
            return null;
        }
        Member member = new Member();
        member.setPassword(memberDto.getPassword());
        member.setId(webMember.getId());
        member.setIsWebMember(true);
        return member;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryPwd")
    public Response queryPwd(String telphone) {
        MemberDto dto = new MemberDto();
        try {
        	Member member = memberRepository.queryByCellphone(telphone);
            if (member != null) {
            	dto.setCellPhone(member.getCellPhone());
                dto.setPassword(member.getPassword());
            } else {
	            WebMember webMember = webMemberRepository.getWebMemberByPhoneAndEmail(telphone, null);
	            dto.setCellPhone(webMember.getPhone());
	            dto.setPassword(webMember.getPwd());
            }
        } catch (Exception e) {
            LOG.error("MemberResource.queryPwd() error!! exception telphone=" + telphone + " " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
        return Response.status(Status.OK).entity(dto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryByEmail")
    public Response queryByEmail(String email) {
        LOG.warn("---MemberResource.queryByEmail() email=" + email);
        MemberDto dto = null;
        try {
            Member member = memberRepository.queryByEmail(email);
            if (member != null) {
                dto = member.toDto();
            } else {
                WebMember webMember = webMemberRepository.queryMemberInfo(email, null);
                if (webMember != null) {
                    dto = convertMemberDto(webMember);
                }
            }
            return Response.status(Status.OK).entity(dto).build();
        } catch (Exception e) {
            LOG.error("----MemberResource.queryByEmail() exception email=" + email + " " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryByCellphone")
    public Response queryByCellphone(String phone) {
        LOG.warn("---MemberResource.queryByCellphone() phone=" + phone);
        MemberDto dto = null;
        try {
            Member member = memberRepository.queryByCellphone(phone);
            if (member != null) {
                dto = member.toDto();
            } 
            return Response.status(Status.OK).entity(dto).build();
        } catch (Exception e) {
            LOG.error("----MemberResource.queryByCellphone() exception phone=" + phone + " " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }

    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryByCode")
    public Response queryByCode(String memberId) {
        List<Member> result = new ArrayList<Member>();
        try {
            result = memberRepository.queryByCode(memberId);
            if (result == null || result.size()==0) {
                LOG.warn("-----MemberResource.queryByCode() result is null memberId-->" + memberId);
                return Response.status(Status.NO_CONTENT).build();
            } else {
                return Response.status(Status.OK).entity(result.get(0).toDto()).build();
            }
        } catch (Exception e) {
            LOG.error("MemberResource.queryByCode() exception memberId=" + memberId + " " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

	@POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryMemberByCellPhoneOnly")
    public Response queryMemberByCellPhoneOnly(String phone) {
        List<Member> result = new ArrayList<Member>();
        try {
            result = memberRepository.queryByCellphoneOrEmail(phone);
            if (result == null || result.size()==0) {
                LOG.warn("-----MemberResource.queryByCode() result is null phone-->" + phone);
                return Response.status(Status.NO_CONTENT).build();
            } else {
                return Response.status(Status.OK).entity(result.get(0).toDto()).build();
            }
        } catch (Exception e) {
            LOG.error("MemberResource.queryByCode() exception phone=" + phone + " " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
	
	@POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryByMemberID")
    public Response queryByMemberID(String memberId) {
        List<Member> result = new ArrayList<Member>();
        try {
            result = memberRepository.queryByMemberID(memberId);
            if (result == null || result.size()==0) {
                LOG.warn("-----MemberResource.queryByMemberID() result is null memberId-->" + memberId);
                return Response.status(Status.NO_CONTENT).build();
            } else {
                return Response.status(Status.OK).entity(result.get(0).toDto()).build();
            }
        } catch (Exception e) {
            LOG.error("MemberResource.queryByMemberID() exception memberId=" + memberId + " " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @SuppressWarnings("deprecation")
	@POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/modifyPwd")
    public Response modifyPwd(MemberDto memberDto) {
        LOG.warn("---MemberResource.modifyPwd Member[id:{},isWebMember:{},memberID:{},memberCode:{},mcMemberCode:{}] modifyPwd to:{}",
                new Object[]{memberDto.getId(), memberDto.getIsWebMember(), memberDto.getMemberID(),memberDto.getMemberCode(), memberDto.getMcMemberCode(), memberDto.getPassword()});
        try {
            memberRepository.updatePwdByMcMemberCode(memberDto.getMcMemberCode(), memberDto.getPassword());
            return Response.status(Status.OK).entity(new CRMResponseDto(null, "1", "")).build();
        } catch (Exception e) {
            LOG.error("---MemberResource.modifyPwd() modifyPwd exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/validatePhone")
    public Response validatePhone(String phone) {
        Member member = new Member();
        StatusDto statusDto = new StatusDto();
        try {
            member = memberRepository.validatePhone(phone);
            if (member != null) {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(true);
                return Response.status(Status.OK).entity(statusDto).build();
            }

            WebMember webMember = webMemberRepository.queryMemberInfo(phone, null);
            if (webMember != null) {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(true);
                return Response.status(Status.OK).entity(statusDto).build();
            }
            statusDto.setExcSuccess(true);
            statusDto.setExistFlag(false);
            LOG.warn("---MemberResource.validatePhone(phone {}) 没有找到该会员", phone);
            return Response.status(Status.OK).entity(statusDto).build();

        } catch (Exception e) {
            statusDto.setExcSuccess(false);
            statusDto.setExistFlag(false);
            statusDto.setErrorMsg(e.getMessage());
            LOG.error("---MemberResource.validatePhone(phone {}) error !!!validatePhone exception phone=" + phone + " " + e.getMessage(), phone, e);
            return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/validateMail")
    public Response validateMail(String mail) {
        Member member = new Member();
        StatusDto statusDto = new StatusDto();
        try {
            member = memberRepository.validateMail(mail);
            if (member == null) {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(false);
                LOG.warn("---MemberResource.validateMail(mail {})没有找到该会员 mail-->" + mail, mail);
                return Response.status(Status.OK).entity(statusDto).build();
            } else {
                statusDto.setExcSuccess(true);
                statusDto.setExistFlag(true);
                return Response.status(Status.OK).entity(statusDto).build();
            }

        } catch (Exception e) {
            statusDto.setExcSuccess(false);
            statusDto.setExistFlag(false);
            statusDto.setErrorMsg(e.getMessage());
            LOG.error("---MemberResource.validateMail(mail {}) error!!! validateMail exception mail=" + mail + " " + e.getMessage(), e, mail);
            return Response.status(Status.UNAUTHORIZED).entity(statusDto).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listHierarchyTypes")
    public Response listHierarchyTypes() {
        List<BaseData> result = memberRepository.listHierarchyTypes();
        if (result == null) {
            LOG.warn("---MemberResource.listHierarchyTypes() result is null");
            return Response.status(Status.UNAUTHORIZED).build();
        }
        ResultMemberDto<BaseDataDto> resultDto = new ResultMemberDto<BaseDataDto>();
        List<BaseDataDto> dtoResult = new ArrayList<BaseDataDto>();
        for (int i = 0; i < result.size(); i++) {
            dtoResult.add(result.get(i).toDto());
        }
        resultDto.setResults(dtoResult);
        return Response.status(Status.OK).entity(resultDto).build();
    }

    // 权益卡查询
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryVIPCardInfo")
    public Response queryVIPCardInfo(String memberId) {
        LOG.warn("---MemberResource.queryVIPCardInfo(memberId {}) queryVIPCardInfo memberId=" + memberId, memberId);
        try {
            List<VipCardInfoDto> list = crmUpdateRightCardRepository.queryVIPCardInfo(memberId);
            ResultMemberDto<VipCardInfoDto> result = new ResultMemberDto<VipCardInfoDto>();
            result.setResults(list);
            return Response.status(Status.OK).entity(result).build();
        } catch (Exception e) {
            LOG.error("---MemberResource.queryVIPCardInfo(memberId {}) 权益卡查询出错" + e.getMessage(), memberId);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    //
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getNextSequence")
    public Response getNextSequence() {
        Long uuid = memberCardOrderRepositoryImpl.getNextSequence();
        if (uuid == null) {
            LOG.warn("---MemberResource.getNextSequence() uuid is null");
            return Response.status(Status.UNAUTHORIZED).build();
        } else {
            return Response.status(Status.OK).entity(uuid.toString()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/bindEmail")
    public Response bindEmail(MemberDto memberDto) {
        try {
            CRMResponseDto crmResponseDto = crmMembershipRepository.bindEmail(memberDto);
            return Response.status(Status.OK).entity(crmResponseDto).build();
        } catch (Exception e) {
            LOG.error("---MemberResource.bindEmail(email {}) exception " + e.getMessage(), memberDto.getEmail(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/saveXml")
    public Response saveXml(MemberXmlDto memberXmlDto) {
        StatusDto statusDto = new StatusDto();

        try {
            memberXmlRepository.saveXml(new MemberXml(memberXmlDto));

            statusDto.setExcSuccess(true);

            return Response.status(Status.OK).entity(statusDto).build();
        } catch (Exception e) {
            statusDto.setExcSuccess(false);
            statusDto.setErrorMsg(e.getMessage());
            LOG.error("---MemberResource.saveXml(memberXmlDto {}) exception " + e.getMessage(), memberXmlDto.getXml(), e);
            return Response.status(Status.OK).entity(statusDto).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryMemberDtoByEmail")
    public Response queryMemberDtoByEmail(String email) {
        LOG.warn("---MemberResource.queryMemberDtoByEmail(email {}) email=" + email, email);
        MemberDto dto = new MemberDto();
        try {
            Member member = memberRepository.queryByEmail(email);
            if (member != null) {
                dto = member.toDto();
            } else {
                LOG.warn("---MemberResource.queryMemberDtoByEmail(email {}) member is null", email);
                dto = null;
            }
            return Response.status(Status.OK).entity(dto).build();
        } catch (Exception e) {
            LOG.error("---MemberResource.queryMemberDtoByEmail(email {}) exception " + e.getMessage(), email, e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
    
    @GET
    @Path("/identityNumberCheck/{identityNumber}/{identityType}")
    public Response identityNumberCheck(@PathParam("identityNumber") String identityNumber, @PathParam("identityType") String identityType) {
    	MemberDto dto = new MemberDto();
    	dto.setIdentityNo(identityNumber);
    	dto.setIdentityType(identityType);
        try {
             Member member = new Member(dto);
             List<Member> memberResultList = memberRepository.queryIdentifyInfo(member);
             if (CollectionUtils.isNotEmpty(memberResultList)) {
             	 return Response.status(Status.OK).entity(Boolean.FALSE).build();
             } else {
                 return Response.status(Status.OK).entity(Boolean.TRUE).build();
             }

         } catch (Exception e) {
             LOG.error("---MemberResource.identityNumberCheck error!!! exception " + e.getMessage(), identityNumber, e);
 			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
         }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/validateIdentifyInfo")
    public Response queryIdentifyInfo(MemberDto dto) {
        Member temp = new Member();
        try {
            Member member = new Member(dto);
            List<Member> memberResultList = memberRepository.queryIdentifyInfo(member);
            if (CollectionUtils.isNotEmpty(memberResultList)) {
                temp = getValidMember(memberResultList);
                for (int i = 0; i < memberResultList.size(); i++) {
                    if (memberResultList.get(i).getMemberVerfyList() != null
                            && memberResultList.get(i).getMemberVerfyList().size() > 0) {
                        temp = memberResultList.get(i);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            Member a = new Member();
            LOG.error("---MemberResource.validateIdentifyInfo(mobile {}) error!!! exception " + e.getMessage(), dto.getCellPhone(), e);
            return Response.status(Status.OK).entity(a.toDto()).build();
        }
        return Response.status(Status.OK).entity(temp.toDto()).build();
    }

    private Member getValidMember(List<Member> memberResultList) {
        Member m = memberResultList.get(0);
        for (Member mem : memberResultList) {
        	String cardNo=mem.getCardNo().toUpperCase();
            if ((cardNo.startsWith("H"))||(cardNo.startsWith("G"))||
    				(cardNo.startsWith("X"))||(cardNo.startsWith("J"))||
    				(cardNo.startsWith("Y"))||(cardNo.startsWith("U"))) {
                m = mem;
                break;
            }
        }
        return m;
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/partnerCardBind")
    public Response partnerCardBind(PartnerCardBindDto dto) {
        LOG.info("--------MemberResource.partnerCardBind start: PartnerCardBindDto = {}", JaxbUtils.convertToXmlString(dto));
        try {
            CardBindStatusDto statusDto = memberHandler.updateMemberBaseBind(dto);
            LOG.info("--------MemberResource.partnerCardBind end : status = {}", statusDto.getStatus());
            return Response.ok(statusDto).build();
        } catch (Exception e) {
            LOG.error("--------MemberResource.partnerCardBind Error: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getPartnerCard/{memberId}")
    public Response getPartnerCard(@PathParam("memberId") String memberId) {
        LOG.info("--------MemberResource.getPartnerCard start: memberCode = "+ memberId);
        try {
        	PartnerCardsListDto dto = new PartnerCardsListDto();
        	
        	MemberPartnerCardRes res = new MemberPartnerCardRes();
        	res = memberHandler.getPartnerCards(memberId);
        	
        	dto = res.getBody().toPartnerCardsListDto();
            return Response.ok(dto).build();
        } catch (Exception e) {
            LOG.error("--------MemberResource.getPartnerCard Error: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/partnerCardQuery")
    public Response partnerCardQuery(PartnerCardBindDto dto) {
        try {
            LOG.warn("partnerCardQuery partnerCardNo=" + dto.getPartnerCardNo() + " partnerCode=" + dto.getPartnerCode());
            CardExistStatusDto statusDto = new CardExistStatusDto();
            if (memberHandler.isExistAirAndCardNo(dto)) {
                statusDto.setStatus(CardExistStatus.EXISTED);
                statusDto.setMessage(String.format("%s已存在，请使用其他卡号", dto.getPartnerCode().getMessage()));
                LOG.warn(String.format("%s已存在", dto.getPartnerCode().getMessage()));
                return Response.ok(statusDto).build();
            }
            statusDto.setStatus(CardExistStatus.NOTEXIST);
            statusDto.setMessage(String.format("%s不存在", dto.getPartnerCode().getMessage()));
            return Response.ok(statusDto).build();
        } catch (Exception e) {
            LOG.info("--------MemberResource.partnerCardQuery Error: ", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryMemberByCode/{mcMemberCode}")
    public Response queryMemberByCode(@PathParam("mcMemberCode") String mcMemberCode) {
        LOG.info("-----begin---MemberResource.queryMemberByCode   mcMemberCode=" + mcMemberCode);
        MemberInfoAnswerDto result = new MemberInfoAnswerDto();
        try {
            Member member = memberRepository.getMemberByMcMemberCode(mcMemberCode);
            result.setMember(member.toDto());
            result.setStatus(BaseResponse.Status.SUCCESS);
            result.setMessage(BaseResponse.Status.SUCCESS.name());
        } catch (NotAcceptableException e) {
            LOG.warn(e.getMessage(), e);
            result.setStatus(BaseResponse.Status.FAIL);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result.setStatus(BaseResponse.Status.ERROR);
            result.setMessage(BaseResponse.Status.ERROR.name());
        }
        LOG.info(" end  queryMemberByCode  xml= " + JaxbUtils.convertToXmlString(result));
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/queryMember")
    public Response queryMember(MemberJREZQueryDto queryDto) {
        try {
            MemberJREZResultsDto result = memberService.getMemberJREZResult(queryDto);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            LOG.error("JREZ query member error", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/referrerHandler")
    public Response referrerHandler(ReferrerInfoDto dto) {
        ReferrerRegistResult result = new ReferrerRegistResult();
        try {
            result = memberService.saveReferrerInfo(dto);
        } catch (Exception e) {
            result.setStatus(ReferrerRegistResult.ReferrerRegistStatus.SERVER_ERROR);
            LOG.error("--------referrerRegist  referrerSaveAndGetAward  Error:", e);
        }
        return Response.ok().entity(result).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberIdByMcMemberCode/{mcMemberCode}")
    public Response getMemberIdByMcMemberCode(@PathParam("mcMemberCode") String mcMemberCode) {
        LOG.debug("getMemberIdByMcMemberCode({})", mcMemberCode);
        try {
            String memberId = memberService.getMemberIDByMcMemberCode(mcMemberCode);
            if (null == memberId || "".equals(memberId)) {
                LOG.warn("getMemberIdByMcMemberCode({}) no result!", mcMemberCode);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok().entity(memberId).build();
        } catch (Exception e) {
            LOG.error("getMemberIdByMcMemberCode({}) error!", mcMemberCode, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMcMemberCodeByMemberID/{memberID}")
    public Response getMcMemberCodeByMemberID(@PathParam("memberID") String memberID) {
        LOG.debug("getMcMemberCodeByMemberID({})", memberID);
        try {
            String mcMemberCode = memberService.getMcMemberCodeByMemberID(memberID);
            if (null == mcMemberCode || "".equals(mcMemberCode)) {
                LOG.warn("getMcMemberCodeByMemberID({}) no result!", memberID);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok().entity(mcMemberCode).build();
        } catch (Exception e) {
            LOG.error("getMcMemberCodeByMemberID({}) error!", memberID, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMcMemberCodeByMemberNum/{memberNum}")
    public Response getMcMemberCodeByMemberNum(@PathParam("memberNum") String memberNum) {
        LOG.debug("getMcMemberCodeByMemberNum({})", memberNum);
        try {
            String mcMemberCode = memberService.getMcMemberCodeByMemberNum(memberNum);
            if (null == mcMemberCode || "".equals(mcMemberCode)) {
                LOG.warn("getMcMemberCodeByMemberNum({}) no result!", memberNum);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok().entity(mcMemberCode).build();
        } catch (Exception e) {
            LOG.error("getMcMemberCodeByMemberNum({}) error!", memberNum, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberByMemberNum/{memberNum}")
    public Response getMemberByMemberNum(@PathParam("memberNum") String memberNum) {
        LOG.debug("getMemberByMemberNum({})", memberNum);
        try {
            Member member = memberService.getMemberByMemberNum(memberNum);
            if (member == null) {
                LOG.warn("getMemberByMemberNum({}) no result!", memberNum);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok().entity(member.toDto()).build();
        } catch (Exception e) {
            LOG.error("getMemberByMemberNum({}) error!", memberNum, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMcMemberCodeByPhone/{phone}")
    public Response getMcMemberCodeByPhone(@PathParam("phone") String phone) {
        LOG.debug("getMcMemberCodeByPhone({})", phone);
        try {
            String mcMemberCode = memberService.getMcMemberCodeByPhone(phone);
            if (null == mcMemberCode || "".equals(mcMemberCode)) {
                LOG.warn("getMcMemberCodeByPhone({}) no result!", phone);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok().entity(mcMemberCode).build();
        } catch (Exception e) {
            LOG.error("getMcMemberCodeByPhone({}) error!", phone, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }


    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberByMcMemberCode/{mcMemberCode}")
    public Response getMemberByMcMemberCode(@PathParam("mcMemberCode") String mcMemberCode) {
        LOG.debug("getMemberByMcMemberCode({})", mcMemberCode);
        try {
            Member member = memberService.getMemberByMcMemberCode(mcMemberCode);
            if (null == member) {
                LOG.warn("getMemberByMcMemberCode({}) no result!", mcMemberCode);
                return Response.status(Status.NOT_FOUND).build();
            }
            return Response.ok().entity(member.toDto()).build();
        } catch (Exception e) {
            LOG.error("getMemberByMcMemberCode({}) error!", mcMemberCode, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberByCardNo/{cardNo}")
    public Response getMemberByCardNo(@PathParam("cardNo") String cardNo) {
        LOG.debug("getMemberByCardNo({})", cardNo);
        try {
            Member member = memberService.getMemberByCardNo(cardNo);
            if (null == member) {
                LOG.warn("getMemberByCardNo({}) no result!", cardNo);
                return Response.status(Status.NOT_FOUND).build();
            }
            return Response.ok().entity(member.toDto()).build();
        } catch (Exception e) {
            LOG.error("getMemberByCardNo({}) error!", cardNo, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getMemberById/{id}")
    public Response getMemberById(@PathParam("id") Long id) {
        LOG.debug("getMemberById({})", id);
        try {
            Member member = memberService.getMemberById(id);
            if (member == null) {
                LOG.warn("getMemberByMemberNum({}) no result!", id);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok().entity(member.toDto()).build();
        } catch (Exception e) {
            LOG.error("getMemberByMemberNum({}) error!", id, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    
    @GET
    @Path("/validateMasterCard/{cardNo}")
    public Response validateMasterCardNo(@PathParam("cardNo") String cardNo) {
        LOG.debug("validateMasterCardNo({})", cardNo);
        try {
        	if(validMasterCardNo(cardNo)) {
        		return Response.ok().entity(Boolean.TRUE.toString()).build();
        	} else {
    	    	return Response.ok().entity(Boolean.FALSE.toString()).build();
        	}
        } catch(Exception e) {
            LOG.error("validateMasterCardNo({}) error!", cardNo, e);
    	    return Response.ok().entity(Boolean.FALSE.toString()).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/sendValidateCode")
    public Response sendValidateCodeOperation(SMSValidationDto dto) {
    	 LOG.debug("sendValidateCodeOperation({})", dto);
    	 try {
    		 ValidateCodeLog validateCodeLog = new ValidateCodeLog();
    		 
    		 /*
    		  * 相同的手机一天内只能注册3次
    		  * 现在变成很厉害的一辈子只能3次
    		  */
    		 validateCodeLog = ValidateCodeLog.getPhoneValidateCodeLogQuery(dto.getPhone(), null);
    		 int countPhoneDay = validateCodeLogRepository.countLogForValidate(validateCodeLog);
    		 if(countPhoneDay >= 3) {
    			 LOG.error(String.format("sendValidateCodeOperation(%s) return error:%s[%s]!", JaxbUtils.convertToXmlString(dto), ValidateCodeMsgType.PHONE_DAY_RULE, countPhoneDay));
    	     	 return Response.ok().entity(ValidateCodeMsgType.PHONE_DAY_RULE.toString()).build();
    		 }
    		 
    		 /*
    		  * 相同的IP一小时内最多注册3次
    		  */
    		 validateCodeLog = ValidateCodeLog.getIPValidateCodeLogQuery(dto.getIpAddress(), DateUtils.addHours(new Date(), -1));
    		 
    		 List<ValidateCodeLog> list = validateCodeLogRepository.selectLogForValidate(validateCodeLog);
    		 
    		 if(list.size() >= 3) {
    			 LOG.error(String.format("sendValidateCodeOperation(%s) return error:%s[%s]!", JaxbUtils.convertToXmlString(dto), ValidateCodeMsgType.IP_HOUR_RULE, list.size()));
    			 return Response.ok().entity(ValidateCodeMsgType.IP_HOUR_RULE.toString()).build();
    		 }
    		 
	    	 /*
     		  * 同一个手机每次发送间隔60秒才能发送
	    	  */
    		 if(list.size() != 0 && list.get(0).getCreateDate().after(DateUtils.addMinutes(new Date(), -1))) {
    			 LOG.error(String.format("sendValidateCodeOperation(%s) return error:%s!", JaxbUtils.convertToXmlString(dto), ValidateCodeMsgType.CODE_TIME_RULE));
    			 return Response.ok().entity(ValidateCodeMsgType.CODE_TIME_RULE.toString()).build();
    		 }
    		 
			 Map<String, String> codeMapper = new HashMap<String, String>();
			 codeMapper.put("ValidateCode", getRandomCode());
			 codeMapper.put("Time", "30");
			 validateCodeLog.setCode(codeMapper.get("ValidateCode"));
			 validateCodeLog.setIp(dto.getIpAddress());
			 validateCodeLog.setPhone(dto.getPhone());
			 validateCodeLog.setCreateDate(new Date());
			 nbpHandler.simpleSendSMS(dto.getPhone(), codeMapper, "M_Quick_Registration_Phone_Validate_SMS", "注册发送验证码到手机");
			 validateCodeLogRepository.insert(validateCodeLog);		
			 
			 return Response.ok().entity(ValidateCodeMsgType.SEND_CODE_SUCCESS.toString()).build();
    	 }catch(Exception e) {
             LOG.error("sendValidateCodeOperation({}) error!", JaxbUtils.convertToXmlString(dto), e);
     	    return Response.ok().entity(Boolean.FALSE.toString()).build();
         }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/SMSRegisterValidation")
    public Response SMSRegisterValidation(SMSValidationDto dto) {
     	 LOG.debug("SMSRegisterValidation({})", dto);
     	 try {
     		ValidateCodeLog validateCodeLog = new ValidateCodeLog();
     		validateCodeLog.setPhone(dto.getPhone());
     	// 查找最近此会员、此手机发送的校验码
			validateCodeLog.setMemberInfoId(dto.getMemberInfoId());
			
     		ValidateCodeLog codeLog = validateCodeLogRepository.getLastLog(validateCodeLog);
     		if(codeLog == null) {
      	        return Response.ok().entity(Boolean.FALSE.toString()).build();
     		} 
     		if(codeLog.getCode().equals(dto.getValidationCode())){
     			return Response.ok().entity(Boolean.TRUE.toString()).build();
     		} else {
      	        return Response.ok().entity(Boolean.FALSE.toString()).build();
     		}
     	 }catch(Exception e) {
             LOG.error("SMSRegisterValidation({}) error!", dto, e);
   	         return Response.ok().entity(Boolean.FALSE.toString()).build();
         }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/bindSns")
    public Response bindSns(MemberContactsnsDto dto) {
        try {
        	dto.setStatus("Effective");
        	CRMResponseDto crmResponseDto = crmMembershipRepository.bindSns(dto);
            if (crmResponseDto == null) {
                return Response.status(Status.NO_CONTENT).build();
            } else {
                return Response.status(Status.OK).entity(crmResponseDto).build();
            }
        } catch (Exception e) {
            LOG.error("----MemberResource.bindSns error!!--- exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/unBindSns")
    public Response unBindSns(MemberContactsnsDto dto) {
        try {
        	dto.setStatus("Uneffective");
        	CRMResponseDto crmResponseDto = crmMembershipRepository.bindSns(dto);
            if (crmResponseDto == null) {
                return Response.status(Status.NO_CONTENT).build();
            } else {
                return Response.status(Status.OK).entity(crmResponseDto).build();
            }
        } catch (Exception e) {
            LOG.error("----MemberResource.bindSns error!!--- exception " + e.getMessage(), e);
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }
 
    private String getRandomCode() {
		int codeLength = 6;
		String codeHash = "abcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			Random random = new Random();
			int nextInt = random.nextInt(codeHash.length());
			sb.append(codeHash.charAt(nextInt));
		}
		return sb.toString();
	}
    
	private boolean validMasterCardNo(String cardNo) {
		cardNo = cardNo.replace(" ", "");
		if(cardNo.length() != 16) {
			return false;
		}
		StringBuilder data = new StringBuilder();
		for(int i = 0; i < cardNo.length(); i++) {
			if(i % 2 == 0) {
			    Integer val = Integer.valueOf(String.valueOf(cardNo.substring(i, i + 1))) * 2;
			    data.append(val.toString());
			} else {
				data.append(cardNo.substring(i, i + 1));
			}
		}
		int total = 0;
		for(int j = 0; j < data.length(); j++) {
			int single = Integer.valueOf(data.toString().substring(j, j + 1));
			total += single;
		}
		if(total % 10 == 0) {
			return true;
		}
		return false;
	}

    private void upgradeVIPForGateway(MemberUpdateDto dto, BaseResponse responseStatus) throws Exception {
        dto.setPostflg("Y");
        dto.setInvflg("N");
        dto.fullValueToOptye();
        CRMResponseDto crmResponseDto = memberUpdateService.updateVIPCardInfo(dto);
        if (crmResponseDto.isExecSuccess()) {
            responseStatus.setStatus(BaseResponse.Status.SUCCESS);
        }
        responseStatus.setMessage(crmResponseDto.getRetmsg());
    }

    private boolean validateMemberNotExist(MemberUpdateDto dto, BaseResponse responseStatus) {
        Member member = memberRepository.getMemberByMcMemberCode(dto.getMcMemberCode());
        if (null == member) {
            LOG.warn("--------member is null!! mcMemberCode={}----", dto.getMcMemberCode());
            responseStatus.setStatus(BaseResponse.Status.ERROR);
            responseStatus.setMessage("无此会员，请核对后再试");
            return true;
        }
        dto.setMembid(member.getMemberCode());
        return false;
    }

    private void prepareBasicInfoDataForUpdate(MemberBasicInfoDto basicInfoDto, Member member) {
      	if (StringUtils.isBlank(basicInfoDto.getEmail())) {
      		basicInfoDto.setEmail(member.getEmail());
      	}
      	if (StringUtils.isBlank(basicInfoDto.getCell())) {
      		basicInfoDto.setCell(member.getCellPhone());
      	}
    	basicInfoDto.setMemberId(member.getMemberID());
    	basicInfoDto.setCdtype(member.getIdentityType());
    	basicInfoDto.setCdno(member.getIdentityNo());
    	basicInfoDto.setTitle(member.getTitle());
		
    	prepareBingFlag(basicInfoDto, member);
	}

	private void prepareBingFlag(MemberBasicInfoDto dto, Member member) {
		if (StringUtils.equals("Mobile Activiated", member.getActivateCode()) && !StringUtils.equals(dto.getCell(), member.getCellPhone())) {
			dto.setBingFlg("Activiated");
		}
		if (StringUtils.equals("Not Activiate", member.getActivateCode())) {
			dto.setBingFlg("Not Activiate");
		}
		if (StringUtils.equals("Email Activiated", member.getActivateCode()) && !StringUtils.equals(dto.getEmail(), member.getEmail())) {
			dto.setBingFlg("Activiated");
		}
		if (StringUtils.equals("Mobile and Email Activiated", member.getActivateCode())) {
			if(!dto.getEmail().equals(member.getEmail()) && !dto.getCell().equals(member.getCellPhone())) {
				dto.setBingFlg("Activiated");
			} else if(!dto.getEmail().equals(member.getEmail()) && dto.getCell().equals(member.getCellPhone())) {
				dto.setBingFlg("Mobile Activiated");
			} else if (dto.getEmail().equals(member.getEmail()) && !dto.getCell().equals(member.getCellPhone())) {
				dto.setBingFlg("Email Activiated");
			}
		}
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/sendValidateCodeWhenBindPhone")
	public Response sendValidateCodeWhenBindPhone(SMSValidationDto dto) {
		LOG.debug("sendValidateCodeWhenBindPhone({})", dto);
		try {
			ValidateCodeLog validateCodeLog = new ValidateCodeLog();
			validateCodeLog = ValidateCodeLog.getValidateCodeLogQuery(dto.getPhone(), DateUtils.addDays(new Date(), -1),dto.getMemberInfoId());
			List<ValidateCodeLog> list = validateCodeLogRepository.selectLogForValidate(validateCodeLog);
			
			//绑定同一手机，一天内只能做3次验证！
			if (list.size() >= 3) {
				return Response.ok().entity(ValidateCodeMsgType.BIND_PHONE_DAY_RULE.toString()).build();
			}
			//同一个手机每次发送间隔60秒才能发送
			if (list.size() != 0 && list.get(0).getCreateDate().after(DateUtils.addMinutes(new Date(), -1))) {
				return Response.ok().entity(ValidateCodeMsgType.CODE_TIME_RULE.toString()).build();
			}

			Map<String, String> codeMapper = new HashMap<String, String>();
			codeMapper.put("ValidateCode", getRandomCode());
			codeMapper.put("Time", "30");
			validateCodeLog.setCode(codeMapper.get("ValidateCode"));
			validateCodeLog.setPhone(dto.getPhone());
			validateCodeLog.setCreateDate(new Date());
			validateCodeLog.setMemberInfoId(dto.getMemberInfoId());
			nbpHandler.simpleSendSMS(dto.getPhone(), codeMapper,
					"M_Quick_Registration_Phone_Validate_SMS", "手机绑定发送验证码到手机");
			validateCodeLogRepository.insert(validateCodeLog);

			return Response.ok()
					.entity(ValidateCodeMsgType.SEND_CODE_SUCCESS.toString())
					.build();
		} catch (Exception e) {
			LOG.error("sendValidateCodeWhenBindPhone({}) error!", dto, e);
			return Response.ok().entity(Boolean.FALSE.toString()).build();
		}
	}
	
	

}