package com.jje.membercenter.remote.handler;


import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.jje.membercenter.template.URLUtils;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.RestClient;
import com.jje.data.util.JsonUtils;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.mms.mmsmanage.MessageRespDto;
import com.jje.dto.nbp.SendType;
import com.jje.dto.nbp.ShortMessageDto;
import com.jje.dto.nbp.TemplateModule;
import com.jje.membercenter.domain.WebMember;


@Component
public class NBPHandler {

    @Value(value = "${nbp.url}")
    private String nbpUrl;

    @Autowired
    private RestClient restClient;

    private static Logger logger = LoggerFactory.getLogger(NBPHandler.class);

    @Value(value = "${member.add.message.templateNo}")
    private String memberAddMessageTemplateNo;

    @Value(value = "${member.transfer.message.templateNo}")
    private String memberTransferMessageTemplateNo;
    
    @Value(value = "${quickRegister.email.subject}")
	private String quickRegisterEmailSubject;
    
    @Value(value = "${sender.mail.message}")
	private String senderName;

	@Value(value = "${senderEmail.mail.message}")
	private String senderEmail;

    @Autowired
    private URLUtils urlUtils;

    @Value(value = "${flamingo.url}")
    private String flamingoUrl;

    private static final String REDIRECT_URL = "/membercenter/enjoy";






    @Async
    public MessageRespDto sendShortMessage(ShortMessageDto shortMessageDto) {
        try{
        restClient.post(nbpUrl+"/shortMessage/sendShortMessage/",shortMessageDto);
        }catch (Exception e){
        logger.error("NBPHandler 发送短信失败{}",e);
            return new MessageRespDto("F",null);
        }
        return new MessageRespDto("T",null);
    }


    public MessageRespDto sendPasswordForStoreRegister(TemplateType type,MemberDto memberDto) {
        if (StringUtils.isNotBlank(memberDto.getCellPhone())) {
            return sendSMSPasswordForStoreRegister(type, memberDto);
        } else if (StringUtils.isNotBlank(memberDto.getEmail())) {
            return sendEmailPasswordForStoreRegister(memberDto);
        } else {
            logger.error(String.format("sendPassword error! memberDto = {}",memberDto));
            return buildErrorMessageRespDto();
        }
    }

    private MessageRespDto sendSMSPasswordForStoreRegister(TemplateType type,MemberDto memberDto) {
        String templateNo = memberAddMessageTemplateNo;
        if (type.equals(TemplateType.TRANSFER)) {
            templateNo = memberTransferMessageTemplateNo;
        }
        return sendPasswordBySMS(memberDto.getCellPhone(), templateNo, memberDto);
    }


    public MessageRespDto sendPasswordBySMS(String cellPhone, String templateNo, MemberDto memberDto) {
        try {
            ShortMessageDto shortMessageDto = new ShortMessageDto( "会员模块发密码到门店注册会员手机",cellPhone,TemplateModule.MEMBER);
            shortMessageDto.setTemplateNo(templateNo);
            Map context = new HashMap();
            context.put("fullName", memberDto.getFullName());
            context.put("cardNo", memberDto.getCardNo());
            context.put("password", memberDto.getPassword());
            shortMessageDto.setContext(JsonUtils.objectToJson(context));
            if (logger.isDebugEnabled()) {
                logger.debug("sendPasswordBySMS:" + shortMessageDto+ ";Message:" + context);
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



    private MessageRespDto buildErrorMessageRespDto() {
        MessageRespDto messageRespDto = new MessageRespDto();
        messageRespDto.setStatus("F");
        return messageRespDto;
    }



    public MessageRespDto sendSMSForWebMember(String templateNo, WebMember webMember) {
        String phone="";
        try {
            phone = webMember.getPhone();
            ShortMessageDto shortMessageDto = new ShortMessageDto("会员模块发密码到快速注册会员手机",phone,TemplateModule.MEMBER);
            shortMessageDto.setTemplateNo(templateNo);
            Map context = new HashMap();
            context.put("fullName",phone);
            context.put("cardNo", webMember.getTempCardNo());
            context.put("password",  webMember.getPwd());
            shortMessageDto.setContext(JsonUtils.objectToJson(context));
            if (logger.isDebugEnabled()) {
                logger.debug("sendSMSForWebMember:" + shortMessageDto+ ";Message:" + context);
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

    public MessageRespDto sendQuickRegisterSuccessMessage(WebMember webMember) {
    	 if (StringUtils.isNotBlank(webMember.getEmail())) {
 			return sendEmailForWebMember(webMember);
 		} else {
 			logger.error("sendQuickRegisterSuccessMessage(WebMemberInfo webMemberInfo) error! {}", webMember);
 			return buildErrorMessageRespDto();
 		}
    }

    public enum TemplateType {
        TRANSFER, ADD;
    }

    private void initForRegister(Map<String, Object> msgArgs,WebMember member) throws Exception {
            String flamingoRedirectUrl = null;
            flamingoRedirectUrl = urlUtils.buildAutoLoginUrl(member.getMcMemberCode(), member.getEmail(), flamingoUrl + REDIRECT_URL);
            msgArgs.put("flamingoRedirectUrl", flamingoRedirectUrl);
            msgArgs.put("urlUtils", URLUtils.class);
    }
    
    private MessageRespDto sendEmailForWebMember(WebMember webMemberInfo) {
		String email = webMemberInfo.getEmail();
		try {
			HashMap contextJson = new HashMap();
			contextJson.put("name",email);
			contextJson.put("cardNo", webMemberInfo.getTempCardNo());
			contextJson.put("password",webMemberInfo.getPwd());
            initForRegister(contextJson,webMemberInfo);
            com.jje.dto.nbp.EmailMessageDto emailDto = new com.jje.dto.nbp.EmailMessageDto();
			 emailDto.setContext(JsonUtils.objectToJson(contextJson));
			 emailDto.setEmailReceiver(email);
			 emailDto.setModule(TemplateModule.MEMBER);
			 emailDto.setPriority(1);
			 emailDto.setSendSource("会员模块发邮件到快速注册会员邮箱");
			 emailDto.setSendType(SendType.PRODUCT);
			 emailDto.setSubject(quickRegisterEmailSubject);
//			 emailDto.setTemplateNo("M_Member_Is_Registered_Email");
			 emailDto.setTemplateNo("M_Member_QuickRegister_Email");
			 emailDto.setSenderName(senderName);
			 emailDto.setSenderEmail(senderEmail);

			 restClient.post(nbpUrl + "/mail/sendSingleMail", emailDto);
			 MessageRespDto messageRespDto = new MessageRespDto();
			messageRespDto.setStatus("T");
			return messageRespDto;
		} catch (Exception e) {
			logger.error("sendEmailForWebMember[" + email+ "] error!", e);
			return buildErrorMessageRespDto();
		}
	}
    
    private MessageRespDto sendEmailPasswordForStoreRegister(MemberDto memberDto) {
		String email = memberDto.getEmail();
		try {
			JSONObject contextJson = new JSONObject();
			contextJson.put("fullName",memberDto.getFullName());
			contextJson.put("cardNo", memberDto.getCardNo());
			contextJson.put("password",memberDto.getPassword());
			
			 com.jje.dto.nbp.EmailMessageDto emailDto = new com.jje.dto.nbp.EmailMessageDto();
			 emailDto.setContext(contextJson.toString());
			 emailDto.setEmailReceiver(email);
			 emailDto.setModule(TemplateModule.MEMBER);
			 emailDto.setPriority(1);
			 emailDto.setSendSource("门店会员激活邮件提醒");
			 emailDto.setSendType(SendType.PRODUCT);
			 emailDto.setSubject(quickRegisterEmailSubject);
			 emailDto.setTemplateNo("M_Stores_Member_Activate_Email");
			 emailDto.setSenderName(senderName);
			 emailDto.setSenderEmail(senderEmail);
			
			 restClient.post(nbpUrl + "/mail/sendSingleMail", emailDto);
			 MessageRespDto messageRespDto = new MessageRespDto();
			messageRespDto.setStatus("T");
			return messageRespDto;
		} catch (Exception e) {
			logger.error("sendPasswordByEmail[" + email + "] error!", e);
			return buildErrorMessageRespDto();
		}
	}
    
    public boolean simpleSendSMS(String mobile, Map<String, String> content,String templateNo,String source) {
        com.jje.dto.nbp.ShortMessageDto sendDto = new com.jje.dto.nbp.ShortMessageDto(source,mobile, TemplateModule.MEMBER);
        sendDto.setContext(objectToJson(content));
        sendDto.setTemplateNo(templateNo);
        try{
            restClient.post(nbpUrl+"/shortMessage/sendShortMessage/",sendDto);
        }catch (Exception e){
            logger.error("NBPHandler 发送短信失败{}", JaxbUtils.convertToXmlString(sendDto),e);
            return false;
        }
        return true;
    }
    
    public static String objectToJson(Object obj)  {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
            mapper.writeValue(gen, obj);
            gen.close();
            String json = writer.toString();
            writer.close();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
