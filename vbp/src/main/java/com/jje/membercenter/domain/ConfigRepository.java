package com.jje.membercenter.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.membercenter.MemberDegree;
import com.jje.membercenter.support.domain.DataCode;
import com.jje.membercenter.support.domain.DataConstant;
import com.jje.membercenter.support.domain.SupportRepository;

@Repository
public class ConfigRepository {	
	@Autowired
	private SupportRepository supportRepository;
	
	public String getUpgradeNumber(MemberDegree memberDegree ){
		if(memberDegree==MemberDegree.CLASSIC || memberDegree==MemberDegree.SILVER_CARD){
			DataCode dataCode=supportRepository.queryDataCode(DataConstant.GOLD_UPGRADE_NUMBER.getType(), DataConstant.GOLD_UPGRADE_NUMBER.name());
			return dataCode.getVal();
		}
		if(memberDegree==MemberDegree.GOLD ||  memberDegree==MemberDegree.PLATINUM_CARD){
			DataCode dataCode=supportRepository.queryDataCode(DataConstant.PLATINUM_UPGRADE_NUMBER.getType(), DataConstant.PLATINUM_UPGRADE_NUMBER.name());
			return dataCode.getVal();
		}
		return null;
 	}
	
	public String getUpgradeScores(MemberDegree memberDegree ){
		if(memberDegree==MemberDegree.CLASSIC || memberDegree==MemberDegree.SILVER_CARD){
			DataCode dataCode=supportRepository.queryDataCode(DataConstant.GOLD_UPGRADE_SCORES.getType(), DataConstant.GOLD_UPGRADE_SCORES.name());
			return dataCode.getVal();
		}
		if(memberDegree==MemberDegree.GOLD ||  memberDegree==MemberDegree.PLATINUM_CARD){
			DataCode dataCode=supportRepository.queryDataCode(DataConstant.PLATINUM_UPGRADE_SCORES.getType(), DataConstant.PLATINUM_UPGRADE_SCORES.name());
			return dataCode.getVal();
		}
		return null;
 	}

}
