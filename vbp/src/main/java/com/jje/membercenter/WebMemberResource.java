package com.jje.membercenter;

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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.bam.BamDataCollector;
import com.jje.common.bam.BamMessage;
import com.jje.common.bam.StatusCode;
import com.jje.common.utils.ExceptionToString;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.MD5Utils;
import com.jje.dto.membercenter.CardType;
import com.jje.dto.membercenter.CheckStatusResponse;
import com.jje.dto.membercenter.MemType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberVerfyDto;
import com.jje.dto.membercenter.QuickRegistResult;
import com.jje.dto.membercenter.QuickRegistResult.QuickRegistStatus;
import com.jje.dto.membercenter.RegistResponse;
import com.jje.dto.membercenter.RegistResponseStatus;
import com.jje.dto.membercenter.WebMemberDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.membercenter.remote.handler.NBPHandler;
import com.jje.membercenter.service.RegMemberService;
import com.jje.membercenter.service.WebMemberService;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.handler.EbpHandler;

@Path("webMember")
@Component
public class WebMemberResource {
	
	private final Logger logger = LoggerFactory.getLogger(WebMemberResource.class);

	@Autowired
	WebMemberRepository webMemberRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberHandler memberHandler;

	@Autowired
	private CbpHandler cbpHandler;
	
	@Autowired
	private NBPHandler nbpHandler;
	
	@Autowired
    private EbpHandler ebpHandler; 
	
	@Autowired
	WebMemberService webMemberService;

    @Autowired
    RegMemberService regMemberService;

    @Value(value = "${member.sns.bind.sms.templateNo}")
    private String memberSnsSmsTemplateNo;
    
    @Autowired
    private BamDataCollector bamDataCollector;    

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/quickRegistAllInfo")
	public Response quickRegistAllInfo(MemberDto dto) {
		RegistResponse result = new RegistResponse();
		try {
			if (validateEmailOrPhone(dto, result)) { 
				return Response.ok().entity(result).build();
			}
			result = memberHandler.quickRegist(new Member(dto));			
            logger.warn("---WebMemberResource.quickRegistAllInfo() updateMemIpAddressByMC ipAddress is {}--- mcMemberCode={}", dto.getIpAddress(), result.getMcMemberCode());
    		if(StringUtils.isNotEmpty(result.getMcMemberCode())&& StringUtils.isNotEmpty(dto.getIpAddress())){
				memberRepository.updateMemIpAddressByMC(dto.getIpAddress(), result.getMcMemberCode());
			}
    		//调用发放优惠券
    		if(result.getStatus().equals(RegistResponseStatus.OK)){
	    		dto.setCardType(CardType.PRESENT.getCode());
	    		dto.setMcMemberCode(result.getMcMemberCode());
				cbpHandler.registerIssue(dto);
    		}
    		
    		if(RegistResponseStatus.OK.equals(result.getStatus())){
    			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.quickRegistAllInfo", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(result)));
    		}else{
    			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.quickRegistAllInfo", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(result)));
    		}
    		
		} catch (Exception e) {
			logger.error("=========WebMemberResource.quickRegistAllInfo({}) quick regist error==========", JaxbUtils.convertToXmlString(dto), e);
			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.quickRegistAllInfo", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(dto), ExceptionToString.toString(e)));
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		logger.warn("WebMemberResource.quickRegistAllInfo() quickRegistAllInfo registResponse="+result.getStatus());
		return Response.status(Status.OK).entity(result).build();
	}
	 
//	@POST
//	@Consumes(MediaType.APPLICATION_XML)
//	@Produces(MediaType.APPLICATION_XML)
//	@Path("/regist")
//	public Response regist(WebMemberDto webMemberDto) {
//		try {
//			if (logger.isDebugEnabled()) {
//				logger.debug("========regist webMemberDto=" + webMemberDto);
//			}
//			WebMemberRegisterReturnDto returnDto = new WebMemberRegisterReturnDto();
//			if (validateEmailOrPhone(webMemberDto, returnDto)) {
//				return Response.ok().entity(returnDto).build();
//			}
//			if (StringUtils.isNotEmpty(webMemberDto.getEmail())) {
//				webMemberDto.setEmail(webMemberDto.getEmail().toLowerCase());
//			}
//			WebMember webMember = WebMember.fromDto(webMemberDto);
//			String mcMemberCode = webMemberService.saveWebMember(webMember);
//			returnDto.setMcMemberCode(mcMemberCode);
//			if(StringUtils.isNotEmpty(mcMemberCode)) {
//				cbpHandler.quickRegisterIssue(
//                        webMemberDto.getRegistChannel(),mcMemberCode,webMember.getRegistTag());
//				ebpHandler.sendRegisterEvent(mcMemberCode, webMemberDto.getRegistChannel().name(), null, null);
//				nbpHandler.sendQuickRegisterSuccessMessage(webMember);
//				bamDataCollector.sendMessage(new BamMessage("vbp.webMember.regist", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(webMemberDto), JaxbUtils.convertToXmlString(returnDto)));
//			}else{
//				bamDataCollector.sendMessage(new BamMessage("vbp.webMember.regist", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(webMemberDto), JaxbUtils.convertToXmlString(returnDto)));
//			}
//			return Response.ok().entity(returnDto).build();
//		} catch (Exception e) {
//			logger.error("========= quick regist error:{}==========", JaxbUtils.convertToXmlString(webMemberDto), e);
//			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.regist", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(webMemberDto), ExceptionToString.toString(e)));
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}
//	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/regist")
	public Response regist(WebMemberDto webMemberDto) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("========regist webMemberDto=" + webMemberDto);
			}
			WebMemberRegisterReturnDto returnDto = new WebMemberRegisterReturnDto();
			boolean flag = false;
			if (validateEmailOrPhone(webMemberDto, returnDto)) {
				return Response.ok().entity(returnDto).build();
			}
			
			String psw = memberRepository.getRandomPassword();
			if(webMemberDto.getPwd()==null || "".equals(webMemberDto.getPwd())){
				webMemberDto.setPwd(MD5Utils.generatePassword(psw));
				flag = true;
			}else{
				psw = webMemberDto.getPwd();
				webMemberDto.setPwd(webMemberDto.getPwd());
			}
			MemberRegisterDto dto = toMemberRegisterResponse(webMemberDto);
        	MemberRegisterResponse response  =  regMemberService.quickRegisterMember(dto);
        	
        	if("00001".equals(response.getBody().getRecode().toString())) {
        		WebMember webMember = WebMember.fromDto(webMemberDto);
        		String mcMemberCode = regMemberService.getMcMemberCodeByMemberNum(response.getBody().getRecord().getMember().getMemberCode());
        		
    			returnDto.setMcMemberCode(mcMemberCode);
    			
    			if(StringUtils.isNotEmpty(mcMemberCode)) {
    				cbpHandler.quickRegisterIssue(
                            webMemberDto.getRegistChannel(),mcMemberCode,webMemberDto.getRegistTag());
    				ebpHandler.sendRegisterEvent(mcMemberCode, webMemberDto.getRegistChannel().name(), null, null);
    				//发送的是没加密过的密码
    				if(flag){
    					if(webMember.getEmail()!=null){
        					webMember.setPwd(psw);
            				webMember.setTempCardNo(response.getBody().getRecord().getMember().getJJBECardNumber());
            				nbpHandler.sendQuickRegisterSuccessMessage(webMember);
        				}
        				if(webMember.getPhone()!=null){
        					webMember.setPwd(psw);
            				webMember.setTempCardNo(response.getBody().getRecord().getMember().getJJBECardNumber());
            				nbpHandler.sendSMSForWebMember("M_Initial_Password_SMS", webMember);
            			}
    				}
    				bamDataCollector.sendMessage(new BamMessage("vbp.webMember.regist", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(webMemberDto), JaxbUtils.convertToXmlString(returnDto)));
    			}else{
    				bamDataCollector.sendMessage(new BamMessage("vbp.webMember.regist", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(webMemberDto), JaxbUtils.convertToXmlString(returnDto)));
    			}
        	}
        	return Response.ok().entity(returnDto).build();
			
		} catch (Exception e) {
			logger.error("========= quick regist error:{}==========", JaxbUtils.convertToXmlString(webMemberDto), e);
			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.regist", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(webMemberDto), ExceptionToString.toString(e)));
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
    @POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/registThirdParty")
	public Response registThirdParty(WebMemberDto webMemberDto) {
    	Response r = this.regist(webMemberDto);
    	return r;
//		try {
//			WebMemberRegisterReturnDto returnDto = new WebMemberRegisterReturnDto();
//            if(StringUtils.isBlank(webMemberDto.getEmail()) || StringUtils.isBlank(webMemberDto.getPhone())){
//                returnDto.setStatus("-3");
//                returnDto.setMessage("邮箱或手机为必填");
//                logger.warn("邮箱或手机为空!");
//                return Response.ok().entity(returnDto).build();
//            }
//            if (validateEmailOrPhone(webMemberDto, returnDto)) return Response.ok().entity(returnDto).build();
//            String password = memberRepository.getRandomPassword();
//            webMemberDto.setPwd(MD5Utils.generatePassword(password));
//            if(StringUtils.isNotEmpty(webMemberDto.getEmail()))
//            	webMemberDto.setEmail(webMemberDto.getEmail().toLowerCase());
//            if(StringUtils.isNotEmpty(webMemberDto.getEmail()))
//            	webMemberDto.setEmail(webMemberDto.getEmail().toLowerCase());
//            String mcMemberCode = webMemberService.saveWebMember(WebMember.fromDto(webMemberDto));
//			returnDto.setMcMemberCode(mcMemberCode);
//            //发送给用户随机密码
//            WebMember webMember = WebMember.fromDto(webMemberDto);
//            //mbpHandler.sendSMSForWebMember(memberRegisterSendMsg,webMember);
//            webMember.setPwd(password);//把没有MD5加密的密码发送给用户
//            nbpHandler.sendSMSForWebMember(memberSnsSmsTemplateNo,webMember);
//            ///
//            logger.warn("-----registThirdParty finished!-----phone="+webMemberDto.getPhone());
//            bamDataCollector.sendMessage(new BamMessage("vbp.webMember.registThirdParty", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(webMemberDto), JaxbUtils.convertToXmlString(returnDto)));
//			return Response.ok().entity(returnDto).build();
//		} catch (Exception e) {
//			logger.error("========= registThirdParty error==========phone="+webMemberDto.getPhone(), e);
//			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.registThirdParty", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(webMemberDto), ExceptionToString.toString(e)));
//			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//		}
	}
    
    @GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryRegisterByCellphoneOrEmail/{name}")
	public Response queryRegisterByCellphoneOrEmail(@PathParam("name") String name) {
		try {
			logger.warn("queryRegisterByCellphoneOrEmail name="+name);
			QuickRegistResult res = new QuickRegistResult();
			res.setStatus(QuickRegistStatus.EXIST);
			MemberVerfyDto existMem = getExistMemberVerfy(name);
			if (existMem == null){
				res.setStatus(QuickRegistStatus.NOT_EXIST);
			}
			logger.warn("queryRegisterByCellphoneOrEmail quickRegistResult="+res.getStatus());
			return Response.status(Status.OK).entity(res).build();
		} catch (Exception e) {
			logger.error("========= queryRegisterByCellphoneOrEmail error========== name="+name, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@SuppressWarnings("deprecation")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/completeWebMemberInfo")
	public Response completeWebMemberInfo(MemberDto dto) {
		try {
			logger.info("快速注册会员转正 getMcMemberCode-->"+dto.getMcMemberCode());
//			WebMember webMember=webMemberRepository.getWebMember(dto.getId());
			Member member = memberRepository.getMemberByMcMemberCode(dto.getMcMemberCode());
			if(member == null){
				bamDataCollector.sendMessage(new BamMessage("vbp.webMember.completeWebMemberInfo", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(dto), "无符合快速注册转正条件会员"));
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			RegistResponse result = toFullMember(dto,member);
			if(RegistResponseStatus.OK.equals(result.getStatus())){
	  			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.completeWebMemberInfo", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(result)));
	  		}else{
	  			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.completeWebMemberInfo", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(result)));
	  		}
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			logger.error("========= completeWebMemberInfo error:{}==========", JaxbUtils.convertToXmlString(dto), e);
			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.completeWebMemberInfo", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(dto), ExceptionToString.toString(e)));
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/completeWebMemberInfoForGateway")
	public Response completeWebMemberInfoForGateway(MemberDto dto) {
		try {
			logger.info("手机快速注册会员完善信息 McMemberCode-->" + dto.getMcMemberCode());
//			WebMember webMember = webMemberRepository.getMemberByMcMemberCode(dto.getMcMemberCode());
			Member member = memberRepository.getMemberByMcMemberCode(dto.getMcMemberCode());
			if(member == null){
				bamDataCollector.sendMessage(new BamMessage("vbp.webMember.completeWebMemberInfoForGateway", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(dto), "无符合快速注册转正条件会员"));
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			RegistResponse result = toFullMember(dto,member);
			if(RegistResponseStatus.OK.equals(result.getStatus())){
  			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.completeWebMemberInfoForGateway", StatusCode.SUCCESS, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(result)));
  		}else{
  			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.completeWebMemberInfoForGateway", StatusCode.BIZ_ERROR, JaxbUtils.convertToXmlString(dto), JaxbUtils.convertToXmlString(result)));
  		}
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			logger.error("completeWebMemberInfoForGateway error:{}",JaxbUtils.convertToXmlString(dto), e);
			bamDataCollector.sendMessage(new BamMessage("vbp.webMember.completeWebMemberInfoForGateway", StatusCode.SYS_ERROR, JaxbUtils.convertToXmlString(dto), ExceptionToString.toString(e)));
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/checkIdentityExists")
	public Response checkIdentityExists(MemberDto dto) {
		try {
			logger.info("========= checkIdentityExists ========= begin"+dto.getId());
			CheckStatusResponse result = webMemberService.checkIdentityExists(dto);
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			logger.error("========= checkIdentityExists error==========", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/getWebMemberById/{id}")
	public Response getWebMemberById(@PathParam("id") Long id) {
		try {
			WebMember webMember=webMemberRepository.getWebMember(id);
            if (webMember == null) {
            	logger.warn("getWebMemberById({}) no result!", id);
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
            return Response.ok().entity(MemberDto.convertMemberDto(webMember.toDto())).build();
        } catch (Exception e) {
        	logger.error("getWebMemberById({}) error!", id, e);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
	}

//	private Member getUpdateMember(Member member, List<Member> memberResultList) {
//		Member m = getValidMember(memberResultList);
//		logger.error("========= getUpdateMember======cardNo===="+m.getCardNo());
//		m.setEmail(member.getEmail());
//		m.setCellPhone(member.getCellPhone());
//		m.setPassword(member.getPassword());
//		m.setRemindQuestion(member.getRemindQuestion());
//		m.setRemindAnswer(member.getRemindAnswer());
//		m.setActiveChannel(member.getActiveChannel());
//        m.setActiveTag(member.getRegisterTag());
//		return m;
//	}

    private RegistResponse toFullMember(MemberDto dto,Member member) throws Exception {
    	if(!StringUtils.isBlank(dto.getIdentityNo())){
			member.setIdentityNo(dto.getIdentityNo());
		}
		if(!StringUtils.isBlank(dto.getIdentityType())){
			member.setIdentityType(dto.getIdentityType());
		}
		if(!StringUtils.isBlank(dto.getFullName())){
			member.setFullName(dto.getFullName());
		}
    	RegistResponse result = new RegistResponse();
    	result.setMcMemberCode(member.getMcMemberCode());
    	try {
    		if(MemType.QUICK_REGIST.getTypeCode().equals(member.getMemberType())){
    			result = memberHandler.updateQuickMemberBaseInfo(member);
    		}else{
    			result.setStatus(RegistResponseStatus.MEMBER_ALREDY_REGIST);
    		}
		} catch (Exception e) {
			result.setStatus(RegistResponseStatus.FAIL);
		}
    	return result;
        
//        List<Member> memberResultList = memberRepository.checkIdentifyInfo(member);
//        logger.warn("toFullMember(Member member) memberResultList=", memberResultList);
//        if (memberResultList != null && !memberResultList.isEmpty()) {
//            result = migrateWebMemberInfo(member, memberResultList);
//            logger.warn("toFullMember(Member member) result=", result);
//            //手机/网站:完善信息时，激活锦江之星会员
//            activingOperation(member.getRegisterSource(), member.getMcMemberCode(), result, member.getRegistTag());
//        } 
//        else {
//            MemberRegisterResponse response = regMemberService.registerMember(member);
//            RegistResponseStatus status = RegistResponseStatus.getRegistResponseStatus(response.getHead().getRetmsg());
//            result.setStatus(status);
//            if (response.isExecSuccess()) {
//                String mcCode = regMemberService.getMcMemberCodeByMemberNum(response.getBody().getRecord().getMember().getMemberCode());
//                if (StringUtils.isNotEmpty(mcCode)) {
//                    memberRepository.updateMemIpAddressByMC(member.getIpAddress(), mcCode);
//                    ebpHandler.sendCompleteInfoEvent(mcCode, member.getRegisterSource(), member.getRegistTag());
//                    result.setMcMemberCode(mcCode);
//                    result.setStatus(RegistResponseStatus.OK);
//                }
//            }
//
//        }
    }

//    private void activingOperation(String registerSource, String mc, RegistResponse result, String registTag) {
//		logger.info("完善激活 activingIssueCoupon(String registerSource, String mc, RegistResponse result)"+registerSource+mc+result.getStatus());
//		//手机完善信息/ 网站完善信息
//		if (RegistResponseStatus.OK.equals(result.getStatus())) {
//			//统一使用事件触发
//			//cbpHandler.activingIssueCoupon(registerSource, mc);
//			ebpHandler.sendActivateEvent(mc, registerSource,registTag);
//		}
//	}
//
//	private RegistResponse migrateWebMemberInfo(Member member, List<Member> memberResultList) throws Exception {
//		RegistResponse result = new RegistResponse();
//		result.setStatus(RegistResponseStatus.MEMBER_ALREDY_REGIST);
//		if (!isAcitiveStatus(memberResultList)){
//			result = memberHandler.updateMemberBaseInfo(getUpdateMember(member, memberResultList));
//		}
//		return result;
//	}

//	private Member getValidMember(List<Member> memberResultList) {
//		Member m = memberResultList.get(0);
//		for (Member mem : memberResultList) {
//			logger.error("========= getValidMember======cardNo===="+mem.getCardNo());
//			String cardNo=mem.getCardNo().toUpperCase();
//			//G,X,J,Y,U,H
//			if ((cardNo.startsWith("H"))||(cardNo.startsWith("G"))||
//				(cardNo.startsWith("X"))||(cardNo.startsWith("J"))||
//				(cardNo.startsWith("Y"))||(cardNo.startsWith("U"))) {
//				m = mem;
//				break;
//			}
//		}
//		return m;
//	}

//	private boolean isAcitiveStatus(List<Member> memberResultList) {
//		boolean isAcitiveStatus = false;
//		for (Member m : memberResultList) {
//			if (!"Not Activiate".equals(m.getActivateCode())) {
//				isAcitiveStatus = true;
//				break;
//			}
//		}
//		return isAcitiveStatus;
//	}

	private boolean validateEmailOrPhone(MemberDto dto, RegistResponse response) {
		if (memberRepository.isExistMemberOfEmail(dto.getEmail())) {
			response.setStatus(RegistResponseStatus.EMAIL_EXIST);
			logger.warn("该邮箱已被使用 email=" + dto.getEmail());
			return true;
		}
		if (memberRepository.isExistMemberOfPhone(dto.getCellPhone())) {
			response.setStatus(RegistResponseStatus.MOBILE_EXIST);
			logger.warn("该手机号码已被使用 phone=" + dto.getCellPhone());
			return true;
		}
		return false;
	}
	
	private boolean validateEmailOrPhone(WebMemberDto webMemberDto, WebMemberRegisterReturnDto returnDto) {
		String email = webMemberDto.getEmail();
		if (StringUtils.isNotBlank(email) && memberRepository.isExistMemberOfEmail(email)) {
			returnDto.setStatus("-1");
			returnDto.setMessage("该邮箱已被使用");
			logger.warn("该邮箱已被使用 email=" + email);
			return true;
		}
		String phone = webMemberDto.getPhone();
		if (StringUtils.isNotBlank(phone) && memberRepository.isExistMemberOfPhone(phone)) {
			returnDto.setStatus("-2");
			returnDto.setMessage("该手机号码已被使用");
			logger.warn("该手机号码已被使用 phone=" + phone);
			return true;
		}
		return false;
	}
	
	private MemberVerfyDto getExistMemberVerfy(String name) {
		MemberVerfyDto result = new MemberVerfyDto();
		List<MemberVerfy> ms = memberRepository.queryRegisterByCellphoneOrEmailOrCardNo(name);
		result = (ms == null || ms.isEmpty()) ? null : ms.get(0).toDto();
		return result;
	}

	public  MemberRegisterDto toMemberRegisterResponse(WebMemberDto webMemberDto) {
    	MemberRegisterDto dto = new MemberRegisterDto();
        MemberInfoDto infoDto = new MemberInfoDto();
        infoDto.setMobile(webMemberDto.getPhone());
        infoDto.setEmail(webMemberDto.getEmail());
        infoDto.setPasssword(webMemberDto.getPwd());
        infoDto.setRegisterSource(webMemberDto.getRegistChannel());
        infoDto.setTier(webMemberDto.getMemberTier());
        infoDto.setHotelChannel(webMemberDto.getHotelChannel());
        //infoDto.setMemberType("TmpMem");
        dto.setMemberInfoDto(infoDto);
        dto.setRegisterTag(webMemberDto.getRegistTag());
    	return dto;
    }
    
}
