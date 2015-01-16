package com.jje.membercenter.template;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.DateUtils;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.WebMember;

public class TemplateUtilsTest extends DataPrepareFramework{
	
	@Autowired 
	private TemplateUtils templateUtils;

	@Test
	public void should_succes_when_merge() {
		String content = templateUtils.merge("vbp_test_for_juint.vm", mockInputMap());
		Assert.assertEquals(content, "jack.shi,http://192.168.2.51:8005/imgdir/cms/mms/email/register/2012-01-01");
	}
	
	private Map<String, Object> mockInputMap() {
		Map<String,Object> msgArgs = new HashMap<String,Object>();
		msgArgs.put("name", "jack.shi");
		msgArgs.put("testDate", DateUtils.parseDate("2012-1-1"));
		return msgArgs;
	}
	
	@Test
	@Ignore
	public void should_succee_when_real_merge() {
		String content = templateUtils.merge("vbp_email_quick_register_succeed.vm", mockRealInputMap());
		System.out.println(content);
	}
	
	private Map<String, Object> mockRealInputMap() {
		Map<String,Object> msgArgs = new HashMap<String,Object>();
		WebMember member = new WebMember();
		member.setEmail("278250658@qq.com");
		member.setMcMemberCode("100010");
		msgArgs.put("webMember", member);
		return msgArgs;
	}
}