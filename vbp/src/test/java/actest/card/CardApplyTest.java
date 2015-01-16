package actest.card;

import java.util.Date;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.accountapply.BuyCardApplyDto;
import com.jje.membercenter.AccountApplyResource;
import com.jje.membercenter.MemberResource;
import com.jje.membercenter.domain.CRMAccountApplyRespository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class CardApplyTest
{
	@Autowired
	MemberResource memberResource ;
	
	@Autowired
	AccountApplyResource accountApplyResource;

	@Autowired
	CRMAccountApplyRespository crmAccountApplyRespository;

	@Test
	public void applyCard() throws Exception
	{
		BuyCardApplyDto dto = new BuyCardApplyDto();
		dto.setApplyDate(new Date());
		dto.setApplyReason("新申请");
		dto.setApplyReasonType("新申请");
		dto.setCardNo("6600000074");
		dto.setContactTelephone("15971219312");
		dto.setHierarchy("普通积分会员卡");
		dto.setMailAddress("北京市海淀区中关村大街");
		dto.setMemberId("1-18120148");
		dto.setReceiverName("lenvon");
		CRMResponseDto dto2  = crmAccountApplyRespository.applyCard(dto);
		Assert.assertEquals("2", dto2.getRetcode());
	}

	@Test
	public void listApplyCard() throws Exception
	{
		QueryMemberDto<BuyCardApplyDto> queryDto = new QueryMemberDto<BuyCardApplyDto>();
		BuyCardApplyDto dto = new BuyCardApplyDto();
		dto.setMemberId("1-375932");
		queryDto.setCondition(dto);
		Response response = accountApplyResource.listApplyCard(queryDto);
		ResultMemberDto<BuyCardApplyDto> resultDto = (ResultMemberDto<BuyCardApplyDto>) response.getEntity();
		Assert.assertEquals(null,resultDto);
	}
	
	@Test
	public void cardStatue() throws Exception
	{
		MemberDto dto = new MemberDto();
		dto.setMemberID("0024491");
		Response response = memberResource.queryCardStauts(dto);
		Assert.assertNotNull(response.getEntity());
	}
}
