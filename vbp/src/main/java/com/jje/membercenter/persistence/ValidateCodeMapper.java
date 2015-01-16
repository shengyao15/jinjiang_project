package com.jje.membercenter.persistence;

import com.jje.membercenter.domain.ValidateCode;

public interface ValidateCodeMapper {
	
	void addCode(ValidateCode validateCode);
	
	ValidateCode getLastCodeByReceiver(String receiver);
	
	int countTodayCodes(String receiver);
}
