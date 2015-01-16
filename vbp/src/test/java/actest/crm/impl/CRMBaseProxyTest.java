/**
 * 
 */
package actest.crm.impl;

import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.crm.impl.CRMAccountApplyProxyImpl;
import com.jje.membercenter.xsd.MemBaseQueryRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

public class CRMBaseProxyTest extends DataPrepareFramework{


	
	@Autowired
    CRMAccountApplyProxyImpl crmProxy;
	
	@Test
	public void testNewMemQueryRequest() throws Exception {
		MemBaseQueryRequest req = crmProxy.newMemQueryRequest();
		Assert.notNull(req);
	}

}
