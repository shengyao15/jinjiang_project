package com.jje.vbp.regist.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.membercenter.domain.WebMember;
import com.jje.vbp.regist.persistence.QuickRegistMapper;

@Repository
public class QuickRegistRepository {
	
	@Autowired
	private QuickRegistMapper quickRegistMapper;
	
	public String getTempCardNum(){
		return quickRegistMapper.getTempCardNum();
	}
	
	public String getMcMemberCode(){
		return quickRegistMapper.getMcMemberCode();
	}
	
	public void saveWebMemberInfo(WebMemberInfo webMemberInfo){
		quickRegistMapper.saveWebMemberInfo(webMemberInfo);
	}
	
	public void saveWebMemberVerfy(WebMemberVerify webMemberVerify){
		quickRegistMapper.saveWebMemberVerify(webMemberVerify);
	}

}
