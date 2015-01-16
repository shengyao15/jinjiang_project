package com.jje.membercenter.score.domain;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.score.ScoreFillUpResultDto;
import com.jje.membercenter.remote.crm.datagram.response.ScoreFillUpRes;

public class ScoreFillUpResult {
	
	private String memberId;
	
	private String returnCode;
	
	private String returnMsg;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	public void fromSoreFillUpCRMResponse(ScoreFillUpRes response) {
		this.returnCode = response.getBody().getRecode().toString();
		if(response.getBody().getMembid() != null)
			this.memberId = response.getBody().getMembid();
		if(response.getBody().getRemsg() != null)
			this.returnMsg = response.getBody().getRemsg();
	}
	
	public ScoreFillUpResultDto toDto() {
		ScoreFillUpResultDto result = new ScoreFillUpResultDto();
		BeanUtils.copyProperties(this, result);
		return result;
	}

}
