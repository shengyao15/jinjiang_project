package com.jje.vbp.handler;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.dto.mms.mmsmanage.MessageRespDto;
import com.jje.dto.nbp.SendType;
import com.jje.dto.nbp.TemplateModule;
import com.jje.vbp.handler.proxy.MbpProxy;
import com.jje.vbp.regist.domain.WebMemberInfo;
import com.jje.vbp.template.MsgTemplateUtils;

@Component
public class MbpHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "${sender.mail.message}")
	private String senderName;

	@Value(value = "${senderEmail.mail.message}")
	private String senderEmail;

	@Value(value = "${quickRegister.email.subject}")
	private String quickRegisterEmailSubject;

	@Autowired
	private MsgTemplateUtils msgTemplateUtils;
	
	@Autowired
	private MbpProxy mbpProxy;
	
//	private String QUICK_REGISTER_EMAIL_MESSAGE_TEMPLATE = "vbp_email_quick_register_succeed.vm";
	
	public MessageRespDto sendQuickRegisterSuccessMessage(WebMemberInfo webMemberInfo) {
	   if (StringUtils.isNotBlank(webMemberInfo.getEmail())) {
			return sendEmailPasswordForQuickRegisterSuccessMessage(webMemberInfo);
		} else {
			logger.error("sendQuickRegisterSuccessMessage(WebMemberInfo webMemberInfo) error! {}", webMemberInfo);
			return buildErrorMessageRespDto();
		}
	}
	
	private MessageRespDto sendEmailPasswordForQuickRegisterSuccessMessage(WebMemberInfo webMemberInfo) {
		return sendEmailForWebMember(webMemberInfo);
	}

//	private String buildQuickRegisterEmailMessage(WebMemberInfo webMemberInfo) {
//		Map<String, Object> msgArgs = new HashMap<String, Object>();
//		msgArgs.put("webMember", webMemberInfo);
//		return msgTemplateUtils.merge(QUICK_REGISTER_EMAIL_MESSAGE_TEMPLATE, msgArgs);
//	}

	private MessageRespDto sendEmailForWebMember(WebMemberInfo webMemberInfo) {
		String email = webMemberInfo.getEmail();
		try {
//			EmailMessageDto emailMessageDto = buildEmailMessageDto("会员模块发邮件到快速注册会员邮箱");
//			emailMessageDto.setSenderName(senderName);
//			emailMessageDto.setSenderEmail(senderEmail);
//			emailMessageDto.setEmailReceiver(buildEmailReceiverDto(email));
//			template = template.replace("[fullName]", email);
//			template = template.replace("[cardNo]", webMemberInfo.getTempCardNo());
//			template = template.replace("[password]", webMemberInfo.getPwd());
//			emailMessageDto.setBody(template);
//			emailMessageDto.setSubject(quickRegisterEmailSubject);
			
			JSONObject contextJson = new JSONObject();
			contextJson.put("fullName",email);
			contextJson.put("cardNo", webMemberInfo.getTempCardNo());
			contextJson.put("password",webMemberInfo.getPwd());
			
			 com.jje.dto.nbp.EmailMessageDto emailDto = new com.jje.dto.nbp.EmailMessageDto();
			 emailDto.setContext(contextJson.toString());
			 emailDto.setEmailReceiver(email);
			 emailDto.setModule(TemplateModule.MEMBER);
			 emailDto.setPriority(1);
			 emailDto.setSendSource("会员模块发邮件到快速注册会员邮箱");
			 emailDto.setSendType(SendType.PRODUCT);
			 emailDto.setSubject(quickRegisterEmailSubject);
			 emailDto.setTemplateNo("M_Member_QuickRegister_Email");
			 emailDto.setSenderName(senderName);
			 emailDto.setSenderEmail(senderEmail);
			
			 mbpProxy.sendEmailMessageNew(emailDto);
			 MessageRespDto messageRespDto = new MessageRespDto();
			messageRespDto.setStatus("T");
			return messageRespDto;
		} catch (Exception e) {
			logger.error("sendEmailForWebMember[" + email+ "] error!", e);
			return buildErrorMessageRespDto();
		}
	}
	

//	private List<EmailReceiverDto> buildEmailReceiverDto(String... emails) {
//		List<EmailReceiverDto> emailReceiver = new ArrayList<EmailReceiverDto>();
//		for (String email : emails) {
//			EmailReceiverDto emailReceiverDto = new EmailReceiverDto();
//			emailReceiverDto.setEmail(email);
//			emailReceiver.add(emailReceiverDto);
//		}
//		return emailReceiver;
//	}
//
//	private EmailMessageDto buildEmailMessageDto(String sendSource) {
//		EmailMessageDto emailMessageDto = new EmailMessageDto();
//		emailMessageDto.setSendSource(sendSource);
//		emailMessageDto.setPriority(1);
//		emailMessageDto.setBodyHtml(true);
//		return emailMessageDto;
//	}
	
	private MessageRespDto buildErrorMessageRespDto() {
		MessageRespDto messageRespDto = new MessageRespDto();
		messageRespDto.setStatus("F");
		return messageRespDto;
	}
}
