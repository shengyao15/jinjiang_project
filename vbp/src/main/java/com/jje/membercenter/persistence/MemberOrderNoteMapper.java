/**
 * 
 */
package com.jje.membercenter.persistence;

import java.util.List;

import com.jje.membercenter.domain.MemberOrderNote;

/**
 * @author Z_Xiong
 *
 */
public interface MemberOrderNoteMapper {
	
  List<MemberOrderNote> getMemberOrderNoteByOrderNo(String orderNo);
  
  void saveMemberOrderNote(MemberOrderNote note);
}
