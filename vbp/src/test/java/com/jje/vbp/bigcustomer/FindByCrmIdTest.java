package com.jje.vbp.bigcustomer;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.bigcustomer.domain.BigCustomer;
import com.jje.vbp.bigcustomer.repository.CustomerAccountRepository;

public class FindByCrmIdTest extends DataPrepareFramework{

	@Autowired
	private CustomerAccountRepository customerAccountRepository; 
	
	@Test
	public void success(){
		BigCustomer bigCustomer = customerAccountRepository.findByCrmId("10086");
		Assert.assertEquals("10086", bigCustomer.getCrmId());
		Assert.assertEquals("JJEMP", bigCustomer.getChannel());
	}
}
