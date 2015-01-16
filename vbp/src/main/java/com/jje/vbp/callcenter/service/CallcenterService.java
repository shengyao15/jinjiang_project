package com.jje.vbp.callcenter.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.vbp.callcenter.MemberCallcenterConditionDto;
import com.jje.dto.vbp.callcenter.MemberCallcenterResult;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;

@Component
public class CallcenterService {
	
	private final Logger logger = LoggerFactory.getLogger(CallcenterService.class);

	@Autowired
	private MemberRepository memberRepository;
	
	public MemberCallcenterResult queryMember(MemberCallcenterConditionDto callcenterCondition) {
		List<Member> members = memberRepository.queryMember(callcenterCondition);
		if (CollectionUtils.isEmpty(members)) {
			logger.warn("CallcenterService.queryMember not found member condition:{}", callcenterCondition);
		}
		List<MemberDto> memeberDtos = Member.toDtos(members);
		return new MemberCallcenterResult(memeberDtos);
	}
	
}
