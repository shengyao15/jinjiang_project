package com.jje.vbp.member.service;

import java.util.List;

import com.jje.membercenter.service.MemberService;
import com.jje.membercenter.service.RegMemberService;
import com.jje.membercenter.xsd.MemberRegisterResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.MemberCardType;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.vbp.member.MemberActivateDto;
import com.jje.dto.vbp.member.MemberActivateResultDto;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.remote.crm.datagram.request.MemberRegistReq;
import com.jje.membercenter.remote.crm.datagram.response.MemberRegistRes;
import com.jje.membercenter.remote.crm.support.CrmResponse.Status;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.handler.EbpHandler;

@Service
public class MemberManagerService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberManagerService.class);
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CRMMembershipRepository crmMembershipRepository;
	
	@Autowired
	private WebMemberRepository webMemberRepository;

	@Autowired
	private CbpHandler cbpHandler;

    @Autowired
    private EbpHandler ebpHandler;

    @Autowired
    private RegMemberService regMemberService;

	public MemberActivateResultDto validateIdentifyInfo(MemberActivateDto memberActivateDto){
		List<Member> members= memberRepository.queryIdentifyInfo(new Member(memberActivateDto.getIdentityType(),memberActivateDto.getIdentityNo(),memberActivateDto.getFullName()));
		if(CollectionUtils.isEmpty(members)){
		   return new MemberActivateResultDto(null,MemberActivateResultDto.StatusMessage.NOT_FOUND);	
		}
		for(Member member:members){
		  if(!"Not Activiate".equals(member.getActivateCode())){
			return new MemberActivateResultDto(member.toDto(),MemberActivateResultDto.StatusMessage.MEMBER_ACTIVATED);
		  }
		  if(!CollectionUtils.isEmpty(member.getMemberVerfyList())){
			return new MemberActivateResultDto(member.toDto(),MemberActivateResultDto.StatusMessage.MEMBER_VERIFY_EXIST);
		  }
		}
		Member member = getValidMember(members);
		if(null==member){
		  return new MemberActivateResultDto(null,MemberActivateResultDto.StatusMessage.NOT_FOUND);	
		}
		if(StringUtils.isNotBlank(member.getMcMemberCode())){
	      return new MemberActivateResultDto(null,MemberActivateResultDto.StatusMessage.MC_MEMBER_CODE_EXSIT);	
		} 
		return new MemberActivateResultDto(member.toDto(), MemberActivateResultDto.StatusMessage.OK);
	}
	

	public MemberActivateResultDto activate(MemberActivateDto memberActivateDto) {
		//先验证会员信息是否存在并且是为激活的
		MemberActivateResultDto  resultDto= validateIdentifyInfo(memberActivateDto);
		if(!MemberActivateResultDto.StatusMessage.OK.equals(resultDto.getStatusMessage())){
		   return resultDto;
		}
		MemberDto memberDto= resultDto.getMemberDto();
		logger.warn("进入激活流程  memberActivateDto:{} ,memberDto:{}",memberActivateDto,memberDto);
	    if(StringUtils.isBlank(memberDto.getCellPhone()) && StringUtils.isBlank(memberDto.getEmail())){
	    	logger.error("手机邮箱不能为空",memberDto);
	    	resultDto.setStatusMessage(MemberActivateResultDto.StatusMessage.PHONE_AND_EMAIL_NULL);	
		    return  resultDto;
		}
	    MemberBasicInfoDto memberBasicInfoDto = memberActivateDto.toMemberBasicInfoDto(memberDto);
//	    String rcode = crmMembershipRepository.activeMember(memberBasicInfoDto);
	    String rcode = memberService.activeMember(memberBasicInfoDto);
	    if(!"00001".equals(rcode)){
	       logger.error("会员激活失败 {}",memberActivateDto);
	       resultDto.setStatusMessage(MemberActivateResultDto.StatusMessage.ACTIVATE_FAIL);
	    }
	    Member member= memberRepository.getMemberByMemberNum(memberDto.getMemberCode());
	    resultDto.setMemberDto(member.toDto());
	    ebpHandler.sendActivateEvent(member.getMcMemberCode(), memberActivateDto.getRegistChannel());
	    return resultDto;
	}
	
	
	public MemberActivateResultDto registMember(MemberActivateDto memberActivateDto,MemberActivateResultDto resultDto) throws Exception {
//		//开始进入注册流程
//		MemberRegistReq req = getMemberRegistReq(memberActivateDto);
//		//开始CRM注册
//		MemberRegistRes res=req.send();
//		if (!res.isStatus(Status.SUCCESS)){
//			logger.error("注册会员CRM10009接口错误,返回："+JaxbUtils.convertToXmlString(res));
//			resultDto.setStatusMessage(MemberActivateResultDto.StatusMessage.REGIST_FAIL);
//			return resultDto;
//		}
        MemberRegisterResponse response = regMemberService.registerMember(memberActivateDto);
		Member member = memberRepository.getMemberByMemberNum(response.getBody().getRecord().getMember().getMemberCode());
		memberRepository.updateMemIpAddressByMC(memberActivateDto.getIpAddress(), member.getMcMemberCode());
		MemberDto memberDto=member.toDto();
	    resultDto.setMemberDto(memberDto);
	    resultDto.setStatusMessage(MemberActivateResultDto.StatusMessage.OK);
		//调用发放优惠券
	    ebpHandler.sendCompleteInfoEvent(member.getMcMemberCode(), member.getRegisterSource());
		return resultDto;
	}
	
	
	public MemberActivateResultDto completeWebMember(MemberActivateDto memberActivateDto) throws Exception {
		//把相关信息设置为快速注册的
		WebMember webMember=webMemberRepository.getMemberByMcMemberCode(memberActivateDto.getMcMemberCode());
		memberActivateDto.setRemindQuestion(webMember.getQuestion());
		memberActivateDto.setRemindAnswer(webMember.getAnswer());
		memberActivateDto.setEmail(webMember.getEmail());
		memberActivateDto.setPhone(webMember.getPhone());
		memberActivateDto.setPassword(webMember.getPwd());
		
		MemberActivateResultDto resultDto= activate(memberActivateDto);
	    //如果没有找到直接注册
	    if(MemberActivateResultDto.StatusMessage.NOT_FOUND.equals(resultDto.getStatusMessage())){
	    	logger.warn("会员进入注册流程  {}",memberActivateDto);
	    	return registMember(memberActivateDto,resultDto);
	    }
	    return resultDto;
	}

	
	
	private MemberRegistReq getMemberRegistReq(MemberActivateDto memberActivateDto) {
		MemberRegistReq req = new MemberRegistReq();
		MemberRegistReq.Record record = new MemberRegistReq.Record();
		
		record.setName(memberActivateDto.getFullName());
		record.setPasswd(memberActivateDto.getPassword());
		record.setPwdquestion(memberActivateDto.getRemindQuestion());
		record.setPwdanswer(memberActivateDto.getRemindAnswer());
		record.setJjcardtype(MemberCardType.JJCARD.getCode());
		record.setTier(MemberDegree.CLASSIC.getCode());
        record.setRegichnl(memberActivateDto.getRegistChannel().name());
        //新增合作类型
        record.setThirdpartyType(memberActivateDto.getThirdpartyType());
		MemberRegistReq.Listofcontact listofcontact = new MemberRegistReq.Listofcontact();
		MemberRegistReq.Contact contact = new MemberRegistReq.Contact();
		contact.setMobile(memberActivateDto.getPhone());
		contact.setEmail(memberActivateDto.getEmail());
		contact.setCardid(memberActivateDto.getIdentityNo());
		contact.setCardtype(memberActivateDto.getIdentityType());
		listofcontact.getContact().add(contact);
		
		record.setListofcontact(listofcontact);
		
		req.getBody().setRecord(record);
		return req;
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


	

}
