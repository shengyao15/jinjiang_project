/**
 * 
 */
package com.jje.membercenter.persistence;

import java.util.List;
import java.util.Map;

import com.jje.membercenter.domain.ScoreOrder;

public interface ScoreOrderMapper {

	List<ScoreOrder> get(Map<String,Object> map);

	void insert(ScoreOrder order);
}
