package com.jje.vbp.validate.persistence;

import java.util.Date;
import java.util.List;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jje.common.utils.DateUtils;
import com.jje.membercenter.DataPrepareFramework;
import com.jje.membercenter.domain.MemberRepository;
import com.jje.vbp.validate.domain.ValidateCodeLog;
import com.jje.vbp.validate.domain.ValidateCodeLogRepository;

public class ValidateCodeLogRepositoryTest extends DataPrepareFramework{

    @Autowired
    private ValidateCodeLogRepository validateCodeLogRepository;

    @Test
		public void should_insert_success() {
    	ValidateCodeLog record = new ValidateCodeLog("ip", "phone", "mail", "code", new Date());
			validateCodeLogRepository.insert(record);
			Assert.assertNotNull(record.getId());
		}
    
    @Test
    public void should_insert_getLastLog_success() {
    	ValidateCodeLog record = new ValidateCodeLog("ip", "phone", "mail", "code", new Date());
			validateCodeLogRepository.insert(record);
			ValidateCodeLog result = validateCodeLogRepository.getLastLog(new ValidateCodeLog("ip", "phone", "mail", "code", DateUtils.addMinutes(new Date(), -10)));
			Assert.assertThat(result.getId(), Is.is(record.getId()));
		}

    @Test
		public void should_insert_and_selectByPrimaryKey_success() {
    	ValidateCodeLog record = new ValidateCodeLog("ip", "phone", "mail", "code", new Date());
    	validateCodeLogRepository.insert(record);
			ValidateCodeLog result = validateCodeLogRepository.selectByPrimaryKey(record.getId());
			Assert.assertNotNull(result);
		}

    @Test
		public void should_insert_and_selectLogForValidate_success() {
    	validateCodeLogRepository.insert(new ValidateCodeLog("ip", "phone", "mail", "code", new Date()));
    	validateCodeLogRepository.insert(new ValidateCodeLog("ip", "phone", "mail", "code", DateUtils.addMinutes(new Date(), 10)));
			List<ValidateCodeLog> resultList = validateCodeLogRepository.selectLogForValidate(new ValidateCodeLog("ip", "phone", "mail", "code", DateUtils.addMinutes(new Date(), 5)));
			Assert.assertThat(resultList.size(), Is.is(1));
		}

    @Test
		public void should_insert_and_countLogForValidate_success() {
    	validateCodeLogRepository.insert(new ValidateCodeLog("ip1", "phone", "mail", "code", new Date()));
    	validateCodeLogRepository.insert(new ValidateCodeLog("ip1", "phone", "mail", "code", DateUtils.addMinutes(new Date(), 10)));
			Assert.assertThat(validateCodeLogRepository.countLogForValidate(new ValidateCodeLog("ip1", "phone", "mail", "code", DateUtils.addMinutes(new Date(), 5))), Is.is(1));
		}
    
}
