package com.jje.vbp.taobao.domain;

import com.esotericsoftware.minlog.Log;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.ObjectUtils;
import com.jje.dto.member.taobao.TaobaoCheckDto;
import com.jje.dto.member.taobao.TaobaoErrorMsg;
import com.jje.dto.member.taobao.TaobaoNotifyDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.dto.membercenter.MemberXmlDto;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.dto.membercenter.UpgradeMemberDto;
import com.jje.dto.vbp.buyCard.BuyCardResultDto;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.membercenter.domain.MemberXml;
import com.jje.membercenter.domain.MemberXmlRepository;
import com.jje.membercenter.persistence.MemberMapper;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.membercenter.service.MemberUpdateService;
import com.jje.vbp.sns.persistence.ThirdpartyBindMapper;
import com.jje.vbp.taobao.persistence.TaobaoBindingMapper;
import com.jje.vbp.taobao.proxy.TaobaoProxy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class TaobaoRepository {
    private Logger logger = LoggerFactory.getLogger(TaobaoRepository.class);
    @Autowired
	private TaobaoBindingMapper taobaoBindingMapper;
    @Autowired
    private TaobaoProxy taobaoProxy;
    @Autowired
	private MemberHandler memberHandler;
    @Autowired
    MemberUpdateService memberUpdateService;
    @Autowired
    MemberCardOrderRepository memberCardOrderRepositoryImpl;
    @Autowired
    private MemberXmlRepository memberXmlRepository;

	@Autowired
	private MemberMapper memberMapper;
	
    public TaobaoCheckDto checkCanBinding(String tbLoginId, Member member) {
        final List<TaobaoBindingDomain> query = taobaoBindingMapper.query(tbLoginId);
        if (CollectionUtils.isEmpty(query)){
        	 return new TaobaoCheckDto(false);
        }
        if (query.size() != 1) {
        	return new TaobaoCheckDto(true, false, StringUtils.EMPTY, TaobaoErrorMsg.SERVER_ERROR);
        }
        TaobaoBindingDomain domain = query.get(0);
        if (member == null) {
            return new TaobaoCheckDto(true, false, domain.getMemberId(), TaobaoErrorMsg.TBID_USED);
        }
        if (StringUtils.equals(tbLoginId, domain.getTaobaoId()) && StringUtils.equals(domain.getStatus(), "UNBIND")) {
            if (StringUtils.equals(member.getMcMemberCode(), domain.getMemberId())) {
                return new TaobaoCheckDto(true, true, domain.getMemberId(), null);
            } else {
                return new TaobaoCheckDto(true, false, domain.getMemberId(), TaobaoErrorMsg.TBID_USED);
            }
        } else {
            return new TaobaoCheckDto(true,false,domain.getMemberId(),TaobaoErrorMsg.BINDED);
        }
    }

    public TaobaoErrorMsg notify(String tbLoginId, Member member) {
        if (!taobaoProxy.notify(buildTaobaoNotify(tbLoginId, member))) {
            return TaobaoErrorMsg.TAOBAO_BIND_FAIL;
        }
        taobaoBindingMapper.updateStatus(tbLoginId,"BINDED");
        return TaobaoErrorMsg.SUCCESS;
    }
    

    public TaobaoErrorMsg bindAndNotify(String tbLoginId, String tbLevel, Member member, boolean isLogin) {
        TaobaoBindingDomain domain = new TaobaoBindingDomain(tbLoginId, member.getMcMemberCode(), tbLevel, "UNBIND", isLogin ? "LOGIN" : "REGISTER");
        try {
            taobaoBindingMapper.insert(domain);
        } catch (Exception e) {
            logger.error("taobao jje bind error,taobaoBindingMapper.insert({})", domain, e);
            return TaobaoErrorMsg.JJE_BIND_ERROR;
        }
        return this.notify(tbLoginId, member);
    }

    private TaobaoNotifyDto buildTaobaoNotify(String tbLoginId, Member member) {
        TaobaoNotifyDto notifyDto = new TaobaoNotifyDto();
        notifyDto.setTaobaoId(Long.parseLong(tbLoginId));
        Long memberScore = 0l;
        try {
            memberScore = Long.parseLong(memberHandler.getMemberScore(member.getMemberCode()).toString());
        } catch (Exception e) {
            logger.error("taobao buildTaobaoNotify error,query member score error mc:{}", member.getMcMemberCode(), e);
        }
        notifyDto.setScore(memberScore);
        notifyDto.setGrade(convertTaobaoGrade(member.getNewMemberHierarchy()));
        notifyDto.setCardNo(member.getCardNo());
        return notifyDto;
    }

    private String convertTaobaoGrade(String hierarchy) {
        NewCardType type = NewCardType.valueOfTier(hierarchy);
        if(type == null){
        	return "V1";
        }
        switch (type) {
            case SILVER_CARD:
                return "V1";
            case GOLD_CARD:
                return "V2";
            case PLATINUM_CARD:
                return "V3";
            default:
                return "V1";

        }
    }

    public TaobaoErrorMsg autoUpgrade(Member member, MemberRegisterDto dto) {

        
    	MemberUpdateDto memberUpdateDto = new MemberUpdateDto();
    	String newCardType = null;
    	if(StringUtils.equals("F2", dto.getMemberInfoDto().getTaobaoLevel())) {
    		newCardType = NewCardType.GOLD_CARD.getCode();
    	} else if(StringUtils.equals("F3", dto.getMemberInfoDto().getTaobaoLevel())){
    		newCardType = NewCardType.PLATINUM_CARD.getCode();
    	} else {
            return TaobaoErrorMsg.SUCCESS;
    	}
    	memberUpdateDto.setOldCardType(NewCardType.SILVER_CARD.getCode());
    	memberUpdateDto.setNewCardType(newCardType);
    	memberUpdateDto.setChannel(RegistChannel.Website.toString());
    	memberUpdateDto.setMembcdno(member.getCardNo());
    	memberUpdateDto.setMembid(member.getMemberID());
    	memberUpdateDto.setMcMemberCode(member.getMcMemberCode());
    	memberUpdateDto.setPostflg("Y");
    	memberUpdateDto.setInvflg("N");
    	memberUpdateDto.fullValueToOptye();
    	memberUpdateDto.setSalesmount("0");

    	try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            UpgradeMemberDto upgradeMemberDto = new UpgradeMemberDto();
            upgradeMemberDto.setMemberUpdateDto(memberUpdateDto);
            upgradeMemberDto.setInvoice(null);
            upgradeMemberDto.setMailInvoice(false);
            upgradeMemberDto.setCardNo(member.getCardNo());
            upgradeMemberDto.setTargetLevel(newCardType);
        	
            saveOrder(member, uuid, memberUpdateDto, upgradeMemberDto);
			CRMResponseDto crmResponseDto = memberUpdateService.updateVIPCardInfo(memberUpdateDto);
			if(!crmResponseDto.isExecSuccess()) {
		        return TaobaoErrorMsg.JJE_BIND_ERROR;
			}
		} catch (Exception e) {
			logger.error("TaobaoRepository.autoUpgrade error, member:{}", member, e);
			return TaobaoErrorMsg.JJE_BIND_ERROR;
		}
        return TaobaoErrorMsg.SUCCESS;
    }

	private void saveOrder(Member member, String uuid, MemberUpdateDto memberUpdateDto, UpgradeMemberDto upgradeMemberDto) {

        String xmlStr = JaxbUtils.convertToXmlString(upgradeMemberDto);
        SimpleDateFormat formatDateOrder = new SimpleDateFormat("yyyyMMdd");
        MemberCardOrderDto memberCardOrderDto = new MemberCardOrderDto();

        // 生成订单号
        String orderUUid = memberCardOrderRepositoryImpl.getNextSequence() + "";
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
        memberCardOrderDto.setMcMemberCode(memberUpdateDto.getMcMemberCode());
        memberCardOrderDto.setCreateTime(new Date());
        memberCardOrderDto.setOrderTime(new Date());
        memberCardOrderDto.setStatus(new Integer(1));
        // 取得价格
        memberCardOrderDto.setAmount(new BigDecimal(memberUpdateDto.getSalesmount()));

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
	}

	public String queryCRMID(String taobaoID) {
		List<TaobaoBindingDomain> list = taobaoBindingMapper.query(taobaoID);
		if(CollectionUtils.isEmpty(list)){
			return "";
		}
		TaobaoBindingDomain domain = list.get(0);
		if(!"BINDED".equals(domain.getStatus())){
			logger.info("taobaoID not binding, return " + taobaoID);
			return "";
		}
		
		String mcCode = domain.getMemberId();
		Member member = memberMapper.getMemberByMcMemberCode(mcCode);
		if(member==null){
			return "";
		}
		
		return member.getMemberID();
	}
	
	public String queryTaobaoID(String mcCode) {
		List<TaobaoBindingDomain> list = taobaoBindingMapper.queryTaobaoID(mcCode);
		if(CollectionUtils.isEmpty(list)){
			return "";
		}
		TaobaoBindingDomain domain = list.get(0);
		if(!"BINDED".equals(domain.getStatus())){
			logger.info("mcCode not binding, return " + mcCode);
			return "";
		}
		
		String taobaoID = domain.getTaobaoId();
		return taobaoID;
	}
}
