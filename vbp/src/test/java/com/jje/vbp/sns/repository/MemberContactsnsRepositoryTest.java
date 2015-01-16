package com.jje.vbp.sns.repository;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.sns.domain.MemberContactsns;

public class MemberContactsnsRepositoryTest  extends DataPrepareFramework{

	@Autowired
	private MemberContactsnsRepository contactsnsRepository;
	
	@Test
	public void queryContactsnsTest(){
		MemberContactsns memberContactsns = new MemberContactsns();
		List<MemberContactsns>  contactsnsList = contactsnsRepository.query(memberContactsns);
		Assert.assertNotNull(contactsnsList);
	}
}
