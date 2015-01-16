package com.jje.membercenter.persistence;

import java.util.List;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.vbp.member.MemberFeedbackDto;
import com.jje.membercenter.domain.MemberFeedback;

/**
 * @version 1.0
 * @date Nov 9, 2011 4:39:52 PM
 */
public interface MemberFeedbackMapper {

	List<MemberFeedback> queryFeedback(QueryMemberDto<MemberFeedbackDto> queryDto);

	void insertMemberFeedback(MemberFeedback memberFeedback);

	void updateMemberFeedback(MemberFeedback memberFeedback);

	Integer queryFeedbackCount(QueryMemberDto<MemberFeedbackDto> queryDto);
}
