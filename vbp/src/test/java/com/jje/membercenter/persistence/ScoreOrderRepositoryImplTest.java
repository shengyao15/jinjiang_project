package com.jje.membercenter.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jje.membercenter.DataPrepareFramework;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.membercenter.domain.ScoreOrder;
import com.jje.membercenter.domain.ScoreOrderRepository;


@Transactional
public class ScoreOrderRepositoryImplTest extends DataPrepareFramework {

	@Autowired
	private ScoreOrderRepository scoreOrderRepository;


    private ScoreOrder createScoreOrder(){
        ScoreOrder order=new ScoreOrder();
        order.setBankCode("bankCode ");
        order.setBuyScore(1);
        order.setCreateTime(new Date());
        order.setMemberId("20130325");
        order.setOrderNo("orderNo");
        order.setOrderStatus("orderSta");
        return order;
    }

	@Test
	public void create() {
        scoreOrderRepository.save(createScoreOrder());
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("memberId", "20130325");
        List<ScoreOrder> list = scoreOrderRepository.get(map);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
        Assert.assertEquals("20130325",list.get(0).getMemberId());
	}
}
