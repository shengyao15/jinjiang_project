package com.jje.vbp.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jje.dto.coupon.coupon.*;
import com.jje.dto.membercenter.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.RestClient;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.activation.ActivationIssueDto;
import com.jje.dto.coupon.issue.exchange.ExchangeIssueDto;
import com.jje.dto.coupon.issue.exchange.ValidateIssueResult;
import com.jje.dto.coupon.issue.register.RegisterIssueDto;
import com.jje.dto.coupon.issue.upgrade.UpgradeIssueDto;
import com.jje.dto.coupon.rule.ExchangeChannel;
import com.jje.dto.coupon.rule.IssueEvent;
import com.jje.dto.coupon.rule.issue.register.AcitivityTagEnum;
import com.jje.dto.coupon.rule.issue.register.RegisterType;
import com.jje.dto.membercenter.score.ScoreAnswerDto;
import com.jje.dto.membercenter.score.ScoreReceiverDto;
import com.jje.membercenter.remote.handler.ScoresHandler;
import com.jje.vbp.handler.proxy.CbpProxy;

@Component
public class CbpHandler {
	
	private static Logger logger = LoggerFactory.getLogger(CbpHandler.class);

	@Value(value = "${cbp.url}")
	private String cbpUrl;
	
	@Autowired
	private CbpProxy cbpProxy;

	@Autowired
	private RestClient restClient;

	@Autowired
	private ScoresHandler scoresHandler;

	public CouponSysIssueResult quickRegisterIssue(RegistChannel channel,String mcMemberCode,String tag) {
		logger.info("do quickRegisterIssue, params: channel {}, mcMemberCode {}",channel,mcMemberCode);
		RegisterIssueDto issueDto = new RegisterIssueDto();
		issueDto.setEventTag(tag);
		issueDto.setOrigin(channel);
		issueDto.setType(RegisterType.QUICK_REGISTER);
		issueDto.setMcMemberCode(mcMemberCode);
		issueDto.setIssueEvent(IssueEvent.REGISTER);
		return cbpProxy.issueCoupon(issueDto);
	}

    public CouponSysIssueResult registerJJCardIssue(String channel, String mcMemberCode,String tag) {
        RegisterIssueDto dto = new RegisterIssueDto();
        if(StringUtils.isNotBlank(channel)) {
            dto.setOrigin(RegistChannel.valueOf(channel));
        }
        dto.setType(RegisterType.SILVER_CARD);
        dto.setMcMemberCode(mcMemberCode);
        dto.setIssueEvent(IssueEvent.REGISTER);
        dto.setEventTag(tag);
        return issueCoupon(dto);
    }
    
    public CouponSysIssueResult recommendRegisterIssue(String channel, String mcMemberCode,AcitivityTagEnum activityTag) {
    	RegisterIssueDto dto = new RegisterIssueDto();
    	if(StringUtils.isNotBlank(channel)) {
    		dto.setOrigin(RegistChannel.valueOf(channel));
    	}
    	dto.setType(RegisterType.SILVER_CARD);
    	dto.setIssueEvent(IssueEvent.REGISTER);
    	dto.setEventTag(null);
    	dto.setActivityTag(activityTag);
    	dto.setMcMemberCode(mcMemberCode);
    	return issueCoupon(dto);
    }
	
	public CouponSysIssueResult registerJJCardIssue(String channel, 
    		String mcMemberCode,
    		String tag,
    		MemberAirLineCompany partner) {
        RegisterIssueDto dto = new RegisterIssueDto();
        if(StringUtils.isNotBlank(channel)) {
            dto.setOrigin(RegistChannel.valueOf(channel));
        }
        dto.setType(RegisterType.SILVER_CARD);
        dto.setMcMemberCode(mcMemberCode);
        dto.setIssueEvent(IssueEvent.REGISTER);
        dto.setEventTag(tag);
        dto.setPartnerOrigin(partner);
        return issueCoupon(dto);
    }

	public CouponSysIssueResult registerIssue(MemberDto member) {
		CouponSysIssueResult result = null;
		try {
			result = issueCoupon(wrapRegisterIssueDto(member));
		} catch (Exception e) {
			logger.error(String.format(" registerIssue error , member info :[mcMember :%s,registerSource :%s,cardType:%s,scoreType:%s",
							member.getMcMemberCode(),
							member.getRegisterSource(), member.getCardType(),
							member.getScoreType()));
		}
		return result;
	}

	private CouponSysIssueResult issueCoupon(RegisterIssueDto issueDto) {
		CouponSysIssueResult sysIssueResult = null;
		try {
			sysIssueResult = restClient.post(cbpUrl + "/sysIssue/registerIssue", issueDto,CouponSysIssueResult.class);
		} catch (Exception e) {
			logger.error("CBPHandler.issueCoupon error : {}",JaxbUtils.convertToXmlString(issueDto));
		}
		return sysIssueResult;
	}

	private RegisterIssueDto wrapRegisterIssueDto(MemberDto member) {
		RegisterIssueDto issueDto = new RegisterIssueDto();
		issueDto.setCouponCode(member.getCouponCode());
		issueDto.setIssueEvent(IssueEvent.REGISTER);
		issueDto.setMcMemberCode(member.getMcMemberCode());
		issueDto.setOrigin(RegistChannel.valueOf(member.getRegisterSource()));
		setPartnerOrigin(member, issueDto);
		issueDto.setType(RegisterType.parse(member.getCardType()));
        issueDto.setEventTag(member.getRegisterTag());
		return issueDto;
	}

	private void setPartnerOrigin(MemberDto member, RegisterIssueDto issueDto) {
		if (CollectionUtils.isNotEmpty(member.getCardList())) {
			List<MemberMemCardDto> cards = member.getCardList();
			for (MemberMemCardDto card : cards) {
                MemberAirLineCompany partner =  MemberAirLineCompany.getMemberAirLineCompany(card.getCardTypeCd());
				if (partner != null) {
					issueDto.setPartnerOrigin(partner);
                    return;
				}
			}
		}
	}

	public void registerUseConpon(String mcMemberCode, String couponCode,String orderNo) {
		if(StringUtils.isNotEmpty(couponCode)){
			addScore(couponCode, mcMemberCode);
			updateCouponStatus(couponCode,mcMemberCode,orderNo);
		}
	}

	
	public  void addScore(String couponCode, String mcMemberCode) {
		try {

			CouponMessageDto couponMessage = restClient.get(cbpUrl+ "/coupon/couponRule/" + couponCode,	CouponMessageDto.class);
			if (couponMessage.getCanAddScore()) {
				ScoreAnswerDto result = doAddScore(mcMemberCode);
				logger.warn(" regist add score  repsonse recode :"+ result.getRecode()+"couponCode: "+couponCode);
			}
		} catch (Exception e) {
			logger.error(" CBPHandler   addScore  error  :", e);
		}

	}

	
	public void updateCouponStatus(String couponCode,String mcMemberCode,String orderNo) {
		try {
			String remark = String.format("Use Coupon mcMemberCode = %s", mcMemberCode);
            BatchUpdateDto batchUpdateDto =   new BatchUpdateDto(couponCode,orderNo,remark,mcMemberCode);
			restClient.put(cbpUrl + "/coupon/batchCouponUsed",batchUpdateDto);
		} catch (Exception e) {
			logger.warn(" registSuccess  updateConponStatus  error: ", e);
		}
	}

	
	public void lockCoupon(String couponCode) throws Exception {
		if(StringUtils.isNotEmpty(couponCode))
			restClient.put(cbpUrl + "/coupon/batchCouponLock", new BatchUpdateDto(couponCode));
	}

	
	public void unlockCoupon(String couponCode) {
		if(StringUtils.isNotEmpty(couponCode)){
			try {
				restClient.put(cbpUrl + "/coupon/batchCouponUnUse",new BatchUpdateDto(couponCode));
			} catch (Exception e) {
				logger.warn(" registFail   unlockCoupon  error : ", e);
			}
		}

	}

	private ScoreAnswerDto doAddScore(String mcMemberCode)throws Exception {
		ScoreReceiverDto receiver = new ScoreReceiverDto();
		receiver.setMcMemberCode(mcMemberCode);
		receiver.setTransdate(new Date()); 
		receiver.setProductname(ScoreReceiverDto.ADD_SCORE_RULE_COUPON_REGIST);
		return scoresHandler.scoreTrade(receiver);
	}

    public CouponSysIssueResult upgradeIssuedCoupon(UpgradeIssueDto upgradeIssueDto) {
        return  restClient.post(cbpUrl + "/sysIssue/upgradeIssue", upgradeIssueDto, CouponSysIssueResult.class);
    }
    
    public CouponSysIssueResult activingIssueCoupon(String channel, String mcMemberCode,String registTag) {
    	logger.info("activingIssueCoupon(channel = {}, mcMemberCode = {})", channel, mcMemberCode);
    	CouponSysIssueResult result = null;
    	try {
			result = restClient.post(cbpUrl + "/sysIssue/activationIssue", buildActivationIssueDto(channel, mcMemberCode,registTag), CouponSysIssueResult.class);
		} catch (Exception e) {
			logger.error("activingIssueCoupon error", e);
		}
		return  result;
    }
    
    public CouponValidateResultForRegisterDto verifyRegisterCouponNo(String code) {
    	if (StringUtils.isBlank(code)) {
    		logger.warn("verifyRegisterCouponNo({}) result empty , code:{}", code);
    		return null;
    	}
        try {
        	CouponValidateForRegisterDto couponRegDto = new CouponValidateForRegisterDto();
        	couponRegDto.setCouponCode(code);
        	return restClient.post(cbpUrl + "/coupon/validateForRegister", couponRegDto, CouponValidateResultForRegisterDto.class);
		} catch (Exception e) {
			logger.error("verifyRegisterCouponNo({}) exception", code, e);
		}
		logger.warn("verifyRegisterCouponNo({}) valid fail , code:{}", code);
		return null;
    }

	private ActivationIssueDto buildActivationIssueDto(String channel, String mcMemberCode,String registTag) {
		RegistChannel issueChannel = RegistChannel.valueOf(channel);
		ActivationIssueDto activationIssueDto = new ActivationIssueDto();
    	activationIssueDto.setActivationOrigin(issueChannel);
    	activationIssueDto.setMcMemberCode(mcMemberCode);
        activationIssueDto.setEventTag(registTag);
		return activationIssueDto;
	}
	
	public CouponMessageDto queryCouponRule(String couponCode){
		try{
			return restClient.get(cbpUrl + "/coupon/couponRule/"+couponCode, CouponMessageDto.class);
		}catch(Exception e){
			logger.warn("queryCouponRule , couponCode = " + couponCode);
		}
		return new CouponMessageDto();
	}
	
	
	public CouponSysIssueResult exchangeIssue(ExchangeChannel channel, String mcMemberCode,Integer issueCount,String productId) {
		ExchangeIssueDto  dto = new ExchangeIssueDto();
		dto.setIssueEvent(IssueEvent.EXCHANGE);
		dto.setExchangeChannel(channel);
		dto.setIssueCount(issueCount);
		dto.setMcMemberCode(mcMemberCode);
		dto.setProductId(productId);
		return restClient.post(cbpUrl+"/sysIssue/exchangeIssue", dto, CouponSysIssueResult.class);
	}
	
	public CouponSysIssueResult triggerCouponExchangeIssue(ExchangeIssueDto exchangeIssueDto) {
		CouponSysIssueResult response = null;
		try {
			response = restClient.post(cbpUrl + "/sysIssue/exchangeIssue", exchangeIssueDto, CouponSysIssueResult.class);
		} catch (Exception e) {
			logger.error("CouponResourceProxy.deliverCoupon({})------interface invoker error!!", exchangeIssueDto, e);
		}
		return response;
	}
	
	public ValidateIssueResult validteExchangeIssueRule(ExchangeIssueDto exchangeIssueDto) {
		ValidateIssueResult response = null;
		try {
			response = restClient.post(cbpUrl + "/sysIssue/canIssueForExchange", exchangeIssueDto, ValidateIssueResult.class);
		} catch (Exception e) {
			logger.error("CouponResourceProxy.validteExchangeIssueRule({})------interface invoker error!!", exchangeIssueDto, e);
		}
		return response;
	}

    public CouponValidateResultForUpgradeDto verifyCouponForUpgrade(String code, String mcMemberCode) {
    	return restClient.post(cbpUrl + "/coupon/validateForUpgrade", new CouponValidateForUpgradeDto(code, mcMemberCode), CouponValidateResultForUpgradeDto.class);
    }

}
