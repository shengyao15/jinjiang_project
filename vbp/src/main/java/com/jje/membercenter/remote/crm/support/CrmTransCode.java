package com.jje.membercenter.remote.crm.support;

public enum CrmTransCode {
	QUERY_MEMBER_ADDRESS("20007", "查询会员地址信息"),
	GET_MEMBER_INFO("20004", "查询会员信息"), 
	GET_MEMBER_RELATED_INFO("20013", "查询会员关系联系人信息"), 
	GET_SCORES_HISTORY("20011",	"查询积分消费历史信息"),
    QUERY_SCORE_DEDUCT("20025","查询积分扣减历史记录"),
	SCORE_FILL_UP("30012", "积分补登"),
	SCORES_TRADE("30011", "积分兑换"),
	REGISTER_SCORES_TRADE("30016", "邀请码注册"),
	CANCEL_TRADE("40011","取消积分兑换"),
    GET_SCORES_REDEEM_LIST("20012","积分补登记录列表"),
    UPDATE_MEMBER_INFO("30004","会员基本信息更新"),
    UPDATE_QUICK_MEMBER_INFO("30001","完善会员基本信息更新"),
    QUICK_REGIST("10004","会员快速注册"),
	MEMBER_AVALIABLE_LOYPOINT("20022","会员可用积分余额"),
	REGISTER_MEMBER("10009", "注册会员"),
	ACTIVE_MEMBER("30019","激活会员"),
	PARTNER_CARD_BIND("30008","绑定合作伙伴卡"),
	TIER_UPDATE("30017","调整层级"),
	GOLDPAL_MEMBER_REGIEST("10006","锦江客户注册接口"),
    BATCH_REGISTER_MEMBER("10005","批量注册会员"),
	GOLDPAL_MEMBER_QUERY("20001","锦江客户查询接口"),
	QUERY_MEMBER_PARTNER_CARD("20008","查询绑定卡"),
	PROMOTION_SIGN_UP("50001","活动报名");
	
	private String code;

	private String alias;

	public String getAlias() {
		return alias;
	}

	public String getCode() {
		return code;
	}

	private CrmTransCode(String code, String alias) {
		this.code = code;
		this.alias = alias;
	}

}
