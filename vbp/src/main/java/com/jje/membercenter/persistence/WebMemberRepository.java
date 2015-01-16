package com.jje.membercenter.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.Pagination;
import com.jje.dto.membercenter.TransformType;
import com.jje.dto.vbp.callcenter.MemberCallcenterConditionDto;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.WebMember;

@Repository
public class WebMemberRepository {

	@Autowired
	private WebMemberMapper webMemberMapper;

	public void insertMemberInfo(WebMember member) {
		webMemberMapper.insertMemberInfo(member);
	}
	
	public String getLastInsertedCardNo(){
		return webMemberMapper.getLastInsertedCardNo();
	}
	
	public void insertMemberVerfy(MemberVerfy verfy){
		webMemberMapper.insertMemberVerfy(verfy);
	}

	public MemberVerfy queryRegisterByCellphoneOrEmail(String name) {
		return webMemberMapper.queryRegisterByCellphoneOrEmail(name);
	}

	public WebMember queryMemberInfo(String usernameOrCellphoneOrEmail, String password) {
		MemberVerfy verfy = new MemberVerfy();
		verfy.setMenName(usernameOrCellphoneOrEmail);
		verfy.setPassword(password);
		return webMemberMapper.queryMemberInfo(verfy);
	}

	public void updateWebMemberInfo(String memCode, long id, String memType) {
		webMemberMapper.updateWebMemberInfo(memCode, id, memType);
	}

	public void updatePwd(Member webMember) {
		MemberVerfy member = new MemberVerfy();
		member.setMemInfoId(webMember.getId());
		member.setPassword(webMember.getPassword());
		webMemberMapper.updatePwd(member);
	}

	public void updateWebMemberQuestion(Long id, String question, String answer) {
		webMemberMapper.updateWebMemberQuestion(id, question, answer);
	}

	public WebMember getWebMember(Long id) {
		return webMemberMapper.getWebMember(id);
	}

	public WebMember getMemberByMcMemberCode(String code) {
		return webMemberMapper.getMemberByMcMemberCode(code);
	}

	public WebMember getWebMemberByPhoneAndEmail(String phone, String email) {
		return webMemberMapper.getWebMemberByPhoneAndEmail(phone, email);
	}
	
	public WebMember getNonNormalWebMemberByPhoneAndEmail(String phone, String email) {
		return webMemberMapper.getNonNormalWebMemberByPhoneAndEmail(phone, email);
	}

	public WebMember getWebMemberByCardNo(String cardNo) {
		return webMemberMapper.getWebMemberByCardNo(cardNo);
	}
	
	public void deleteWebMemberVerify(Long id){
		webMemberMapper.deleteWebMemberVerify(id);
	}
	
	public void updateTransformTypeWhenNormalUpgrate(String mc) {
		webMemberMapper.updateTransformType(mc, TransformType.NORMAL);
	}
	
	public void updateTransformTypeWhenActivationUpgrate(String mc) {
		webMemberMapper.updateTransformType(mc, TransformType.ACTIVATION);
	}
	 public List<Long> getWebMemberIdList(Pagination pagination) {
        return webMemberMapper.getWebMemberIdList(pagination);
    }
	 
	 public int getWebMemberCount() {
		 return webMemberMapper.getWebMemberCount();
	 }
	
    public WebMember getWebMemberInfoById(Long id){
        return webMemberMapper.getWebMemberInfoById(id);
    }
	public List<WebMember> queryMember(MemberCallcenterConditionDto callcenterCondition) {
		return webMemberMapper.queryMember(callcenterCondition);
	}
	
}
