package com.jje.membercenter.crm.impl;

import org.springframework.stereotype.Component;

import com.jje.membercenter.account.xsd.AccountActivationRequest;
import com.jje.membercenter.account.xsd.AccountActivationResponse;
import com.jje.membercenter.account.xsd.AccountValidationRequest;
import com.jje.membercenter.account.xsd.AccountValidationResponse;
import com.jje.membercenter.crm.CRMMembershipProxy;
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

@Component
public class CRMMembershipProxyImpl extends CRMBaseProxy implements
		CRMMembershipProxy {

	public MemberPrivilegeResponse getPrivCardInfo(
			MemberPrivilegeRequest request) throws Exception {
		return this.getResponse(request, MemberPrivilegeResponse.class);
	}

	public QueryTravellPreferenceResponse getTravellPreference(
			QueryTravellPreferenceRequest request) throws Exception {
		return this.getResponse(request, QueryTravellPreferenceResponse.class);
	}

	public UpdateTravellPreferenceResponse updateTravellPreference(
			UpdateTravellPreferenceRequest request) throws Exception {
		return this.getResponse(request, UpdateTravellPreferenceResponse.class);
	}

	public MemberPrivilegeHistoryResponse getPrivCardHistory(
			MemberPrivilegeHistoryRequest request) throws Exception {
		return this.getResponse(request, MemberPrivilegeHistoryResponse.class);
	}

	public MemberResponse queryCardStauts(MemberRequest request)
			throws Exception {
		return this.getResponse(request, MemberResponse.class);
	}

	public MemberRegisterResponse addVIPMembership(MemberRegisterRequest request)
			throws Exception {
		return this.getResponse(request, MemberRegisterResponse.class);
	}
	
	public MemberRegisterResponse addQuickMembership(MemberQuickRegisterRequest request)
			throws Exception {
		return this.getResponse(request, MemberRegisterResponse.class);
	}

	public SynRightCardResponse updateVIPCardInfo(SynRightCardRequest request)
			throws Exception {
		return this.getResponse(request, SynRightCardResponse.class);
	}

	public MemberResponse getMember(MemberRequest request) throws Exception {
		return this.getResponse(request, MemberResponse.class);
	}

	public RedeemResponse reduceScore(RedeemRequest request) throws Exception {
		return this.getResponse(request, RedeemResponse.class);
	}

	public QuickRegisterResponse quickRegister(QuickRegisterRequest request)
			throws Exception {
		return this.getResponse(request, QuickRegisterResponse.class);
	}

	public SynRightCardResponse updateCardInfo(SynRightCardRequest request)
			throws Exception {
		return this.getResponse(request, SynRightCardResponse.class);
	}

	public QueryContactResponse queryContact(QueryContactRequest request)
			throws Exception {
		return this.getResponse(request, QueryContactResponse.class);
	}

	public UpdateContactResponse updateContact(UpdateContactRequest request)
			throws Exception {
		return this.getResponse(request, UpdateContactResponse.class);
	}

	public UpdateMemberAddressResponse updateMemberAddress(
			UpdateMemberAddressRequest request) throws Exception {
		return this.getResponse(request, UpdateMemberAddressResponse.class);
	}

	public MemberHobbyResponse queryMemberHobby(MemberHobbyRequest request)
			throws Exception {
		return this.getResponse(request, MemberHobbyResponse.class);
	}

	public MemberHobbyUpdateResponse updateHobbies(
			MemberHobbyUpdateRequest request) throws Exception {
		return this.getResponse(request, MemberHobbyUpdateResponse.class);
	}

	public MemberAddressResponse queryMemberAddress(MemberAddressRequest request)
			throws Exception {
		return this.getResponse(request, MemberAddressResponse.class);
	}

	public MemberCommunicationResponse queryMemberCommuniction(
			MemberCommunicationRequest request) throws Exception {
		return this.getResponse(request, MemberCommunicationResponse.class);
	}

	public AccountValidationResponse validateActivationMember(
			AccountValidationRequest request) throws Exception {
		return this.getResponse(request, AccountValidationResponse.class);
	}

	public AccountActivationResponse activateMember(
			AccountActivationRequest request) throws Exception {
		return this.getResponse(request, AccountActivationResponse.class);
	}

	public MemberBasicInfoUpdateResponse updateMemberBasicInfo(
			MemberBasicInfoUpdateRequest request) throws Exception {
		// TODO Auto-generated method stub
		return this.getResponse(request, MemberBasicInfoUpdateResponse.class);
	}
	
	public MemberQuickBasicInfoUpdateResponse updateMemberBasicInfo(MemberQuickBasicInfoUpdateRequest request) throws Exception {
		// TODO Auto-generated method stub
		return this.getResponse(request, MemberQuickBasicInfoUpdateResponse.class);
	}

	public MemberInfoUpdateResponse updateMemberInfo(
			MemberBasicInfoUpdateRequest request) throws Exception {
		// TODO Auto-generated method stub
		return this.getResponse(request, MemberInfoUpdateResponse.class);
	}

	public QueryRightCardInfoResponse queryVIPCardInfoTest(
			QueryRightCardInfoRequest request) throws Exception {
		return this.getResponse(request, QueryRightCardInfoResponse.class);
	}

	public MemberBindResponse bindPhone(
			MemberBindRequest request) throws Exception {
		return this.getResponse(request, MemberBindResponse.class);
	}

	public MemberAddressDeleteResponse deleteMemberAddress(
			MemberAddressDeleteRequest request) throws Exception {
		return this.getResponse(request, MemberAddressDeleteResponse.class);
	}

	public ModifyPassWordResponse modifyPwd(ModifyPassWordRequest request)
			throws Exception {
		return this.getResponse(request, ModifyPassWordResponse.class);
	}

	public ThirdPartyLoginResponse queryByCrsId(ThirdPartyLoginRequest request)
			throws Exception {
		return this.getResponse(request, ThirdPartyLoginResponse.class);
	}
	
	public MemberSnsBindResponse bindSns(MemberSnsBindRequest request) throws Exception{
		return this.getResponse(request, MemberSnsBindResponse.class);
	}

}
