package com.jje.membercenter.campaign.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.membercenter.campaign.LotteryCoinOperateDto;
import com.jje.membercenter.campaign.persistence.LotteryCoinMapper;

@Repository
public class LotteryCoinRepository {
	
	@Autowired
	private LotteryCoinMapper lotteryCoinMapper;

	public LotteryCoin getLotteryCoin(LotteryCoinOperateDto operate) {
        return lotteryCoinMapper.queryLotteryCoin(operate);
    }
	
	public Long getOperateLotteryCoin(LotteryCoinOperateDto operate) {
        return lotteryCoinMapper.getOperateLotteryCoin(operate);
    }
	
	public void storeLotteryCoin(LotteryCoin coin){
		if(coin.getId() == null)
			lotteryCoinMapper.insertLotteryCoin(coin);
		else
			lotteryCoinMapper.updateAvailableLotteryCoin(coin);
	}
	
	public void insertLotteryCoinDetail(LotteryCoinDetail lotteryCoinDetail){
		lotteryCoinMapper.insertLotteryCoinDetail(lotteryCoinDetail);
	}
	
	public List<LotteryCoinDetail> selectLotteryCoinDetail(LotteryCoinDetail lotteryCoinDetail){
		return lotteryCoinMapper.selectLotteryCoinDetail(lotteryCoinDetail);
	}
	

}
