/**
 * 
 */
package com.jje.membercenter.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;

/**
 * @author SHENGLI_LU
 *
 */
@Repository
@Transactional
public class MemberXmlRepositoryImpl implements MemberXmlRepository{

	@Autowired
	private MemberXmlMapper memberXmlMapper;
	
	public MemberXml getXml(String id) {
		// TODO Auto-generated method stub
		return memberXmlMapper.getXml(id);
	}

	public void saveXml(MemberXml memberXml) {
		// TODO Auto-generated method stub
		memberXmlMapper.saveXml(memberXml);
	}

	public void updateCallBackFlag(String id) {
		// TODO Auto-generated method stub
		memberXmlMapper.updateCallBackFlag(id);
	}
	
	public MemberXml getXmlByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		return memberXmlMapper.getXmlByOrderNo(orderNo);
	}

	public void updateCallBackFlagByBean(MemberXml mx) {
		memberXmlMapper.updateCallBackFlagByBean(mx);
	}

}
