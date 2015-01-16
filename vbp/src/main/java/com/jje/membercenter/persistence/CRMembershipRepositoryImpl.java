package com.jje.membercenter.persistence;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.DateUtils;
import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.MD5Utils;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.membercenter.AccountDto;
import com.jje.dto.membercenter.AddressDto;
import com.jje.dto.membercenter.CRMActivationRespDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.CardDto;
import com.jje.dto.membercenter.ContactQqAndMsnDto;
import com.jje.dto.membercenter.MemberAddressDto;
import com.jje.dto.membercenter.MemberBasicInfoDto;
import com.jje.dto.membercenter.MemberCommunicationDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberHobbyDto;
import com.jje.dto.membercenter.MemberInfoDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MemberPrivilegeDto;
import com.jje.dto.membercenter.MemberQuickRegisterDto;
import com.jje.dto.membercenter.MemberRegisterDto;
import com.jje.dto.membercenter.MemberScoreType;
import com.jje.dto.membercenter.MemberUpdateDto;
import com.jje.dto.membercenter.PrivilegeItemDto;
import com.jje.dto.membercenter.PrivilegeLogDto;
import com.jje.dto.membercenter.SSOMemberDto;
import com.jje.dto.membercenter.SSORedeemDto;
import com.jje.dto.membercenter.TravellPreferenceDto;
import com.jje.dto.vbp.sns.MemberContactsnsDto;
import com.jje.membercenter.account.xsd.AccountActivationRequest;
import com.jje.membercenter.account.xsd.AccountActivationResponse;
import com.jje.membercenter.account.xsd.AccountValidationRequest;
import com.jje.membercenter.account.xsd.AccountValidationResponse;
import com.jje.membercenter.crm.CRMConstant;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMMembershipRepository;
import com.jje.membercenter.remote.crm.support.CrmConstant;
import com.jje.membercenter.remote.crm.support.CrmTransCode;
import com.jje.membercenter.xsd.MemberAddressDeleteRequest;
import com.jje.membercenter.xsd.MemberAddressDeleteResponse;
import com.jje.membercenter.xsd.MemberAddressRequest;
import com.jje.membercenter.xsd.MemberAddressResponse;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateRequest;
import com.jje.membercenter.xsd.MemberBasicInfoUpdateResponse;
import com.jje.membercenter.xsd.MemberBindRequest;
import com.jje.membercenter.xsd.MemberBindResponse;
import com.jje.membercenter.xsd.MemberCommunicationRequest;
import com.jje.membercenter.xsd.MemberCommunicationResponse;
import com.jje.membercenter.xsd.MemberHobbyRequest;
import com.jje.membercenter.xsd.MemberHobbyResponse;
import com.jje.membercenter.xsd.MemberHobbyUpdateRequest;
import com.jje.membercenter.xsd.MemberHobbyUpdateResponse;
import com.jje.membercenter.xsd.MemberInfoUpdateResponse;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryRequest;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryResponse;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryResponse.Body.Benefit;
import com.jje.membercenter.xsd.MemberPrivilegeRequest;
import com.jje.membercenter.xsd.MemberPrivilegeResponse;
import com.jje.membercenter.xsd.MemberPrivilegeResponse.Body.Record;
import com.jje.membercenter.xsd.MemberRegisterRequest;
import com.jje.membercenter.xsd.MemberRegisterRequest.Body.Record.Listofcontact;
import com.jje.membercenter.xsd.MemberRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress;
import com.jje.membercenter.xsd.MemberRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress.Personaladdress;
import com.jje.membercenter.xsd.MemberRegisterResponse;
import com.jje.membercenter.xsd.MemberRequest;
import com.jje.membercenter.xsd.MemberResponse;
import com.jje.membercenter.xsd.MemberSnsBindRequest;
import com.jje.membercenter.xsd.MemberSnsBindResponse;
import com.jje.membercenter.xsd.ModifyPassWordRequest;
import com.jje.membercenter.xsd.ModifyPassWordResponse;
import com.jje.membercenter.xsd.QueryTravellPreferenceRequest;
import com.jje.membercenter.xsd.QueryTravellPreferenceResponse;
import com.jje.membercenter.xsd.QuickRegisterRequest;
import com.jje.membercenter.xsd.QuickRegisterResponse;
import com.jje.membercenter.xsd.RedeemRequest;
import com.jje.membercenter.xsd.RedeemResponse;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;
import com.jje.membercenter.xsd.ThirdPartyLoginRequest;
import com.jje.membercenter.xsd.ThirdPartyLoginResponse;
import com.jje.membercenter.xsd.UpdateContactRequest;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactemail;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactemail.Contactemail;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactfax;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactfax.Contactfax;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactim;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactim.Contactim;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactphone;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactphone.Contactphone;
import com.jje.membercenter.xsd.UpdateContactResponse;
import com.jje.membercenter.xsd.UpdateMemberAddressRequest;
import com.jje.membercenter.xsd.UpdateMemberAddressResponse;
import com.jje.membercenter.xsd.UpdateTravellPreferenceRequest;
import com.jje.membercenter.xsd.UpdateTravellPreferenceRequest.Body.Listofprefer;
import com.jje.membercenter.xsd.UpdateTravellPreferenceRequest.Body.Listofprefer.Preference;
import com.jje.membercenter.xsd.UpdateTravellPreferenceResponse;
import com.jje.vbp.sns.domain.MemberContactsns;
import com.jje.vbp.sns.repository.MemberContactsnsRepository;

@Component
public class CRMembershipRepositoryImpl implements CRMMembershipRepository {

	@Autowired
	CRMMembershipProxy crmMembershipProxy;
	
	@Autowired
	MemberContactsnsRepository memberContactsnsRepository;

	public static String CRM_RESPONSE_SUCCESS_CODE = "00001";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public CRMResponseDto insertContactInfo(ContactQqAndMsnDto qqMsnDto) {
		UpdateContactRequest request = new UpdateContactRequest();
		UpdateContactResponse response = new UpdateContactResponse();
		UpdateContactRequest.Head head = new UpdateContactRequest.Head();
		UpdateContactRequest.Body body = new UpdateContactRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("30014");
		body.setMembid(qqMsnDto.getMemberId());
		UpdateContactRequest.Body.Listofcontact listofcontact = new UpdateContactRequest.Body.Listofcontact();
		Contact contact = new Contact();
		Listofcontactim tim = new Listofcontactim();
		Contactim qq = new Contactim();
		qq.setIm(qqMsnDto.getQqNo());
		qq.setUsetype("QQ");
		if (qqMsnDto.getQqNo() != null && !"".equals(qqMsnDto.getQqNo())) {
			tim.getContactim().add(qq);
		}
		Contactim msn = new Contactim();
		msn.setIm(qqMsnDto.getMsnNo());
		msn.setUsetype("MSN");
		if (qqMsnDto.getMsnNo() != null && !"".equals(qqMsnDto.getMsnNo())) {
			tim.getContactim().add(msn);
		}
		// Contactim other = new Contactim();
		// other.setIm(qqMsnDto.getMsnNo());
		// other.setUsetype("MSN");
		// tim.getContactim().add(other);

		contact.setListofcontactim(tim);
		listofcontact.setContact(contact);
		body.setListofcontact(listofcontact);
		request.setHead(head);
		request.setBody(body);
		try {
			response = crmMembershipProxy.updateContact(request);
		} catch (Exception e) {
			logger.error("updateContact({}) error!", JaxbUtils.convertToXmlString(request), e);
		}
		return new CRMResponseDto(response.getBody().getMembid(), response.getBody().getRecode(), response.getBody().getRemsg());
		// return null;
	}

	public MemberPrivilegeDto getPrivCardInfo(QueryMemberDto<String> queryDto) throws Exception {
		MemberPrivilegeDto dto = new MemberPrivilegeDto();
		MemberPrivilegeRequest request = new MemberPrivilegeRequest();
		MemberPrivilegeResponse response = new MemberPrivilegeResponse();
		MemberPrivilegeRequest.Head head = new MemberPrivilegeRequest.Head();
		MemberPrivilegeRequest.Body body = new MemberPrivilegeRequest.Body();
		MemberPrivilegeHistoryRequest req = new MemberPrivilegeHistoryRequest();
		MemberPrivilegeHistoryResponse res = new MemberPrivilegeHistoryResponse();
		MemberPrivilegeHistoryRequest.Head head2 = new MemberPrivilegeHistoryRequest.Head();
		MemberPrivilegeHistoryRequest.Body body2 = new MemberPrivilegeHistoryRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("20009");
		body.setMembid(queryDto.getCondition());
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.getPrivCardInfo(request);
		head2.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head2.setTranscode("20015");
		body2.setMembid(queryDto.getCondition());
		req.setHead(head2);
		req.setBody(body2);
		res = crmMembershipProxy.getPrivCardHistory(req);
		List<Record> listRecords = response.getBody().getRecord();
		List<Benefit> list = res.getBody().getBenefit();
		List<PrivilegeItemDto> listItem = new ArrayList<PrivilegeItemDto>();
		List<PrivilegeLogDto> listLog = new ArrayList<PrivilegeLogDto>();
		for (int i = 0; i < listRecords.size(); i++) {
			PrivilegeItemDto p = new PrivilegeItemDto();
			p.setPrivTypeName(listRecords.get(i).getViptype());
			if (listRecords.get(i).getViptimes() != null) {
				p.setRemainQuantity(Integer.parseInt(listRecords.get(i).getViptimes()));
			}
			if (listRecords.get(i).getExpiredate() != null) {
				p.setExpiredate(new SimpleDateFormat("MM/dd/yyyy").parse(listRecords.get(i).getExpiredate()));
			}
			listItem.add(p);
		}
		for (int i = 0; i < list.size(); i++) {
			PrivilegeLogDto logDto = new PrivilegeLogDto();
			logDto.setPrivTypeId(i);
			if (list.get(i).getBenefitnote().equals("") || list.get(i).getBenefitnote() == null) {
				logDto.setOrderNo(null);
				logDto.setPrivTypeName(null);
			} else {
				logDto.setOrderNo(list.get(i).getBenefitnote().split(";")[0]);
				logDto.setPrivTypeName(list.get(i).getBenefitnote().split(";")[1]);
			}
			logDto.setComsumeDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(list.get(i).getOperationtime()));
			if (list.get(i).getBenefittime() != null && !list.get(i).getBenefittime().equals("")) {
				logDto.setDeductQuantity(Integer.parseInt(list.get(i).getBenefittime()));
			}
			if (list.get(i).getOperationtype().equals("MINUS")) {
				listLog.add(logDto);
			}
		}
		dto.setPrivilegeList(listItem);
		dto.setPrivLogList(listLog);
		return dto;
	}

	public CRMResponseDto updateTravellPreference(TravellPreferenceDto travellPreferenceDto) throws Exception {
		UpdateTravellPreferenceRequest request = new UpdateTravellPreferenceRequest();
		UpdateTravellPreferenceResponse response = new UpdateTravellPreferenceResponse();
		UpdateTravellPreferenceRequest.Head head = new UpdateTravellPreferenceRequest.Head();
		UpdateTravellPreferenceRequest.Body body = new UpdateTravellPreferenceRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("30005");
		head.setSystype("JJ000");
		body.setMembid(travellPreferenceDto.getMemberId());
		Listofprefer listofprefer = new Listofprefer();
		Preference p1 = new Preference();
		p1.setPrefertype("BedType");
		p1.setHobby(travellPreferenceDto.getBedType());
		listofprefer.getPreference().add(p1);
		Preference p2 = new Preference();
		p2.setPrefertype("Rooms");
		p2.setHobby(travellPreferenceDto.getRoom());
		listofprefer.getPreference().add(p2);
		Preference p3 = new Preference();
		p3.setPrefertype("RoomTier");
		p3.setHobby(travellPreferenceDto.getFloor());
		listofprefer.getPreference().add(p3);
		Preference p4 = new Preference();
		p4.setPrefertype("Environment");
		p4.setHobby(travellPreferenceDto.getEnvironment());
		listofprefer.getPreference().add(p4);
		Preference p5 = new Preference();
		p5.setPrefertype("FirstLanguage");
		p5.setHobby(travellPreferenceDto.getDefaultLanguage());
		listofprefer.getPreference().add(p5);
		Preference p6 = new Preference();
		p6.setPrefertype("DreamSpace");
		StringBuilder str = new StringBuilder();
		if (travellPreferenceDto.getDreamDestination() != null) {
			for (int i = 0; i < travellPreferenceDto.getDreamDestination().size(); i++) {
				str.append(travellPreferenceDto.getDreamDestination().get(i)).append(";");
			}
		}
		p6.setHobby(str.toString());
		if (!str.toString().equals("")) {
			listofprefer.getPreference().add(p6);
		}

		Preference p7 = new Preference();
		p7.setPrefertype("FavoriteCar");
		StringBuilder string = new StringBuilder();
		if (travellPreferenceDto.getFavouriteCar() != null) {
			for (int i = 0; i < travellPreferenceDto.getFavouriteCar().size(); i++) {
				string.append(travellPreferenceDto.getFavouriteCar().get(i)).append(";");
			}
		}
		if (!string.toString().equals("")) {
			p7.setHobby(string.toString());
			listofprefer.getPreference().add(p7);
		}
		body.setListofprefer(listofprefer);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.updateTravellPreference(request);
		return new CRMResponseDto(response.getBody().getMembid(), response.getBody().getRecode(), response.getBody().getRemsg());
	}

	public TravellPreferenceDto getTravellPreference(String memberId) throws Exception {
		QueryTravellPreferenceRequest request = new QueryTravellPreferenceRequest();
		QueryTravellPreferenceResponse response = new QueryTravellPreferenceResponse();
		QueryTravellPreferenceRequest.Head head = new QueryTravellPreferenceRequest.Head();
		QueryTravellPreferenceRequest.Body body = new QueryTravellPreferenceRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("20005");
		head.setSystype("JJ000");
		body.setMembid(memberId);
		request.setBody(body);
		request.setHead(head);
		response = crmMembershipProxy.getTravellPreference(request);
		TravellPreferenceDto dto = new TravellPreferenceDto();
		if (response.getBody().getListofprefer() != null) {
			if (response.getBody().getListofprefer().getPreference().size() >= 0) {
				for (QueryTravellPreferenceResponse.Body.Listofprefer.Preference p : response.getBody().getListofprefer().getPreference()) {
					if (p.getPrefertype().equals("BedType")) {
						dto.setBedType(p.getHobby());
					}
					if (p.getPrefertype().equals("Rooms")) {
						dto.setRoom(p.getHobby());
					}
					if (p.getPrefertype().equals("RoomTier")) {
						dto.setFloor(p.getHobby());
					}
					if (p.getPrefertype().equals("Environment")) {
						dto.setEnvironment(p.getHobby());
					}
					if (p.getPrefertype().equals("FirstLanguage")) {
						dto.setDefaultLanguage(p.getHobby());
					}
					if (p.getPrefertype().equals("DreamSpace")) {
						List<String> list = new ArrayList<String>();
						for (int i = 0; i < p.getHobby().split(";").length; i++) {
							list.add(p.getHobby().split(";")[i]);
						}
						dto.setDreamDestination(list);
					}
					if (p.getPrefertype().equals("FavoriteCar")) {
						List<String> list = new ArrayList<String>();
						for (int i = 0; i < p.getHobby().split(";").length; i++) {
							list.add(p.getHobby().split(";")[i]);
						}
						dto.setFavouriteCar(list);
					}
				}
			}
		}
		return dto;
	}

	public CardDto queryCardStauts(String memberId) throws Exception {
		MemberRequest request = new MemberRequest();
		MemberResponse response = new MemberResponse();
		MemberRequest.Head head = new MemberRequest.Head();
		MemberRequest.Body body = new MemberRequest.Body();
		head.setResptime(BigInteger.valueOf(Long.parseLong((new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())))));
		head.setTranscode(BigInteger.valueOf(Long.parseLong("20004")));
		body.setMembid(memberId);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.queryCardStauts(request);
		CardDto dto = new CardDto();
		dto.setStatus(response.getBody().getCardstat());
		dto.setCardNo(response.getBody().getMembid());
		return dto;
	}

	public SSOMemberDto getMember(String memberId) throws Exception {
		MemberRequest request = new MemberRequest();
		MemberResponse response = new MemberResponse();
		MemberRequest.Head head = new MemberRequest.Head();
		MemberRequest.Body body = new MemberRequest.Body();
		head.setResptime(BigInteger.valueOf(Long.parseLong((new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())))));
		head.setTranscode(BigInteger.valueOf(Long.parseLong("20004")));
		head.setSystype("JJ000");
		body.setMembid(memberId);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.getMember(request);
		SSOMemberDto dto = new SSOMemberDto();
		if (response.getHead().getRetcode().equals("00001")) {
			dto.setMemberId(response.getBody().getMembid());
			dto.setName(response.getBody().getListofcontact().getContact().getLastname());
			// dto.setName_en(response.getBody().getListofcontact().getContact().getTitle());
			dto.setScore(response.getBody().getLoypoint().toString());
			if (response.getBody().getListofcontact().getContact() != null) {
				dto.setSex(response.getBody().getListofcontact().getContact().getSex());
				dto.setCertificateType(response.getBody().getListofcontact().getContact().getCdtype());
				dto.setCertificatenumber(StringUtils.defaultIfEmpty(response.getBody().getListofcontact().getContact().getCdno(), ""));
			}
			String str = dto.getMemberId() + dto.getName() + dto.getScore() + dto.getSex() + dto.getCertificateType() + dto.getCertificatenumber()
					+ "iskfdklaladfklladsnewrfi349sahjo";
			dto.setMd5(MD5Utils.generatePassword(str));
		} else {
			dto = null;
		}
		return dto;
	}

	public CRMResponseDto addVIPMembership(MemberRegisterDto dto) throws Exception {
		MemberRegisterRequest request = new MemberRegisterRequest();
		MemberRegisterResponse response = new MemberRegisterResponse();
		MemberRegisterRequest.Head head = getRegisterRequestHead();
		MemberRegisterRequest.Body body = new MemberRegisterRequest.Body();

		MemberRegisterRequest.Body.Record record = getMemberRegisterRequestHead(dto);
		Listofcontact listofcontact = new Listofcontact();
		MemberRegisterRequest.Body.Record.Listofcontact.Contact contact = getMemberRegisterRequestContact(dto);
		Listofpersonaladdress listofpersonaladdress = getPersonAdds(dto);

		logger.debug("***** 设置完毕  ****");
		// 如果没有子项就不传
		if (!listofpersonaladdress.getPersonaladdress().isEmpty()) {
			contact.setListofpersonaladdress(listofpersonaladdress);
		}
		listofcontact.setContact(contact);
		record.setListofcontact(listofcontact);
		body.setRecord(record);
		request.setHead(head);
		request.setBody(body);
		logger.warn("addVIPMembership  send crm  Request---> requestInfo =" + JaxbUtils.convertToXmlString(request));
		response = crmMembershipProxy.addVIPMembership(request);
		logger.warn("addVIPMembership  send crm  response---> responseInfo =" + JaxbUtils.convertToXmlString(response));
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setMembid(response.getBody().getMembid());
		crmResponseDto.setRetcode(response.getBody().getRecode().toString());
		crmResponseDto.setRetmsg(response.getBody().getRemsg());
		crmResponseDto.setMember(getMemberDtoByBody(response.getBody()));
		System.out.println(CRM_RESPONSE_SUCCESS_CODE.equals(response.getBody().getRecode().toString()));
		crmResponseDto.setExecSuccess(CRM_RESPONSE_SUCCESS_CODE.equals(response.getBody().getRecode().toString()));
		return crmResponseDto;

	}

	private Listofpersonaladdress getPersonAdds(MemberRegisterDto dto) {
		Listofpersonaladdress listofpersonaladdress = new Listofpersonaladdress();

		logger.debug("***** 准备 设置首要地址 ****");
		// 首要地址是否有填写(省,市,详细地址都有写的情况下，说明有填写)
		AddressDto primaryAddress = dto.getMemberInfoDto().getPrimaryAddress();
		if (primaryAddress != null && StringUtils.isNotEmpty(primaryAddress.getCityId()) && StringUtils.isNotEmpty(primaryAddress.getProvinceId())
				&& StringUtils.isNotEmpty(primaryAddress.getAddress())) {
			listofpersonaladdress.getPersonaladdress().add(wrapPersonAddress(primaryAddress));
		}
		AddressDto invoiceAddr = dto.getInvoiceMailAddress();
		// 是否邮寄发票
		if (dto.isNeedMailInvoice() && invoiceAddr != null) {
			addPersonalAddFromInvoiceAdd(listofpersonaladdress, invoiceAddr);

		}
		return listofpersonaladdress;
	}

	private MemberRegisterRequest.Body.Record.Listofcontact.Contact getMemberRegisterRequestContact(MemberRegisterDto dto) {
		MemberInfoDto info = dto.getMemberInfoDto();
		MemberRegisterRequest.Body.Record.Listofcontact.Contact contact = new MemberRegisterRequest.Body.Record.Listofcontact.Contact();
		contact.setTitle(info.getTitle());
		contact.setConpriflag("Y");
		contact.setCardtype(info.getCertificateType());
		contact.setCardid(info.getCertificateNo());
		contact.setEmail(info.getEmail());
		if (dto.isNeedMailCard() == true) {
			contact.setPostflg("Y");
		} else {
			if ("JJ Card".equals(info.getMemberType())) {
				contact.setPostflg("N");
			} else {
				contact.setPostflg("Y");
			}

		}
		if (dto.isNeedMailInvoice() == true) {
			contact.setInvflg("Y");
		} else {
			contact.setInvflg("N");
		}
		contact.setEmailbill("N");
		contact.setEmailepro("N");
		contact.setEmailinvestigate("N");
		contact.setEmailpartner("N");
		contact.setEmailpro("N");

		logger.debug("***** 准备 设置订阅信息 ****");
		if (dto.getMemberSubscribe() != null && dto.getMemberSubscribe().getSubscribeList() != null) {
			setSubscribeInfo(dto, contact);
		}
		contact.setMobile(info.getMobile());
		return contact;
	}

	private MemberRegisterRequest.Body.Record getMemberRegisterRequestHead(MemberRegisterDto dto) {
		MemberInfoDto info = dto.getMemberInfoDto();
		MemberRegisterRequest.Body.Record record = new MemberRegisterRequest.Body.Record();
		record.setName(info.getSurname());
		record.setPasswd(info.getPasssword());
		record.setPwdanswer(info.getRemindAnswer());
		record.setPwdquestion(info.getRemindQuestion());
		if (null != info.getRegisterSource())
			record.setRegichnl(info.getRegisterSource().name());
		String jjcard = info.getMemberType();
		record.setJjcardtype(jjcard);
		record.setSalesPerson(dto.getSalesInfo().getSalesPerson());
		record.setSalesChannel(dto.getSalesInfo().getSalesChannel());
		record.setSalesAmount(dto.getSalesInfo().getSalesAmount());
		record.setRegichnltag(dto.getRegisterTag());

		record.setTier(info.getTier());
		if (MemberScoreType.MILEAGE.equals(info.getMemberScoreType())) {
			record.setPartnerflag(CRMConstant.FLAG_TRUE);
		} else {
			record.setPartnerflag(CRMConstant.FLAG_FALSE);
		}
		if (StringUtils.isNotBlank(info.getAirLineCardNo()) && info.getAirLineCompany() != null) {
			record.setPartnercard(info.getAirLineCardNo());
			record.setPartnername(info.getAirLineCompany().name());
		}
		if (StringUtils.isNotBlank(info.getPartnerName()) && info.getPartnerName() != null) {
			record.setPartnercard(info.getPartnercardNo());
			record.setPartnername(info.getPartnerName());
		}
		if (dto.getInvoice() != null && StringUtils.isEmpty(dto.getCouponCode())) {
			String invoiceStr = (dto.getInvoice().getTitle() == null) ? "" : dto.getInvoice().getTitle();
			invoiceStr += dto.getInvoice().getItem();
			record.setInvc(invoiceStr + "|" + dto.getInvoice().getItem() + "|" + dto.getInvoice().getAmount());
		} else if (StringUtils.isNotEmpty(dto.getCouponCode())) {
			record.setInvc("" + dto.getCouponRuleID());
			record.setActivityType(dto.getCouponCode());
		}
		// 新增合作类型
		record.setThirdpartyType(info.getThirdpartyType());
		return record;
	}

	private MemberRegisterRequest.Head getRegisterRequestHead() {
		MemberRegisterRequest.Head head = new MemberRegisterRequest.Head();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode(BigInteger.valueOf(Long.parseLong("10009")));
		head.setSystype("JJ000");
		return head;
	}

	private void setSubscribeInfo(MemberRegisterDto dto, MemberRegisterRequest.Body.Record.Listofcontact.Contact contact) {
		List subscribeList = dto.getMemberSubscribe().getSubscribeList();
		for (int i = 0; i < subscribeList.size(); i++) {
			Object a = subscribeList.get(i);
			if (a != null) {
				if ("0".equals(a.toString())) {
					contact.setEmailbill("Y");
				} else if ("1".equals(a.toString())) {
					contact.setEmailepro("Y");
				} else if ("2".equals(a.toString())) {
					contact.setEmailinvestigate("Y");
				} else if ("3".equals(a.toString())) {
					contact.setEmailpartner("Y");
				} else if ("4".equals(a.toString())) {
					contact.setEmailpro("Y");
				}
			}

		}
	}

	private void addPersonalAddFromInvoiceAdd(Listofpersonaladdress listofpersonaladdress, AddressDto invoiceAddr) {
		logger.debug("***** 准备 设置发票地址 ****");
		Personaladdress invoiceAddress = new Personaladdress();
		// 是否首要地址(跟CRM协商,只能有首要地址为Y)
		invoiceAddress.setAddpriflag("N");
		invoiceAddress.setAddress(invoiceAddr.getAddress());
		invoiceAddress.setAddrtype("Receipt Address");
		invoiceAddress.setArea(invoiceAddr.getDistrictId());
		invoiceAddress.setCity(invoiceAddr.getCityId());
		if (StringUtils.isNotEmpty(invoiceAddr.getPostcode()))
			invoiceAddress.setPostcode(BigInteger.valueOf(Long.parseLong(invoiceAddr.getPostcode())));
		// invoiceAddress.setNation("Chinese");
		invoiceAddress.setProvince(invoiceAddr.getProvinceId());
		invoiceAddress.setStreetaddr(invoiceAddr.getAddress());
		listofpersonaladdress.getPersonaladdress().add(invoiceAddress);

		if (invoiceAddr.getPriFlag().equals("N")) {
			// 发票 地址另存
			Personaladdress invoiceAddress2 = new Personaladdress();
			// 是否首要地址(跟CRM协商,只能有首要地址为Y)
			invoiceAddress2.setAddpriflag("N");
			invoiceAddress2.setAddress(invoiceAddr.getAddress());
			String invoiceAddType = invoiceAddr.getType();
			if ("1".equals(invoiceAddType)) {
				invoiceAddress2.setAddrtype("Home Address");
			} else if ("0".equals(invoiceAddType)) {
				invoiceAddress2.setAddrtype("Office Address");
			} else if ("2".equals(invoiceAddType)) {
				invoiceAddress2.setAddrtype("Other Address");
			}
			invoiceAddress2.setArea(invoiceAddr.getDistrictId());
			invoiceAddress2.setCity(invoiceAddr.getCityId());
			if (StringUtils.isNotEmpty(invoiceAddr.getPostcode()))
				invoiceAddress2.setPostcode(BigInteger.valueOf(Long.parseLong(invoiceAddr.getPostcode())));
			// invoiceAddress.setNation("Chinese");
			invoiceAddress2.setProvince(invoiceAddr.getProvinceId());
			invoiceAddress2.setStreetaddr(invoiceAddr.getAddress());
			listofpersonaladdress.getPersonaladdress().add(invoiceAddress2);
		}
	}

	private Personaladdress wrapPersonAddress(AddressDto primaryAddress) {
		Personaladdress address = new Personaladdress();
		// 是否首要地址
		address.setAddpriflag("Y");
		address.setAddress(primaryAddress.getAddress());
		String addType = primaryAddress.getType();
		if ("1".equals(addType)) {
			address.setAddrtype("Home Address");
		} else if ("0".equals(addType)) {
			address.setAddrtype("Office Address");
		} else if ("2".equals(addType)) {
			address.setAddrtype("Other Address");
		}
		address.setArea(primaryAddress.getDistrictId());
		address.setCity(primaryAddress.getCityId());
		if (StringUtils.isNotEmpty(primaryAddress.getPostcode()))
			address.setPostcode(BigInteger.valueOf(Long.parseLong(primaryAddress.getPostcode())));
		// address.setNation("Chinese");
		address.setProvince(primaryAddress.getProvinceId());
		address.setStreetaddr(primaryAddress.getAddress());
		return address;
	}

	// 升级
	public CRMResponseDto updateVIPCardInfo(MemberUpdateDto memberUpdateDto) throws Exception {
		SynRightCardRequest request = new SynRightCardRequest();
		SynRightCardResponse response = new SynRightCardResponse();
		SynRightCardRequest.Head head = new SynRightCardRequest.Head();
		SynRightCardRequest.Body body = new SynRightCardRequest.Body();
		head.setReqtime(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		head.setTranscode("30010");
		head.setSystype("JJ000");
		body.setMembid(memberUpdateDto.getMembid());
		body.setMembcdno(memberUpdateDto.getMembcdno());
		body.setOptype(memberUpdateDto.getOptype());
		// body.setOptype("J升级J2");/TODO Need To Check ...
		if (memberUpdateDto.getBuydate() != null) {
			Date dateTemp = DateUtils.parseDate(memberUpdateDto.getBuydate(), "yyyyMMdd-HHmmss");
			String buyDate = DateUtils.formatDate(dateTemp, "MM/dd/yyyy HH:mm:ss");
			body.setBuydate(buyDate);
		} else {
			body.setBuydate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		}
		body.setChannel(memberUpdateDto.getChannel());
		body.setVipstore(memberUpdateDto.getVipstore());
		body.setSalesperson(memberUpdateDto.getSalesperson());
		body.setSalesmount(memberUpdateDto.getSalesmount());
		if (StringUtils.isNotBlank(memberUpdateDto.getCouponCode())) {
			body.setActivityType(memberUpdateDto.getCouponCode());
			body.setDesc("" + memberUpdateDto.getCouponRuleID());
		} else {
			body.setDesc(memberUpdateDto.getDesc());
		}

		body.setPostflg(memberUpdateDto.getPostflg());
		body.setInvflg(memberUpdateDto.getInvflg());
		request.setHead(head);
		request.setBody(body);
		logger.warn("***** updateVIPCardInfo  request *******" + JaxbUtils.convertToXmlString(request));
		try {
			response = crmMembershipProxy.updateVIPCardInfo(request);
		} catch (Exception ex) {
			logger.warn("***** updateVIPCardInfo  response *******" + JaxbUtils.convertToXmlString(response), ex);
		}
		logger.warn("***** updateVIPCardInfo  response *******" + JaxbUtils.convertToXmlString(response));
		CRMResponseDto crmResponseDto = new CRMResponseDto(response.getHead().getTranscode(), response.getHead().getRetcode(), response.getHead().getRetmsg());
		crmResponseDto.setMember(fullMemberDto(response));
		crmResponseDto.setExecSuccess("00001".equals(response.getHead().getRetcode()));
		return crmResponseDto;
	}

	@SuppressWarnings("deprecation")
	private MemberDto fullMemberDto(SynRightCardResponse response) {
		MemberDto member = new MemberDto();
		SynRightCardResponse.Body.Member respMem = response.getBody().getMember();
		member.setMemberID(response.getBody().getMembid());
		member.setMemberCode(respMem.getMemberCode());
		member.setNewMemberHierarchy(respMem.getMemberHierarchy());
		member.setCardLevel(respMem.getMemberHierarchy());

		List<SynRightCardResponse.Body.Member.listofcard.Card> respCardList = response.getBody().getMember().getListofcard().getCardlist();
		respCardList = (respCardList == null) ? new ArrayList<SynRightCardResponse.Body.Member.listofcard.Card>() : respCardList;
		List<MemberMemCardDto> cardList = new ArrayList<MemberMemCardDto>();

		for (SynRightCardResponse.Body.Member.listofcard.Card card : respCardList) {
			MemberMemCardDto cardDto = new MemberMemCardDto();
			cardDto.setCardTypeCd(card.getTierName());
			cardDto.setxCardNum(card.getCardNumber());
			cardDto.setStatus(card.getCardStatus());
			Date validDate = DateUtils.parseDate(card.getCardStartDate(), "MM/dd/yyyy");
			Date dueDate = DateUtils.parseDate(card.getCardExpireDate(), "MM/dd/yyyy");
			cardDto.setValidDate(validDate);
			cardDto.setDueDate(dueDate);
			cardDto.setMemId(response.getBody().getMembid());
			cardList.add(cardDto);

			member.setCardNo(card.getCardNumber());
			member.setCardStatus(card.getCardStatus());
			member.setCardType(card.getTierName());
		}

		member.setCardList(cardList);
		logger.warn("***** fullMemberDto *******" + JaxbUtils.convertToXmlString(member));
		return member;
	}

	public CRMResponseDto quickRegister(MemberQuickRegisterDto memberQuickRegisterDto) throws Exception {
		QuickRegisterRequest request = new QuickRegisterRequest();
		QuickRegisterResponse response = new QuickRegisterResponse();
		QuickRegisterRequest.Head head = new QuickRegisterRequest.Head();
		QuickRegisterRequest.Body body = new QuickRegisterRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode(BigInteger.valueOf(Long.parseLong("10004")));
		head.setSystype("JJ000");
		QuickRegisterRequest.Body.Record record = new QuickRegisterRequest.Body.Record();
		record.setName(memberQuickRegisterDto.getName());
		record.setPasswd(memberQuickRegisterDto.getPassword());
		QuickRegisterRequest.Body.Record.Listofcontact listofcontact = new QuickRegisterRequest.Body.Record.Listofcontact();
		QuickRegisterRequest.Body.Record.Listofcontact.Contact contact = new QuickRegisterRequest.Body.Record.Listofcontact.Contact();
		contact.setConpriflag("");
		contact.setTitle(memberQuickRegisterDto.getTitle());
		// contact.setTitle("Mr.");
		contact.setEmail(memberQuickRegisterDto.getEmail());
		if (memberQuickRegisterDto.getMobile() != null) {
			contact.setMobile(memberQuickRegisterDto.getMobile());
		}
		if (memberQuickRegisterDto.getCertificateNo() != null) {
			contact.setCardid(memberQuickRegisterDto.getCertificateNo());
		}
		contact.setCardtype(memberQuickRegisterDto.getCertificateType());
		// contact.setCardtype("ID");
		if (memberQuickRegisterDto.getPostflg()) {
			contact.setPostflg("Y");
		} else {
			contact.setPostflg("N");
		}

		// QuickRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress
		// listofpersonaladdress = new
		// QuickRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress();
		// com.jje.membercenter.xsd.QuickRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress.Personaladdress
		// adr = new
		// QuickRegisterRequest.Body.Record.Listofcontact.Contact.Listofpersonaladdress.Personaladdress();
		/*
		 * adr.setAddpriflag("Y"); adr.setAddrtype("家庭地址");// TODO Need To Check
		 * . adr.setAddress("");
		 * adr.setProvince(memberQuickRegisterDto.getProvince());
		 * adr.setCity(memberQuickRegisterDto.getCity());
		 * adr.setArea(memberQuickRegisterDto.getArea());
		 * adr.setStreetaddr(memberQuickRegisterDto.getStreetaddr());
		 * if(!StringUtils.isEmpty(memberQuickRegisterDto.getPostcode()))
		 * adr.setPostcode
		 * (BigInteger.valueOf(Long.valueOf(memberQuickRegisterDto
		 * .getPostcode())));
		 * listofpersonaladdress.getPersonaladdress().add(adr);
		 * contact.setListofpersonaladdress(listofpersonaladdress);
		 */
		listofcontact.setContact(contact);
		record.setListofcontact(listofcontact);
		body.setRecord(record);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.quickRegister(request);
		CRMResponseDto dto = new CRMResponseDto();
		dto.setMembid(response.getBody().getMembid());
		dto.setRetcode(response.getBody().getRecode().toString());
		dto.setRetmsg(response.getBody().getRemsg());
		return dto;
	}

	public String reduceScore(SSORedeemDto ssoRedeemDto) throws Exception {
		RedeemRequest request = new RedeemRequest();
		RedeemResponse response = new RedeemResponse();
		RedeemRequest.Head head = new RedeemRequest.Head();
		RedeemRequest.Body body = new RedeemRequest.Body();
		head.setReqtime(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		head.setTranscode("30011");
		head.setSystype("JJ000");
		body.setMembid(ssoRedeemDto.getMemid());
		if (ssoRedeemDto.getTime() != null && !"".equals(ssoRedeemDto.getTime())) {
			body.setTransdate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyyMMddHHmmss").parse(ssoRedeemDto.getTime())));
		}
		body.setTranstype("REDEMPTION");
		body.setOrdernumber(ssoRedeemDto.getOrderid());
		body.setProductname("");
		body.setPoints(ssoRedeemDto.getScore());
		body.setRedeemproduct(ssoRedeemDto.getPdcode());
		request.setHead(head);
		request.setBody(body);
		logger.warn("***** reduceScore  request *******" + JaxbUtils.convertToXmlString(request));
		response = crmMembershipProxy.reduceScore(request);
		logger.warn("***** reduceScore  response *******" + JaxbUtils.convertToXmlString(response));
		if (response.getHead().getRetcode().equals("00002")) {
			if (response.getHead().getRetmsg().contains("SBL-EXL-00151")) {
				return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Response><Code>204</Code><Desc>User does not exist.</Desc></Response>";
			} else {
				return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Response><Code>205</Code><Desc>Error.</Desc></Response>";
			}
		} else {
			return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Response><Code>201</Code><Desc>Success.</Desc></Response>";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @续会
	 */
	public CRMResponseDto updateCardInfo(MemberUpdateDto memberUpdateDto) throws Exception {
		SynRightCardRequest request = new SynRightCardRequest();
		SynRightCardResponse response = new SynRightCardResponse();
		SynRightCardRequest.Head head = new SynRightCardRequest.Head();
		SynRightCardRequest.Body body = new SynRightCardRequest.Body();
		head.setReqtime(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		head.setTranscode("30010");
		head.setSystype("JJ000");
		body.setMembid(memberUpdateDto.getMembid());
		if (memberUpdateDto.getBuydate() != null)
			body.setBuydate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(memberUpdateDto.getBuydate()));
		else
			body.setBuydate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		request.setHead(head);
		request.setBody(body);
		logger.warn("***** resume  request *******" + JaxbUtils.convertToXmlString(request));
		response = crmMembershipProxy.updateVIPCardInfo(request);
		logger.warn("***** resume   response *******" + JaxbUtils.convertToXmlString(response));
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setMembid(response.getBody().getMembid());
		crmResponseDto.setRetcode(response.getBody().getRecode());
		crmResponseDto.setRetmsg(response.getBody().getRemsg());
		return crmResponseDto;
	}

	public CRMResponseDto updateMemberAddress() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public MemberHobbyDto queryMemberHobby(String memberId) {
		MemberHobbyRequest request = new MemberHobbyRequest();
		MemberHobbyResponse response = new MemberHobbyResponse();
		MemberHobbyRequest.Head head = new MemberHobbyRequest.Head();
		MemberHobbyRequest.Body body = new MemberHobbyRequest.Body();
		MemberHobbyDto memberHobbyDto = new MemberHobbyDto();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("20006");
		head.setSystype("JJ000");
		body.setMembid(memberId);
		request.setHead(head);
		request.setBody(body);
		try {
			response = crmMembershipProxy.queryMemberHobby(request);
		} catch (Exception e) {
			logger.error("queryMemberHobby({}) error!", JaxbUtils.convertToXmlString(request), e);
		}
		if (response != null && response.getBody() != null && response.getBody().getListofcontact() != null) {
			memberHobbyDto.setMemberId(memberId);
			MemberHobbyResponse.Body.Listofcontact.Contact contact = response.getBody().getListofcontact().getContact();
			memberHobbyDto.setConpriflag(contact.getConpriflag());
			memberHobbyDto.setCall(contact.getCall());
			memberHobbyDto.setSms(contact.getSms());
			memberHobbyDto.setEmail(contact.getEmail());
			memberHobbyDto.setFax(contact.getFax());
			memberHobbyDto.setPost(contact.getPost());
			memberHobbyDto.setTravel(contact.getTravel());
			memberHobbyDto.setFashion(contact.getFashion());
			memberHobbyDto.setMovie(contact.getMovie());
			memberHobbyDto.setMusic(contact.getMusic());
			memberHobbyDto.setCulture(contact.getCulture());
			memberHobbyDto.setShopping(contact.getShopping());
			memberHobbyDto.setSports(contact.getSports());
			memberHobbyDto.setPubli(contact.getPublic());
			memberHobbyDto.setMuslim(contact.getMuslim());
			memberHobbyDto.setVegetarian(contact.getVegetarian());
			memberHobbyDto.setGame(contact.getGame());
			memberHobbyDto.setMag(contact.getMag());
			memberHobbyDto.setEmailbill(contact.getEmailbill());
			memberHobbyDto.setEmailepro(contact.getEmailepro());
			memberHobbyDto.setEmailinvet(contact.getEmailinvet());
			memberHobbyDto.setEmailsubpartn(contact.getEmailsubpartn());
			memberHobbyDto.setEmailsubpro(contact.getEmailsubpro());
		}
		return memberHobbyDto;

	}

	public String updateHobbies(MemberHobbyDto memberHobbyDto) {
		MemberHobbyUpdateRequest request = new MemberHobbyUpdateRequest();
		MemberHobbyUpdateResponse response = new MemberHobbyUpdateResponse();
		MemberHobbyUpdateRequest.Head head = new MemberHobbyUpdateRequest.Head();
		MemberHobbyUpdateRequest.Body body = new MemberHobbyUpdateRequest.Body();
		MemberHobbyUpdateRequest.Body.Listofcontact.Contact contact = new MemberHobbyUpdateRequest.Body.Listofcontact.Contact();
		MemberHobbyUpdateRequest.Body.Listofcontact listofcontact = new MemberHobbyUpdateRequest.Body.Listofcontact();

		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("30006");
		head.setSystype("JJ000");

		contact.setCall(memberHobbyDto.getCall());
		contact.setSms(memberHobbyDto.getSms());
		contact.setEmail(memberHobbyDto.getEmail());
		contact.setFax(memberHobbyDto.getFax());
		contact.setPost(memberHobbyDto.getPost());
		contact.setTravel(memberHobbyDto.getTravel());
		contact.setFashion(memberHobbyDto.getFashion());
		contact.setMovie(memberHobbyDto.getMovie());
		contact.setMusic(memberHobbyDto.getMusic());
		contact.setCulture(memberHobbyDto.getCulture());
		contact.setShopping(memberHobbyDto.getShopping());
		contact.setSports(memberHobbyDto.getSports());
		contact.setPublic(memberHobbyDto.getPubli());
		contact.setMuslim(memberHobbyDto.getMuslim());
		contact.setVegetarian(memberHobbyDto.getVegetarian());
		contact.setGame(memberHobbyDto.getGame());
		contact.setMag(memberHobbyDto.getMag());
		contact.setEmailbill(memberHobbyDto.getEmailbill());
		contact.setEmailepro(memberHobbyDto.getEmailepro());
		contact.setEmailinvet(memberHobbyDto.getEmailinvet());
		contact.setEmailsubpartn(memberHobbyDto.getEmailsubpartn());
		contact.setEmailsubpro(memberHobbyDto.getEmailsubpro());
		listofcontact.setContact(contact);
		body.setMembid(memberHobbyDto.getMemberId());
		body.setListofcontact(listofcontact);
		request.setHead(head);
		request.setBody(body);

		/*MemberBasicInfoDto memberBasicInfo = queryMemberBaseInfo(memberHobbyDto.getMemberId());
		if (memberBasicInfo == null || memberBasicInfo.getMemberId() == null || memberBasicInfo.getMemberId().equals("")) {
			return "10000";
		}*/

		try {
			response = crmMembershipProxy.updateHobbies(request);
		} catch (Exception e) {
			logger.error("updateHobbies({}) error!", JaxbUtils.convertToXmlString(request), e);
			return null;
		}

		return response.getBody().getRecode();
	}

	// 会员基本信息查询
	public MemberBasicInfoDto queryMemberBaseInfo(String memberId) {
		MemberRequest request = new MemberRequest();
		MemberResponse response = null;
		MemberRequest.Head head = new MemberRequest.Head();
		MemberRequest.Body body = new MemberRequest.Body();

		MemberResponse.Body.Listofcontact.Contact contact = new MemberResponse.Body.Listofcontact.Contact();

		head.setTranscode(new BigInteger("20004"));
		head.setSystype("JJ000");
		head.setResptime(new BigInteger(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
		body.setMembid(memberId);

		request.setHead(head);
		request.setBody(body);
		logger.warn("***** queryMemberBaseInfo  request *******" + JaxbUtils.convertToXmlString(request));
		MemberBasicInfoDto baseInfoDto = new MemberBasicInfoDto();
		try {
			response = crmMembershipProxy.getMember(request);
			logger.warn("***** queryMemberBaseInfo  response *******" + JaxbUtils.convertToXmlString(response));
			contact = response.getBody().getListofcontact().getContact();
			String memberType = response.getBody().getMemberType();
			String newMemberHierarchy = response.getBody().getRightflg();

			baseInfoDto.setCell(contact.getCell());
			baseInfoDto.setEdulevl(contact.getEdulevl());
			baseInfoDto.setEmail(contact.getEmail());
			baseInfoDto.setTitle(contact.getTitle());
			baseInfoDto.setBirthday(contact.getBirthday());
			baseInfoDto.setCdtype(contact.getCdtype());
			baseInfoDto.setCdno(contact.getCdno());
			baseInfoDto.setLastName(contact.getLastname());
			baseInfoDto.setMemberId(memberId);
			baseInfoDto.setMemType(memberType);
			baseInfoDto.setNewMemberHierarchy(newMemberHierarchy);
		} catch (Exception e) {
			logger.error("getMember({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return baseInfoDto;
	}

	// 会员基本信息更新
	public String updateMemberBaseInfo(MemberBasicInfoDto baseInfo) {
		MemberBasicInfoUpdateRequest request = new MemberBasicInfoUpdateRequest();
		MemberBasicInfoUpdateResponse response = null;
		MemberBasicInfoUpdateRequest.Head head = new MemberBasicInfoUpdateRequest.Head();
		MemberBasicInfoUpdateRequest.Body body = new MemberBasicInfoUpdateRequest.Body();
		MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact contact = new MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact();
		MemberBasicInfoUpdateRequest.Body.Listofcontact listofcontact = new MemberBasicInfoUpdateRequest.Body.Listofcontact();
		MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact.Listofpersonaladdress listofpersonaladdress = new MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact.Listofpersonaladdress();
		MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress personaladdress = new MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress();
		// listofpersonaladdress.getPersonaladdress().add(personaladdress);
		// MemberBasicInfoDto memberBasicInfoDto =
		// queryMemberBaseInfo(baseInfo.getMemberId());
		// personaladdress.setAddpriflag("Y");
		// listofpersonaladdress.getPersonaladdress().add(personaladdress);
		String rcode = "";

		head.setTranscode("30004");
		head.setSystype("JJ000");
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		contact.setEmail(baseInfo.getEmail());
		contact.setBirthday(baseInfo.getBirthday());
		contact.setCell(baseInfo.getCell());
		contact.setCdtype(baseInfo.getCdtype());
		contact.setEdulevl(baseInfo.getEdulevl());
		contact.setTitle(baseInfo.getTitle());
		contact.setCdno(baseInfo.getCdno());
		// contact.setListofpersonaladdress(listofpersonaladdress);
		if (baseInfo.getBingFlg() != null && !"".equals(baseInfo.getBingFlg())) {
			body.setBindflg(baseInfo.getBingFlg());
		}
		request.setHead(head);
		listofcontact.getContact().add(contact);
		body.setListofcontact(listofcontact);
		body.setMembid(baseInfo.getMemberId());
		body.setPasswd(baseInfo.getPassword());
		request.setBody(body);
		logger.warn("***** updateMemberBaseInfo  request *******" + JaxbUtils.convertToXmlString(request));
		try {
			response = crmMembershipProxy.updateMemberBasicInfo(request);
			logger.warn("***** updateMemberBaseInfo  response *******" + JaxbUtils.convertToXmlString(response));
			rcode = (response != null && response.getBody() != null) ? response.getBody().getRecode() : "";
		} catch (Exception e) {
			logger.error("updateMemberBaseInfo({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return rcode;
	}

	// 会员基本地址信息查询
	public List<MemberAddressDto> queryMemberAddress(String memberId) {
		MemberAddressRequest request = new MemberAddressRequest();
		MemberAddressResponse response = null;
		MemberAddressRequest.Head head = new MemberAddressRequest.Head();
		MemberAddressRequest.Body body = new MemberAddressRequest.Body();

		head.setTranscode("20007");
		head.setSystype("JJ000");
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		body.setMembid(memberId);

		request.setHead(head);
		request.setBody(body);

		List<MemberAddressDto> addressDtos = new ArrayList<MemberAddressDto>();
		try {
			response = crmMembershipProxy.queryMemberAddress(request);
			if (response != null && response.getBody() != null && response.getBody().getListofcontact() != null) {
				List<MemberAddressResponse.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress> personaladdresses = response.getBody().getListofcontact().getContact()
						.getListofpersonaladdress().getPersonaladdress();
				if (personaladdresses != null) {
					for (MemberAddressResponse.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress personaladdress : personaladdresses) {
						MemberAddressDto memberAddressDto = new MemberAddressDto();
						memberAddressDto.setAddr(personaladdress.getAddr());
						memberAddressDto.setAddrtype(personaladdress.getAddrtype());
						memberAddressDto.setArea(personaladdress.getArea());
						memberAddressDto.setCity(personaladdress.getCity());
						memberAddressDto.setPostcode(personaladdress.getPostcode());
						memberAddressDto.setProvince(personaladdress.getProvince());
						memberAddressDto.setStreet(personaladdress.getStreet());
						memberAddressDto.setAddrnumber(personaladdress.getAddrnumber());
						memberAddressDto.setAddrpriflag(personaladdress.getAddpriflag());
						addressDtos.add(memberAddressDto);
					}
				}
			}
		} catch (Exception e) {
			logger.error("queryMemberAddress({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return addressDtos;
	}

	// 会员基本地址信息查询
	public String updateMemberAddress(List<MemberAddressDto> addressDtos) {
		UpdateMemberAddressRequest request = new UpdateMemberAddressRequest();
		UpdateMemberAddressResponse response = null;
		UpdateMemberAddressRequest.Head head = new UpdateMemberAddressRequest.Head();
		UpdateMemberAddressRequest.Body body = new UpdateMemberAddressRequest.Body();
		UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress listofpersonaladdress = new UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress();
		UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress personaladdress = new UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress();
		UpdateMemberAddressRequest.Body.Listofcontact listofcontact = new UpdateMemberAddressRequest.Body.Listofcontact();
		UpdateMemberAddressRequest.Body.Listofcontact.Contact contact = new UpdateMemberAddressRequest.Body.Listofcontact.Contact();

		head.setTranscode("30007");
		head.setSystype("JJ000");
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		body.setMembid(addressDtos.get(0).getMemberId());
		if (addressDtos != null && addressDtos.size() > 0) {
			for (MemberAddressDto addressDto : addressDtos) {
				personaladdress.setState("中国");
				personaladdress.setAddr(addressDto.getAddr());
				personaladdress.setArea(addressDto.getArea());
				personaladdress.setCity(addressDto.getCity());
				personaladdress.setPostcode(addressDto.getPostcode());
				personaladdress.setStreet(addressDto.getStreet());
				personaladdress.setProvince(addressDto.getProvince());
				personaladdress.setAddrtype(addressDto.getAddrtype());
				personaladdress.setAddpriflag(addressDto.getAddrpriflag());
				personaladdress.setAddrnumber(addressDto.getAddrnumber());
				// .setListofpersonaladdress(listofpersonaladdress.getPersonaladdress().add(personaladdress))
				listofpersonaladdress.getPersonaladdress().add(personaladdress);
			}
		}
		contact.setListofpersonaladdress(listofpersonaladdress);
		listofcontact.setContact(contact);
		body.setListofcontact(listofcontact);
		request.setHead(head);
		request.setBody(body);
		String rcode = "";

		try {
			response = crmMembershipProxy.updateMemberAddress(request);
			rcode = response.getBody().getRecode();

		} catch (Exception e) {
			logger.error("updateMemberAddress({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return rcode;
	}

	// 设定会员基本地址为首地址
	public String firstMemberAddress(List<MemberAddressDto> addressDtos) {
		UpdateMemberAddressRequest request = new UpdateMemberAddressRequest();
		UpdateMemberAddressResponse response = null;
		UpdateMemberAddressRequest.Head head = new UpdateMemberAddressRequest.Head();
		UpdateMemberAddressRequest.Body body = new UpdateMemberAddressRequest.Body();
		UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress listofpersonaladdress = new UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress();
		UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress personaladdress = new UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress();
		UpdateMemberAddressRequest.Body.Listofcontact listofcontact = new UpdateMemberAddressRequest.Body.Listofcontact();
		UpdateMemberAddressRequest.Body.Listofcontact.Contact contact = new UpdateMemberAddressRequest.Body.Listofcontact.Contact();

		head.setTranscode("33007");
		head.setSystype("JJ000");
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		body.setMembid(addressDtos.get(0).getMemberId());
		if (addressDtos != null && addressDtos.size() > 0) {
			for (MemberAddressDto addressDto : addressDtos) {
				personaladdress.setAddr(addressDto.getAddr());
				personaladdress.setArea(addressDto.getArea());
				personaladdress.setCity(addressDto.getCity());
				personaladdress.setPostcode(addressDto.getPostcode());
				personaladdress.setStreet(addressDto.getStreet());
				personaladdress.setProvince(addressDto.getProvince());
				personaladdress.setAddrtype(addressDto.getAddrtype());
				personaladdress.setAddpriflag(addressDto.getAddrpriflag());
				personaladdress.setAddrnumber(addressDto.getAddrnumber());
				// .setListofpersonaladdress(listofpersonaladdress.getPersonaladdress().add(personaladdress))
				listofpersonaladdress.getPersonaladdress().add(personaladdress);
			}
		}
		contact.setListofpersonaladdress(listofpersonaladdress);
		listofcontact.setContact(contact);
		body.setListofcontact(listofcontact);
		request.setHead(head);
		request.setBody(body);
		String rcode = "";

		try {
			response = crmMembershipProxy.updateMemberAddress(request);
			rcode = response.getBody().getRecode();

		} catch (Exception e) {
			logger.error("updateMemberAddress({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return rcode;
	}

	// 会员基本地址信息查询
	public String saveMemberAddress(List<MemberAddressDto> addressDtos) {
		UpdateMemberAddressRequest request = new UpdateMemberAddressRequest();
		UpdateMemberAddressResponse response = null;
		UpdateMemberAddressRequest.Head head = new UpdateMemberAddressRequest.Head();
		UpdateMemberAddressRequest.Body body = new UpdateMemberAddressRequest.Body();
		UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress listofpersonaladdress = new UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress();
		UpdateMemberAddressRequest.Body.Listofcontact listofcontact = new UpdateMemberAddressRequest.Body.Listofcontact();
		UpdateMemberAddressRequest.Body.Listofcontact.Contact contact = new UpdateMemberAddressRequest.Body.Listofcontact.Contact();
		head.setTranscode("32007");
		head.setSystype("JJ000");
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		body.setMembid(addressDtos.get(0).getMemberId());
		if (addressDtos != null && addressDtos.size() > 0) {
			for (MemberAddressDto addressDto : addressDtos) {
				UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress personaladdress = new UpdateMemberAddressRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress();
				personaladdress.setState("中国");
				personaladdress.setAddpriflag("Y");
				personaladdress.setAddr(addressDto.getAddr());
				personaladdress.setArea(addressDto.getArea());
				personaladdress.setCity(addressDto.getCity());
				personaladdress.setPostcode(addressDto.getPostcode());
				personaladdress.setStreet(addressDto.getStreet());
				personaladdress.setProvince(addressDto.getProvince());
				personaladdress.setAddrtype(addressDto.getAddrtype());
				listofpersonaladdress.getPersonaladdress().add(personaladdress);
			}
		}
		contact.setListofpersonaladdress(listofpersonaladdress);
		listofcontact.setContact(contact);
		body.setListofcontact(listofcontact);
		request.setHead(head);
		request.setBody(body);
		String rcode = "";

		try {
			response = crmMembershipProxy.updateMemberAddress(request);
			rcode = response.getBody().getRecode();

		} catch (Exception e) {
			logger.error("updateMemberAddress({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return rcode;
	}

	// 会员基本地址信息查询
	public String deleteMemberAddress(MemberAddressDto addressDto) {
		MemberAddressDeleteRequest request = new MemberAddressDeleteRequest();
		MemberAddressDeleteResponse response = null;
		MemberAddressDeleteRequest.Head head = new MemberAddressDeleteRequest.Head();
		MemberAddressDeleteRequest.Body body = new MemberAddressDeleteRequest.Body();
		MemberAddressDeleteRequest.Body.Listofcontact.Contact.Listofpersonaladdress listofpersonaladdress = new MemberAddressDeleteRequest.Body.Listofcontact.Contact.Listofpersonaladdress();
		MemberAddressDeleteRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress personaladdress = new MemberAddressDeleteRequest.Body.Listofcontact.Contact.Listofpersonaladdress.Personaladdress();
		MemberAddressDeleteRequest.Body.Listofcontact listofcontact = new MemberAddressDeleteRequest.Body.Listofcontact();
		MemberAddressDeleteRequest.Body.Listofcontact.Contact contact = new MemberAddressDeleteRequest.Body.Listofcontact.Contact();

		head.setTranscode("31007");
		head.setSystype("JJ000");
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		body.setMembid(addressDto.getMemberId());
		personaladdress.setAddrnumber(addressDto.getAddrnumber());
		listofpersonaladdress.getPersonaladdress().add(personaladdress);
		contact.setListofpersonaladdress(listofpersonaladdress);
		listofcontact.setContact(contact);
		body.setListofcontact(listofcontact);
		body.setMembid(addressDto.getMemberId());
		request.setHead(head);
		request.setBody(body);
		String rcode = "";

		try {
			response = crmMembershipProxy.deleteMemberAddress(request);
			rcode = response.getBody().getRecode();

		} catch (Exception e) {
			logger.error("deleteMemberAddress({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return rcode;
	}

	// 会员联系信息查询
	public MemberCommunicationDto queryMemberCommunication(String memberId) {
		MemberCommunicationRequest request = new MemberCommunicationRequest();
		MemberCommunicationResponse response = null;
		MemberCommunicationRequest.Head head = new MemberCommunicationRequest.Head();
		MemberCommunicationRequest.Body body = new MemberCommunicationRequest.Body();
		MemberCommunicationResponse.Body.Listofcontact.Contact contact = null;
		MemberCommunicationDto comDto = new MemberCommunicationDto();
		String rcode = "";
		head.setTranscode("20014");
		head.setSystype("JJ000");
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		body.setMembid(memberId);

		request.setHead(head);
		request.setBody(body);

		try {
			response = crmMembershipProxy.queryMemberCommuniction(request);
			if (response != null && response.getBody() != null && response.getBody().getListofcontact() != null) {
				contact = response.getBody().getListofcontact().getContact();
				MemberCommunicationResponse.Body.Listofcontact.Contact.Listofcontactemail listofcontactemail = contact.getListofcontactemail();
				if (listofcontactemail != null && listofcontactemail.getContactemail().size() != 0) {
					MemberCommunicationResponse.Body.Listofcontact.Contact.Listofcontactemail.Contactemail contactEmail = listofcontactemail.getContactemail().get(0);
					if (contactEmail != null) {
						comDto.setEmail(contactEmail.getEmail());
						comDto.setEmailType(contactEmail.getUsetype());
					}
				}

				MemberCommunicationResponse.Body.Listofcontact.Contact.Listofcontactfax listofcontactfax = contact.getListofcontactfax();
				if (listofcontactfax != null && listofcontactfax.getContactfax().size() != 0) {
					MemberCommunicationResponse.Body.Listofcontact.Contact.Listofcontactfax.Contactfax contactFax = listofcontactfax.getContactfax().get(0);
					if (contactFax != null) {
						comDto.setFax(contactFax.getFax());
						comDto.setFaxType(contactFax.getUsetype());
					}
				}

				MemberCommunicationResponse.Body.Listofcontact.Contact.Listofcontactim listofcontactim = contact.getListofcontactim();
				if (listofcontactim != null && listofcontactim.getContactim().size() != 0) {
					MemberCommunicationResponse.Body.Listofcontact.Contact.Listofcontactim.Contactim contactim = listofcontactim.getContactim().get(0);
					if (contactim != null) {
						comDto.setIm(contactim.getIm());
						comDto.setImType(contactim.getUsetype());
					}
				}

				MemberCommunicationResponse.Body.Listofcontact.Contact.Listofcontactphone listofcontactphone = contact.getListofcontactphone();
				if (listofcontactphone != null && listofcontactphone.getContactphone().size() != 0) {
					MemberCommunicationResponse.Body.Listofcontact.Contact.Listofcontactphone.Contactphone contactPhone = contact.getListofcontactphone().getContactphone().get(0);
					if (contactPhone != null) {
						comDto.setPhone(contactPhone.getPhone());
						comDto.setPhoneType(contactPhone.getUsetype());
					}
				}
			}
		} catch (Exception e) {
			logger.error("queryMemberCommuniction({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return comDto;
	}

	// 会员联系信息更新
	public String updateMemberCommunication(MemberCommunicationDto comDto) {
		UpdateContactRequest request = new UpdateContactRequest();
		UpdateContactResponse response = new UpdateContactResponse();
		UpdateContactRequest.Head head = new UpdateContactRequest.Head();
		UpdateContactRequest.Body body = new UpdateContactRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("30014");
		body.setMembid(comDto.getMemberId());
		UpdateContactRequest.Body.Listofcontact listofcontact = new UpdateContactRequest.Body.Listofcontact();
		Contact contact = new Contact();
		if (comDto.getEmail() != null && !"".equals(comDto.getEmail().trim())) {
			Listofcontactemail email = new Listofcontactemail();
			Contactemail mail = new Contactemail();
			mail.setEmail(comDto.getEmail());
			mail.setUsetype(comDto.getEmailType());
			email.getContactemail().add(mail);
			contact.setListofcontactemail(email);
		}

		if (comDto.getFax() != null && !"".equals(comDto.getFax().trim())) {
			Listofcontactfax fax = new Listofcontactfax();
			Contactfax f = new Contactfax();
			f.setFax(comDto.getFax());
			f.setUsetype(comDto.getFaxType());
			fax.getContactfax().add(f);
			contact.setListofcontactfax(fax);
		}

		if (comDto.getIm() != null && !"".equals(comDto.getIm().trim())) {
			Listofcontactim tim = new Listofcontactim();
			Contactim t = new Contactim();
			t.setIm(comDto.getIm());
			t.setUsetype(comDto.getImType());
			tim.getContactim().add(t);
			contact.setListofcontactim(tim);
		}

		if (comDto.getPhone() != null && !"".equals(comDto.getPhone().trim())) {
			Listofcontactphone phone = new Listofcontactphone();
			Contactphone p = new Contactphone();
			p.setPhone(comDto.getPhone());
			p.setUsetype(comDto.getPhoneType());
			phone.getContactphone().add(p);
			contact.setListofcontactphone(phone);
		}

		listofcontact.setContact(contact);
		body.setListofcontact(listofcontact);
		request.setHead(head);
		request.setBody(body);
		try {
			response = crmMembershipProxy.updateContact(request);
		} catch (Exception e) {
			logger.error("updateContact({}) error!", JaxbUtils.convertToXmlString(request), e);
		}
		return response.getBody().getRecode();
	}

	public CRMActivationRespDto validateActivationMember(AccountDto accountDto) throws Exception {
		AccountValidationRequest.Head head = new AccountValidationRequest.Head();
		AccountValidationRequest.Body body = new AccountValidationRequest.Body();
		AccountValidationRequest request = new AccountValidationRequest();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setTranscode(new BigInteger("20020"));
		head.setSystype("JJ000");
		body.setCdno(accountDto.getCdno());
		body.setCdtype(accountDto.getCdtype());
		body.setEmail(accountDto.getEmail());
		body.setMemcdid(accountDto.getCardNo());
		body.setMobile(accountDto.getMobile());
		body.setName(accountDto.getSurname() + accountDto.getName());
		request.setHead(head);
		request.setBody(body);
		AccountValidationResponse response = crmMembershipProxy.validateActivationMember(request);
		CRMActivationRespDto crmActivationRespDto = new CRMActivationRespDto();
		crmActivationRespDto.setMembrowid(response.getBody().getMembrowid());
		crmActivationRespDto.setRetcode(response.getBody().getRecode().toString());
		crmActivationRespDto.setRetmsg(response.getBody().getRemsg());
		return crmActivationRespDto;
	}

	public CRMResponseDto activateMember(AccountDto accountDto) throws Exception {
		AccountActivationRequest.Head head = new AccountActivationRequest.Head();
		AccountActivationRequest.Body body = new AccountActivationRequest.Body();
		AccountActivationRequest request = new AccountActivationRequest();
		head.setSystype("JJ000");
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setTranscode(new BigInteger("30020"));
		body.setMembrowid(accountDto.getMembrowid());
		body.setPasswd(accountDto.getPasswd());
		body.setPwdquestion(accountDto.getPwdquestion());
		body.setPwdanswer(accountDto.getPwdanswer());
		request.setHead(head);
		request.setBody(body);
		AccountActivationResponse response = crmMembershipProxy.activateMember(request);
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setMembid(response.getBody().getMembid());
		crmResponseDto.setRetcode(response.getBody().getRecode().toString());
		crmResponseDto.setRetmsg(response.getBody().getRemsg());
		return crmResponseDto;
	}

	public MemberDto getMemberDto(String memberId) throws Exception {
		MemberRequest request = new MemberRequest();
		MemberResponse response = new MemberResponse();
		MemberRequest.Head head = new MemberRequest.Head();
		MemberRequest.Body body = new MemberRequest.Body();
		head.setResptime(BigInteger.valueOf(Long.parseLong((new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())))));
		head.setTranscode(BigInteger.valueOf(Long.parseLong("20004")));
		head.setSystype("JJ000");
		body.setMembid(memberId);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.getMember(request);
		MemberDto dto = new MemberDto();
		if (response.getHead().getRetcode().equals("00001")) {
			dto.setCellPhone(response.getBody().getListofcontact().getContact().getCell().toString());
		} else {
			dto = null;
		}
		return dto;
	}

	public CRMResponseDto bindPhone(MemberDto memberDto) throws Exception {
		MemberBindRequest request = new MemberBindRequest();
		MemberBindResponse response = new MemberBindResponse();
		MemberBindRequest.Head head = new MemberBindRequest.Head();
		MemberBindRequest.Body body = new MemberBindRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("30002");
		head.setSystype("JJ000");
		body.setMembid(memberDto.getMemberID());
		body.setBindChannel("Website");
		if ("Mobile and Email Activiated".equals(memberDto.getActivateCode())) {
			body.setBindflg("Mobile and Email Activiated");
		}
		if ("Email Activiated".equals(memberDto.getActivateCode())) {
			body.setBindflg("Mobile and Email Activiated");
		}
		if ("Not Activiate".equals(memberDto.getActivateCode()) || "Activiated".equals(memberDto.getActivateCode())) {
			body.setBindflg("Mobile Activiated");
		}
		if ("Mobile Activiated".equals(memberDto.getActivateCode())) {
			body.setBindflg("Mobile Activiated");
		}

		request.setHead(head);
		request.setBody(body);
		logger.warn("***** bindPhone  request *******" + JaxbUtils.convertToXmlString(request));
		response = crmMembershipProxy.bindPhone(request);
		logger.warn("***** bindPhone  response *******" + JaxbUtils.convertToXmlString(response));
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setMembid(response.getBody().getMembid());
		crmResponseDto.setRetcode(response.getBody().getRecode());
		crmResponseDto.setRetmsg(response.getBody().getRemsg());
		return crmResponseDto;
	}

	public CRMResponseDto modifyPwd(MemberDto memberDto) throws Exception {
		ModifyPassWordRequest request = new ModifyPassWordRequest();
		ModifyPassWordResponse response = new ModifyPassWordResponse();
		ModifyPassWordRequest.Head head = new ModifyPassWordRequest.Head();
		ModifyPassWordRequest.Body body = new ModifyPassWordRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("30022");
		head.setSystype("JJ000");
		body.setMembrowid(memberDto.getMemberID());
		body.setPasswd(memberDto.getPassword());
		request.setHead(head);
		request.setBody(body);
		logger.warn("***** modifyPwd  request *******" + JaxbUtils.convertToXmlString(request));
		response = crmMembershipProxy.modifyPwd(request);
		logger.warn("***** modifyPwd  response *******" + JaxbUtils.convertToXmlString(response));
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		// crmResponseDto.setMembid(response.getHead().getMembid());
		crmResponseDto.setRetcode(response.getHead().getRetcode());
		crmResponseDto.setRetmsg(response.getHead().getRetmsg());
		return crmResponseDto;
	}

	public CRMResponseDto bindEmail(MemberDto memberDto) throws Exception {
		MemberBindRequest request = new MemberBindRequest();
		MemberBindResponse response = new MemberBindResponse();
		MemberBindRequest.Head head = new MemberBindRequest.Head();
		MemberBindRequest.Body body = new MemberBindRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("30002");
		head.setSystype("JJ000");
		body.setMembid(memberDto.getMemberID());
		body.setBindChannel("Website");
		if ("Mobile and Email Activiated".equals(memberDto.getActivateCode())) {
			body.setBindflg("Mobile and Email Activiated");
		}
		if ("Mobile Activiated".equals(memberDto.getActivateCode())) {
			body.setBindflg("Mobile and Email Activiated");
		}
		if ("Not Activiate".equals(memberDto.getActivateCode()) || "Activiated".equals(memberDto.getActivateCode())) {
			body.setBindflg("Email Activiated");
		}
		if ("Email Activiated".equals(memberDto.getActivateCode())) {
			body.setBindflg("Email Activiated");
		}

		request.setHead(head);
		request.setBody(body);
		logger.warn("***** bindEmail  request *******" + JaxbUtils.convertToXmlString(request));
		response = crmMembershipProxy.bindPhone(request);
		logger.warn("***** bindEmail  response *******" + JaxbUtils.convertToXmlString(response));
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setMembid(response.getBody().getMembid());
		crmResponseDto.setRetcode(response.getBody().getRecode());
		crmResponseDto.setRetmsg(response.getBody().getRemsg());
		return crmResponseDto;
	}

	// 会员迁移基本信息更新
	public String updateMemberMoveBaseInfo(MemberBasicInfoDto baseInfo) {
		MemberBasicInfoUpdateRequest request = new MemberBasicInfoUpdateRequest();
		MemberBasicInfoUpdateResponse response = null;
		MemberBasicInfoUpdateRequest.Head head = new MemberBasicInfoUpdateRequest.Head();
		MemberBasicInfoUpdateRequest.Body body = new MemberBasicInfoUpdateRequest.Body();
		MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact contact = new MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact();
		MemberBasicInfoUpdateRequest.Body.Listofcontact listofcontact = new MemberBasicInfoUpdateRequest.Body.Listofcontact();
		String rcode = "";
		head.setTranscode("30004");
		head.setSystype("JJ000");
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

		contact.setCell(baseInfo.getCell());
		contact.setEmail(baseInfo.getEmail());
		contact.setTitle(baseInfo.getTitle());

		request.setHead(head);
		listofcontact.getContact().add(contact);
		body.setListofcontact(listofcontact);
		body.setMembid(baseInfo.getMemberId());
		body.setBindflg(baseInfo.getBingFlg());
		body.setPasswd(baseInfo.getPassword());
		request.setBody(body);
		try {
			logger.warn("updateMemberMoveBaseInfo  send crm  Request---> requestInfo =" + JaxbUtils.convertToXmlString(request));
			response = crmMembershipProxy.updateMemberBasicInfo(request);
			logger.warn("updateMemberMoveBaseInfo  send crm  reponse---> reponseInfo =" + JaxbUtils.convertToXmlString(response));
			rcode = (response != null && response.getBody() != null) ? response.getBody().getRecode() : "";
		} catch (Exception e) {
			logger.error("updateMemberBasicInfo({}) error!", JaxbUtils.convertToXmlString(request), e);
		}

		return rcode;
	}

	public MemberInfoUpdateResponse activeMemberV2(MemberBasicInfoDto baseInfo) {
		MemberBasicInfoUpdateRequest request = new MemberBasicInfoUpdateRequest();
		MemberInfoUpdateResponse response = null;
		MemberBasicInfoUpdateRequest.Head head = new MemberBasicInfoUpdateRequest.Head();
		MemberBasicInfoUpdateRequest.Body body = new MemberBasicInfoUpdateRequest.Body();
		MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact contact = new MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact();
		MemberBasicInfoUpdateRequest.Body.Listofcontact listofcontact = new MemberBasicInfoUpdateRequest.Body.Listofcontact();
		head.setTranscode(CrmTransCode.ACTIVE_MEMBER.getCode());
		head.setSystype(CrmConstant.SYSTEM_TYPE);
		head.setReqtime(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
		request.setHead(head);
		body.setMembid(baseInfo.getMemberId());
		body.setBindflg(baseInfo.getBingFlg());
		body.setPasswd(baseInfo.getPassword());
		body.setActiveChannel(baseInfo.getRegistChannel().name());
		// 新增合作类型
		body.setThirdpartyType(baseInfo.getThirdpartyType());

		//
		if ("Y".equals(baseInfo.getIntegerValue())) {
			body.setCellflg(baseInfo.getIntegerValue());
		} else {
			body.setCellflg("N");
		}

		contact.setEmail(baseInfo.getEmail());
		contact.setCell(baseInfo.getCell());
		contact.setTitle(baseInfo.getTitle());
		listofcontact.getContact().add(contact);
		body.setListofcontact(listofcontact);
		request.setBody(body);
		try {
			logger.info("activeMember send crm  Request---> " + JaxbUtils.convertToXmlString(request));
			response = crmMembershipProxy.updateMemberInfo(request);
			logger.info("activeMember send crm  reponse---> " + JaxbUtils.convertToXmlString(response));
			return response;
		} catch (Exception e) {
			logger.error("activeMember updateMemberBasicInfo({}) error ", JaxbUtils.convertToXmlString(request), e);
			return null;
		}
	}

	public String activeMember(MemberBasicInfoDto baseInfo) {
		MemberBasicInfoUpdateRequest request = new MemberBasicInfoUpdateRequest();
		MemberBasicInfoUpdateResponse response = null;
		MemberBasicInfoUpdateRequest.Head head = new MemberBasicInfoUpdateRequest.Head();
		MemberBasicInfoUpdateRequest.Body body = new MemberBasicInfoUpdateRequest.Body();
		MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact contact = new MemberBasicInfoUpdateRequest.Body.Listofcontact.Contact();
		MemberBasicInfoUpdateRequest.Body.Listofcontact listofcontact = new MemberBasicInfoUpdateRequest.Body.Listofcontact();
		String rcode = "";
		head.setTranscode(CrmTransCode.ACTIVE_MEMBER.getCode());
		head.setSystype(CrmConstant.SYSTEM_TYPE);
		head.setReqtime(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
		request.setHead(head);
		body.setMembid(baseInfo.getMemberId());
		body.setBindflg(baseInfo.getBingFlg());
		body.setPasswd(baseInfo.getPassword());
		body.setActiveChannel(baseInfo.getRegistChannel().name());
		// 新增合作类型
		body.setThirdpartyType(baseInfo.getThirdpartyType());
		contact.setEmail(baseInfo.getEmail());
		contact.setCell(baseInfo.getCell());
		contact.setTitle(baseInfo.getTitle());
		listofcontact.getContact().add(contact);
		body.setListofcontact(listofcontact);
		request.setBody(body);
		try {
			logger.info("activeMember send crm  Request---> " + JaxbUtils.convertToXmlString(request));
			response = crmMembershipProxy.updateMemberBasicInfo(request);
			logger.info("activeMember send crm  reponse---> " + JaxbUtils.convertToXmlString(response));
			rcode = (response != null && response.getBody() != null) ? response.getBody().getRecode() : "";
		} catch (Exception e) {
			logger.error("activeMember updateMemberBasicInfo({}) error ", JaxbUtils.convertToXmlString(request), e);
		}
		return rcode;
	}

	public CRMResponseDto queryByCrsId(String crsId, String phone) throws Exception {
		ThirdPartyLoginRequest request = new ThirdPartyLoginRequest();
		ThirdPartyLoginResponse response = new ThirdPartyLoginResponse();

		ThirdPartyLoginRequest.Head head = new ThirdPartyLoginRequest.Head();
		ThirdPartyLoginRequest.Body body = new ThirdPartyLoginRequest.Body();

		head.setReqtime(BigInteger.valueOf(Long.parseLong((new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())))));
		head.setTranscode(BigInteger.valueOf(Long.parseLong("30018")));
		head.setSystype("JJ002");
		body.setCrsid(crsId);
		body.setPhone(phone);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.queryByCrsId(request);
		CRMResponseDto dto = new CRMResponseDto(response.getBody().getMembid(), response.getBody().getRecode(), response.getBody().getRemsg());
		dto.setExecSuccess(StringUtils.equals("00001", response.getBody().getRecode()));
		return dto;
	}

	public CRMResponseDto bindSns(MemberContactsnsDto memberContactsnsDto) throws Exception {
		MemberSnsBindRequest request = new MemberSnsBindRequest();
		MemberSnsBindResponse response = new MemberSnsBindResponse();
		MemberSnsBindRequest.Head head = new MemberSnsBindRequest.Head();
		MemberSnsBindRequest.Body body = new MemberSnsBindRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("30003");
		head.setSystype("JJ000");
		body.setMembid(memberContactsnsDto.getMemberId());
		MemberSnsBindRequest.Body.Contactsns sns = new MemberSnsBindRequest.Body.Contactsns();
		sns.setBindchannel(memberContactsnsDto.getBindChannel());
		sns.setComments(memberContactsnsDto.getComments());
		sns.setSns(memberContactsnsDto.getSns());
		sns.setStatus(memberContactsnsDto.getStatus());
		sns.setUsetype(memberContactsnsDto.getUseType());

		body.setContactsns(sns);
		request.setHead(head);
		request.setBody(body);
		logger.warn("***** bindSns  request *******" + JaxbUtils.convertToXmlString(request));
		response = crmMembershipProxy.bindSns(request);
		logger.warn("***** bindSns  response *******" + JaxbUtils.convertToXmlString(response));
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		
		if("00001".equals(response.getHead().getRetcode()) ){
			crmResponseDto.setExecSuccess(true);
			MemberContactsns memberContactsns = new MemberContactsns(memberContactsnsDto);
			memberContactsnsRepository.saveOrUpdate(memberContactsns);
			
		}
		crmResponseDto.setMembid(response.getBody().getMembid());
		crmResponseDto.setRetcode(response.getHead().getRetcode());
		crmResponseDto.setRetmsg(response.getHead().getRetmsg());
		return crmResponseDto;
	}
	
	private MemberDto getMemberDtoByBody(MemberRegisterResponse.Body body) {
        com.jje.membercenter.xsd.MemberRegisterResponse.Record.Member member = body.getRecord().getMember();
        MemberDto dto = new MemberDto();
        dto.setMemberID(body.getMembid());
        dto.setFullName(member.getFullName());
        dto.setIdentityNo(member.getCDNumber());
        dto.setIdentityType(member.getCDType());
        dto.setCellPhone(member.getCellPhone());
        dto.setEmail(member.getEmail());
        dto.setLastUpd(DateUtils.parseDate(member.getLastUpdDate(), "MM/dd/yyyy"));
        dto.setNewMemberHierarchy(member.getMemberHierarchy());//crm传过来的层级放入新层级字段
        dto.setMemberType(member.getMemberType());
        dto.setTitle(member.getMemberTitle());
        dto.setPassword(member.getPassword());
        dto.setRegisterDate(DateUtils.parseDate(member.getRegisterDate(), "MM/dd/yyyy"));
        dto.setRegisterSource(member.getRegisterSource());
        dto.setRegisterTag(member.getRegisterSourceTag());
        dto.setRemindAnswer(member.getRemindAnswer());
        dto.setRemindQuestion(member.getRemindQuestion());
        dto.setStatus(member.getStatus());
        dto.setThirdpartyType(member.getThirdpartyType());
        dto.setCardList(member.getListofcard().getMemberCardList());
        dto.setMemberCode(member.getMemberCode());
        dto.setActivateCode(member.getJJBEActivateCode());
        dto.setCardNo(member.getJJBECardNumber());
        dto.setCardLevel(member.getCardLevel());
//        dto.setMemberHierarchy(member.getMemberHierarchy());
		dto.setScoreType(NumberUtils.toInt(body.getRecord().getMember().getJJBEConvertFlag()));
        return dto;
    }
}
