package com.jje.vbp.handler.proxy;

import com.jje.common.utils.RestClient;
import com.jje.dto.mms.mmsmanage.MessageRespDto;
import com.jje.dto.nbp.ShortMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NbpProxy {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${nbp.url}")
    private String nbpUrl;

    @Autowired
    private RestClient restClient;

    @Async
    public MessageRespDto sendShortMessage(ShortMessageDto shortMessageDto) {
        logger.info("do sendShortMessage, params: shortMessageDto {}", shortMessageDto);
        try{
            restClient.post(nbpUrl+"/shortMessage/sendShortMessage/",shortMessageDto);
        }catch (Exception e){
            logger.error("NbpProxy 发送短信失败{}",e);
            return new MessageRespDto("F",null);
        }
        return new MessageRespDto("T",null);
    }

}
