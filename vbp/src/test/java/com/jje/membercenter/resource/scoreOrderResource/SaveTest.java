package com.jje.membercenter.resource.scoreOrderResource;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.ScoreOrderDto;
import com.jje.membercenter.ScoreOrderResource;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
@Transactional
public class SaveTest {

    @Autowired
    private VirtualDispatcherService virtualDispatcherService;

    @Test
    public void saveTest() throws Exception {
        String content = JaxbUtils.convertToXmlString(getScoreOrderDto());
        MockHttpRequest request = MockHttpRequest
                .post("/scoreOrder/save");
        request.content(content.getBytes("UTF-8"));
        request.contentType(MediaType.APPLICATION_XML);
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("scoreOrderResource",
                ScoreOrderResource.class).invoke(request, response);
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    private ScoreOrderDto getScoreOrderDto() {
        ScoreOrderDto order = new ScoreOrderDto();
        order.setBankCode("bankCode ");
        order.setBuyScore(1);
        order.setCreateTime(new Date());
        order.setMemberId("00001");
        order.setOrderNo("orderNo");
        order.setOrderStatus("orderSta");
        return order;
    }

}
