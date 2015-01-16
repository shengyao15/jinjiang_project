package com.jje.membercenter.domain;

import java.util.List;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.membercenter.MemberPrivilegeDto;
import com.jje.dto.membercenter.VipCardInfoDto;

public interface CRMUpdateRightCardRepository
{
	List<VipCardInfoDto> queryVIPCardInfo(String memberId) throws Exception;
}
