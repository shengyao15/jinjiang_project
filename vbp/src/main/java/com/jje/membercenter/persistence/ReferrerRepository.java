package com.jje.membercenter.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.domain.ReferrerInfo;

@Repository
@Transactional
public class ReferrerRepository {
	
	@Autowired
	private ReferrerInfoMapper referrerInfoMapper;

	public void save(ReferrerInfo info) {
		referrerInfoMapper.save(info);
	}

}
