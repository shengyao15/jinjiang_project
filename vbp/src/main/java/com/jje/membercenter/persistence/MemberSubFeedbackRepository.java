package com.jje.membercenter.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.vbp.member.MemberSubFeedbackDto;
import com.jje.membercenter.domain.MemberSubFeedback;



/**
 * @version 1.0
 * @date Nov 9, 2011 4:39:52 PM
 */
@Repository
public class MemberSubFeedbackRepository {

	@Autowired
	private MemberSubFeedbackMapper memberSubFeedbackMapper;

	public List<MemberSubFeedback> querySubFeedback(QueryMemberDto<MemberSubFeedbackDto> queryDto) {
		return memberSubFeedbackMapper.querySubFeedback(queryDto);
	}

	public void insertMemberSubFeedback(MemberSubFeedback MemberSubFeedback) {
		memberSubFeedbackMapper.insertMemberSubFeedback(MemberSubFeedback);
	}
}
