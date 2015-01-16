/**
 * 
 */
package com.jje.membercenter.domain;

/**
 * @author SHENGLI_LU
 *
 */
public interface MemberXmlRepository {
	public MemberXml getXml(String id);
	public void saveXml(MemberXml memberXml);
	public void updateCallBackFlag(String id);
	public MemberXml getXmlByOrderNo(String orderNo);
	public void updateCallBackFlagByBean(MemberXml mx);
	
}
