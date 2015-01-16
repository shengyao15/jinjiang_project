package com.jje.membercenter.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.vbp.member.MemberFeedbackDto;
import com.jje.membercenter.domain.MemberFeedback;



/**
 * @version 1.0
 * @date Nov 9, 2011 4:39:52 PM
 */
@Repository
public class MemberFeedbackRepository {

	@Autowired
	private MemberFeedbackMapper memberFeedbackMapper;

	public List<MemberFeedback> queryFeedback(QueryMemberDto<MemberFeedbackDto> queryDto) {
		return memberFeedbackMapper.queryFeedback(queryDto);
	}

	public void insertMemberFeedback(MemberFeedback memberFeedback) {
		memberFeedbackMapper.insertMemberFeedback(memberFeedback);
	}

	public void updateMemberFeedback(MemberFeedback memberFeedback) {
		memberFeedbackMapper.updateMemberFeedback(memberFeedback);
	}

	public Integer queryFeedbackCount(QueryMemberDto<MemberFeedbackDto> queryDto) {
		return memberFeedbackMapper.queryFeedbackCount(queryDto);
	}

}
