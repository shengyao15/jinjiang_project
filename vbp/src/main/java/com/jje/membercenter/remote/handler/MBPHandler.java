package com.jje.membercenter.remote.handler;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.RestClient;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.mms.mmsmanage.EmailMessageDto;
import com.jje.dto.mms.mmsmanage.MessageRespDto;
import com.jje.dto.mms.mmsmanage.ShortMessageDto;
import com.jje.dto.nbp.SendType;
import com.jje.dto.nbp.TemplateModule;
import com.jje.membercenter.domain.WebMember;
import com.jje.membercenter.template.TemplateUtils;

@Component
public class MBPHandler {

	private static Logger logger = LoggerFactory.getLogger(MBPHandler.class);

	@Value(value = "${mbp.mms.url}")
	private String mmsService;
	
	@Value(value = "${nbp.url}")
	private String nbp;

	@Autowired
	private RestClient restClient;

	@Value(value = "${member.add.message}")
	private String memberAddMessage;

	@Value(value = "${member.transfer.message}")
	private String memberTransferMessage;

	@Value(value = "${member.add.email.message}")
	private String memberAddEmailMessage;

	@Value(value = "${member.transfer.email.message}")
	private String memberTransferEmailMessage;

	@Value(value = "${member.email.subject}")
	private String emailSubject;

	@Value(value = "${sender.mail.message}")
	private String senderName;

	@Value(value = "${senderEmail.mail.message}")
	private String senderEmail;
	
	@Value(value = "${replyName.mail.message}")
	private String replyName;

	@Value(value = "${replyEmail.mail.message}")
	private String replyEmail;

	@Value(value = "${quickRegister.email.subject}")
	private String quickRegisterEmailSubject;

	@Autowired
	private TemplateUtils templateUtils;
	
	private String QUICK_REGISTER_EMAIL_MESSAGE_TEMPLATE = "vbp_email_quick_register_succeed.vm";
	
	public MessageRespDto sendShortMessage(ShortMessageDto shortMessageDto) {
		return restClient.post(mmsService + "/shortmsg", shortMessageDto,MessageRespDto.class);
	}

	public MessageRespDto sendEmailMessage(EmailMessageDto emailMessageDto) {
		return restClient.post(mmsService + "/emailmsg", emailMessageDto,MessageRespDto.class);
	}

	public MessageRespDto sendPasswordBySMS(String cellPhone, String template, MemberDto memberDto) {
		try {
			ShortMessageDto shortMessageDto = buildShortMessageDto(cellPhone,"会员模块发密码到门店注册会员手机");
			String msg = template;
			msg = msg.replace("[fullName]", memberDto.getFullName());
			msg = msg.replace("[cardNo]", memberDto.getCardNo());
			msg = msg.replace("[password]", memberDto.getPassword());
			shortMessageDto.setMessage(msg);

			if (logger.isDebugEnabled()) {
				logger.debug("sendPasswordBySMS:" + shortMessageDto+ ";Message:" + msg);
			}
			MessageRespDto messageRespDto = sendShortMessage(shortMessageDto);
			if (logger.isInfoEnabled()) {
				logger.info("sendPasswordBySMS[" + shortMessageDto + "] result:" + messageRespDto);
			}
			return messageRespDto;
		} catch (Exception e) {
			logger.error("sendPasswordBySMS[" + cellPhone + "] error!", e);
			return buildErrorMessageRespDto();
		}

	}

	public enum TemplateType {
		TRANSFER, ADD;
	}

	public MessageRespDto sendPasswordForStoreRegister(TemplateType type,MemberDto memberDto) {
		if (StringUtils.isNotBlank(memberDto.getCellPhone())) {
			return sendSMSPasswordForStoreRegister(type, memberDto);
		} else if (StringUtils.isNotBlank(memberDto.getEmail())) {
			return sendEmailPasswordForStoreRegister(type, memberDto);
		} else {
			logger.error(String.format("sendPassword error! memberDto = {}",memberDto));
			return buildErrorMessageRespDto();
		}

	}

	private MessageRespDto sendSMSPasswordForStoreRegister(TemplateType type,MemberDto memberDto) {
		String template = memberAddMessage;
		if (type.equals(TemplateType.TRANSFER)) {
			template = memberTransferMessage;
		}
		return sendPasswordBySMS(memberDto.getCellPhone(), template, memberDto);
	}

	private MessageRespDto sendEmailPasswordForStoreRegister(TemplateType type,MemberDto memberDto) {
		String template = memberAddEmailMessage;
		if (type.equals(TemplateType.TRANSFER)) {
			template = memberTransferEmailMessage;
		}
		return sendPasswordByEmail(template, memberDto);
	}

	private MessageRespDto sendPasswordByEmail(String template, MemberDto memberDto) {
		String email = memberDto.getEmail();
		try {
//			EmailMessageDto emailMessageDto = buildEmailMessageDto("会员模块发邮件到门店注册会员邮箱");
//			emailMessageDto.setSenderName(senderName);
//			emailMessageDto.setSenderEmail(senderEmail);
//			emailMessageDto.setEmailReceiver(buildEmailReceiverDto(email));
//			
//			// emailMessageDto.setReplyName(replyName);
//			// emailMessageDto.setReplyEmail(replyEmail);
//			template = template.replace("[fullName]", memberDto.getFullName());
//			template = template.replace("[cardNo]", memberDto.getCardNo());
//			template = template.replace("[password]", memberDto.getPassword());
//			emailMessageDto.setBody(template);
//			emailMessageDto.setSubject(emailSubject);
//			return sendEmailMessage(emailMessageDto);
			
			JSONObject contextJson = new JSONObject();
			contextJson.put("fullName",memberDto.getFullName());
			contextJson.put("cardNo", memberDto.getCardNo());
			contextJson.put("password",memberDto.getPassword());
			
			 com.jje.dto.nbp.EmailMessageDto emailDto = new com.jje.dto.nbp.EmailMessageDto();
			 emailDto.setContext(contextJson.toString());
			 emailDto.setEmailReceiver(email);
			 emailDto.setModule(TemplateModule.MEMBER);
			 emailDto.setPriority(1);
			 emailDto.setSendSource("会员模块发邮件到快速注册会员邮箱");
			 emailDto.setSendType(SendType.PRODUCT);
			 emailDto.setSubject(emailSubject);
			 emailDto.setTemplateNo("M_Member_QuickRegister_Email");
			 emailDto.setSenderName(senderName);
			 emailDto.setSenderEmail(senderEmail);
			
			 restClient.post(nbp + "/mail/sendSingleMail", emailDto);
			 MessageRespDto messageRespDto = new MessageRespDto();
			messageRespDto.setStatus("T");
			return messageRespDto;
		} catch (Exception e) {
			logger.error("sendPasswordByEmail[" + email + "] error!", e);
			return buildErrorMessageRespDto();
		}
	}

	public MessageRespDto sendQuickRegisterSuccessMessage(WebMember webMember) {
	   if (StringUtils.isNotBlank(webMember.getEmail())) {
			return sendEmailPasswordForQuickRegisterSuccessMessage(webMember);
		} else {
			logger.error("sendQuickRegisterSuccessMessage(WebMember webMember) error! {}", webMember);
			return buildErrorMessageRespDto();
		}
	}
	
	private MessageRespDto sendEmailPasswordForQuickRegisterSuccessMessage(WebMember webMember) {
		String quickRegisterEmailMessage = buildQuickRegisterEmailMessage(webMember);
		return sendEmailForWebMember(quickRegisterEmailMessage, webMember);
	}

	private String buildQuickRegisterEmailMessage(WebMember webMember) {
		Map<String, Object> msgArgs = new HashMap<String, Object>();
		msgArgs.put("webMember", webMember);
		return templateUtils.merge(QUICK_REGISTER_EMAIL_MESSAGE_TEMPLATE, msgArgs);
	}

	private MessageRespDto sendEmailForWebMember(String template,WebMember webMember) {
		String email = webMember.getEmail();
		try {
//			EmailMessageDto emailMessageDto = buildEmailMessageDto("会员模块发邮件到快速注册会员邮箱");
//			emailMessageDto.setSenderName(senderName);
//			emailMessageDto.setSenderEmail(senderEmail);
//			emailMessageDto.setReplyEmail(replyEmail);
//			emailMessageDto.setReplyName(replyName);
//			emailMessageDto.setEmailReceiver(buildEmailReceiverDto(email));
//			template = template.replace("[fullName]", email);
//			template = template.replace("[cardNo]", webMember.getTempCardNo());
//			template = template.replace("[password]", webMember.getPwd());
//			emailMessageDto.setBody(template);
//			emailMessageDto.setSubject(quickRegisterEmailSubject);
//			return sendEmailMessage(emailMessageDto);
			
			JSONObject contextJson = new JSONObject();
			contextJson.put("fullName",email);
			contextJson.put("cardNo", webMember.getTempCardNo());
			contextJson.put("password",webMember.getPwd());
			
			 com.jje.dto.nbp.EmailMessageDto emailDto = new com.jje.dto.nbp.EmailMessageDto();
			 emailDto.setContext(contextJson.toString());
			 emailDto.setEmailReceiver(buildEmailReceiverDto(email));
			 emailDto.setModule(TemplateModule.MEMBER);
			 emailDto.setPriority(1);
			 emailDto.setSendSource("会员模块发邮件到快速注册会员邮箱");
			 emailDto.setSendType(SendType.PRODUCT);
			 emailDto.setSubject(quickRegisterEmailSubject);
			 emailDto.setTemplateNo("M_Member_QuickRegister_Email");
			 emailDto.setSenderName(senderName);
			 emailDto.setSenderEmail(senderEmail);
			 emailDto.setReplyEmail(replyEmail);
			 emailDto.setReplyName(replyName);
			
			 restClient.post(nbp + "/mail/sendSingleMail", emailDto);
			 MessageRespDto messageRespDto = new MessageRespDto();
			messageRespDto.setStatus("T");
			return messageRespDto;
		} catch (Exception e) {
			logger.error("sendEmailForWebMember[" + email+ "] error!", e);
			return buildErrorMessageRespDto();
		}
	}
	
	public MessageRespDto sendSMSForWebMember(String template,WebMember webMember) {
		String phone = webMember.getPhone();
		try {
			ShortMessageDto shortMessageDto = buildShortMessageDto(phone,"会员模块发密码到快速注册会员手机");
			String msg = template;
			msg = msg.replace("[fullName]", phone);
			msg = msg.replace("[cardNo]", webMember.getTempCardNo());
			msg = msg.replace("[password]", webMember.getPwd());
			shortMessageDto.setMessage(msg);

			if (logger.isDebugEnabled()) {
				logger.debug("sendSMSForWebMember:" + shortMessageDto+ ";Message:" + msg);
			}

			MessageRespDto messageRespDto = sendShortMessage(shortMessageDto);

			if (logger.isInfoEnabled()) {
				logger.info("sendSMSForWebMember[" + shortMessageDto + "] result:" + messageRespDto);
			}
			return messageRespDto;
		} catch (Exception e) {
			logger.error("sendSMSForWebMember[" + phone + "] error!", e);
			return buildErrorMessageRespDto();
		}
	}

	private ShortMessageDto buildShortMessageDto(String phone, String sendSource) {
		ShortMessageDto shortMessageDto = new ShortMessageDto();
		shortMessageDto.setUserName("jinjiangmms");
		shortMessageDto.setPassword("123456");
		shortMessageDto.setSendSource(sendSource);
		shortMessageDto.setRealTimeFlag("true");
		shortMessageDto.setPriority(0);
		shortMessageDto.setMobile(phone);
		return shortMessageDto;
	}
	
	private String buildEmailReceiverDto(String... emails) {
		String emailReceiver = "";
		for (String email : emails) {
			emailReceiver+=email+",";
		}
		return emailReceiver;
	}

	private MessageRespDto buildErrorMessageRespDto() {
		MessageRespDto messageRespDto = new MessageRespDto();
		messageRespDto.setStatus("F");
		return messageRespDto;
	}
}
