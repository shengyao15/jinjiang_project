package actest.crm.impl;

import java.util.Calendar;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.dto.member.score.QueryScoreDto;
import com.jje.membercenter.domain.CRMOperationRespository;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class CRMOperationRepositoryImplTest {

	@Autowired
	private CRMOperationRespository crmOperationRespository;

	@Test
	public void getScoreLog() throws Exception {
		crmOperationRespository.queryScore(getQuery());
	}

	private QueryScoreDto getQuery() {
		QueryScoreDto query = new QueryScoreDto();
		query.setMcMemberCode("3");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -2);
		query.setStartDate(cal.getTime());
		cal.add(Calendar.MONTH, +2);
		query.setEndDate(cal.getTime());
		return query;
	}

}
