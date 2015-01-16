package actest.member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.crm.CRMMembershipProxy;
import com.jje.membercenter.xsd.QueryTravellPreferenceRequest;
import com.jje.membercenter.xsd.QueryTravellPreferenceResponse;
import com.jje.membercenter.xsd.UpdateTravellPreferenceRequest.Body.Listofprefer.Preference;
import com.jje.membercenter.xsd.UpdateTravellPreferenceResponse;
import com.jje.membercenter.xsd.UpdateTravellPreferenceRequest;
//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class TravellPreferenceTest
{
	@Autowired
	CRMMembershipProxy crmMembershipProxy ;
	
	@Test
	public void getTravellPreferencePassTest() throws Exception
	{
		String memberId = "0024491";
		QueryTravellPreferenceRequest request = new QueryTravellPreferenceRequest();
		QueryTravellPreferenceResponse response = new QueryTravellPreferenceResponse();
		QueryTravellPreferenceRequest.Head head = new QueryTravellPreferenceRequest.Head();
		QueryTravellPreferenceRequest.Body body = new QueryTravellPreferenceRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("20005");
		body.setMembid(memberId);
		request.setBody(body);
		request.setHead(head);
		response = crmMembershipProxy.getTravellPreference(request);
		Assert.assertNotNull(response);
	}
	
	@Ignore
	//@Test
	public void getTravellPreferenceFailTest()
	{
		String memberId = "NB20111015444";
//		Response response = memberResource.getTravellPreference(memberId);
//		Assert.assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
	
	//@Ignore
	@Test
	public void updateTravellPreferencePassTest() throws Exception
	{
		String memberId = "1-157324";
		UpdateTravellPreferenceRequest request = new UpdateTravellPreferenceRequest();
		UpdateTravellPreferenceResponse response = new UpdateTravellPreferenceResponse();
		UpdateTravellPreferenceRequest.Head head = new UpdateTravellPreferenceRequest.Head();
		UpdateTravellPreferenceRequest.Body body = new UpdateTravellPreferenceRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setTranscode("30005");
		body.setMembid(memberId);
		UpdateTravellPreferenceRequest.Body.Listofprefer listofprefer = new UpdateTravellPreferenceRequest.Body.Listofprefer();
		UpdateTravellPreferenceRequest.Body.Listofprefer.Preference preference1 = new UpdateTravellPreferenceRequest.Body.Listofprefer.Preference();
		preference1.setPrefertype("梦想目的地");
		preference1.setHobby("家庭游;沙滩阳光;浪漫;购物;历史;探险;");
		listofprefer.getPreference().add(preference1);
		UpdateTravellPreferenceRequest.Body.Listofprefer.Preference preference2 = new UpdateTravellPreferenceRequest.Body.Listofprefer.Preference();
		preference2.setPrefertype("床品类型");
		preference2.setHobby("2张单人床");
		listofprefer.getPreference().add(preference2);
		UpdateTravellPreferenceRequest.Body.Listofprefer.Preference preference3 = new UpdateTravellPreferenceRequest.Body.Listofprefer.Preference();
		preference3.setPrefertype("房间");
		preference3.setHobby("无烟");
		listofprefer.getPreference().add(preference3);
		UpdateTravellPreferenceRequest.Body.Listofprefer.Preference preference4 = new UpdateTravellPreferenceRequest.Body.Listofprefer.Preference();
		preference4.setPrefertype("首选语言");
		preference4.setHobby("简体中文");
		listofprefer.getPreference().add(preference4);
		UpdateTravellPreferenceRequest.Body.Listofprefer.Preference preference5 = new UpdateTravellPreferenceRequest.Body.Listofprefer.Preference();
		preference5.setPrefertype("最爱座驾");
		preference5.setHobby("SUV;商务车;跑车;");
		listofprefer.getPreference().add(preference5);
		UpdateTravellPreferenceRequest.Body.Listofprefer.Preference preference6 = new UpdateTravellPreferenceRequest.Body.Listofprefer.Preference();
		preference6.setPrefertype("楼层");
		preference6.setHobby("高");
		listofprefer.getPreference().add(preference6);
		UpdateTravellPreferenceRequest.Body.Listofprefer.Preference preference7 = new UpdateTravellPreferenceRequest.Body.Listofprefer.Preference();
		preference7.setPrefertype("环境");
		preference7.setHobby("靠近电梯");
		listofprefer.getPreference().add(preference7);
		body.setListofprefer(listofprefer);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.updateTravellPreference(request);
		Assert.assertNotNull(response);
	}
}


















