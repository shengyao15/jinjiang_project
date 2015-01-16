
package com.jje.membercenter.callcenter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.membercenter.service.MemberService;


@javax.jws.WebService(
    serviceName = "JJBE_spcQuery_spcFor_spcCC",
    portName = "GetMcMemberCodeByPhoneService",
    targetNamespace = "http://siebel.com/asi/",
    //wsdlLocation = "crmGetUserId.wsdl",
    endpointInterface = "com.jje.membercenter.callcenter.GetMcMemberCodeByPhoneService")
    
public class GetMcMemberCodeByPhoneServiceImpl implements GetMcMemberCodeByPhoneService {

	private static final Logger LOG = LoggerFactory.getLogger(GetMcMemberCodeByPhoneServiceImpl.class);

	@Autowired
	private MemberService memberService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.siebel.asi.Default#queryForCC(String strCell )*
	 */
	public String queryForCC(String strCell) {
		LOG.info("Executing operation queryForCC");
		System.out.println(strCell);
		try {
			String _return = memberService.getMcMemberCodeByPhone(strCell);
			if (StringUtils.isEmpty(_return)) {
				return "1-1";
			}
			return _return;
		} catch (Exception ex) {
			LOG.error("queryForCC({}) error!", strCell, ex);
			throw new RuntimeException(ex);
		}
	}

}
