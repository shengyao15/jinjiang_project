package com.jje.membercenter.template;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.membercenter.DataPrepareFramework;


public class URLUtilsTest extends DataPrepareFramework{
	
	@Autowired 
	private URLUtils urlUtils;

	@Test
	public void should_succes_when_buildAutoLoginUrl() throws Exception {
		String backUrl = urlUtils.buildAutoLoginUrl("10000", "278250658@qq.com", "www.jinjing.com");
		System.out.println(backUrl);
		Assert.assertNotNull(backUrl);
	}

    @Ignore
	public void should_succes_when_decodeAutoLoginUrl() throws Exception {
		String mc = urlUtils.decodeAutoLoginUrl("01a7d5c8-4481-47d5-83f8-62f6936eca5e-oWm27cJKa%2FVesh3ftU9%2FzR4DzsWalihrYDkqDZIrkHc%3D");
		Assert.assertEquals(mc, "10000");
	}
}