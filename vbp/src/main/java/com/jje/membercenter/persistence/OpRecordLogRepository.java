package com.jje.membercenter.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.domain.OpRecordLogDomain;

@Repository
@Transactional
public class OpRecordLogRepository {
	@Autowired
	private OpRecordLogMapper opRecordLogMapper;

	public void insert(OpRecordLogDomain domain) {
		opRecordLogMapper.insert(domain);
	}
}
