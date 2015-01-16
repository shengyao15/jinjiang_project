package com.jje.membercenter.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.vbp.order.domain.CardOrderHistory;

/**
 * @author zhenhui_xiong
 * @version 1.0
 * @date Nov 3, 2011 5:17:41 PM
 */
@Repository
@Transactional
public class MemberCardOrderRepositoryImpl implements MemberCardOrderRepository {

	private static final Logger logger = LoggerFactory.getLogger(MemberCardOrderRepositoryImpl.class);
	
	@Autowired
	private MemberCardOrderMapper memberCardOrderMapper;

	public void updateStatus(MemberCardOrder memberCardOrder) {
		memberCardOrderMapper.updateStatus(memberCardOrder);
	}
	
	public  MemberCardOrder getMemberCardOrder(String orderNo){
		return memberCardOrderMapper.getMemberCardOrder(orderNo);
	}


	public void insertOrder(MemberCardOrder memberCardOrder) {
		memberCardOrderMapper.insertOrder(memberCardOrder);
	}
	
	public void insertOrderHistory(CardOrderHistory history) {
		try {
			memberCardOrderMapper.insertOrderHistory(history);
		} catch (Exception e) {
			logger.error("insertOrderHistory({}) error!", history, e);
		}
	}

	public Long getNextSequence() {
		// TODO Auto-generated method stub
		return memberCardOrderMapper.getNextSequence();
	}

	public ResultMemberDto<MemberCardOrderDto> queryMemberCardOrderList(QueryMemberDto<MemberCardOrderDto> cardOrderDto){
	  //MemberCardOrder memberCardOrder = new MemberCardOrder(cardOrderDto.getCondition());
		
//		if (StringUtils.isBlank(cardOrderDto.getCondition().getMemberId())) {
//			ResultMemberDto<MemberCardOrderDto> memberCardOrderDto = new ResultMemberDto<MemberCardOrderDto>();
//			Pagination pagination = cardOrderDto.getPagination();
//			pagination.countRecords(0);
//			memberCardOrderDto.setPagination(pagination);
//			memberCardOrderDto.setResults(new ArrayList<MemberCardOrderDto>());
//			return memberCardOrderDto;
//		}
	  
	  Integer count = memberCardOrderMapper.getMemberCardOrderCount(cardOrderDto);
	  Pagination pagination = new Pagination();
	  pagination.countRecords(count);
	  
	  ResultMemberDto<MemberCardOrderDto> memberCardOrderDto = new ResultMemberDto<MemberCardOrderDto>();
	  List<MemberCardOrder> memberCardOrderList = memberCardOrderMapper.queryMemberCardOrder(cardOrderDto);
	  List<MemberCardOrderDto> memberCardOrderDtoList = new ArrayList<MemberCardOrderDto>();
	  if(memberCardOrderList!=null && memberCardOrderList.size()>0){
		  for(MemberCardOrder localMemberCardOrder:memberCardOrderList){
			  memberCardOrderDtoList.add(localMemberCardOrder.toDto());
		  }
		  if(cardOrderDto.getPagination()!=null)
			  pagination.setPage(cardOrderDto.getPagination().getPage());
	  }
	  memberCardOrderDto.setPagination(pagination);
	  memberCardOrderDto.setResults(memberCardOrderDtoList);
	  return memberCardOrderDto;
   }
	
}
