package actest.crm.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.membercenter.account.xsd.StarAccountValidationRequest;
import com.jje.membercenter.account.xsd.StarAccountActivationRequest;
import com.jje.membercenter.crm.AccountActivationProxy;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Ignore
public class AccountActivationProxyImplTest {

	@Autowired
	AccountActivationProxy accountActivationProxy;
	
	@Test
	public void test() throws Exception {
		StarAccountValidationRequest request = new StarAccountValidationRequest();
		StarAccountValidationRequest.Head head = new StarAccountValidationRequest.Head();
		request.setHead(head);
		request.getHead().setReqtime("20021");
		request.getHead().setSystype("");
		request.getHead().setTranscode("");
		StarAccountValidationRequest.Body body = new StarAccountValidationRequest.Body();
		request.setBody(body);
		request.getBody().setCdno("");
		request.getBody().setCdtype("");
		request.getBody().setJjmemcdid("");
		request.getBody().setName("");
		
		//accountActivationProxy.activateMember(request);
		
		StarAccountActivationRequest request1 = new StarAccountActivationRequest();
		
		StarAccountActivationRequest.Head head1= new StarAccountActivationRequest.Head();
		request1.setHead(head1);
		
		request1.getHead().setReqtime("30021");
		request1.getHead().setSystype("");
		request1.getHead().setTranscode("");
		StarAccountActivationRequest.Body body1 = new StarAccountActivationRequest.Body();
		request1.setBody(body1);
		
		request1.getBody().setEmail("");
		request1.getBody().setMembrowid("");
		request1.getBody().setMobile("");
		request1.getBody().setPasswd("");
		request1.getBody().setPwdanswer("");
		request1.getBody().setPwdquestion("");
		//accountActivationProxy.validateActivationMember(request1);		
	}

}
