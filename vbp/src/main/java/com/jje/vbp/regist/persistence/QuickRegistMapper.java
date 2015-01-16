package com.jje.vbp.regist.persistence;

import com.jje.vbp.regist.domain.WebMemberInfo;
import com.jje.vbp.regist.domain.WebMemberVerify;

public interface QuickRegistMapper {
	
	public String getTempCardNum();
	
	public String getMcMemberCode();
	
	public void saveWebMemberInfo(WebMemberInfo webMemberInfo);
	
	public void saveWebMemberVerify(WebMemberVerify webMemberVerify);

}
