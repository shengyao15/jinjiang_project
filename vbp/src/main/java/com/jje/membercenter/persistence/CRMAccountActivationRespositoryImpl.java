/**
 * 
 */
package com.jje.membercenter.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.AccountDto;
import com.jje.dto.membercenter.CRMActivationRespDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.membercenter.account.xsd.StarAccountActivationRequest;
import com.jje.membercenter.account.xsd.StarAccountActivationResponse;
import com.jje.membercenter.account.xsd.StarAccountValidationRequest;
import com.jje.membercenter.account.xsd.StarAccountValidationResponse;
import com.jje.membercenter.crm.AccountActivationProxy;
import com.jje.membercenter.domain.CRMAccountActivationRespository;

/**
 * @author SHENGLI_LU
 *
 */
@Component
public class CRMAccountActivationRespositoryImpl implements CRMAccountActivationRespository {

	private final static String REQTIME_FORMAT="yyyyMMddHHmmss";
	private final static String TRANS_CODE_ACTIVATION_VALIDATION = "20021";
	private final static String TRANS_CODE_ACTIVATION = "30021";
	private final static String CRM_SYS_TYPE = "JJ000";
	
	@Autowired
	AccountActivationProxy accountActivationProxy;
	
	public CRMActivationRespDto validateActivationStar(AccountDto accountDto)
			throws Exception {
		StarAccountValidationRequest.Head head = new StarAccountValidationRequest.Head();
		StarAccountValidationRequest.Body body = new StarAccountValidationRequest.Body();
		StarAccountValidationRequest request = new StarAccountValidationRequest();
		
		head.setReqtime(new SimpleDateFormat(REQTIME_FORMAT).format(new Date()));
		head.setTranscode(TRANS_CODE_ACTIVATION_VALIDATION);
		head.setSystype(CRM_SYS_TYPE);
		
		body.setCdno(accountDto.getCdno());
		body.setCdtype(accountDto.getCdtype());		
		body.setJjmemcdid(accountDto.getCardNo());
		body.setName(accountDto.getName());
		
		request.setHead(head);
		request.setBody(body);
		
		StarAccountValidationResponse response= accountActivationProxy.validateActivationStarMember(request);
		CRMActivationRespDto crmActivationRespDto = new CRMActivationRespDto();
		crmActivationRespDto.setMembrowid(response.getBody().getMembrowid());
		crmActivationRespDto.setRetcode(response.getBody().getRecode().toString());
		crmActivationRespDto.setRetmsg(response.getBody().getRemsg());
		crmActivationRespDto.setEmail(response.getBody().getEmail());
		crmActivationRespDto.setMobile(response.getBody().getMobile());
		return crmActivationRespDto;
	}

	public CRMResponseDto activateStar(AccountDto accountDto)
			throws Exception {
		StarAccountActivationRequest.Head head = new StarAccountActivationRequest.Head();
		StarAccountActivationRequest.Body body = new StarAccountActivationRequest.Body();
		StarAccountActivationRequest request = new StarAccountActivationRequest();
		
		head.setReqtime(new SimpleDateFormat(REQTIME_FORMAT).format(new Date()));
		head.setTranscode(TRANS_CODE_ACTIVATION);
		head.setSystype(CRM_SYS_TYPE);
		
		body.setMembrowid(accountDto.getMembrowid());
		body.setPasswd(accountDto.getPasswd());
		body.setPwdquestion(accountDto.getPwdquestion());
		body.setPwdanswer(accountDto.getPwdanswer());
		body.setEmail(accountDto.getEmail());
		body.setMobile(accountDto.getMobile());
		
		request.setHead(head);
		request.setBody(body);
		
		StarAccountActivationResponse response= accountActivationProxy.activateStarMember(request);
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setMembid(response.getBody().getMembid());
		crmResponseDto.setRetcode(response.getBody().getRecode().toString());
		crmResponseDto.setRetmsg(response.getBody().getRemsg());
		return crmResponseDto;
	}

}
