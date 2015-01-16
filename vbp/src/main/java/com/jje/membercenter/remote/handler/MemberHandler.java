package com.jje.membercenter.remote.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.MD5Utils;
import com.jje.dto.membercenter.AddressDto;
import com.jje.dto.membercenter.MemberCardType;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.RegistResponse;
import com.jje.dto.membercenter.RegistResponseStatus;
import com.jje.dto.membercenter.cardbind.CardBindStatus;
import com.jje.dto.membercenter.cardbind.CardBindStatusDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;
import com.jje.membercenter.remote.crm.datagram.request.LoypointReq;
import com.jje.membercenter.remote.crm.datagram.request.MemberBatchRegistReq;
import com.jje.membercenter.remote.crm.datagram.request.MemberPartnerCardReq;
import com.jje.membercenter.remote.crm.datagram.request.MemberPartnerCardRes;
import com.jje.membercenter.remote.crm.datagram.request.MemberQueryAddressReq;
import com.jje.membercenter.remote.crm.datagram.request.MemberQuickRegistReq;
import com.jje.membercenter.remote.crm.datagram.request.MemberQuickRegistReq.Record;
import com.jje.membercenter.remote.crm.datagram.request.MemberRegistReq;
import com.jje.membercenter.remote.crm.datagram.request.TierUpdateReq;
import com.jje.membercenter.remote.crm.datagram.response.LoypointRes;
import com.jje.membercenter.remote.crm.datagram.response.MemberQueryAddressRes;
import com.jje.membercenter.remote.crm.datagram.response.MemberQuickRegistRes;
import com.jje.membercenter.remote.crm.datagram.response.MemberRegistRes;
import com.jje.membercenter.remote.crm.datagram.response.TierUpdateRes;
import com.jje.membercenter.remote.crm.support.CrmConstant;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.remote.crm.support.CrmResponse.Status;
import com.jje.membercenter.remote.crm.support.CrmTransCode;
import com.jje.membercenter.service.MemberService;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberInfoUpdateResponse;
import com.jje.membercenter.xsd.MemberQuickBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberQuickBasicInfoUpdateResponse;

@Component
public class MemberHandler {

    @Autowired
    private MemberScoreLevelInfoRepository memberScoreLevelInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CRMMembershipProxy crmMembershipProxy;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getAddress(String memberCode) {
        MemberQueryAddressRes res=getMemberQueryAddressReq(memberCode).send();
        if(res.getBody().getListofcontact()!=null && !res.getBody().getListofcontact().getContact().isEmpty() ){
            MemberQueryAddressRes.Contact contact=res.getBody().getListofcontact().getContact().get(0);
            if(contact.getListofpersonaladdress()!=null && !contact.getListofpersonaladdress().getPersonaladdress().isEmpty() ){
                MemberQueryAddressRes.Personaladdress addr=contact.getListofpersonaladdress().getPersonaladdress().get(0);
                AddressDto addre=memberRepository.getAddressValues(addr.toAddressDto());
                return addre.buildAllAddress()+addr.getStreet();
            }
        }
        return null;
    }	
    
    public MemberPartnerCardRes getPartnerCards(String memberCode){
    	MemberPartnerCardReq req = new MemberPartnerCardReq();
    	MemberPartnerCardReq.RequestBody body = new MemberPartnerCardReq.RequestBody();
    	body.setMembid(memberCode);
    	req.setBody(body);
    	MemberPartnerCardRes res = new MemberPartnerCardRes();
    	res = req.send();
    	return res;
    	
    }


    private MemberQueryAddressReq getMemberQueryAddressReq(String memberCode) {
        MemberQueryAddressReq  req=new MemberQueryAddressReq();
        req.getBody().setMembid(memberCode);
        return req;
    }


    public Integer getMemberScore(String memberId) throws Exception {
        Integer loypoint = 0;
        LoypointReq req = new LoypointReq();
        req.getBody().setMembid(memberId);
        LoypointRes res = req.send();
        if (res.isStatus(CrmResponse.Status.ERROR) || res.isStatus(CrmResponse.Status.FAIL)
                || (StringUtils.isNotBlank(res.getBody().getPoints()) && "-1"
                .equals(res.getBody().getPoints())))
            throw new Exception("Call crm happen exception ");
        if (StringUtils.isNotBlank(res.getBody().getPoints()))
            loypoint = Integer.parseInt(res.getBody().getPoints());
        return loypoint;

    }
    
    public void isValidResponse(MemberInfoUpdateResponse response) {
		if(response == null) {
			throw new IllegalArgumentException("MemberInfoUpdateResponse is null");
		}
		if(response.getBody() == null) {
			throw new IllegalArgumentException("MemberInfoUpdateResponse.body is null");
		}
		if(response.getBody().getMember() == null) {
			throw new IllegalArgumentException("member is null");
		}
	}

    public RegistResponse updateMemberBaseInfo(Member member) throws Exception {
        RegistResponse result = new RegistResponse();
		/*
		 * 现在外部调用时传入的memberId实际上是memberNum，crm新接口则接收memberId,特此转换
		 */
		List<Member> members = memberRepository.getMemberCardNo(member.getMemberCode());
		if(members == null || members.size() == 0) { 
			throw new Exception("updateMemberBaseInfo: Member not found");
		}
		member.setMemberID(members.get(0).getMemberID());
//        MemberBasicInfoUpdateResponse response = crmMembershipProxy.updateMemberBasicInfo(getMemberUpdateReq(member));
		MemberInfoUpdateResponse response = crmMembershipProxy.updateMemberInfo(getMemberUpdateReq(member));
		isValidResponse(response);
    	// update
		String memberId = response.getBody().getMembid();
		MemberDto memberDto = response.getBody().getMember();

		if (StringUtils.isBlank(memberId)) {
			throw new Exception("memberId is null");
		}
		memberDto.setMemberID(memberId);
		List<Member> memberList = memberRepository.queryByMemberID(memberId);
		// crmResp.setStatus("数据更新成功");
		if (memberList == null || memberList.size() == 0) {
			throw new Exception("the record for the memberId is not existed");
		}
				
		// 填充memberDto信息
		if (StringUtils.isBlank(memberDto.getMemberCode())) {
			memberDto.setMemberCode(memberList.get(0).getMemberCode());
		}
		memberDto.setId(memberList.get(0).getId());
		if (StringUtils.isBlank(memberDto.getPassword())) {
			memberDto.setPassword(member.getPassword());
		}
		if (CollectionUtils.isEmpty(memberList.get(0).getMemberVerfyList())) {
			// 网站/手机完善信息 / 立即加入迁移流程 时，激活锦江之星会员
			memberDto.setActiveDate(new Date());
		    memberService.updateMemberInfoAndVerifyForMove(memberDto);
			// crmDto.setStatus("迁移成功");
		} else {
			memberRepository.updateMemberInfoAndVerify(new Member(memberDto));
		}
        RegistResponseStatus status = RegistResponseStatus.getRegistResponseStatus(response.getBody().getRemsg());
        String recode = response.getBody().getRecode();
        if (CrmResponse.Status.ERROR.getCode().equals(recode))
            throw new Exception("Call crm happen exception ");
        if (CrmResponse.Status.SUCCESS.getCode().equals(recode))
            status = RegistResponseStatus.OK;
        result.setStatus(status);
        return result;
    }
    
    // 更新非完整会员完善信息
	@SuppressWarnings("deprecation")
	public RegistResponse updateQuickMemberBaseInfo(Member baseInfo) {
		MemberQuickBasicInfoUpdateRequest request = getQuickBaseInfoUpdateRequest(baseInfo);
		logger.warn("***** updateQuickMemberBaseInfo  request *******" + JaxbUtils.convertToXmlString(request));
		RegistResponse result = new RegistResponse();
		try {
			MemberQuickBasicInfoUpdateResponse response = crmMembershipProxy.updateMemberBasicInfo(request);
			MemberQuickBasicInfoUpdateResponse.Body.Member baseInfoMember = (response != null && response.getBody() != null)?response.getBody().getMember():null;
			RegistResponseStatus status = RegistResponseStatus.getRegistResponseStatus(response.getBody().getRemsg());
	        String recode = response.getBody().getRecode();
	        if (CrmResponse.Status.ERROR.getCode().equals(recode)){
	        	throw new Exception("Call crm happen exception ");
	        }
	        
			if(baseInfoMember != null && CrmResponse.Status.SUCCESS.getCode().equals(recode)){
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberID(baseInfo.getMemberID());
				memberDto.setIdentityType(baseInfoMember.getCDType());
				memberDto.setIdentityNo(baseInfoMember.getCDNumber());
				memberDto.setMemberType(baseInfoMember.getMemberType());
				memberDto.setFullName(baseInfoMember.getFullName());
				memberDto.setActivateCode("Activiated");
				
				memberRepository.upgradeMemberInfo(memberDto);
				result.setStatus(RegistResponseStatus.OK);
				return result;
			}
			result.setStatus(status);
			logger.warn("***** updateQuickMemberBaseInfo  response *******" + JaxbUtils.convertToXmlString(response));
		} catch (Exception e) {
			result.setStatus(RegistResponseStatus.FAIL);
			logger.error("updateQuickMemberBaseInfo({}) error!",JaxbUtils.convertToXmlString(request), e);
		}
		return result;
	}


	private MemberQuickBasicInfoUpdateRequest getQuickBaseInfoUpdateRequest(Member baseInfo) {
		MemberQuickBasicInfoUpdateRequest request = new MemberQuickBasicInfoUpdateRequest();
		
		MemberQuickBasicInfoUpdateRequest.Head head = new MemberQuickBasicInfoUpdateRequest.Head();
		MemberQuickBasicInfoUpdateRequest.Body body = new MemberQuickBasicInfoUpdateRequest.Body();
		MemberQuickBasicInfoUpdateRequest.Body.Listofcontact.Contact contact = new MemberQuickBasicInfoUpdateRequest.Body.Listofcontact.Contact();
		MemberQuickBasicInfoUpdateRequest.Body.Listofcontact listofcontact = new MemberQuickBasicInfoUpdateRequest.Body.Listofcontact();

		head.setTranscode(CrmTransCode.UPDATE_QUICK_MEMBER_INFO.getCode());
		head.setSystype(CrmConstant.SYSTEM_TYPE);
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		contact.setCardtype(baseInfo.getIdentityType());
		contact.setCardid(baseInfo.getIdentityNo());
		
		request.setHead(head);
		listofcontact.getContact().add(contact);
		body.setListofcontact(listofcontact);
		body.setMembid(baseInfo.getMemberID());
		body.setName(baseInfo.getFullName());
		
		request.setBody(body);
		return request;
	}
	

    private MemberBasicInfoUpdateRequest getMemberUpdateReq(Member member) {
        MemberBasicInfoUpdateRequest req = new MemberBasicInfoUpdateRequest();
        MemberBasicInfoUpdateRequest.Head head = new MemberBasicInfoUpdateRequest.Head();
        MemberBasicInfoUpdateRequest.Body body = new MemberBasicInfoUpdateRequest.Body();
        MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact contact = new MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact();
        MemberBasicInfoUpdateRequest.Body.Listofcontact listofcontact = new MemberBasicInfoUpdateRequest.Body.Listofcontact();
        head.setTranscode(CrmTransCode.ACTIVE_MEMBER.getCode());
        head.setSystype(CrmConstant.SYSTEM_TYPE);
        head.setReqtime(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
        req.setHead(head);

//        body.setMembid(member.getMemberCode());
		body.setMembid(member.getMemberID()); // new
		body.setCellflg("N");
		String md5Pwd = MD5Utils.generatePassword(memberRepository.getRandomPassword());
		String pwd = StringUtils.isEmpty(member.getPassword()) ? md5Pwd:member.getPassword();
        body.setPasswd(pwd);
        body.setBindflg("Activiated");
        body.setActiveChannel(member.getActiveChannel());
        body.setActiveSubChannel(member.getActiveTag());
        //新增合作类型
        body.setThirdpartyType(member.getThirdpartyType());
        contact.setEmail(member.getEmail());
        contact.setCell(member.getCellPhone());
        contact.setTitle(member.getTitle());
        listofcontact.getContact().add(contact);
        body.setListofcontact(listofcontact);
        req.setBody(body);
        return req;
    }

    public RegistResponse quickRegist(Member member) throws Exception {
        RegistResponse result = new RegistResponse();
        MemberQuickRegistRes res = getMemberQuickRegistReq(member).send();
        RegistResponseStatus status = RegistResponseStatus
                .getRegistResponseStatus(res.getBody().getRemsg());
        if (res.isStatus(CrmResponse.Status.ERROR))
            throw new Exception("Call crm happen exception ");
        if (res.isStatus(Status.SUCCESS)){
            status = RegistResponseStatus.OK;
            result.setMcMemberCode(memberRepository.getMcMemberCodeByMemberNum(res.getBody().getMembid()));
        }
        result.setStatus(status);
        return result;
    }

    private MemberQuickRegistReq getMemberQuickRegistReq(Member member) {
        MemberQuickRegistReq req = new MemberQuickRegistReq();
        req.getBody().setRecord(getMemberQuickRegisterRecord(member));
        return req;
    }

    private Record getMemberQuickRegisterRecord(Member member) {
        Record record = new Record();
        record.setName(member.getFullName());
        record.setPasswd(member.getPassword());
        record.setRegichnltag(member.getRegisterTag());
        if(StringUtils.isNotBlank(member.getRegisterSource()))  record.setRegichnl(RegistChannel.valueOf(member.getRegisterSource()).name());

        record.setListofcontact(geQuickRegistertListofcontact(member));
        return record;
    }

    private MemberQuickRegistReq.Listofcontact geQuickRegistertListofcontact(
            Member member) {
        MemberQuickRegistReq.Listofcontact listofcontact = new MemberQuickRegistReq.Listofcontact();
        MemberQuickRegistReq.Contact contact = new MemberQuickRegistReq.Contact();
        contact.setMobile(member.getCellPhone());
        contact.setEmail(member.getEmail());
        contact.setCardid(member.getIdentityNo());
        contact.setCardtype(member.getIdentityType());
        listofcontact.getContact().add(contact);
        return listofcontact;
    }

    @Transactional
    public CardBindStatusDto updateMemberBaseBind(PartnerCardBindDto dto)
            throws Exception {
        CardBindStatusDto statusDto = new CardBindStatusDto();
        statusDto.setStatus(CardBindStatus.FAIL);
        if(!memberRepository.partnerCardBind(dto)){
            return statusDto;
        }
        if (MemberScoreType.MILEAGE.equals(dto.getPartnerFlag()) && !isExistAirAndCardNo(dto)) {
            storeMemberCard(dto);
        }
        updateMemberScoreType(dto);
        statusDto.setStatus(CardBindStatus.SUCCESS);
        return statusDto;

    }

    public boolean isExistAirAndCardNo(PartnerCardBindDto dto) {
        return memberRepository.isExistAirAndCardNo(dto.getPartnerCardNo(), dto
                .getPartnerCode().name());
    }

    private void updateMemberScoreType(PartnerCardBindDto dto) {
        MemberScoreLevelInfoDto scoreLevelInfoDto = new MemberScoreLevelInfoDto();
        scoreLevelInfoDto.setMemberInfoId(memberService.getMemberInfoIdByMcMemberCode(dto.getMcMemberCode()));
        scoreLevelInfoDto.setScoreType(dto.getMemberScoreType());
        memberScoreLevelInfoRepository.updateMemberScoreType(scoreLevelInfoDto);
    }

    private void storeMemberCard(PartnerCardBindDto dto) {
        MemberMemCard memberCard = new MemberMemCard();
        memberCard.setxCardNum(dto.getPartnerCardNo());
        memberCard.setCardTypeCd(dto.getPartnerCode().name());
        memberCard.setMemId(memberService.getMemberIDByMcMemberCode(dto.getMcMemberCode()));
        memberCard.setMemInfoId(memberService.getMemberInfoIdByMcMemberCode(dto.getMcMemberCode()));
        memberRepository.storeMemberCard(memberCard);
    }

    public RegistResponse regist(Member member) throws Exception {
        RegistResponse result = new RegistResponse();
        MemberRegistRes res = getMemberRegistReq(member).send();
        RegistResponseStatus status = RegistResponseStatus.getRegistResponseStatus(res.getBody().getRemsg());
        if (res.isStatus(CrmResponse.Status.ERROR))
            throw new Exception("Call crm happen exception ");
        if (res.isStatus(Status.SUCCESS)){
            status = RegistResponseStatus.OK;
            result.setMcMemberCode(memberRepository.getMcMemberCodeByMemberNum(res.getBody().getMembid()));
        }
        result.setStatus(status);
        return result;
    }

    public RegistResponse batchRegist(Member member) throws Exception {
        RegistResponse result = new RegistResponse();
        MemberRegistRes res = getMemberBatchRegistReq(member).send();
        RegistResponseStatus status = RegistResponseStatus.getRegistResponseStatus(res.getBody().getRemsg());
        if (res.isStatus(CrmResponse.Status.ERROR))
            throw new Exception("Call crm happen exception ");
        if (res.isStatus(Status.SUCCESS)){
            status = RegistResponseStatus.OK;
            result.setMcMemberCode(memberRepository.getMcMemberCodeByMemberNum(res.getBody().getMembid()));
        }
        result.setStatus(status);
        return result;
    }

    private MemberBatchRegistReq getMemberBatchRegistReq(Member member) {
        MemberBatchRegistReq req = new MemberBatchRegistReq();
        MemberBatchRegistReq.Record record = new MemberBatchRegistReq.Record();
        record.setName(member.getFullName());
        String md5Pwd = MD5Utils.generatePassword(memberRepository.getRandomPassword());
		String pwd = StringUtils.isEmpty(member.getPassword()) ? md5Pwd:member.getPassword();
        record.setPasswd(pwd);
        record.setPwdquestion(member.getRemindQuestion());
        record.setPwdanswer(member.getRemindAnswer());
        record.setJjcardtype(MemberCardType.JJCARD.getCode());
        record.setTier(MemberDegree.CLASSIC.getCode());
        record.setRegichnl(member.getRegisterSource());
        record.setRegichnltag(member.getRegisterTag());
        record.setActiveSubChannel(member.getActiveTag());
        //新增合作类型
        record.setThirdpartyType(member.getThirdpartyType());
        MemberBatchRegistReq.Listofcontact listofcontact = new MemberBatchRegistReq.Listofcontact();
        MemberBatchRegistReq.Contact contact = new MemberBatchRegistReq.Contact();
        contact.setMobile(member.getCellPhone());
        contact.setEmail(member.getEmail());
        contact.setCardid(member.getIdentityNo());
        contact.setCardtype(member.getIdentityType());
        listofcontact.getContact().add(contact);
        record.setListofcontact(listofcontact);
        req.getBody().setRecord(record);
        return req;
    }


    private MemberRegistReq getMemberRegistReq(Member member){
        MemberRegistReq req = new MemberRegistReq();
        MemberRegistReq.Record record = new MemberRegistReq.Record();

        record.setName(member.getFullName());
        record.setPasswd(member.getPassword());
        record.setPwdquestion(member.getRemindQuestion());
        record.setPwdanswer(member.getRemindAnswer());
        record.setJjcardtype(MemberCardType.JJCARD.getCode());
        record.setTier(MemberDegree.CLASSIC.getCode());
        record.setRegichnl(member.getRegisterSource());
        record.setRegichnltag(member.getRegisterTag());
        record.setActiveSubChannel(member.getActiveTag());
        //新增合作类型
        record.setThirdpartyType(member.getThirdpartyType());
        MemberRegistReq.Listofcontact listofcontact = new MemberRegistReq.Listofcontact();
        MemberRegistReq.Contact contact = new MemberRegistReq.Contact();
        contact.setMobile(member.getCellPhone());
        contact.setEmail(member.getEmail());
        contact.setCardid(member.getIdentityNo());
        contact.setCardtype(member.getIdentityType());
        listofcontact.getContact().add(contact);

        record.setListofcontact(listofcontact);

        req.getBody().setRecord(record);
        return req;
    }

    public TierUpdateRes updateTier(String membid,String tier,String thirdpartyType, String openId, String thirdpartyLevel) throws Exception {
        TierUpdateReq req = new TierUpdateReq();
        req.getBody().setMembid(membid);
        req.getBody().setTier(tier);
        req.getBody().setThirdpartyType(thirdpartyType);
        req.getBody().setOpenId(openId);
        req.getBody().setThirdpartyLevel(thirdpartyLevel);
        TierUpdateRes res = req.send();
        return res;
    }

}
