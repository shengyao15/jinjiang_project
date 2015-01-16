package com.jje.membercenter.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jje.dto.Pagination;
import com.jje.dto.membercenter.TransformType;
import com.jje.dto.vbp.callcenter.MemberCallcenterConditionDto;
import com.jje.membercenter.domain.AppMember;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.WebMember;

public interface WebMemberMapper {

	MemberVerfy queryRegisterByCellphoneOrEmail(String name);

	WebMember queryMemberInfo(MemberVerfy member);

	void insertMemberVerfy(MemberVerfy member);

	void insertMemberInfo(WebMember member);

	WebMember getWebMember(Long id);

	String getLastInsertedCardNo();

	void updateWebMemberInfo(@Param("memberCode") String memberCode, @Param("id") long id, @Param("memType") String memType);

	// ComplexMemberInfoDto queryComplexMemberInfo(Map<String,String> param);

	void updatePwd(MemberVerfy member);

	void updateWebMemberQuestion(@Param("id") Long id, @Param("question") String question, @Param("answer") String answer);

	WebMember getMemberByMcMemberCode(String code);

	AppMember getProMemberByMcMemberCode(String mcMemberCode);

	WebMember getWebMemberByPhoneAndEmail(@Param("phone") String phone, @Param("email") String email);

    WebMember getWebMemberByCardNo(String cardNo);
    
    void deleteWebMemberVerify(Long id);

	WebMember getNonNormalWebMemberByPhoneAndEmail(@Param("phone") String phone, @Param("email")String email);

	void updateTransformType(@Param("mc") String mc, @Param("transformType") TransformType transformType);
	
	List<Long> getWebMemberIdList(Pagination pagination);
	
	Integer getWebMemberCount();

    WebMember getWebMemberInfoById(Long id);
	List<WebMember> queryMember(MemberCallcenterConditionDto callcenterCondition);

}
