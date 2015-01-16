package com.jje.vbp.taobao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.membercenter.DataPrepareFramework;
import com.jje.vbp.taobao.domain.TaobaoBindingDomain;
import com.jje.vbp.taobao.persistence.TaobaoBindingMapper;

public class TaobaoBindingTest extends DataPrepareFramework {

	@Autowired
	private TaobaoBindingMapper mapper;

	@Test
	public void test() {
		TaobaoBindingDomain taobaoBinding;
		for (Integer i = 10000; i < 10100; i++) {
			taobaoBinding = new TaobaoBindingDomain();
			taobaoBinding.setTaobaoId(i.toString());
			taobaoBinding.setTaobaoLevel("F1");
			taobaoBinding.setMemberId(i.toString());
			taobaoBinding.setStatus("UNBIND");
			taobaoBinding.setBindMode("LOGIN");
			mapper.insert(taobaoBinding);
		}

		List<TaobaoBindingDomain> list = mapper.query("10010");

		mapper.updateStatus("10011", "DONE");

		Assert.assertTrue(true);
	}
}
