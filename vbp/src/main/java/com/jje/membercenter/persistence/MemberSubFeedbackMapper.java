package com.jje.membercenter.persistence;

import java.util.List;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.vbp.member.MemberSubFeedbackDto;
import com.jje.membercenter.domain.MemberSubFeedback;

/**
 * @version 1.0
 * @date Nov 9, 2011 4:39:52 PM
 */
public interface MemberSubFeedbackMapper {

	List<MemberSubFeedback> querySubFeedback(QueryMemberDto<MemberSubFeedbackDto> queryDto);

	void insertMemberSubFeedback(MemberSubFeedback MemberSubFeedback);
}
