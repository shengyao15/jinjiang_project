package com.jje.vbp.taobao.service;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jje.vbp.taobao.persistence.TaobaoBindingMapper;

@Service
public class TaobaoBindingRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(TaobaoBindingRepository.class);

	@Autowired
	private TaobaoBindingMapper taobaoBindingMapper;

}
