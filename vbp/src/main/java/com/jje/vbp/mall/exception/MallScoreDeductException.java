package com.jje.vbp.mall.exception;

import com.jje.vbp.mall.domain.MallScoreDeductResultCode;

public class MallScoreDeductException extends Exception {

	private static final long serialVersionUID = -5954286793016840001L;

	private MallScoreDeductResultCode resultCode;

	public MallScoreDeductException() {
		super();
	}

	public MallScoreDeductException(MallScoreDeductResultCode resultCode) {
		this();
		this.resultCode = resultCode;
	}

	public MallScoreDeductResultCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(MallScoreDeductResultCode resultCode) {
		this.resultCode = resultCode;
	}

	public String getCode() {
		return resultCode.getCode();
	}

	public String getMessage() {
		return resultCode.getMessage();
	}

}
