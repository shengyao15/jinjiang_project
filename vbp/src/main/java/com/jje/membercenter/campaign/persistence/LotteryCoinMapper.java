package com.jje.membercenter.campaign.persistence;

import java.util.List;

import com.jje.dto.membercenter.campaign.LotteryCoinOperateDto;
import com.jje.membercenter.campaign.domain.LotteryCoin;
import com.jje.membercenter.campaign.domain.LotteryCoinDetail;

public interface LotteryCoinMapper {
	
	LotteryCoin queryLotteryCoin(LotteryCoinOperateDto operate);
	
	Long getOperateLotteryCoin(LotteryCoinOperateDto operate);
	
	void updateAvailableLotteryCoin(LotteryCoin coin);
	
	void insertLotteryCoin(LotteryCoin coin);
	
	void insertLotteryCoinDetail(LotteryCoinDetail lotteryCoinDetail);
	
	List<LotteryCoinDetail> selectLotteryCoinDetail(LotteryCoinDetail lotteryCoinDetail);

}
