package com.jje.vbp.memberWechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jje.vbp.memberWechat.domain.MemberWechatDomain;
import com.jje.vbp.memberWechat.persistence.MemberWechatMapper;

@Service
public class MemberWechatService {

	@Autowired
	private MemberWechatMapper memberWechatMapper;

	public int insert(MemberWechatDomain memberWechatDomain) {
		return memberWechatMapper.insert(memberWechatDomain);
	}

	public List<MemberWechatDomain> getMemberWechatByMcCode(String mcCode) {
		return memberWechatMapper.getMemberWechatByMcCode(mcCode);
	}
}
