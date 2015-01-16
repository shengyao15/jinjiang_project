package actest.score;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.SSOReconcileDto;
import com.jje.dto.membercenter.SSOReconciliationDto;
import com.jje.dto.membercenter.SSORedeemDto;
import com.jje.membercenter.SSOResource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class RedeemTest
{
	@Autowired
	SSOResource ssoResource ;
	
	@Ignore
	@Test
	public void addRedeemTest() throws Exception
	{
		SSORedeemDto ssoRedeemDto = new SSORedeemDto();
		ssoRedeemDto.setMemid("1-1531725");
		ssoRedeemDto.setOrderid("ddd");
		ssoRedeemDto.setPdcode("ccc");
		ssoRedeemDto.setScore("50");
		ssoRedeemDto.setTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		Response response = ssoResource.reduceScore(ssoRedeemDto);
		Assert.assertNotNull(response.getEntity());
	}
	
	@Ignore
	@Test
	public void queryRedeemTest() throws Exception
	{
		SSORedeemDto ssoRedeemDto = new SSORedeemDto();
		ssoRedeemDto.setMemid("1-18793182");
		ssoRedeemDto.setOrderid("ddd");
		ssoRedeemDto.setPdcode("ccc");
		ssoRedeemDto.setScore("200");
		ssoRedeemDto.setTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		ssoResource.reduceScore(ssoRedeemDto);
		SSORedeemDto ssoRedeemDto1 = new SSORedeemDto();
		ssoRedeemDto1.setMemid("1-18793182");
		ssoRedeemDto1.setOrderid("ddd");
		ssoRedeemDto1.setPdcode("ccc");
		ssoRedeemDto1.setScore("200");
		ssoRedeemDto1.setTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		ssoResource.reduceScore(ssoRedeemDto1);
		SSORedeemDto ssoRedeemDto2 = new SSORedeemDto();
		ssoRedeemDto2.setMemid("1-18793182");
		ssoRedeemDto2.setOrderid("ddd");
		ssoRedeemDto2.setPdcode("ccc");
		ssoRedeemDto2.setScore("200");
		ssoRedeemDto2.setTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		ssoResource.reduceScore(ssoRedeemDto2);
		SSORedeemDto ssoRedeemDto3 = new SSORedeemDto();
		ssoRedeemDto3.setMemid("1-18793182");
		ssoRedeemDto3.setOrderid("ddd");
		ssoRedeemDto3.setPdcode("ccc");
		ssoRedeemDto3.setScore("200");
		ssoRedeemDto3.setTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new SimpleDateFormat("yyyyMMddHHmmss").parse("20091130111100")));
		ssoResource.reduceScore(ssoRedeemDto3);
		SSOReconcileDto ssoReconcileDto = new SSOReconcileDto();
		ssoReconcileDto.setsTime("20091101101010");
		ssoReconcileDto.seteTime("20121101101010");
		Response response = ssoResource.reconciled(ssoReconcileDto);
		ResultMemberDto<SSOReconciliationDto> resultDto = (ResultMemberDto<SSOReconciliationDto>) response.getEntity();
		List<SSOReconciliationDto> list = resultDto.getResults();
//		System.out.println(list.size());
//		for (int i = 0; i < list.size(); i++)
//		{
//			System.out.println(list.get(i).getMemberId());
//		}
		Assert.assertNotNull(list);
	}
}
