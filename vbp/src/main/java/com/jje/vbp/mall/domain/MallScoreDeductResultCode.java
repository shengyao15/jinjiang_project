package com.jje.vbp.mall.domain;

public enum MallScoreDeductResultCode {

	SUCCESS_201("201", "积分扣减成功", false),
	ERROR_202("202","积分扣减失败：积分不够", false),
	ERROR_203("","", false),
	ERROR_204("204","积分扣减失败：无此用户", false),
	ERROR_205("205","积分扣减失败：其他因素", true),
	ERROR_206("206","积分扣减失败：积分扣减异常", true),
	ERROR_207("207","积分扣减失败：积分记录异常", true),
	ERROR_208("208","积分扣减失败：积分扣减异常", true),
	ERROR_209("209","积分扣减失败:无效的用户信息", false),
	ERROR_210("210","积分扣减失败:数据校验失败", false),
	ERROR_211("211","积分扣减失败:积分扣减异常", false),
	ERROR_212("212","积分扣减失败：该订单已存在", false),
	ERROR_213("213","积分扣减失败：该订单数据异常", false),
	ERROR_214("214","积分扣减失败：积分扣减拒绝", false);

	private String code;
	private String message;
	private boolean isBlockable; 

	private MallScoreDeductResultCode(String code, String message, boolean isBlockable) {
		this.code = code;
		this.message = message;
		this.isBlockable = isBlockable;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public boolean isBlockable() {
		return isBlockable;
	}
	
}
