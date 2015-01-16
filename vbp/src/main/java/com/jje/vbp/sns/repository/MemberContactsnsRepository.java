package com.jje.vbp.sns.repository;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.vbp.sns.domain.MemberContactsns;
import com.jje.vbp.sns.persistence.MemberContactsnsMapper;

@Repository
public class MemberContactsnsRepository {

	@Autowired
	private MemberContactsnsMapper memberContactsnsMapper;
	
	public void saveOrUpdate(MemberContactsns memberContactsns) {
		List<MemberContactsns> contactsns = query(memberContactsns);
		if(CollectionUtils.isEmpty(contactsns)){
			memberContactsnsMapper.save(memberContactsns);	
		} else {
			memberContactsnsMapper.update(memberContactsns);
		}
	}
	
	public List<MemberContactsns> query(MemberContactsns memberContactsns){
		return memberContactsnsMapper.query(memberContactsns);
	}

}
