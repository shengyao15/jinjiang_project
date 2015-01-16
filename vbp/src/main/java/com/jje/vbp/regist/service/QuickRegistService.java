package com.jje.vbp.regist.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.WebMemberRegisterReturnDto;
import com.jje.membercenter.domain.WebMember;
import com.jje.vbp.regist.domain.QuickRegistRepository;
import com.jje.vbp.regist.domain.WebMemberInfo;
import com.jje.vbp.regist.domain.WebMemberVerify;

@Component
public class QuickRegistService {
	
	@Autowired
	private QuickRegistRepository quickRegistRepository;
	
	@Autowired
	private RegistUtils registUtils;
	
	
	public WebMemberRegisterReturnDto regist(WebMemberInfo webMemberInfo){
		WebMemberRegisterReturnDto returnDto = new WebMemberRegisterReturnDto();
		
		webMemberInfo.setTempCardNo(registUtils.generateTempCardNo());
		String mcMemberCode = registUtils.generateMcMemberCode();
		webMemberInfo.setMcMemberCode(mcMemberCode);
		
		this.save(webMemberInfo);
		
		returnDto.success(mcMemberCode);
		return returnDto;
		
	}
	
	private void save(WebMemberInfo webMemberInfo){
		this.saveWebMemberInfo(webMemberInfo);
		this.saveWebMemberVerifys(webMemberInfo);
	}
	
	private void saveWebMemberInfo(WebMemberInfo webMemberInfo){
		quickRegistRepository.saveWebMemberInfo(webMemberInfo);
	}
	
	private void saveWebMemberVerifys(WebMemberInfo webMemberInfo) {
		List<WebMemberVerify> verifys = webMemberInfo.createVerifys();
		for(WebMemberVerify webMemberVerify : verifys){
			quickRegistRepository.saveWebMemberVerfy(webMemberVerify);
		}
		
	}
}
