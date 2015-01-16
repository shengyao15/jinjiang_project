package com.jje.vbp.coupon.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.coupon.coupon.BatchUpdateDto;
import com.jje.dto.coupon.coupon.CouponValidateResultForExchangeDto;
import com.jje.dto.coupon.rule.use.ExchangeSceneDto.ExchangeType;
import com.jje.dto.membercenter.score.ScoreReceiverDto;
import com.jje.dto.vbp.coupon.ExchangeScoreDto;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.vbp.handler.proxy.CbpProxy;

@Component
public class MemberCouponService {
	private static final Logger logger = LoggerFactory.getLogger(MemberCouponService.class);

	@Autowired
	private CbpProxy cbpProxy;
	
	@Autowired
	private ScoresHandler scoresHandler;	
	
	public String exchangeScore(ExchangeScoreDto exchangeScoreDto) {
		String result = "success";
		try {
			doExchangeScore(exchangeScoreDto);
		} catch (Exception e) {
			logger.error("exchangeScore(ExchangeScoreDto) error", e); 
			result = e.getMessage();
			cbpProxy.unlockCoupon(new BatchUpdateDto(exchangeScoreDto.getCouponCode(), "", "兑换解锁优惠卷,mc=" + exchangeScoreDto.getMcMemberCode()));
		}
		return result;
	}

	private void doExchangeScore(ExchangeScoreDto exchangeScoreDto) throws Exception {
		String productName  = validateCoupon(exchangeScoreDto);
		cbpProxy.lockCoupon(new BatchUpdateDto(exchangeScoreDto.getCouponCode(), "", "兑换锁定优惠卷,mc=" + exchangeScoreDto.getMcMemberCode()));
		addScore(exchangeScoreDto, productName);
		cbpProxy.useCoupon(new BatchUpdateDto(exchangeScoreDto.getCouponCode(), "", "兑换使用优惠卷,mc=" + exchangeScoreDto.getMcMemberCode()));
	}


	private void addScore(ExchangeScoreDto exchangeScoreDto, String productName) throws Exception {
		ScoreReceiverDto receiver = new ScoreReceiverDto();
		receiver.setMcMemberCode(exchangeScoreDto.getMcMemberCode());
		receiver.setTransdate(new Date()); 
		receiver.setProductname(productName);
		scoresHandler.scoreTrade(receiver);
	}

	private String validateCoupon(ExchangeScoreDto exchangeScoreDto) throws Exception {
		CouponValidateResultForExchangeDto couponValidDto = cbpProxy.validateForExchange(exchangeScoreDto.getCouponCode(), exchangeScoreDto.getMcMemberCode());
		if (!couponValidDto.isCanUse()) {
			throw new Exception("优惠卷不可以使用,"+couponValidDto.getMessage().getMessage());
		}
		if (!ExchangeType.SCORE.equals(couponValidDto.getExchangeType())) {
			throw new Exception("优惠卷不可以兑换积分");
		}
		return couponValidDto.getBussinessId();
	}
	
	
}
