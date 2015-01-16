package com.jje.vbp.sns.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.vbp.sns.domain.ThirdpartyBind;
import com.jje.vbp.sns.persistence.ThirdpartyBindMapper;

@Repository
public class ThirdpartyBindRepository {

	@Autowired
	private ThirdpartyBindMapper thirdpartyBindMapper;
	
	public void save(ThirdpartyBind thirdpartyBind) {
		thirdpartyBindMapper.save(thirdpartyBind);
	}
	
	public List<ThirdpartyBind> query(ThirdpartyBind thirdpartyBind){
		return thirdpartyBindMapper.query(thirdpartyBind);
	}

	public List<ThirdpartyBind> queryByMcMemberCode(ThirdpartyBind thirdpartyBind){
		return thirdpartyBindMapper.queryByMcMemberCode(thirdpartyBind);
	}
	
	public List<ThirdpartyBind> queryBySign(ThirdpartyBind thirdpartyBind){
		return thirdpartyBindMapper.queryBySign(thirdpartyBind);
	}
}
