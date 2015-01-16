package actest.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.accountapply.AccountMergeApplyDto;
import com.jje.membercenter.AccountApplyResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class AccountApplyTest
{
	@Autowired
	AccountApplyResource accountApplyResource;
	
	@Test
	public void addAccountMergeApplyPassTest() throws Exception
	{
		AccountMergeApplyDto accountMergeApplyDto = new AccountMergeApplyDto();
		accountMergeApplyDto.setAppylDate(new Date());
		try
		{
			accountMergeApplyDto.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1987-10-14"));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		accountMergeApplyDto.setEmail("feng.2087@163.com");
		accountMergeApplyDto.setMobile("15971219311");
		accountMergeApplyDto.setPrimaryAddress("djdjjjjdjjdjdd");
		accountMergeApplyDto.setProvideMemberId("112222");
		accountMergeApplyDto.setStatus("1");
		accountMergeApplyDto.setTrueName("lenvon");
		Response response = accountApplyResource.addAccountMergeApply(accountMergeApplyDto);
		Assert.assertNotNull(response);
	}
	
	@Test
	public void addAccountMergeApplyFailTest() throws Exception
	{
		AccountMergeApplyDto accountMergeApplyDto = new AccountMergeApplyDto();
		accountMergeApplyDto.setAppylDate(new Date());
		try
		{
			accountMergeApplyDto.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1987-10-14"));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		accountMergeApplyDto.setPrimaryAddress("djdjjjjdjjdjdd");
		accountMergeApplyDto.setProvideMemberId("112222");
		accountMergeApplyDto.setStatus("1");
		accountMergeApplyDto.setTrueName("lenvon");
		Response response = accountApplyResource.addAccountMergeApply(accountMergeApplyDto);
		Assert.assertEquals(Status.NOT_MODIFIED.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void listMergeApplyHistoryPassTest()
	{
			QueryMemberDto<AccountMergeApplyDto> queryDto = new QueryMemberDto<AccountMergeApplyDto>();
			queryDto.setPagination(new Pagination(1,1,"",""));
			AccountMergeApplyDto accountMergeApplyDto=new AccountMergeApplyDto();
			accountMergeApplyDto.setMemberId("1111");
			queryDto.setCondition(accountMergeApplyDto);
			Response response = accountApplyResource.listMergeApplyHistory(queryDto);
			ResultMemberDto<AccountMergeApplyDto> resultDto = (ResultMemberDto<AccountMergeApplyDto>) response.getEntity();
		             
		
	}

}
