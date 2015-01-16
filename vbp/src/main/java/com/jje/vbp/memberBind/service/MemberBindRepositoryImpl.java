package com.jje.vbp.memberBind.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.common.utils.StringUtils;
import com.jje.vbp.memberBind.domain.MemberBindEntity;
import com.jje.vbp.memberBind.persistence.MemberBindMapper;

@Repository
public class MemberBindRepositoryImpl implements MemberBindRepository {
	private static final Logger logger = LoggerFactory.getLogger(MemberBindRepositoryImpl.class);
	
	@Autowired
	private MemberBindMapper memberBindMapper;

	@Override
	public void bindMember(MemberBindEntity MemberBindEntity) throws Exception {
		// TODO Auto-generated method stub
		memberBindMapper.bindMember(MemberBindEntity);
		logger.info("会员绑定成功","会员绑定成功");
		return;
	}

	@Override
	public int queryBindMemberCount(MemberBindEntity memberBindEntity) {
		int count = 0;
		
		if( memberBindEntity!= null && !StringUtils.isBlank(memberBindEntity.getChannel()) && 
						!StringUtils.isBlank(memberBindEntity.getMcMemberCode()) ){
			count = memberBindMapper.queryBindMemberCount(memberBindEntity);
		}
		return count;
	}

	@Override
	public String getMemberIdByKey(String key, String channel) {
		MemberBindEntity memberBindEntity = new MemberBindEntity();
		memberBindEntity.setBindKey(key);
		memberBindEntity.setChannel(channel);
        return memberBindMapper.getMemberIdByKey(memberBindEntity);
	}

	@Override
	public String getMcMemberCodeByKey(String key, String channel) {
		MemberBindEntity memberBindEntity = new MemberBindEntity();
		memberBindEntity.setBindKey(key);
		memberBindEntity.setChannel(channel);
        return memberBindMapper.getMcMemberCodeByKey(memberBindEntity);
	}
}
