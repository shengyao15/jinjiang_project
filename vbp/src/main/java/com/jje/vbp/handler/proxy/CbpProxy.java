package com.jje.vbp.handler.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.RestClient;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.coupon.coupon.BatchUpdateDto;
import com.jje.dto.coupon.coupon.CouponQueryForMemberDto;
import com.jje.dto.coupon.coupon.CouponValidateForExchangeDto;
import com.jje.dto.coupon.coupon.CouponValidateResultForExchangeDto;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.register.RegisterIssueDto;
import com.jje.dto.coupon.rule.CouponRuleDto;

@Component
public class CbpProxy {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestClient restClient;
	
	@Value(value = "${cbp.url}")
	private String cbpUrl;



	public CouponSysIssueResult issueCoupon(RegisterIssueDto issueDto) {
		CouponSysIssueResult sysIssueResult = null;
		try {
			sysIssueResult = restClient.post(cbpUrl + "/sysIssue/registerIssue", issueDto,CouponSysIssueResult.class);
		} catch (Exception e) {
			logger.error("CbpProxy   registerIssue({})------interface invoker error :",sysIssueResult, e);
		}
		return sysIssueResult;
	}


	
	public void useCoupon(BatchUpdateDto useDto) {
		restClient.put(cbpUrl + "/coupon/batchCouponUsed", useDto);
	}

	
	public void lockCoupon(BatchUpdateDto lockDto) {
		restClient.put(cbpUrl + "/coupon/batchCouponLock", lockDto);
	}

	
	public void unlockCoupon(BatchUpdateDto unlockDto) {
		restClient.put(cbpUrl + "/coupon/batchCouponUnUse", unlockDto);
	}

	public CouponValidateResultForExchangeDto validateForExchange(String couponCode, String mcMemberCode) {
		return restClient.post(cbpUrl + "/coupon/validateForExchange", new CouponValidateForExchangeDto(couponCode, mcMemberCode), CouponValidateResultForExchangeDto.class);
	}
	
	public ResultMemberDto<CouponRuleDto> getCouponInfo(String mcMemberCode) {
		CouponQueryForMemberDto couponQueryForMemberDto = new CouponQueryForMemberDto();
		couponQueryForMemberDto.setMemberID(mcMemberCode);
		couponQueryForMemberDto.setCouponStatus("1");
		return restClient.post(cbpUrl + "/newCouponRule/queryCouponRules", couponQueryForMemberDto, ResultMemberDto.class);
	}
	

}
