package com.jje.membercenter.persistence;

import java.util.List;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.vbp.order.domain.CardOrderHistory;

/**
 * @author zhenhui_xiong
 * @version 1.0
 * @date Nov 3, 2011 4:09:24 PM
 */
public interface MemberCardOrderMapper {
	
	 void updateStatus(MemberCardOrder memberCardOrder);
	 
	 MemberCardOrder getMemberCardOrder(String orderNo);

	 void insertOrder(MemberCardOrder memberCardOrder);

	 Long getNextSequence();
	 
	 List<MemberCardOrder> queryMemberCardOrder(QueryMemberDto<MemberCardOrderDto> cardOrderDto);
	 
	 Integer getMemberCardOrderCount(QueryMemberDto<MemberCardOrderDto> cardOrderDto);
	 
	 List<MemberCardOrder> queryAdminMemberCardOrder(QueryMemberDto<MemberCardOrderDto> cardOrderDto);
	 
	 Integer getAdminMemberCardOrderCount(QueryMemberDto<MemberCardOrderDto> cardOrderDto);
	 
	 void insertOrderHistory(CardOrderHistory history);
}
