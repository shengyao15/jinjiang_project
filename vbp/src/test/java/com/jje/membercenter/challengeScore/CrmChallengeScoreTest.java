package com.jje.membercenter.challengeScore;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.bam.JaxbUtils;
import com.jje.common.utils.DateUtils;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.crm.CRMPromotionProxy;
import com.jje.membercenter.remote.crm.datagram.request.CrmPromotionRequest;
import com.jje.membercenter.remote.crm.datagram.response.CrmPromotionResponse;

public class CrmChallengeScoreTest extends DataPrepareFramework{
	
	@Autowired
	CRMPromotionProxy proxy;
	
	@Test
	public void test() {
		
	}
	
	@Test
	public void signupTest() throws Exception {
		CrmPromotionRequest request = new CrmPromotionRequest();
		request.setBody(getRequestBody());
		CrmPromotionResponse response = proxy.challengeScoreSignup(request);
		System.out.println(JaxbUtils.convertToXmlString(response));
		Assert.assertEquals(response.getBody().getAttstatus(), "fail");
	}
	
	private CrmPromotionRequest.RequestBody getRequestBody() {
		CrmPromotionRequest.RequestBody body =  new CrmPromotionRequest.RequestBody();
		body.setActivity("JFDTZ");
		body.setAttchanel("Webistes");
		body.setAttdate(DateUtils.formatDate(new Date(), "MMddyyyy"));
		body.setMembid("1-8Q29W1");
		return body;
	}
}
