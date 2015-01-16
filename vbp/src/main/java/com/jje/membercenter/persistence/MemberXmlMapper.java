package com.jje.membercenter.persistence;

import com.jje.membercenter.domain.MemberXml;

public interface MemberXmlMapper {
	public void saveXml(MemberXml memberXml);

	public MemberXml getXml(String id);

	public void updateCallBackFlag(String id);
	
	public void updateCallBackFlagByBean(MemberXml memberXml);
	
	public MemberXml getXmlByOrderNo(String orderNo);
}
