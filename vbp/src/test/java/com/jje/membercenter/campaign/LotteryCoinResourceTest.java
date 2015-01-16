package com.jje.membercenter.campaign;

import java.util.Calendar;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.dto.membercenter.campaign.LotteryCoinDto;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateCategory;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateDto;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateResultDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class LotteryCoinResourceTest {
	
	@Autowired
	LotteryCoinResource resource;
	
	@Test
	public void should_increase_award_coin_test() {
		LotteryCoinOperateDto oper = mockIncreaseMemberAwardOperateDto();
		System.out.println(JaxbUtils.convertToXmlString(oper));
		Response res = resource.increaseOrDecreaseLotteryCoin(oper);
		Assert.assertTrue(res.getStatus()==Status.OK.getStatusCode());
		LotteryCoinOperateResultDto status = (LotteryCoinOperateResultDto) res.getEntity();
		Assert.assertTrue(LotteryCoinOperateResultDto.LotteryCoinOperateStatus.SUCCESS.equals(status.getStatus()));
	}
	
	@Test
	public void should_decrease_award_coin_test() {
		LotteryCoinOperateDto oper = mockDncreaseMemberAwardOperateDto();
		System.out.println(JaxbUtils.convertToXmlString(oper));
		Response res = resource.increaseOrDecreaseLotteryCoin(oper);
		Assert.assertTrue(res.getStatus()==Status.OK.getStatusCode());
		LotteryCoinOperateResultDto status = (LotteryCoinOperateResultDto) res.getEntity();
		Assert.assertTrue(LotteryCoinOperateResultDto.LotteryCoinOperateStatus.COIN_NOT_ENOUGH.equals(status.getStatus()));
	}
	
	@Test
	public void should_get_member_available_lottery_coin_by_mcmembercode_test() {
		String mcMemberCode="1-19056406";
		Response res = resource.queryAvailableLotteryCoin(mcMemberCode);
		Assert.assertTrue(res.getStatus()==Status.OK.getStatusCode());
		LotteryCoinDto coin = (LotteryCoinDto) res.getEntity();
		Assert.assertTrue(coin != null);
		Assert.assertTrue(coin.getAvailableLotteryCoin() > 0);
	}
	
	private LotteryCoinOperateDto mockIncreaseMemberAwardOperateDto() {
		LotteryCoinOperateDto oper = new LotteryCoinOperateDto();
		oper.setLotteryCoinOperateCategory(LotteryCoinOperateCategory.RECOMMEND_FRIEND);
		oper.setMcMemberCode("123456");
		Calendar date = Calendar.getInstance();
		date.set(2012, 4, 20);
		oper.setTriggerDate(date.getTime());
		return oper;
	}

	private LotteryCoinOperateDto mockDncreaseMemberAwardOperateDto() {
		LotteryCoinOperateDto oper = new LotteryCoinOperateDto();
		oper.setLotteryCoinOperateCategory(LotteryCoinOperateCategory.EVERY_MONTH);
		oper.setMcMemberCode("123456");
		Calendar date = Calendar.getInstance();
		date.set(2012, 4, 20);
		oper.setTriggerDate(date.getTime());
		return oper;
	}

}
