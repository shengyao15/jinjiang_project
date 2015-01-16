package com.jje.vbp.handler.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.dto.ebp.EventDto;
import com.jje.ebp.client.EventClient;

@Component
public class EbpProxy {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value(value = "${ebp.url}")
	private String ebpUrl;

	public void sendEvent(EventDto eventDto) {
		EventClient eventClient = new EventClient(ebpUrl);
		logger.info(String.format("SendEvent:Code:%s,Channel:%s,Module:%s,mcMemeberCode:%s", eventDto.getEventCode(), eventDto.getEventChannel(),eventDto.getEventModule(),eventDto.getEventMcMemberCode()));
		try {
			eventClient.trigger(eventDto);
		} catch (Exception e) {
			logger.error("send ebp event error:{}",e);
		}
	}
	

}
