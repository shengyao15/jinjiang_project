package com.jje.vbp.bigcustomer.repository;

import com.jje.vbp.bigcustomer.domain.BigCustomer;
import com.jje.vbp.bigcustomer.domain.CustomerAccount;
import com.jje.vbp.bigcustomer.persistence.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerAccountRepository {

	@Autowired
	private CustomerMapper customerMapper;

	public List<BigCustomer> query(CustomerAccount customerAccount) {
		return customerMapper.query(customerAccount);
	}
	
	
	public BigCustomer findByMcCode(String mcMemberCode) {
		return customerMapper.findByMcCode(mcMemberCode);
	}


	public List<BigCustomer> queryAll() {
		return customerMapper.queryAll();
	}

    public void update(BigCustomer bigCustomer) {
        customerMapper.update(bigCustomer);
    }

    public BigCustomer findById(String id) {
        return customerMapper.findById(id);
    }
    
    public BigCustomer findByCrmId(String crmId) {
        return customerMapper.findByCrmId(crmId);
    }

    public List<BigCustomer> queryBigCustomerByAccount(CustomerAccount customerAccount) {
        return customerMapper.queryBigCustomerByAccount(customerAccount);
    }
}
