package actest.score;

import com.jje.common.utils.VirtualDispatcherService;
import com.jje.membercenter.ScoreMemberResource;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
@Ignore
public class ScoreMemberResourceTest {
    @Autowired
    private VirtualDispatcherService virtualDispatcherService;

    @Test
    public void should_be_get_score_success_when_mcMemberCode_exist() throws Exception {
        //1-18120148  该接口参数已经由id改为mcMemberCode
        MockHttpRequest request = MockHttpRequest.get("/scoreMember/getMemberScoreById/7");
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("scoreMemberResource", ScoreMemberResource.class).invoke(request, response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}
