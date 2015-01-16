package com.jje.vbp.regist.service;

import com.jje.membercenter.DataPrepareFramework;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class RegistUtilsTest extends DataPrepareFramework{
	
	@Autowired
	RegistUtils registUtils;
	
	@Test
	public void testGenerateTempCardNo(){
		String cardNo1 = registUtils.generateTempCardNo();
        String cardNo2 = registUtils.generateTempCardNo();
        Assert.assertFalse(cardNo1.equals(cardNo2));
	}
}
