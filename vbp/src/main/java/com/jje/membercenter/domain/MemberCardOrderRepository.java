package com.jje.membercenter.domain;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.vbp.order.domain.CardOrderHistory;

/**
 * @author zhenhui_xiong
 * @version 1.0
 * @date Nov 3, 2011 5:18:46 PM
 */
public interface MemberCardOrderRepository {
	
	 void updateStatus(MemberCardOrder memberCardOrder);
	 
     MemberCardOrder getMemberCardOrder(String orderNo);

	 void insertOrder(MemberCardOrder memberCardOrder);
	 
	 void insertOrderHistory(CardOrderHistory history);

	Long getNextSequence();
	
	ResultMemberDto<MemberCardOrderDto> queryMemberCardOrderList(QueryMemberDto<MemberCardOrderDto> memberCardOrderDto);
}
