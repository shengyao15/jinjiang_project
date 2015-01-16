package com.jje.vbp.template;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.nbp.TemplateDto;
import com.jje.dto.nbp.TemplateType;
import com.jje.membercenter.DataPrepareFramework;

public class TemplateResourceTest extends DataPrepareFramework {

	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;

	
	@Autowired
	private TemplateResource resource;

                                                                
	@Test
	public void should_sync_success() {
		TemplateDto tempateDto  = new TemplateDto();
		tempateDto.setContent("abc");
		tempateDto.setEndDate(new Date());
		tempateDto.setIsBill(true);
		tempateDto.setSendType(com.jje.dto.nbp.SendType.PRODUCT);
		tempateDto.setSerialNumber("111");
		tempateDto.setStartDate(new Date());
		tempateDto.setSubject("abc");
		tempateDto.setTemplateNo("testTemplateSync");
		tempateDto.setTemplateType(TemplateType.MAIL);

		try {
			resource.syncTemplateToCrm(tempateDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
