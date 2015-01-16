package com.jje.membercenter.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.membercenter.AddressDto;
import com.jje.dto.membercenter.CardDto;
import com.jje.dto.membercenter.MemberAirLineCompany;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberJREZQueryDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MemberPrivilegeDto;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.membercenter.TravellPreferenceDto;
import com.jje.dto.membercenter.cardbind.PartnerCardBindDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMRespDto;
import com.jje.dto.membercenter.memberbind.MemberBindDto;
import com.jje.dto.vbp.callcenter.MemberCallcenterConditionDto;
import com.jje.membercenter.crm.CRMConstant;
import com.jje.membercenter.domain.AddressValue;
import com.jje.membercenter.domain.AppMember;
import com.jje.membercenter.domain.BaseData;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberScoreLevelInfo;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.ValidateCode;
import com.jje.membercenter.remote.crm.datagram.request.PartnerCardBindReq;
import com.jje.membercenter.remote.crm.datagram.response.PartnerCardBindRes;
import com.jje.membercenter.remote.crm.support.CrmResponse;
import com.jje.membercenter.service.MemberService;
import com.jje.vbp.bigcustomer.domain.BigCustomer;
import com.jje.vbp.bigcustomer.persistence.CustomerMapper;
import com.jje.vbp.memberCardLvChanelRel.service.MemberCardLvChanelRelService;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberRepositoryImpl.class);

    @Autowired
    private CustomerMapper customerMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MemberScoreLevelInfoMapper scoreLevelMapper;
	
	@Autowired
	private ValidateCodeMapper validateCodeMapper;

	@Autowired
	private WebMemberMapper webMemberMapper;

	@Value(value = "${member.add.password.hash}")
	private String memberAddPasswordHash;
	@Value(value = "${member.add.password.length}")
	private int memberAddPasswordLength;

	@Autowired
	private BaseDataMapper basedataMapper;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberCardLvChanelRelService memberCardLvChanelRelService;

	public List<Member> queryMemberByCardNo(MemberJREZQueryDto queryDto) {
		return memberMapper.queryMemberByCardNo(queryDto);
	}

	public List<Member> queryMember(MemberJREZQueryDto queryDto) {
		return memberMapper.queryMember(queryDto);
	}

	public Member queryByUsernameOrCellphoneOrEmail(String usernameOrCellphoneOrEmail) {
		Member member = memberMapper.queryByUsernameOrCellphoneOrEmail(usernameOrCellphoneOrEmail);
        if(member == null){
            return member;
        }
        if(StringUtils.isNotBlank(member.getmCustomerId())){
              BigCustomer bigCustomer = customerMapper.findByCrmId(member.getmCustomerId()) ;
              if(bigCustomer != null){
                  member.addChannels(bigCustomer.getChannel());
              }
        }
        member.addChannels(getMemberLevelRateChannels(member.getNewMemberHierarchy()));
		return member;
	}
	
	public Member queryMemberByLoginName1(String usernameOrCellphoneOrEmail) {
		Member member = memberMapper.queryByUsernameOrCellphoneOrEmail(usernameOrCellphoneOrEmail);
        if(member == null){
            return member;
        }
        member.addChannels(getMemberLevelRateChannels(member.getNewMemberHierarchy()));
		return member;
	}

	public Member queryMemberByLoginName2(String usernameOrCellphoneOrEmail) {
		Member member = memberMapper.queryMemberByLoginName2(usernameOrCellphoneOrEmail);
        if(member == null){
            return member;
        }
        member.addChannels(getMemberLevelRateChannels(member.getNewMemberHierarchy()));
		return member;
	}
	
    private List<String> getMemberLevelRateChannels(String memberHierarchy) {
        MemberDegree md = MemberDegree.getMemberDegree(memberHierarchy);
        if(md == null){
            return new ArrayList<String>();
        }
        return memberCardLvChanelRelService.queryChannelByKey("Channel_"+md.toString());
    }


    public MemberPrivilegeDto getPrivCardInfo(QueryMemberDto<String> queryDto) {
		// return crmMembershipProxy.getPrivCardInfo(queryDto);
		return null;
	}

	public TravellPreferenceDto getTravellPreference(String memberId) {
		// return crmMembershipProxy.getTravellPreference(memberId);
		return null;
	}

	public void updateTravellPreference(TravellPreferenceDto travellPreferenceDto) {
		// crmMembershipProxy.updateTravellPreference(travellPreferenceDto);

	}

	public CardDto queryCardStauts(String memberId, String cardNo) {
		// TODO Auto-generated method stub
		return null;
	}

	// start
	public List<BaseData> listMemberType() {
		List<BaseData> resultDto = basedataMapper.listMemberType();
		return resultDto;
	}

	public List<BaseData> listCertificateTypes() {
		List<BaseData> resultDto = basedataMapper.listCertificateTypes();
		return resultDto;
	}

	public List<BaseData> listRemindQuestionTypes() {
		List<BaseData> resultDto = basedataMapper.listRemindQuestionTypes();
		return resultDto;

	}

	public List<BaseData> listAddressTypes() {
		List<BaseData> resultDto = basedataMapper.listAddressTypes();
		return resultDto;

	}

	public List<BaseData> listEduTypes() {
		List<BaseData> resultDto = basedataMapper.listEduTypes();
		return resultDto;
	}

	public List<BaseData> listInvoiceTypes() {
		List<BaseData> resultDto = basedataMapper.listInvoiceTypes();
		return resultDto;
	}

	public List<BaseData> listTitleTypes() {
		List<BaseData> resultDto = basedataMapper.listTitleTypes();
		return resultDto;
	}

	public List<BaseData> listProvinceTypes() {
		List<BaseData> resultDto = basedataMapper.listProvinceTypes();
		return resultDto;
	}

	public List<BaseData> listCityTypes(String parRowId) {
		List<BaseData> resultDto = basedataMapper.listCityTypes(parRowId);
		return resultDto;
	}

	public List<BaseData> listTownTypes(String parRowId) {
		List<BaseData> resultDto = basedataMapper.listTownTypes(parRowId);
		return resultDto;
	}

	public List<Member> queryByCellphoneOrEmail(String cellphoneOrEmail) {
		List<Member> memberList = memberMapper.queryByCellphoneOrEmail(cellphoneOrEmail);
		return memberList;
	}

	public List<Member> keyInfoCheck(String keyInfo, String keyValue) {
		
		List<Member> memberList = new ArrayList<Member>();
		
		if("card_no".equals(keyInfo)){
			memberList = memberMapper.checkByCardNo(keyValue);
		}else if("identity_no".equals(keyInfo)){
			memberList = memberMapper.checkByID(keyValue);
		}else if("phone".equals(keyInfo)){
			memberList = memberMapper.checkByPhone(keyValue);
		}else if("email".equals(keyInfo)){
			memberList = memberMapper.checkByEmail(keyValue);
		}
		
		return memberList;
	}
	
	public List<BaseData> getPrice(String memberType) {
		// TODO Auto-generated method stub
		List<BaseData> resultDto = basedataMapper.getPrice(memberType);
		return resultDto;
	}

	public AddressDto getAddressValues(AddressDto nameDto) {
		// TODO Auto-generated method stub
		BaseData basedata = new BaseData();
		AddressValue addressValue = new AddressValue();

		basedata.setName(nameDto.getProvinceId());
		basedata.setType("STATE_ABBREV");
		List<BaseData> resultDto = basedataMapper.getBaseDataById(basedata);
		if (resultDto.size() > 0 && !"".equals(resultDto.get(0).getVal())) {
			addressValue.setProvinceValue(resultDto.get(0).getVal());
		}

		basedata.setName(nameDto.getCityId());
		basedata.setType("LOY_CITY_CD");
		List<BaseData> resultDto2 = basedataMapper.getBaseDataById(basedata);
		if (resultDto2.size() > 0 && !"".equals(resultDto2.get(0).getVal())) {
			addressValue.setCityValue(resultDto2.get(0).getVal());
		}

		basedata.setName(nameDto.getDistrictId());
		basedata.setType("LOY_COUNTY_CD");
		List<BaseData> resultDto3 = basedataMapper.getBaseDataById(basedata);
		if (resultDto3.size() > 0 && !"".equals(resultDto3.get(0).getVal())) {
			addressValue.setDistrictValue(resultDto3.get(0).getVal());
		}

		return addressValue.toDto();
	}

	// end

	public void storeMember(Member member) {
		memberMapper.insert(member);
	}

	public void deleteMemberByMemberId(String memberId) {
		memberMapper.deleteMemberByMemberId(memberId);
	}

	public void updateMember(Member member) {
		if (member.getEmail() != null && !"".equals(member.getEmail()))
			memberMapper.updateMemberUserNameByEmail(member);
		if (member.getCellPhone() != null && !"".equals(member.getCellPhone()))
			memberMapper.updateMemberUserNameByCellPhone(member);
		if (member.getCardNo() != null && !"".equals(member.getCardNo()))
			memberMapper.updateMemberUserNameByCardNo(member);
		memberMapper.update(member);
	}

	public List<Member> getMemberCardNo(String memberId) {
		// TODO Auto-generated method stub
		List<Member> resultDto = memberMapper.getMemberCardNo(memberId);
		return resultDto;
	}

	public void updatePwd(Member member) {
		memberMapper.updatePwd(member);
	}

	public Member queryByCellphone(String cellphone) {
		Member member = memberMapper.queryByCellphone(cellphone);
		return member;
	}

	public Member queryByEmail(String email) {
		Member member = memberMapper.queryByEmail(email);
		return member;
	}

	public List<Member> queryByCode(String memberId) {
		List<Member> resultDto = memberMapper.queryByCode(memberId);
		return resultDto;
	}

	public Member queryByMemNum(String memNum) {
		List<Member> members = this.queryByCode(memNum);
		if (members != null) {
			return members.get(0);
		}
		return null;
	}

	public Member validatePhone(String cellphone) {
		Member member = memberMapper.validatePhone(cellphone);
		return member;
	}

	public Member validateMail(String mail) {
		Member member = memberMapper.validatePhone(mail);
		return member;
	}

	public List<BaseData> listHierarchyTypes() {
		List<BaseData> resultDto = basedataMapper.listHierarchyTypes();
		return resultDto;
	}

	public String getCardLevelByOrderBy(String name) {
        if(StringUtils.isNotBlank(name)){
            return basedataMapper.getCardLevel(name);
        }
        return null;
	}

	public List<Member> queryByMemberID(String memberID) {
		List<Member> memberList = memberMapper.queryByMemberID(memberID);
		return memberList;
	}

	public List<MemberVerfy> getRepeatMenName(Member member) {
		return memberMapper.queryRepeatMenName(member);
	}

	public void updateQuestion(Member member) {
		memberMapper.updateQuestion(member);
	}

	public List<MemberVerfy> queryRegisterByCellphoneOrEmailOrCardNo(String menName) {
		return memberMapper.queryRegisterByCellphoneOrEmailOrCardNo(menName);
	}

	public List<Member> queryIdentifyInfo(Member member) {
		return memberMapper.queryIdentifyInfo(member);
	}

	public void storeMemberInfo(Member member) {
		memberMapper.insertMemberInfo(member);
	}

	public long getSequence(Member member) {
		return memberMapper.getSequence(member);
	}

	public void storeMemberCard(MemberMemCard memberMemCard) {
		memberMapper.insertMemberCard(memberMemCard);
	}

	public void storeMemberVerify(MemberVerfy memberVerfy) {
		memberMapper.insertMemberVerify(memberVerfy);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMemberInfoAndVerify(Member member) {
		updateVerify(member);
		memberMapper.updateMemberInfo(member);
	}

	public  void updateVerify(Member member) {
		if (StringUtils.isNotEmpty(member.getEmail())){
			saveOrUpdateMemberVerify(member, member.getEmail(),memberMapper.updateMemberVerifyMemNameByEmail(member));

		}
		if (StringUtils.isNotEmpty(member.getCellPhone())){
			saveOrUpdateMemberVerify(member, member.getCellPhone(),memberMapper.updateMemberVerifyMemNameByCellPhone(member));
		}
		if (StringUtils.isNotEmpty(member.getCardNo())){
			saveOrUpdateMemberVerify(member, member.getCardNo(),memberMapper.updateMemberVerifyMemNameByCardNo(member));
		}
	}

	private void saveOrUpdateMemberVerify(Member member,String menName, int updateRows) {
		if(updateRows<=0){
			saveMemberVerify(menName,member.getMemberID(),member.getMemberCode(),member.getPassword(),member.getId());
		}
	}

	public void updateMemberInfo(Member member) {
		memberMapper.updateMemberInfo(member);
	}

	public void updateMemberCard(MemberMemCard memberMemCard) {

		memberMapper.updateMemberCard(memberMemCard);
	}

	public List<MemberMemCard> queryMemberMemCardByMemberId(String memberID) {
		return memberMapper.queryMemberMemCardByMemberId(memberID);
	}

	public void updateMemberMemCardTwoCondition(MemberMemCard memberMemCard) {
		memberMapper.updateMemberMemCardTwoCondition(memberMemCard);
	}

	public List<MemberMemCard> queryMemberMemCardThreeCondition(MemberMemCard memberMemCard) {
		return memberMapper.queryMemberMemCardThreeCondition(memberMemCard);
	}

	public void updateMemberMemCardById(MemberMemCard memberMemCard) {
		memberMapper.updateMemberMemCardById(memberMemCard);
	}

	public List<MemberMemCard> queryMemberMemCardThreeConditionTwo(MemberMemCard memberMemCard) {
		return memberMapper.queryMemberMemCardThreeConditionTwo(memberMemCard);
	}

	public void storeAllMemberInfo(MemberDto memberDto, Member member) throws Exception {
		logger.debug("****insert memberInfo start");
		this.storeMemberInfo(member);// 存一条到MemberInfo表
		logger.debug("****insert memberInfo end");

		logger.debug("****get sequence start");
		// 取得memberInFo保存时的id;
		long id = this.getSequence(member);
		logger.info("****get sequence =" + id);
		memberDto.setId(id);
		logger.debug("****insert memberCard start");
		List<MemberMemCardDto> memberMemCardDtoList = memberDto.getCardList();
		String airLineCardVerifyInfo="";
		if (memberMemCardDtoList != null && memberMemCardDtoList.size() > 0) {
			airLineCardVerifyInfo = saveCardList(memberDto,	memberMemCardDtoList);
		}
		logger.debug("****insert memberCard end");
		storeMemberCardAndEmailAndPhoneVerify(memberDto, airLineCardVerifyInfo);
		scoreLevelMapper.insert(new MemberScoreLevelInfo(memberDto));
	}

	private String saveCardList(MemberDto memberDto,List<MemberMemCardDto> memberMemCardDtoList) {
		String airLineCardVerifyInfo="";
		for (MemberMemCardDto memberMemCardDto : memberMemCardDtoList) {
			MemberMemCard memberMemCard = new MemberMemCard(memberMemCardDto);
			memberMemCard.setMemInfoId(memberDto.getId());
			memberMemCard.setMemId(memberDto.getMemberID());
			airLineCardVerifyInfo = MemberAirLineCompany.getCardLoginName(memberMemCardDto);
			this.storeMemberCard(memberMemCard);// 存该会员的1条或2条卡信息到MemberCard表
		}
		return airLineCardVerifyInfo;
	}



	private void saveMemberVerify(String menName,String memId,String memNum,String pwd,Long id) {
		if(StringUtils.isNotEmpty(menName)){
			this.storeMemberVerify(new MemberVerfy(id,memId,memNum,menName,pwd));
		}
	}



	@SuppressWarnings("deprecation")
	public void storeMemberCardAndEmailAndPhoneVerify(MemberDto memberDto,String... airLineCardVerifyInfo) throws Exception {
		String memberID = memberDto.getMemberID();
		String memberCode = memberDto.getMemberCode();
		String password = memberDto.getPassword();
		Long id=memberDto.getId();
		List<MemberVerfy> memberCardVerfy = queryRegisterByCellphoneOrEmailOrCardNo(memberDto.getCardNo());
		if(memberCardVerfy.isEmpty()){
			saveMemberVerify(memberDto.getCardNo(),memberID,memberCode,password,id);
		}
		List<MemberVerfy> memberPhoneVerfy = queryRegisterByCellphoneOrEmailOrCardNo(memberDto.getCellPhone());
		if(memberPhoneVerfy.isEmpty()){
			saveMemberVerify(memberDto.getCellPhone(),memberID,memberCode,password,id);
		}
		List<MemberVerfy> memberEmailVerfy = queryRegisterByCellphoneOrEmailOrCardNo(memberDto.getEmail());
		if(memberEmailVerfy.isEmpty()){
			saveMemberVerify(memberDto.getEmail(),memberID,memberCode,password,id);
		}
		if(airLineCardVerifyInfo .length>0){
			List<MemberVerfy> memberAirVerfy = queryRegisterByCellphoneOrEmailOrCardNo(airLineCardVerifyInfo[0]);
			if(memberAirVerfy.isEmpty()){
				saveMemberVerify(airLineCardVerifyInfo[0],memberID,memberCode,password,id);
			}
		}
	}

	public void saveJJINNCardVerify(MemberDto memberDto) {
		if(memberDto.getCardList()!=null && !memberDto.getCardList().isEmpty()){
			List<MemberMemCardDto>  cards=  memberDto.getCardList();
			for(MemberMemCardDto card : cards){
				if (StringUtils.isNotBlank(card.getxCardNum()) && !card.getxCardNum().equals(memberDto.getCardNo())) {
					saveMemberVerify(card.getxCardNum().toLowerCase(),memberDto.getMemberID(),memberDto.getMemberCode(),memberDto.getPassword(),memberDto.getId());
				}
			}
		}
	}


	public void updateMemberInfoAndStoreVerify(Member member, MemberDto memberDto) throws Exception {
		this.updateMemberInfo(member);
		this.storeMemberCardAndEmailAndPhoneVerify(memberDto);
		saveJJINNCardVerify(memberDto);

	}

	@SuppressWarnings("deprecation")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMemberInfoAndCard(MemberDto memberDto) throws Exception {
		this.updateMemberInfo(fullMemberInfoByCard(memberDto));// 更新一条会员信息表记录
		List<MemberMemCardDto> cardList = memberDto.getCardList();// 取得卡表List
		
		for(MemberMemCardDto memberMemCardDto : cardList){
			MemberMemCard memberMemCard = new MemberMemCard(memberMemCardDto);
			memberMemCard.setMemId(memberDto.getMemberID());
			
			if (NewCardType.GOLD_CARD.getCode().equals(memberMemCardDto.getCardTypeCd()) 
					|| NewCardType.PLATINUM_CARD.getCode().equals(memberMemCardDto.getCardTypeCd())
					|| NewCardType.BLACK_CARD.getCode().equals(memberMemCardDto.getCardTypeCd())) {// 卡类型 
				String xCardNum = memberMemCardDto.getxCardNum();
				String cardNo = (memberDto.getCardNo() == null) ? "" : memberDto.getCardNo();

				if (!cardNo.equals(xCardNum)) {// card_Num != 电商卡号
//					memberMemCard.setCardNo(cardNo);
					this.updateMemberMemCardTwoCondition(memberMemCard);// memberId和x_card_num两个条件
				} else {// card_Num == 电商卡号
					memberMemCard.setCardNo(cardNo);
					// 查看是否存在其他电商的卡,礼卡除外
					List<MemberMemCard> memberMemCardListThreeCondition = this.queryMemberMemCardThreeCondition(memberMemCard);// memberId和card_type_cd和x_card_num=cardNo(电商卡号)三个条件
					if (memberMemCardListThreeCondition != null && memberMemCardListThreeCondition.size() != 0) {// 存在更新
						long id = memberMemCardListThreeCondition.get(0).getId();
						memberMemCard.setId(id);
						this.updateMemberMemCardById(memberMemCard);
					} else {// 不存在插入
						List<Member> memberList = this.queryByMemberID(memberDto.getMemberID());
						long id = memberList.get(0).getId();
						memberMemCard.setMemInfoId(id);// 外键
						this.storeMemberCard(memberMemCard);
					}
				}
			} else if (NewCardType.SILVER_CARD.getCode().equals(memberMemCardDto.getCardTypeCd())) {// 卡类型 =礼卡
				logger.warn("Error: cardType is SILVER_CARD");
				throw new Exception("cardType is SILVER_CARD");
			}
		}
	}

	@SuppressWarnings("deprecation")
	private Member fullMemberInfoByCard(MemberDto memberDto) {
		Member member = new Member();
		member.setNewMemberHierarchy(memberDto.getNewMemberHierarchy());
		member.setMemberHierarchy(memberDto.getMemberHierarchy());
		member.setCardLevel(memberDto.getCardLevel());
		member.setCardNo(memberDto.getCardNo());
		member.setCardType(memberDto.getCardType());
		member.setMemberID(memberDto.getMemberID());
		return member;
	}

	public AppMember getAppMemberByMcMemberCode(String mcMemberCode, Boolean flag) {
		AppMember member;
//		if (flag) {
//			member = webMemberMapper.getProMemberByMcMemberCode(mcMemberCode);
//		} else {
//			member = memberMapper.getFullMemberByMcMemberCode(mcMemberCode);
//		}
		
		member = memberMapper.getFullMemberByMcMemberCode(mcMemberCode);
        if(member == null) {
            throw new RuntimeException(String.format("mcMemberCode = %s,flag=%s Not Found ",mcMemberCode,flag));
        }
        member.setCardLevel(getCardLevelByOrderBy(member.getCardLevel()));
        Member completeMember = new Member();

        if(StringUtils.isNotBlank(member.getmCustomerId())){
            //查询大客户对应的渠道
            BigCustomer bigCustomer = customerMapper.findByCrmId(member.getmCustomerId()) ;
            if(bigCustomer != null){
                completeMember.addChannels(bigCustomer.getChannel());
            }
        }
        // 层级对应的渠道
        completeMember.addChannels(getMemberLevelRateChannels(member.getNewMemberHierarchy()));
        member.setChannels(completeMember.getChannels());
		return member;
	}

	public boolean isExistMember(String email, String phone) {
		Map map = new HashMap();
		map.put("email", email);
		map.put("phone", phone);
		Long total = memberMapper.sumMember(map);
		if (total > 0) {
			return true;
		}
		return false;
	}

	public boolean isExistMemberOfEmail(String email) {
		return isExistMember(email, null);
	}

	public boolean isExistMemberOfPhone(String phone) {
		return isExistMember(null, phone);
	}

	public boolean isExistAirAndCardNo(String cardNo, String partnerCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cardNo", cardNo);
		map.put("partnerCode", partnerCode);
		return memberMapper.isExistAirAndCardNo(map) > 0 ? true : false;
	}

	public boolean partnerCardBind(PartnerCardBindDto dto) throws InterruptedException {
		PartnerCardBindReq request = new PartnerCardBindReq();
		PartnerCardBindRes response = new PartnerCardBindRes();
		PartnerCardBindReq.DefaultRequestHead head = request.getHead();
		PartnerCardBindReq.RequestBody body = request.getBody();
		body.setMembid(memberService.getMemberCodeByMcMemberCode(dto.getMcMemberCode()));
		body.setAccpointflg(MemberScoreType.MILEAGE.equals(dto.getPartnerFlag()) ? CRMConstant.FLAG_TRUE
				: CRMConstant.FLAG_FALSE);
		if (dto.getPartnerFlag().equals(MemberScoreType.MILEAGE)) {
			body.setMembcdtyp(dto.getPartnerCode().name());
			body.setMemcdno(dto.getPartnerCardNo());
		}
		request.setHead(head);
		request.setBody(body);
		response = request.send();
		if (response.isStatus(CrmResponse.Status.ERROR)) {
			throw new InterruptedException();
		}
		logger.info("responseCode:" + response.getHead().getRetcode());
		if (CrmResponse.Status.SUCCESS.getCode().equals(response.getHead().getRetcode())) {
			return true;
		}
		return false;
	}
	
	public String getRandomPassword() {
		int length = memberAddPasswordHash.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < memberAddPasswordLength; i++) {
			Random random = new Random();
			int nextInt = random.nextInt(length);
			sb.append(memberAddPasswordHash.charAt(nextInt));
		}
		return sb.toString();
	}

	public Member querySingleMemberByCardNo(String cardNo) {
		return memberMapper.querySingleMemberByCardNo(cardNo);
	}

	public List<Member> checkIdentifyInfo(Member member) {
		return memberMapper.checkIdentifyInfo(member);
	}
	
	public Member getMemberByIdentify(String identityType, String identityNo) {
		return memberMapper.getMemberByIdentify(identityType, identityNo);
	}

	public String generateMcMemberCode() {
		return memberMapper.generateMcMemberCode();
	}

	public Member getMemberByMcMemberCode(String code) {
		return memberMapper.getMemberByMcMemberCode(code);
	}

	public Member getMemberByMemberID(String memberID) {
		return memberMapper.getMemberByMemberID(memberID);
	}

	public Member getMemberByMemberNum(String memberNum) {
		return memberMapper.getMemberByMemberNum(memberNum);
	}

	public void updatePwdByMcMemberCode(String mcMemberCode, String password) {
		memberMapper.updatePwdByMemInfoId(memberService.getMemberInfoIdByMcMemberCode(mcMemberCode), password);
	}

	public String getMcMemberCodeByPhone(String phone) {
		List<String> mcMemberCodeByPhone = memberMapper.getMcMemberCodeByPhone(phone);
		for (String mcMemberCode : mcMemberCodeByPhone) {
			if (StringUtils.isNotBlank(mcMemberCode)) {
				return mcMemberCode;
			}
		}
		return null;
	}

	public String getMcMemberCodeByMemberNum(String memberNum) {
		return memberMapper.getMcMemberCodeByMemberNum(memberNum);
	}
	
	public String getMcMemberCodeByMemberId(String memberId) {
		return memberMapper.getMcMemberCodeByMemberId(memberId);
	}

	public Member getMemberByCardNo(String cardNo) {
		return memberMapper.getMemberByCardNo(cardNo);
	}

	public void deleteMemberVerifyByMemberCode(String memberCode) {
		memberMapper.deleteMemberVerifyByMemberCode(memberCode);
	}

	public List<MemberVerfy> getMemberVerfyByMemberCode(String memberCode) {
		return memberMapper.getMemberVerfyByMemberCode(memberCode);
	}

	public void updateMemIpAddressByMC(String ipAddress, String mcMemberCode) {
        if(StringUtils.isNotBlank(ipAddress)){
            memberMapper.updateMemIpAddressByMC(ipAddress, mcMemberCode);
        }
	}

	public void resetMcMemberCode(String mcMemberCode) {
		memberMapper.resetMcMemberCode(mcMemberCode);
	}

	public Boolean identityIsExists(Member member) {
		  return memberMapper.countWithIdentity(member.getIdentityType(), member.getIdentityNo())  > 0;
	}

	public MemberMemCard getCardInfoByCardno(String cardNo) {
		try {
			return memberMapper.getCardInfoByCardno(cardNo);
		} catch (Exception e) {
			logger.error("getCardInfoByCardno({}) error!", cardNo, e);
			return null;
		}
	}

	public List<Member> queryMember(MemberCallcenterConditionDto condition) {
		return memberMapper.queryMemberForAdmin(condition);
	}
	
	public Member getMemberById(Long id) {
		return memberMapper.getMemberById(id);
	}

	public List<MemberMemCard> queryMemberCardInfos(Long id) {
		return memberMapper.queryMemberCardInfos(id);
	}
	
	@SuppressWarnings("deprecation")
	public MemberForCRMRespDto upgradeMemberInfo(MemberDto memberDto) {
		MemberForCRMRespDto crmResp = new MemberForCRMRespDto();
		if(memberDto == null){
			crmResp.setStatus("memberDto不能为空");
			return crmResp;
		}
		try {
			if (StringUtils.isBlank(memberDto.getMemberID())) {
				logger.warn("memberId is null");
				crmResp.setStatus("数据格式错误，MemberId不能为空");
				return crmResp;
			}

			crmResp.setMemberId(memberDto.getMemberID());
			List<Member> memberList = memberMapper.queryByMemberID(memberDto.getMemberID());
			if (memberList == null || memberList.size() == 0) {
				logger.warn("the record for the memberId is not existed");
				crmResp.setStatus("NoMember");
				return crmResp;
			}
			fillMemberDto(memberDto, memberList);// 填充memberDto信息
			updateMemberInfoAndVerify(new Member(memberDto));
			crmResp.setStatus("数据更新成功");
		} catch (Exception e) {
			logger.error("update member error:{}", JaxbUtils.convertToXmlString(memberDto), e);
			crmResp.setStatus("数据更新失败");
		}
		return crmResp;
	}
	
	@SuppressWarnings("deprecation")
	private void fillMemberDto(MemberDto memberDto, List<Member> memberList) {
		if (StringUtils.isBlank(memberDto.getMemberCode())) { 
			memberDto.setMemberCode(memberList.get(0).getMemberCode());
		}
		if (memberDto.getId() == 0) {
			memberDto.setId(memberList.get(0).getId());
		}
		if(StringUtils.isBlank(memberDto.getPassword())) {
			memberDto.setPassword(memberList.get(0).getPassword());
		}
	}

	public String addValidateCode(ValidateCode code) {
		int sendTimes = validateCodeMapper.countTodayCodes(code.getReceiver());
		if(sendTimes>=5){
			return "OVER 5 TIMES";
		}
		ValidateCode lastValidateCode = new ValidateCode();
		lastValidateCode = validateCodeMapper.getLastCodeByReceiver(code.getReceiver());
		if(lastValidateCode!=null&&System.currentTimeMillis()-lastValidateCode.getCreateTime().getTime()<60*1000){
			return "WAIT 60 SEC";
		}
		validateCodeMapper.addCode(code);
		return "OK";
	}
	
	public boolean checkValidateCode(ValidateCode code) {
		ValidateCode validateCode = validateCodeMapper.getLastCodeByReceiver(code.getReceiver());
		if(validateCode==null){
			return false;
		}
		if(System.currentTimeMillis()-validateCode.getCreateTime().getTime()>30*60*1000){
			return false;
		}
		if(code.getCode().equals(validateCode.getCode())){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateVerifyStatus(String userName, String pwd) {
		
		//  查询出v2表中的对应的infoID  已有
		long infoID;
		List<MemberVerfy> list = memberMapper.queryRegisterByCellphoneOrEmailOrCardNo2(userName);
		if(list!=null && list.size()>0){
			infoID = list.get(0).getMemInfoId();
		}else{
			return false;
		}
		
		//  通过infoID 更新v2表中的状态     新建
		int updateFlag = memberMapper.updateMemberVerifyMemNameByLoginName(infoID);
		if(updateFlag==0){
			return false;
		}
		
		
		//  通过infoID 查询出所有的v2表中的记录 list 已有
		List<MemberVerfy> list2 = memberMapper.queryVerifyInfoByInfoId2(infoID);
		
		//  通过list 插入v1表
		for (MemberVerfy memberVerfy : list2) {
			memberVerfy.setPassword(pwd);
			memberMapper.insertMemberVerify(memberVerfy);
		}
		return true;
	}

}
