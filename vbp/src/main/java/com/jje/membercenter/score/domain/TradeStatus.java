package com.jje.membercenter.score.domain;

public enum TradeStatus {
	
	RECEIVE_REQUEST("接收请求成功"),
	CRM_DEDUCT_SUCCESS("CRM扣减成功"), 
	STORE_SCORE_REDEEM_SUCCESS("保存积分扣减对账成功"), 
	TRIGGER_COUPON_SUCCESS("发送优惠券成功"),
	TRIGGER_COUPON_FAIL("发送优惠券"), 
	SUCCESS("积分兑换流程成功"), 
	CRM_FAIL("CRM扣减失败"),
	BLOCK("兑换中"),
    FAIL_NEED_MANUAL_PROCESSING("失败需要手动处理"),
	FAIL("积分兑换流程失败");
	
	private String cnName;

	private TradeStatus(String cnName) {
		this.cnName = cnName;
	}

	public String getCnName() {
		return cnName;
	}

}
