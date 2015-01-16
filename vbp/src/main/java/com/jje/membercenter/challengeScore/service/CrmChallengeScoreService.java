package com.jje.membercenter.challengeScore.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jje.common.bam.JaxbUtils;
import com.jje.common.utils.DateUtils;
import com.jje.membercenter.crm.CRMPromotionProxy;
import com.jje.membercenter.domain.OpRecordLogDomain;
import com.jje.membercenter.domain.OpRecordLogDomain.EnumOpType;
import com.jje.membercenter.persistence.OpRecordLogRepository;
import com.jje.membercenter.remote.crm.datagram.request.CrmPromotionRequest;
import com.jje.membercenter.remote.crm.datagram.response.CrmPromotionResponse;

@Service
public class CrmChallengeScoreService {
	private static final Logger logger = LoggerFactory.getLogger(CrmChallengeScoreService.class);
    
	@Autowired
	private CRMPromotionProxy crmPromotionProxy;
    @Autowired
    OpRecordLogRepository opRecordLogRepository;
	
	public String signup(String memberId, String channel, String activityName) throws Exception {
		CrmPromotionRequest request = new CrmPromotionRequest();
		request.getBody().setActivity(activityName);
		request.getBody().setAttchanel(channel);
		request.getBody().setAttdate(DateUtils.formatDate(new Date(), "MMddyyyy"));
		request.getBody().setMembid(memberId);
		try {
			CrmPromotionResponse response = crmPromotionProxy.challengeScoreSignup(request);
			if("00002".equals(response.getHead().getRetcode())) {
				logger.error("crm sign up fail request:{}", JaxbUtils.convertToXmlString(request));
				logger.error("crm sign up fail response:{}", JaxbUtils.convertToXmlString(response));
				opRecordLogRepository.insert(new OpRecordLogDomain(
						EnumOpType.CRM_SIGNUP,
						"crm signup fail[trancode:50001]",
						"request: " + JaxbUtils.convertToXmlString(request) + "\n" +
						"response: " + JaxbUtils.convertToXmlString(response)));
			}
			return response.getBody().getAttstatus();
		} catch(Exception ex) {
			return "fail";
		}
	}
}
