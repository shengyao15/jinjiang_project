package com.jje.membercenter.persistence;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.domain.MemberScoreLevelInfoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })

public class MemberScoreLevelInfoRepositoryImplTest {

	@Autowired
	private MemberScoreLevelInfoRepository memberScoreLevelInfoRepository;

	@Test
	public void query()  {
        MemberScoreLevelInfoDto info=memberScoreLevelInfoRepository.getMemberScoreInfo("111");
        Assert.assertNull(info.getId());
	}
	
	@Test
	public void update() {
		MemberScoreLevelInfoDto info=mockScoreLevelInfo();
		memberScoreLevelInfoRepository.updateMemberAvailableScore(info);
		info=memberScoreLevelInfoRepository.getMemberScoreInfo(String.valueOf(info.getMemberInfoId()));
		Assert.assertNull(info.getId());
		
	}
	
	private MemberScoreLevelInfoDto  mockScoreLevelInfo(){
		MemberScoreLevelInfoDto info=new MemberScoreLevelInfoDto();
		info.setAvailableScore(10000L);
		info.setMcMemberCode("3333");
		info.setMemberInfoId(1L);
		return info;
	}


}
