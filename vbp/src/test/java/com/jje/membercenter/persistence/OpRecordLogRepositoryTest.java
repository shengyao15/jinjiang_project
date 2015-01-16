package com.jje.membercenter.persistence;

import com.jje.membercenter.DataPrepareFramework;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.domain.OpRecordLogDomain;
import com.jje.membercenter.domain.OpRecordLogDomain.EnumOpType;

@Transactional
public class OpRecordLogRepositoryTest extends DataPrepareFramework {

	@Autowired
	private OpRecordLogRepository opRecordLogRepository;

	@Test
	public void insert_success() {
		OpRecordLogDomain domain = new OpRecordLogDomain();
		domain.setOpType(EnumOpType.MEMBER_ACTIVE);
		domain.setMessage("成功");
		domain.setContent("<response/>");
		opRecordLogRepository.insert(domain);
		Assert.assertTrue(true);
	}

}
