package com.jje.vbp.mall.service;

import java.util.*;

import com.jje.dto.vbp.mall.DeductResDto;
import com.jje.dto.vbp.mall.QueryScoreDeductDto;
import com.jje.email.EmailProvider;
import com.jje.email.domain.EmailMessage;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.remote.crm.datagram.request.MemberQueryScoreReq;
import com.jje.membercenter.remote.crm.datagram.response.MemberQueryScoreRes;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.CouponSysIssueResult.ResponseMessage;
import com.jje.dto.coupon.rule.ExchangeChannel;
import com.jje.dto.membercenter.SSORedeemDto;
import com.jje.dto.membercenter.score.ExchangeType;
import com.jje.dto.vbp.coupon.MallScoreDeductDto;
import com.jje.membercenter.domain.MemberScoreRedeem;
import com.jje.membercenter.domain.MemberScoreRedeemRepository;
import com.jje.membercenter.persistence.MemberScoreTradeLogRepository;
import com.jje.membercenter.persistence.MemberScoreTradeOrderRepository;
import com.jje.membercenter.remote.crm.support.CrmPassage;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.membercenter.score.domain.MallScoreDeductResult;
import com.jje.membercenter.score.domain.MemberScoreTrade;
import com.jje.membercenter.score.domain.TradeStatus;
import com.jje.membercenter.service.MemberService;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.mall.domain.DeductReceived;
import com.jje.vbp.mall.domain.DeductReceived.Body;
import com.jje.vbp.mall.domain.DeductReceived.Head;
import com.jje.vbp.mall.domain.DeductReturned;
import com.jje.vbp.mall.domain.MallScoreDeductResultCode;
import com.jje.vbp.mall.exception.MallScoreDeductException;

@Component
public class MemberMallService {

	private static final Logger logger = LoggerFactory.getLogger(MemberMallService.class);

	@Autowired
	private CrmPassage crm;
	
	@Autowired
    private MemberScoreRedeemRepository memberScoreRedeemRepository;
	
	@Autowired
	private MemberScoreTradeOrderRepository memberScoreTradeOrderRepository;
	
	@Autowired
	private MemberScoreTradeLogRepository memberScoreTradeLogRepository;
	
	@Autowired
   private MemberService memberService;
	
	@Autowired
	private CbpHandler cbpHandler;

    @Autowired
    private MemberHandler memberHandler;

    @Autowired
    private EmailProvider emailProvider;

    @Autowired
    private MemberRepository memberRepository;


    private static final String  EMAIL_ADDRESS_KEY  = "JJE_MEMBER_SCORE_EXCHANGE";

	public MallScoreDeductResult mallScoreDeduct(MallScoreDeductDto mallScoreDeductDto) {
		MemberScoreTrade trade = null;
		try {
			trade = addMemberScoreTrade(mallScoreDeductDto);
			return processMemberScoreTrade(mallScoreDeductDto, trade);
		} catch (MallScoreDeductException e) {
			logger.error(e.getMessage() + ";参数:" + mallScoreDeductDto, e);
			if(e.getResultCode().isBlockable()){
				 updateMemberScoreTrade(trade, TradeStatus.BLOCK, e.getMessage());
			}else{
				 updateMemberScoreTrade(trade, TradeStatus.FAIL, e.getMessage());
			}
			return new MallScoreDeductResult(e.getResultCode());
		} catch (Exception e) {
			logger.error("206积分扣减失败：积分扣减异常{}", mallScoreDeductDto , e);
			updateMemberScoreTrade(trade, TradeStatus.BLOCK, e.getMessage());
			return new MallScoreDeductResult(MallScoreDeductResultCode.ERROR_206);
		}
		
	}

	private MallScoreDeductResult processMemberScoreTrade(MallScoreDeductDto mallScoreDeductDto, MemberScoreTrade trade)
			throws MallScoreDeductException {
		String memberId = getMemberId(mallScoreDeductDto.getMcMemberCode());
		checkMemberScore(getMemberScoreByMemberId(memberId), mallScoreDeductDto.getPoints());
		
		DeductReceived received = new DeductReceived(
				new Head("30015", DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"), "JJ000"),
				new Body(memberId, mallScoreDeductDto.getProductId(), mallScoreDeductDto.getPoints(), 
						DateUtils.formatDate(mallScoreDeductDto.getTransDate()==null?new Date():mallScoreDeductDto.getTransDate(), "MM/dd/yyyy HH:mm:ss"), 
						mallScoreDeductDto.getOrderNum(), mallScoreDeductDto.getCallId(), mallScoreDeductDto.getPartnerCard(), mallScoreDeductDto.getSid(),
						mallScoreDeductDto.getUname(), mallScoreDeductDto.getUmobile(), null));
		DeductReturned returned = mallScoreCRMDeduct(received);
		
		if ("00201".equals(returned.getHead().getRetcode())) {
			updateMemberScoreTrade(trade, TradeStatus.CRM_DEDUCT_SUCCESS, JaxbUtils.convertToXmlString(returned));
			MallScoreDeductResult MallScoreDeductResult = deductAfterCRMSuccess(mallScoreDeductDto, trade, memberId);
			return MallScoreDeductResult;
		} else{
			updateMemberScoreTrade(trade, TradeStatus.CRM_FAIL, JaxbUtils.convertToXmlString(returned));
			if ("00202".equals(returned.getHead().getRetcode())) {
				logger.error("202积分扣减失败：积分不够。参数：{}；返回：{}", mallScoreDeductDto, JaxbUtils.convertToXmlString(returned));
				throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_202);
			} else if ("00204".equals(returned.getHead().getRetcode())) {
				logger.error("204积分扣减失败：无此用户。参数：{}；返回：{}", mallScoreDeductDto, JaxbUtils.convertToXmlString(returned));
				throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_204);
			} else {
				logger.error("205积分扣减失败：其他因素。参数：{}；返回：{}", mallScoreDeductDto, JaxbUtils.convertToXmlString(returned));
				throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_205);
			}
		}
	}

	private MallScoreDeductResult deductAfterCRMSuccess(MallScoreDeductDto mallScoreDeductDto, MemberScoreTrade trade, String memberId)
			throws MallScoreDeductException {
		SSORedeemDto redeemDto = null;
		try {
			redeemDto = mallScoreDeductDto2SSORedeemDto(mallScoreDeductDto, memberId);
			if(null != mallScoreDeductDto.getChannel() && mallScoreDeductDto.getChannel().equals(ExchangeChannel.JJMALL)){
			     storeScoreRedeem(redeemDto);
			     updateMemberScoreTrade(trade, TradeStatus.STORE_SCORE_REDEEM_SUCCESS, JaxbUtils.convertToXmlString(redeemDto));
			}
		} catch (Exception e) {
			updateMemberScoreTrade(trade, TradeStatus.STORE_SCORE_REDEEM_SUCCESS,
					redeemDto == null ? "SSORedeemDto转换异常" : JaxbUtils.convertToXmlString(redeemDto));
			logger.error("207积分扣减失败：积分记录异常{}", mallScoreDeductDto, e);
			throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_207);
		}
		
		
		CouponSysIssueResult couponSysIssueResult = cbpHandler.triggerCouponExchangeIssue(mallScoreDeductDto.toExchangeIssueDto());
		if(!ResponseMessage.SUCCESS.equals(couponSysIssueResult.getResponseMessage())){ 
			logger.error("积分兑换未发优惠券:参数{};结果{}", mallScoreDeductDto, couponSysIssueResult);
			updateMemberScoreTrade(trade, TradeStatus.TRIGGER_COUPON_FAIL, JaxbUtils.convertToXmlString(couponSysIssueResult));
		}else{
			updateMemberScoreTrade(trade, TradeStatus.TRIGGER_COUPON_SUCCESS, JaxbUtils.convertToXmlString(couponSysIssueResult));
		}
		
		
		MallScoreDeductResult MallScoreDeductResult = new MallScoreDeductResult(MallScoreDeductResultCode.SUCCESS_201);
		updateMemberScoreTrade(trade, TradeStatus.SUCCESS, JaxbUtils.convertToXmlString(MallScoreDeductResult));
		return MallScoreDeductResult;
	}
	
	public MallScoreDeductResult retryMallScoreDeduct(MallScoreDeductDto mallScoreDeductDto) {

		MemberScoreTrade orderForRetry = memberScoreTradeOrderRepository.getMemberScoreTradeOrderForRetry(mallScoreDeductDto
				.getOrderNum());

		if (orderForRetry == null) {
			return mallScoreDeduct(mallScoreDeductDto);
		} else {
			if(TradeStatus.SUCCESS.equals(orderForRetry.getStatus())){
				return new MallScoreDeductResult(MallScoreDeductResultCode.SUCCESS_201);
			}else{
				return retryMallScoreDeduct(orderForRetry, mallScoreDeductDto);
			}
		}
	}

	private MallScoreDeductResult retryMallScoreDeduct(MemberScoreTrade trade, MallScoreDeductDto mallScoreDeductDto) {
		try {
			String memberId = getMemberId(trade.getMcMemberCode());
			QueryScoreDeductDto queryDto = new QueryScoreDeductDto();
			queryDto.setMemberId(memberId);
			queryDto.setOrderId(trade.getTrdOrderNo());
			DeductResDto queryScoreDeduct = queryScoreDeduct(queryDto);
			
			if(queryScoreDeduct == null){
				return processMemberScoreTrade(mallScoreDeductDto, trade);
			}
			
			if(!queryScoreDeduct.success()){
				logger.error("210积分扣减失败：该订单数据异常{}", trade);
				return new MallScoreDeductResult(MallScoreDeductResultCode.ERROR_213); 
			}
			
			if("Rejected".equalsIgnoreCase(queryScoreDeduct.getStatus())){
				throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_214);
			}else if("Processed".equalsIgnoreCase(queryScoreDeduct.getStatus())){
				updateMemberScoreTrade(trade, TradeStatus.CRM_DEDUCT_SUCCESS, JaxbUtils.convertToXmlString(mallScoreDeductDto));
				return deductAfterCRMSuccess(mallScoreDeductDto, trade, memberId);
			}else{
				throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_206);
			}
			
		} catch (MallScoreDeductException e) {
			logger.error(e.getMessage() + ";参数:" + trade, e);
			if (!e.getResultCode().isBlockable()) {
				updateMemberScoreTrade(trade, TradeStatus.FAIL, e.getMessage());
			}
			return new MallScoreDeductResult(e.getResultCode());
		} catch (Exception e) {
			logger.error("206积分扣减失败：积分扣减异常{}", trade, e);
			updateMemberScoreTrade(trade, TradeStatus.BLOCK, e.getMessage());
			return new MallScoreDeductResult(MallScoreDeductResultCode.ERROR_206);
		}

	}

		public DeductResDto queryScoreDeduct(QueryScoreDeductDto queryDto) {
        try{
            MemberQueryScoreReq req = new MemberQueryScoreReq(queryDto.getMemberId(),queryDto.getOrderId());
            MemberQueryScoreRes res = req.send();
            DeductResDto deductResDto = res.toDto();
            if(deductResDto.success()){
                return deductResDto;
            }
        }catch (Exception e){
            logger.error("积分扣减记录查询失败",queryDto,e);
        }
        return null;
    }

	private DeductReturned mallScoreCRMDeduct(DeductReceived received) throws MallScoreDeductException {
		DeductReturned returned;
		try {
			returned = crm.sendToType(received, DeductReturned.class);
			if (returned.getHead().getRetcode() == null) {
				logger.error("CRM积分扣减失败,返回空状态：积分扣减参数{}", JaxbUtils.convertToXmlString(received));
			}
			return returned;
		} catch (Exception e) {
			logger.error(String.format("205积分扣减失败：其他因素。参数DeductReceived：%s", JaxbUtils.convertToXmlString(received)), e);
			throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_205);
		}
	}

	private void checkMemberScore(Integer crmCurrentScore, String points) throws MallScoreDeductException {
		int result = 0;
		try {
			result = crmCurrentScore.compareTo(new Integer(points));
		} catch (Exception e) {
			logger.error(String.format("202积分扣减失败：积分参数不合法。参数memberScoreByMemberId：%s,points:%s", crmCurrentScore, points), e);
			throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_202);
		}
		if (result < 0) {
			logger.error(String.format("202积分扣减失败：积分不够。参数memberScoreByMemberId：%s,points:%s", crmCurrentScore, points));
			throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_202);
		}
	}

	private Integer getMemberScoreByMemberId(String memberId) throws MallScoreDeductException {
		try {
			return memberHandler.getMemberScore(memberId);
		} catch (Exception e) {
			logger.error("202积分扣减失败：积分不够。参数memberId:{}", memberId, e);
			throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_202);
		}
	}

	private String getMemberId(String mcMemberCode) throws MallScoreDeductException {
		String memberId=memberService.getMemberCodeByMcMemberCode(mcMemberCode);
		
		if( memberId == null){
			logger.error("204积分扣减失败：无此用户。参数：{}", mcMemberCode);
			throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_204);
		}
		return memberId;
	}

	private void updateMemberScoreTrade(MemberScoreTrade trade, TradeStatus status, String remark) {
		try {
			trade.setStatus(status);
			trade.setLastUpdateTime(new Date());
			trade.setRemark(remark);
			//更新日志
			memberScoreTradeOrderRepository.updateMemberScoreTradeOrder(trade);
			memberScoreTradeLogRepository.addMemberScoreTradeLog(trade);
		} catch (Exception e) {
			logger.error(String.format("updateMemberScoreTrade(%s,%s,%s) error!", trade, status,remark), e);
		}
	}



	private MemberScoreTrade addMemberScoreTrade(
			MallScoreDeductDto mallScoreDeductDto) throws MallScoreDeductException {
		MemberScoreTrade trade;
		try {
			trade = new MemberScoreTrade(UUID.randomUUID().toString().replace("-","").toUpperCase(), 
					mallScoreDeductDto.getOrderNum(),
					new Date(),
					mallScoreDeductDto.getMcMemberCode(), 
					mallScoreDeductDto.getProductId(), 
					mallScoreDeductDto.getPoints(),
					TradeStatus.RECEIVE_REQUEST,
					null,
					ExchangeType.mallScoreExchange);
			trade.setRemark(JaxbUtils.convertToXmlString(mallScoreDeductDto));
			//记录日志
			memberScoreTradeOrderRepository.addMemberScoreTradeOrder(trade);
			memberScoreTradeLogRepository.addMemberScoreTradeLog(trade);
		} catch (DuplicateKeyException e) {
			logger.error(String.format("MemberScoreTrade(%s) already exist!", mallScoreDeductDto), e);
			throw new MallScoreDeductException(MallScoreDeductResultCode.ERROR_212);
		} catch (Exception e) {
			logger.error(String.format("addMemberScoreTrade(%s) error!", mallScoreDeductDto), e);
			return null;
		}
		return trade;
	}

	
	
	private SSORedeemDto received2SSORedeemDto(DeductReceived received) {
		SSORedeemDto sso = new SSORedeemDto();
		sso.setKey(received.getBody().getKey());
		sso.setMemid(received.getBody().getMemberId());
		sso.setOrderid(received.getBody().getOrderNum());
		sso.setPdcode(received.getBody().getProductId());
		sso.setScore(received.getBody().getPoints());
		String td = received.getBody().getTransDate();
		Date temp = DateUtils.parseDate(td, "MM/dd/yyyy hh:mm:ss");
		sso.setTime(DateUtils.formatDate(temp, "yyyyMMddhhmmss"));
		return sso;
	}
	
	private SSORedeemDto mallScoreDeductDto2SSORedeemDto(MallScoreDeductDto mallScoreDeductDto, String memberId) {
		SSORedeemDto sso = new SSORedeemDto();
		sso.setKey(null);
		sso.setMemid(memberId);
		sso.setOrderid(mallScoreDeductDto.getOrderNum());
		sso.setPdcode(mallScoreDeductDto.getProductId());
		sso.setScore(mallScoreDeductDto.getPoints());
		String td = DateUtils.formatDate(mallScoreDeductDto.getTransDate(), "MM/dd/yyyy hh:mm:ss");
		Date temp = DateUtils.parseDate(td, "MM/dd/yyyy hh:mm:ss");
		sso.setTime(DateUtils.formatDate(temp, "yyyyMMddhhmmss"));
		return sso;
	}
	
	private void storeScoreRedeem(SSORedeemDto ssoRedeemDto) throws Exception {
		MemberScoreRedeem memberScoreRedeem = new MemberScoreRedeem(ssoRedeemDto);
		memberScoreRedeemRepository.addScoreRedeem(memberScoreRedeem);
	}



	public void scoreDeductTimeoutByMinute(int timeoutLimitMinutes) {
        MemberScoreTrade memberScoreTrade = new MemberScoreTrade();
        memberScoreTrade.setStatus(TradeStatus.BLOCK);
        memberScoreTrade.setLastUpdateTime(DateUtils.addMinutes(new Date() ,0 - timeoutLimitMinutes));
        this.sendEmailWithUpdateTradeStatus(memberScoreTradeOrderRepository.queryMemberScoreTradeOrderForTimeout(memberScoreTrade));

	}

    private void sendEmailWithUpdateTradeStatus(List<MemberScoreTrade> memberScoreTrades) {

        if(CollectionUtils.isEmpty(memberScoreTrades)){
            return ;
        }
        Map<String,Member> cacheMember = new HashMap<String,Member>();
        EmailMessage email = new EmailMessage();
        email.setSubject("Score Exchange ERROR：" + DateUtils.formatDate(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
        StringBuffer errorMsg = new StringBuffer("<table>");
        for(MemberScoreTrade trade : memberScoreTrades){
            errorMsg.append("<tr>");
            if(!cacheMember.containsKey(trade.getMcMemberCode())){
               cacheMember.put(trade.getMcMemberCode(), memberRepository.getMemberByMcMemberCode(trade.getMcMemberCode()));
            }
            Member tempMember = cacheMember.get(trade.getMcMemberCode());
            if (tempMember != null){
                errorMsg.append("<td>会员信息 {手机：" + tempMember.getCellPhone());
                errorMsg.append("mem_num：" + tempMember.getMemberCode());
                errorMsg.append("邮箱：" + tempMember.getEmail());
                errorMsg.append("卡号：" + tempMember.getCardNo());
                errorMsg.append("姓名：" + tempMember.getFullName());
                errorMsg.append("mem_id：" + tempMember.getMemberID());
                errorMsg.append("}</td>");
            }
            errorMsg.append("<td>出错订单详情： " + trade +"</td></tr>");
            trade.setStatus(TradeStatus.FAIL_NEED_MANUAL_PROCESSING);
            trade.setLastUpdateTime(new Date());
            memberScoreTradeOrderRepository.updateMemberScoreTradeOrder(trade);
        }
        errorMsg.append("</table>");
        try{
            email.setContent(errorMsg.toString());
            emailProvider.sendEmailByAddressKey(email,EMAIL_ADDRESS_KEY);
        }catch (Exception e) {
            logger.error("发送邮件失败" + errorMsg.toString() ,e);
        }
    }
	
}
