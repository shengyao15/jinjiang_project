package com.jje.vbp.order.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.CardType;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberXmlDto;
import com.jje.dto.membercenter.ResumeCardDto;
import com.jje.dto.vbp.order.CardOrderResponseCode;
import com.jje.dto.vbp.order.CardOrderResponseDto;
import com.jje.dto.vbp.order.CardOrderStatus;
import com.jje.membercenter.domain.BaseData;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;
import com.jje.vbp.order.domain.CardOrder;
import com.jje.vbp.order.domain.CardOrderHistory;
import com.jje.vbp.order.service.bean.CustomTraversableResolver;
import com.jje.vbp.order.service.bean.EmptyValidation;
import com.jje.vbp.order.service.bean.OrderException;

@Service
public class CardOrderService {

	private static final Logger logger = LoggerFactory.getLogger(CardOrderService.class);

	@Autowired
	private MemberCardOrderRepository memberCardOrderRepository;

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberXmlRepository memberXmlRepository;

	public CardOrderResponseDto createOrder(CardOrder order) {

		validateSimpleProperty(order);
		
		Member member = memberRepository.getMemberByMcMemberCode(order.getMcMemberCode());
		
		if(member == null){
			logger.error("order[{}] 查无该用户！", order);
			throw new OrderException(CardOrderResponseCode.ERROR, "查无该用户！");
		}
		
		//checkCard(order); 由于卡号重复 及 查询性能问题 经确认不做卡校验

		MemberCardOrder memberCardOrder = generateMemberCardOrder(order, member.toDto());

		memberCardOrderRepository.insertOrder(memberCardOrder);
		memberCardOrderRepository.insertOrderHistory(new CardOrderHistory(memberCardOrder, order.getAction(), "create order succes"));
		
		try {
			MemberXml memberXml = saveMemberXml(member, order);
			memberCardOrderRepository.insertOrderHistory(new CardOrderHistory(memberCardOrder, "saveMemberXml success", JaxbUtils.convertToXmlString(memberXml)));
		} catch (Exception e) {
			logger.error("order[{}] 保存MemberXml异常！", order);
			updateOrderToFail(memberCardOrder);
			memberCardOrderRepository.insertOrderHistory(new CardOrderHistory(memberCardOrder, "saveMemberXml error", StringUtils.substring(com.jje.common.utils.StringUtils.getStackTraceAsString(e), 0, 666)));
			throw new OrderException(CardOrderResponseCode.ERROR, "存单异常！");
		}
		
		return new CardOrderResponseDto(CardOrderResponseCode.SUCCESS, "", memberCardOrder.getOrderNo());

	}

	private void checkCard(CardOrder order) {
		MemberMemCard memberMemCard = memberRepository.getCardInfoByCardno(order.getCardNo());
		if(memberMemCard == null){
			logger.error("order[{}] 查无此卡！", order);
			throw new OrderException(CardOrderResponseCode.ERROR, "查无此卡！");
		}
		
		if(!order.getCardType().getCode().equals(memberMemCard.getCardTypeCd())){
			logger.error("order[{}] 订单卡类型与[{}]不一致！", order, memberMemCard.getCardTypeCd());
			throw new OrderException(CardOrderResponseCode.ERROR, "订单卡类型不一致！");
		}
		
		//续费的卡只能是J Benefit Card和J2 Benefit Card
		//过期的享卡可以续费啊，但是注销的不行；过期的悦享卡不能续费了
		if(memberMemCard.getCardTypeCd().equals(CardType.ENJOY.getCode())){
			if("Logout".equals(memberMemCard.getStatus())){
				logger.error("order[{}] 订单卡状态[{}]无效！", order, memberMemCard.getSource());
				throw new OrderException(CardOrderResponseCode.ERROR, "订单卡状态无效！");
			}
		}else if(memberMemCard.getCardTypeCd().equals(CardType.ENJOY2.getCode())){
			if("Logout".equals(memberMemCard.getStatus()) || "Expired".equals(memberMemCard.getStatus())){
				logger.error("order[{}] 订单卡状态[{}]无效！", order, memberMemCard.getSource());
				throw new OrderException(CardOrderResponseCode.ERROR, "订单卡状态无效！");
			}
			Date add = DateUtils.add(new Date(), Calendar.MONTH, 3);
			if(add.before(memberMemCard.getDueDate())){
				logger.error("order[{}] 订单卡需在过期日期[{}]前三个月才可续费！", order, memberMemCard.getDueDate());
				throw new OrderException(CardOrderResponseCode.ERROR, "订单卡需在过期日期前三个月才可续费！");
			}
		}else{
			logger.error("order[{}] 订单卡类型[{}]无效！", order, memberMemCard.getCardTypeCd());
			throw new OrderException(CardOrderResponseCode.ERROR, "订单卡类型无效！");
		}
	}

	private void updateOrderToFail(MemberCardOrder memberCardOrder) {
		MemberCardOrder updateMemberCardOrder = new MemberCardOrder();
		updateMemberCardOrder.setOrderNo(memberCardOrder.getOrderNo());
		updateMemberCardOrder.setStatus(CardOrderStatus.FAIL.getCode());
		updateMemberCardOrder.setPayStatus(CardOrderStatus.FAIL.getCode());
		memberCardOrderRepository.updateStatus(updateMemberCardOrder);
	}

	private void validateSimpleProperty(CardOrder order) {
		ValidatorFactory factory = Validation.byDefaultProvider().configure().traversableResolver(new CustomTraversableResolver())
				.buildValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<CardOrder>> requestViolations = validator.validate(order, EmptyValidation.class);
		for (ConstraintViolation<CardOrder> constraintViolation : requestViolations) {
			logger.error("order[{}] 数据校验异常!", order, constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
			throw new OrderException(CardOrderResponseCode.FAIL, constraintViolation.getPropertyPath() + ":"
					+ constraintViolation.getMessage());
		}
	}

	private MemberCardOrder generateMemberCardOrder(CardOrder order, MemberDto member) {
		
		MemberCardOrder memberCardOrder = new MemberCardOrder();

		Long orderUUid = memberCardOrderRepository.getNextSequence();
		if (orderUUid == null) {
			logger.error("order[{}] 创建订单号异常！", order);
			throw new OrderException(CardOrderResponseCode.ERROR, "创建订单号异常！");
		}

		String orderDate = DateUtils.formatDate(order.getOrderDate(), DateUtils.YYYYMMDD);
		if (StringUtils.isBlank(orderDate)) {
			logger.error("order[{}] 下单日期异常！", order);
			throw new OrderException(CardOrderResponseCode.ERROR, "下单日期异常！");
		}

		String uuidOrderDate = orderUUid + orderDate;
		String orderNo = "V" + Long.toHexString(new Long(uuidOrderDate));
		memberCardOrder.setId(orderUUid);
		memberCardOrder.setOrderNo(orderNo);
		memberCardOrder.setOrderType(order.getOrderType().toString());
		memberCardOrder.setCurrentLevel(member.getCardType());
		memberCardOrder.setNextLevel(member.getCardType());
		memberCardOrder.setMcMemberCode(member.getMcMemberCode());
		memberCardOrder.setCreateTime(new Date());
		memberCardOrder.setOrderTime(order.getOrderDate());
		memberCardOrder.setStatus(CardOrderStatus.UNPAY.getCode());
		memberCardOrder.setCardNo(order.getCardNo());
		memberCardOrder.setSaleChannel(order.getOrderChannel());
		
		order.setOrderNo(orderNo);

		try {
			List<BaseData> result = memberRepository.getPrice(order.getAction());
			String price = result.get(0).getVal();
			memberCardOrder.setAmount(BigDecimal.valueOf(Double.parseDouble(price)));
		} catch (Exception e) {
			logger.error("order[{}] 获取价格错误!", order, e);
			throw new OrderException(CardOrderResponseCode.ERROR, "获取价格错误!");
		}
		memberCardOrder.setPayStatus(CardOrderStatus.UNPAY.getCode());
		return memberCardOrder;
	}
	
	//TODO 直接迁移过来，待重构
	private MemberXml saveMemberXml(Member member, CardOrder order) {
		ResumeCardDto dto = new ResumeCardDto();
		dto.setMembid(member.getMemberID());
		dto.setMemcardno(order.getCardNo());
		dto.setAction(order.getAction());
		dto.setChannel(order.getOrderChannel());
		
		String xmlStr = JaxbUtils.convertToXmlString(dto);

		MemberXmlDto xmlObj = new MemberXmlDto();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		xmlObj.setId(uuid);
		xmlObj.setOrderNo(order.getOrderNo());
		xmlObj.setCertificateNo("");
		xmlObj.setCertificateType("");
		xmlObj.setEmail(member.getEmail());
		xmlObj.setMobile(member.getCellPhone());
		xmlObj.setCallBackFlag("N");
		xmlObj.setXml(xmlStr);
		MemberXml memberXml = new MemberXml(xmlObj);
		memberXmlRepository.saveXml(memberXml);
		return memberXml;
	}

	public MemberCardOrder getMemberCardOrderForPayByOrderNo(String orderNo) {
		MemberCardOrder memberCardOrder = memberCardOrderRepository.getMemberCardOrder(orderNo);
		if (memberCardOrder == null)
			return null;
		MemberXml memberXml = memberXmlRepository.getXmlByOrderNo(orderNo);
		memberCardOrder.setMemberXml(memberXml);
		return memberCardOrder;
	}

}
