package com.jje.membercenter.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.ResumeCardDto;
import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.domain.CRMResumeCardRepository;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;

@Component
@Transactional
public class CRMResumeCardRepositoryImpl implements CRMResumeCardRepository
{

	@Autowired
	CRMMembershipProxy crmMembershipProxy;
	
	public CRMResponseDto resumeCard(ResumeCardDto resumeCardDto) throws Exception
	{
		SynRightCardRequest request = new SynRightCardRequest();
		SynRightCardResponse response = new SynRightCardResponse();
		SynRightCardRequest.Head head = new SynRightCardRequest.Head();
		SynRightCardRequest.Body body = new SynRightCardRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("30010");
		body.setMembid(resumeCardDto.getMembid());
		body.setMembcdno(resumeCardDto.getMemcardno());
		body.setOptype(resumeCardDto.getAction());
		body.setBuydate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		if(StringUtils.isNotBlank(resumeCardDto.getChannel())){
			body.setChannel(resumeCardDto.getChannel());
		}
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.updateVIPCardInfo(request);
		CRMResponseDto dto = new CRMResponseDto();
		dto.setRetcode(response.getHead().getRetcode());
		dto.setRetmsg(response.getHead().getRetmsg());
		return dto;
	}

}
