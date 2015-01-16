package com.jje.vbp.taobao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jje.vbp.taobao.domain.TaobaoBindingDomain;

public interface TaobaoBindingMapper {

/*	List<TaobaoBindingDomain> query(
			@Param("taobaoId") String taobaoId, 
			@Param("memberId") String memberId);*/
	
	List<TaobaoBindingDomain> query(@Param("taobaoId") String taobaoId);

	List<TaobaoBindingDomain> queryTaobaoID(@Param("memberID") String memberID);
	
	int insert(TaobaoBindingDomain taobaoBinding);

	void updateStatus(@Param("taobaoId") String taobaoId,
			@Param("status") String status);
}
