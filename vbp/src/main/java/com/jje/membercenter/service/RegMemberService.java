package com.jje.membercenter.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.data.util.RecommendStatic;
import com.jje.dto.membercenter.MemberCardType;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.vbp.member.MemberActivateDto;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.remote.crm.datagram.request.MemberRegistReq;
import com.jje.membercenter.xsd.MemberQuickRegisterRequest;
import com.jje.membercenter.xsd.MemberRegisterRequest;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.vbp.memberRecommend.domain.MemberRecommendDomain;
import com.jje.vbp.memberRecommend.service.MemberRecommendService;

@Component
public class RegMemberService {

    @Autowired
    CRMMembershipProxy crmMembershipProxy;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    MemberService memberService;
    
    @Autowired
    MemberRecommendService memberRecommendService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public MemberRegisterResponse registerMember(MemberRegisterDto registerDto)throws Exception{
        registerDto.initMemberTier();//初始化卡等级，如果为空默认是银卡
        MemberRegisterRequest request = new MemberRegisterRequest(registerDto);
        if(logger.isInfoEnabled()){
            logger.info("registerMember  send crm  Request---> requestInfo =" + JaxbUtils.convertToXmlString(request));
        }
        MemberRegisterResponse response = crmMembershipProxy.addVIPMembership(request);
        if(logger.isInfoEnabled()){
            logger.info("registerMember  send crm  response---> responseInfo =" + JaxbUtils.convertToXmlString(response));
        }
        if(response.isExecSuccess()) {
            storeMember(response.getBody(), registerDto);
        }
        return response;
    }
    
    public MemberRegisterResponse quickRegisterMember(MemberRegisterDto registerDto)throws Exception{
        registerDto.initMemberTier();//初始化卡等级，如果为空默认是礼卡
//        registerDto.initNewMemberTier();
        MemberRegisterResponse response = new MemberRegisterResponse();
            MemberQuickRegisterRequest request = new MemberQuickRegisterRequest(registerDto);
            response = new MemberRegisterResponse();
            if(logger.isDebugEnabled()){
            	logger.debug("registerMember  send crm  Request---> requestInfo =" + JaxbUtils.convertToXmlString(request));
            }
            response = crmMembershipProxy.addQuickMembership(request);
            if(logger.isDebugEnabled()){
            	logger.debug("registerMember  send crm  response---> responseInfo =" + JaxbUtils.convertToXmlString(response));
            }
            if(response.isExecSuccess()){
                storeMember(response.getBody(),registerDto);
//                String mcMemberCode = getMcMemberCodeByMemberNum(response.getBody().getRecord().getMember().getMemberCode());
//                response.getBody().getRecord().getMember().setMcMemberCode(mcMemberCode);
            }
        return response;
    }


    public MemberRegisterResponse registerMember(Member member)throws Exception{
        MemberRegisterDto registerDto = memberToMemberRegisterDto(member);
        return registerMember(registerDto);
    }

    public MemberRegisterResponse registerMember(MemberActivateDto memberActivateDto)throws Exception{
        MemberRegisterDto registerDto = memberActivateDtoToMemberRegisterDto(memberActivateDto);
        return registerMember(registerDto);
    }

    private MemberRegisterDto memberActivateDtoToMemberRegisterDto(MemberActivateDto memberActivateDto) {
        MemberRegisterDto dto = new MemberRegisterDto();
        MemberInfoDto info = new MemberInfoDto();
        info.setSurname(memberActivateDto.getFullName());
        info.setPasssword(memberActivateDto.getPassword());
        info.setEmail(memberActivateDto.getEmail());
        info.setMobile(memberActivateDto.getPhone());
        info.setCertificateNo(memberActivateDto.getIdentityNo());
        info.setCertificateType(memberActivateDto.getIdentityType());
        info.setMemberType(MemberCardType.JJCARD.getCode());
        info.setTier(MemberDegree.CLASSIC.getCode());
        info.setRegisterSource(memberActivateDto.getRegistChannel());
        info.setThirdpartyType(memberActivateDto.getThirdpartyType());
        MemberRegistReq.Contact contact = new MemberRegistReq.Contact();
        contact.setMobile(memberActivateDto.getPhone());
        contact.setEmail(memberActivateDto.getEmail());
        contact.setCardid(memberActivateDto.getIdentityNo());
        contact.setCardtype(memberActivateDto.getIdentityType());
        List list = new ArrayList();
        list.add(contact);
        info.setContacteeList(list);
        dto.setMemberInfoDto(info);
        return dto;
    }

    private MemberRegisterDto memberToMemberRegisterDto(Member member) {
        MemberRegisterDto dto = new MemberRegisterDto();
        MemberInfoDto info = new MemberInfoDto();
        info.setSurname(member.getFullName());
        info.setPasssword(member.getPassword());
        info.setEmail(member.getEmail());
        info.setMobile(member.getCellPhone());
        info.setCertificateNo(member.getIdentityNo());
        info.setCertificateType(member.getIdentityType());
        info.setMemberType(MemberCardType.JJCARD.getCode());
        info.setTier(MemberDegree.CLASSIC.getCode());
        info.setRegisterSource(RegistChannel.valueOf(member.getRegisterSource()));
        dto.setRegisterTag(member.getRegisterTag());
        info.setThirdpartyType(member.getThirdpartyType());
        MemberRegistReq.Contact contact = new MemberRegistReq.Contact();
        contact.setMobile(member.getCellPhone());
        contact.setEmail(member.getEmail());
        contact.setCardid(member.getIdentityNo());
        contact.setCardtype(member.getIdentityType());
        List list = new ArrayList();
        list.add(contact);
        info.setContacteeList(list);
        dto.setMemberInfoDto(info);
        return dto;
    }


    private void storeMember(MemberRegisterResponse.Body body, MemberRegisterDto dto) {
        logger.warn("================callBack store=============email is {}====", body.getRecord().getMember().getEmail());
        MemberDto memberDto = getMemberDtoByBody(body);
        memberDto.setActiveDate(new Date());
        memberDto.setActiveChannel(memberDto.getRegisterSource());
        
        MemberRecommendDomain recommend = new MemberRecommendDomain();
        recommend.setRecommenderId(dto.getReferredId());
        recommend.setRegisterId(body.getMembid());
        recommend.setCampaign(RecommendStatic.RECOMMEND_CAMPAIGN);

        if (logger.isWarnEnabled()) logger.warn(JaxbUtils.convertToXmlString(memberDto));
        Member member = new Member(memberDto);
        member.setHotelChannel(dto.getMemberInfoDto().getHotelChannel());
        
        try {
            List<Member> memberList = memberRepository.queryByMemberID(member.getMemberID());
            if (memberList != null && memberList.size() != 0) {
                logger.warn("the record for the memberId has existed");
            }
            if (memberDto.getLastUpd() == null) {
                member.setLastUpd(new Date());
            }
            // 事务处理
            member.setMcMemberCode(memberRepository.generateMcMemberCode());
            memberService.storeMember(member, memberDto);
            if(dto.getReferredId()!=null && !"".equals(dto.getReferredId())){
            	memberRecommendService.insertRecommend(recommend);
            }
        } catch (Exception e) {
            logger.error("store member error:{}", JaxbUtils.convertToXmlString(memberDto), e);
        }
    }

    private MemberDto getMemberDtoByBody(MemberRegisterResponse.Body body) {
        com.jje.membercenter.xsd.MemberRegisterResponse.Record.Member member = body.getRecord().getMember();
        MemberDto dto = new MemberDto();
        dto.setMemberID(body.getMembid());
        dto.setFullName(member.getFullName());
        dto.setIdentityNo(member.getCDNumber());
        dto.setIdentityType(member.getCDType());
        dto.setCellPhone(member.getCellPhone());
        dto.setEmail(member.getEmail());
        dto.setLastUpd(DateUtils.parseDate(member.getLastUpdDate(), "MM/dd/yyyy"));
        dto.setNewMemberHierarchy(member.getMemberHierarchy());//crm传过来的层级放入新层级字段
        dto.setMemberType(member.getMemberType());
        dto.setTitle(member.getMemberTitle());
        dto.setPassword(member.getPassword());
        dto.setRegisterDate(DateUtils.parseDate(member.getRegisterDate(), "MM/dd/yyyy"));
        dto.setRegisterSource(member.getRegisterSource());
        dto.setRegisterTag(member.getRegisterSourceTag());
        dto.setRemindAnswer(member.getRemindAnswer());
        dto.setRemindQuestion(member.getRemindQuestion());
        dto.setStatus(member.getStatus());
        dto.setThirdpartyType(member.getThirdpartyType());
        dto.setCardList(member.getListofcard().getMemberCardList());
        dto.setMemberCode(member.getMemberCode());
        dto.setActivateCode(member.getJJBEActivateCode());
        dto.setCardNo(member.getJJBECardNumber());
        dto.setCardLevel(member.getCardLevel());
//        dto.setMemberHierarchy(member.getMemberHierarchy());
		dto.setScoreType(NumberUtils.toInt(body.getRecord().getMember().getJJBEConvertFlag()));
        return dto;
    }

    private void updateIpAddressByMcCode(String ipAddress, String mcMemberCode) {
        memberRepository.updateMemIpAddressByMC(ipAddress, mcMemberCode);
    }


    public String getMcMemberCodeByMemberNum(String memberNum) {
        logger.debug("getMcMemberCodeByMemberNum({})", memberNum);
        String mcMemberCode = memberRepository.getMcMemberCodeByMemberNum(memberNum);
        if (StringUtils.isEmpty(mcMemberCode)) {
            logger.warn("getMcMemberCodeByMemberNum({}) no result!", memberNum);
            return mcMemberCode;
        }
        logger.debug("{} getMcMemberCodeByMemberNum({})", mcMemberCode, memberNum);
        return mcMemberCode;
    }




}
