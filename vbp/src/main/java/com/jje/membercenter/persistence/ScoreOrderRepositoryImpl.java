package com.jje.membercenter.persistence;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.membercenter.domain.ScoreOrder;
import com.jje.membercenter.domain.ScoreOrderRepository;
@Repository
public class  ScoreOrderRepositoryImpl implements ScoreOrderRepository{
	@Autowired
	private ScoreOrderMapper scoreOrderMapper;
	
	
	public  List<ScoreOrder> get(Map<String,Object> map){
		return scoreOrderMapper.get(map);
 	}

	public void save(ScoreOrder order){
		scoreOrderMapper.insert(order);		
	}

}
