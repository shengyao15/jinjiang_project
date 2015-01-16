package com.jje.vbp.handler.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.jje.common.utils.RestClient;
import com.jje.dto.mms.mmsmanage.EmailMessageDto;
import com.jje.dto.mms.mmsmanage.MessageRespDto;

@Component
public class MbpProxy {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "${mbp.mms.url}")
	private String mmsService;
	
	@Value(value = "${nbp.url}")
	private String nbpUrl;
	@Autowired
	private RestClient restClient;
	
	private static final String MMS_AUTHOR_USER_NAME = "jinjiangmms";
	private static final String MMS_AUTHOR_PASSWORD = "123456";

	public MessageRespDto sendEmailMessage(EmailMessageDto emailMessageDto) {
		logger.info("do sendEmailMessage, params: emailMessageDto {}",emailMessageDto);
		this.setAuthInfo(emailMessageDto);
		return restClient.post(mmsService + "/emailmsg", emailMessageDto,MessageRespDto.class);
	}
	
	@Async
	public void sendEmailMessageNew(com.jje.dto.nbp.EmailMessageDto emailMessageDto) {
		logger.info("do sendEmailMessage, params: emailMessageDto {}",emailMessageDto);
		restClient.post(nbpUrl + "/mail/sendSingleMail", emailMessageDto);
	}

	private void setAuthInfo(EmailMessageDto emailMessageDto) {
		emailMessageDto.setUserName(MMS_AUTHOR_USER_NAME);
		emailMessageDto.setPassword(MMS_AUTHOR_PASSWORD);
	}
	
}
