package com.jje.membercenter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.MemberDegree;
import com.jje.membercenter.domain.ConfigRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class ConfigTest {

	@Autowired
	private VirtualDispatcherService virtualDispatcherService;
	@Autowired
	private ConfigRepository configRepository;

	@Test
	public void getUpgradeNumberTest() throws Exception {
			String upgradeNumber=configRepository.getUpgradeNumber(MemberDegree.CLASSIC);
			Assert.assertEquals("30", upgradeNumber);
			 
	}

	@Test
	public void getUpgradeScoresTest() throws Exception {
		String upgradeScores =configRepository.getUpgradeScores(MemberDegree.CLASSIC);
		Assert.assertEquals("30000", upgradeScores);
	}
}
