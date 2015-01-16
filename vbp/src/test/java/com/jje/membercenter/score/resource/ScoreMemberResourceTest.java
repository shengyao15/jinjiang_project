package com.jje.membercenter.score.resource;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.MemberDto;
import com.jje.membercenter.ScoreMemberResource;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public class ScoreMemberResourceTest {
    @Autowired
    private VirtualDispatcherService virtualDispatcherService;

    @Test
    public void should_be_get_member_success_when_email_exist() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/scoreMember/getMemberInfo/chenyongne@126.com");
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("scoreMemberResource", ScoreMemberResource.class).invoke(request, response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        MemberDto memberDto = JaxbUtils.convertToObject(response.getContentAsString(), MemberDto.class);
        Assert.assertEquals("陈勇", memberDto.getFullName());
        Assert.assertEquals("13585909080", memberDto.getCellPhone());
    }

    @Test
    public void should_be_get_member_fail_when_email_not_exist() throws Exception {
        MockHttpRequest request = MockHttpRequest.get("/scoreMember/getMemberInfo/notexist@email.com");
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("scoreMemberResource", ScoreMemberResource.class).invoke(request, response);
        Assert.assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
