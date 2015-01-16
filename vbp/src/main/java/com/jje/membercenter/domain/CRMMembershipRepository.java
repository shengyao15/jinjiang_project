package com.jje.membercenter.domain;

import java.util.List;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.membercenter.AccountDto;
import com.jje.dto.membercenter.CRMActivationRespDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.CardDto;
import com.jje.dto.membercenter.ContactQqAndMsnDto;
import com.jje.dto.membercenter.MemberAddressDto;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.MemberCommunicationDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberHobbyDto;
import com.jje.dto.membercenter.MemberPrivilegeDto;
import com.jje.dto.membercenter.MemberQuickRegisterDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.dto.membercenter.SSOMemberDto;
import com.jje.dto.membercenter.SSORedeemDto;
import com.jje.dto.membercenter.TravellPreferenceDto;
import com.jje.dto.vbp.sns.MemberContactsnsDto;
import com.jje.membercenter.xsd.MemberInfoUpdateResponse;

public interface CRMMembershipRepository
{

	MemberPrivilegeDto getPrivCardInfo(QueryMemberDto<String> queryDto) throws Exception;

	TravellPreferenceDto getTravellPreference(String memberId) throws Exception;

	CRMResponseDto updateTravellPreference(TravellPreferenceDto travellPreferenceDto) throws Exception;

	CardDto queryCardStauts(String memberId) throws Exception;

	SSOMemberDto getMember(String memberId) throws Exception;

	String reduceScore(SSORedeemDto ssoRedeemDto) throws Exception;

	CRMResponseDto addVIPMembership(MemberRegisterDto dto) throws Exception;

	CRMResponseDto quickRegister(MemberQuickRegisterDto memberQuickRegisterDto) throws Exception;

	CRMResponseDto updateVIPCardInfo(MemberUpdateDto memberUpdateDto) throws Exception;

	CRMResponseDto updateCardInfo(MemberUpdateDto memberUpdateDto) throws Exception;

	CRMResponseDto updateMemberAddress() throws Exception;

	MemberHobbyDto queryMemberHobby(String memberId);

	String updateHobbies(MemberHobbyDto memberHobbyDto);
	MemberDto getMemberDto(String memberId) throws Exception;

	CRMResponseDto bindPhone(MemberDto memberDto) throws Exception;
	
	MemberBasicInfoDto queryMemberBaseInfo(String memberId);
	
   String updateMemberBaseInfo(MemberBasicInfoDto baseInfo);
   
   List<MemberAddressDto> queryMemberAddress(String memberId);
   
   String updateMemberAddress(List<MemberAddressDto> addressDtos);
   
   MemberCommunicationDto queryMemberCommunication(String memberId);
   
   String updateMemberCommunication(MemberCommunicationDto comDto);
	
	CRMActivationRespDto validateActivationMember(AccountDto accountDto) throws Exception;
	
	CRMResponseDto activateMember(AccountDto accountDto) throws Exception;
	
	String deleteMemberAddress(MemberAddressDto addressDto) ;
	   
	String saveMemberAddress(List<MemberAddressDto> addressDtos);
	
    String firstMemberAddress(List<MemberAddressDto> addressDtos);
    
    CRMResponseDto modifyPwd(MemberDto memberDto) throws Exception ;
    
    CRMResponseDto insertContactInfo(ContactQqAndMsnDto qqMsnDto);
    
    CRMResponseDto bindEmail(MemberDto memberDto) throws Exception;
    
    CRMResponseDto bindSns(MemberContactsnsDto memberContactsnsDto) throws Exception;
    
    //add by bht for member move
    String updateMemberMoveBaseInfo(MemberBasicInfoDto baseInfo);
    
    String activeMember(MemberBasicInfoDto baseInfo);
    
    MemberInfoUpdateResponse activeMemberV2(MemberBasicInfoDto baseInfo);

    CRMResponseDto queryByCrsId(String crsId, String phone) throws Exception;
}
