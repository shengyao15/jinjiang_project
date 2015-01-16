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
import com.jje.membercenter.xsd.QueryContactRequest;
import com.jje.membercenter.xsd.QueryContactResponse;
import com.jje.membercenter.xsd.UpdateContactRequest;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactemail;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactemail.Contactemail;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactphone;
import com.jje.membercenter.xsd.UpdateContactRequest.Body.Listofcontact.Contact.Listofcontactphone.Contactphone;
import com.jje.membercenter.xsd.UpdateContactResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class ContactTest
{
	@Autowired
	CRMMembershipProxy crmMembershipProxy;
	
	@Ignore
	@Test
	public void queryContactTest() throws Exception
	{
		QueryContactRequest request = new QueryContactRequest();
		QueryContactResponse response = new QueryContactResponse();
		QueryContactRequest.Head head = new QueryContactRequest.Head();
		QueryContactRequest.Body body = new QueryContactRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("20014");
		body.setMembid("1-18038621");
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.queryContact(request);
		Assert.assertEquals("00001", response.getHead().getRetcode());
	}
	
	@Ignore
	//@Test
	public void updateContactTest() throws Exception
	{
		UpdateContactRequest request = new UpdateContactRequest();
		UpdateContactResponse response = new UpdateContactResponse();
		UpdateContactRequest.Head head = new UpdateContactRequest.Head();
		UpdateContactRequest.Body body = new UpdateContactRequest.Body();
		head.setReqtime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		head.setSystype("JJ000");
		head.setTranscode("30014");
		body.setMembid("1-18038621");
		Listofcontact listofcontact = new Listofcontact();
		Contact contact = new Contact();
		contact.setFirstname("Lee");
		contact.setLastname("Lenvon");
		
			Listofcontactemail email = new Listofcontactemail();
			Contactemail mail = new Contactemail();
			mail.setEmail("1@163.com");
			mail.setStartdate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2010")));
			mail.setEnddate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2011")));
			mail.setUsetype("电子邮件");
			mail.setStatus("有效");
			email.getContactemail().add(mail);
			contact.setListofcontactemail(email);
			
//			Listofcontactfax fax = new Listofcontactfax();
//			Contactfax f = new Contactfax();
//			f.setFax("010-12345678");
//			f.setStartdate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2010")));
//			f.setEnddate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2011")));
//			f.setUsetype("传真");
//			f.setStatus("有效");
//			fax.getContactfax().add(f);
//			contact.setListofcontactfax(fax);
			
//			Listofcontactim tim = new Listofcontactim();
//			Contactim t = new Contactim();
//			t.setIm("1515899199");
//			t.setStartdate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2010")));
//			t.setEnddate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2011")));
//			t.setUsetype("QQ账号");
//			t.setStatus("有效");
//			tim.getContactim().add(t);
//			contact.setListofcontactim(tim);
			
			Listofcontactphone phone = new Listofcontactphone();
			Contactphone p = new Contactphone();
			p.setPhone("15971219312");
			p.setStartdate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2010")));
			p.setEnddate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2011")));
			p.setUsetype("手机");
			p.setStatus("有效");
			phone.getContactphone().add(p);
			contact.setListofcontactphone(phone);
			
//			Listofcontactsns sns = new Listofcontactsns();
//			Contactsns s = new Contactsns();
//			s.setSns("adfdfa");
//			s.setStartdate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2010")));
//			s.setEnddate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse("11/01/2010")));
//			s.setUsetype("新浪微博账号");
//			s.setStatus("无效");
//			sns.getContactsns().add(s);
//			contact.setListofcontactsns(sns);
			
		listofcontact.setContact(contact);
		body.setListofcontact(listofcontact);
		request.setHead(head);
		request.setBody(body);
		response = crmMembershipProxy.updateContact(request);
		Assert.assertEquals("00001", response.getHead().getRetcode());
	}
}
