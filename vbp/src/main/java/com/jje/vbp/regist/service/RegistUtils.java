package com.jje.vbp.regist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.vbp.regist.domain.QuickRegistRepository;

@Component
public class RegistUtils {
	private final static String TEMP_CARD_NO_PREFIX = "J";
	private final static int TEMP_CARD_NUM_LENGTH = 11;
	
	@Autowired
	private QuickRegistRepository quickRegistRepository;
	
	public String generateTempCardNo(){
		return TEMP_CARD_NO_PREFIX+this.spellTempCardNum(this.getTempCardNum());
	}
	
	public String generateMcMemberCode(){
		return quickRegistRepository.getMcMemberCode();
	}
	
	private String spellTempCardNum(String cardNum){
		int len = cardNum.length();
		for(int i=0; i<TEMP_CARD_NUM_LENGTH-len; i++){
			cardNum = "0"+cardNum;
		}
		return cardNum;
	}
	
	private String getTempCardNum(){
		return quickRegistRepository.getTempCardNum();
	}
	
}
