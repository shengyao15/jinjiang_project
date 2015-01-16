package com.jje.membercenter.commonContact.domain;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jje.dto.membercenter.contact.CommonlyUsedContactDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.commonContact.persistence.CommonlyUsedContactMapper;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;

@Repository
public class CommonlyUsedContactRepository {

	@Autowired
	private CommonlyUsedContactMapper mapper;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberScoreLevelInfoRepository memberScoreLevelInfoRepository;

	public List<CommonlyUsedContact> queryCommonlyUsedContactsWithoutPagination(
			String mcMemberCode, String label) {
		return mapper.queryCommonlyUsedContactsWithoutPagination(mcMemberCode,
				label);
	}

	public void addCommonlyUsedContact(CommonlyUsedContact commonlyUsedContact) {
		mapper.addCommonlyUsedContact(commonlyUsedContact);
	}

	@Transactional
	public void batchCommonlyUsedContact(
			List<CommonlyUsedContactDto> commonlyUsedContactDtos) {
		for (CommonlyUsedContactDto dto : commonlyUsedContactDtos) {
			addCommonlyUsedContact(new CommonlyUsedContact(dto));
		}
	}

	public MemberLevel queryMemberLevel(MemberLevel memberLevel) {
		List<MemberLevel> levels = mapper.queryMemberLevel(memberLevel);
		if (CollectionUtils.isEmpty(levels)) {
			return null;
		}
		for (MemberLevel level : levels) {
			if (StringUtils.isNotBlank(level.getMcMemberCode())) {
				return level;
			}
		}
		return levels.get(0);
	}

	public MemberLevel queryMember(MemberLevel memberLevel) {
		Member member = new Member();
		member.setFullName(memberLevel.getName());
		member.setIdentityType(memberLevel.getIdentityType());
		member.setIdentityNo(memberLevel.getIdentityNo());
		List<Member> members = memberRepository.queryIdentifyInfo(member);
		if (CollectionUtils.isEmpty(members)) {
			return null;
		}
		for (Member tempMember : members) {
			if (StringUtils.isNotBlank(tempMember.getMcMemberCode())) {
				return buildMemberLevelWithScore(tempMember);
			}
		}
		return buildMemberLevelWithScore(members.get(0));
	}

	private MemberLevel buildMemberLevelWithScore(Member member) {
		MemberScoreLevelInfoDto memberScoreInfo = memberScoreLevelInfoRepository
				.getMemberScoreInfo(String.valueOf(member.getId()));
		MemberLevel memberLevel = new MemberLevel(member.toDto());
		memberLevel.setScoreLevel(String.valueOf(memberScoreInfo
				.getScoreLevel()));
		memberLevel.setMcMemberCode(member.getMcMemberCode());
		return memberLevel;
	}
}
