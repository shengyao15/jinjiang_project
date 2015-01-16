/**
 * 
 */
package com.jje.membercenter.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.MemberOrderNoteDto;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderAdminRepository;
import com.jje.membercenter.domain.MemberOrderNote;

/**
 * @author Z_Xiong
 *
 */
@Repository
public class MemberCardOrderAdminRespositoryImpl implements
		MemberCardOrderAdminRepository {
	
	@Autowired
	private MemberCardOrderMapper memberCardOrderMapper;
	
	@Autowired
	private MemberOrderNoteMapper memberOrderNoteMapper;

	/* (non-Javadoc)
	 * @see com.jje.membercenter.domain.MemberCardOrderAdminRepository#queryAdminMemberCardOrder(com.jje.dto.QueryMemberDto)
	 */
	public ResultMemberDto<MemberCardOrderDto> queryAdminMemberCardOrder(
			QueryMemberDto<MemberCardOrderDto> cardOrderDto) {
		  Integer count = memberCardOrderMapper.getAdminMemberCardOrderCount(cardOrderDto);
		  Pagination pagination = cardOrderDto.getPagination();
		  pagination.countRecords(count);
		  
		  ResultMemberDto<MemberCardOrderDto> memberCardOrderDto = new ResultMemberDto<MemberCardOrderDto>();
		  List<MemberCardOrder> memberCardOrderList = memberCardOrderMapper.queryAdminMemberCardOrder(cardOrderDto);
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
	
	public ResultMemberDto<MemberOrderNoteDto> getMemberOrderNoteByOrderNo(String orderNo){
		List<MemberOrderNote> notes = memberOrderNoteMapper.getMemberOrderNoteByOrderNo(orderNo);
		List<MemberOrderNoteDto> notesDto = new ArrayList<MemberOrderNoteDto>();
		ResultMemberDto<MemberOrderNoteDto> results = new ResultMemberDto<MemberOrderNoteDto>();
		if(notes!=null){
			for(MemberOrderNote note:notes){
				notesDto.add(note.toDto());
			}
		}
		results.setResults(notesDto);
		return results;
	}
	
	public void saveMemberOrderNote(MemberOrderNoteDto noteDto){
		MemberOrderNote note = new MemberOrderNote(noteDto);
		memberOrderNoteMapper.saveMemberOrderNote(note);
	}
	

}
