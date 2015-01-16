package com.jje.membercenter.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jje.dto.membercenter.MemberJREZQueryDto;
import com.jje.dto.vbp.callcenter.MemberCallcenterConditionDto;
import com.jje.membercenter.domain.AppMember;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberVerfy;

public interface MemberMapper {

	Member queryByUsernameOrCellphoneOrEmail(String usernameOrCellphoneOrEmail);

	List<Member> queryByCellphoneOrEmail(String cellphoneOrEmail);

	List<Member> checkByCardNo(String keyValue);
	
	List<Member> checkByID(String keyValue);
	
	List<Member> checkByPhone(String keyValue);
	
	List<Member> checkByEmail(String keyValue);
	
	List<Member> queryByMemberID(String memberID);

	void insert(Member member);

	void deleteMemberByMemberId(String memberId);

	void update(Member member);

	void resetMcMemberCode(String mcMemberCode);

	void updateMemberUserNameByEmail(Member member);

	void updateMemberUserNameByCellPhone(Member member);

	void updateMemberUserNameByCardNo(Member member);

	List<Member> getMemberCardNo(String memberId);

	void updatePwd(Member member);

	Member queryByCellphone(String cellPhone);

	Member queryByEmail(String email);

	List<Member> queryByCode(String memberCode);

	Member validatePhone(String cellPhone);

	Member validateMail(String mail);

	List<MemberVerfy> queryRepeatMenName(Member member);

	void updateQuestion(Member member);

	List<MemberVerfy> queryRegisterByCellphoneOrEmailOrCardNo(String menName);
	
	List<MemberVerfy> queryRegisterByCellphoneOrEmailOrCardNo2(String menName);

	List<Member> queryIdentifyInfo(Member member);

	void insertMemberInfo(Member member);

	long getSequence(Member member);

	void insertMemberCard(MemberMemCard memberMemCard);

	void insertMemberVerify(MemberVerfy memberVerfy);

	void insertMemberVerifyJJZX(MemberVerfy memberVerfy);
	
	void insertMemberVerifyJJZXConflict(MemberVerfy memberVerfy);
	
	int updateMemberVerifyMemNameByEmail(Member member);

	int updateMemberVerifyMemNameByCellPhone(Member member);

	int updateMemberVerifyMemNameByCardNo(Member member);
	
	int updateMemberVerifyMemNameByLoginName(Long infoID);

	void updateMemberInfo(Member member);

	void updateMemberCard(MemberMemCard memberMemCard);

	List<MemberMemCard> queryMemberMemCardByMemberId(String memberID);

	void updateMemberMemCardTwoCondition(MemberMemCard memberMemCard);

	List<MemberMemCard> queryMemberMemCardThreeCondition(MemberMemCard memberMemCard);

	void updateMemberMemCardById(MemberMemCard memberMemCard);

	List<MemberMemCard> queryMemberMemCardThreeConditionTwo(MemberMemCard memberMemCard);

	AppMember getFullMemberByMcMemberCode(String mcMemberCode);

	Long sumMember(Map<String, String> map);

	int isExistAirAndCardNo(Map<String, String> map);

	List<Member> queryMember(MemberJREZQueryDto condition);

	List<Member> queryMemberByCardNo(MemberJREZQueryDto queryDto);

	Member querySingleMemberByCardNo(String cardNo);

	List<Member> checkIdentifyInfo(Member member);
	
	Member getMemberByIdentify(@Param("identityType") String identityType, @Param("identityNo") String identityNo);	

	String generateMcMemberCode();

	Member getMemberByMcMemberCode(String code);

	Member getMemberByMemberID(String memberID);

	Member getMemberByMemberNum(String memberNum);

	void updatePwdByMemInfoId(@Param("memInfoId") Long memInfoId, @Param("password") String password);

	List<String> getMcMemberCodeByPhone(String phone);

	String getMcMemberCodeByMemberNum(String memberNum);
	
	String getMcMemberCodeByMemberId(String memberId);

	Member getMemberByCardNo(String cardNo);

	void deleteMemberVerifyByMemberCode(String memberCode);

	List<MemberVerfy> getMemberVerfyByMemberCode(String memberCode);

	void updateMemIpAddressByMC(@Param("ipAddress") String ipAddress, @Param("mcMemberCode") String mcMemberCode);

    Integer countWithIdentity(@Param("identityType") String identityType, @Param("identityNo") String identityNo);
    
    MemberMemCard getCardInfoByCardno(String cardNo);

	List<Member> queryMemberForAdmin(MemberCallcenterConditionDto condition);

	Member getMemberById(Long id);
	
	List<MemberMemCard> queryMemberCardInfos(Long id);

	Member queryMemberByLoginName2(String usernameOrCellphoneOrEmail);

	List<Member> queryMemberByLoginName3(String usernameOrCellphoneOrEmail);
	
	Member queryMemberByLoginName1(String usernameOrCellphoneOrEmail);
	
	List<MemberVerfy> queryVerifyInfoByInfoId(Long infoID);
	List<MemberVerfy> queryVerifyInfoByInfoId2(Long infoID);
}
