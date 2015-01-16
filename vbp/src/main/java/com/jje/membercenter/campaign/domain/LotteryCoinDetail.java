package com.jje.membercenter.campaign.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.campaign.LotteryCoinDetailDto;
import com.jje.dto.membercenter.campaign.LotteryCoinOperateCategory;

public class LotteryCoinDetail {
	private Long id;
	private LotteryCoin lotteryCoin;
	private LotteryCoinOperateCategory operateCategory;
	private Long coinQuantity;
	private Date createDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LotteryCoinOperateCategory getOperateCategory() {
		return operateCategory;
	}
	public void setOperateCategory(LotteryCoinOperateCategory operateCategory) {
		this.operateCategory = operateCategory;
	}
	public Long getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Long coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public LotteryCoin getLotteryCoin() {
		return lotteryCoin;
	}
	public void setLotteryCoin(LotteryCoin lotteryCoin) {
		this.lotteryCoin = lotteryCoin;
	}
	
	 public LotteryCoinDetailDto toDto(){
		 LotteryCoinDetailDto dto = new LotteryCoinDetailDto();
		 dto.setId(this.id);
		 dto.setOperateCategory(this.operateCategory);
		 dto.setCoinQuantity(this.coinQuantity);
		 dto.setMcMemberCode(this.lotteryCoin.getMcMemberCode());
		 dto.setCreateDate(this.createDate);
	     return dto;
	 }
	
}
