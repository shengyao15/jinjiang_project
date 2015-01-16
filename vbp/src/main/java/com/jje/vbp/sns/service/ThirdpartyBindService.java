package com.jje.vbp.sns.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.vbp.sns.ThirdpartyBindResult;
import com.jje.dto.vbp.sns.ThirdpartyBindType;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.remote.crm.datagram.response.TierUpdateRes;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.vbp.sns.ThirdpartyBindResource;
import com.jje.vbp.sns.domain.ThirdpartyBind;
import com.jje.vbp.sns.repository.ThirdpartyBindRepository;

@Component
public class ThirdpartyBindService {
	
	private final Logger logger = LoggerFactory.getLogger(ThirdpartyBindResource.class);
	
	@Autowired
	private ThirdpartyBindRepository thirdpartyBindRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberHandler memberHandler;
	
	@Transactional(rollbackFor=Exception.class)
	public ThirdpartyBindResult bind(ThirdpartyBind thirdpartyBind) throws Exception{
		if(exists(thirdpartyBind)){
			return new ThirdpartyBindResult("00002","用户已经绑定",thirdpartyBind.getMemberId());
		}
		
		ThirdpartyBindResult result = new ThirdpartyBindResult("00001","绑定成功",thirdpartyBind.getMemberId());
		Member member = memberRepository.getMemberByMemberNum(thirdpartyBind.getMemberId());
		String tier = ThirdpartyBindType.valueOf(thirdpartyBind.getThirdpartyType()).calTier(thirdpartyBind.getThirdpartyLevel());
		//如果现有层级高于奖励层级，则按原有层级计算
		if(Integer.valueOf(member.getMemberHierarchy()) > Integer.valueOf(tier)){
			tier = member.getMemberHierarchy();
		}
		thirdpartyBind.setOriginLevel(member.getMemberHierarchy());
		thirdpartyBind.setDestinationLevel(tier);
		boolean isRegister = StringUtils.equals(thirdpartyBind.getThirdpartyType(), member.getThirdpartyType());
		thirdpartyBind.setRegisterFlag(String.valueOf(isRegister));
		thirdpartyBind.setCouponFlag(String.valueOf(isRegister));
		thirdpartyBind.setScoreFlag(String.valueOf(!isRegister));
		thirdpartyBindRepository.save(thirdpartyBind);
		//调用CRM调整层级
		TierUpdateRes response = memberHandler.updateTier(thirdpartyBind.getMemberId(), tier, thirdpartyBind.getThirdpartyType(), thirdpartyBind.getThirdpartySign(), thirdpartyBind.getThirdpartyLevel());
		if(response.isSuccess()){
			//修改会员层级、合作类型
			Member newMember = new Member();
			newMember.setMemberHierarchy(tier);
			newMember.setMemberID(member.getMemberID());
			newMember.setThirdpartyType(thirdpartyBind.getThirdpartyType());
			memberRepository.updateMemberInfo(newMember);
		}else{
			logger.warn("调用CRM调整层级失败!");
			throw new Exception();
		}
		result.setTier(tier);
		return result;
	}
	
	public ThirdpartyBind queryThirdpartyBind(ThirdpartyBind thirdpartyBind){
		List<ThirdpartyBind> list = thirdpartyBindRepository.query(thirdpartyBind);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return new ThirdpartyBind();
	}
	
	private boolean exists(ThirdpartyBind thirdpartyBind){
		List<ThirdpartyBind> list = thirdpartyBindRepository.queryByMcMemberCode(thirdpartyBind);
		List<ThirdpartyBind> list2 = thirdpartyBindRepository.queryBySign(thirdpartyBind);
		return CollectionUtils.isNotEmpty(list) || CollectionUtils.isNotEmpty(list2);
	}
	
}
