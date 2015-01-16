package com.jje.membercenter.resource.scoreResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.jje.membercenter.DataPrepareFramework;
import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jje.common.utils.JaxbUtils;
import com.jje.common.utils.VirtualDispatcherService;
import com.jje.dto.membercenter.score.MemberScoreLevelInfoDto;
import com.jje.membercenter.ScoreResource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GetMemberScoreInfoTest extends DataPrepareFramework {

	@Autowired
	private VirtualDispatcherService virtualDispatcherService;

    @Test
	public void queryScoreInfo()throws Exception{
		MockHttpRequest request = MockHttpRequest.get("/score/getMemberScoreInfo/2013032510082" );
		MockHttpResponse response = new MockHttpResponse();
		virtualDispatcherService.getDispatcher("scoreResource", ScoreResource.class).invoke(request, response);
		Assert.assertTrue(StringUtils.isNotEmpty(response.getContentAsString()));
	}

	@Test
	public void updateScoreInfo() throws Exception{
        MemberScoreLevelInfoDto  dto=mockMemberScoreDto();
        String content=JaxbUtils.convertToXmlString(dto);
        MockHttpRequest request = MockHttpRequest.post("/score/updateMemberAvailableScore" );
        request.content(content.getBytes("UTF-8"));
        request.contentType(MediaType.APPLICATION_XML);
        MockHttpResponse response = new MockHttpResponse();
        virtualDispatcherService.getDispatcher("scoreResource", ScoreResource.class).invoke(request, response);
        Assert.assertEquals(Status.OK.getStatusCode(),response.getStatus());
	}

	private MemberScoreLevelInfoDto  mockMemberScoreDto(){
		MemberScoreLevelInfoDto  dto=new MemberScoreLevelInfoDto();
		dto.setAvailableScore(1111L);
		dto.setMcMemberCode("3");
		return dto;
	}

}
