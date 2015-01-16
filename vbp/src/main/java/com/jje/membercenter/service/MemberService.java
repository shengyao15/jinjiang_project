package com.jje.membercenter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.spi.NotAcceptableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jje.common.bam.BamDataCollector;
import com.jje.common.bam.BamMessage;
import com.jje.common.bam.StatusCode;
import com.jje.common.utils.CommonUtils;
import com.jje.common.utils.ExceptionToString;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.MD5Utils;
import com.jje.dto.coupon.issue.CouponSysIssueResult;
import com.jje.dto.coupon.issue.upgrade.UpgradeIssueDto;
import com.jje.dto.membercenter.AccountMergeDto;
import com.jje.dto.membercenter.MemType;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberJREZQueryDto;
import com.jje.dto.membercenter.MemberJREZResultDto;
import com.jje.dto.membercenter.MemberJREZResultsDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.NewCardType;
import com.jje.dto.membercenter.ReferrerInfoDto;
import com.jje.dto.membercenter.ReferrerRegistResult;
import com.jje.dto.membercenter.RegistChannel;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.domain.Member;
import com.jje.membercenter.domain.MemberMemCard;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.membercenter.domain.MemberVerfy;
import com.jje.membercenter.domain.OpRecordLogDomain;
import com.jje.membercenter.domain.OpRecordLogDomain.EnumOpType;
import com.jje.membercenter.domain.ReferrerInfo;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.persistence.OpRecordLogRepository;
import com.jje.membercenter.persistence.ReferrerRepository;
import com.jje.membercenter.persistence.WebMemberRepository;
import com.jje.membercenter.remote.handler.MBPHandler;
import com.jje.membercenter.remote.handler.MemberHandler;
import com.jje.membercenter.remote.handler.NBPHandler;
import com.jje.membercenter.xsd.MemberInfoUpdateResponse;
import com.jje.vbp.handler.CbpHandler;
import com.jje.vbp.handler.EbpHandler;

@Component
public class MemberService {

	private static final Logger LOG = LoggerFactory
			.getLogger(MemberService.class);

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private WebMemberRepository webMemberRepository;

	@Autowired
	private ReferrerRepository referrerRepository;

	@Autowired
	private MemberHandler memberHandler;

	@Autowired
	private MergeOrderSerive mergeOrderSerive;

	@Autowired
	private MBPHandler mbpHandler;

	@Autowired
	private CbpHandler cbpHandler;

	@Autowired
	private NBPHandler nbpHandler;

	@Autowired
	private EbpHandler ebpHandler;

	@Autowired
	private CRMMembershipRepository crmMembershipRepository;

	@Autowired
	private BamDataCollector bamDataCollector;
	
	@Autowired
	private OpRecordLogRepository opRecordLogRepository;

	private static final String ACTIVE_SUCCESS = "00001";
	private static final String ACTIVE_FAILURE = "00002";

	public ReferrerRegistResult saveReferrerInfo(ReferrerInfoDto dto)
			throws Exception {
		ReferrerRegistResult result = new ReferrerRegistResult();
		result.setStatus(ReferrerRegistResult.ReferrerRegistStatus.SUCCESS);
		Member member = memberRepository.querySingleMemberByCardNo(dto
				.getReferrerCardNo());
		if (member == null) {
			result.setStatus(ReferrerRegistResult.ReferrerRegistStatus.REFERRER_NOT_EXIST);
			return result;
		}
		referrerRepository.save(new ReferrerInfo(dto));
		return result;
	}

	public MemberJREZResultsDto getMemberJREZResult(MemberJREZQueryDto queryDto) {
		MemberJREZResultsDto results = new MemberJREZResultsDto();
		List<Member> members = null;
		if (StringUtils.isBlank(queryDto.getCardNo())) {
			members = memberRepository.queryMember(queryDto);

		} else {
			members = memberRepository.queryMemberByCardNo(queryDto);
		}
		if (!CommonUtils.isEmptyCollection(members)) {
			for (Member m : members) {
				results.getResult().add(getMemberJREZResult(m));
			}
		}
		return results;
	}

	private MemberJREZResultDto getMemberJREZResult(Member m) {
		MemberJREZResultDto result = new MemberJREZResultDto();
		result.setAddress(memberHandler.getAddress(m.getMemberCode()));
		result.setCardNo(m.getCardNo());
		result.setEmail(m.getEmail());
		result.setName(m.getFullName());
		result.setPhone(m.getCellPhone());
		MemberDegree degree = MemberDegree.getMemberDegree(m.getNewMemberHierarchy());
		if (null != degree)
			result.setScoreHierarchy(degree.getAlias());
		return result;
	}

	public Member getMemberByMcMemberCode(String code) {
		LOG.debug("getMemberByMcMemberCode({})", code);
		Member member = memberRepository.getMemberByMcMemberCode(code);
		if (member != null) {
			LOG.debug("getMemberByMcMemberCode({}) result Member:{}", code,
					member.getId());
			return member;
		}
		LOG.warn("getMemberByMcMemberCode({}) no result!", code);
		return null;
	}

	public String getMemberIDByMcMemberCode(String mcMemberCode) {
		LOG.debug("getMemberIDByMcMemberCode({})", mcMemberCode);
		Member member = getMemberByMcMemberCode(mcMemberCode);
		if (member == null) {
			LOG.warn("getMemberIDByMcMemberCode({}) no result!", "null");
			return null;
		}
		LOG.debug("{} getMemberIDByMcMemberCode({})", member.getMemberID(),
				mcMemberCode);
		return member.getMemberID();
	}

	public Long getMemberInfoIdByMcMemberCode(String mcMemberCode) {
		LOG.debug("getMemberIDByMcMemberCode({})", mcMemberCode);
		Member member = getMemberByMcMemberCode(mcMemberCode);
		if (member == null) {
			LOG.warn("getMemberIDByMcMemberCode({}) no result!", "null");
			return null;
		}
		LOG.debug("{} getMemberIDByMcMemberCode({})", member.getMemberID(),
				mcMemberCode);
		return member.getId();
	}

	public String getMemberCodeByMcMemberCode(String mcMemberCode) {
		LOG.debug("getMemberCodeByMcMemberCode({})", mcMemberCode);
		Member member = getMemberByMcMemberCode(mcMemberCode);
		if (member == null) {
			LOG.warn("getMemberCodeByMcMemberCode({}) no result!", "null");
			return null;
		}
		LOG.debug("{} getMemberCodeByMcMemberCode({})", member.getMemberID(),
				mcMemberCode);
		return member.getMemberCode();
	}

	private Member getMemberByMemberID(String memberID) {
		LOG.debug("getMemberByMemberID({})", memberID);
		Member member = memberRepository.getMemberByMemberID(memberID);
		if (member != null) {
			LOG.debug("getMemberByMemberID({}) result Member:{}", memberID,
					member.getId());
			return member;
		}
		LOG.warn("getMemberByMemberID({}) no result!", memberID);
		return null;
	}

	public String getMcMemberCodeByMemberID(String memberID) {
		LOG.debug("getMcMemberCodeByMemberID({})", memberID);
		Member member = getMemberByMemberID(memberID);
		if (member == null) {
			LOG.warn("getMcMemberCodeByMemberID({}) no result!", "null");
			return null;
		}
		LOG.debug("{} getMemberCodeByMcMemberCode({})", member.getMemberID(),
				memberID);
		return member.getMcMemberCode();
	}

	public Member getMemberByMemberNum(String memberNum) {
		LOG.debug("getMemberByMemberNum({})", memberNum);
		Member member = memberRepository.getMemberByMemberNum(memberNum);
		if (member != null) {
			LOG.debug("getMemberByMemberNum({}) result Member:{}", memberNum,
					member.getId());
			return member;
		}
		LOG.warn("getMemberByMemberNum({}) no result!", memberNum);
		return null;
	}

	public String getMcMemberCodeByMemberNum(String memberNum) {
		LOG.debug("getMcMemberCodeByMemberNum({})", memberNum);
		String mcMemberCode = memberRepository
				.getMcMemberCodeByMemberNum(memberNum);
		if (StringUtils.isEmpty(mcMemberCode)) {
			LOG.warn("getMcMemberCodeByMemberNum({}) no result!", memberNum);
			return mcMemberCode;
		}
		LOG.debug("{} getMcMemberCodeByMemberNum({})", mcMemberCode, memberNum);
		return mcMemberCode;
	}

	public String getMcMemberCodeByPhone(String phone) {
		return memberRepository.getMcMemberCodeByPhone(phone);
	}

	public Member getMemberByCardNo(String cardNo) {
		LOG.debug("getMemberByCardNo({})", cardNo);
		Member member = memberRepository.getMemberByCardNo(cardNo);
		if (member != null) {
			LOG.debug("getMemberByCardNo({}) result Member:{}", cardNo,
					member.getId());
			return member;
		}
		WebMember webMember = webMemberRepository.getWebMemberByCardNo(cardNo);
		if (webMember != null) {
			LOG.debug("getMemberByCardNo({}) result WebMember:{}", cardNo,
					webMember.getId());
			return new Member(webMember);
		}
		LOG.warn("getMemberByCardNo({}) no result!", cardNo);
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void storeMember(Member member, MemberDto dto) throws Exception {
		WebMember webMember = getNonNormalWebMember(dto.getCellPhone(),
				dto.getEmail());
		if (webMember != null) {
			String pwd = webMember.getPwd();
			if (StringUtils.isNotBlank(pwd)) {
				dto.setPassword(pwd);
			}
			updateWebmember(dto.getMemberCode(), webMember.getId());
			member.setMcMemberCode(webMember.getMcMemberCode());
			// update webmember transfer_type is Normal
			webMemberRepository.updateTransformTypeWhenNormalUpgrate(webMember
					.getMcMemberCode());
		}
		memberRepository.storeAllMemberInfo(dto, member);
	}

	private WebMember getNonNormalWebMember(String cellPhone, String email) {
		WebMember webMember = null;
		if (StringUtils.isNotEmpty(cellPhone)) {
			webMember = webMemberRepository
					.getNonNormalWebMemberByPhoneAndEmail(cellPhone, "");
		}
		if (webMember == null && StringUtils.isNotEmpty(email)) {
			webMember = webMemberRepository
					.getNonNormalWebMemberByPhoneAndEmail("", email);
		}
		return webMember;
	}

	private void updateWebmember(String memberCode, Long webMemberID) {
		webMemberRepository.updateWebMemberInfo(memberCode, webMemberID,
				MemType.NORMAL.name());
		webMemberRepository.deleteWebMemberVerify(webMemberID);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMemberInfoAndVerifyForMove(MemberDto dto)
			throws Exception {
		Member member = new Member(dto);
		WebMember webMember = getNonNormalWebMemberByPhoneAndEmail(member);
		if (StringUtils.isEmpty(member.getMcMemberCode()))
			member.setMcMemberCode(memberRepository.generateMcMemberCode());
		memberRepository.updateMemberInfoAndStoreVerify(member, dto);

		// 更新临时会员，transfer为迁移Activation
		if (webMember != null) {
			webMemberRepository
					.updateTransformTypeWhenActivationUpgrate(webMember
							.getMcMemberCode());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMergeMember(List<MemberDto> mems) throws Exception {
		// 更新订单 被合并的帐号的订单更新成激活的帐号的订单 更新 MC
		// 请勿改动代码顺序
		List<String> mergedMC = handlerMergedMems(mems);
		String mc = handlerActiveMember(mems, mergedMC);
		mergeOrders(mc, mergedMC);
	}

	private List<String> handlerMergedMems(List<MemberDto> mems) {
		List<String> mergedMcList = new ArrayList<String>();
		for (MemberDto mem : mems) {
			Member member = new Member(mem);
			if ("Merged".equals(member.getStatus())) {
				memberRepository.deleteMemberVerifyByMemberCode(member
						.getMemberCode());
				String mc = memberRepository.getMcMemberCodeByMemberNum(mem
						.getMemberCode());
				if (StringUtils.isNotEmpty(mc)) {
					mergedMcList.add(mc);
				}
				memberRepository.updateMemberInfo(member);
			}
		}
		return mergedMcList;
	}

	private String handlerActiveMember(List<MemberDto> mems,
			List<String> mergedMcList) throws Exception {
		String mcMemberCode = "";
		for (MemberDto mem : mems) {
			Member member = new Member(mem);
			if (("Active").equals(member.getStatus())
					&& StringUtils.isNotEmpty(member.getActivateCode())
					&& !"Not Activiate".equals(member.getActivateCode())) {
				mcMemberCode = saveActivedMemberVerify(member, true);
				// 置空mcmemberCode,防止mc_member_code 冲突
				if (mergedMcList.contains(mcMemberCode))
					memberRepository.resetMcMemberCode(mcMemberCode);
				memberRepository.updateMemberInfo(member);
			}
		}
		return mcMemberCode;
	}

	private void mergeOrders(String mcMemberCode,
			List<String> mergedMcMemberCode) {
		if (StringUtils.isNotEmpty(mcMemberCode)
				&& !mergedMcMemberCode.isEmpty()) {
			AccountMergeDto accountMergeDto = new AccountMergeDto();
			accountMergeDto.setMainAccountMcMemberCode(mcMemberCode);
			accountMergeDto.setMergedMcMemberCodeList(mergedMcMemberCode);
			try {
				LOG.warn(" 用户合并 订单合并信息："
						+ JaxbUtils.convertToXmlString(accountMergeDto));
				mergeOrderSerive.mergeOrderMcMemberCode(accountMergeDto);
			} catch (Exception e) {
				LOG.error(
						" 用户合并 订单合并失败："
								+ JaxbUtils.convertToXmlString(accountMergeDto),
						e);
			}
		} else
			LOG.warn(" 用户合并 无需订单合并信息");
	}

	private String saveActivedMemberVerify(Member member, boolean isMerged)
			throws Exception {
		List<Member> existMems = memberRepository.queryByMemberID(member
				.getMemberID());
		member.setId(existMems.get(0).getId());
		member.setMcMemberCode(existMems.get(0).getMcMemberCode());
		List<MemberVerfy> verifys = memberRepository
				.getMemberVerfyByMemberCode(member.getMemberCode());
		if (verifys == null || verifys.isEmpty()) {
			if (StringUtils.isEmpty(member.getCellPhone())
					&& StringUtils.isEmpty(member.getEmail()))
				throw new Exception("用户手机号,邮箱不能都为空");
			WebMember webMember = null;
			if (!isMerged) {
				webMember = getNonNormalWebMemberByPhoneAndEmail(member);

			} else {
				webMember = getWebMember(member);
			}
			// 合并设置transform_type值为Activation
			if (webMember != null) {
				webMemberRepository
						.updateTransformTypeWhenActivationUpgrate(webMember
								.getMcMemberCode());
			}
			storeMemberVerify(member);
		} else {
			member.setPassword(verifys.get(0).getPassword());
			validateMemberVerify(member);
			memberRepository.updateVerify(member);
		}
		if (StringUtils.isEmpty(member.getMcMemberCode()))
			member.setMcMemberCode(memberRepository.generateMcMemberCode());
		return member.getMcMemberCode();
	}

	private void validateMemberVerify(Member member) throws Exception {
		String memberPhone = member.getCellPhone();
		List<MemberVerfy> memberVerifyList = new ArrayList<MemberVerfy>();

		if (StringUtils.isNotEmpty(memberPhone)) {
			List<MemberVerfy> memberPhoneVerifys = memberRepository
					.queryRegisterByCellphoneOrEmailOrCardNo(memberPhone);
			memberVerifyList.addAll(memberPhoneVerifys);
		}
		String memberEmail = member.getEmail();
		if (StringUtils.isNotEmpty(memberEmail)) {
			List<MemberVerfy> memberEmailVerifys = memberRepository
					.queryRegisterByCellphoneOrEmailOrCardNo(memberEmail);
			memberVerifyList.addAll(memberEmailVerifys);
		}
		for (MemberVerfy mv : memberVerifyList) {
			if (!member.getMemberID().equals(mv.getMemId())) {
				LOG.warn("the member[" + mv.getMenName() + "] is reduplicated");
				throw new Exception("数据已存在,无法插入");
			}
		}

	}

	private void storeMemberVerify(Member member) throws Exception {
		if (StringUtils.isNotEmpty(member.getCellPhone()))
			isMemberInfoExists(member.getCellPhone());
		if (StringUtils.isNotEmpty(member.getEmail()))
			isMemberInfoExists(member.getEmail());
		MemberDto mem = member.toDto();
		if (StringUtils.isEmpty(member.getPassword()))
			mem.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
		memberRepository.storeMemberCardAndEmailAndPhoneVerify(mem);
		memberRepository.saveJJINNCardVerify(mem);
	}

	private void isMemberInfoExists(String menName) throws Exception {
		List<MemberVerfy> verifys = memberRepository
				.queryRegisterByCellphoneOrEmailOrCardNo(menName);
		if (verifys != null && !verifys.isEmpty()) {
			LOG.warn("the member[" + menName + "] is reduplicated");
			throw new Exception("数据已存在,无法插入");
		}
	}

	private WebMember getWebMember(Member member) throws Exception {
		WebMember webMember = getWebMember(member.getCellPhone(),
				member.getEmail());
		LOG.warn("----memberCenterInterface  update  webmeber webMember != null: "
				+ (webMember != null));
		if (webMember != null) {
			getMcAndPwdFromWebMember(member, webMember);
			updateWebmember(member.getMemberCode(), webMember.getId());
			return webMember;
		}
		return null;
	}

	private WebMember getWebMember(String cellPhone, String email) {
		WebMember webMember = null;
		if (StringUtils.isNotEmpty(cellPhone)) {
			webMember = webMemberRepository.getWebMemberByPhoneAndEmail(
					cellPhone, "");
		}
		if (webMember == null && StringUtils.isNotEmpty(email)) {
			webMember = webMemberRepository.getWebMemberByPhoneAndEmail("",
					email);
		}
		return webMember;
	}

	private WebMember getNonNormalWebMemberByPhoneAndEmail(Member member)
			throws Exception {
		WebMember webMember = getNonNormalWebMember(member.getCellPhone(),
				member.getEmail());
		LOG.warn("----memberCenterInterface  update  webmeber webMember != null: "
				+ (webMember != null));
		if (webMember != null) {
			getMcAndPwdFromWebMember(member, webMember);
			updateWebmember(member.getMemberCode(), webMember.getId());
			return webMember;
		}
		WebMember existMem = getWebMember(member.getCellPhone(),
				member.getEmail());
		if (existMem != null) {
			LOG.warn("the webMember[phone:" + member.getCellPhone()
					+ " or email:" + member.getEmail() + "] is reduplicated");
			throw new NotAcceptableException("数据已存在,无法插入");
		}
		return null;

	}

	private void getMcAndPwdFromWebMember(Member member, WebMember webMember) {
		member.setMcMemberCode(webMember.getMcMemberCode());
		setWebMemberPwdAndMemberPwdIfPwdIsBlank(member, webMember);
	}

	private void setWebMemberPwdAndMemberPwdIfPwdIsBlank(Member member,
			WebMember webMember) {
		if (StringUtils.isNotBlank(member.getPassword())) {
			return;
		}
		WebMember wm = webMemberRepository.getMemberByMcMemberCode(webMember
				.getMcMemberCode());
		if (wm != null) {
			member.setPassword(wm.getPwd());
			webMember.setPwd(wm.getPwd());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void storeActiveMember(MemberDto dto) throws Exception {
		Member member = new Member(dto);
		if (dto.getCardList() != null && !dto.getCardList().isEmpty())
			member.setCardList(toCardList(dto));
		if (StringUtils.isEmpty(member.getActivateCode()))
			member.setActivateCode("Activiated");
		saveActivedMemberVerify(member, false);
		memberRepository.updateMemberInfo(member);
		// crm active
		// 统一使用事件触发
		// cbpHandler.activingIssueCoupon(RegistChannel.Store.name(),
		// member.getMcMemberCode());
		ebpHandler.sendActivateEvent(member.getMcMemberCode(),
				RegistChannel.Store);
	}

	private List<MemberMemCard> toCardList(MemberDto dto) {
		List<MemberMemCard> cards = new ArrayList<MemberMemCard>();
		for (MemberMemCardDto cardDto : dto.getCardList()) {
			cards.add(new MemberMemCard(cardDto));
		}
		return cards;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addOrTransferMemberInfo(MemberDto memberDto) throws Exception {
		if (memberDto.getLastUpd() == null) {
			memberDto.setLastUpd(new Date());
		}
		checkAddMember(memberDto);
		WebMember webMember = getNonNormalWebMemberByPhoneAndEmail(new Member(
				memberDto));
		if (webMember == null) {// 全新会员
			String randomPassword = memberRepository.getRandomPassword();
			memberDto.setPassword(MD5Utils.generatePassword(randomPassword));
			memberDto.setMcMemberCode(memberRepository.generateMcMemberCode());
			addMemberInfo(memberDto);
			memberDto.setPassword(randomPassword);
			// 调用发放优惠券
			nbpHandler.sendPasswordForStoreRegister(
					NBPHandler.TemplateType.ADD, memberDto);
		} else {// 存在临时会员
			memberDto.setPassword(webMember.getPwd());
			memberDto.setRemindQuestion(webMember.getQuestion());
			memberDto.setRemindAnswer(webMember.getAnswer());
			memberDto.setMcMemberCode(webMember.getMcMemberCode());
			addMemberInfo(memberDto);
			nbpHandler.sendPasswordForStoreRegister(
					NBPHandler.TemplateType.TRANSFER, memberDto);
			// TODO 门店注册设置transform_type值为Normal
			webMemberRepository.updateTransformTypeWhenNormalUpgrate(webMember
					.getMcMemberCode());
		}
		memberDto.setRegisterSource(RegistChannel.Store.name());
		cbpHandler.registerIssue(memberDto);
		ebpHandler.sendRegisterEvent(memberDto.getMcMemberCode(),
				memberDto.getRegisterSource(), memberDto.getCardType(), null);
	}

	public Member getMemberById(Long id) {
		Member member = memberRepository.getMemberById(id);
		List<MemberMemCard> cards = memberRepository.queryMemberCardInfos(id);
		if (member != null) {
			member.setCardList(cards);
			return member;
		}
		return null;
	}

	private void addMemberInfo(MemberDto memberDto) {
		try {
			memberRepository.storeAllMemberInfo(memberDto,
					new Member(memberDto));
		} catch (Exception e) {
			LOG.error("storeAllMemberInfo[phone:" + memberDto.getCellPhone()
					+ ",email:" + memberDto.getEmail() + "] error!", e);
			throw new RuntimeException("数据已存在,无法插入", e);
		}
	}

	private void checkAddMember(MemberDto memberDto) {
		checkMemberID(memberDto);
		checkMemberCode(memberDto);
		checkMemberCardNo(memberDto);
		checkMemberPhoneAndEmail(memberDto);
	}

	private boolean isEmpty(List memberList) {
		return memberList == null || memberList.isEmpty();
	}

	private void checkMemberCardNo(MemberDto memberDto) {
		List<MemberVerfy> memberList = memberRepository
				.queryRegisterByCellphoneOrEmailOrCardNo(memberDto.getCardNo());
		if (!isEmpty(memberList)) {
			LOG.warn("the member[cardNo:" + memberDto.getCardNo()
					+ "] is existed");
			throw new NotAcceptableException("数据已存在,无法插入");
		}
	}

	private void checkMemberID(MemberDto memberDto) {
		List<Member> memberList = memberRepository.queryByMemberID(memberDto
				.getMemberID());
		if (!isEmpty(memberList)) {
			LOG.warn("the member[memberId:" + memberDto.getMemberID()
					+ "] is existed");
			throw new NotAcceptableException("数据已存在,无法插入");
		}
	}

	private void checkMemberCode(MemberDto memberDto) {
		List<Member> memberList = memberRepository.queryByCode(memberDto
				.getMemberCode());
		if (!isEmpty(memberList)) {
			LOG.warn("the member[memberCode:" + memberDto.getMemberCode()
					+ "] is existed");
			throw new NotAcceptableException("数据已存在,无法插入");
		}
	}

	private void checkMemberPhoneAndEmail(MemberDto memberDto) {
		if (StringUtils.isNotBlank(memberDto.getCellPhone())) {
			List<MemberVerfy> pMemberList = memberRepository
					.queryRegisterByCellphoneOrEmailOrCardNo(memberDto
							.getCellPhone());
			if (!isEmpty(pMemberList)) {
				LOG.warn("the member[cellPhone:" + memberDto.getCellPhone()
						+ "] is existed");
				throw new NotAcceptableException("数据已存在,无法插入");
			}
		}
		if (StringUtils.isNotBlank(memberDto.getEmail())) {
			List<MemberVerfy> eMemberList = memberRepository
					.queryRegisterByCellphoneOrEmailOrCardNo(memberDto
							.getEmail());
			if (!isEmpty(eMemberList)) {
				LOG.warn("the member[email:" + memberDto.getEmail()
						+ "] is existed");
				throw new NotAcceptableException("数据已存在,无法插入");
			}
		}
	}

	public CouponSysIssueResult upgradeIssuedCouponForStore(MemberDto memberDto) {
		if (StringUtils.isBlank(memberDto.getMemberID())
				|| StringUtils.isBlank(memberDto.getCardType())) {
			throw new NotAcceptableException(
					"memberId or cardType is null,参数不合法,无法发放优惠券");
		}
		Member member = memberRepository.getMemberByMemberID(memberDto
				.getMemberID());
		if (null == member || StringUtils.isBlank(member.getMcMemberCode())) {
			throw new NotAcceptableException("mcMemberCode 为空,无法发放优惠券");
		}
		UpgradeIssueDto upgradeIssueDto = new UpgradeIssueDto();
		upgradeIssueDto.setUpgradeOrigin(RegistChannel.Store);
		upgradeIssueDto.setMcMemberCode(member.getMcMemberCode());
		upgradeIssueDto.setCardType(NewCardType.valueOfCode(memberDto.getCardType()));
		return cbpHandler.upgradeIssuedCoupon(upgradeIssueDto);
	}

	public boolean isExistsEmailOrPhone(String emailOrPhone) {
		if (StringUtils.isNotBlank(emailOrPhone)) {
			Member member = memberRepository
					.queryByUsernameOrCellphoneOrEmail(emailOrPhone);
			return member != null;
		}
		return false;
	}
	
	public void isValidResponse(MemberInfoUpdateResponse response) {
		if(response == null) {
			LOG.warn("MemberInfoUpdateResponse is null");
			opRecordLogRepository.insert(new OpRecordLogDomain(
					EnumOpType.MEMBER_ACTIVE, 
					"response为空，可能是服务正忙",
					JaxbUtils.convertToXmlString(response)));
			throw new IllegalArgumentException("MemberInfoUpdateResponse is null");
		}
		if(response.getBody() == null) {
			LOG.warn("MemberInfoUpdateResponse.body is null");
			opRecordLogRepository.insert(new OpRecordLogDomain(
					EnumOpType.MEMBER_ACTIVE, 
					"数据格式错误，body为空",
					JaxbUtils.convertToXmlString(response)));
			throw new IllegalArgumentException("MemberInfoUpdateResponse.body is null");
		}
		if(response.getBody().getMember() == null) {
			LOG.warn("member is null");
			opRecordLogRepository.insert(new OpRecordLogDomain(
					EnumOpType.MEMBER_ACTIVE, 
					"数据格式错误，member为空",
					JaxbUtils.convertToXmlString(response)));
			throw new IllegalArgumentException("member is null");
		}
	}

//	public String activeMember(MemberBasicInfoDto basicInfoDto) {
//		if (isExistsEmailOrPhone(basicInfoDto.getCell())
//				|| isExistsEmailOrPhone(basicInfoDto.getEmail())) {
//			return ACTIVE_FAILURE;
//		}
//		String rcode = crmMembershipRepository.activeMember(basicInfoDto);
//		activingOperation(rcode, basicInfoDto);
//		return rcode;
//	}

	// 新激活流程
	public String activeMember(MemberBasicInfoDto basicInfoDto) {
		if (isExistsEmailOrPhone(basicInfoDto.getCell())
				|| isExistsEmailOrPhone(basicInfoDto.getEmail())) {
			LOG.warn("email or phone already exist.");
			opRecordLogRepository.insert(new OpRecordLogDomain(
					EnumOpType.MEMBER_ACTIVE, 
					"邮箱或手机在 MEMBER_VERIFY 中已经存在",
					JaxbUtils.convertToXmlString(basicInfoDto)));
			return ACTIVE_FAILURE;
		}
		/*
		 * 现在外部调用时传入的memberId实际上是memberNum，crm新接口则接收memberId,特此转换
		 */
		List<Member> members = memberRepository.getMemberCardNo(basicInfoDto.getMemberId());
		if(members == null || members.size() == 0) {
			LOG.warn("member is null with the given member number");
			opRecordLogRepository.insert(new OpRecordLogDomain(
					EnumOpType.MEMBER_ACTIVE, 
					"memberNum为" + basicInfoDto.getMemberId() + "的Member不存在",
					JaxbUtils.convertToXmlString(basicInfoDto)));
			return ACTIVE_FAILURE;
		}
		basicInfoDto.setMemberId(members.get(0).getMemberID());
		MemberInfoUpdateResponse response = crmMembershipRepository.activeMemberV2(basicInfoDto);
		
		try {
			isValidResponse(response);
    		// update
			String memberId = response.getBody().getMembid();
			MemberDto memberDto = response.getBody().getMember();

			if (StringUtils.isBlank(memberId)) {
				LOG.warn("memberId is null");
				opRecordLogRepository.insert(new OpRecordLogDomain(
						EnumOpType.MEMBER_ACTIVE, 
						"MemberId为空",
						JaxbUtils.convertToXmlString(response)));
				// crmResp.setStatus("数据格式错误，MemberId不能为空");
				bamDataCollector.sendMessage(new BamMessage(
						"vbp.membercenterinterface.update",
						StatusCode.BIZ_ERROR, 
						JaxbUtils.convertToXmlString(basicInfoDto), 
						JaxbUtils.convertToXmlString(memberDto)));
			}
			memberDto.setMemberID(memberId);
			List<Member> memberList = memberRepository
					.queryByMemberID(memberId);
			// crmResp.setStatus("数据更新成功");
			if (memberList == null || memberList.size() == 0) {
				LOG.warn("the record for the memberId is not existed");
				opRecordLogRepository.insert(new OpRecordLogDomain(
						EnumOpType.MEMBER_ACTIVE, 
						"找不到id为 " + memberId + "的会员",
						memberId));
				// crmResp.setStatus("NoMember");
				bamDataCollector.sendMessage(new BamMessage(
						"vbp.membercenterinterface.update",
						StatusCode.BIZ_ERROR, 
						JaxbUtils.convertToXmlString(basicInfoDto), 
						JaxbUtils.convertToXmlString(memberDto)));
			}
			// 填充memberDto信息
			if (StringUtils.isBlank(memberDto.getMemberCode())) {
				memberDto.setMemberCode(memberList.get(0).getMemberCode());
			}
//			if (basicInfoDto.get == 0) {
				memberDto.setId(memberList.get(0).getId());
//			}
//			if (StringUtils.isBlank(memberDto.getPassword())) {
				memberDto.setPassword(basicInfoDto.getPassword());
//			}
			if (CollectionUtils.isEmpty(memberList.get(0).getMemberVerfyList())) {
				// 网站/手机完善信息 / 立即加入迁移流程 时，激活锦江之星会员
				memberDto.setActiveDate(new Date());
			    updateMemberInfoAndVerifyForMove(memberDto);
				// crmDto.setStatus("迁移成功");
				bamDataCollector.sendMessage(new BamMessage(
						"vbp.membercenterinterface.update",
						StatusCode.SUCCESS, 
						JaxbUtils.convertToXmlString(basicInfoDto),
						JaxbUtils.convertToXmlString(memberDto)));
			} else {
				memberRepository.updateMemberInfoAndVerify(new Member(memberDto));
				bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.update", 
						StatusCode.SUCCESS, 
						JaxbUtils.convertToXmlString(basicInfoDto), 
						JaxbUtils.convertToXmlString(memberDto)));
			}

		} catch (Exception ex) {
			LOG.error("update member error:{}", JaxbUtils.convertToXmlString(response), ex);
			bamDataCollector.sendMessage(new BamMessage("vbp.membercenterinterface.update", 
					StatusCode.SYS_ERROR, 
					JaxbUtils.convertToXmlString(basicInfoDto), 
					ExceptionToString.toString(ex)));
		}

		String rcode = (response != null && response.getBody() != null) ? response.getBody().getRecode() : "";
		activingOperation(rcode, basicInfoDto);
		
		return rcode;
	}
	

	private void activingOperation(String rcode, MemberBasicInfoDto basicInfoDto) {
		RegistChannel channel = basicInfoDto.getRegistChannel();
		if (ACTIVE_SUCCESS.equals(rcode) && channel != null) {
			String mc = memberRepository
					.getMcMemberCodeByMemberNum(basicInfoDto.getMemberId());
			// 统一使用事件触发
			// cbpHandler.activingIssueCoupon(channel.name(), mcMemberCode);
			ebpHandler.sendActivateEvent(mc, channel);
		}
	}
}
