package com.jje.vbp.validate.persistence;

import java.util.List;

import com.jje.vbp.validate.domain.ValidateCodeLog;

public interface ValidateCodeLogMapper {

	int insert(ValidateCodeLog record);

	int insertSelective(ValidateCodeLog record);

	ValidateCodeLog selectByPrimaryKey(Long id);
	
	ValidateCodeLog getLastLog(ValidateCodeLog data);
	
	List<ValidateCodeLog> selectLogForValidate(ValidateCodeLog data);
	
	int countLogForValidate(ValidateCodeLog data);
	
	int updateByPrimaryKeySelective(ValidateCodeLog record);

	int updateByPrimaryKey(ValidateCodeLog record);
}