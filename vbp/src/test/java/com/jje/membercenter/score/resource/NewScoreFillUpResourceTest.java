package com.jje.membercenter.score.resource;

import java.math.BigDecimal;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.dto.membercenter.score.ScoreFillUpDto;
import com.jje.dto.membercenter.score.ScoreFillUpType;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.remote.crm.datagram.request.ScoreFillUpReq;
import com.jje.membercenter.remote.crm.datagram.response.ScoreFillUpRes;
import com.jje.membercenter.remote.crm.support.CrmSrType;
import com.jje.membercenter.remote.handler.RequiredParamsNullException;
import com.jje.membercenter.score.ScoreFillUpResource;
import com.jje.membercenter.score.domain.ScoreFillUp;
import com.jje.membercenter.score.domain.ScoreFillUpResult;

public class NewScoreFillUpResourceTest extends DataPrepareFramework {
	
	@Autowired
	ScoreFillUpResource scoreFillUpResource;

	@Test
	public void testScoreFillUpRequest() {
		ScoreFillUpDto scoreDto = mockHotelScoreFillUp();
		Response response = scoreFillUpResource.fillUp(scoreDto);
		
	}
	
    private ScoreFillUpDto mockHotelScoreFillUp() {
        ScoreFillUpDto score = new ScoreFillUpDto();
        score.setBusinessType(ScoreFillUpType.AUTO);
        score.setCheckInCity("上海");
        score.setHotelName("和平饭店");
        score.setRoomNo("503");
        score.setPayTime(new Date());
        score.setCarEndTime(new Date());
        score.setCheckInCity("上海");
        score.setGroupCode("ccc");
        score.setCarNo("110");
        score.setStoreName("store");
        score.setCheckInTime(new Date());
        score.setCheckOutTime(new Date());
        score.setAmount(new BigDecimal("800.00"));
        score.setOrderNo("JH00000052364");
        score.setInvoiceNo("02159875278");
        score.setMcMemberCode("1000");
        return score;
    }

}
