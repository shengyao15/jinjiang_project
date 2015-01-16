package com.jje.membercenter.campaign.domain;


import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.campaign.LotteryCoinDto;

public class LotteryCoin {

    private Long id;
    private String  mcMemberCode;
    private Long  availableLotteryCoin = 0L;
    
    public LotteryCoin() {}

    public LotteryCoin(String mcMemberCode) {
    	this.mcMemberCode = mcMemberCode;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMcMemberCode() {
        return mcMemberCode;
    }

    public void setMcMemberCode(String mcMemberCode) {
        this.mcMemberCode = mcMemberCode;
    }

	public Long getAvailableLotteryCoin() {
		return availableLotteryCoin;
	}

	public void setAvailableLotteryCoin(Long availableLotteryCoin) {
		this.availableLotteryCoin = availableLotteryCoin;
	}

	public void operateCoinNum(Long coinOperateNum){
		setAvailableLotteryCoin(availableLotteryCoin + coinOperateNum);
    }
	

	public boolean isAvailableLotteryCoin(Long coinOperateNum) {
		if(availableLotteryCoin + coinOperateNum < 0) {
			return false;
		}
		return true;
	}

    public LotteryCoinDto toDto(){
        LotteryCoinDto lotteryCoinDto = new LotteryCoinDto();
        BeanUtils.copyProperties(this, lotteryCoinDto);
        return lotteryCoinDto;
    }
}
