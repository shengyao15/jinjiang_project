package com.jje.membercenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.RestClient;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.coupon.coupon.CouponMessageDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemberAddressDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.ResumeCardDto;
import com.jje.dto.membercenter.UpgradeMemberDto;
import com.jje.dto.membercenter.ValidationDto;
import com.jje.dto.payment.PayResultForBizDto;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.CRMResumeCardRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.service.MemberService;
import com.jje.membercenter.service.MemberUpdateService;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.handler.EbpHandler;

@Path("member")
@Component
public class MemberXmlResource {
	private static final Logger LOG = LoggerFactory
			.getLogger(MemberXmlResource.class);

	@Value(value = "${payment.url}")
	private String pbpUrl;
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberXmlRepository memberXmlRepository;

	@Autowired
	private CRMMembershipRepository crmMembershipRepository;

	@Autowired
	private CRMResumeCardRepository crmResumeCardRepository;
	@Autowired
	private WebMemberRepository webMemberRepository;

    @Autowired
    MemberUpdateService memberUpdateService;
    
    @Autowired
    private MemberService memberService;

	public static final String UPDATE_ORDER_SUCCESS = "success";

	public static final String UPDATE_ORDER_FAILURE = "failure";

	@Autowired
	MemberCardOrderRepository memberCardOrderRepositoryImpl;

	@Autowired
	RestClient client;
    @Autowired
    private CbpHandler cbpHandler;
    @Autowired
    private EbpHandler ebpHandler;
    
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/getXml")
	public Response getMemberXml(String uuid) {
		LOG.warn("----MemberXmlResource.getMemberXml() uuid=" + uuid);
		MemberXml registerXml = memberXmlRepository.getXml(uuid);
		try {
			return Response.status(Status.OK).entity(registerXml.toDto())
					.build();
		} catch (Exception e) {
			LOG.error("----MemberXmlResource转换注册xml失败", e.getMessage());
			return Response.status(Status.UNAUTHORIZED)
					.entity(UPDATE_ORDER_FAILURE).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/updateXmlStatus")
	public Response updateCallBackFlag(String uuid) {
		try {
			LOG.warn("---MemberXmlResource.updateCallBackFlag uuid=" + uuid);
			memberXmlRepository.updateCallBackFlag(uuid);
			return Response.status(Status.OK).entity("ok").build();
		} catch (Exception e) {
			LOG.error("---MemberXmlResource.updateCallBackFlag()转换注册xml失败", e.getMessage());
			return Response.status(Status.UNAUTHORIZED).entity("failure")
					.build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/registerLogin")
	public Response registerLogin(ValidationDto validationDto) {
		LOG.warn("---MemberXmlResource.registerLogin() usernameOrCellphoneOrEmail="+ validationDto.getUsernameOrCellphoneOrEmail());
		Member member = memberRepository.queryByUsernameOrCellphoneOrEmail(validationDto.getUsernameOrCellphoneOrEmail());
		if (member == null) {
			LOG.warn("----MemberXmlResource.registerLogin()----member is null");
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.status(Status.OK).entity(member.toDto()).build();
	}

	// @POST
	// @Produces(MediaType.APPLICATION_XML)
	// @Path("/order")
	// public Response getMemeberCardOrder(MemberCardOrderDto
	// memberCardOrderDto)
	// {
	// ResultMemberDto<MemberCardOrderDto> results =
	// memberCardOrderRepositoryImpl.queryMemberCardOrderList(memberCardOrderDto);
	// return Response.status(Status.OK).entity(results).build();
	// }

    /**
     * 购买注册回调接口
     * 包含优惠卷邀请注册流程
     * @param payResultForBizDto 支付结果
     * @param uuid 订单uuid
     * @return
     */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/pay")
	public Response updatePaymentStatus(PayResultForBizDto payResultForBizDto,@QueryParam("uuid") String uuid) {
		String result="";
		LOG.warn(new Date()+"----------updatePaymentStatus(uuid {}) 进入支付callback流程----------uuid:{},  params info :"+JaxbUtils.convertToXmlString(payResultForBizDto), uuid);
		if (payResultForBizDto == null || payResultForBizDto.getOrderNo() == null) {
			LOG.warn("-------------updatePaymentStatus, params is not correct-------- ");
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}	
		try {
			result=checkCardOrder(payResultForBizDto.getOrderNo(), uuid);
			if(StringUtils.isNotEmpty(result))
				return Response.ok().entity(result).build();
			result=UPDATE_ORDER_SUCCESS;
			boolean isPaySuccess=PayResultForBizDto.Result.TRUE.equals(payResultForBizDto.getResult());
			updateCardOrderPayStatus(payResultForBizDto, isPaySuccess);
			if(isPaySuccess){
				registAndAfterProcess(uuid, payResultForBizDto.getOrderNo());
			}
		} catch (Exception e) {
			LOG.error("----------MemberXmlResource.updatePaymentStatus(uuid {}) 支付后，用户信息注册失败！" , uuid, e);
			// 更新临时表 状态为F--操作异常		
			memberXmlRepository.updateCallBackFlagByBean(new MemberXml(uuid,MemberXml.EXEC_ERROR));
			result=UPDATE_ORDER_FAILURE;
		}
		return Response.ok().entity(result).build();


	}

	private void registAndAfterProcess(String uuid, String orderNo) throws Exception {
		MemberXml registerXml = memberXmlRepository.getXml(uuid);
		MemberRegisterDto registerDto = JaxbUtils.convertToObject(registerXml.getXml(), MemberRegisterDto.class);
		//FIXME OK 只有直接注册享卡才需要传入tier给CRM，以后传入的tier值以VBP为基准(20121220 cilin.xiao) 
		registerDto.calcTier(cbpHandler.verifyRegisterCouponNo(registerDto.getCouponCode()));
		registWithCoupon(registerDto,orderNo);
		LOG.warn("---------- 转换注册xml-------regist info :"+registerXml.getXml());
		CRMResponseDto response = crmMembershipRepository.addVIPMembership(registerDto);
		LOG.warn("---------- crm  regist  resultInfo  :"+JaxbUtils.convertToXmlString(response));
        if(response.isExecSuccess()){
        	MemberDto regMemberDto = response.getMember();
            Member regMember = new Member(regMemberDto);
			regMember.setMcMemberCode(memberRepository.generateMcMemberCode());
			memberService.storeMember(regMember, regMemberDto);
        	
        	Member member = memberRepository.getMemberByMemberID(response.getMembid());
	    	updatePaymentLogMemInfo(orderNo,new MemberDto(member.getMcMemberCode(),member.getFullName()));
	    	updateCardOrderMemInfo(member.getCardNo(),orderNo,member.getMcMemberCode());
	    	
	    	MemberDto memberDto = buildCouponRegisterIssueMemberDto(registerDto, response.getMembid(), member.getMcMemberCode());
	    	cbpHandler.registerIssue(memberDto);
	    	cbpHandler.registerUseConpon(member.getMcMemberCode(),registerDto.getCouponCode(),orderNo);
	    	ebpHandler.sendBuyCardEvent(member.getMcMemberCode(), registerDto.getMemberInfoDto().getMemberType(), member.getRegisterSource());
        } else {
        	cbpHandler.unlockCoupon(registerDto.getCouponCode());
        	memberXmlRepository.updateCallBackFlagByBean(new MemberXml(uuid,MemberXml.EXEC_FAIL));
        }
	}

	private void registWithCoupon(MemberRegisterDto registerDto,String orderNo){
		String couponCode = registerDto.getCouponCode();
		try{
			if(StringUtils.isNotBlank(couponCode)){
				cbpHandler.lockCoupon(couponCode);
				CouponMessageDto couponMessageDto = cbpHandler.queryCouponRule(couponCode);
				registerDto.setCouponRuleID(couponMessageDto.getRuleID());
			}
		}catch (Exception e) {
			LOG.warn("registWithCoupon.lockCoupon error",e);
		}
		
	}

	private MemberDto buildCouponRegisterIssueMemberDto(MemberRegisterDto registerDto, String memberId,String mcMemberCode) {
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberID(memberId);
		memberDto.setCardType(registerDto.getMemberInfoDto().getMemberType());
		memberDto.setRegisterSource(registerDto.getMemberInfoDto().getRegisterSource().name());
		memberDto.setMcMemberCode(mcMemberCode);
		return memberDto;
	}

	private void updateCardOrderMemInfo(String cardNo, String orderNo,String mcMemberCode) {		
		memberCardOrderRepositoryImpl.updateStatus(new MemberCardOrder(orderNo,cardNo,mcMemberCode));		
	}

	
	private void updatePaymentLogMemInfo(String orderNo,MemberDto memberDto) {
		try {			
			client.post(pbpUrl+"/payment/updatePaymentBuyerByOrderNo/"+orderNo, memberDto);
		} catch (Exception e) {
			LOG.error("----------updatePaymentStatus  支付后，更新支付中心订单信息失败！orderNo ：" + orderNo,  e);
		}
		
	}

	private void updateCardOrderPayStatus(PayResultForBizDto payResultForBizDto,	boolean isPaySuccess) {
		MemberCardOrder memberCardOrder = new MemberCardOrder(payResultForBizDto.getOrderNo(),
				payResultForBizDto.getPayTime(),payResultForBizDto.getPaymentSerialNumber(),
				payResultForBizDto.getPayType().name(),payResultForBizDto.getPaymentType().name(),
				payResultForBizDto.getPaymentBankCode(),isPaySuccess?MemberCardOrder.PAY_STATUS_SUCC:MemberCardOrder.PAY_STATUS_FAIL);
		memberCardOrderRepositoryImpl.updateStatus(memberCardOrder);
	}

	private String  checkCardOrder(String orderNo,String uuid){
		String result="";
		MemberCardOrder cardOrder = memberCardOrderRepositoryImpl.getMemberCardOrder(orderNo);
		if (cardOrder == null) {
			LOG.warn("--------------MemberXmlResource.updatePaymentStatus(uuid {}),订单不存在 ,orderNo :"+orderNo,uuid);
			result=UPDATE_ORDER_FAILURE;
		}
		else if (cardOrder.getPayStatus().equals(MemberCardOrder.PAY_STATUS_SUCC)) {
			LOG.warn("----------MemberXmlResource.updatePaymentStatus(uuid {}) ,payStatus already success,orderNo:" + orderNo, uuid);
			result=UPDATE_ORDER_SUCCESS;
		}
		return result;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/payForUpdate")
	public Response payForUpdate(PayResultForBizDto payResultForBizDto,
			@QueryParam("uuid") String uuid) {
		LOG.warn(new Date()+"----------MemberXmlResource.payForUpdate(uuid {})***start  update service ******", uuid);
		LOG.warn("----------MemberXmlResource.payForUpdate(uuid {}) "	+ payResultForBizDto, uuid);

        if (payResultForBizDto == null
                || payResultForBizDto.getOrderNo() == null) {
            if(LOG.isWarnEnabled())
                LOG.warn("----------MemberXmlResource.payForUpdate(uuid {}) *****payResultForBizDto is empty *********", uuid);

            return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
        }

		if (PayResultForBizDto.Result.FALSE.equals(payResultForBizDto.getResult())) {
            if(LOG.isWarnEnabled())
			    LOG.warn("----------MemberXmlResource.payForUpdate(uuid {}) payResultForBizDto.getResult()-->"
					+ payResultForBizDto.getResult(), uuid);

                MemberXml mx = new MemberXml();
                mx.setId(uuid);
                mx.setCallBackFlag("F");
                memberXmlRepository.updateCallBackFlagByBean(mx);

			return Response.ok().entity(UPDATE_ORDER_SUCCESS).build();
		}

		boolean opFlag = true;
		try {

			MemberXml updateXml = memberXmlRepository.getXml(uuid);
			UpgradeMemberDto upgradeMemberDto = null;
			try {
                if(LOG.isInfoEnabled())
				    LOG.info("----------MemberXmlResource.payForUpdate(uuid {})********xml is " + updateXml.getXml(), uuid);
				upgradeMemberDto = JaxbUtils.convertToObject(updateXml.getXml(), UpgradeMemberDto.class);
			} catch (Exception e) {
				opFlag = false;
				LOG.error("----------MemberXmlResource.payForUpdate(uuid {}) error!!! 转换升级xml失败", e.getMessage(), uuid);
			}
			/**
			 * 调用crm升级接口
			 */
			SimpleDateFormat formatDate = new SimpleDateFormat(
					"yyyyMMdd-HHmmss");
			String regStartTime = formatDate.format(new Date());
			LOG.debug("----------MemberXmlResource.payForUpdate(uuid {})" + regStartTime + "*****update interface start******", uuid);
			CRMResponseDto dto = null;
			try {
				LOG.warn(new Date()+"----------payForUpdate,crm update vipCardInfo ***start  add service ******");
				MemberUpdateDto memberUpdateDto = upgradeMemberDto.getMemberUpdateDto();
				dto = memberUpdateService.updateVIPCardInfo(memberUpdateDto);
				LOG.warn(new Date()+"----------payForUpdate,crm update vipCardInfo ***end  add service ******");
				if(dto == null  || !dto.isExecSuccess()){
                    LOG.error("CRM 升级享卡失败 memberUpdateDto= " + memberUpdateDto.toString() + "uuid = " +uuid);
                    throw new Exception("CRM 升级享卡失败");
                }
				cbpHandler.updateCouponStatus(upgradeMemberDto.getMemberUpdateDto().getCouponCode(), upgradeMemberDto.getMemberUpdateDto().getMcMemberCode(),payResultForBizDto.getOrderNo());
			} catch (Exception e) {
				cbpHandler.unlockCoupon(upgradeMemberDto.getMemberUpdateDto().getCouponCode());
				LOG.error("----------MemberXmlResource.payForUpdate(uuid {}) error!!! *****update interface failed ****", uuid, e.getMessage());
				opFlag = false;
			}
			String regEndTime = formatDate.format(new Date());
            if(LOG.isDebugEnabled())
			    LOG.debug("----------MemberXmlResource.payForUpdate(uuid {}) " + regEndTime + "*****update interface end******", uuid);
			// 调用地址增加 接口
			ResultMemberDto<MemberAddressDto> memberAddressDto = upgradeMemberDto.getMemberAddressDto();
			// 为空不需要调用
			if (memberAddressDto != null
					&& memberAddressDto.getResults() != null
					&& memberAddressDto.getResults().size() > 0) {
				String preStartTime = formatDate.format(new Date());
                if(LOG.isDebugEnabled())
				    LOG.debug("----------MemberXmlResource.payForUpdate(uuid {})" + preStartTime + "*****addr interface start******", uuid);
				try {
					crmMembershipRepository.saveMemberAddress(memberAddressDto.getResults());
				} catch (Exception e) {
					LOG.error("----------MemberXmlResource.payForUpdate(uuid {}) *****addr interface failed*****", uuid, e.getMessage());
				}
				String preEndTime = formatDate.format(new Date());
                if(LOG.isDebugEnabled())
				    LOG.debug("----------MemberXmlResource.payForUpdate(uuid {})" + preEndTime + "*****addr interface end******", uuid);
			}
			
			// 调用地址更新(设置邮寄发票和邮寄卡的地址)
			List<MemberAddressDto> upAddress = upgradeMemberDto.getUpdateAddressDto();
			// 为空不需要 调用
			if (upAddress != null && upAddress.size() > 0) {
				String preStartTime1 = formatDate.format(new Date());
                if(LOG.isDebugEnabled())
				    LOG.debug("----------MemberXmlResource.payForUpdate(uuid {})" + preStartTime1 + "*****update addr interface start******", uuid);
				try {
					crmMembershipRepository.updateMemberAddress(upAddress);
				} catch (Exception e) {
					LOG.error("----------MemberXmlResource.payForUpdate(uuid {}) error!!! ****update addr interface failed****", uuid, e.getMessage());
				}
				String preEndTime1 = formatDate.format(new Date());
                if(LOG.isDebugEnabled())
				    LOG.debug("----------MemberXmlResource.payForUpdate(uuid {})" + preEndTime1 + "*****update addr interface end******", uuid);
			}
			MemberCardOrder cardOrder = memberCardOrderRepositoryImpl.getMemberCardOrder(payResultForBizDto.getOrderNo());
			if (cardOrder == null) {
                if(LOG.isWarnEnabled())
				    LOG.warn("----------MemberXmlResource.payForUpdate(uuid {}) *****cardOrder is empty******", uuid);
				return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
			}
			if (MemberCardOrder.PAY_STATUS_SUCC.equals(cardOrder.getPayStatus())) {
				return Response.ok().entity(UPDATE_ORDER_SUCCESS).build();
			}

			// 更新订单信息
			MemberCardOrder memberCardOrder = new MemberCardOrder();
			memberCardOrder.setOrderNo(payResultForBizDto.getOrderNo());
			memberCardOrder.setPayStatus(MemberCardOrder.PAY_STATUS_SUCC);
			memberCardOrder.setBankCode(payResultForBizDto.getPaymentBankCode());
			// 卡号
			memberCardOrder.setCardNo(upgradeMemberDto.getCardNo());
			// 差一个memberId
//			memberCardOrder.setMemberId(cardOrder.getMemberId()); modified by cyz 0627
			memberCardOrder.setMcMemberCode(cardOrder.getMcMemberCode()); 
			memberCardOrder.setPaymentNo(payResultForBizDto.getPaymentSerialNumber());
			memberCardOrder.setPaymentVender(payResultForBizDto.getPaymentType().name());
			memberCardOrder.setPayType(payResultForBizDto.getPayType().name());
			memberCardOrder.setPayTime(payResultForBizDto.getPayTime());
			memberCardOrderRepositoryImpl.updateStatus(memberCardOrder);
			if (!opFlag) {
				// 更新临时表 状态为F--操作失败
				MemberXml mx = new MemberXml();
				mx.setId(uuid);
				mx.setCallBackFlag("F");
				memberXmlRepository.updateCallBackFlagByBean(mx);
			}
			ebpHandler.sendBuyCardEvent(cardOrder.getMcMemberCode(), cardOrder.getNextLevel(), RegistChannel.Website.name());
			return Response.ok().entity(UPDATE_ORDER_SUCCESS).build();
		} catch (Exception e) {
			LOG.error("----------MemberXmlResource.payForUpdate(uuid {}) ****update call back failed *****" + e.getMessage(), uuid, e);
			// 更新临时表 状态为F--操作失败
			MemberXml mx = new MemberXml();
			mx.setId(uuid);
			mx.setCallBackFlag("F");
			memberXmlRepository.updateCallBackFlagByBean(mx);
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/getmembercardorder")
	public Response getMemberCardOrderDto(String orderNo) {
		try {
            if(LOG.isWarnEnabled())
			    LOG.warn("----------MemberXmlResource.getMemberCardOrderDto(orderNo{})", orderNo);
			MemberCardOrder cardOrder = memberCardOrderRepositoryImpl
					.getMemberCardOrder(orderNo);
			return Response.ok().entity(cardOrder.toDto()).build();
		} catch (Exception e) {
			LOG.error("----------MemberXmlResource.getMemberCardOrderDto(orderNo{}) error!!! exception " + e.getMessage(), orderNo, e);
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/getmemberXmlByOrderNo")
	public Response getmemberXmlByOrderNo(String orderNo) {
		try {
			LOG.warn("----------MemberXmlResource.getmemberXmlByOrderNo(orderNo {})", orderNo);
            if(LOG.isDebugEnabled()) LOG.debug("----------MemberXmlResource.getmemberXmlByOrderNo(orderNo {})", orderNo);
			MemberXml memberXml = memberXmlRepository.getXmlByOrderNo(orderNo);
			return Response.ok().entity(memberXml.toDto()).build();
		} catch (Exception e) {
			LOG.error("----------MemberXmlResource.getmemberXmlByOrderNo(orderNo {}) exception " + e.getMessage(), orderNo, e);
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/order")
	public Response getMemeberCardOrder(
			QueryMemberDto<MemberCardOrderDto> memberCardOrderDto) {
		LOG.debug("***** GET MEMBER CARD ORDER METHOD INVOKE *****");
		ResultMemberDto<MemberCardOrderDto> results = new ResultMemberDto<MemberCardOrderDto>();
		try {
			results = memberCardOrderRepositoryImpl
					.queryMemberCardOrderList(memberCardOrderDto);
			LOG.debug("***** QUERY MEMBER CARD ORDER METHOD INVOKE *****");
		} catch (Exception e) {
			LOG.error("order exception " + e.getMessage(), e);
		}
		return Response.status(Status.OK).entity(results).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/payForResumeCard")
	public Response payForResumeCard(PayResultForBizDto payResultForBizDto,
			@QueryParam("uuid") String uuid) {
		LOG.debug("*****START  RESUME SERVICE*****");
		if (LOG.isDebugEnabled()) {
			LOG.debug("*****REST SERVICE START*****" + payResultForBizDto);
		}
		if (PayResultForBizDto.Result.FALSE.equals(payResultForBizDto
				.getResult())) {
			return Response.ok().entity(UPDATE_ORDER_SUCCESS).build();
		}
		if (payResultForBizDto == null
				|| payResultForBizDto.getOrderNo() == null) {
			LOG.warn("*****PAYRESULTFORBIZDTO IS EMPTY*****", "e");
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}
		boolean opFlag = true;
		try {
			LOG.debug("*****SELECT FOR XML START*****");
			MemberXml resumeXML = memberXmlRepository.getXml(uuid);
			LOG.debug("*****SELECT FOR XML END*****");
			ResumeCardDto resumeCardDto = null;
			try {
				LOG.info(resumeXML.getXml());
				resumeCardDto = JaxbUtils.convertToObject(resumeXML.getXml(),
						ResumeCardDto.class);
				LOG.debug("*****COVERT RESUME CARD XML SUCCESS*****");
			} catch (Exception e) {
				opFlag = false;
				LOG.error("*****COVERT RESUME CARD XML ERROR*****",
						e.getMessage());
			}
			/**
			 * 调用CRM续会接口
			 */
			SimpleDateFormat formatDate = new SimpleDateFormat(
					"yyyyMMdd-HHmmss");
			String regStartTime = formatDate.format(new Date());
			CRMResponseDto dto = null;
			LOG.debug(regStartTime + "*****RUSUME CARD INTERFACR START******");
			try {
				LOG.debug("*****CALL RESUME CARD INTERFACE OF CRM START*****");
				dto = crmResumeCardRepository.resumeCard(resumeCardDto);
				if ("00002".equals(dto.getRetcode())) {
					opFlag = false;
				}
			} catch (Exception e) {
				LOG.error("*****RUSUME CARD INTERFACR ERROR*****",
						e.getMessage());
				opFlag = false;
			}
			String regEndTime = formatDate.format(new Date());
			LOG.debug("*****" + dto.getMembid()
					+ "*****RUSUME CARD INTERFACR SUCCESS******" + regEndTime);
			MemberCardOrder cardOrder = memberCardOrderRepositoryImpl
					.getMemberCardOrder(payResultForBizDto.getOrderNo());
			if (cardOrder == null) {
				LOG.warn("*****CARD ORDER IS EMPTY******");
				return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
			}
			if (cardOrder.getPayStatus()
					.equals(MemberCardOrder.PAY_STATUS_SUCC)) {
				return Response.ok().entity(UPDATE_ORDER_SUCCESS).build();
			}
			LOG.debug("*****UPDATE MEMBER CARD ORDER INFO START*****");
			MemberCardOrder memberCardOrder = new MemberCardOrder();
			memberCardOrder.setOrderNo(payResultForBizDto.getOrderNo());
			memberCardOrder.setPayStatus(MemberCardOrder.PAY_STATUS_SUCC);
			memberCardOrder
					.setBankCode(payResultForBizDto.getPaymentBankCode());
			// 差一个memberId
//			memberCardOrder.setMemberId(cardOrder.getMemberId()); modified by cyz 0627
			memberCardOrder.setMcMemberCode(cardOrder.getMcMemberCode());
			// 卡号
			memberCardOrder.setCardNo(resumeCardDto.getJjmemcardno());
			memberCardOrder.setPaymentNo(payResultForBizDto
					.getPaymentSerialNumber());
			memberCardOrder.setPaymentVender(payResultForBizDto
					.getPaymentType().name());
			memberCardOrder.setPayType(payResultForBizDto.getPayType().name());
			memberCardOrder.setPayTime(payResultForBizDto.getPayTime());
			memberCardOrderRepositoryImpl.updateStatus(memberCardOrder);
			LOG.debug("*****UPDATE MEMBER CARD ORDER INFO END*****");
			if (!opFlag) {
				// 更新临时表 状态为F--操作失败
				MemberXml mx = new MemberXml();
				mx.setId(uuid);
				mx.setCallBackFlag("F");
				memberXmlRepository.updateCallBackFlagByBean(mx);
			}
			ebpHandler.sendBuyCardEvent(cardOrder.getMcMemberCode(), cardOrder.getNextLevel(), RegistChannel.Website.name());
			return Response.ok().entity(UPDATE_ORDER_SUCCESS).build();
		} catch (Exception e) {
			MemberXml mx = new MemberXml();
			mx.setId(uuid);
			mx.setCallBackFlag("F");
			memberXmlRepository.updateCallBackFlagByBean(mx);
			LOG.error("*****SYSTEM EXCEPTION*****" + e.getMessage(), e);
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/getMemberRepeat")
	public Response getMemberRepeat(MemberDto memberDto) {
		try {
			LOG.warn("-----MemberXmlResource.getMemberRepeat memberCode=" + memberDto.getMemberCode()
					+ " userName=" + memberDto.getUserName());
			String repeat = "N";
			Member member = new Member();
			member.setMemberCode(memberDto.getMemberCode());
			member.setUserName(memberDto.getUserName());
			List<MemberVerfy> members = memberRepository
					.getRepeatMenName(member);
			if (members != null && members.size() > 0)
				repeat = "Y";
			else
				repeat = isWebVerifyExists(memberDto.getUserName());
			return Response.ok().entity(repeat).build();
		} catch (Exception e) {
			LOG.error("-----MemberXmlResource.getMemberRepeat exception " + e.getMessage(), e);
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}
	}

	private String isWebVerifyExists(String menName) {
		String repeat = "N";
		MemberVerfy verify = webMemberRepository
				.queryRegisterByCellphoneOrEmail(menName);
		if (verify != null) {
			repeat = "Y";
		}
		return repeat;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryRepeatEmailMember")
	public Response queryRepeatEmailMember(MemberDto memberDto) {
		try {
			LOG.warn("------MemberXmlResource.queryRepeatEmailMember memberCode="
					+ memberDto.getMemberCode() + " userName="
					+ memberDto.getUserName());
			String repeat = "N";
			Member member = new Member();
			member.setMemberCode(memberDto.getMemberCode());
			member.setUserName(memberDto.getUserName());
			List<MemberVerfy> members = memberRepository
					.getRepeatMenName(member);
			if (members != null && members.size() > 0)
				repeat = "Y";
			else
				repeat = isWebVerifyExists(memberDto.getUserName());
			return Response.ok().entity(repeat).build();
		} catch (Exception e) {
			LOG.error("-----MemberXmlResource.queryRepeatEmailMember error!! exception " + e.getMessage(), e);
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}
	}
	
	@SuppressWarnings("deprecation")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/queryRepeatCdnoMember")
	public Response queryRepeatCdnoMember(MemberDto memberDto) {
		try {
			LOG.warn("------MemberXmlResource.queryRepeatCdnoMember getIdentityNo=" + memberDto.getIdentityNo() + " getIdentityType=" + memberDto.getIdentityType());
			String repeat = "N";
			Member member = new Member();
			member.setIdentityNo(memberDto.getIdentityNo());
			member.setIdentityType(memberDto.getIdentityType());
			List<Member> memberResultList = memberRepository.queryIdentifyInfo(member);
			if (memberResultList != null && memberResultList.size() > 0){
				if(memberResultList.size() == 1){
					Member mem = memberResultList.get(0);
					repeat = mem.getMemberCode().equals(memberDto.getMemberCode())? "N" : "Y";
				}else{
					repeat = "Y";
				}
			}
			return Response.ok().entity(repeat).build();
		} catch (Exception e) {
			LOG.error("-----MemberXmlResource.queryRepeatCdnoMember error!! exception " + e.getMessage(), e);
			return Response.ok().entity(UPDATE_ORDER_FAILURE).build();
		}
	}

}
