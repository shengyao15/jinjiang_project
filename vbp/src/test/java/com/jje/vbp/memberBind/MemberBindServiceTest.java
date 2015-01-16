package com.jje.vbp.memberBind;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.bam.JaxbUtils;
import com.jje.dto.membercenter.memberbind.MemberBindCardDto;
import com.jje.dto.membercenter.memberbind.MemberBindDto;
import com.jje.dto.membercenter.memberbind.MemberBindStatus;
import com.jje.dto.membercenter.memberbind.MemberInfoResultDto;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.persistence.MemberScoreLevelInfoRepositoryImpl;
import com.jje.vbp.memberBind.persistence.MemberBindMapper;
import com.jje.vbp.memberBind.service.MemberBindRepositoryImpl;
import com.jje.vbp.memberBind.service.MemberBindService;

public class MemberBindServiceTest extends DataPrepareFramework {
	
	@Autowired
	MemberBindService service;
	@Autowired
	MemberBindRepositoryImpl repository;
	@Autowired
	MemberBindMapper mapper;
	@Autowired
	MemberScoreLevelInfoRepositoryImpl memberScoreLevelInfoRepository;

	@Test
	public void loginBindTest_binded() throws Exception {
		MemberBindDto dto = new MemberBindDto(
				"mcMemberCode01",
				"key01",
				"cardstay",
				"username01",
				"password01");
		String msg = service.checkLoginAndBind(dto);
		Assert.assertEquals(msg, MemberBindStatus.BINDED.getStatus());
	}
	
	@Test
	public void loginBindTest_notFound() throws Exception {
		MemberBindDto dto = new MemberBindDto(
				"mcMemberCode01",
				"key01",
				"cardstay",
				"username01_notFound",
				"password01");
		String msg = service.checkLoginAndBind(dto);
		Assert.assertEquals(msg, MemberBindStatus.NOT_FOUND.getStatus());
	}
	
	@Test
	public void loginBindTest_success() throws Exception {
		MemberBindDto dto = new MemberBindDto(
				"mcMemberCode02",
				"key02",
				"cardstay",
				"username02",
				"password02");
		String msg = service.checkLoginAndBind(dto);
		Assert.assertEquals(msg, MemberBindStatus.BIND_SUCCESS.getStatus());
	}
	
	@Test
	public void loginBindTest_invalid() throws Exception {
		MemberBindDto dto = new MemberBindDto(
				"mcMemberCode02",
				"key02",
				"cardstay",
				"username02",
				"password03");
		String msg = service.checkLoginAndBind(dto);
		Assert.assertEquals(msg, MemberBindStatus.INVALID.getStatus());
	}	
	
	@Test
	public void getMemberInfoTest_invalid() throws Exception {
		String channel = "aaaa";
		String bindKey = "key01";
		MemberInfoResultDto dto = service.getMemberInfo(channel, bindKey);
		
		Assert.assertNotNull(dto);
	}
	
	@Test
	public void getMemberCardInfoTest() {
		String key = "key01";
		MemberBindCardDto dto = service.getMemCardInfoByKey(key, "cardstay");
		System.out.println(JaxbUtils.convertToXmlString(dto));
		Assert.assertEquals(dto.getCardNumber(), "111f128");
	}
	
    @Test
    public void getMemberIdTest() {
    	MemberBindCardDto dto = service.getMemCardInfoByKey("key01", "cardstay");
	    Assert.assertEquals("111f128", dto.getCardNumber());
    }
    
    @Test
    public void getMcMemberCodeTest() {
    	String mcMemberCode = repository.getMcMemberCodeByKey("key01", "cardstay");
	    Assert.assertEquals(mcMemberCode, "mcMemberCode01");
    } 
   

}