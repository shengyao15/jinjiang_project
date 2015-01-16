package com.jje.vbp.validate.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.membercenter.WebMemberResource;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;

@Component
public class ValidateService {
	private final Logger logger = LoggerFactory.getLogger(WebMemberResource.class);
	@Autowired
	MemberRepository memberRepository;
	
	public boolean isUsedByEmailOrPhone(String email,String phone, WebMemberRegisterReturnDto returnDto) {
		if (this.isExistMemberOfEmail(email)) {
			returnDto.emailExisted();
			logger.warn("该邮箱已被使用, email=" + email);
			return true;
		}
		if (this.isExistMemberOfPhone(phone)) {
			returnDto.phoneExisted();
			logger.warn("该手机号码已被使用, phone=" + phone);
			return true;
		}
		return false;
	}
	
	private boolean isExistMemberOfEmail(String email){
		return StringUtils.isNotBlank(email) && memberRepository.isExistMemberOfEmail(email);
	}
	
	private boolean isExistMemberOfPhone(String phone){
		return StringUtils.isNotBlank(phone) && memberRepository.isExistMemberOfEmail(phone);
	}
	
	public boolean isExistCertificateNo(String identityType,String identityNo){
		Member member = new Member();
		member.setIdentityType(identityType);
		member.setIdentityNo(identityNo);
		return memberRepository.identityIsExists(member);
	}
	
}
