package com.jje.membercenter.domain;

import java.util.List;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.membercenter.AddressDto;
import com.jje.dto.membercenter.CardDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberJREZQueryDto;
import com.jje.dto.membercenter.MemberPrivilegeDto;
import com.jje.dto.membercenter.TravellPreferenceDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMRespDto;
import com.jje.dto.vbp.callcenter.MemberCallcenterConditionDto;

public interface MemberRepository {

	Member queryByUsernameOrCellphoneOrEmail(String usernameOrCellphoneOrEmail);

	Member queryMemberByLoginName1(String cellphoneOrEmail);
	
	Member queryMemberByLoginName2(String cellphoneOrEmail);
	
	List<Member> queryByCellphoneOrEmail(String cellphoneOrEmail);

	List<Member> keyInfoCheck(String keyInfo, String keyValue);
	
	List<Member> queryByMemberID(String memberID);

	MemberPrivilegeDto getPrivCardInfo(QueryMemberDto<String> queryDto);

	void updateTravellPreference(TravellPreferenceDto travellPreferenceDto);

	TravellPreferenceDto getTravellPreference(String memberId);

	CardDto queryCardStauts(String memberId, String cardNo);

	// zz start
	List<BaseData> listMemberType();

	List<BaseData> listCertificateTypes();

	List<BaseData> listRemindQuestionTypes();

	List<BaseData> listAddressTypes();

	List<BaseData> listInvoiceTypes();

	List<BaseData> listTitleTypes();

	List<BaseData> listEduTypes();

	List<BaseData> listProvinceTypes();

	List<BaseData> listCityTypes(String name);

	List<BaseData> listTownTypes(String name);

	List<BaseData> getPrice(String memberType);

	AddressDto getAddressValues(AddressDto nameDto);

	// zz end
	void storeMember(Member member);

	void deleteMemberByMemberId(String memberId);

	MemberForCRMRespDto upgradeMemberInfo(MemberDto memberDto);
	
	void updateMember(Member member);
	
	void resetMcMemberCode(String mcMemberCode);

	List<Member> getMemberCardNo(String memberId);

	void updatePwd(Member member);

	Member queryByCellphone(String cellphone);

	Member queryByEmail(String email);

	List<Member> queryByCode(String memberId);

	Member validatePhone(String phone);

	Member validateMail(String mail);

	List<BaseData> listHierarchyTypes();

	List<MemberVerfy> getRepeatMenName(Member member);

	void updateQuestion(Member member);

	List<MemberVerfy> queryRegisterByCellphoneOrEmailOrCardNo(String menName);

	List<Member> queryIdentifyInfo(Member member);

	List<Member> checkIdentifyInfo(Member member);
	
	Member getMemberByIdentify(String identityType, String identityNo);	

	void storeMemberInfo(Member member);

	long getSequence(Member member);

	void storeMemberCard(MemberMemCard memberMemCard);

	void storeMemberVerify(MemberVerfy memberVerfy);

	void updateMemberInfoAndVerify(Member member);

	void updateMemberInfo(Member member);

	void updateMemberCard(MemberMemCard memberMemCard);

	List<MemberMemCard> queryMemberMemCardByMemberId(String memberID);

	void updateMemberMemCardTwoCondition(MemberMemCard memberMemCard);

	List<MemberMemCard> queryMemberMemCardThreeCondition(MemberMemCard memberMemCard);

	void updateMemberMemCardById(MemberMemCard memberMemCard);

	List<MemberMemCard> queryMemberMemCardThreeConditionTwo(MemberMemCard memberMemCard);

	void storeAllMemberInfo(MemberDto memberDto, Member member) throws Exception;

	void storeMemberCardAndEmailAndPhoneVerify(MemberDto memberDtostoreAllMemberVerify,String... airLineCardVerifyInfo) throws Exception;

	void updateMemberInfoAndStoreVerify(Member member, MemberDto memberDto) throws Exception;

	void updateMemberInfoAndCard(MemberDto memberDto) throws Exception;

	AppMember getAppMemberByMcMemberCode(String cardNo, Boolean flag);

	boolean isExistMember(String email, String phone);

	boolean isExistMemberOfEmail(String email);

	boolean isExistMemberOfPhone(String phone);

	boolean isExistAirAndCardNo(String cardNo, String partnerCode);

	boolean partnerCardBind(PartnerCardBindDto dto) throws InterruptedException;

	Member queryByMemNum(String memNum);

	List<Member> queryMember(MemberJREZQueryDto queryDto);

	List<Member> queryMemberByCardNo(MemberJREZQueryDto queryDto);

	Member querySingleMemberByCardNo(String cardNo);
	
	String generateMcMemberCode();

	Member getMemberByMcMemberCode(String code);

  Member getMemberByMemberID(String memberID);

	Member getMemberByMemberNum(String memberNum);
	
	void updatePwdByMcMemberCode(String mcMemberCode, String password);
	
	String getMcMemberCodeByPhone(String phone);

	String getMcMemberCodeByMemberNum(String memberNum);
	
	String getMcMemberCodeByMemberId(String memberId);

    Member getMemberByCardNo(String cardNo);
    
    void deleteMemberVerifyByMemberCode(String memberCode);
    
    List<MemberVerfy> getMemberVerfyByMemberCode(String memberCode);

    
    void updateMemIpAddressByMC(String ipAddress,String mcMemberCode);

    
    String getRandomPassword();

	Boolean identityIsExists(Member member);
	
	void saveJJINNCardVerify(MemberDto memberDto) ;
	
	void updateVerify(Member member);
	
	MemberMemCard getCardInfoByCardno(String cardNo);

	List<Member> queryMember(MemberCallcenterConditionDto queryMemberDto);

	Member getMemberById(Long id);
	
	public List<MemberMemCard> queryMemberCardInfos(Long id);
	
	String addValidateCode(ValidateCode code); 
	
	boolean checkValidateCode(ValidateCode code);

	boolean updateVerifyStatus(String userName, String pwd);
	
}
