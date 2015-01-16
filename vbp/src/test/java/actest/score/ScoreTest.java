package actest.score;

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
import com.jje.membercenter.xsd.MemberScoreRequest;
import com.jje.membercenter.xsd.MemberScoreResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class ScoreTest
{
	@Autowired
	CRMAccountApplyProxy crmAccountApplyProxy;
	
	@Ignore
	@Test
	public void queryScorePassTest() throws Exception
	{
		MemberScoreRequest request = new MemberScoreRequest();
		MemberScoreResponse response = new MemberScoreResponse();
		MemberScoreRequest.Head head = new MemberScoreRequest.Head();
		MemberScoreRequest.Body body = new MemberScoreRequest.Body();
		head.setReqtime(new SimpleDateFormat("MMddyyyy").format(new Date()));
//		head.setSystype("JJ001");
		head.setTranscode("20011");
		body.setMembid("1-194119");
		body.setStartdate(new SimpleDateFormat("MMddyyyy").format(new SimpleDateFormat("MMddyyyy").parse("01012007")));
//		body.setPartnername(new SimpleDateFormat("MMddyyyy").format(new SimpleDateFormat("MMddyyyy").parse("01012011")));
		body.setPartnername(null);
		body.setPointtype(null);
		request.setHead(head);
		request.setBody(body);
		response = crmAccountApplyProxy.queryScore(request);
		Assert.assertNotNull(response);
	}
	
	@Ignore
	@Test
	public void queryScoreFailTest() throws Exception
	{
		MemberScoreRequest request = new MemberScoreRequest();
		MemberScoreResponse response = new MemberScoreResponse();
		MemberScoreRequest.Head head = new MemberScoreRequest.Head();
		MemberScoreRequest.Body body = new MemberScoreRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMDDHHMMSS").format(new Date()));
		head.setSystype("JJ001");
		head.setTranscode("20011");
		body.setMembid("1-375932");
		body.setStartdate(new SimpleDateFormat("yyyyMMDDHHMMSS").format(new SimpleDateFormat("yyyyMMDD").parse("20070101")));
		body.setPartnername(new SimpleDateFormat("yyyyMMDDHHMMSS").format(new SimpleDateFormat("yyyyMMDD").parse("20110101")));
		body.setPartnername(null);
		body.setPointtype(null);
		request.setHead(head);
		request.setBody(body);
		response = crmAccountApplyProxy.queryScore(request);
		Assert.assertNotNull(response);
	}
}
