package com.jje.membercenter.support.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.membercenter.score.DataCodeDto;
import com.jje.membercenter.persistence.SupportMapper;
 

@Repository
public class SupportRepository {
	@Autowired
	private  SupportMapper supportMapper;
	
	public DataCode queryDataCode(String type,String code){
		DataCode dataCode=new DataCode();
		dataCode.setType(type);
		dataCode.setCode(code);
		return supportMapper.queryDataCode(dataCode);
	}
	
	public List<DataCodeDto> queryDataCodeAll(){
		List<DataCodeDto> dataCodeDtoList=new ArrayList<DataCodeDto>();
		List<DataCode> dataCodeList=supportMapper.queryDataCodeList();
		for(DataCode dataCode : dataCodeList){
			dataCodeDtoList.add(dataCode.toDto());
		}
		return dataCodeDtoList;
	}
	
}
