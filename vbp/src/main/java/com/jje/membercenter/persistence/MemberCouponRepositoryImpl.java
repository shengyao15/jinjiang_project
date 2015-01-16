package com.jje.membercenter.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.domain.MemberCoupon;
import com.jje.membercenter.domain.MemberCouponRepository;

/**
 * @author zhenhui_xiong
 * @version 1.0
 * @date Nov 9, 2011 3:00:57 PM
 */
@Repository
@Transactional
public class MemberCouponRepositoryImpl implements MemberCouponRepository {
	
	@Autowired
	MemberCouponMapper memberCouponMapper;
	
   public int getCouponCount(MemberCoupon memberCoupon){
	   return memberCouponMapper.getCouponCount(memberCoupon);
   }
}
