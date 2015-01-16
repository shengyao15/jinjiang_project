package com.jje.membercenter.commonContact.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jje.membercenter.commonContact.domain.CommonlyUsedContact;
import com.jje.membercenter.commonContact.domain.MemberLevel;

public interface CommonlyUsedContactMapper {

	public List<CommonlyUsedContact> queryCommonlyUsedContactsWithoutPagination(
			@Param(value = "mcMemberCode") String mcMemberCode,
			@Param(value = "label") String label);

	public void addCommonlyUsedContact(CommonlyUsedContact commonlyUsedContact);

	public List<MemberLevel> queryMemberLevel(MemberLevel memberLevel);

}
