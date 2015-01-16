package actest.member;


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

import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryRequest;
import com.jje.membercenter.xsd.MemberPrivilegeHistoryResponse;
import com.jje.membercenter.xsd.MemberPrivilegeRequest;
import com.jje.membercenter.xsd.MemberPrivilegeResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class MemberPrivilegeTest
{
	@Autowired
	CRMMembershipProxy crmMembershipProxy;
	
	@Ignore
	@Test
	public void listPrivilegePassTest() throws Exception
	{
		String memberId = "1-18120148";
		MemberPrivilegeRequest request = new MemberPrivilegeRequest();
		MemberPrivilegeResponse response = new MemberPrivilegeResponse();
		MemberPrivilegeRequest.Head head = new MemberPrivilegeRequest.Head();
		MemberPrivilegeRequest.Body body = new MemberPrivilegeRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("20009");
		body.setMembid(memberId);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.getPrivCardInfo(request);
		MemberPrivilegeHistoryRequest req = new MemberPrivilegeHistoryRequest();
		MemberPrivilegeHistoryResponse res = new MemberPrivilegeHistoryResponse();
		MemberPrivilegeHistoryRequest.Head head2 = new MemberPrivilegeHistoryRequest.Head();
		MemberPrivilegeHistoryRequest.Body body2 = new MemberPrivilegeHistoryRequest.Body();
		head2.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head2.setTranscode("20015");
		body2.setMembid(memberId);
		req.setHead(head2);
		req.setBody(body2);
		res = crmMembershipProxy.getPrivCardHistory(req);
		Assert.assertNotNull(response);
	}
	
	@Ignore
	//@Test
	public void listPrivilegeFailTest() throws Exception
	{
		String memberId = "1-18120148";
		MemberPrivilegeRequest request = new MemberPrivilegeRequest();
		MemberPrivilegeResponse response = new MemberPrivilegeResponse();
		MemberPrivilegeRequest.Head head = new MemberPrivilegeRequest.Head();
		MemberPrivilegeRequest.Body body = new MemberPrivilegeRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("20009");
		body.setMembid(memberId);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.getPrivCardInfo(request);
		Assert.assertNotNull(response);
	}
}
