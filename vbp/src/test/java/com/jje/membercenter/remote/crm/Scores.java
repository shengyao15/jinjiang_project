package com.jje.membercenter.remote.crm;

import java.util.Date;

import com.jje.membercenter.DataPrepareFramework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.common.utils.DateUtils;
import com.jje.membercenter.remote.crm.datagram.request.CancelTradeReq;
import com.jje.membercenter.remote.crm.datagram.request.GetScoresHistoryReq;
import com.jje.membercenter.remote.crm.datagram.request.ScoresTradeReq;

@Transactional
public class Scores extends DataPrepareFramework {

	@Test
	public void getScoresHistory(){
		GetScoresHistoryReq  req=new GetScoresHistoryReq();
		req.getBody().setMembid("1-18120148");
		req.getBody().setStartdate("01/22/2011");
		req.getBody().setEnddate("11/22/2012");
		req.send();
	}
	
	@Test
	public void trade(){
		ScoresTradeReq req =new ScoresTradeReq();
		req.getBody().setMembid("1-18120148");
		req.getBody().setPoints("1");
		req.getBody().setRedeemproduct("abc");
		req.getBody().setTransdate(DateUtils.formatDate(new Date(), "MM/dd/yyyy"));
		req.send();
 	}
	
	@Test
	public void cancelTrade(){
		CancelTradeReq req=new CancelTradeReq();
		req.getBody().setMembid("1-18120148");
		req.getBody().setPunishpoints("0");
		req.getBody().setTxnid("1-EOOKU");
		req.send(); 
	}
}
