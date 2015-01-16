/**
 * 
 */
package com.jje.membercenter.crm.impl;

import org.springframework.stereotype.Component;

import com.jje.membercenter.account.xsd.StarAccountValidationRequest;
import com.jje.membercenter.account.xsd.StarAccountValidationResponse;
import com.jje.membercenter.account.xsd.StarAccountActivationRequest;
import com.jje.membercenter.account.xsd.StarAccountActivationResponse;
import com.jje.membercenter.crm.AccountActivationProxy;

/**
 * @author SHENGLI_LU
 *
 */
@Component
public class AccountActivationProxyImpl extends CRMBaseProxy implements AccountActivationProxy  {

	public StarAccountActivationResponse activateStarMember(
			StarAccountActivationRequest request) throws Exception {
		return this.getResponse(request, StarAccountActivationResponse.class);
	}

	public StarAccountValidationResponse validateActivationStarMember(
			StarAccountValidationRequest request) throws Exception {
		return this.getResponse(request, StarAccountValidationResponse.class);
	}

}
