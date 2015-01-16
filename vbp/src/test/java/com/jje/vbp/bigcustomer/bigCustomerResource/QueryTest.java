package com.jje.vbp.bigcustomer.bigCustomerResource;

import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.common.utils.ResourceInvokeHandler.InvokeResult;
import com.jje.dto.membercenter.bigcustomer.BigCustomerDto;
import com.jje.dto.membercenter.bigcustomer.BigCustomersDto;
import com.jje.dto.membercenter.bigcustomer.CustomerAccountDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.bigcustomer.BigCustomerResource;

public class QueryTest extends DataPrepareFramework {
	@Autowired
	private ResourceInvokeHandler resourceInvokeHandler;
	@Test
	public void should_be_query_success_when_given_accountDto() {
		InvokeResult<BigCustomersDto> outPut = resourceInvokeHandler.doPost("bigCustomerResource", BigCustomerResource.class, "/bigCustomer/query", mockCustomerAccountDto() , BigCustomersDto.class);
		Assert.assertEquals(outPut.getStatus(), Status.OK);
		Assert.assertEquals(outPut.getOutput().getBigCustomers().size(), 7);
		System.out.println(outPut.getOutput().getBigCustomers().get(0));
	}
	private CustomerAccountDto mockCustomerAccountDto(){
		return new CustomerAccountDto("HKO","HKO");
	}
	
	private CustomerAccountDto mockInvaildCustomerAccountDto(){
		return new CustomerAccountDto("HKO","HKO1");
	}
	
	@Test
	public void should_be_query_fail_when_given_accountDto() {
		InvokeResult<BigCustomersDto> outPut = resourceInvokeHandler.doPost("bigCustomerResource", BigCustomerResource.class, "/bigCustomer/query", mockInvaildCustomerAccountDto() , BigCustomersDto.class);
		Assert.assertEquals(outPut.getStatus(), Status.OK);
		Assert.assertEquals(outPut.getOutput().getBigCustomers().size(), 0);
	}
	
	@Test
	public void should_be_query_success_when_given_findByCrmId() {
		InvokeResult<BigCustomerDto> outPut = resourceInvokeHandler.doGet("bigCustomerResource", BigCustomerResource.class, "/bigCustomer/findByCrmId/" + 100, BigCustomerDto.class);
		Assert.assertEquals(outPut.getStatus(), Status.OK);
		Assert.assertEquals(outPut.getOutput().getName(), "锦江员工");
	}
	
}
