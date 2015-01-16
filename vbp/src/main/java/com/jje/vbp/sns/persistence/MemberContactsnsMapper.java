package com.jje.vbp.sns.persistence;

import java.util.List;

import com.jje.vbp.sns.domain.MemberContactsns;

public interface MemberContactsnsMapper {
	
	void update(MemberContactsns memberContactsns);

	void save(MemberContactsns memberContactsns);

	List<MemberContactsns> query(MemberContactsns memberContactsns);

}
