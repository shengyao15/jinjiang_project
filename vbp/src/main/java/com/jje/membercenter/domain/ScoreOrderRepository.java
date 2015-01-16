package com.jje.membercenter.domain;

import java.util.List;
import java.util.Map;

public interface ScoreOrderRepository
{
	public List<ScoreOrder> get(Map<String,Object> map);

	public void save(ScoreOrder order);

}
