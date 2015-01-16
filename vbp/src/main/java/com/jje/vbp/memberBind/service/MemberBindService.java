package com.jje.vbp.memberBind.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.jboss.resteasy.spi.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.StringUtils;
import com.jje.dto.coupon.rule.BookingModule;
import com.jje.dto.coupon.rule.CouponRuleDto;
import com.jje.dto.coupon.rule.CouponRuleType;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.memberbind.MemberBindCardDto;
import com.jje.dto.membercenter.memberbind.MemberBindDto;
import com.jje.dto.membercenter.memberbind.MemberBindStatus;
import com.jje.dto.membercenter.memberbind.MemberCouponInfoDto;
import com.jje.dto.membercenter.memberbind.MemberCouponInfosDto;
import com.jje.dto.membercenter.memberbind.MemberInfoResultDto;
import com.jje.dto.membercenter.memberbind.RegisterMemberBindDto;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;
import com.jje.membercenter.domain.OpRecordLogDomain;
import com.jje.membercenter.domain.OpRecordLogDomain.EnumOpType;
import com.jje.membercenter.persistence.OpRecordLogRepository;
import com.jje.membercenter.service.MemberService;
import com.jje.vbp.handler.proxy.CbpProxy;
import com.jje.vbp.memberBind.MemberBindResource;
import com.jje.vbp.memberBind.domain.MemberBindEntity;

@Service
public class MemberBindService {
	private static final Logger logger = LoggerFactory
			.getLogger(MemberBindResource.class);
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberBindRepository memberBindRepository;
    @Autowired
    private MemberResource memberResource;
	@Autowired
	MemberService memberService;
	@Autowired
	MemberScoreLevelInfoRepository memberScoreLevelInfoRepository;
	
	@Autowired
	private CbpProxy cbpProxy;
	
    @Autowired
    OpRecordLogRepository opRecordLogRepository;
	
	public String checkLoginAndBind(MemberBindDto dto) throws Exception {

		// valid dto
		validationBind(dto);
		
		MemberBindEntity memberBindEntity = new MemberBindEntity();
		
		// check
		// set bindKey, channel
		BeanUtils.copyProperties(memberBindEntity, dto);
		String bindStatus = checkMemberAndIsBind(
				dto.getUsername(), dto.getPassword(), memberBindEntity);
		
		// bind
		if(MemberBindStatus.UNBINDED.getStatus().equals(bindStatus)) {
			memberBindEntity.setBindType(MemberBindEntity.BindType.LOGIN_BIND);
			memberBindEntity.setStatus(MemberBindEntity.Status.STATUS_ON); 
			memberBindRepository.bindMember(memberBindEntity);
			return MemberBindStatus.BIND_SUCCESS.getStatus();
		}
		return bindStatus; 
	}

	public String checkMemberAndIsBind(
			String username, 
			String password,
			MemberBindEntity memberBindEntity) {
		Member member = memberRepository.queryByUsernameOrCellphoneOrEmail(username);
        if (member != null) {
        	if(!member.validate(password)) {
        		// wrong password 
        		return MemberBindStatus.INVALID.getStatus();
        	}
        	memberBindEntity.setMemberId(member.getMemberID());
        	memberBindEntity.setMcMemberCode(member.getMcMemberCode());
        	
        	int count = memberBindRepository.queryBindMemberCount(memberBindEntity);
        	if(count > 1) {
        		// 绑定异常：一个账户多次绑定
        		logger.error("member bind error:multipile bind! {}", JaxbUtils.convertToXmlString(memberBindEntity));
        		return MemberBindStatus.BIND_ERROR.getStatus();
        	}
        	boolean isBinded = ( count == 0 ? false : true);
        	return isBinded == true ? MemberBindStatus.BINDED.getStatus() : 
        		MemberBindStatus.UNBINDED.getStatus();
        }
		return MemberBindStatus.NOT_FOUND.getStatus();
	}
	
	public String registBind(RegisterMemberBindDto registerMemberBindDto) throws Exception {
		MemberRegisterDto memberRegisterDto = registerMemberBindDto.getMemberRegisterDto();
		
		MemberBindDto memberBindDto = registerMemberBindDto.getMemberBindDto();
		
		validationRegister(memberBindDto,memberRegisterDto);
		
		//检查channel和key是否重复
		String id = memberBindRepository.getMemberIdByKey(memberBindDto.getBindKey(), memberBindDto.getChannel());
		if(!StringUtils.isBlank(id)) {
			logger.error("channel and bindKey have used!");
			throw new IllegalArgumentException(
					"memberBindDto is invalid: channel and bindKey have used!");
		}
		
		//  complete register
		CRMResponseDto crmResponseDto = (CRMResponseDto)memberResource.addVIPMembership(memberRegisterDto).getEntity();
		
		if(logger.isDebugEnabled()){
			logger.debug(
					String.format("memberBindService?crmResponseDto=>%s "
							+ "registerMemberBindDto=>%s", 
							JaxbUtils.convertToXmlString(crmResponseDto), 
							JaxbUtils.convertToXmlString(registerMemberBindDto)));
		}
		
		if(!crmResponseDto.isExecSuccess() || StringUtils.isBlank(crmResponseDto.getMcMemberCode())) {
			return MemberBindStatus.REGISTER_ERROR.getStatus();
		}
		
		String mcMemberCode = crmResponseDto.getMcMemberCode();
		String memberId = memberService.getMemberIDByMcMemberCode(mcMemberCode);
		
		// bind
		if (!StringUtils.isBlank(memberId)) {
			 
			MemberBindEntity memberBindEntity = new MemberBindEntity();
			BeanUtils.copyProperties(memberBindEntity, memberBindDto);
			 
			memberBindEntity.setMcMemberCode(mcMemberCode);
			memberBindEntity.setMemberId(memberId);
			memberBindEntity.setStatus( MemberBindEntity.Status.STATUS_ON );
			memberBindEntity.setBindType( MemberBindEntity.BindType.REGISTER_BIND );
			
			memberBindRepository.bindMember(memberBindEntity);
			 
			return MemberBindStatus.BIND_SUCCESS.getStatus();
         }
		
		 return MemberBindStatus.BIND_ERROR.getStatus();
	}
	
	public MemberBindCardDto getMemCardInfoByKey(String key, String channel) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(channel)) {
			throw new IllegalArgumentException("key or channel is blank!");
		}
		String memberId = memberBindRepository.getMemberIdByKey(key, channel);
		if(StringUtils.isBlank(memberId)) {
			// member unbinded
			logger.error("member unbinded!", key + "&" + channel);
			memberBindOpLog("getMemCardInfoByKey:member unbinded!", 
					key + "&" + channel);
			return new MemberBindCardDto();
		}
		Member member = memberRepository.getMemberByMemberID(memberId);
		if(ObjectUtils.equals(null, member)) {
			// binded but not found
			logger.error("member not found!", memberId);
			memberBindOpLog("getMemCardInfoByKey:member not found!", 
					memberId);
			return new MemberBindCardDto();
		}
		String cardNo = member.getCardNo();
		MemberBindCardDto dto = new MemberBindCardDto();
		for(MemberMemCard card : member.getCardList()){
			if(card.getxCardNum().equals(cardNo)) {
			    dto.setCardLevel(card.getCardTypeCd());
			    dto.setCardNumber(cardNo);
			    dto.setExpireDate(card.getDueDate());
				break;
			}
		}
		MemberScoreLevelInfoDto levelDto = memberScoreLevelInfoRepository.getMemberScoreInfo(
				String.valueOf(member.getId()));
		if(ObjectUtils.equals(null, levelDto)) {
			logger.error("get rank score error: {}", key + "&" + channel);
			return dto;
		}
		dto.setRankScore(levelDto.getRankScore());
		return dto;
	}
	
	public void memberBindOpLog(String msg, Object obj) {
		try {
			String content = "";
			if(obj instanceof MemberBindDto) {
				content = JaxbUtils.convertToXmlString(obj);
			} else {
				content = String.valueOf(obj);
			}
			OpRecordLogDomain opRecord = new OpRecordLogDomain(
					EnumOpType.MEMBER_BIND,
					msg,
					content);
			if(!MemberBindStatus.BIND_SUCCESS.getStatus().equals(msg)) {
				opRecordLogRepository.insert(opRecord);
			}
		} catch(Exception ex) {
			logger.error("memberBindOpLog error: ", ex);
		}
	}
	
	private void validationBind(MemberBindDto dto) {
		if(ObjectUtils.equals(null, dto) 
				|| StringUtils.isBlank(dto.getUsername()) 
				|| StringUtils.isBlank(dto.getPassword())
				|| StringUtils.isBlank(dto.getChannel())
				|| StringUtils.isBlank(dto.getBindKey())) {
			logger.error("memberBindDto invalid");
			throw new IllegalArgumentException(
					"memberBindDto is invalid: requested field is blank!");
		}
		//检查channel和key是否重复
		String id = memberBindRepository.getMemberIdByKey(dto.getBindKey(), dto.getChannel());
		if(!StringUtils.isBlank(id)) {
			logger.error("bindKey have used for channel:{}!", dto.getChannel());
			memberBindOpLog("member bind error:key used!", dto);
			throw new IllegalArgumentException(
					"memberBindDto is invalid: bindKey have used for channel!");
		}
	}
	
	private void validationRegister(MemberBindDto dto,MemberRegisterDto registerDto) {
		if( dto == null || registerDto == null 
				|| registerDto.getMemberInfoDto() == null
				|| StringUtils.isBlank(dto.getChannel())
				|| StringUtils.isBlank(dto.getBindKey())
			) {
			logger.error("memberBindDto invalid");
			throw new IllegalArgumentException(
					"memberBindDto is invalid: requested field is blank!");
		}
		
		if( StringUtils.isBlank(registerDto.getRegisterTag())){
			registerDto.setRegisterTag( dto.getChannel() );
		}
		
	}
	
	
	public MemberInfoResultDto getMemberInfo(String channel,String bindKey)throws Exception{
		if(StringUtils.isBlank(channel) || StringUtils.isBlank(bindKey)){
			throw new IllegalArgumentException("channel or bindKey is invalid: requested field is blank!");
		}
		String memberID = memberBindRepository.getMemberIdByKey(bindKey, channel);
		
		if( StringUtils.isBlank(memberID) ){
			throw new NotFoundException("not fount memberInfo from channel and bindKey!");
		}
		List<Member> result = memberRepository.queryByMemberID(memberID);
		
		if( result == null || result.size() <= 0 ){
			throw new NotFoundException("not fount memberInfo from channel and bindKey!");
		}
		
		if( result.size() > 1 ){
			throw new IllegalStateException("member Info Error,a memberID corresponding to more result!");
		}
		Member member = result.get(0);
		MemberInfoResultDto memberInfoReult = new MemberInfoResultDto();
		memberInfoReult.setUserName( member.getFullName() );
		memberInfoReult.setTitle( member.getTitle() );
 		
		return memberInfoReult;
	}
	
	public MemberCouponInfosDto getCouponInfo(String channel,String bindKey)throws Exception{
		if(StringUtils.isBlank(channel) || StringUtils.isBlank(bindKey)){
			logger.debug("channel or bindkey is empty!");
			return new MemberCouponInfosDto();
		}
		String mcMemberCode = memberBindRepository.getMcMemberCodeByKey(bindKey, channel);
		
		if( StringUtils.isBlank(mcMemberCode) ){
			logger.debug("mcCode is empty!");
			return new MemberCouponInfosDto();
		}
		
		List<CouponRuleDto> result = cbpProxy.getCouponInfo(mcMemberCode).getResults();
		
		if( result == null || result.size() <= 0 ){
			logger.debug("result is empty!");
			return new MemberCouponInfosDto();
		}
		
		return populate(result);
	}
	
	public MemberCouponInfosDto populate(List<CouponRuleDto> result){
		
		
		MemberCouponInfosDto memberCouponInfosDto = new MemberCouponInfosDto();
		
		List<MemberCouponInfoDto> results = new ArrayList<MemberCouponInfoDto>();
		for( CouponRuleDto dto : result){
			MemberCouponInfoDto memberCouponInfoDto = new MemberCouponInfoDto();
			BigDecimal couponParValue = dto.getCouponParValue();
			memberCouponInfoDto.setCouponParValue(couponParValue);
			Integer count = dto.getCount();
			memberCouponInfoDto.setCount(count);
			
			Map<BookingModule,Integer> moduleMap = dto.getUseSceneDto().getBookingSceneDto().getModuleMap();
			List<String> list = null;
			if( moduleMap != null && moduleMap.size()>0 ){
				list = new ArrayList<String>();
				for( BookingModule key : moduleMap.keySet() ){
					if( moduleMap.get(key)!=null && moduleMap.get(key)>0 ){
						list.add( key.getMessage() );
					}
				}
			}
			
			memberCouponInfoDto.setScope( list );
			
			CouponRuleType couponRuleType = dto.getCouponRuleType();
			memberCouponInfoDto.setCouponRuleType(couponRuleType);
			
			Date useEffectDate = dto.getUseEffectDate();
			memberCouponInfoDto.setUseEffectDate(useEffectDate);
			
			Date useueInvalidDate = dto.getUseInvalidDate();
			memberCouponInfoDto.setUseueInvalidDate(useueInvalidDate);
			
			results.add(memberCouponInfoDto);
		}
		
		
		memberCouponInfosDto.setResult(results);
		return memberCouponInfosDto;
	}

	
}
