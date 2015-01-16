package com.jje.vbp.validate.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.vbp.validate.persistence.ValidateCodeLogMapper;

@Repository
public class ValidateCodeLogRepository {

	@Autowired
	private ValidateCodeLogMapper validateCodeLogMapper;

	public int insert(ValidateCodeLog record) {
		return validateCodeLogMapper.insert(record);
	}

	public ValidateCodeLog selectByPrimaryKey(Long id) {
		return validateCodeLogMapper.selectByPrimaryKey(id);
	}

	public List<ValidateCodeLog> selectLogForValidate(ValidateCodeLog data) {
		return validateCodeLogMapper.selectLogForValidate(data);
	}

	public ValidateCodeLog getLastLog(ValidateCodeLog data) {
		return validateCodeLogMapper.getLastLog(data);
	}

	public int countLogForValidate(ValidateCodeLog data) {
		return validateCodeLogMapper.countLogForValidate(data);
	}

}
