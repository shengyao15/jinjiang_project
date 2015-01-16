/**
 * 
 */
package com.jje.membercenter.domain;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.MemberOrderNoteDto;

/**
 * @author Z_Xiong
 *
 */
public interface MemberCardOrderAdminRepository {
   
	ResultMemberDto<MemberCardOrderDto> queryAdminMemberCardOrder(QueryMemberDto<MemberCardOrderDto> cardOrderDto);
	
	ResultMemberDto<MemberOrderNoteDto> getMemberOrderNoteByOrderNo(String orderNo);
	
	void saveMemberOrderNote(MemberOrderNoteDto noteDto);
	 
}
