package com.jje.membercenter.persistence;

import java.util.List;

import com.jje.membercenter.support.domain.DataCode;

public interface SupportMapper {
	DataCode queryDataCode(DataCode dataCode);
	
	List<DataCode> queryDataCodeList();
}
