package com.jje.vbp.memberBind.service;

import com.jje.vbp.memberBind.domain.MemberBindEntity;

public interface MemberBindRepository {
	/**
	 * 绑定会员
	 * @param memberBindDto
	 * @throws Exception
	 */
	void bindMember(MemberBindEntity MemberBindEntity) throws Exception;
	
	/**
	 * 根据channel和bindkey 查询绑定用户数
	 * @param memberBindEntity
	 * @return
	 */
	int queryBindMemberCount(MemberBindEntity memberBindEntity);
	
	String getMemberIdByKey(String key, String channel);
	
    String getMcMemberCodeByKey(String key, String channel);
}
