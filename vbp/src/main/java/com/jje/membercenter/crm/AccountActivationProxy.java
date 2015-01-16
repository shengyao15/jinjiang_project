/**
 * 
 */
package com.jje.membercenter.crm;

import com.jje.membercenter.account.xsd.StarAccountValidationRequest;
import com.jje.membercenter.account.xsd.StarAccountValidationResponse;
import com.jje.membercenter.account.xsd.StarAccountActivationRequest;
import com.jje.membercenter.account.xsd.StarAccountActivationResponse;

/**
 * @author SHENGLI_LU
 *
 */
public interface AccountActivationProxy {

	StarAccountActivationResponse  activateStarMember(StarAccountActivationRequest request) throws Exception;
	
	StarAccountValidationResponse validateActivationStarMember(StarAccountValidationRequest request) throws Exception;
}
