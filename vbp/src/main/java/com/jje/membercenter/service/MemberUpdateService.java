package com.jje.membercenter.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.coupon.coupon.CouponMessageDto;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.upgrade.UpgradeIssueDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.UpdateBasicInfoResult;
import com.jje.dto.membercenter.UpgradeMemberDto;
import com.jje.dto.payment.PayResultForBizDto.PaymentType;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.vbp.handler.CbpHandler;

@Service
public class MemberUpdateService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberUpdateService.class);
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WebMemberRepository webMemberRepository;

    @Autowired
    private CRMMembershipRepository crmMembershipRepository;
    @Autowired
    private CbpHandler cbpHandler;
    
    @Autowired
	private MemberCardOrderRepository memberCardOrderRepository;
    @Autowired
    private MemberXmlRepository memberXmlRepository;
    
    public UpdateBasicInfoResult validMemberRepeat(MemberBasicInfoDto basicInfoDto, String memberCode) {
        Member tempMember = new Member();
        tempMember.setMemberCode(memberCode);
        boolean cellFlag = validMemberRepeat(tempMember, basicInfoDto.getCell());
        boolean emailFlag = validMemberRepeat(tempMember, basicInfoDto.getEmail());
        if (cellFlag && emailFlag) {
            return UpdateBasicInfoResult.EXISTS_EMAIL_AND_PHONE;
        }
        if (cellFlag) {
            return UpdateBasicInfoResult.EXISTS_PHONE;
        }
        if (emailFlag) {
            return UpdateBasicInfoResult.EXISTS_EMAIL;
        }
        return null;
    }

    private boolean isWebVerifyExists(String menName) {
        return webMemberRepository.queryRegisterByCellphoneOrEmail(menName) != null;
    }

    private boolean validMemberRepeat(Member member, String userName) {
        member.setUserName(userName);
        List<MemberVerfy> members = memberRepository.getRepeatMenName(member);
        if (!CollectionUtils.isEmpty(members))
            return true;
        else
            return isWebVerifyExists(member.getUserName());
    }

    public CRMResponseDto updateVIPCardInfo(MemberUpdateDto memberUpdateDto) throws Exception {
        LOG.warn("updateVIPCardInfo --------------" + JaxbUtils.convertToXmlString(memberUpdateDto));
        upgradeWithCoupon(memberUpdateDto);
        CRMResponseDto crmResponseDto = crmMembershipRepository.updateVIPCardInfo(memberUpdateDto);
        try {
            if (crmResponseDto.isExecSuccess()) {
            	memberRepository.updateMemberInfoAndCard(crmResponseDto.getMember());
            	
                UpgradeIssueDto upgradeIssueDto = new UpgradeIssueDto();
                upgradeIssueDto.setCardType(NewCardType.valueOfCode(memberUpdateDto.getNewCardType()));
                upgradeIssueDto.setUpgradeOrigin(RegistChannel.valueOf(memberUpdateDto.getChannel()));
                upgradeIssueDto.setMcMemberCode(memberUpdateDto.getMcMemberCode());
                upgradeIssueDto.setCouponCode(memberUpdateDto.getCouponCode());
                CouponSysIssueResult sysIssueResult = cbpHandler.upgradeIssuedCoupon(upgradeIssueDto);
                //发放积分
                if(StringUtils.isNotBlank(memberUpdateDto.getCouponCode())){
                	cbpHandler.addScore(memberUpdateDto.getCouponCode(), memberUpdateDto.getMcMemberCode());
                }
                LOG.warn(sysIssueResult.getResponseMessage().getMessage());
            }
        } catch (Exception e) {
            LOG.error(String.format("upgrade card issue coupon error memberUpdateDto = %s", memberUpdateDto.toString()), e);
        }
        return crmResponseDto;
    }
    
    public CRMResponseDto freeUpgradeVIPCardInfo(UpgradeMemberDto upgradeMemberDto,String orderNo,String uuid,String couponCode) throws Exception{
    	LOG.warn("updateVIPCardInfo --------------   {}",upgradeMemberDto.getMemberUpdateDto());
    	//调用CRM升级
    	CRMResponseDto crmdto = updateVIPCardInfo(upgradeMemberDto.getMemberUpdateDto());
    	if(!crmdto.isExecSuccess()){
    	   LOG.error("updateVIPCardInfo fail {}",crmdto.getRetmsg());	
    	   return crmdto;
    	}
      try{	
    	try{
    	//更新地址
    	if(!CollectionUtils.isEmpty(upgradeMemberDto.getMemberAddressDto().getResults())){
    	   String rcode = crmMembershipRepository.saveMemberAddress(upgradeMemberDto.getMemberAddressDto().getResults());
    	   if(rcode.equals("00002")){
    		 LOG.error("saveMemberAddress  fail {}",upgradeMemberDto.getMemberAddressDto().getResults());	  
    	   }
    	}
    	//更新发票信息
    	if(!CollectionUtils.isEmpty(upgradeMemberDto.getUpdateAddressDto())){
    	   String rcode=crmMembershipRepository.updateMemberAddress(upgradeMemberDto.getUpdateAddressDto());
    	   if(rcode.equals("00002")){
    		 LOG.error("updateMemberAddress  fail {}",upgradeMemberDto.getUpdateAddressDto());	  
    	   }
     	}
    	}catch (Exception e) {
     	  LOG.error("系统异常  {},调用dto为 {} ",e,upgradeMemberDto);
		}
    	//更新订单状态
    	MemberCardOrder cardOrder = memberCardOrderRepository.getMemberCardOrder(orderNo);
    	if(null==cardOrder){
    	   LOG.error("cardOrder is null");
        }
    	// 更新订单信息
    	MemberCardOrder memberCardOrder = new MemberCardOrder();
    	memberCardOrder.setOrderNo(orderNo);
    	memberCardOrder.setPayStatus(MemberCardOrder.PAY_STATUS_SUCC);
    	memberCardOrder.setCardNo(upgradeMemberDto.getCardNo());
    	memberCardOrder.setMcMemberCode(cardOrder.getMcMemberCode()); 
    	memberCardOrder.setPaymentVender(PaymentType.COUPON.name());
    	memberCardOrder.setPayType(PaymentType.COUPON.name());
    	memberCardOrder.setPayTime(new Date());
    	memberCardOrderRepository.updateStatus(memberCardOrder);
		//更新优惠券
    	cbpHandler.updateCouponStatus(upgradeMemberDto.getMemberUpdateDto().getCouponCode(), upgradeMemberDto.getMemberUpdateDto().getMcMemberCode(),orderNo);
      }catch (Exception e) {
    	  LOG.error("系统异常  {},调用dto为 {} ",e,upgradeMemberDto);
    	  cbpHandler.unlockCoupon(couponCode);
    	  MemberXml mx = new MemberXml();
		  mx.setId(uuid);
		  mx.setCallBackFlag("F");
		  memberXmlRepository.updateCallBackFlagByBean(mx);
		}
    	return crmdto;
    }

    private void upgradeWithCoupon(MemberUpdateDto dto){
    	try{
    		String couponCode = dto.getCouponCode();
    		if(StringUtils.isNotBlank(couponCode)){
    			CouponMessageDto couponMessageDto = cbpHandler.queryCouponRule(couponCode);
    			dto.setCouponRuleID(couponMessageDto.getRuleID());
    		}
    	}catch (Exception e) {
    		LOG.warn("updateVIPCardInfo upgradeWithCoupon() error",e);
		}
	}
}
