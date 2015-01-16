package com.jje.vbp.bigcustomer.persistence;

import com.jje.vbp.bigcustomer.domain.BigCustomer;
import com.jje.vbp.bigcustomer.domain.CustomerAccount;

import java.util.List;

public interface CustomerMapper {

	public List<BigCustomer> query(CustomerAccount customerAccount);

    public List<BigCustomer> queryBigCustomerByAccount(CustomerAccount customerAccount);

	public BigCustomer findByMcCode(String mcCode);

	public List<BigCustomer> queryAll();

    public void update(BigCustomer bigCustomer);

    public BigCustomer findById(String id);

	public BigCustomer findByCrmId(String crmId);
}
