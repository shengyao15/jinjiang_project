package com.jje.vbp.handler;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.coupon.rule.issue.register.RegisterType;
import com.jje.dto.ebp.BusinessEvent;
import com.jje.dto.ebp.Channel;
import com.jje.dto.ebp.MemberCardType;
import com.jje.dto.ebp.Module;
import com.jje.dto.ebp.event.member.ActivateMemberEventDto;
import com.jje.dto.ebp.event.member.BuyCardEventDto;
import com.jje.dto.ebp.event.member.CompleteInfoEventDto;
import com.jje.dto.ebp.event.member.LoginEventDto;
import com.jje.dto.ebp.event.member.MemberAirLineCompany;
import com.jje.dto.ebp.event.member.RegisterEventDto;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.vbp.handler.proxy.EbpProxy;

@Component
public class EbpHandler {

	@Autowired
	private EbpProxy ebpProxy;
	
	@Autowired
	private CbpHandler cbpHandler;
	
	//激活事件
	public void sendActivateEvent(String mcMemberCode, RegistChannel channel) {
		this.sendActivateEvent(mcMemberCode, channel.name(),null);
	}
	public void sendActivateEvent(String mcMemberCode, String channel,String registTag) {
		ActivateMemberEventDto eventDto = new ActivateMemberEventDto(); 
		eventDto.setEventChannel(convertRegistChannel(channel));
		eventDto.setEventCode(BusinessEvent.MEMBER_ACTIVATE);
		eventDto.setEventMcMemberCode(mcMemberCode);
		eventDto.setEventModule(Module.MEMEBERCENTER);
		ebpProxy.sendEvent(eventDto);
		
		cbpHandler.activingIssueCoupon(channel, mcMemberCode,registTag);
	}
	
	//买卡事件
	public void sendBuyCardEvent(String mcMemberCode, String cardType, String channel) {
		BuyCardEventDto eventDto = new BuyCardEventDto(); 
		eventDto.setEventChannel(convertRegistChannel(channel));
		eventDto.setEventModule(Module.MEMEBERCENTER);
		eventDto.setEventCode(BusinessEvent.MEMBER_CARD_UPGRADE);
		eventDto.setEventMcMemberCode(mcMemberCode);
		eventDto.setCardType(convertCardType(cardType));
		ebpProxy.sendEvent(eventDto);
	}
	
	//完善信息事件
	public void sendCompleteInfoEvent(String mcMemberCode, String channel) {
        sendCompleteInfoEvent(mcMemberCode, channel,null);
	}

    public void sendCompleteInfoEvent(String mcMemberCode, String channel,String tag) {
        CompleteInfoEventDto eventDto = new CompleteInfoEventDto();
        eventDto.setEventChannel(convertRegistChannel(channel));
        eventDto.setEventModule(Module.MEMEBERCENTER);
        eventDto.setEventCode(BusinessEvent.MEMBER_COMPLETE);
        eventDto.setEventMcMemberCode(mcMemberCode);
        ebpProxy.sendEvent(eventDto);
        cbpHandler.registerJJCardIssue(channel, mcMemberCode,tag);
    }
	
	//登录事件
	public void sendAppLoginEvent(String mcMemberCode, boolean isTempMember) {
		sendLoginEvent(mcMemberCode, Channel.MOBILE, isTempMember);
	}
	public void sendWWWLoginEvent(String mcMemberCode, boolean isTempMember) {
		sendLoginEvent(mcMemberCode, Channel.WEBSITE_WWW, isTempMember);
	}
	private void sendLoginEvent(String mcMemberCode, Channel eventChannel, boolean isTempMember) {
		LoginEventDto eventDto = new LoginEventDto(); 
		eventDto.setEventChannel(eventChannel);
		eventDto.setEventCode(BusinessEvent.MEMBER_LOGIN);
		eventDto.setEventModule(Module.MEMEBERCENTER);
		eventDto.setEventMcMemberCode(mcMemberCode);
		eventDto.setTempMember(isTempMember);
		ebpProxy.sendEvent(eventDto);
	}
	
	//注册事件
	public void sendRegisterEvent(String mcMemberCode, String channel, String cardType, com.jje.dto.membercenter.MemberAirLineCompany memberAirLineCompany) {
		RegisterEventDto eventDto = new RegisterEventDto(); 
		eventDto.setEventChannel(convertRegistChannel(channel));
		eventDto.setEventCode(BusinessEvent.MEMBER_REGIST);
		eventDto.setEventModule(Module.MEMEBERCENTER);
		eventDto.setEventMcMemberCode(mcMemberCode);
		if (StringUtils.isNotEmpty(cardType)) {
			MemberCardType cardTypeEnum;
			try {
				cardTypeEnum = MemberCardType.valueOf(cardType);
				eventDto.setCardType(cardTypeEnum);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (memberAirLineCompany != null) {
			MemberAirLineCompany airLineCompany;
			try {
				airLineCompany = MemberAirLineCompany.valueOf(memberAirLineCompany.toString());
				eventDto.setPartnerOrigin(airLineCompany);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ebpProxy.sendEvent(eventDto);
	}
	public void sendJJCardRegisterEvent(String mcMemberCode, String channel, String memberType) {
		this.sendRegisterEvent(mcMemberCode, channel, convertCardType(memberType).toString(), null);
	}

	private Channel convertRegistChannel(String channel) {
		RegistChannel rc;
		try {
			rc = RegistChannel.valueOf(channel);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		switch (rc) {
		case Website:
			return Channel.WEBSITE;
		case Mobile:
			return Channel.MOBILE;
		case Ikamobile:
			return Channel.MOBILE_IKAMOBILE;
		case Jiudiankong:
			return Channel.MOBILE_JIUDIANKONG;
		case Store:
			return Channel.STORE;
		case Website_partner:
			return Channel.WEBSITE_PARTNER;
		case CallCenter:
			return Channel.CALLCENTER;
		case Email:
			return Channel.EMAIL;
		case JDDR:
			return Channel.JDDR;
		case TENPAY:
			return Channel.TENPAY;
		default:
			break;
		}
		return null;
	}

	private MemberCardType convertCardType(String cardType) {
		NewCardType cardTypeEnum;
		try {
			cardTypeEnum = NewCardType.valueOfCode(cardType);
			switch (cardTypeEnum) {
			case SILVER_CARD:
				return MemberCardType.PRESENT;
			case GOLD_CARD:
				return MemberCardType.ENJOY;
			case PLATINUM_CARD:
				return MemberCardType.ENJOY2;
			case BLACK_CARD:
				return MemberCardType.ENJOY8;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
