package com.jje.vbp.buycard.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.MD5Utils;
import com.jje.data.util.JsonUtils;
import com.jje.dto.coupon.coupon.CouponValidateResultForUpgradeDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemType;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.MemberCardType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.dto.membercenter.MemberXmlDto;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.membercenter.RegistResponse;
import com.jje.dto.membercenter.RegistResponseStatus;
import com.jje.dto.membercenter.UpgradeMemberDto;
import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.dto.membercenter.buyCard.BuyCardRequest;
import com.jje.dto.membercenter.buyCard.BuyCardResponse;
import com.jje.dto.mms.mmsmanage.MessageRespDto;
import com.jje.dto.nbp.SendType;
import com.jje.dto.nbp.ShortMessageDto;
import com.jje.dto.nbp.TemplateModule;
import com.jje.dto.vbp.buyCard.BuyCardDto;
import com.jje.dto.vbp.buyCard.BuyCardResultDto;
import com.jje.dto.vbp.buyCard.GetCardPriceDto;
import com.jje.dto.vbp.buyCard.GetCardPriceResultDto;
import com.jje.membercenter.WebMemberResource;
import com.jje.membercenter.domain.BaseData;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;
import com.jje.membercenter.persistence.WebMemberMapper;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.membercenter.service.MemberUpdateService;
import com.jje.membercenter.service.WebMemberService;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.handler.proxy.NbpProxy;
import com.jje.vbp.member.service.MemberManagerService;
import com.jje.vbp.validate.service.ValidateService;

@Component
public class BuyCardService {

    private final Logger logger = LoggerFactory.getLogger(BuyCardService.class);
	@Autowired
	MemberHandler memberHandler;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    NbpProxy nbpProxy;
    @Value("${member.buycard.initial.password.templateNo}")
    private String Member_Buy_Card_Initial_Password_TemplateNo;
    @Autowired
    private WebMemberMapper webMemberMapper;
    @Autowired
    ValidateService validateService;

    @Autowired
    private CRMMembershipRepository crmMembershipRepository;

    @Autowired
    MemberCardOrderRepository memberCardOrderRepository;
    @Autowired
    private MemberXmlRepository memberXmlRepository;
    @Autowired
    MemberCardOrderRepository memberCardOrderRepositoryImpl;
    @Autowired
    private WebMemberResource webMemberResource;
    @Autowired
    private MemberUpdateService memberUpdateService;
    @Autowired
    private WebMemberRepository webMemberRepository;
    @Autowired
    private CbpHandler cbpHandler;
    @Autowired
    private WebMemberService webMemberService;
    @Autowired
    private MemberManagerService memberManagerService;


    public BuyCardResponse jddrSaleCard(BuyCardRequest buyCardRequest) {
        String errDesc = "";
        String ordertype = "MEMCARD";
        BigDecimal amount = BigDecimal.valueOf(0);
        try {
            // 校验
            errDesc = validateJDDR(buyCardRequest);
            if (StringUtils.isNotBlank(errDesc)) {
                return new BuyCardResponse(BuyCardResponse.FAILURE, errDesc, "");
            }
            MemberRegisterDto memberRegisterDto = new MemberRegisterDto(
                    buyCardRequest);
            MemberInfoDto memberInfoDto = memberRegisterDto.getMemberInfoDto();
            String orderNo = saveOrder(memberRegisterDto, ordertype, amount);
            // 生成密码
            String randomPassword = memberRepository.getRandomPassword();
            memberInfoDto.setPasssword(MD5Utils.generatePassword(randomPassword));
            // 注册买卡
            CRMResponseDto crmResponseDto = crmMembershipRepository.addVIPMembership(memberRegisterDto);
            if (!crmResponseDto.isExecSuccess()) {
                return new BuyCardResponse(BuyCardResponse.FAILURE, "购卡发生系统异常", "");
            }
            String cardNo = queryCardNo(memberRegisterDto, crmResponseDto.getMembid());
            this.sendPwd(memberInfoDto.getMobile(), cardNo, randomPassword);
            updateOrder(orderNo, crmResponseDto.getMcMemberCode(), cardNo, buyCardRequest.getChannel());
            return new BuyCardResponse(BuyCardResponse.SUCCESS, "购卡成功", cardNo);
        } catch (Exception e) {
            logger.error("酒店达人购卡出错:", e);
            return new BuyCardResponse(BuyCardResponse.FAILURE, "购卡发生系统异常", "");
        }

    }

    private void updateOrder(String orderNo, String mcMemberCode,
                             String cardNo, String saleChannel) {
        try {
            // 修改订单表状态位
            MemberCardOrder memberCardOrder = new MemberCardOrder();
            memberCardOrder.setOrderNo(orderNo);
            memberCardOrder.setPayStatus(2);
            memberCardOrder.setMcMemberCode(mcMemberCode);
            memberCardOrder.setCardNo(cardNo);
            memberCardOrder.setPaymentVender(saleChannel);
            memberCardOrder.setPayTime(new Date());
            memberCardOrderRepository.updateStatus(memberCardOrder);
        } catch (Exception e) {
            logger.warn("修改订单状态失败,订单号：" + orderNo);
        }

    }

    private String queryCardNo(MemberRegisterDto memberRegisterDto,
                               String memberId) {
        // 查询卡号
        String cardNo = "";
        List<Member> result = memberRepository.getMemberCardNo(memberId);
        if (CollectionUtils.isEmpty(result)) {
            logger.error("第三方购卡接口buyCard 查询卡号失败,memberId=" + memberId);
        } else {
            cardNo = result.get(0).getCardNo();
        }
        return cardNo;
    }

    private void sendPwd(String mobile, String cardNo, String randomPassword) {
        try {
            // 发送密码
            ShortMessageDto shortMessageDto = new ShortMessageDto();
            shortMessageDto.setSendSource("购买享卡发送密码");
            shortMessageDto.setSendType(SendType.PRODUCT.name());
            shortMessageDto.setModule(TemplateModule.MEMBER.name());
            shortMessageDto.setPriority(0);
            shortMessageDto.setMobile(mobile);
            shortMessageDto.setTemplateNo(Member_Buy_Card_Initial_Password_TemplateNo);
            Map context = new HashMap();
            context.put("cardNo",cardNo);
            context.put("randomPassword", randomPassword);
            shortMessageDto.setContext(JsonUtils.objectToJson(context));
            MessageRespDto messageRespDto = nbpProxy
                    .sendShortMessage(shortMessageDto);
            if (MessageRespDto.STATUS_FAIL.equals(messageRespDto.getStatus())) {
                logger.error("第三方购卡接口buyCard 注册成功，下发密码短信失败mobile=" + mobile);
            }
        } catch (Exception e) {
            logger.error("第三方购卡接口buyCard 注册成功，下发密码短信失败mobile=" + mobile);
        }
    }

    private String validateJDDR(BuyCardRequest buyCardRequest) {
        String errdesc = "";
        if (!(StringUtils.isNotBlank(buyCardRequest.getAddress())
                && StringUtils.isNotBlank(buyCardRequest.getUserName())
                && StringUtils.isNotBlank(buyCardRequest.getTitle())
                && StringUtils.isNotBlank(buyCardRequest.getCertificateType())
                && StringUtils.isNotBlank(buyCardRequest.getCertificateNo())
                && StringUtils.isNotBlank(buyCardRequest.getMemberType()) && StringUtils
                .isNotBlank(buyCardRequest.getPhone()))) {
            errdesc = "缺少必填项,姓名、性别、证件类型、证件号码、手机、地址、购卡类型不能为空";
            return errdesc;
        }
        if (validateService.isUsedByEmailOrPhone(buyCardRequest.getEmail(),
                buyCardRequest.getPhone(), new WebMemberRegisterReturnDto())) {
            errdesc = "身份信息已存在";
        }
        if (validateService.isExistCertificateNo(
                buyCardRequest.getCertificateType(),
                buyCardRequest.getCertificateNo())) {
            errdesc = "身份信息已存在";
        }
        return errdesc;
    }

    private String saveOrder(MemberRegisterDto addDto, String ordertype,
                             BigDecimal amount) throws RuntimeException {
        String registerXml = JaxbUtils.convertToXmlString(addDto);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        // 保存注册的信息到临时表 中
        MemberXmlDto xmlObj = new MemberXmlDto();
        MemberInfoDto memberInfoDto = addDto.getMemberInfoDto();
        xmlObj.setCertificateNo(memberInfoDto.getCertificateNo());
        xmlObj.setCertificateType(memberInfoDto.getCertificateType());
        xmlObj.setEmail(memberInfoDto.getEmail());
        xmlObj.setMobile(memberInfoDto.getMobile());
        xmlObj.setXml(registerXml);
        xmlObj.setCallBackFlag("N");
        MemberCardOrder memberCardOrder = new MemberCardOrder();
        // 生成订单号
        Long orderUUid = memberCardOrderRepository.getNextSequence();
        String orderDate = formatDate.format(new Date());
        String uuidOrderDate = orderUUid + orderDate;
        String orderNo = "V" + Long.toHexString(new Long(uuidOrderDate));

        xmlObj.setOrderNo(orderNo);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        xmlObj.setId(uuid);
        // 保存xml到临时表
        memberXmlRepository.saveXml(new MemberXml(xmlObj));
        memberCardOrder.setId(new Long(orderUUid));
        memberCardOrder.setOrderNo(orderNo);
        // 1-填充订单
        memberCardOrder.setOrderType(ordertype);
        memberCardOrder.setCurrentLevel(memberInfoDto.getMemberType());
        memberCardOrder.setCreateTime(new Date());
        memberCardOrder.setOrderTime(new Date());
        memberCardOrder.setStatus(1);
        memberCardOrder.setAmount(amount);

        memberCardOrder.setPayStatus(1);
        memberCardOrder .setSaleChannel(memberInfoDto.getRegisterSource().name());

        memberCardOrderRepository.insertOrder(memberCardOrder);
        return orderNo;
    }

    // 手机渠道预订单
    public BuyCardResponse mobilePrepareSaleCard(BuyCardRequest buyCardRequest) {
        /*
		 * 1.校验入参,必填项：memberType,address,provinceId,cityId,districtId,mcMemberCode
		 * 2.校验mccode是会员是否符合（按照一人一卡的原则只能升级不能降级） 如果是临时会员则进行转正操作 3.生成订单
		 */
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String errdesc = "";
        String bgUrl = "member/payForUpdate?uuid=" + uuid;
        errdesc = this.validatePerpareBuyCardInput(buyCardRequest);
        if (StringUtils.isNotBlank(errdesc)) {
            logger.error("buyCardRequest=" + buyCardRequest + " errdesc=" + errdesc);
            return new BuyCardResponse(BuyCardResponse.FAILURE, errdesc, "");
        }
        try {
            Member member = memberRepository.getMemberByMcMemberCode(buyCardRequest.getMcMemberCode());
            if (member == null) {
                return new BuyCardResponse(BuyCardResponse.FAILURE, errdesc, "");
            }
            
			// 先判断会员类型 快速会员转正
			if (MemType.QUICK_REGIST.getTypeCode().equals(member.getMemberType())) {
				if (validateService.isExistCertificateNo(buyCardRequest.getCertificateType(), buyCardRequest.getCertificateNo())) {
					errdesc = "用户已存在";
					return new BuyCardResponse(BuyCardResponse.FAILURE, errdesc, "");
				}
				RegistResponse result = toFullMember(member, toBuyCardDto(buyCardRequest));
				if (!RegistResponseStatus.OK.equals(result.getStatus())) {
					return new BuyCardResponse(BuyCardResponse.FAILURE, errdesc, "");
				}
				member = memberRepository.getMemberByMcMemberCode(buyCardRequest.getMcMemberCode());
			} else if (MemType.NORMAL.getTypeCode().equals(member.getMemberType())) {
				// 如果是礼享卡用户则判断是否重复购买
				errdesc = this.validatePerpareBuyCardCanBuy(member, buyCardRequest.getMemberType());
				if (StringUtils.isNotBlank(errdesc)) {
					return new BuyCardResponse(BuyCardResponse.FAILURE, errdesc, "");
				}
			} else {
				errdesc = "用户不存在";
				return new BuyCardResponse(BuyCardResponse.FAILURE, errdesc, "");
			}
            MemberDto memberDto = member.toDto();
            UpgradeMemberDto upgradeMemberDto = new UpgradeMemberDto(buyCardRequest, member.getCardNo(), memberDto.getCardType(), member.getMemberID(), null);
            String orderNo = saveUpdateOrder(upgradeMemberDto, member, uuid, new Integer(1));
            if (StringUtils.isNotBlank(orderNo)) {
                return new BuyCardResponse(BuyCardResponse.SUCCESS, "下订单成功",
                        orderNo, bgUrl);
            }
        } catch (RuntimeException e) {
            logger.error("手机渠道预订单错误 buyCardRequest=" + buyCardRequest, e);
            return new BuyCardResponse(BuyCardResponse.FAILURE, errdesc, "");
        }
        return new BuyCardResponse(BuyCardResponse.FAILURE, errdesc, "");
    }

    private BuyCardDto toBuyCardDto(BuyCardRequest buyCardRequest) {
    	BuyCardDto buyCardDto = new BuyCardDto();
    	buyCardDto.setCertificateNo(buyCardRequest.getCertificateNo());
    	buyCardDto.setCertificateType(buyCardRequest.getCertificateType());
    	buyCardDto.setUserName(buyCardRequest.getUserName());
		return buyCardDto;
	}

	private String saveUpdateOrder(UpgradeMemberDto upgradeMemberDto,
                                   Member member, String uuid, Integer payMethod) {
        try {
            String xmlStr = JaxbUtils.convertToXmlString(upgradeMemberDto);
            MemberUpdateDto memberUpdateDto = upgradeMemberDto
                    .getMemberUpdateDto();

            SimpleDateFormat formatDateOrder = new SimpleDateFormat("yyyyMMdd");
            MemberCardOrderDto memberCardOrderDto = new MemberCardOrderDto();

            // 生成订单号
            String orderUUid = memberCardOrderRepositoryImpl.getNextSequence()
                    + "";
            String orderDate = formatDateOrder.format(new Date());
            String uuidOrderDate = orderUUid + orderDate;
            String orderNo = "V" + Long.toHexString(new Long(uuidOrderDate));
            memberCardOrderDto.setId(new Long(orderUUid));
            memberCardOrderDto.setOrderNo(orderNo);
            memberCardOrderDto.setCardNo(memberUpdateDto.getMembcdno());
            // 2-升级
            memberCardOrderDto.setOrderType("UPGRADE");
            memberCardOrderDto.setCurrentLevel(memberUpdateDto.getOldCardType());
            memberCardOrderDto.setNextLevel(memberUpdateDto.getNewCardType());
            memberCardOrderDto.setMcMemberCode(memberUpdateDto
                    .getMcMemberCode());
            memberCardOrderDto.setCreateTime(new Date());
            memberCardOrderDto.setOrderTime(new Date());
            memberCardOrderDto.setStatus(new Integer(1));
            // 取得价格
            memberCardOrderDto.setAmount(new BigDecimal(memberUpdateDto
                    .getSalesmount()));

            // 如果需要支付则状态为1，不需要支付状态为2
            memberCardOrderDto.setPayStatus(new Integer(1));
            memberCardOrderDto.setSaleChannel(memberUpdateDto.getChannel());
            MemberCardOrder memberCardOrder = new MemberCardOrder(memberCardOrderDto);
            memberCardOrderRepositoryImpl.insertOrder(memberCardOrder);
            MemberXmlDto xmlObj = new MemberXmlDto();
            xmlObj.setId(uuid);
            xmlObj.setOrderNo(orderNo);
            xmlObj.setCertificateNo("");
            xmlObj.setCertificateType("");
            xmlObj.setEmail(member.getEmail());
            xmlObj.setMobile(member.getCellPhone());
            xmlObj.setCallBackFlag("N");
            xmlObj.setXml(xmlStr);
            memberXmlRepository.saveXml(new MemberXml(xmlObj));
            return orderNo;
        } catch (Exception e) {
            throw new RuntimeException(
                    "payProcess:saveUpdateOrder fail! upgradeMemberDto=" + upgradeMemberDto);
        }
    }

    private String validatePerpareBuyCardCanBuy(Member member, String membertype) {
        String errdesc = "";
        MemberDto memberDto = member.toDto();
        if (!member.isCardTypeHigher(memberDto.getCardType(), membertype)) {
            errdesc = "该会员已是"
                    + MemberCardType.getMemberCardTypeByCode(
                    memberDto.getCardType()).getMessage()
                    + "会员，不需要再升级为"
                    + MemberCardType.getMemberCardTypeByCode(
                    memberDto.getCardType()).getMessage() + "！";
        }
        return errdesc;
    }

    private String validatePerpareBuyCardInput(BuyCardRequest buyCardRequest) {
        String errdesc = "";
        if (!(StringUtils.isNotBlank(buyCardRequest.getAddress())
                && StringUtils.isNotBlank(buyCardRequest.getCityId())
                && StringUtils.isNotBlank(buyCardRequest.getDistrictId())
                && StringUtils.isNotBlank(buyCardRequest.getUserName())
                && StringUtils.isNotBlank(buyCardRequest.getProvinceId())
                && StringUtils.isNotBlank(buyCardRequest.getMcMemberCode())
                && StringUtils.isNotBlank(buyCardRequest.getCertificateType())
                && StringUtils.isNotBlank(buyCardRequest.getCertificateNo())
                && StringUtils.isNotBlank(buyCardRequest.getMemberType()))) {
            errdesc = "缺少必填项。用户编号、地址、购卡类型不能为空";
        }
        return errdesc;
    }


    public GetCardPriceResultDto getCardPrice(GetCardPriceDto getCardPriceDto) throws Exception {
        BigDecimal cardPrice = null;
        NewCardType currentCardType = getCardPriceDto.getCurrentCardType();
        if (currentCardType == null) {
            currentCardType = NewCardType.SILVER_CARD;
        }
        List<BaseData> priceData = memberRepository.getPrice(Member.getOptye(currentCardType.name(), getCardPriceDto.getUpgradeCardType().name()));
        if (CollectionUtils.isNotEmpty(priceData)) {
            cardPrice = new BigDecimal(priceData.get(0).getVal());
        }
        if (cardPrice == null) {
            logger.error("价格获取异常  {}", getCardPriceDto);
            return new GetCardPriceResultDto(GetCardPriceResultDto.StatusMessage.PRICE_NULL, cardPrice, getCardPriceDto.getUpgradeCardType());
        }
        //判断使用优惠券
        if (StringUtils.isNotBlank(getCardPriceDto.getCouponCode())) {
            //验证是否为升级邀请码
            CouponValidateResultForUpgradeDto upgradeResult = cbpHandler.verifyCouponForUpgrade(getCardPriceDto.getCouponCode(), getCardPriceDto.getMcMemberCode());
            if (upgradeResult.isCanUse() && !upgradeResult.getNewCardType().equals(getCardPriceDto.getUpgradeCardType())) {
                return new GetCardPriceResultDto(GetCardPriceResultDto.StatusMessage.TYPE_NOT_MATCH, cardPrice, getCardPriceDto.getUpgradeCardType());
            }
            if (upgradeResult.isOk(getCardPriceDto.getUpgradeCardType())) {
                return new GetCardPriceResultDto(GetCardPriceResultDto.StatusMessage.OK, subtract(upgradeResult.getCouponParValue(), cardPrice), getCardPriceDto.getUpgradeCardType());
            }
        }
        if(getCardPriceDto.getUpgradeCardType().equals(NewCardType.BLACK_CARD)){
           return new GetCardPriceResultDto(GetCardPriceResultDto.StatusMessage.NOT_FOUND_BLACK_CARD, cardPrice, getCardPriceDto.getUpgradeCardType());
        }
        return new GetCardPriceResultDto(GetCardPriceResultDto.StatusMessage.OK, cardPrice, getCardPriceDto.getUpgradeCardType());
    }

    private BigDecimal subtract(BigDecimal couponPar, BigDecimal cardPrice) {
        BigDecimal price = BigDecimal.ZERO;
        if (cardPrice.compareTo(couponPar) > 0) {
            price = cardPrice.subtract(couponPar);
        }
        return price;
    }


    public BuyCardResultDto createOrder(BuyCardDto buyCardDto) throws Exception {
        Member member = getMemberDto(buyCardDto.getMcMemberCode());
        MemberDto memberDto = (member == null ? null : member.toDto());
        if (null == memberDto) {
            return new BuyCardResultDto(BuyCardResultDto.StatusMessage.USER_NOT_EXSIT);
        }

        //获取卡的价格
        GetCardPriceResultDto cardPriceResultDto = getCardPrice(new GetCardPriceDto(NewCardType.valueOfCode(memberDto.getCardType()), buyCardDto.getUpgradeCardType(), buyCardDto.getCouponCode(), buyCardDto.getMcMemberCode()));
        buyCardDto.setAmount(cardPriceResultDto.getCardPrice());

        //先判断会员类型 快速会员直接转正
        if (MemType.QUICK_REGIST.getTypeCode().equals(memberDto.getMemberType())) {
        	RegistResponse result = toFullMember(member ,buyCardDto);
        	if(!RegistResponseStatus.OK.equals(result.getStatus())){
        		 return new BuyCardResultDto(BuyCardResultDto.StatusMessage.COMPLETE_FAIL);
        	}
        	member = getMemberDto(buyCardDto.getMcMemberCode());
        	memberDto = (member == null ? null : member.toDto());
            logger.warn("完善信息成功  {}", memberDto);
        }

        // 如果是礼享卡用户则判断是否重复购买
        if (!new Member().isCardTypeHigher(memberDto.getCardType(), buyCardDto.getUpgradeCardType().getCode())) {
            return new BuyCardResultDto(BuyCardResultDto.StatusMessage.LOW_LEVEL);
        }


        //判断锁优惠券
        if (StringUtils.isNotBlank(buyCardDto.getCouponCode())) {
            logger.error("锁定优惠券 {}", buyCardDto.getCouponCode());
            cbpHandler.lockCoupon(buyCardDto.getCouponCode());
        }

        //保存订单
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String bgUrl = "member/payForUpdate?uuid=" + uuid;
        UpgradeMemberDto upgradeMemberDto = new UpgradeMemberDto(buyCardDto.toBuyCardRequest(), memberDto.getCardNo(), memberDto.getCardType(), memberDto.getMemberID(), buyCardDto.getCouponCode());
        logger.warn("会员升级Dto,{}", upgradeMemberDto);
        String orderNo = saveUpdateOrder(upgradeMemberDto, new Member(memberDto), uuid, buyCardDto.getIsNeedPay());
        if (StringUtils.isBlank(orderNo)) {
            return new BuyCardResultDto(BuyCardResultDto.StatusMessage.SAVE_ORDER_FAIL);
        }

        //如果价格为零则使用了邀请码 直接升级
        if (buyCardDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            CRMResponseDto crmdto = memberUpdateService.freeUpgradeVIPCardInfo(upgradeMemberDto, orderNo, uuid, buyCardDto.getCouponCode());
            if (crmdto.getRetcode().equals("00002")) {
                return new BuyCardResultDto(BuyCardResultDto.StatusMessage.UPGRADE_FAIL, orderNo, bgUrl, buyCardDto.canPay(), buyCardDto.getAmount(), uuid, buyCardDto.getUpgradeCardType());
            }
        }
        logger.info("response " + buyCardDto);
        return new BuyCardResultDto(BuyCardResultDto.StatusMessage.ok, orderNo, bgUrl, buyCardDto.canPay(), buyCardDto.getAmount(), uuid, buyCardDto.getUpgradeCardType());
    }
    
    private RegistResponse toFullMember(Member member,BuyCardDto buyCardDto) {
    	if(!StringUtils.isBlank(buyCardDto.getCertificateNo())){
			member.setIdentityNo(buyCardDto.getCertificateNo());
		}
		if(!StringUtils.isBlank(buyCardDto.getCertificateType())){
			member.setIdentityType(buyCardDto.getCertificateType());
		}
		if(!StringUtils.isBlank(buyCardDto.getUserName())){
			member.setFullName(buyCardDto.getUserName());
		}
    	RegistResponse result = new RegistResponse();
    	result.setMcMemberCode(member.getMcMemberCode());
    	try {
    		result = memberHandler.updateQuickMemberBaseInfo(member);
		} catch (Exception e) {
			result.setStatus(RegistResponseStatus.FAIL);
		}
    	return result;
    }


    private Member getMemberDto(String mcMemberCode) {
        return memberRepository.getMemberByMcMemberCode(mcMemberCode);
    }


}
