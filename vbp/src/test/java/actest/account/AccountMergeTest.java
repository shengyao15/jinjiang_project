package actest.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
public class AccountMergeTest
{
	@Autowired
	AccountApplyResource accountApplyResource ;
	
	@Ignore
	@Test
	public void addAccountMergeApplyPassTest() throws Exception
	{
		AccountMergeApplyDto accountMergeApplyDto = new AccountMergeApplyDto();
		accountMergeApplyDto.setMemberId("0024491");
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
		accountMergeApplyDto.setProvideMemberId("1-375932");
		accountMergeApplyDto.setStatus("1");
		accountMergeApplyDto.setTrueName("lenvon");
		Response response = accountApplyResource.addAccountMergeApply(accountMergeApplyDto);
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Ignore
	//@Test
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
		accountMergeApplyDto.setProvideMemberId("0024491");
		accountMergeApplyDto.setStatus("1");
		accountMergeApplyDto.setTrueName("lenvon");
		Response response = accountApplyResource.addAccountMergeApply(accountMergeApplyDto);
		Assert.assertEquals(Status.NOT_MODIFIED.getStatusCode(), response.getStatus());
	}
	
	@Ignore
	//@Test
	public void listMergeApplyHistoryPassTest()
	{
		AccountMergeApplyDto dto = new AccountMergeApplyDto();
		dto.setMemberId("0024491");
		QueryMemberDto<AccountMergeApplyDto> queryDto = new QueryMemberDto<AccountMergeApplyDto>();
		queryDto.setPagination(new Pagination(1,1,"",""));
		queryDto.setCondition(dto);
		Response response = accountApplyResource.listMergeApplyHistory(queryDto);
		ResultMemberDto<AccountMergeApplyDto> resultDto = (ResultMemberDto<AccountMergeApplyDto>) response.getEntity();
		List<AccountMergeApplyDto> list = resultDto.getResults();
		Assert.assertNotNull(list);
	}
	@Ignore
	//@Test
	public void listMergeApplyHistoryFailTest()
	{
		QueryMemberDto<AccountMergeApplyDto> queryDto = new QueryMemberDto<AccountMergeApplyDto>();
		queryDto.setPagination(new Pagination(1,0,"",""));
		Response response = accountApplyResource.listMergeApplyHistory(queryDto);
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}
}
