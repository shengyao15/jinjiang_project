package com.jje.membercenter.register;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.MemberMemCardDto;
import com.jje.dto.membercenter.MemberVerfyDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMReqDto;
import com.jje.dto.membercenter.forcrm.MemberForCRMRespDto;
import com.jje.membercenter.forcrm.MemberCenterInterface;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class RegisterCrmCallBackTest {
	
	@Autowired
	private MemberCenterInterface center;
	
	@Test
	public void test_storeMember_success() {
		Response res = center.storeMember(mockMemberForCRMReqDto());
		Assert.assertTrue(res.getStatus() == Status.OK.getStatusCode());
		MemberForCRMRespDto crmRes = (MemberForCRMRespDto) res.getEntity();
		Assert.assertTrue(crmRes.getMemberId() != null);
		Assert.assertTrue(crmRes.getStatus().equals("数据插入成功"));
	}
	
	private MemberForCRMReqDto mockMemberForCRMReqDto() {
		MemberForCRMReqDto req = new MemberForCRMReqDto();
		req.setAuthorizationUserName("jinjiangvbp");
		req.setAuthorizationPassword("123456");
		req.setMember(mockMember());
		return req;
	}

	private MemberDto mockMember() {
		MemberDto mem = new MemberDto();
		mem.setMemberID("11111123");
		mem.setUserName("testname");
		mem.setFullName("xtestname");
		mem.setMemberCode("1-12346789");
		mem.setActivateCode("activate123");
		mem.setCardNo("12346789734134");
		mem.setCardType("JJ Card");
		mem.setCardLevel("1");
		mem.setMemberType("Individual");
		mem.setMemberHierarchy("1");
		mem.setRegisterSource("Website");
		mem.setRegisterDate(new Date());
		mem.setRemindQuestion("Safe001");
		mem.setRemindAnswer("321");
		mem.setStatus("Active");
		mem.setPassword("123456");
		mem.setCellPhone("15026988879");
		mem.setEmail("aaa1234@163.com");
		mem.setTitle("Mr.");
		mem.setIdentityNo("330002312412349");
		mem.setIdentityType("ID");
		mem.setLastUpd(new Date());
		mem.setCardList(mockCardList());
		mem.setMemberVerfyList(mockMemberVerfyList());
		mem.setScoreType(1);
		return mem;
	}

	private List<MemberVerfyDto> mockMemberVerfyList() {
		List<MemberVerfyDto> verfys = new ArrayList<MemberVerfyDto>();
		MemberVerfyDto emailv = new MemberVerfyDto();
		emailv.setMemId("11111123");
		emailv.setMemInfoId(123456);
		emailv.setMemNum("1-12346789");
		emailv.setMenName("15026988879");
		emailv.setPassword("123456");
		emailv.setWebMember(false);
		MemberVerfyDto cardnov = new MemberVerfyDto();
		emailv.setMemId("11111123");
		emailv.setMemInfoId(123456);
		emailv.setMemNum("1-12346789");
		emailv.setMenName("aaa1234@163.com");
		emailv.setPassword("123456");
		emailv.setWebMember(false);
		MemberVerfyDto phonev = new MemberVerfyDto();
		emailv.setMemId("11111123");
		emailv.setMemInfoId(123456);
		emailv.setMemNum("1-12346789");
		emailv.setMenName("12346789734134");
		emailv.setPassword("123456");
		emailv.setWebMember(false);
		verfys.add(emailv);
		verfys.add(cardnov);
		verfys.add(phonev);
		return verfys;
	}

	private List<MemberMemCardDto> mockCardList() {
		List<MemberMemCardDto> cardList = new ArrayList<MemberMemCardDto>();
		MemberMemCardDto jjcard = new MemberMemCardDto();
		jjcard.setMemInfoId(123456);
		jjcard.setMemId("11111123");
		jjcard.setCardTypeCd("JJ Card");
		jjcard.setxCardNum("12346789734134");
		jjcard.setSource("");
		jjcard.setValidDate(new Date());
		jjcard.setDueDate(new Date());
		jjcard.setStatus("To Make");
		jjcard.setCrmKey("");
		jjcard.setDtStatus("");
		jjcard.setDtMsg("");
		jjcard.setDtUpd(new Date());
		cardList.add(jjcard);
		MemberMemCardDto aircard = new MemberMemCardDto();
		aircard.setMemInfoId(123456);
		aircard.setMemId("11111123");
		aircard.setCardTypeCd("ACDH");
		aircard.setxCardNum("ACDH1234678");
		aircard.setSource("");
		aircard.setValidDate(new Date());
		aircard.setDueDate(new Date());
		aircard.setStatus("Sent");
		aircard.setCrmKey("");
		aircard.setDtStatus("");
		aircard.setDtMsg("");
		aircard.setDtUpd(new Date());
		cardList.add(aircard);
		return cardList;
	}

}
