package com.jje.membercenter.campaign.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jje.common.utils.DateUtils;
import com.jje.dto.membercenter.campaign.LotteryCoinDetailListDto;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateCategory;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateDto;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateResultDto;
import com.jje.membercenter.campaign.domain.LotteryCoin;
import com.jje.membercenter.campaign.domain.LotteryCoinDetail;
import com.jje.membercenter.campaign.domain.LotteryCoinRepository;

@Component
public class LotteryCoinService {
	
	private static final Logger log = LoggerFactory.getLogger(LotteryCoinService.class);
	
	@Autowired
	private LotteryCoinRepository repo;
	
	@Value(value="${lottery.startDate}")
	private String startDate;
	
	@Value(value="${lottery.endDate}")
	private String endDate;
	
	private  boolean isLate(Date triggerDate ) {
		log.debug("triggerDate-->"+DateUtils.formatDate(triggerDate, DateUtils.YYYY_MM_DD_HH_MM_SS));
		log.debug("活动时间有效期--> "+startDate+"-"+endDate);
	    Date formatDate=DateUtils.parseDate(DateUtils.formatDate(triggerDate, DateUtils.YYYY_MM_DD));
    	Calendar start = Calendar.getInstance();
    	start.setTime(DateUtils.parseDate(startDate));
    	Calendar end = Calendar.getInstance();
    	end.setTime(DateUtils.parseDate(endDate));
    	if(formatDate.compareTo(start.getTime())>=0 && formatDate.compareTo(end.getTime())<=0){
    		log.info("isLate:false");
    		return false;
    	}
    	log.info("isLate:true");
    	return true;
    }
    
	@Transactional
	public LotteryCoinOperateResultDto increaseOrDecreaseLotteryCoin(
			LotteryCoinOperateDto operate) {
		LotteryCoinOperateResultDto result = new LotteryCoinOperateResultDto();
		if(isLate(operate.getTriggerDate())) {
			result.activityOutOfDate();
			return result;
		}
		if(operate.getLotteryCoinOperateCategory() == null) {
			result.operateNotMismatch();
			return result;
		}
		LotteryCoin coin = getLotteryCoin(operate);
		Long operateLotteryCoin = getOperateLotteryCoin(operate);
		if (!coin.isAvailableLotteryCoin(operateLotteryCoin)) {
			result.coinNotEnough();
			return result;
		}
		coin.setMcMemberCode(operate.getMcMemberCode());
		coin.operateCoinNum(operateLotteryCoin);
		repo.storeLotteryCoin(coin);
		
		this.storeLotteryCoinDetail(coin,operate.getLotteryCoinOperateCategory(),operateLotteryCoin);
		
		return result;
	}
	
	public LotteryCoinDetailListDto selectCoinDetailsByMemCodeWithOperateCategory(String mcMemberCode,LotteryCoinOperateCategory operateCategory){
		LotteryCoinDetail condition = new LotteryCoinDetail();
		
		LotteryCoin lotteryCoin = new LotteryCoin();
		lotteryCoin.setMcMemberCode(mcMemberCode);
		condition.setLotteryCoin(lotteryCoin);
		
		condition.setOperateCategory(operateCategory);
		List<LotteryCoinDetail> coinDetails = repo.selectLotteryCoinDetail(condition);
		
		return this.toCoinDetailListDto(coinDetails);
	}

	private LotteryCoinDetailListDto toCoinDetailListDto(List<LotteryCoinDetail> coinDetails) {
		LotteryCoinDetailListDto detailListDto = new LotteryCoinDetailListDto();
		for(LotteryCoinDetail detail : coinDetails){
			detailListDto.add(detail.toDto());
		}
		return detailListDto;
	}
	
	private LotteryCoin getLotteryCoin(LotteryCoinOperateDto operate) {
		LotteryCoin coin = repo.getLotteryCoin(operate);
		if(coin == null) {
			coin = new LotteryCoin();
		}
		return coin;
	}

	private Long getOperateLotteryCoin(LotteryCoinOperateDto operate) {
		return repo.getOperateLotteryCoin(operate);
	}
	
	private void storeLotteryCoinDetail(LotteryCoin lotteryCoin, LotteryCoinOperateCategory lotteryCoinOperateCategory, Long coinQuantity){
		LotteryCoinDetail lotteryCoinDetail = new LotteryCoinDetail();
		lotteryCoinDetail.setLotteryCoin(lotteryCoin);
		lotteryCoinDetail.setOperateCategory(lotteryCoinOperateCategory);
		lotteryCoinDetail.setCoinQuantity(coinQuantity);
		repo.insertLotteryCoinDetail(lotteryCoinDetail);
	}
}
