package com.jje.membercenter.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.membercenter.CheckStatusResponse;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.RegistResponse;
import com.jje.dto.membercenter.RegistResponseStatus;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.remote.handler.MemberHandler;

@Component
public class WebMemberService {

	private char beginChar = 'J';

	private BigDecimal maxCardNum = BigDecimal.valueOf(99999999999L);
	@Autowired
	private WebMemberRepository webMemberRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberHandler memberHandler;

	private static final Logger logger = LoggerFactory.getLogger(WebMemberRepository.class);

	@Transactional(propagation = Propagation.REQUIRED)
	public String saveWebMember(WebMember member) throws Exception {
		member.setTempCardNo(generateCardNo());
		member.setMcMemberCode(memberRepository.generateMcMemberCode());
		try {
			webMemberRepository.insertMemberInfo(member);
		} catch (DuplicateKeyException e) {
			int retryCount = 0;
			logger.debug(" --------webMemberRegist  DuplicatedCardNo---------- ");
			getCardNoAndSaveWebMember(member, retryCount);
		}
		saveMemberVerfy(member);
		return member.getMcMemberCode();
	}

	private void getCardNoAndSaveWebMember(WebMember member, int retryCount) throws Exception {
		try {
			retryCount++;
			if (retryCount > 3)
				throw new Exception("--------webMemberRegist---------generate cardNo beyond 3 times----------------");
			member.setTempCardNo(generateCardNo());
			webMemberRepository.insertMemberInfo(member);
		} catch (DuplicateKeyException e) {
			logger.error(" -------getCardNoAndSaveWebMember  exception---------- ", e);
			getCardNoAndSaveWebMember(member, retryCount);
		}
	}

	private void saveMemberVerfy(WebMember member) {
		MemberVerfy verfy = new MemberVerfy();
		verfy.setMemInfoId(member.getId());
		verfy.setPassword(member.getPwd());
		saveEmailValidate(verfy, member);
		savePhoneValidate(verfy, member);
		saveCardValidate(verfy, member);

	}

	private String generateCardNo() throws Exception {
		String letter = beginChar + "";
		String cardNo = webMemberRepository.getLastInsertedCardNo();
		// 第一个从10000开始
		String cardNum = generate11CardNum(10000L);
		if (StringUtils.isNotBlank(cardNo)) {
			letter = getLetterFromCurrentCardNo(cardNo);
			cardNum = getCardNumFromCurrentCardNo(cardNo);
		}
		return letter + cardNum;
	}

	private String getLetterFromCurrentCardNo(String cardNo) throws Exception {
		String letter = cardNo.substring(0, 1);
		if (isNextLetter(cardNo))
			letter = theNextLetter(cardNo);
		return letter;
	}

	private String getCardNumFromCurrentCardNo(String cardNo) {
		String cardNum = generate11CardNum(getNextCardNum(cardNo).longValue());
		if (isNextLetter(cardNo))
			cardNum = generate11CardNum(1L);
		return cardNum;
	}

	private void saveEmailValidate(MemberVerfy verfy, WebMember member) {
		if (StringUtils.isNotEmpty(member.getEmail())) {
			verfy.setMenName(member.getEmail());
			webMemberRepository.insertMemberVerfy(verfy);
		}

	}

	private void savePhoneValidate(MemberVerfy verfy, WebMember member) {
		if (StringUtils.isNotEmpty(member.getPhone())) {
			verfy.setMenName(member.getPhone());
			webMemberRepository.insertMemberVerfy(verfy);
		}

	}

	private void saveCardValidate(MemberVerfy verfy, WebMember member) {
		if (StringUtils.isNotEmpty(member.getTempCardNo())) {
			verfy.setMenName(member.getTempCardNo());
			webMemberRepository.insertMemberVerfy(verfy);
		}

	}

	private String generate11CardNum(Long seId) {
		String cardNo = "";
		int len = seId.toString().length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 11 - len; i++) {
			sb.append("0");
		}
		cardNo = sb.toString() + seId;
		return cardNo;
	}

	private String theNextLetter(String letter) throws Exception {
		int nextle = (int) letter.charAt(0) + 1;
		if (nextle > 122)
			throw new Exception(" beyond  the max ascii of char");
		return (char) nextle + "";
	}

	private Boolean isNextLetter(String cardNo) {
		return maxCardNum.compareTo(getNextCardNum(cardNo)) > 0 ? false : true;
	}

	private BigDecimal getNextCardNum(String cardNo) {
		BigDecimal lastCardNum = BigDecimal.valueOf(Long.parseLong(cardNo.substring(1)));
		BigDecimal nextCardNum = lastCardNum.add(BigDecimal.valueOf(1));
		return nextCardNum;
	}

	public CheckStatusResponse checkIdentityExists(MemberDto dto) {
		Boolean hasExists = memberRepository.identityIsExists(new Member(dto));
		if (hasExists) {
			return CheckStatusResponse.ID_NUMBER_EXIST;
		}
		return CheckStatusResponse.ID_NUMBER_NOT_EXIST;
	}
	
	public RegistResponse toFullMember(Member member) throws Exception{
		RegistResponse result = null;
		List<Member> memberResultList = memberRepository.checkIdentifyInfo(member);
		if (memberResultList != null && !memberResultList.isEmpty()) {
			result = migrateWebMemberInfo(member, memberResultList);
		} else {
			result = memberHandler.regist(member);
            if(StringUtils.isNotEmpty(result.getMcMemberCode())){
                memberRepository.updateMemIpAddressByMC(member.getIpAddress(), result.getMcMemberCode());
            }
		}
		return result;
	}
	
	private RegistResponse migrateWebMemberInfo(Member member, List<Member> memberResultList) throws Exception {
		RegistResponse result = new RegistResponse();
		result.setStatus(RegistResponseStatus.MEMBER_ALREDY_REGIST);
		if (!isAcitiveStatus(memberResultList))
			result = memberHandler.updateMemberBaseInfo(getUpdateMember(member, memberResultList));
		return result;
	}
	
	private boolean isAcitiveStatus(List<Member> memberResultList) {
		boolean isAcitiveStatus = false;
		for (Member m : memberResultList) {
			if (!m.getActivateCode().equals("Not Activiate")) {
				isAcitiveStatus = true;
				break;
			}
		}
		return isAcitiveStatus;
	}
	
	private Member getUpdateMember(Member member, List<Member> memberResultList) {
		Member m = getValidMember(memberResultList);
		logger.error("========= getUpdateMember======cardNo===="+m.getCardNo());
		m.setEmail(member.getEmail());
		m.setCellPhone(member.getCellPhone());
		m.setPassword(member.getPassword());
		m.setRemindQuestion(member.getRemindQuestion());
		m.setRemindAnswer(member.getRemindAnswer());
		m.setActiveChannel(member.getActiveChannel());
		return m;
	}
	
	private Member getValidMember(List<Member> memberResultList) {
		Member m = memberResultList.get(0);
		for (Member mem : memberResultList) {
			logger.error("========= getValidMember======cardNo===="+mem.getCardNo());
			String cardNo=mem.getCardNo().toUpperCase();
			//G,X,J,Y,U,H
			if ((cardNo.startsWith("H"))||(cardNo.startsWith("G"))||
				(cardNo.startsWith("X"))||(cardNo.startsWith("J"))||
				(cardNo.startsWith("Y"))||(cardNo.startsWith("U"))) {
				m = mem;
				break;
			}
		}
		return m;
	}
}
