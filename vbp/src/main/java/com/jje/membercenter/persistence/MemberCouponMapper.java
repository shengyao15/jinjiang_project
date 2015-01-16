package com.jje.membercenter.persistence;

import com.jje.membercenter.domain.MemberCoupon;

/**
 * @author zhenhui_xiong
 * @version 1.0
 * @date Nov 9, 2011 4:39:52 PM
 */
public interface MemberCouponMapper {
   Integer getCouponCount(MemberCoupon coupon);
}
