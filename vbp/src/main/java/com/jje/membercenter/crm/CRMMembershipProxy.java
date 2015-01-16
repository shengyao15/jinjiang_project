package com.jje.membercenter.crm;

import com.jje.membercenter.account.xsd.AccountActivationRequest;
import com.jje.membercenter.account.xsd.AccountActivationResponse;
import com.jje.membercenter.account.xsd.AccountValidationRequest;
import com.jje.membercenter.account.xsd.AccountValidationResponse;
import com.jje.membercenter.xsd.MemberAddressDeleteRequest;
import com.jje.membercenter.xsd.MemberAddressDeleteResponse;
import com.jje.membercenter.xsd.MemberAddressRequest;
import com.jje.membercenter.xsd.MemberAddressResponse;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateResponse;
import com.jje.membercenter.xsd.MemberBindRequest;
import com.jje.membercenter.xsd.MemberBindResponse;
import com.jje.membercenter.xsd.MemberCommunicationRequest;
import com.jje.membercenter.xsd.MemberCommunicationResponse;
import com.jje.membercenter.xsd.MemberHobbyRequest;
import com.jje.membercenter.xsd.MemberHobbyResponse;
import com.jje.membercenter.xsd.MemberHobbyUpdateRequest;
import com.jje.membercenter.xsd.MemberHobbyUpdateResponse;
import com.jje.membercenter.xsd.MemberInfoUpdateResponse;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryRequest;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryResponse;
import com.jje.membercenter.xsd.MemberPrivilegeRequest;
import com.jje.membercenter.xsd.MemberPrivilegeResponse;
import com.jje.membercenter.xsd.MemberQuickBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberQuickBasicInfoUpdateResponse;
import com.jje.membercenter.xsd.MemberQuickRegisterRequest;
import com.jje.membercenter.xsd.MemberRegisterRequest;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.membercenter.xsd.MemberRequest;
import com.jje.membercenter.xsd.MemberResponse;
import com.jje.membercenter.xsd.MemberSnsBindRequest;
import com.jje.membercenter.xsd.MemberSnsBindResponse;
import com.jje.membercenter.xsd.ModifyPassWordRequest;
import com.jje.membercenter.xsd.ModifyPassWordResponse;
import com.jje.membercenter.xsd.QueryContactRequest;
import com.jje.membercenter.xsd.QueryContactResponse;
import com.jje.membercenter.xsd.QueryRightCardInfoRequest;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse;
import com.jje.membercenter.xsd.QueryTravellPreferenceRequest;
import com.jje.membercenter.xsd.QueryTravellPreferenceResponse;
import com.jje.membercenter.xsd.QuickRegisterRequest;
import com.jje.membercenter.xsd.QuickRegisterResponse;
import com.jje.membercenter.xsd.RedeemRequest;
import com.jje.membercenter.xsd.RedeemResponse;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;
import com.jje.membercenter.xsd.ThirdPartyLoginRequest;
import com.jje.membercenter.xsd.ThirdPartyLoginResponse;
import com.jje.membercenter.xsd.UpdateContactRequest;
import com.jje.membercenter.xsd.UpdateContactResponse;
import com.jje.membercenter.xsd.UpdateMemberAddressRequest;
import com.jje.membercenter.xsd.UpdateMemberAddressResponse;
import com.jje.membercenter.xsd.UpdateTravellPreferenceRequest;
import com.jje.membercenter.xsd.UpdateTravellPreferenceResponse;

public interface CRMMembershipProxy
{
	MemberPrivilegeResponse getPrivCardInfo(MemberPrivilegeRequest request) throws Exception;

	MemberPrivilegeHistoryResponse getPrivCardHistory(MemberPrivilegeHistoryRequest request) throws Exception;

	QueryTravellPreferenceResponse getTravellPreference(QueryTravellPreferenceRequest request) throws Exception;

	UpdateTravellPreferenceResponse updateTravellPreference(UpdateTravellPreferenceRequest request) throws Exception;

	MemberResponse queryCardStauts(MemberRequest request) throws Exception;

	MemberResponse getMember(MemberRequest request) throws Exception;

	MemberRegisterResponse addVIPMembership(MemberRegisterRequest request) throws Exception;
	
	MemberRegisterResponse addQuickMembership(MemberQuickRegisterRequest request) throws Exception;

	QuickRegisterResponse quickRegister(QuickRegisterRequest request) throws Exception;

	SynRightCardResponse updateVIPCardInfo(SynRightCardRequest request) throws Exception;

	SynRightCardResponse updateCardInfo(SynRightCardRequest request) throws Exception;

	RedeemResponse reduceScore(RedeemRequest request) throws Exception;

	QueryContactResponse queryContact(QueryContactRequest request) throws Exception;

	UpdateContactResponse updateContact(UpdateContactRequest request) throws Exception;

	UpdateMemberAddressResponse updateMemberAddress(UpdateMemberAddressRequest request) throws Exception;

	MemberHobbyResponse queryMemberHobby(MemberHobbyRequest request) throws Exception;

	MemberHobbyUpdateResponse updateHobbies(MemberHobbyUpdateRequest request) throws Exception;

	MemberBasicInfoUpdateResponse updateMemberBasicInfo(MemberBasicInfoUpdateRequest request) throws Exception;
	
	MemberQuickBasicInfoUpdateResponse updateMemberBasicInfo(MemberQuickBasicInfoUpdateRequest request) throws Exception;
	
	MemberInfoUpdateResponse updateMemberInfo(MemberBasicInfoUpdateRequest request) throws Exception;

	MemberBindResponse bindPhone(MemberBindRequest request) throws Exception;

	MemberAddressResponse queryMemberAddress(MemberAddressRequest request) throws Exception;

	MemberCommunicationResponse queryMemberCommuniction(MemberCommunicationRequest request) throws Exception;

	AccountValidationResponse validateActivationMember(AccountValidationRequest request) throws Exception;
	
	AccountActivationResponse activateMember(AccountActivationRequest request) throws Exception;

	QueryRightCardInfoResponse queryVIPCardInfoTest(QueryRightCardInfoRequest request) throws Exception;
	
    MemberAddressDeleteResponse deleteMemberAddress(MemberAddressDeleteRequest request) throws Exception;

	ModifyPassWordResponse modifyPwd(ModifyPassWordRequest request) throws Exception;

	ThirdPartyLoginResponse queryByCrsId(ThirdPartyLoginRequest request)throws Exception;

	MemberSnsBindResponse bindSns(MemberSnsBindRequest request) throws Exception;
}
