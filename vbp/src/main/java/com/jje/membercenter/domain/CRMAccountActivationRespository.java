/**
 * 
 */
package com.jje.membercenter.domain;

import com.jje.dto.membercenter.AccountDto;
import com.jje.dto.membercenter.CRMActivationRespDto;
import com.jje.dto.membercenter.CRMResponseDto;

/**
 * @author SHENGLI_LU
 *
 */
public interface CRMAccountActivationRespository {

	CRMActivationRespDto validateActivationStar(AccountDto accountDto) throws Exception;
	
	CRMResponseDto activateStar(AccountDto accountDto) throws Exception;
}
