package actest.register;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.crm.CRMAccountApplyProxy;
import com.jje.membercenter.xsd.RegisterScoreHistoryRequest;
import com.jje.membercenter.xsd.RegisterScoreHistoryResponse;
import com.jje.membercenter.xsd.RegisterScoreRequest;
import com.jje.membercenter.xsd.RegisterScoreResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class RegisterScoreHistoryTest
{
	@Autowired
	CRMAccountApplyProxy crmAccountApplyProxy;
	
	//@Ignore
	@Test
	public void addCreditsRetorPassTest() throws Exception
	{
		RegisterScoreRequest request = new RegisterScoreRequest();
		RegisterScoreResponse response = new RegisterScoreResponse();
		RegisterScoreRequest.Body body = new RegisterScoreRequest.Body();
		RegisterScoreRequest.Head head = new RegisterScoreRequest.Head();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setSystype("JJ001");
		head.setTranscode(BigInteger.valueOf(30012));
		body.setMembid("1-18038621");
		body.setSrtype("积分补登");
		body.setSrdetail("JJ001|20111110150936|锦江酒店|上海|上海|B0901|20111007000000|20111118000000|3000|SN100002|补登积分：0|SN100002|null|");
		request.setHead(head);
		request.setBody(body);
		response = crmAccountApplyProxy.applyRegisterScore(request);
		Assert.assertEquals(BigInteger.valueOf(2l), response.getBody().getRecode());
	}

	//@Ignore
	@Test
	public void addCreditsRetorFailTest() throws Exception
	{
		RegisterScoreRequest request = new RegisterScoreRequest();
		RegisterScoreResponse response = new RegisterScoreResponse();
		RegisterScoreRequest.Body body = new RegisterScoreRequest.Body();
		RegisterScoreRequest.Head head = new RegisterScoreRequest.Head();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setSystype("JJ001");
		head.setTranscode(BigInteger.valueOf(30012));
		body.setMembid("1-18038621");
		body.setSrtype(new String("积分补登".getBytes("GBK"),"utf-8"));
		request.setHead(head);
		request.setBody(body);
		response = crmAccountApplyProxy.applyRegisterScore(request);
		Assert.assertEquals(BigInteger.valueOf(2l), response.getBody().getRecode());
	}
	
	//@Ignore
	@Test
	public void listRegisterScoreHistoryPassTest() throws Exception
	{
		RegisterScoreHistoryRequest request = new RegisterScoreHistoryRequest();
		RegisterScoreHistoryResponse response = new RegisterScoreHistoryResponse();
		RegisterScoreHistoryRequest.Head head = new RegisterScoreHistoryRequest.Head();
		RegisterScoreHistoryRequest.Body body = new RegisterScoreHistoryRequest.Body();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setSystype("JJ001");
		head.setTranscode(BigInteger.valueOf(20012));
		body.setMembid("0024491");
		body.setSrtype("Claims");
		request.setHead(head);
		request.setBody(body);
		response = crmAccountApplyProxy.listRegisterScoreHistory(request);
		Assert.assertNotNull(response);
	}
	
	//@Ignore
	@Test
	public void listRegisterScoreHistoryFailTest() throws Exception
	{
		RegisterScoreHistoryRequest request = new RegisterScoreHistoryRequest();
		RegisterScoreHistoryResponse response = new RegisterScoreHistoryResponse();
		RegisterScoreHistoryRequest.Head head = new RegisterScoreHistoryRequest.Head();
		RegisterScoreHistoryRequest.Body body = new RegisterScoreHistoryRequest.Body();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setSystype("JJ001");
		head.setTranscode(BigInteger.valueOf(20012));
		body.setMembid("1-375932");
		request.setHead(head);
		request.setBody(body);
		response = crmAccountApplyProxy.listRegisterScoreHistory(request);
		Assert.assertNotNull(response);
	}
}
