package actest.member;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.dto.membercenter.MemberJREZQueryDto;
import com.jje.dto.membercenter.MemberJREZResultsDto;
import com.jje.membercenter.MemberResource;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class JREZMemberQueryTest {
	
	@Autowired
	MemberResource memberResource;
	
	@Test
	public void should_query_member_with_condition() {
		MemberJREZQueryDto query = mockMemberJREZQueryDto();
		Response res = memberResource.queryMember(query);
		Assert.assertTrue(res.getStatus() == Status.OK.getStatusCode());
		Assert.assertTrue(res.getEntity() != null);
		MemberJREZResultsDto list = (MemberJREZResultsDto) res.getEntity();
		Assert.assertTrue(list.getResult().size() > 0);
	}
	
	private MemberJREZQueryDto mockMemberJREZQueryDto() {
		MemberJREZQueryDto query = new MemberJREZQueryDto();
		//query.setEmail("jreztest@123.com");
		//query.setIdentityNo("22222");
		//query.setIdentityType("Others");
		//query.setCardNo("6765676763");
		//query.setName("jreztest");
		//query.setPhone("15026988244");
		//query.setCardNo("1157863308");
		return query;
	}

}
