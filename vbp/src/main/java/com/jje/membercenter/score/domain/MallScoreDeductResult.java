package com.jje.membercenter.score.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.BeanUtils;

import com.jje.dto.vbp.coupon.MallScoreDeductResultDto;
import com.jje.vbp.mall.domain.MallScoreDeductResultCode;

@XmlRootElement
public class MallScoreDeductResult implements Serializable {

	private static final long serialVersionUID = 3490593492085029883L;

	private MallScoreDeductResultCode resultCode;

	public MallScoreDeductResult() {
		super();
	}

	public MallScoreDeductResult(MallScoreDeductResultCode resultCode) {
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

	public boolean isSuccess() {
		return "201".equals(getCode());
	}

	@Override
	public String toString() {
		return "MallScoreDeductResultDto [code=" + getCode() + ", message=" + getMessage() + "]";
	}

	public MallScoreDeductResultDto toDto() {
		MallScoreDeductResultDto result = new MallScoreDeductResultDto();
		BeanUtils.copyProperties(this, result);
		return result;
	}

}
