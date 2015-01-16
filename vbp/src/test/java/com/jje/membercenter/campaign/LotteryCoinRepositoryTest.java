package com.jje.membercenter.campaign;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.dto.membercenter.campaign.LotteryCoinOperateCategory;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateDto;
import com.jje.membercenter.campaign.domain.LotteryCoin;
import com.jje.membercenter.campaign.domain.LotteryCoinDetail;
import com.jje.membercenter.campaign.domain.LotteryCoinRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class LotteryCoinRepositoryTest {

	@Autowired
	private LotteryCoinRepository repo;

	@Test
	public void test_getLotteryCoin() {
		LotteryCoinOperateDto operate = new LotteryCoinOperateDto();
		operate.setMcMemberCode("1-19056406");
		LotteryCoin coin = repo.getLotteryCoin(operate);
		Assert.assertNotNull(coin.getAvailableLotteryCoin());
	}

	@Test
	public void test_increaseOrDecreaseLotteryCoin() {
		try {
			LotteryCoinOperateDto operate = new LotteryCoinOperateDto();
			operate.setMcMemberCode("1-19056406");
			operate.setLotteryCoinOperateCategory(LotteryCoinOperateCategory.EVERY_WEEKLY);
			LotteryCoin coin = repo.getLotteryCoin(operate);
			coin.operateCoinNum(repo.getOperateLotteryCoin(operate));
			repo.storeLotteryCoin(coin);
			test_getAvailableLotteryCoin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_getAvailableLotteryCoin() {
		LotteryCoinOperateDto operate = new LotteryCoinOperateDto();
		operate.setLotteryCoinOperateCategory(LotteryCoinOperateCategory.EVERY_WEEKLY);
		Long coinOperateNum = repo.getOperateLotteryCoin(operate);
		Assert.assertNotNull(coinOperateNum);
	}

	@Test
	public void test_storeLotteryCoin() {
		try {
			LotteryCoin coin = new LotteryCoin();
			coin.setAvailableLotteryCoin(250L);
			coin.setMcMemberCode("1157863308");
			repo.storeLotteryCoin(coin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void should_insert_lottery_coin_detail(){
		LotteryCoin lotteryCoin = repo.getLotteryCoin(this.moke_get_lotteryCoin_condition());
		LotteryCoinOperateCategory operateCategory = LotteryCoinOperateCategory.EVERY_WEEKLY;
		
		LotteryCoinDetail lotteryCoinDetail = new LotteryCoinDetail();
		lotteryCoinDetail.setLotteryCoin(lotteryCoin);
		lotteryCoinDetail.setOperateCategory(operateCategory);
		lotteryCoinDetail.setCoinQuantity(this.getCoinQuantity(operateCategory));
		
		Assert.assertNull(lotteryCoinDetail.getId());
		repo.insertLotteryCoinDetail(lotteryCoinDetail);
		Assert.assertNotNull(lotteryCoinDetail.getId());
	}
	
	private LotteryCoinOperateDto moke_get_lotteryCoin_condition(){
		LotteryCoinOperateDto operate = new LotteryCoinOperateDto();
		operate.setMcMemberCode("1-19056406");
		return operate;
	}
	
	private Long getCoinQuantity(LotteryCoinOperateCategory operateCategory){
		LotteryCoinOperateDto operate = new LotteryCoinOperateDto();
		operate.setLotteryCoinOperateCategory(operateCategory);
		Long coinOperateNum = repo.getOperateLotteryCoin(operate);
		return coinOperateNum;
	}
	
	

}
