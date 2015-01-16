package actest.card;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.crm.CRMUpdateRightCardProxy;
import com.jje.membercenter.xsd.QueryRightCardInfoRequest;
import com.jje.membercenter.xsd.QueryRightCardInfoResponse;
import com.jje.membercenter.xsd.SynRightCardRequest;
import com.jje.membercenter.xsd.SynRightCardResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class SynRightCardTest
{
	@Autowired
	CRMMembershipProxy crmMembershipProxy ;
	
	@Autowired
	CRMUpdateRightCardProxy crmUpdateRightCardProxy;
	
	@Test
	public void updateVIPCardInfoTest() throws Exception
	{
		SynRightCardRequest request = new SynRightCardRequest();
		SynRightCardResponse response = new SynRightCardResponse();
		SynRightCardRequest.Head head = new SynRightCardRequest.Head();
		SynRightCardRequest.Body body = new SynRightCardRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("30010");
		body.setMembid("1-20981408");
		body.setMembcdno("6600002932");
		body.setOptype("Upgrade J2");
		body.setBuydate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		body.setChannel("abd");
		body.setVipstore("2000");
		body.setSalesperson("salas");
		body.setSalesmount("2000");
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.updateVIPCardInfo(request);
		Assert.assertEquals("00002", response.getHead().getRetcode());
	}
	
	@Test
	public void updateCardInfoTest() throws Exception
	{
		SynRightCardRequest request = new SynRightCardRequest();
		SynRightCardResponse response = new SynRightCardResponse();
		SynRightCardRequest.Head head = new SynRightCardRequest.Head();
		SynRightCardRequest.Body body = new SynRightCardRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("30010");
		body.setMembid("1-157324");
		body.setMembcdno("6600000330");
		body.setOptype("Recharge J");
		body.setBuydate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
//		body.setChannel(null);
//		body.setVipstore(null);
//		body.setSalesperson(null);
//		body.setSalesmount(null);
//		body.setDesc(null);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.updateVIPCardInfo(request);
		Assert.assertEquals("00002", response.getHead().getRetcode());
	}
	
	@Test
	public void buyVIPCardInfoTest() throws Exception
	{
		SynRightCardRequest request = new SynRightCardRequest();
		SynRightCardResponse response = new SynRightCardResponse();
		SynRightCardRequest.Head head = new SynRightCardRequest.Head();
		SynRightCardRequest.Body body = new SynRightCardRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("30010");
		body.setMembid("0024697");
		body.setMembcdno("5600000051");
		body.setOptype("购买享卡");
		body.setBuydate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
		body.setChannel("abd");
		body.setVipstore("2000");
		body.setSalesperson("salas");
		body.setSalesmount("2000");
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.updateVIPCardInfo(request);
		Assert.assertEquals("00002", response.getHead().getRetcode());
	}
	
	@Test
	public void queryVIPCardInfoTest() throws Exception
	{
		QueryRightCardInfoRequest request = new QueryRightCardInfoRequest();
		QueryRightCardInfoResponse response = new QueryRightCardInfoResponse();
		QueryRightCardInfoRequest.Head head = new QueryRightCardInfoRequest.Head();
		QueryRightCardInfoRequest.Body body = new QueryRightCardInfoRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("20010");
		body.setMembid("0024491");
		request.setHead(head);
		request.setBody(body);
		response = crmUpdateRightCardProxy.queryVIPCardInfo(request);
		Assert.assertNotNull(response);
	}
	
}
